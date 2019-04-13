package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfertaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Oferta> getOfertas(){
        try {
            return jdbcTemplate.query("SELECT * FROM oferta", new OfertaRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Oferta>();
        }
    }

    public Oferta getOferta(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM oferta WHERE idActividad=?",
                    new OfertaRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addOferta(Oferta oferta) {
        jdbcTemplate.update("INSERT INTO oferta VALUES(?, ?)",
                oferta.getIdActividad(), oferta.getDniMonitor());
    }

    public void deleteOferta(int id) {
        jdbcTemplate.update("DELETE FROM oferta WHERE idActividad=?", id);
    }

    public void updateOferta(Oferta oferta) {
        jdbcTemplate.update("UPDATE oferta SET dniMonitor=? where idActividad=?", oferta.getIdActividad(), oferta.getDniMonitor());
    }

}
