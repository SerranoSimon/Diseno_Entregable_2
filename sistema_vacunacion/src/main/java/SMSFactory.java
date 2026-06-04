public class SMSFactory extends NotificacionFactory {
    @Override
    void crearNotificacion() {
        Notificacion sms = new SMS();
        sms.enviarMensaje("Aqui debe ir el mensaje que se envia al paciente o funcionario");

    }
}
