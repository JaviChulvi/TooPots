package es.uji.ei1027.toopots.model.tipos;

public enum NivelActividad {

    BAJA ('B'),
    MEDIA ('M'),
    ALTA ('A'),
    EXTREMA ('E');

    private final char codigo;

    private NivelActividad(char codigo){
        this.codigo = codigo;
    }

    public char getCodigo(){
        return codigo;
    }

    public static NivelActividad getEnum(char codigo){
        NivelActividad [] values = NivelActividad.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
