package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Entrada;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntradaRowMapper implements RowMapper<Entrada> {

    @Override
    public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
        Entrada entrada = new Entrada();
        entrada.setId(rs.getInt("id"));
        entrada.setTipo(rs.getString("tipus"));
        entrada.setPrecio(rs.getFloat("preu"));
        return entrada;
    }
}
