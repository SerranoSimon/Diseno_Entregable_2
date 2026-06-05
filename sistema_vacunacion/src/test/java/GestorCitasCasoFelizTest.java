import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorCitasCasoFelizTest {

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

        // Crear Centro de Vacunación y agregar horario Abierto los Lunes de 09:00 a 18:00
        CentroVacunacion centro = new CentroVacunacion(
                ID_CENTRO,
                "Centro Mall del Centro CCP",
                "Movil",
                "Barros Arana 1068 CCP"
        );
        HorarioCentro horarioLunes = new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));
        centro.agregarHorario(horarioLunes);

        // Crear Funcionario de Salud disponible los Lunes de 09:00 a 13:00
        HorarioFs horarioLunesFs = new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
        ArrayList<HorarioFs> horariosFuncionario = new ArrayList<>();
        horariosFuncionario.add(horarioLunesFs);

        FuncSalud funcionario = new FuncSalud(
                "11.111.111-1",
                "Juan Carlos",
                "Perez Lopez",
                912345678,
                "juan.perez@salud.cl",
                LocalDate.of(1985, 3, 15),
                horariosFuncionario,
                NotificacionPreferencia.CORREOELECTRONICO
        );
        centro.agregarFuncionariosSalud(funcionario);

        // Agregar Stock de Vacunas de 5 unidades para la campaña
        Vacuna vacunaInfluenza = new Vacuna(1, "Influenza Adulto");
        StockVacuna stockVacuna = new StockVacuna(vacunaInfluenza, 5, campania);
        centro.agregarStockVacunas(stockVacuna);
        ArrayList<CentroVacunacion> listaCentros = new ArrayList<>();
        listaCentros.add(centro);
        CentrosRepo centrosRepo = new CentrosRepo(listaCentros);
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        gestorCitas = new GestorCitas(gestorNotificaciones, validadorCita);
    }

    @Test
    public void testCrearCita_CuandoTodosLosRequisitosSeCumplen_DebeRegistrarLaCitaExitosamente() {
        // Lunes a las 10:00 AM el Centro está abierto, funcionario libre, con stock y campaña vigente
        LocalDateTime fechaCitaValida = LocalDateTime.of(2026, 6, 8, 10, 0);
        Cita citaObtenida = gestorCitas.crearCita(paciente, fechaCitaValida, ID_CENTRO, ID_CAMPANIA);
        assertNotNull(citaObtenida, "La cita no debería ser null ya que se cumplen todas las condiciones del sistema.");
        assertEquals(EstadoCita.VIGENTE, citaObtenida.getEstado(), "La cita recién creada debe quedar en estado VIGENTE.");
        assertEquals("22.121.545-1", citaObtenida.getPaciente().getRUT(), "El RUT del paciente debe coincidir.");
        assertEquals("11.111.111-1", citaObtenida.getFuncionario().getRUT(), "El funcionario asignado debe ser el que estaba disponible.");
        assertEquals("Influenza Adulto", citaObtenida.getVacuna().getNombre(), "La vacuna asociada debe ser la correspondiente a la campaña.");
    }
}