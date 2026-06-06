import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ===================== DATOS =====================
        Paciente paciente = new Paciente(
                "22.121.545-1",
                "Simon Pablo",
                "Serrano Luarte",
                98475930,
                "bersiclamar@gmail.com",
                LocalDate.of(1990, 1, 1),
                NotificacionPreferencia.AMBOS
        );
       // CREACION BLOQUES DE HORARIOS FUNC 1
        HorarioFs h1fs1 = new HorarioFs(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(9, 30));
        HorarioFs h2fs1 = new HorarioFs(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(10, 30));
        ArrayList<HorarioFs> horarioFs1 = new ArrayList<>();
        horarioFs1.add(h1fs1);
        horarioFs1.add(h2fs1);
        // CREACION BLOQUES DE HORARIOS FUNC 2
        HorarioFs h1fs2 = new HorarioFs(DayOfWeek.FRIDAY, LocalTime.of(17, 0), LocalTime.of(17, 30));
        ArrayList<HorarioFs> horarioFs2 = new ArrayList<>();
        horarioFs2.add(h1fs2);
        // CREACION FUNC1
        FuncSalud funcSalud1 = new FuncSalud(
                "7.382.025-1", "Leonel Patricio", "Jara Diaz",
                980284751, "ljara@gmail.com", LocalDate.of(1990, 1, 1),
                horarioFs1, NotificacionPreferencia.CORREOELECTRONICO
        );
        // CREACION FUNC2
        FuncSalud funcSalud2 = new FuncSalud(
                "7.382.025-1", "Leonel Patricio", "Jara Diaz",
                980284751, "ljara@gmail.com", LocalDate.of(1990, 1, 1),
                horarioFs2, NotificacionPreferencia.CORREOELECTRONICO
        );
        // CREACION CAMPAÑA
        Campania campania = new Campania(
                1, "Covid-19",
                LocalDate.of(2026, 6, 1), LocalDate.of(2026, 8, 1),
                "Campaña para el covid-19", EstadoCampania.EN_CURSO, null
        );
        //CREACION DE 2 VACUNAS PARA LA CAMPAÑA
        Vacuna v1 = new Vacuna(1, "pzifer");
        Vacuna v2 = new Vacuna(1, "astrazeneca");
        StockVacuna stockVacuna1 = new StockVacuna(v1, 100, campania);
        StockVacuna stockVacuna2 = new StockVacuna(v2, 50, campania);
        campania.agregarStockVacunas(stockVacuna1);
        campania.agregarStockVacunas(stockVacuna2);
        // CREACION DE HORARIOS PARA UN CENTRO
        HorarioCentro h1 = new HorarioCentro(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(20, 0));
        HorarioCentro h2 = new HorarioCentro(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(13, 0));
        HorarioCentro h3 = new HorarioCentro(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(19, 0));
        // CREAR STOCKVACUNA PARA UN CENTRO
        StockVacuna stockVacunaC1 = new StockVacuna(v1, 5, campania);
        // CREACION CENTRO
        CentroVacunacion centro1 = new CentroVacunacion(
                1, "Centro mall del centro ccp", "Movil", "Barros Arana 1068 CCP"
        );
        // AGREGAMOS LOS HORARIOS Y VACUNAS AL CENTRO
        centro1.agregarHorario(h1);
        centro1.agregarHorario(h2);
        centro1.agregarHorario(h3);
        centro1.agregarStockVacunas(stockVacunaC1);
        centro1.agregarFuncionariosSalud(funcSalud1);
        centro1.agregarFuncionariosSalud(funcSalud2);
        // CREACION DEL REPO DE CAMPAÑAS
        ArrayList<Campania> campanias = new ArrayList<>();
        campanias.add(campania);
        CampaniaRepo campaniaRepo = new CampaniaRepo(campanias);
        // CREACION DEL REPO DE CENTROS
        ArrayList<CentroVacunacion> centros = new ArrayList<>();
        centros.add(centro1);
        CentrosRepo centrosRepo = new CentrosRepo(centros);
        // CREACION DE GESTORES
        GestorNotificaciones gestorNotificaciones = new GestorNotificaciones();
        ValidadorCita validadorCita = new ValidadorCita(campaniaRepo, centrosRepo);
        GestorCitas gestorCitas = new GestorCitas(gestorNotificaciones, validadorCita);

        // ===================== INTERFAZ =====================
        Scanner scanner = new Scanner(System.in);

        // --- Autenticación ---
        System.out.println("=== Sistema de Vacunación ===");
        System.out.print("Ingrese su RUT: ");
        String rutIngresado = scanner.nextLine().trim();
        System.out.print("Ingrese su contraseña: ");
        String contrasenaIngresada = scanner.nextLine().trim();

        String contrasena = "ABC2026";
        if (!rutIngresado.equals(paciente.getRUT()) || !contrasenaIngresada.equals(contrasena)) {
            System.out.println("Autenticación fallida. RUT o contraseña incorrectos.");
            scanner.close();
            return;
        }
        System.out.println("Bienvenido, " + paciente.getNombres() + " " + paciente.getApellidos() + "!\n");

        // --- Menú principal ---
        boolean salir = false;
        while (!salir) {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Solicitar cita");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    // --- Elegir campaña ---
                    System.out.println("\n-- Campañas disponibles --");
                    for (Campania c : campanias) {
                        System.out.println("[" + c.getIdCampania() + "] " + c.getNombre()
                                + " | " + c.getFechaInicio() + " → " + c.getFechaFin()
                                + " | Estado: " + c.getEstadoCampania());
                    }
                    System.out.print("Ingrese el ID de la campaña: ");
                    int idCampania;
                    try {
                        idCampania = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.\n");
                        break;
                    }

                    // --- Elegir centro ---
                    System.out.println("\n-- Centros de vacunación disponibles --");
                    for (CentroVacunacion c : centros) {
                        System.out.println("[" + c.getId_centro() + "] " + c.getNombre()
                                + " | " + c.getDireccion());
                    }
                    System.out.print("Ingrese el ID del centro: ");
                    int idCentro;
                    try {
                        idCentro = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.\n");
                        break;
                    }

                    // --- Elegir fecha y hora ---
                    System.out.println("\nIngrese la fecha y hora de la cita:");
                    try {
                        System.out.print("  Año  : ");
                        int anio = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("  Mes  : ");
                        int mes = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("  Día  : ");
                        int dia = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("  Hora : ");
                        int hora = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("  Minuto: ");
                        int minuto = Integer.parseInt(scanner.nextLine().trim());

                        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);

                        System.out.println();
                        paciente.solicitarCita(idCampania, idCentro, fechaHora, gestorCitas);

                    } catch (NumberFormatException e) {
                        System.out.println("Fecha u hora inválida.\n");
                    } catch (Exception e) {
                        System.out.println("Error al procesar la fecha: " + e.getMessage() + "\n");
                    }
                    break;

                case "0":
                    salir = true;
                    System.out.println("Hasta luego.");
                    break;

                default:
                    System.out.println("Opción no válida.\n");
            }
        }
        scanner.close();
    }
}