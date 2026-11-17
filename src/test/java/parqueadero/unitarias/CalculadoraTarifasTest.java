package parqueadero.unitarias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import parqueadero.CalculadoraTarifas;

/** Pruebas unitarias iniciales de calcularTarifa. */
class CalculadoraTarifasTest {
    private final CalculadoraTarifas calculadora = new CalculadoraTarifas();

    @Test
    @DisplayName("Un carro con una hora y un minuto paga dos horas")
    void carroConUnaHoraYUnMinutoPagaDosHoras() {
        assertEquals(6000, calculadora.calcularTarifa(61, "carro"));
    }

    @Test
    @DisplayName("Una moto con media hora paga una hora")
    void motoConMediaHoraPagaUnaHora() {
        assertEquals(1500, calculadora.calcularTarifa(30, "moto"));
    }

    @Test
    @DisplayName("Rechaza un tipo de vehículo desconocido")
    void rechazaUnTipoDeVehiculoDesconocido() {
        assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(30, "bicicleta"));
    }

    @ParameterizedTest(name = "{0} minutos para {1} cuesta {2}")
    @CsvSource({"0, carro, 0", "15, carro, 0", "0, moto, 0", "15, moto, 0"})
    @DisplayName("Los límites de gracia no generan cobro")
    void limitesDeGraciaNoGeneranCobro(int minutos, String tipoVehiculo, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, tipoVehiculo));
    }

    @ParameterizedTest(name = "{0} minutos para {1} cuesta {2}")
    @CsvSource({"16, carro, 3000", "60, carro, 3000", "61, carro, 6000", "16, moto, 1500",
            "60, moto, 1500", "61, moto, 3000"})
    @DisplayName("Después de la gracia se cobra cada hora o fracción")
    void cobraHoraOFraccion(int minutos, String tipoVehiculo, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, tipoVehiculo));
    }

    @ParameterizedTest(name = "{0} minutos para {1} aplica el tope de {2}")
    @CsvSource({"480, carro, 24000", "481, carro, 25000", "1440, carro, 25000",
            "480, moto, 12000", "481, moto, 12000", "1440, moto, 12000"})
    @DisplayName("Cobra hasta el tope diario para cada tipo de vehículo")
    void aplicaTopeDiario(int minutos, String tipoVehiculo, int valorEsperado) {
        assertEquals(valorEsperado, calculadora.calcularTarifa(minutos, tipoVehiculo));
    }

    @ParameterizedTest
    @CsvSource({"-1, carro", "1441, moto"})
    @DisplayName("Rechaza minutos fuera del rango permitido")
    void rechazaMinutosFueraDelRango(int minutos, String tipoVehiculo) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularTarifa(minutos, tipoVehiculo));
        assertEquals("Los minutos deben estar entre 0 y 1440", error.getMessage());
    }
}
