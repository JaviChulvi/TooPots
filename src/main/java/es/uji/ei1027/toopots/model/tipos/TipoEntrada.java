package es.uji.ei1027.toopots.model.tipos;

public enum TipoEntrada {

    MENOR18 ('1'),
    ENTRE1850 ('2'),
    MAYOR50 ('3'),
    GRUPO ('G'),
    TEMPORADABAJA ('B'),
    TEMPORADAALTA ('A');

    private final char codigo;

    private TipoEntrada(char codigo){
        this.codigo = codigo;
    }

    public char getCodigo(){
        return codigo;
    }

    public static TipoEntrada getEnum(char codigo){
        TipoEntrada [] values = TipoEntrada.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
