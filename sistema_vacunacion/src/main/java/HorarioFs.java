import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public boolean abarca(LocalDateTime fechaHora){
        DayOfWeek dia = fechaHora.getDayOfWeek();
        LocalTime hora = fechaHora.toLocalTime();
        return diaSemana.equals(dia) && !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }

    public void bloquear(){
        this.bloqueado = true;
    }

    public boolean estaDisponible(){
        return !bloqueado;
    }
}
