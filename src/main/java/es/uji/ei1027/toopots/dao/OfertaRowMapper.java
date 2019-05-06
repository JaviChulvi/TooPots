package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Oferta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfertaRowMapper implements RowMapper<Oferta> {

    @Override
    public Oferta mapRow(ResultSet rs, int rewNum) throws SQLException {

        Oferta oferta = new Oferta();
        oferta.setNombre(rs.getString("nombre"));
        oferta.setDescripcion(rs.getString("descripcion"));
        oferta.setDescuento(rs.getFloat("descuento"));
        oferta.setTipo(rs.getString("tipo"));

        return oferta;
    }

}
