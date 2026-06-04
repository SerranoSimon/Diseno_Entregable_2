import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CentroVacunacion {
    private Integer id_centro;
    private String nombre;
    private String tipo;
    private ArrayList<StockVacuna> stockVacunas;
    private ArrayList<FuncSalud> funcSalud;
    private ArrayList<HorarioCentro> horarios;

    public CentroVacunacion(Integer id_centro, String nombre, String tipo, ArrayList<StockVacuna> stockVacunas, ArrayList<FuncSalud> funcSalud, ArrayList<HorarioCentro> horarios) {
        this.id_centro = id_centro;
        this.nombre = nombre;
        this.tipo = tipo;
        this.stockVacunas = stockVacunas;
        this.funcSalud = funcSalud;
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

    public Vacuna buscarVacuna(Campania camp){
            for(StockVacuna sv: stockVacunas){
                if(sv.vacunaEsDeCampania(camp) && sv.verificarStock()){
                    return sv.reservar();
                }
            }
            return null;
    };

}
