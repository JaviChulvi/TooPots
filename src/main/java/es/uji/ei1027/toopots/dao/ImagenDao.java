package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagenDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Imagen> getImagenes(){
        try {
            return jdbcTemplate.query("SELECT * FROM imatgespromocionals", new ImagenRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Imagen>();
        }
    }

    public Imagen getImagen(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM imatgespromocionals WHERE id=?",
                    new ImagenRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addImagen(Imagen imagen) {
        jdbcTemplate.update("INSERT INTO imatgespromocionals VALUES(?, ?)",
                imagen.getId(), imagen.getImagen());
    }

    public void deleteImagen(int id) {
        jdbcTemplate.update("DELETE FROM imatgespromocionals WHERE id=?", id);
    }

    public void updateImagen(Imagen imagen) {
        jdbcTemplate.update("UPDATE imatgespromocionals SET imagen=? where id=?",
                imagen.getImagen(), imagen.getId());
    }
}
