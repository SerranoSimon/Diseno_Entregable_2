public class Vacuna {
    private Integer id_vacuna;
    private String nombre;

    public Vacuna(Integer id_vacuna, String nombre) {
        this.id_vacuna = id_vacuna;
        this.nombre = nombre;

    }

    public int getId_vacuna() {
        return id_vacuna;
    }

    public String getNombre() {
        return nombre;
    }


}
