import java.time.LocalDateTime;

public class Cita {
    private LocalDateTime fecha_hora;
    private EstadoCita estado;
    private Paciente paciente;
    private FuncSalud funcionario;
    private CentroVacunacion centro;
    private Vacuna vacuna;
    private Campania campania;

    public Cita(Paciente paciente, FuncSalud funcionario, LocalDateTime fecha_hora,
                CentroVacunacion centro, Vacuna vacuna, Campania camp) {
        this.paciente = paciente;
        this.funcionario = funcionario;
        this.fecha_hora = fecha_hora;
        this.centro = centro;
        this.vacuna= vacuna;
        this.estado = EstadoCita.VIGENTE;
        this.campania = camp;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public FuncSalud getFuncionario() {
        return funcionario;
    }

    public CentroVacunacion getCentro() {
        return centro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public Campania getCampania() {
        return campania;
    }
}
