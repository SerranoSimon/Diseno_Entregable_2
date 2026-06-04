import java.util.ArrayList;

public class CentrosRepo {
    private ArrayList<CentroVacunacion> centros;

    public CentrosRepo(ArrayList<CentroVacunacion> centros) {
        this.centros = centros;
    }
    public CentroVacunacion obtenerCentroPorId(Integer id) {
        for (CentroVacunacion c : centros) {
            if (c.getId_centro().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
