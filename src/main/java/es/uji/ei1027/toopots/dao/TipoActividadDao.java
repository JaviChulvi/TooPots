package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoActividadDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<TipoActividad> getTiposActividad() {

        try {
            return jdbcTemplate.query("SELECT * FROM tipoactividad", new TipoActividadRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<TipoActividad>();
        }
    }
    public List<TipoActividad> getTiposActividadPermitidosMonitor(String dniMonitor) {
        // este método devuelve los tipos de actividades que el administrador ha aceptado que cree un monitor (después de haber proporcionado un certificado)
        //select act.id, act.nombre, act.nivel  from tipoactividad as act INNER JOIN acredita as acre ON act.id=acre.idtipoactividad INNER JOIN acreditacion AS cert ON acre.certificado=cert.certificado WHERE cert.dnimonitor='admin';
        try {
            return jdbcTemplate.query("SELECT act.id, act.nombre, act.nivel  FROM tipoactividad AS act " +
                                            "INNER JOIN acredita AS acre ON act.id=acre.idtipoactividad " +
                                            "INNER JOIN acreditacion AS cert ON acre.certificado=cert.certificado " +
                                            "WHERE cert.dnimonitor=?", new TipoActividadRowMapper(),  dniMonitor);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<TipoActividad>();
        }
    }

    public TipoActividad getTipoActividad(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM tipoactividad WHERE id=?",
                    new TipoActividadRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addTipoActividad(TipoActividad tipoActividad) {
        jdbcTemplate.update("INSERT INTO tipoactividad (nombre, nivel) VALUES (?,?)",
                tipoActividad.getNombre(), tipoActividad.getNivelActividad());
    }

    public void deleteTipoActividad(int id) {

        jdbcTemplate.update("DELETE FROM tipoactividad WHERE id=?", id);
    }

    public void updateActividad(TipoActividad tipoActividad) {

        jdbcTemplate.update("UPDATE tipoactividad SET nombre=?, nivel=? WHERE id=?",
                tipoActividad.getNombre(), tipoActividad.getNivelActividad(), tipoActividad.getId());
    }

}