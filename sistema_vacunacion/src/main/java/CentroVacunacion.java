import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CentroVacunacion {
    private Integer id_centro;
    private String nombre;
    private String tipo;

    public int getId_centro() {
        return id_centro;
    }
    private ArrayList<Vacuna> vacunas;
    private ArrayList<FuncSalud> funcSalud;
    private ArrayList<HorarioCentro> horarios;

    public CentroVacunacion(ArrayList<HorarioCentro> horarios) {
        this.horarios = horarios;
    }

    public boolean estaAbierto(LocalDateTime fechaHora){
        DayOfWeek dia= fechaHora.getDayOfWeek();
        LocalTime hora = fechaHora.toLocalTime();
        for(HorarioCentro horarioCentro: horarios){
            if(horarioCentro.getDiaSemana().equals(dia) && !hora.isBefore(horarioCentro.getHoraApertura())
            && !hora.isAfter(horarioCentro.getHoraCierre())){
                return true;
            }
        }
      return false;
    };

}
