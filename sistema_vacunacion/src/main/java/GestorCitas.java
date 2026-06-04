import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorCitas {

    private CampaniaRepo campaniaRepo;
    private CentrosRepo centrosRepo;
    private GestorNotificaciones gestorNotificaciones;

    public GestorCitas(CampaniaRepo campaniaRepo, CentrosRepo centrosRepo, GestorNotificaciones gestorNotificaciones) {
        this.campaniaRepo = campaniaRepo;
        this.centrosRepo = centrosRepo;
        this.gestorNotificaciones = gestorNotificaciones;
    }

    public Cita crearCita(Paciente paciente, LocalDateTime fecha_hora, Integer id_centro, Integer id_campania) {
        
        Campania camp = campaniaRepo.obtenerCampaniaPorId(id_campania);
        CentroVacunacion c = centrosRepo.obtenerCentroPorId(id_centro);
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
        Vacuna v= c.buscarVacuna(camp);
        Cita cita = new Cita(paciente, fs, fecha_hora, c, v);
        gestorNotificaciones.notificarCita(cita);

        return cita;
    }



}
