import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
// Clase creadora de citas
public class GestorCitas {


    private GestorNotificaciones gestorNotificaciones;
    private ValidadorCita validadorCita;
    private ArrayList<Cita> citasCreadas;
    public GestorCitas(GestorNotificaciones gestorNotificaciones, ValidadorCita validadorCita) {
        this.gestorNotificaciones = gestorNotificaciones;
        this.validadorCita = validadorCita;
        this.citasCreadas = new ArrayList<>();
    }
    // Crear cita usa al experto ValidarCita para ver si es posible crearla.
    public Cita crearCita(Paciente paciente, LocalDateTime fecha_hora, Integer id_centro, Integer id_campania) {
        if(validadorCita.validarCita(fecha_hora,id_centro,id_campania)){
            FuncSalud fs = validadorCita.getFuncSalud();
            CentroVacunacion centro = validadorCita.getCentroVacunacion();
            Vacuna v = validadorCita.getVacuna();
            Campania campania = validadorCita.getCampania();
            Cita cita = new Cita(paciente, fs, fecha_hora, centro, v,campania);
            gestorNotificaciones.notificarCita(cita);
            citasCreadas.add(cita);
            return cita;
        } else{
            System.out.println("La cita no se puede llevar acabo");
            return null;
        }

    }



}
