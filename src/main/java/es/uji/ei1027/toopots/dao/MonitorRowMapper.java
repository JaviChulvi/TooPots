package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonitorRowMapper implements RowMapper<Monitor> {

    @Override
    public Monitor mapRow(ResultSet rs, int rewNum) throws SQLException {
        Monitor monitor = new Monitor();
        monitor.setDni(rs.getString("dni"));
        monitor.setEstado(rs.getString("estado"));
        monitor.setNombre(rs.getString("nombre"));
        monitor.setDomicilio(rs.getString("domicilio"));
        monitor.setCorreo(rs.getString("email"));
        monitor.setIban(rs.getString("iban"));
        monitor.setFoto(rs.getString("foto"));

        return monitor;
    }
}
