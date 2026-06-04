import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorCitas {

    private List<CentroVacunacion> centros = new ArrayList<>();
    private List<Campania> campanias = new ArrayList<>();
    private GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();

    public GestorCitas(List<CentroVacunacion> centros, List<Campania> campanias) {
        this.centros = centros;
        this.campanias = campanias;
    }

    public Cita crearCita(Paciente paciente, LocalDateTime fecha_hora, int id_centro, int id_campania) {
        
        Campania camp = buscarCampania(id_campania);
        CentroVacunacion c = buscarCentro(id_centro);
        if (c == null || camp == null) {
            return null;
        }
        if (!c.estaAbierto(fecha_hora)) {
            return null;
        }
        FuncSalud fs = c.buscarFsParaCita(fecha_hora);
        if (fs == null) {
            return null;
        }
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
