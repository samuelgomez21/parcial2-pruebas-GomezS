package parqueadero;

/** Aplica descuentos a los clientes del parqueadero. */
public class Descuentos {

    /** Calcula el descuento inicial para un cliente frecuente. */
    public int aplicarDescuento(int valor, String tipoCliente) {
        if ("frecuente".equals(tipoCliente)) {
            return valor - 300;
        }
        if ("ninguno".equals(tipoCliente)) {
            return valor;
        }
        throw new IllegalArgumentException("Tipo de cliente no válido");
    }
}
