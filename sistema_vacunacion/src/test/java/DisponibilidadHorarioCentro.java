import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DisponibilidadHorarioCentro {

    private CentroVacunacion centro;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de cada test para preparar los datos
        ArrayList<HorarioCentro> horarios = new ArrayList<>();

        // El centro abre los Lunes de 09:00 a 18:00
        horarios.add(new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)));

        // El centro abre los Miércoles de 10:00 a 14:00
        horarios.add(new HorarioCentro(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(14, 0)));

        centro = new CentroVacunacion(null,null,null,null,null,horarios);
    }

    @Test
    void testEstaAbierto_CentroAbiertoLunes() {
        // Lunes a las 10:30 AM -> Debería estar ABIERTO
        LocalDateTime fechaPrueba = LocalDateTime.of(2026, 6, 1, 10, 30); // 2 de Oct de 2023 fue Lunes
        assertTrue(centro.estaAbierto(fechaPrueba), "El centro debería estar abierto el Lunes a las 10:30");
    }

    @Test
    void testEstaAbierto_CentroAbiertoJustoAlAbrir() {
        // Lunes a las 09:00 AM exactas -> Debería estar ABIERTO
        LocalDateTime fechaPrueba = LocalDateTime.of(2026, 6, 1, 9, 0);
        assertTrue(centro.estaAbierto(fechaPrueba), "El centro debería estar abierto justo a las 09:00");
    }

    @Test
    void testEstaAbierto_CentroCerradoAntesDeHora() {
        // Lunes a las 08:30 AM -> Debería estar CERRADO
        LocalDateTime fechaPrueba = LocalDateTime.of(2026, 6, 1, 8, 30);
        assertFalse(centro.estaAbierto(fechaPrueba), "El centro debería estar cerrado antes de las 09:00");
    }

    @Test
    void testEstaAbierto_CentroCerradoDespuesDeHora() {
        // Lunes a las 18:30 PM -> Debería estar CERRADO
        LocalDateTime fechaPrueba = LocalDateTime.of(2026, 6, 1, 18, 30);
        assertFalse(centro.estaAbierto(fechaPrueba), "El centro debería estar cerrado después de las 18:00");
    }

    @Test
    void testEstaAbierto_CentroCerradoDiaIncorrecto() {
        // Martes a las 12:00 PM (No hay horario los martes) -> Debería estar CERRADO
        LocalDateTime fechaPrueba = LocalDateTime.of(2026, 6, 2, 12, 0); // 3 de Oct de 2023 fue Martes
        assertFalse(centro.estaAbierto(fechaPrueba), "El centro debería estar cerrado los Martes");
    }

    // Crea un centro con un único funcionario que atiende los Lunes de 09:00 a 18:00
    private CentroVacunacion centroConFuncionario(FuncSalud fs) {
        ArrayList<FuncSalud> funcionarios = new ArrayList<>();
        funcionarios.add(fs);
        return new CentroVacunacion(1, new ArrayList<>(), funcionarios);
    }

    private FuncSalud funcionarioLunes() {
        ArrayList<HorarioFs> horariosFs = new ArrayList<>();
        horariosFs.add(new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)));
        return new FuncSalud(horariosFs);
    }

    @Test
    void testBuscarFsParaCita_RetornaFuncionarioDisponible() {
        FuncSalud fs = funcionarioLunes();
        CentroVacunacion centroConFs = centroConFuncionario(fs);

        // Lunes 2026-06-01 a las 10:00 -> el funcionario está disponible
        FuncSalud encontrado = centroConFs.buscarFsParaCita(LocalDateTime.of(2026, 6, 1, 10, 0));
        assertSame(fs, encontrado, "Debe retornar el funcionario con horario disponible");
    }

    @Test
    void testBuscarFsParaCita_SinFuncionarioDisponible() {
        CentroVacunacion centroConFs = centroConFuncionario(funcionarioLunes());

        // Martes 2026-06-02 -> ningún funcionario atiende ese día
        assertNull(centroConFs.buscarFsParaCita(LocalDateTime.of(2026, 6, 2, 10, 0)),
                "No debe retornar funcionario si ninguno está disponible");
    }
}