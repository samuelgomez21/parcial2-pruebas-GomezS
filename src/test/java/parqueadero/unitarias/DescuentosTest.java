package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import parqueadero.Descuentos;

/** Prueba inicial del ciclo TDD de descuentos. */
class DescuentosTest {
    private final Descuentos descuentos = new Descuentos();

    @Test
    @DisplayName("Un cliente frecuente recibe diez por ciento de descuento")
    void clienteFrecuenteRecibeDescuento() {
        assertEquals(2700, descuentos.aplicarDescuento(3000, "frecuente"));
    }

    @Test
    @DisplayName("Un cobro de cero para cliente sin descuento queda en cero")
    void clienteSinDescuentoConValorCero() {
        assertEquals(0, descuentos.aplicarDescuento(0, "ninguno"));
    }
}
