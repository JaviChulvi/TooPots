package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Imagen;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagenRowMapper implements RowMapper<Imagen> {

    @Override
    public Imagen mapRow(ResultSet rs, int rowNum) throws SQLException {
        Imagen imagen = new Imagen();
        imagen.setIdActividad(rs.getInt("idActividad"));
        imagen.setImagen(rs.getString("imagen"));
        return imagen;
    }
}
