package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservaRowMapper implements RowMapper<Reserva> {
    @Override
    public Reserva mapRow(ResultSet rs, int rewNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setNumTransaccion(rs.getString("numTransaccio"));
        //reserva.setActividad();
        //reserva.setCliente();
        //reserva.setEstadoReserva();
        reserva.setFechaReserva(rs.getDate("fechaReserva"));
        reserva.setNumAsistentes(rs.getInt("numAsistentes"));
        reserva.setPrecioPersona(rs.getDouble("precioPersona"));
        return reserva;
    }
}
