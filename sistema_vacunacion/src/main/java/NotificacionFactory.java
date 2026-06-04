public abstract class NotificacionFactory {
    StringBuilder mensaje=new StringBuilder();
    abstract void crearNotificacion();
    public String crearMensajePaciente(Cita cita){
        mensaje.append("Estimado Usuario.\n");
        mensaje.append("Usted ");
        mensaje.append(cita.getPaciente().getNombres()).append(" ").append(cita.getPaciente().getApellidos());
        mensaje.append(". Tiene una cita para vacunarse de ").append(cita.getVacuna()).append(" ,en el centro ").append(cita.getCentro());
        mensaje.append(".\nEl horario a asistir es ").append(cita.getFecha_hora());
        mensaje.append(" y será atendido por ").append(cita.getFuncionario().getNombres()).append(" ").append(cita.getFuncionario().getApellidos());
        mensaje.append(".\nEsperamos su asistencia y puntualidad.");
        return mensaje.toString();
    }
    public String crearMensajeFuncionario(Cita cita){
        mensaje.append("Estimado Funcionario.\n");
        mensaje.append("Usted ");
        mensaje.append(cita.getFuncionario().getNombres()).append(" ").append(cita.getFuncionario().getApellidos());
        mensaje.append(". Tiene que vacunar de ").append(cita.getVacuna()).append(" ,en el centro ").append(cita.getCentro());
        mensaje.append(".\nEl horario de vacunación es ").append(cita.getFecha_hora());
        mensaje.append(" y atenderá a ").append(cita.getPaciente().getNombres()).append(" ").append(cita.getPaciente().getApellidos());
        mensaje.append(".\nDe antemano muchas gracias.");
        return mensaje.toString();
    }
}
