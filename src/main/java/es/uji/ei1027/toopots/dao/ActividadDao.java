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
            return jdbcTemplate.query("SELECT * FROM activitat", new ActividadRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Actividad>();
        }
    }

    public Actividad getActividad(int idActividad) {

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM activitat WHERE id=?",
                    new ActividadRowMapper(), idActividad);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addActividad(Actividad act) {

        jdbcTemplate.update("INSERT INTO activitat VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                act.getIdActividad(), act.getTipoActividad(), act.getEstadoActividad(),
                act.getNombre(), act.getDescripción(), act.getDuracion(), act.getFecha(), act.getMinAsistentes(),
                act.getLugarActividad(), act.getPuntoEncuentro(), act.getTextoCliente());
    }

    public void deleteActividad(int idActividad) {

        jdbcTemplate.update("DELETE FROM activitat WHERE id=?", idActividad);
    }

    public void updateActividad(Actividad act) {

        jdbcTemplate.update("UPDATE activitat SET tipusactivitat=?, estat=?, nom=?, descripcio=?, durada=?, data=?, minassistents=?, lloc=?, puntdetrobada=?, textclient=? WHERE id=?",
                act.getTipoActividad(), act.getEstadoActividad(), act.getNombre(), act.getDescripción(),
                act.getDuracion(), act.getFecha(), act.getMinAsistentes(), act.getLugarActividad(),
                act.getPuntoEncuentro(), act.getTextoCliente(), act.getIdActividad());
    }
}
