import java.time.LocalDate;

public class Usuario {
    private String RUT;
    private String nombres;
    private String apellidos;
    private Integer fono;
    private String correoElectronico;
    private LocalDate fechaNacimiento;
    private NotificacionPreferencia preferencia;

    public Usuario(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento, NotificacionPreferencia preferencia) {
        this.RUT = RUT;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fono = fono;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.preferencia = preferencia;
    }

    public NotificacionPreferencia getPreferencia() {
        return preferencia;
    }

    public String getNombres() {
        return nombres;
    }
    public String getApellidos() {return apellidos;}

    public Integer getFono() {return fono;}
    public String getCorreoElectronico() {return correoElectronico;}


}
