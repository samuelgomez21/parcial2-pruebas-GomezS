package parqueadero;

import java.util.Map;

/** Aplica descuentos a los clientes del parqueadero. */
public class Descuentos {

    private static final Map<String, Integer> PORCENTAJE_DE_DESCUENTO = Map.of(
            "ninguno", 0,
            "frecuente", 10,
            "convenio", 20);
    private static final int PORCENTAJE_COMPLETO = 100;

    /** Devuelve el valor con descuento, redondeado hacia abajo al peso entero. */
    public int aplicarDescuento(int valor, String tipoCliente) {
        validarValor(valor);
        validarTipoCliente(tipoCliente);
        return valor * (PORCENTAJE_COMPLETO - PORCENTAJE_DE_DESCUENTO.get(tipoCliente))
                / PORCENTAJE_COMPLETO;
    }

    private void validarValor(int valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
    }

    private void validarTipoCliente(String tipoCliente) {
        if (!PORCENTAJE_DE_DESCUENTO.containsKey(tipoCliente)) {
            throw new IllegalArgumentException("Tipo de cliente no válido");
        }
    }
}
