package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActividadRowMapper implements RowMapper<Actividad> {

    @Override
    public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actividad act = new Actividad();
        act.setId(rs.getInt("id"));
        act.setIdTipoActividad(rs.getInt("idTipoActividad"));
        act.setEstado(rs.getString("estado"));
        act.setNombre(rs.getString("nombre"));
        act.setDescripcion(rs.getString("descripcion"));
        act.setDuracion(rs.getTime("duracion"));
        act.setFecha(rs.getDate("fecha"));
        act.setMinAsistentes(rs.getInt("minAsistentes"));
        act.setMaxAsistentes(rs.getInt("maxAsistentes"));
        act.setLugar(rs.getString("lugar"));
        act.setPuntoDeEncuentro(rs.getString("puntoDeEncuentro"));
        act.setHoraDeEncuentro(rs.getTime("horaDeEncuentro"));
        act.setMonitor(rs.getString("monitor"));
        act.setDescuentoAplicado(rs.getString("descuentoAplicado"));
        act.setInscritos(rs.getInt("inscritos"));
        return act;
    }
}
