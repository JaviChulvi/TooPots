package es.uji.ei1027.toopots.model.tipos;

public enum TipoEntrada {

    MENOR18 ("menor18"),
    ENTRE1850 ("entre1850"),
    MAYOR50 ("mayor50"),
    GRUPO ("grupo"),
    TEMPORADABAJA ("baja"),
    TEMPORADAALTA ("alta");

    private final String codigo;

    private TipoEntrada(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }

    public static TipoEntrada getEnum(String codigo){
        TipoEntrada [] values = TipoEntrada.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
