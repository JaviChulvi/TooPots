package es.uji.ei1027.toopots.model.tipos;

public enum EstadoReserva {

    PENDIENTE ('P'),
    PAGADA ('A');

    private final char codigo;

    private EstadoReserva(char codigo){
        this.codigo = codigo;
    }

    public char getCodigo(){
        return codigo;
    }

    public static EstadoReserva getEnum(char codigo){
        EstadoReserva [] values = EstadoReserva.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
