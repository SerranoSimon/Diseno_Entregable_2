import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorCitas {

    private List<CentroVacunacion> centros = new ArrayList<>();
    private List<Campania> campanias = new ArrayList<>();
    private GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();

    public Cita crearCita(Paciente paciente, LocalDateTime fecha_hora, int id_centro, int id_campania) {
        
        Campania camp = buscarCampania(id_campania);
        CentroVacunacion c = buscarCentro(id_centro);
        FuncSalud fs = c.buscarFsParaCita(fecha_hora);
        Cita cita = new Cita(paciente, fs, fecha_hora, c, camp);
        gestorNotificaciones.notificarCita(cita);

        return cita;
    }

    private Campania buscarCampania(int id_campania) {
        for (Campania camp : campanias) {
            if (camp.getIdCampania() == id_campania) {
                return camp;
            }
        }
        return null;
    }

    private CentroVacunacion buscarCentro(int id_centro) {
        for (CentroVacunacion c : centros) {
            if (c.getId_centro() == id_centro) {
                return c;
            }
        }
        return null;
    }
}
