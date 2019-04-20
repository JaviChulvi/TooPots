package es.uji.ei1027.toopots.model.tipos;

public enum NivelActividad {

    BAJA ("baja"),
    MEDIA ("media"),
    ALTA ("alta"),
    EXTREMA ("extrema");

    private final String codigo;

    private NivelActividad(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }

    public static NivelActividad getEnum(String codigo){
        NivelActividad [] values = NivelActividad.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
