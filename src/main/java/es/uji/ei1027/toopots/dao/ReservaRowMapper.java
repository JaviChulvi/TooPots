package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ReservaRowMapper implements RowMapper<Reserva> {
    @Override
    public Reserva mapRow(ResultSet rs, int rewNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdActividad(rs.getInt("idActividad"));
        reserva.setDniCliente(rs.getString("dniCliente"));
        reserva.setEstadoPago(rs.getString("estadoPago"));
        // pasar la fecha de la base de datos a un Objeto de tipo LocalDate
        reserva.setFecha(rs.getObject("fecha", LocalDate.class));
        reserva.setNumJubilados(rs.getInt("numJubilados"));
        reserva.setNumAdultos(rs.getInt("numAdultos"));
        reserva.setNumMenores(rs.getInt("numMenores"));
        reserva.setPrecioTotal(rs.getDouble("precioTotal"));
        return reserva;
    }
}
