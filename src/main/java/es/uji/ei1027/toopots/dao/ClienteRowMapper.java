package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRowMapper implements RowMapper<Cliente> {
    @Override
    public Cliente mapRow(ResultSet rs, int rewNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setCorreo(rs.getString("email"));
        cliente.setGenero(rs.getString("sexo"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        return cliente;
    }
}
