package es.uji.ei1027.toopots.model.tipos;

public enum EstadoActividad {

    ABIERTA ('A'),
    CERRADA ('C'),
    COMPLETA ('P'),
    CANCELADA ('X');

    private final char codigo;

    private EstadoActividad(char codigo){
        this.codigo = codigo;
    }

    public char getCodigo(){
        return codigo;
    }

    public static EstadoActividad getEnum(char codigo){
        EstadoActividad [] values = EstadoActividad.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
