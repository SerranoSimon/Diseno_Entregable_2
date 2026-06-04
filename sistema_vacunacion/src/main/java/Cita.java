import java.time.LocalDateTime;

public class Cita {
    private LocalDateTime fecha_hora;
    private Paciente paciente;
    private FuncSalud funcSalud;
    private Vacuna vacuna;
    private EstadoCita estado;

    public Cita(LocalDateTime fecha_hora, Paciente paciente, FuncSalud funcSalud, Vacuna vacuna) {
        this.fecha_hora = fecha_hora;
        this.paciente = paciente;
        this.funcSalud = funcSalud;
        this.vacuna = vacuna;
        this.estado = EstadoCita.VIGENTE;
    }
}
