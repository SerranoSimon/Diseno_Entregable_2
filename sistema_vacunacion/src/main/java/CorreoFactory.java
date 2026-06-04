public class CorreoFactory extends NotificacionFactory{
    void crearNotificacion(){
        CorreoElectronico correoElectronico = new CorreoElectronico();
        correoElectronico.enviarMensaje("Aqui debe ir el mensaje que se envia al paciente o funcionario");
    }
}
