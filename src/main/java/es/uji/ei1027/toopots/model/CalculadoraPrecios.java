package es.uji.ei1027.toopots.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CalculadoraPrecios {
    private float precioBruto;
    private int numAdultos;
    private int numJubilados;
    private int numMenores;
    private String tipoOferta;
    private float descuentoOferta;

    public CalculadoraPrecios(float precioBruto, int numAdultos, int numJubilados, int numMenores, String tipoOferta, float descuentoOferta) {
        this.precioBruto = precioBruto;
        this.numAdultos = numAdultos;
        this.numJubilados = numJubilados;
        this.numMenores = numMenores;
        this.tipoOferta = tipoOferta;
        this.descuentoOferta = descuentoOferta;
    }

    public double calcularPrecio(){
        // CODIGO ESPAGUETI ESCRITO A LAS 01:48 DE LA NOCHE, DISCULPE LAS MOLESTIAS
        double descuento_menores = 0.5;
        double descuento_jubilados = 0.25;
        int grupo = 7; // se considera grupo si son 7 o más personas
        // 3 4 5 9 10 11
        List<Integer> mesesTemporadaBaja = Arrays.asList(3, 4, 5, 9, 10, 11);
        if (tipoOferta=="") {
            return numAdultos*precioBruto + ((numJubilados*precioBruto)*descuento_jubilados) + ((numMenores*precioBruto)*descuento_menores);
        } else if (tipoOferta=="menor18") {
            return numAdultos*precioBruto + ((numJubilados*precioBruto)*descuento_jubilados) + ((numMenores*precioBruto)*(descuento_menores+descuentoOferta));
        } else if (tipoOferta=="entre18-50") {
            return (numAdultos*precioBruto)*descuentoOferta + ((numJubilados*precioBruto)*descuento_jubilados) + ((numMenores*precioBruto)*descuento_menores);
        } else if (tipoOferta=="mayor50") {
            return (numAdultos * precioBruto) + ((numJubilados * precioBruto) * (descuento_jubilados+descuentoOferta)) + ((numMenores * precioBruto) * descuento_menores);
        } else if (tipoOferta=="grupo") {
            if ((numMenores + numJubilados + numAdultos) >= 7) {
                return ((numAdultos * precioBruto) + ((numJubilados * precioBruto) * descuento_jubilados) + ((numMenores * precioBruto) * descuento_menores))*descuentoOferta;
            } else {
                return numAdultos*precioBruto + ((numJubilados*precioBruto)*descuento_jubilados) + ((numMenores*precioBruto)*descuento_menores);
            }
        } else if (tipoOferta=="temporadabaja") {
            LocalDate today = LocalDate.now();
            int mesActual = today.getMonthValue();
            if (mesesTemporadaBaja.contains(mesActual)){
                return ((numAdultos * precioBruto) + ((numJubilados * precioBruto) * descuento_jubilados) + ((numMenores * precioBruto) * descuento_menores))*descuentoOferta;
            } else {
                return numAdultos*precioBruto + ((numJubilados*precioBruto)*descuento_jubilados) + ((numMenores*precioBruto)*descuento_menores);
            }
        } else if (tipoOferta=="total") {
            return ((numAdultos * precioBruto) + ((numJubilados * precioBruto) * descuento_jubilados) + ((numMenores * precioBruto) * descuento_menores))*descuentoOferta;
        }
            return 0.0;
    }
}
