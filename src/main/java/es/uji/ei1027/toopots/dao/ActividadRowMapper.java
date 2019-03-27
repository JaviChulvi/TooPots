package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActividadRowMapper implements RowMapper<Actividad> {

    @Override
    public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actividad act = new Actividad();
        act.setIdActividad(rs.getInt("id"));
        act.setTipoActividad(rs.getString("tipusActivitat"));
        act.setEstadoActividad(rs.getString("estat"));
        act.setNombre(rs.getString("nom"));
        act.setDescripción(rs.getString("descripcio"));
        act.setFecha(rs.getDate("data"));
        act.setMinAsistentes(rs.getInt("minAssistents"));
        act.setLugarActividad(rs.getString("lloc"));
        act.setPuntoEncuentro(rs.getString("puntDeTrobada"));
        act.setTextoCliente(rs.getString("textClient"));
        return act;
    }
}
