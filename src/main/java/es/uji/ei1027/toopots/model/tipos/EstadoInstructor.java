package es.uji.ei1027.toopots.model.tipos;

public enum EstadoInstructor {

    ACEPTADA ("aceptada"),
    RECHAZADA ("rechazada"),
    PENDIENTE ("pendiente");

    private final String codigo;

    private EstadoInstructor(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }

    public static EstadoInstructor getEnum(String codigo){
        EstadoInstructor [] values = EstadoInstructor.values();
        for (int i = 0; i < values.length; i++)
            if (values[i].getCodigo() == codigo)
                return values[i];

        return null;
    }
}
