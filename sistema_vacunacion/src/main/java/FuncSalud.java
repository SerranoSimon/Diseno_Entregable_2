import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FuncSalud extends Usuario{
    private ArrayList<HorarioFs> horarios;

    public FuncSalud(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento, ArrayList<HorarioFs> horarios, CanalNoti preferencia) {
        super(RUT, nombres, apellidos, fono, correoElectronico, fechaNacimiento, preferencia);
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
