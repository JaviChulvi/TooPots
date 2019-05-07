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

    public List<Entrada> getEntradasActividad(int id) {
        try {
            return jdbcTemplate.query("SELECT * FROM entrada WHERE idactividad=?",
                    new EntradaRowMapper(), id);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Entrada>();
        }
    }

    public void addEntrada(Entrada entrada) {
        jdbcTemplate.update("INSERT INTO entrada VALUES(?, ?, ?)",
                entrada.getIdActividad(), entrada.getTipo(), entrada.getPrecio());
    }

    public void deleteEntrada(int id) {
        jdbcTemplate.update("DELETE FROM entrada WHERE idactividad=?", id);
    }

    public void updateEntrada(Entrada entrada) {
        jdbcTemplate.update("UPDATE entrada SET preciobruto=? where tipo=? AND idactividad=?",
                entrada.getPrecio(),  entrada.getTipo(), entrada.getIdActividad());
    }
}
