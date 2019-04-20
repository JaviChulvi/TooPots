package es.uji.ei1027.toopots.model.tipos;

public enum EstadoActividad {

    ABIERTA ("abierta"),
    CERRADA ("cerrada"),
    COMPLETA ("completa"),
    CANCELADA ("cancelada");

    private final String codigo;

    private EstadoActividad(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }

    public static EstadoActividad getEnum(String codigo){
        EstadoActividad [] values = EstadoActividad.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
