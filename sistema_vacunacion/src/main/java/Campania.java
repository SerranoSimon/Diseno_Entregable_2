import java.time.LocalDate;

public class Campania {
    private Integer idCampania;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private EstadoCampania estadoCampania;
    private PoblacionObjetivo pobObj;

    public Integer getIdCampania() {
        return idCampania;
    }
}
