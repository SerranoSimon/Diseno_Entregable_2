import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FuncSaludTest {

    // Funcionario con un único horario: Lunes de 09:00 a 13:00
    private FuncSalud funcConHorarioLunes() {
        ArrayList<HorarioFs> horarios = new ArrayList<>();
        horarios.add(new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(13, 0)));


        return new FuncSalud(
                "22.222.222-2",
                "Ana",
                "Gómez",
                912345678,
                "ana.gomez@cesfam.cl",
                LocalDate.of(1985, 5, 20),
                horarios,
                null
        );
    }

    @Test
    void testDisponible_ConHorarioQueCalza() {
        // Lunes 2026-06-01 a las 10:00 -> tiene horario disponible
        assertTrue(funcConHorarioLunes().disponible(LocalDateTime.of(2026, 6, 1, 10, 0)));
    }

    @Test
    void testDisponible_SinHorarioQueCalza() {
        // Martes 2026-06-02 -> no tiene horario ese día
        assertFalse(funcConHorarioLunes().disponible(LocalDateTime.of(2026, 6, 2, 10, 0)));
    }

    @Test
    void testDisponible_NoPermiteDobleReserva() {
        FuncSalud fs = funcConHorarioLunes();
        LocalDateTime fechaCita = LocalDateTime.of(2026, 6, 1, 10, 0);

        assertTrue(fs.disponible(fechaCita), "Primera cita: el horario está libre");
        // La segunda vez el único horario ya quedó bloqueado por la primera
        assertFalse(fs.disponible(fechaCita), "Segunda cita en el mismo horario: ya está ocupado");
    }
}