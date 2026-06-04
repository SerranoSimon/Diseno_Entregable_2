import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuscarVacunaCentroTest{

    private Campania campaniaCovid;
    private Campania campaniaOtra;
    private Vacuna pfizer;
    private Vacuna astrazeneca;

    @BeforeEach
    void setUp() {
        // Asumimos que Campania tiene un constructor con el nombre


        campaniaCovid = new Campania(1,"Covid 19", null,null,null,null,null, null);
        campaniaOtra= new Campania(2, "Influenza", null,null,null,null,null, null);
        // Asumimos que Vacuna tiene un constructor con nombre y campaña
        pfizer = new Vacuna(null,"Pfizer", campaniaCovid);
        astrazeneca = new Vacuna(null,"AstraZeneca", campaniaCovid);
    }

    @Test
    void testBuscarVacuna_RetornaPfizerSiHayStock() {
        // Preparación: Pfizer tiene 10 dosis, AstraZeneca tiene 5
        ArrayList<StockVacuna> inventario = new ArrayList<>();
        inventario.add(new StockVacuna(pfizer, 10));
        inventario.add(new StockVacuna(astrazeneca, 5));

        CentroVacunacion centro = new CentroVacunacion(null,null,null,inventario,null,null);

        // Ejecución
        Vacuna vacunaAsignada = centro.buscarVacuna(campaniaCovid);

        // Verificación: Debería darnos Pfizer porque es la primera en la lista con stock
        assertNotNull(vacunaAsignada, "Debe retornar una vacuna, no null");
        assertEquals(pfizer, vacunaAsignada, "La vacuna asignada debería ser Pfizer");

        // Verificamos que se descontó el stock de Pfizer correctamente (quedan 9)
        assertEquals(9, inventario.get(0).getCantidadDisponible(), "El stock de Pfizer debió bajar a 9");
    }

    @Test
    void testBuscarVacuna_RetornaAstrazenecaSiPfizerNoTieneStock() {
        // Preparación: Pfizer tiene 0 dosis, AstraZeneca tiene 5
        ArrayList<StockVacuna> inventario = new ArrayList<>();
        inventario.add(new StockVacuna(pfizer, 0));
        inventario.add(new StockVacuna(astrazeneca, 5));

        CentroVacunacion centro = new CentroVacunacion(null,null,null,inventario,null,null);

        // Ejecución
        Vacuna vacunaAsignada = centro.buscarVacuna(campaniaCovid);

        // Verificación: Se salta Pfizer (por no tener stock) y nos da AstraZeneca
        assertNotNull(vacunaAsignada, "Debe retornar una vacuna, no null");
        assertEquals(astrazeneca, vacunaAsignada, "La vacuna asignada debería ser AstraZeneca");
    }

    @Test
    void testBuscarVacuna_RetornaNullSiNoHayStockDeNinguna() {
        // Preparación: Ambas vacunas de Covid tienen 0 stock
        ArrayList<StockVacuna> inventario = new ArrayList<>();
        inventario.add(new StockVacuna(pfizer, 0));
        inventario.add(new StockVacuna(astrazeneca, 0));

        CentroVacunacion centro = new CentroVacunacion(null,null,null,inventario,null,null);

        // Ejecución
        Vacuna vacunaAsignada = centro.buscarVacuna(campaniaCovid);

        // Verificación
        assertNull(vacunaAsignada, "Debe retornar null porque no hay stock de ninguna vacuna para esa campaña");
    }

    @Test
    void testBuscarVacuna_RetornaNullSiEsOtraCampania() {
        // Preparación: Hay vacunas de Covid, pero vamos a buscar de otra campaña (Influenza)
        ArrayList<StockVacuna> inventario = new ArrayList<>();
        inventario.add(new StockVacuna(pfizer, 10));

        CentroVacunacion centro = new CentroVacunacion(null,null,null,inventario,null,null);

        // Ejecución
        Vacuna vacunaAsignada = centro.buscarVacuna(campaniaOtra);

        // Verificación
        assertNull(vacunaAsignada, "Debe retornar null porque el centro no tiene stock de la campaña solicitada");
    }
}