public class StockVacuna {
    private Vacuna vacuna;
    private Integer cantidadDisponible;
    private Integer cantidadReservada;

    public StockVacuna(Vacuna vacuna, Integer cantidad) {
        this.vacuna = vacuna;
        this.cantidadDisponible = cantidad;
        this.cantidadReservada = 0;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }


    public boolean vacunaEsDeCampania(Campania camp){
        if(vacuna.getCampania().equals(camp)) return true;
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
