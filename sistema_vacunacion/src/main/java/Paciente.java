import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Paciente extends Usuario {
    private ArrayList<Cita> citas;
    public Paciente(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento) {
        super(RUT, nombres, apellidos, fono, correoElectronico, fechaNacimiento);
        citas= new ArrayList<>();
    }
    public void solicitarCita(Integer id_camp, Integer id_centro, LocalDateTime fecha_hora, GestorCitas gestorCitas){
       Cita cita= gestorCitas.crearCita(this,fecha_hora,id_centro,id_camp);
       citas.add(cita);
    };
}
