package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoActividadRowMapper implements RowMapper<TipoActividad> {
    @Override
    public TipoActividad mapRow(ResultSet rs, int rewNum) throws SQLException {
        TipoActividad tipoActividad = new TipoActividad();
        tipoActividad.setId(rs.getInt("id"));
        tipoActividad.setNombre(rs.getString("nombre"));
        tipoActividad.setNivelActividad(rs.getString("nivel"));
        return tipoActividad;
    }
}
