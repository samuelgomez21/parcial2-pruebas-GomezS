# language: es
Característica: Cobro de estadías en EstacionaYa
  Como dueño del parqueadero
  Quiero conocer el valor justo de cada estadía
  Para recaudar según las reglas del negocio

  Esquema del escenario: Cobrar una estadía según su duración y vehículo
    Dado un <vehiculo> estuvo <minutos> minutos en el parqueadero
    Cuando calculo el valor de su estadía
    Entonces debe pagar <valor> pesos

    Ejemplos:
      | vehiculo | minutos | valor |
      | carro    | 15      | 0     |
      | moto     | 15      | 0     |
      | carro    | 61      | 6000  |
      | moto     | 61      | 3000  |
      | carro    | 1440    | 25000 |
      | moto     | 1440    | 12000 |

  Escenario: Aplicar descuento a un cliente frecuente
    Dado un cobro de 3000 pesos para un cliente frecuente
    Cuando aplico el descuento del cliente
    Entonces el cliente debe pagar 2700 pesos
