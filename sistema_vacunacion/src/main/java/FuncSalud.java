import java.time.LocalDateTime;
import java.util.ArrayList;

public class FuncSalud extends Usuario{
    private ArrayList<HorarioFs> horarios = new ArrayList<>();

    public FuncSalud(ArrayList<HorarioFs> horarios){
        this.horarios = horarios;
    }

    public boolean disponible(LocalDateTime fechaHora){
        for(HorarioFs h: horarios){
            if(h.abarca(fechaHora) && h.estaDisponible()){
                h.bloquear();
                return true;
            }
        }
        return false;
    }
}
