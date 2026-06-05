import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorCitasSinStockTest {

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

        // Crear Centro de Vacunación abierto los Lunes de 09:00 a 18:00
        CentroVacunacion centro = new CentroVacunacion(
                ID_CENTRO,
                "Centro Mall del Centro CCP",
                "Movil",
                "Barros Arana 1068 CCP"
        );
        HorarioCentro horarioLunes = new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));
        centro.agregarHorario(horarioLunes);

        // Agregar Funcionario disponible los Lunes
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

        // Agregar Stock con cantidad = 0 . Sin stock disponible
        Vacuna vacunaInfluenza = new Vacuna(1, "Influenza Adulto");
        StockVacuna stockSinUnidades = new StockVacuna(vacunaInfluenza, 0, campania);
        centro.agregarStockVacunas(stockSinUnidades);
        ArrayList<CentroVacunacion> listaCentros = new ArrayList<>();
        listaCentros.add(centro);
        CentrosRepo centrosRepo = new CentrosRepo(listaCentros);
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        gestorCitas = new GestorCitas(gestorNotificaciones, validadorCita);
    }

    @Test
    public void testCrearCita_CuandoNoHayStockDeVacunas_DebeRetornarNull() {
        // Fecha en un lunes a las 10:00 AM el centro está abierto y el funcionario disponible
        LocalDateTime fechaLunes = LocalDateTime.of(2026, 6, 8, 10, 0);
        Cita citaObtenida = gestorCitas.crearCita(paciente, fechaLunes, ID_CENTRO, ID_CAMPANIA);
        assertNull(citaObtenida, "La cita debería ser null porque el stock de vacunas para esta campaña está agotado (0).");
    }
}