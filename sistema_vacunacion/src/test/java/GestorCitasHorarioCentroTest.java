import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorCitasHorarioCentroTest {

    private GestorCitas gestorCitas;
    private Paciente paciente;
    private final Integer ID_CAMPANIA = 1;
    private final Integer ID_CENTRO = 1;

    @BeforeEach
    public void setUp() {
        // Crear Paciente
        paciente = new Paciente(
                "22.121.545-1",
                "Simon Pablo",
                "Serrano Luarte",
                98475930,
                "bersiclamar@gmail.com",
                LocalDate.of(1990, 1, 1),
                NotificacionPreferencia.AMBOS
        );

        // Crear Campaña y su repositorio
        Campania campania = new Campania(
                ID_CAMPANIA,
                "Vacunación Influenza 2026",
                LocalDate.of(2026, 5, 1),
                LocalDate.of(2026, 7, 1),
                "Campaña anual",
                EstadoCampania.EN_CURSO,
                new PoblacionObjetivo()
        );
        ArrayList<Campania> listaCampanias = new ArrayList<>();
        listaCampanias.add(campania);
        CampaniaRepo campaniaRepo = new CampaniaRepo(listaCampanias);

        // Se crea el/ centro de Vacunación con el horario de Lunes de 09:00 a 18:00)
        CentroVacunacion centro = new CentroVacunacion(
                ID_CENTRO,
                "Centro Mall del Centro CCP",
                "Movil",
                "Barros Arana 1068 CCP"
        );
        HorarioCentro horarioLunes = new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));
        centro.agregarHorario(horarioLunes);
        ArrayList<CentroVacunacion> listaCentros = new ArrayList<>();
        listaCentros.add(centro);
        CentrosRepo centrosRepo = new CentrosRepo(listaCentros);
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        gestorCitas = new GestorCitas(gestorNotificaciones, validadorCita);
    }

    @Test
    public void testCrearCita_CuandoCentroEstaCerradoPorDia_DebeRetornarNull() {
        // La fecha cae día DOMINGO y el centro solo abre los LUNES
        LocalDateTime fechaDomingo = LocalDateTime.of(2026, 6, 7, 12, 0); // 7 de Junio de 2026 es Domingo
        Cita citaObtenida = gestorCitas.crearCita(paciente, fechaDomingo, ID_CENTRO, ID_CAMPANIA);
        assertNull(citaObtenida, "La cita debería ser null porque el centro está cerrado los domingos.");
    }

    @Test
    public void testCrearCita_CuandoCentroEstaCerradoPorHora_DebeRetornarNull() {
        // La fecha es LUNES pero a las 08:00 AM y el centro abre a las 09:00 AM
        LocalDateTime fechaLunesTemprano = LocalDateTime.of(2026, 6, 8, 8, 0); // 8 de Junio de 2026 es Lunes
        Cita citaObtenida = gestorCitas.crearCita(paciente, fechaLunesTemprano, ID_CENTRO, ID_CAMPANIA);
        assertNull(citaObtenida, "La cita debería ser null porque el centro abre a las 09:00 AM.");
    }
}