import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorCitasFuncionariosOcupadosTest {

    private GestorCitas gestorCitas;
    private Paciente paciente1;
    private Paciente paciente2;
    private Paciente paciente3;
    private final Integer ID_CAMPANIA = 1;
    private final Integer ID_CENTRO = 1;

    @BeforeEach
    public void setUp() {
        // Crear 3 Pacientes distintos
        paciente1 = new Paciente("22.121.545-1", "Simon Pablo", "Serrano", 98475930, "simon@gmail.com", LocalDate.of(1990, 1, 1), NotificacionPreferencia.AMBOS);
        paciente2 = new Paciente("23.333.333-3", "Maria Jose", "Perez", 911112222, "maria@gmail.com", LocalDate.of(1992, 5, 10), NotificacionPreferencia.CORREOELECTRONICO);
        paciente3 = new Paciente("24.444.444-4", "Juan Pedro", "Araya", 933334444, "juan@gmail.com", LocalDate.of(1995, 8, 20), NotificacionPreferencia.SMS);

        // Crear Campaña y repositorio
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

        // Crear Centro de Vacunación abierto los Lunes de 09:00 a 18:00
        CentroVacunacion centro = new CentroVacunacion(
                ID_CENTRO,
                "Centro Mall del Centro CCP",
                "Movil",
                "Barros Arana 1068 CCP"
        );
        HorarioCentro horarioLunes = new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));
        centro.agregarHorario(horarioLunes);

        // Crear y agregar el FUNCIONARIO 1 con horario disponible los Lunes de 09:00 a 13:00
        HorarioFs horarioF1 = new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
        ArrayList<HorarioFs> horariosFunc1 = new ArrayList<>();
        horariosFunc1.add(horarioF1);
        FuncSalud funcSalud1 = new FuncSalud("11.111.111-1", "Funcionario", "Uno", 911111111, "f1@salud.cl", LocalDate.of(1980, 1, 1), horariosFunc1, NotificacionPreferencia.CORREOELECTRONICO);
        centro.agregarFuncionariosSalud(funcSalud1);

        // Crear y agregar el FUNCIONARIO 2 mismo horario
        HorarioFs horarioF2 = new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
        ArrayList<HorarioFs> horariosFunc2 = new ArrayList<>();
        horariosFunc2.add(horarioF2);
        FuncSalud funcSalud2 = new FuncSalud("12.222.222-2", "Funcionario", "Dos", 922222222, "f2@salud.cl", LocalDate.of(1983, 2, 2), horariosFunc2, NotificacionPreferencia.CORREOELECTRONICO);
        centro.agregarFuncionariosSalud(funcSalud2);

        // Agregar Stock de vacunas de 5 unidades
        Vacuna vacuna = new Vacuna(1, "Influenza Adulto");
        StockVacuna stockVacuna = new StockVacuna(vacuna, 5, campania);
        centro.agregarStockVacunas(stockVacuna);
        ArrayList<CentroVacunacion> listaCentros = new ArrayList<>();
        listaCentros.add(centro);
        CentrosRepo centrosRepo = new CentrosRepo(listaCentros);
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        gestorCitas = new GestorCitas(gestorNotificaciones, validadorCita);
    }

    @Test
    public void testCrearCita_CuandoAmbosFuncionariosSeOcupan_ElTercerIntentoDebeRetornarNull() {
        // Hora compartida por ambos funcionarios
        LocalDateTime fechaHoraCita = LocalDateTime.of(2026, 6, 8, 10, 0); // Lunes a las 10:00 AM

        // El primer paciente reserva de forma exitosa (Ocupa al Funcionario 1)
        Cita cita1 = gestorCitas.crearCita(paciente1, fechaHoraCita, ID_CENTRO, ID_CAMPANIA);
        assertNotNull(cita1, "La primera cita debería crearse con éxito.");
        assertEquals("11.111.111-1", cita1.getFuncionario().getRUT(), "Debería haberse asignado al Funcionario 1.");

        // El segundo paciente reserva de forma exitosa para la misma hora (Ocupa al Funcionario 2)
        Cita cita2 = gestorCitas.crearCita(paciente2, fechaHoraCita, ID_CENTRO, ID_CAMPANIA);
        assertNotNull(cita2, "La segunda cita debería crearse con éxito.");
        assertEquals("12.222.222-2", cita2.getFuncionario().getRUT(), "Debería haberse asignado al Funcionario 2.");

        // El tercer paciente intenta reservar para la misma hora
        Cita cita3 = gestorCitas.crearCita(paciente3, fechaHoraCita, ID_CENTRO, ID_CAMPANIA);

        // Ya no quedan funcionarios libres para ese bloque horario, debe retornar null
        assertNull(cita3, "La tercera cita debería ser null porque ambos funcionarios ya están ocupados en este horario.");
    }
}