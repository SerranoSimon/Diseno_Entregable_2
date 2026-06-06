import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
// Clase que representa un bloque de horario de un funcionario
public class HorarioFs {
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean bloqueado;

    public HorarioFs(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFin){
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    // revisamos si el bloque abarca la hora que se pidio.
    public boolean abarca(LocalDateTime fechaHora){
        DayOfWeek dia = fechaHora.getDayOfWeek();
        LocalTime hora = fechaHora.toLocalTime();
        return diaSemana.equals(dia) && !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }
    // bloquea el bloque horario
    public void bloquear(){
        this.bloqueado = true;
    }
   // devuelve si el bloque está o no disponible
    public boolean estaDisponible(){
        return !bloqueado;
    }
}
