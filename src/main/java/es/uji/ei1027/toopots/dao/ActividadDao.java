package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActividadDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Actividad> getActividades() {

        try {
            return jdbcTemplate.query("SELECT * FROM actividad", new ActividadRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }

    public Actividad getActividad(int idActividad) {

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM actividad WHERE id=?",
                    new ActividadRowMapper(), idActividad);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addActividad(Actividad act) {

        jdbcTemplate.update("INSERT INTO actividad VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                act.getId(), act.getIdTipoActividad(), act.getEstado(),
                act.getNombre(), act.getDescripción(), act.getDuracion(), act.getFecha(), act.getMinAsistentes(),
                act.getMaxAsistentes(), act.getLugar(), act.getPuntoDeEncuentro(), act.getHoraDeEncuentro());
    }

    public void deleteActividad(int idActividad) {

        jdbcTemplate.update("DELETE FROM actividad WHERE id=?", idActividad);
    }

    public void updateActividad(Actividad act) {

        jdbcTemplate.update("UPDATE actividad SET idTipoActividad=?, estado=?, nombre=?, descripcion=?, duradacion=?, fecha=?, " +
                        "minAsistentes=?, maxAsistentes=?, lugar=?, puntoDeEncuentro=?, horaDeEncuentro=? WHERE id=?",
                act.getIdTipoActividad(), act.getEstado(), act.getNombre(), act.getDescripción(),
                act.getDuracion(), act.getFecha(), act.getMinAsistentes(), act.getMaxAsistentes(),act.getLugar(),
                act.getPuntoDeEncuentro(), act.getHoraDeEncuentro(),act.getId());
    }

    public List<Actividad> getActividadesMonitor(String dniMonitor) {

        try {
            return jdbcTemplate.query("SELECT * FROM actividad WHERE ", new ActividadRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }
}
