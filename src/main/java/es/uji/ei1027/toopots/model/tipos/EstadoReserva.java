package es.uji.ei1027.toopots.model.tipos;

public enum EstadoReserva {

    PENDIENTE ("pendiente"),
    PAGADA ("pagada");

    private final String codigo;

    private EstadoReserva(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }

    public static EstadoReserva getEnum(String codigo){
        EstadoReserva [] values = EstadoReserva.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
