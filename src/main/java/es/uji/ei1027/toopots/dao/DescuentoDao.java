package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Descuento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DescuentoDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Descuento> getDescuentos(){
        try {
            return jdbcTemplate.query("SELECT * FROM descuento", new DescuentoRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Descuento>();
        }
    }

    public Descuento getDescuento(String nombre) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM descuento WHERE nombre=?",
                    new DescuentoRowMapper(), nombre);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addDescuento(Descuento descuento) {
        jdbcTemplate.update("INSERT INTO descuento VALUES(?, ?, ?, ?)",
                descuento.getNombre(), descuento.getDescripcion(), descuento.getDescuento(), descuento.getTipo());
    }

    public void deleteDescuento(String nombre) {
        jdbcTemplate.update("DELETE FROM descuento WHERE nombre=?", nombre);
    }

    public void updateDescuento(Descuento descuento) {
        jdbcTemplate.update("UPDATE oferta SET descripcion=?, descuento=?, tipo=? where nombre=?",
                descuento.getDescripcion(), descuento.getDescuento(), descuento.getTipo(), descuento.getNombre());
    }

}
