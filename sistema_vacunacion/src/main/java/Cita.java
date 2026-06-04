import java.time.LocalDateTime;

public class Cita {
    private LocalDateTime fecha_hora;
    private EstadoCita estado;
    private Paciente paciente;
    private FuncSalud funcionario;
    private CentroVacunacion centro;
    private Campania campania;

    public Cita(Paciente paciente, FuncSalud funcionario, LocalDateTime fecha_hora,
                CentroVacunacion centro, Campania campania) {
        this.paciente = paciente;
        this.funcionario = funcionario;
        this.fecha_hora = fecha_hora;
        this.centro = centro;
        this.campania = campania;
        this.estado = EstadoCita.VIGENTE;
    }
}
