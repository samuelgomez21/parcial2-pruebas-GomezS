package parqueadero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Construye el resumen de los registros diarios del parqueadero. */
public class Reportes {

    private static final String CARRO = "carro";
    private static final String MOTO = "moto";
    private static final String TOTAL_RECAUDADO = "total_recaudado";

    private final CalculadoraTarifas calculadoraTarifas = new CalculadoraTarifas();

    /** Cuenta los vehículos por tipo y suma las tarifas recaudadas. */
    public Map<String, Integer> resumen(List<Registro> registros) {
        Map<String, Integer> resumen = crearResumenVacio();
        for (Registro registro : registros) {
            sumarRegistro(resumen, registro);
        }
        return resumen;
    }

    private Map<String, Integer> crearResumenVacio() {
        Map<String, Integer> resumen = new HashMap<>();
        resumen.put(CARRO, 0);
        resumen.put(MOTO, 0);
        resumen.put(TOTAL_RECAUDADO, 0);
        return resumen;
    }

    private void sumarRegistro(Map<String, Integer> resumen, Registro registro) {
        String tipoVehiculo = registro.tipo();
        resumen.put(tipoVehiculo, resumen.get(tipoVehiculo) + 1);
        int tarifa = calculadoraTarifas.calcularTarifa(registro.minutos(), tipoVehiculo);
        resumen.put(TOTAL_RECAUDADO, resumen.get(TOTAL_RECAUDADO) + tarifa);
    }
}
