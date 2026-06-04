import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestorCitasTest {

    // Lunes 2026-06-01 a las 10:00
    private static final LocalDateTime LUNES_10 = LocalDateTime.of(2026, 6, 1, 10, 0);
    // Martes 2026-06-02 a las 10:00
    private static final LocalDateTime MARTES_10 = LocalDateTime.of(2026, 6, 2, 10, 0);

    // --- MÉTODOS AUXILIARES PARA PREPARAR DATOS ---

    private ArrayList<HorarioCentro> apertura(DayOfWeek... dias) {
        ArrayList<HorarioCentro> horarios = new ArrayList<>();
        for (DayOfWeek dia : dias) {
            horarios.add(new HorarioCentro(dia, LocalTime.of(9, 0), LocalTime.of(18, 0)));
        }
        return horarios;
    }

    // CORRECCIÓN: Adaptamos el constructor de FuncSalud para incluir los datos de Usuario
    private ArrayList<FuncSalud> funcionarioLunes() {
        ArrayList<HorarioFs> horariosFs = new ArrayList<>();
        // Asumo que tu clase HorarioFs se maneja con dia, apertura y cierre
        horariosFs.add(new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)));

        ArrayList<FuncSalud> funcionarios = new ArrayList<>();
        funcionarios.add(new FuncSalud(
                "22.222.222-2", "Ana", "Gómez", 912345678, "ana@mail.com",
                LocalDate.of(1985, 5, 20), horariosFs
        ));
        return funcionarios;
    }

    private GestorCitas gestorCon(CentroVacunacion centro, Campania campania) {
        ArrayList<CentroVacunacion> listaCentros = new ArrayList<>();
        if (centro != null) listaCentros.add(centro);
        CentrosRepo centrosRepo = new CentrosRepo(listaCentros);

        ArrayList<Campania> listaCampanias = new ArrayList<>();
        if (campania != null) listaCampanias.add(campania);
        CampaniaRepo campaniaRepo = new CampaniaRepo(listaCampanias);

        GestorNotificaciones notificacionesDummy = new GestorNotificaciones() {
            @Override
            public void notificarCita(Cita cita) { }
        };

        return new GestorCitas(campaniaRepo, centrosRepo, notificacionesDummy);
    }

    private Paciente crearPacienteDummy() {
        return new Paciente("11.111.111-1", "Juan", "Pérez", 987654321, "juan@mail.com", LocalDate.of(1990, 1, 1));
    }

    private Campania crearCampaniaDummy(Integer id) {
        return new Campania(
                id, "Campaña de Prueba", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31),
                "Descripción test", null, null, new ArrayList<>()
        );
    }

    // --- TESTS ---

    @Test
    void testCrearCita_CaminoFeliz() {
        CentroVacunacion centro = new CentroVacunacion(
                1, "Cesfam Central", "Primario",
                new ArrayList<>(), funcionarioLunes(), apertura(DayOfWeek.MONDAY)
        );
        GestorCitas gestor = gestorCon(centro, crearCampaniaDummy(10));

        Cita cita = gestor.crearCita(crearPacienteDummy(), LUNES_10, 1, 10);

        assertNotNull(cita, "Debe crear la cita");
        assertNotNull(cita.getCentro(), "Debe asociar el centro");
        assertNotNull(cita.getFuncionario(), "Debe asignar un funcionario disponible");
    }

    @Test
    void testCrearCita_CentroInexistente() {
        CentroVacunacion centro = new CentroVacunacion(
                1, "Cesfam Central", "Primario",
                new ArrayList<>(), funcionarioLunes(), apertura(DayOfWeek.MONDAY)
        );
        GestorCitas gestor = gestorCon(centro, crearCampaniaDummy(10));

        Cita cita = gestor.crearCita(crearPacienteDummy(), LUNES_10, 999, 10);
        assertNull(cita, "Centro inexistente -> no se crea la cita");
    }

    @Test
    void testCrearCita_CampaniaInexistente() {
        CentroVacunacion centro = new CentroVacunacion(
                1, "Cesfam Central", "Primario",
                new ArrayList<>(), funcionarioLunes(), apertura(DayOfWeek.MONDAY)
        );
        GestorCitas gestor = gestorCon(centro, crearCampaniaDummy(10));

        Cita cita = gestor.crearCita(crearPacienteDummy(), LUNES_10, 1, 999);
        assertNull(cita, "Campaña inexistente -> no se crea la cita");
    }

    @Test
    void testCrearCita_CentroCerrado() {
        CentroVacunacion centro = new CentroVacunacion(
                1, "Cesfam Central", "Primario",
                new ArrayList<>(), funcionarioLunes(), apertura(DayOfWeek.MONDAY)
        );
        GestorCitas gestor = gestorCon(centro, crearCampaniaDummy(10));

        Cita cita = gestor.crearCita(crearPacienteDummy(), LocalDateTime.of(2026, 6, 1, 20, 0), 1, 10);
        assertNull(cita, "Centro cerrado -> no se crea la cita");
    }

    @Test
    void testCrearCita_SinFuncionarioDisponible() {
        CentroVacunacion centro = new CentroVacunacion(
                1, "Cesfam Central", "Primario",
                new ArrayList<>(), funcionarioLunes(), apertura(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
        );
        GestorCitas gestor = gestorCon(centro, crearCampaniaDummy(10));

        Cita cita = gestor.crearCita(crearPacienteDummy(), MARTES_10, 1, 10);
        assertNull(cita, "Sin funcionario disponible -> no se crea la cita");
    }
}