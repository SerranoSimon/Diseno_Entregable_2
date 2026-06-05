import java.time.LocalDateTime;

public class ValidadorCita {
    private FuncSalud funcSalud;
    private Vacuna vacuna;
    private Campania campania;
    private CentroVacunacion centroVacunacion;
    private CampaniaRepo campaniaRepo;
    private CentrosRepo centrosRepo;

    public ValidadorCita( CampaniaRepo campaniaRepo, CentrosRepo centrosRepo) {
        this.funcSalud = null;
        this.vacuna = null;
        this.campania = null;
        this.campaniaRepo = campaniaRepo;
        this.centrosRepo = centrosRepo;
    }

    public FuncSalud getFuncSalud() {
        return funcSalud;
    }

    public Campania getCampania() {
        return campania;
    }

    public CentroVacunacion getCentroVacunacion() {
        return centroVacunacion;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public boolean validarCita(LocalDateTime fecha_hora, Integer id_centro, Integer id_campania){

        Campania camp = campaniaRepo.obtenerCampaniaPorId(id_campania);
        CentroVacunacion c = centrosRepo.obtenerCentroPorId(id_centro);
        if (c == null || camp == null) {
            return false;
        }
        campania = camp;
        centroVacunacion = c;
        if (!c.estaAbierto(fecha_hora)) {
            return false;
        }
        FuncSalud fs = c.buscarFsParaCita(fecha_hora);
        if (fs == null) {
            return false;
        }
        funcSalud = fs;
        Vacuna v= c.buscarVacuna(camp);
        if(v == null){
            return  false;
        }

        vacuna=v;
        return true;
    }

}
