package com.asistencia.sistema_asistencia.controller;

import com.asistencia.sistema_asistencia.model.Asistencia;
import com.asistencia.sistema_asistencia.repository.AreaRepository;
import com.asistencia.sistema_asistencia.repository.AsistenciaRepository;
import com.asistencia.sistema_asistencia.repository.EmpleadoRepository;
import com.asistencia.sistema_asistencia.repository.TurnoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/reportes")
public class ReporteController {

    @Autowired private AsistenciaRepository asistenciaRepository;
    @Autowired private AreaRepository areaRepository;
    @Autowired private TurnoRepository turnoRepository;
    @Autowired private EmpleadoRepository empleadoRepository;

    @GetMapping
    public String reportes(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
        @RequestParam(required = false) String area,
        @RequestParam(required = false) String turno,
        @RequestParam(required = false) Integer idEmpleado,
        @RequestParam(required = false) String generado,
        Model model
    ) {
        model.addAttribute("areas", areaRepository.findAll());
        model.addAttribute("turnos", turnoRepository.findAll());
        model.addAttribute("empleados", empleadoRepository.findAll());

        model.addAttribute("filtroDesdStr", desde != null ? desde.toString() : "");
        model.addAttribute("filtroHastaStr", hasta != null ? hasta.toString() : "");
        model.addAttribute("filtroArea", area != null ? area : "");
        model.addAttribute("filtroTurno", turno != null ? turno : "");
        model.addAttribute("filtroEmpleado", idEmpleado);

        if (generado != null) {
            List<Asistencia> registros = asistenciaRepository.filtrarReporte(
                desde, hasta,
                (area != null && !area.isEmpty()) ? area : null,
                (turno != null && !turno.isEmpty()) ? turno : null,
                idEmpleado
            );

            long totalAsistencias = registros.stream().filter(a -> "Asistencia".equalsIgnoreCase(a.getEstado())).count();
            long totalTardanzas   = registros.stream().filter(a -> "Tardanza".equalsIgnoreCase(a.getEstado())).count();
            long totalFaltas      = registros.stream().filter(a -> "Falta".equalsIgnoreCase(a.getEstado())).count();
            long totalJustificadas = registros.stream().filter(a -> "Justificada".equalsIgnoreCase(a.getEstado())).count();

            // Agrupar por empleado para la tabla resumen
            Map<String, long[]> resumen = registros.stream().collect(
                Collectors.groupingBy(
                    a -> a.getEmpleado().getNombre() + " " + a.getEmpleado().getApellido(),
                    Collectors.collectingAndThen(Collectors.toList(), lista -> new long[]{
                        lista.stream().filter(x -> "Asistencia".equalsIgnoreCase(x.getEstado())).count(),
                        lista.stream().filter(x -> "Tardanza".equalsIgnoreCase(x.getEstado())).count(),
                        lista.stream().filter(x -> "Falta".equalsIgnoreCase(x.getEstado())).count()
                    })
                )
            );

            // Para la tabla detallada necesitamos el área y turno del empleado
            model.addAttribute("registros", registros);
            model.addAttribute("resumen", resumen);
            model.addAttribute("totalAsistencias", totalAsistencias);
            model.addAttribute("totalTardanzas", totalTardanzas);
            model.addAttribute("totalFaltas", totalFaltas);
            model.addAttribute("totalJustificadas", totalJustificadas);
            model.addAttribute("generado", true);
        }

        return "admin/reportes";
    }

    @GetMapping("/excel")
    public void exportarExcel(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
        @RequestParam(required = false) String area,
        @RequestParam(required = false) String turno,
        @RequestParam(required = false) Integer idEmpleado,
        HttpServletResponse response
    ) throws IOException {

        List<Asistencia> registros = asistenciaRepository.filtrarReporte(
            desde, hasta,
            (area != null && !area.isEmpty()) ? area : null,
            (turno != null && !turno.isEmpty()) ? turno : null,
            idEmpleado
        );

        String filename = "reporte-asistencia-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");

            // === ESTILOS ===
            // Título
            CellStyle tituloStyle = workbook.createCellStyle();
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 14);
            tituloStyle.setFont(tituloFont);
            tituloStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            tituloStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font tituloFontBlanco = workbook.createFont();
            tituloFontBlanco.setBold(true);
            tituloFontBlanco.setFontHeightInPoints((short) 14);
            tituloFontBlanco.setColor(IndexedColors.WHITE.getIndex());
            tituloStyle.setFont(tituloFontBlanco);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            // Header columnas
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Celda dato normal
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            // Badge estado
            CellStyle okStyle = workbook.createCellStyle();
            okStyle.cloneStyleFrom(dataStyle);
            Font okFont = workbook.createFont();
            okFont.setColor(IndexedColors.GREEN.getIndex());
            okFont.setBold(true);
            okStyle.setFont(okFont);

            CellStyle lateStyle = workbook.createCellStyle();
            lateStyle.cloneStyleFrom(dataStyle);
            Font lateFont = workbook.createFont();
            lateFont.setColor(IndexedColors.ORANGE.getIndex());
            lateFont.setBold(true);
            lateStyle.setFont(lateFont);

            CellStyle faltaStyle = workbook.createCellStyle();
            faltaStyle.cloneStyleFrom(dataStyle);
            Font faltaFont = workbook.createFont();
            faltaFont.setColor(IndexedColors.RED.getIndex());
            faltaFont.setBold(true);
            faltaStyle.setFont(faltaFont);

            // === FILA 0: TÍTULO ===
            Row titulo = sheet.createRow(0);
            titulo.setHeightInPoints(28);
            Cell tituloCell = titulo.createCell(0);
            tituloCell.setCellValue("NEXUS — Reporte de Asistencia");
            tituloCell.setCellStyle(tituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            // === FILA 1: subtítulo rango ===
            Row subtitulo = sheet.createRow(1);
            String rango = (desde != null ? desde.toString() : "—") + "  →  " + (hasta != null ? hasta.toString() : "—");
            Cell subCell = subtitulo.createCell(0);
            subCell.setCellValue("Período: " + rango + (area != null && !area.isEmpty() ? "   |   Área: " + area : "") + (turno != null && !turno.isEmpty() ? "   |   Turno: " + turno : ""));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

            // === FILA 2: TOTALES ===
            long totalAsistencias = registros.stream().filter(a -> "Asistencia".equalsIgnoreCase(a.getEstado())).count();
            long totalTardanzas   = registros.stream().filter(a -> "Tardanza".equalsIgnoreCase(a.getEstado())).count();
            long totalFaltas      = registros.stream().filter(a -> "Falta".equalsIgnoreCase(a.getEstado())).count();
            long totalJustificadas = registros.stream().filter(a -> "Justificada".equalsIgnoreCase(a.getEstado())).count();

            Row totalesRow = sheet.createRow(2);
            totalesRow.createCell(0).setCellValue("Total Asistencias: " + totalAsistencias);
            totalesRow.createCell(1).setCellValue("Tardanzas: " + totalTardanzas);
            totalesRow.createCell(2).setCellValue("Faltas: " + totalFaltas);
            totalesRow.createCell(3).setCellValue("Justificadas: " + totalJustificadas);

            // === FILA 3: vacía ===
            sheet.createRow(3);

            // === FILA 4: HEADERS tabla ===
            Row header = sheet.createRow(4);
            String[] cols = {"Empleado", "Área", "Turno", "Fecha", "Entrada", "Salida", "Estado"};
            for (int i = 0; i < cols.length; i++) {
                Cell c = header.createCell(i);
                c.setCellValue(cols[i]);
                c.setCellStyle(headerStyle);
            }

            // === FILAS DE DATOS ===
            int rowNum = 5;
            DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

            for (Asistencia a : registros) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(a.getEmpleado().getNombre() + " " + a.getEmpleado().getApellido());
                row.createCell(1).setCellValue(a.getEmpleado().getArea().getNombreArea());
                row.createCell(2).setCellValue(a.getEmpleado().getTurno().getNombreTurno());
                row.createCell(3).setCellValue(a.getFecha() != null ? a.getFecha().format(dateFmt) : "—");
                row.createCell(4).setCellValue(a.getHoraEntrada() != null ? a.getHoraEntrada().format(timeFmt) : "—");
                row.createCell(5).setCellValue(a.getHoraSalida()  != null ? a.getHoraSalida().format(timeFmt)  : "—");

                Cell estadoCell = row.createCell(6);
                estadoCell.setCellValue(a.getEstado() != null ? a.getEstado() : "—");
                String est = a.getEstado() != null ? a.getEstado().toLowerCase() : "";
                if (est.contains("asistencia")) estadoCell.setCellStyle(okStyle);
                else if (est.contains("tardanza")) estadoCell.setCellStyle(lateStyle);
                else if (est.contains("falta"))    estadoCell.setCellStyle(faltaStyle);
                else estadoCell.setCellStyle(dataStyle);

                // estilos celdas normales
                for (int i = 0; i <= 5; i++) row.getCell(i).setCellStyle(dataStyle);
            }

            // Autosize columnas
            for (int i = 0; i < 7; i++) sheet.autoSizeColumn(i);

            workbook.write(response.getOutputStream());
        }
    }
}
