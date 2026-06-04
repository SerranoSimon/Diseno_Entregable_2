public class GestorNotificaciones {

    public void notificarCita(Cita cita) {
        CanalNoti preferencia = cita.getPaciente().getPreferencia();
        String mensaje=null;
        //Creo que esto deberia ir en otra parte
        switch (preferencia){
            case SMS:
                NotificacionFactory smsFactory = new SMSFactory();
                mensaje = smsFactory.crearMensajePaciente(cita);

                break;
            case CORREOELECTRONICO:
                NotificacionFactory correoFactory = new CorreoFactory();
                mensaje = correoFactory.crearMensajePaciente(cita);

                break;
            case AMBOS:
                NotificacionFactory smsFactory2 = new SMSFactory();
                NotificacionFactory correoFactory2 = new CorreoFactory();
                break;
        }
    }
}
