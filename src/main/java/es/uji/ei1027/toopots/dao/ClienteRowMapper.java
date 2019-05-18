package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClienteRowMapper implements RowMapper<Cliente> {
    @Override
    public Cliente mapRow(ResultSet rs, int rewNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setDni(rs.getString("dni"));
        cliente.setPassword(rs.getString("contraseña"));
        cliente.setPassword2(rs.getString("contraseña"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setCorreo(rs.getString("email"));
        cliente.setGenero(rs.getString("sexo"));
        // pasar la fecha de la base de datos a un Objeto de tipo LocalDate
        cliente.setFechaNacimiento(rs.getObject("fechaNacimiento", LocalDate.class));
        return cliente;
    }
}
