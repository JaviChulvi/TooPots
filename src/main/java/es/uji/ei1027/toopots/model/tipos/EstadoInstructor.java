package es.uji.ei1027.toopots.model.tipos;

public enum EstadoInstructor {

    ACEPTADA ('A'),
    RECHAZADA ('R'),
    PENDIENTE ('P');

    private final char codigo;

    private EstadoInstructor(char codigo){
        this.codigo = codigo;
    }

    public char getCodigo(){
        return codigo;
    }

    public static EstadoInstructor getEnum(char codigo){
        EstadoInstructor [] values = EstadoInstructor.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
