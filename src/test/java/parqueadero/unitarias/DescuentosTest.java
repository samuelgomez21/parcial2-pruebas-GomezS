package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Un cliente de convenio recibe veinte por ciento de descuento")
    void clienteConvenioRecibeDescuento() {
        assertEquals(20000, descuentos.aplicarDescuento(25000, "convenio"));
    }

    @Test
    @DisplayName("El descuento frecuente redondea hacia abajo al peso entero")
    void descuentoFrecuenteRedondeaHaciaAbajo() {
        assertEquals(1799, descuentos.aplicarDescuento(1999, "frecuente"));
    }

    @Test
    @DisplayName("Un valor negativo es rechazado")
    void rechazaValorNegativo() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(-1, "ninguno"));
        assertEquals("El valor no puede ser negativo", error.getMessage());
    }

    @Test
    @DisplayName("Un tipo de cliente desconocido es rechazado")
    void rechazaTipoDeClienteInvalido() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> descuentos.aplicarDescuento(100, "preferencial"));
        assertEquals("Tipo de cliente no válido", error.getMessage());
    }
}
