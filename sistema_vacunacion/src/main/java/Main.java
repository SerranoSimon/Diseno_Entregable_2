import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Paciente paciente= new Paciente(
                "22.121.545-1",
                    "Simon Pablo",
                "Serrano Luarte",
                    98475930,
                "bersiclamar@gmail.com",
                LocalDate.of(1990, 1, 1),
                NotificacionPreferencia.AMBOS
        );
        // Horarios para FuncSalud1
        HorarioFs h1fs1=new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(13,0));
        HorarioFs h2fs1=new HorarioFs(DayOfWeek.WEDNESDAY, LocalTime.of(9,0), LocalTime.of(13,0));
        ArrayList<HorarioFs> horarioFs1= new ArrayList<>();
        horarioFs1.add(h1fs1);
        horarioFs1.add(h2fs1);

        //Horarios para FuncSalud2
        HorarioFs h1fs2=new HorarioFs(DayOfWeek.FRIDAY ,LocalTime.of(17,0), LocalTime.of(19,0));
        ArrayList<HorarioFs> horarioFs2= new ArrayList<>();
        horarioFs2.add(h1fs2);

        // FuncSalud 1
        FuncSalud funcSalud1= new FuncSalud(
                "7.382.025-1",
                "Leonel Patricio",
                "Jara Diaz",
                980284751,
                "ljara@gmail.com",
                LocalDate.of(1990, 1, 1),
                horarioFs1,
                NotificacionPreferencia.CORREOELECTRONICO
        );
        // FuncSalud 2
        FuncSalud funcSalud2= new FuncSalud(
                "7.382.025-1",
                "Leonel Patricio",
                "Jara Diaz",
                980284751,
                "ljara@gmail.com",
                LocalDate.of(1990, 1, 1),
                horarioFs2,
                NotificacionPreferencia.CORREOELECTRONICO
        );
        // Campaña
        Campania campania = new Campania(
                1,
                "Covid-19",
                LocalDate.of(2026, 6,1),
                LocalDate.of(2026, 8,1),
                "Campaña para el covid-19",
                EstadoCampania.EN_CURSO,
                null
        );

        // Vacunas
        Vacuna v1 = new Vacuna(1,"pzifer");
        Vacuna v2 = new Vacuna(1,"astrazeneca");
        // Lote de vacunas para campaña
        StockVacuna stockVacuna1 = new StockVacuna(v1,100, campania);
        StockVacuna stockVacuna2 = new StockVacuna(v2,50, campania) ;
        // Añadimos las vacunas a la campaña
        campania.agregarStockVacunas(stockVacuna1);
        campania.agregarStockVacunas(stockVacuna2);

        // Horarios para un centro (incluye horas donde no hay ningun fs)
        HorarioCentro h1= new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(20,0));
        HorarioCentro h2 = new HorarioCentro(DayOfWeek.WEDNESDAY, LocalTime.of(9,0), LocalTime.of(13,0));
        HorarioCentro h3= new HorarioCentro(DayOfWeek.FRIDAY, LocalTime.of(9,0), LocalTime.of(19,0));

        // Stock de vacunas para centro
        StockVacuna stockVacunaC1= new StockVacuna(v1,5, campania);
        //Centro vacunacion
        CentroVacunacion centro1 = new CentroVacunacion(
                1,
                "Centro mall del centro ccp",
                "Movil",
                "Barros Arana 1068 CCP"
        );
        //Añadimos los horarios al centro
        centro1.agregarHorario(h1);
        centro1.agregarHorario(h2);
        centro1.agregarHorario(h3);
        // Añadimos las vacunas al centro
        centro1.agregarStockVacunas(stockVacunaC1);
        // Añadimos los funcionarios
        centro1.agregarFuncionariosSalud(funcSalud1);
        centro1.agregarFuncionariosSalud(funcSalud2);

        // Repos
        ArrayList<Campania> campanias = new ArrayList<>();
        campanias.add(campania);
        CampaniaRepo campaniaRepo = new CampaniaRepo(campanias);

        ArrayList<CentroVacunacion> centros = new ArrayList<>();
        centros.add(centro1);
        CentrosRepo centrosRepo  = new CentrosRepo(centros);

        // Gestores
        GestorNotificaciones gestorNotificaciones= new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        GestorCitas gestorCitas = new GestorCitas(gestorNotificaciones,validadorCita);

        // PACIENTE SOLICITA CITA EXITOSA
        paciente.solicitarCita(1,1, LocalDateTime.of(2026,6,1,9,0),gestorCitas);




    }
}
