package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Entrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EntradaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Entrada> getEntradas(){
        try {
            return jdbcTemplate.query("SELECT * FROM entrada", new EntradaRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Entrada>();
        }
    }

    public Entrada getEntrada(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM entrada WHERE id=?",
                    new EntradaRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addEntrada(Entrada entrada) {
        jdbcTemplate.update("INSERT INTO entrada VALUES(?, ?, ?)",
                entrada.getId(), entrada.getTipo(), entrada.getPrecio());
    }

    public void deleteEntrada(int id) {
        jdbcTemplate.update("DELETE FROM entrada WHERE id=?", id);
    }

    public void updateEntrada(Entrada entrada) {
        jdbcTemplate.update("UPDATE entrada SET tipus=?, preu=? where id=?", entrada.getTipo(),
                entrada.getPrecio(), entrada.getId());
    }
}
