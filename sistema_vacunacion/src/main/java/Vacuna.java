public class Vacuna {
    private Integer id_vacuna;
    private String nombre;
    private Campania campania;

    public Vacuna(Integer id_vacuna, String nombre, Campania campania) {
        this.id_vacuna = id_vacuna;
        this.nombre = nombre;
        this.campania = campania;
    }

    public int getId_vacuna() {
        return id_vacuna;
    }

    public String getNombre() {
        return nombre;
    }


    public Campania getCampania() {
        return campania;
    }

}
