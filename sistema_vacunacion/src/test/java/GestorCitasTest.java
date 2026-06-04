import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorCitasTest {

    // Lunes 2026-06-01 a las 10:00
    private static final LocalDateTime LUNES_10 = LocalDateTime.of(2026, 6, 1, 10, 0);
    // Martes 2026-06-02 a las 10:00
    private static final LocalDateTime MARTES_10 = LocalDateTime.of(2026, 6, 2, 10, 0);

    private ArrayList<HorarioCentro> apertura(DayOfWeek... dias) {
        ArrayList<HorarioCentro> horarios = new ArrayList<>();
        for (DayOfWeek dia : dias) {
            horarios.add(new HorarioCentro(dia, LocalTime.of(9, 0), LocalTime.of(18, 0)));
        }
        return horarios;
    }

    // Un funcionario que atiende solo los Lunes de 09:00 a 18:00
    private ArrayList<FuncSalud> funcionarioLunes() {
        ArrayList<HorarioFs> horariosFs = new ArrayList<>();
        horariosFs.add(new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)));
        ArrayList<FuncSalud> funcionarios = new ArrayList<>();
        funcionarios.add(new FuncSalud(horariosFs));
        return funcionarios;
    }

    private GestorCitas gestorCon(CentroVacunacion centro, Campania campania) {
        List<CentroVacunacion> centros = new ArrayList<>();
        if (centro != null) centros.add(centro);
        List<Campania> campanias = new ArrayList<>();
        if (campania != null) campanias.add(campania);
        return new GestorCitas(centros, campanias);
    }

    @Test
    void testCrearCita_CaminoFeliz() {
        CentroVacunacion centro = new CentroVacunacion(1, apertura(DayOfWeek.MONDAY), funcionarioLunes());
        GestorCitas gestor = gestorCon(centro, new Campania(10));

        Cita cita = gestor.crearCita(new Paciente(), LUNES_10, 1, 10);

        assertNotNull(cita, "Debe crear la cita");
        assertEquals(EstadoCita.VIGENTE, cita.getEstado(), "Una cita nueva debe quedar VIGENTE");
        assertNotNull(cita.getCentro(), "Debe asociar el centro");
        assertNotNull(cita.getCampania(), "Debe asociar la campaña");
        assertNotNull(cita.getFuncionario(), "Debe asignar un funcionario disponible");
    }

    @Test
    void testCrearCita_CentroInexistente() {
        CentroVacunacion centro = new CentroVacunacion(1, apertura(DayOfWeek.MONDAY), funcionarioLunes());
        GestorCitas gestor = gestorCon(centro, new Campania(10));

        // Pedimos un id_centro que no existe -> no debe crear y no debe lanzar NPE
        Cita cita = gestor.crearCita(new Paciente(), LUNES_10, 999, 10);
        assertNull(cita, "Centro inexistente -> no se crea la cita");
    }

    @Test
    void testCrearCita_CampaniaInexistente() {
        CentroVacunacion centro = new CentroVacunacion(1, apertura(DayOfWeek.MONDAY), funcionarioLunes());
        GestorCitas gestor = gestorCon(centro, new Campania(10));

        Cita cita = gestor.crearCita(new Paciente(), LUNES_10, 1, 999);
        assertNull(cita, "Campaña inexistente -> no se crea la cita");
    }

    @Test
    void testCrearCita_CentroCerrado() {
        CentroVacunacion centro = new CentroVacunacion(1, apertura(DayOfWeek.MONDAY), funcionarioLunes());
        GestorCitas gestor = gestorCon(centro, new Campania(10));

        // Lunes pero a las 20:00 -> el centro ya está cerrado
        Cita cita = gestor.crearCita(new Paciente(), LocalDateTime.of(2026, 6, 1, 20, 0), 1, 10);
        assertNull(cita, "Centro cerrado -> no se crea la cita");
    }

    @Test
    void testCrearCita_SinFuncionarioDisponible() {
        // El centro abre Lunes y Martes, pero el funcionario solo atiende los Lunes
        CentroVacunacion centro = new CentroVacunacion(1, apertura(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), funcionarioLunes());
        GestorCitas gestor = gestorCon(centro, new Campania(10));

        // Martes: el centro está abierto, pero ningún funcionario está disponible
        Cita cita = gestor.crearCita(new Paciente(), MARTES_10, 1, 10);
        assertNull(cita, "Sin funcionario disponible -> no se crea la cita");
    }
}
