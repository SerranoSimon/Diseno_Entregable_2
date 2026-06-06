import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FuncSalud extends Usuario implements Notificable{
    private NotificacionPreferencia preferencia;
    private ArrayList<HorarioFs> horarios;
    
    public FuncSalud(String RUT, String nombres, String apellidos, Integer fono, String correoElectronico, LocalDate fechaNacimiento, ArrayList<HorarioFs> horarios, NotificacionPreferencia preferencia) {
        super(RUT, nombres, apellidos, fono, correoElectronico, fechaNacimiento, preferencia);
        this.horarios = horarios;
        this.preferencia = preferencia;
    }
     // Busca entre los horarios del funcionario para ver si puede atender en cierta fecha_hora,
    // si es que puede, lo bloquea (será asignado a la cita que se creará).
    public boolean disponible(LocalDateTime fechaHora){
        for(HorarioFs h: horarios){
            if(h.abarca(fechaHora) && h.estaDisponible()){
                h.bloquear();
                return true;
            }
        }
        return false;
    }

    @Override
    public NotificacionPreferencia getNotificacionPreferencia() {
        return preferencia;
    }

    @Override
    public String getMensajeCita(Cita cita) {
        String mensaje = "Estimado Funcionario.\n" +
                "Usted " +
                cita.getFuncionario().getNombres() + " " + cita.getFuncionario().getApellidos() +
                ". Tiene que aplicar una vacuna " + cita.getVacuna().getNombre() + " ,en el centro " + cita.getCentro().getNombre() +
                ".\nUbicacado en " + cita.getCentro().getDireccion() +
                ".\nEl horario de vacunación es " + cita.getFecha_hora() +
                " y atenderá a " + cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos();
        return mensaje;
    }
}
