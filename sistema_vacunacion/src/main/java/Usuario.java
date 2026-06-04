import java.time.LocalDate;

public class Usuario {
    private String RUT;
    private String nombres;
    private String apellidos;
    private Integer fono;
    private String correoElectronico;
    private LocalDate fechaNacimiento;

    public Usuario(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento) {
        this.RUT = RUT;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fono = fono;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
    }
}
