import java.time.LocalDate;
import java.util.ArrayList;

public class Campania {
    private Integer idCampania;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private EstadoCampania estadoCampania;
    private PoblacionObjetivo pobObj;
    private ArrayList<StockVacuna> stockVacunas;

    public Campania(Integer idCampania, String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, EstadoCampania estadoCampania, PoblacionObjetivo pobObj, ArrayList<StockVacuna> stockVacunas) {
        this.idCampania = idCampania;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.estadoCampania = estadoCampania;
        this.pobObj = pobObj;
        this.stockVacunas = stockVacunas;
    }

    public Integer getIdCampania() {
        return idCampania;
    }
}
