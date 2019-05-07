package es.uji.ei1027.toopots.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CalculadoraPrecios {
    private int numAdultos;
    private int numJubilados;
    private int numMenores;
    private String tipoOferta;
    private float descuentoOferta;
    private float precioMenor;
    private float precioAdulto;
    private float precioJubilado;

    public CalculadoraPrecios( int numAdultos, int numJubilados, int numMenores, String tipoOferta, float descuentoOferta, float precioMenor, float precioAdulto, float precioJubilado) {
        this.numAdultos = numAdultos;
        this.numJubilados = numJubilados;
        this.numMenores = numMenores;
        this.tipoOferta = tipoOferta;
        this.descuentoOferta = descuentoOferta;
        this.precioMenor = precioMenor;
        this.precioAdulto = precioAdulto;
        this.precioJubilado = precioJubilado;
    }

    public double calcularPrecio(){
        int grupo = 7; // se considera grupo si son 7 o más personas
        // 3 4 5 9 10 11
        List<Integer> mesesTemporadaBaja = Arrays.asList(3, 4, 5, 9, 10, 11);
        if (tipoOferta=="menor18") {
            return numAdultos*precioAdulto + numJubilados*precioJubilado + ((numMenores*precioMenor)*descuentoOferta);
        } else if (tipoOferta=="entre18-50") {
            return (numAdultos*precioAdulto)*descuentoOferta + numJubilados*precioJubilado + numMenores*precioMenor;
        } else if (tipoOferta=="mayor50") {
            return numAdultos * precioAdulto + ((numJubilados * precioJubilado) * descuentoOferta) + numMenores*precioMenor;
        } else if (tipoOferta=="grupo") {
            if ((numMenores + numJubilados + numAdultos) >= grupo) {
                return (numAdultos*precioAdulto + numJubilados*precioJubilado + numMenores*precioMenor)*descuentoOferta;
            }
        } else if (tipoOferta=="temporadabaja") {
            LocalDate today = LocalDate.now();
            int mesActual = today.getMonthValue();
            if (mesesTemporadaBaja.contains(mesActual)){
                return ((numAdultos * precioAdulto) + ((numJubilados * precioJubilado) ) + ((numMenores * precioMenor)))*descuentoOferta;
            }
        } else if (tipoOferta=="total") {
            return (numAdultos*precioAdulto + numJubilados*precioJubilado + numMenores*precioMenor)*descuentoOferta;
        }
        return numAdultos*precioAdulto + numJubilados*precioJubilado + numMenores*precioMenor;
    }
}
