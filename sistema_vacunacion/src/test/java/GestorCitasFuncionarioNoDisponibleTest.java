import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorCitasFuncionarioNoDisponibleTest {

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

        // Crear Centro de Vacunación que está abierto los LUNES de 09:00 a 18:00
        CentroVacunacion centro = new CentroVacunacion(
                ID_CENTRO,
                "Centro Mall del Centro CCP",
                "Movil",
                "Barros Arana 1068 CCP"
        );
        HorarioCentro horarioLunes = new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0));
        centro.agregarHorario(horarioLunes);

        // Crear un Funcionario de Salud que solo trabaja los MIÉRCOLES
        HorarioFs horarioMiercolesFs = new HorarioFs(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
        ArrayList<HorarioFs> horariosFuncionario = new ArrayList<>();
        horariosFuncionario.add(horarioMiercolesFs);

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

        // Aseguramos que haya stock de vacunas
        Vacuna vacuna = new Vacuna(1, "Influenza Adulto");
        StockVacuna stockVacuna = new StockVacuna(vacuna, 10, campania);
        centro.agregarStockVacunas(stockVacuna);

        ArrayList<CentroVacunacion> listaCentros = new ArrayList<>();
        listaCentros.add(centro);
        CentrosRepo centrosRepo = new CentrosRepo(listaCentros);

        // Inicializamos Gestores y Validadores
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        gestorCitas = new GestorCitas(gestorNotificaciones, validadorCita);
    }

    @Test
    public void testCrearCita_CuandoCentroEstaAbiertoPeroFuncionarioNoAtiendeEseDia_DebeRetornarNull() {
        // El centro abre los Lunes, pero el funcionario solo trabaja los Miércoles
        LocalDateTime fechaLunes = LocalDateTime.of(2026, 6, 8, 10, 0); // 8 de Junio de 2026 es Lunes
        Cita citaObtenida = gestorCitas.crearCita(paciente, fechaLunes, ID_CENTRO, ID_CAMPANIA);
        assertNull(citaObtenida, "La cita debería ser null porque el centro está abierto, pero ningún funcionario atiende los lunes.");
    }
}