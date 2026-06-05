public class StockVacuna {
    private Vacuna vacuna;
    private Integer cantidadDisponible;
    private Integer cantidadReservada;
    private Campania campania;

    public StockVacuna(Vacuna vacuna, Integer cantidad, Campania campania) {
        this.vacuna = vacuna;
        this.cantidadDisponible = cantidad;
        this.cantidadReservada = 0;
        this.campania= campania;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }


    public boolean vacunaEsDeCampania(Campania camp){
        if(this.campania.equals(camp)) return true;
        else return false;
    }
    public boolean verificarStock(){
        if(cantidadDisponible>0) return true;
        else return false;
    }
    public Vacuna reservar(){
        cantidadReservada++;
        cantidadDisponible--;
        return vacuna;
    }
}
