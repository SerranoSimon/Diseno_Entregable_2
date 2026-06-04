import java.time.DayOfWeek;
import java.time.LocalTime;
public class HorarioCentro {
    private DayOfWeek diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public HorarioCentro(DayOfWeek diaSemana, LocalTime horaApertura, LocalTime horaCierre) {
        this.diaSemana = diaSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }
}
