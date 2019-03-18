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
        reserva.setActividad(rs.getInt("idActivitat"));
        reserva.setCliente(rs.getString("idClient"));
        reserva.setEstadoReserva(rs.getString("estatPagament"));
        reserva.setFechaReserva(rs.getDate("data"));
        reserva.setNumAsistentes(rs.getInt("numAssistents"));
        reserva.setPrecioPersona(rs.getDouble("preuPerPersona"));
        return reserva;
    }
}
