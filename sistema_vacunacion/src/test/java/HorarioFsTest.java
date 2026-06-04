import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class HorarioFsTest {

    // Horario base para los tests: Lunes de 09:00 a 13:00
    private HorarioFs horarioLunes() {
        return new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
    }

    @Test
    void testAbarca_DentroDelRango() {
        // Lunes 2026-06-01 a las 10:00 -> dentro del rango
        assertTrue(horarioLunes().abarca(LocalDateTime.of(2026, 6, 1, 10, 0)));
    }

    @Test
    void testAbarca_LimitesInclusivos() {
        HorarioFs h = horarioLunes();
        assertTrue(h.abarca(LocalDateTime.of(2026, 6, 1, 9, 0)), "Debe incluir la hora de inicio");
        assertTrue(h.abarca(LocalDateTime.of(2026, 6, 1, 13, 0)), "Debe incluir la hora de fin");
    }

    @Test
    void testAbarca_FueraDeRango() {
        HorarioFs h = horarioLunes();
        assertFalse(h.abarca(LocalDateTime.of(2026, 6, 1, 8, 59)), "Antes de la apertura");
        assertFalse(h.abarca(LocalDateTime.of(2026, 6, 1, 13, 1)), "Después del cierre");
    }

    @Test
    void testAbarca_DiaIncorrecto() {
        // Martes 2026-06-02 -> el horario es de Lunes
        assertFalse(horarioLunes().abarca(LocalDateTime.of(2026, 6, 2, 10, 0)));
    }

    @Test
    void testBloquearCambiaDisponibilidad() {
        HorarioFs h = horarioLunes();
        assertTrue(h.estaDisponible(), "Un horario nuevo debe estar disponible");
        h.bloquear();
        assertFalse(h.estaDisponible(), "Tras bloquear ya no debe estar disponible");
    }
}
