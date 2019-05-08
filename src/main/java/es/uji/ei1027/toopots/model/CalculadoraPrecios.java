package es.uji.ei1027.toopots.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CalculadoraPrecios {
    private int numAdultos;
    private int numJubilados;
    private int numMenores;
    private String tipoDescuento;
    private float descuento;
    private float precioMenor;
    private float precioAdulto;
    private float precioJubilado;

    public CalculadoraPrecios(int numAdultos, int numJubilados, int numMenores, String tipoDescuento, float descuento, float precioMenor, float precioAdulto, float precioJubilado) {
        this.numAdultos = numAdultos;
        this.numJubilados = numJubilados;
        this.numMenores = numMenores;
        this.tipoDescuento = tipoDescuento;
        this.descuento = descuento;
        this.precioMenor = precioMenor;
        this.precioAdulto = precioAdulto;
        this.precioJubilado = precioJubilado;
    }

    public double calcularPrecio(){
        int grupo = 7; // se considera grupo si son 7 o más personas
        // 3 4 5 9 10 11
        List<Integer> mesesTemporadaBaja = Arrays.asList(3, 4, 5, 9, 10, 11);

        if (tipoDescuento.equals("menor18")) {
            return numAdultos*precioAdulto + numJubilados*precioJubilado + ((numMenores*precioMenor)* descuento);
        } else if (tipoDescuento.equals("entre18-50")) {
            return (numAdultos*precioAdulto)* descuento + numJubilados*precioJubilado + numMenores*precioMenor;
        } else if (tipoDescuento.equals("mayor50")) {
            return numAdultos * precioAdulto + ((numJubilados * precioJubilado) * descuento) + numMenores*precioMenor;
        } else if (tipoDescuento.equals("grupo")) {
            if ((numMenores + numJubilados + numAdultos) >= grupo) {
                return (numAdultos*precioAdulto + numJubilados*precioJubilado + numMenores*precioMenor)* descuento;
            }
        } else if (tipoDescuento.equals("temporadabaja")) {
            LocalDate today = LocalDate.now();
            int mesActual = today.getMonthValue();
            if (mesesTemporadaBaja.contains(mesActual)){
                return ((numAdultos * precioAdulto) + ((numJubilados * precioJubilado) ) + ((numMenores * precioMenor)))* descuento;
            }
        } else if (tipoDescuento.equals("total")) {

            return (numAdultos*precioAdulto + numJubilados*precioJubilado + numMenores*precioMenor)* descuento;
        }
        return numAdultos*precioAdulto + numJubilados*precioJubilado + numMenores*precioMenor;
    }
}
