import java.time.LocalDate;

public class FuncSalud extends Usuario{
    public FuncSalud(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento) {
        super(RUT, nombres, apellidos, fono, correoElectronico, fechaNacimiento);
    }
}
