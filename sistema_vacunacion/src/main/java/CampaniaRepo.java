import java.util.ArrayList;

public class CampaniaRepo {
    private ArrayList<Campania> campanias;

    public CampaniaRepo(ArrayList<Campania> campanias) {
        this.campanias = campanias;
    }
    public Campania obtenerCampaniaPorId(Integer id){
        for(Campania c: campanias){
            if(c.getIdCampania().equals(id)){
                return c;
            }
        }
        return null;
    }
}
