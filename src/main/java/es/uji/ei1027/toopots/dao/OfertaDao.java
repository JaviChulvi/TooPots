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
            return jdbcTemplate.queryForObject("SELECT * FROM oferta WHERE id=?",
                    new OfertaRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addOferta(Oferta oferta) {
        jdbcTemplate.update("INSERT INTO oferta VALUES(?, ?, ?)",
                oferta.getId(), oferta.getActividad(), oferta.getInstructor());
    }

    public void deleteOferta(int id) {
        jdbcTemplate.update("DELETE FROM oferta WHERE id=?", id);
    }

    public void updateOferta(Oferta oferta) {
        jdbcTemplate.update("UPDATE oferta SET idActividad=?, idInstructor=? where id=?", oferta.getActividad(), oferta.getActividad(), oferta.getId());
    }

}
