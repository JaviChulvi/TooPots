package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Descuento;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DescuentoRowMapper implements RowMapper<Descuento> {

    @Override
    public Descuento mapRow(ResultSet rs, int rewNum) throws SQLException {

        Descuento descuento = new Descuento();
        descuento.setNombre(rs.getString("nombre"));
        descuento.setDescripcion(rs.getString("descripcion"));
        descuento.setDescuento(rs.getFloat("descuento"));
        descuento.setTipo(rs.getString("tipo"));

        return descuento;
    }

}
