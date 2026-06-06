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

    // Verifica que una vacuna sirva para cierta campaña
    public boolean vacunaEsDeCampania(Campania camp){
        if(this.campania.equals(camp)) return true;
        else return false;
    }
    // Verifica que existan vacunas disponibles
    public boolean verificarStock(){
        if(cantidadDisponible>0) return true;
        else return false;
    }
    // Reserva una vacuna
    public Vacuna reservar(){
        cantidadReservada++;
        cantidadDisponible--;
        return vacuna;
    }
}
