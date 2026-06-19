package com.asistencia.sistema_asistencia.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "dni", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // =========================
    // RELACION MUCHOS A UNO
    // AREA
    // =========================
    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

    // =========================
    // RELACION MUCHOS A UNO
    // CARGO
    // =========================
    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;

    // =========================
    // RELACION MUCHOS A UNO
    // TURNO
    // =========================
    @ManyToOne
    @JoinColumn(name = "id_turno", nullable = false)
    private Turno turno;

    // =========================
    // RELACION 1:1 CON USUARIO
    // =========================
    @OneToOne(mappedBy = "empleado")
    private Usuario usuario;

    // =========================
    // RELACION 1:N CON ASISTENCIA
    // =========================
    @JsonIgnore
    @OneToMany(mappedBy = "empleado")
    private List<Asistencia> asistencias;

    // =========================
    // RELACION 1:N CON JUSTIFICACION
    // =========================
    @JsonIgnore
    @OneToMany(mappedBy = "empleado")
    private List<Justificacion> justificaciones;

    // =========================
    // GETTERS Y SETTERS
    // =========================

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public List<Justificacion> getJustificaciones() {
        return justificaciones;
    }

    public void setJustificaciones(List<Justificacion> justificaciones) {
        this.justificaciones = justificaciones;
    }
}