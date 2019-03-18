package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReservaRowMapper implements RowMapper<Reserva> {
    @Override
    public Reserva mapRow(ResultSet rs, int rewNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setNumTransaccion(rs.getInt("numTransaccio"));
        reserva.setActividad(rs.getInt("actividad"));
        reserva.setCliente(rs.getString("cliente"));
        reserva.setCliente(rs.getString("estadoReserva"));
        reserva.setFechaReserva(rs.getDate("fechaReserva"));
        reserva.setNumAsistentes(rs.getInt("numAsistentes"));
        reserva.setPrecioPersona(rs.getDouble("precioPersona"));
        return reserva;
    }
}
