import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Paciente extends Usuario {
    private ArrayList<Cita> citas;
    public Paciente(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento) {
        super(RUT, nombres, apellidos, fono, correoElectronico, fechaNacimiento);
    }
    public void solicitarCita(Campania camp, LocalDateTime fecha_hora, CentroVacunacion centro){};
}
