import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Paciente extends Usuario implements Notificable {
    private ArrayList<Cita> citas;
    private NotificacionPreferencia preferencia;
    public Paciente(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento, NotificacionPreferencia preferencia) {
        super(RUT, nombres, apellidos, fono, correoElectronico, fechaNacimiento, preferencia);
        this.citas= new ArrayList<>();
        this.preferencia = preferencia;
    }
    public void solicitarCita(Integer id_camp, Integer id_centro, LocalDateTime fecha_hora, GestorCitas gestorCitas){
       Cita cita= gestorCitas.crearCita(this,fecha_hora,id_centro,id_camp);
       citas.add(cita);
    };

    @Override
    public NotificacionPreferencia getNotificacionPreferencia() {
        return preferencia;
    }

    @Override
    public String getMensajeCita(Cita cita) {
        String mensaje = "Estimado Paciente.\n" +
                "Usted " +
                cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos() +
                " tiene una cita para vacunarse contra " + cita.getCampania().getNombre() +
                ".\n Se le aplicará la vacuna "+ cita.getVacuna().getNombre() + " ,en el centro " + cita.getCentro().getNombre() +
                ".\nUbicado en " + cita.getCentro().getDireccion() +
                ".\nEl horario a asistir es " + cita.getFecha_hora() +
                " y será atendido por " + cita.getFuncionario().getNombres() + " " + cita.getFuncionario().getApellidos() +
                ".\nEsperamos su asistencia y puntualidad.";
        return mensaje;
    }

}
