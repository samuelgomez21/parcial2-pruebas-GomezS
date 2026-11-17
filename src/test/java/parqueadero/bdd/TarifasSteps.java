package parqueadero.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import parqueadero.CalculadoraTarifas;
import parqueadero.Descuentos;

/** Define los pasos ejecutables de los escenarios del parqueadero. */
public class TarifasSteps {
    private final CalculadoraTarifas calculadoraTarifas = new CalculadoraTarifas();
    private final Descuentos descuentos = new Descuentos();
    private int minutos;
    private String tipoVehiculo;
    private int valor;
    private String tipoCliente;
    private int resultado;

    @Dado("un {word} estuvo {int} minutos en el parqueadero")
    public void unVehiculoEstuvoMinutosEnElParqueadero(String vehiculo, int minutosEstadia) {
        tipoVehiculo = vehiculo;
        minutos = minutosEstadia;
    }

    @Cuando("calculo el valor de su estadía")
    public void calculoElValorDeSuEstadia() {
        resultado = calculadoraTarifas.calcularTarifa(minutos, tipoVehiculo);
    }

    @Entonces("debe pagar {int} pesos")
    public void debePagarPesos(int valorEsperado) {
        assertEquals(valorEsperado, resultado);
    }

    @Dado("un cobro de {int} pesos para un cliente {word}")
    public void unCobroParaUnCliente(int valorCobro, String cliente) {
        valor = valorCobro;
        tipoCliente = cliente;
    }

    @Cuando("aplico el descuento del cliente")
    public void aplicoElDescuentoDelCliente() {
        resultado = descuentos.aplicarDescuento(valor, tipoCliente);
    }

    @Entonces("el cliente debe pagar {int} pesos")
    public void elClienteDebePagarPesos(int valorEsperado) {
        assertEquals(valorEsperado, resultado);
    }
}
