package parqueadero;

/** Aplica descuentos a los clientes del parqueadero. */
public class Descuentos {

    /** Calcula el descuento inicial para un cliente frecuente. */
    public int aplicarDescuento(int valor, String tipoCliente) {
        if (valor < 0) {
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
        if ("frecuente".equals(tipoCliente)) {
            return valor * 90 / 100;
        }
        if ("ninguno".equals(tipoCliente)) {
            return valor;
        }
        if ("convenio".equals(tipoCliente)) {
            return valor * 80 / 100;
        }
        return valor;
    }
}
