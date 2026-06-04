import java.time.LocalDateTime;

public class GestorCitas {

    public Cita crearCita(Paciente p, FuncSalud fs, LocalDateTime fecha_hora, Vacuna v){
        return new Cita(fecha_hora,p,fs,v);
    };
}
