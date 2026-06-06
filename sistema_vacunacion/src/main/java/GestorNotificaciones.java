
// Clase experta en gestionar la forma en que se notifica.
public class GestorNotificaciones {

    public void notificarCita(Cita cita) {
        notificarDestinatario(cita.getPaciente(), cita);
        notificarDestinatario(cita.getFuncionario(), cita);
    }
    private void notificarDestinatario(Notificable destinatario, Cita cita) {
        NotificacionPreferencia preferencia = destinatario.getNotificacionPreferencia();
        NotificacionFactory factory = getFactory(preferencia);
        String mensaje = destinatario.getMensajeCita(cita);
        Notificacion notificacion = factory.crearNotificacion();
        notificacion.enviarMensaje(mensaje);
    }
    private NotificacionFactory getFactory(NotificacionPreferencia pref) {
        return switch (pref) {
            case SMS -> new SMSFactory();
            case CORREOELECTRONICO -> new CorreoFactory();
            case AMBOS -> new NotificacionDobleFactory();
        };
    }
}
