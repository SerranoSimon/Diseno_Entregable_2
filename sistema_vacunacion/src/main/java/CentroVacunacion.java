import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CentroVacunacion {
    private Integer id_centro;
    private String nombre;
    private String tipo;
    private String direccion;
    private ArrayList<StockVacuna> stockVacunas;
    private ArrayList<FuncSalud> funcionariosSalud;
    private ArrayList<HorarioCentro> horarios;

    public CentroVacunacion(Integer id_centro, String nombre, String tipo, String direccion ) {
        this.id_centro = id_centro;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo = tipo;
        this.stockVacunas = new ArrayList<>();
        this.funcionariosSalud = new ArrayList<>();
        this.horarios = new ArrayList<>();
    }

    public boolean estaAbierto(LocalDateTime fechaHora){
        DayOfWeek dia= fechaHora.getDayOfWeek();
        LocalTime hora = fechaHora.toLocalTime();
        for(HorarioCentro horarioCentro: horarios){
            if(horarioCentro.getDiaSemana().equals(dia) && !hora.isBefore(horarioCentro.getHoraApertura())
            && !hora.isAfter(horarioCentro.getHoraCierre())){
                return true;
            }
        }
      return false;
    };

    public FuncSalud buscarFsParaCita(LocalDateTime fechaHora){
        for(FuncSalud fs: funcionariosSalud){
            if(fs.disponible(fechaHora)){
                return fs;
            }
        }
        return null;
    }
    public Vacuna buscarVacuna(Campania camp){
            for(StockVacuna sv: stockVacunas){
                if(sv.vacunaEsDeCampania(camp) && sv.verificarStock()){
                    return sv.reservar();
                }
            }
            return null;
    }

    public Integer getId_centro() {
        return id_centro;
    }
    public void agregarHorario(HorarioCentro horarioCentro){
        horarios.add(horarioCentro);
    }
    public void agregarFuncionariosSalud(FuncSalud funcSalud ){
        funcionariosSalud.add(funcSalud);
    }
    public void agregarStockVacunas(StockVacuna stockVacuna){
        stockVacunas.add(stockVacuna);
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<StockVacuna> getStockVacunas() {
        return stockVacunas;
    }

    public ArrayList<FuncSalud> getFuncionariosSalud() {
        return funcionariosSalud;
    }

    public ArrayList<HorarioCentro> getHorarios() {
        return horarios;
    }


}
