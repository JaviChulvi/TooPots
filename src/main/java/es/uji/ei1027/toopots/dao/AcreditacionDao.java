package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acreditacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AcreditacionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Acreditacion> getAcreditaciones(){
        try {
            return jdbcTemplate.query("SELECT * FROM acreditacio", new AcreditacionRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Acreditacion>();
        }
    }

    public Acreditacion getAcreditacion(int idInstructor, String certificado) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM acreditacio WHERE idinstructor=? AND certificat=?",
                    new AcreditacionRowMapper(), idInstructor, certificado);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("INSERT INTO acreditacio VALUES(?, ?, ?)",
                acreditacion.getCertificado(), acreditacion.getIdInstructor(), acreditacion.getEstado());
    }

    public void deleteAcreditacion(int id, String certificado) {
        jdbcTemplate.update("DELETE FROM acreditacio WHERE certificat=? AND idinstructor=?", id, certificado);
    }

    public void updateAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("UPDATE acreditacio SET estat=? WHERE certificat=? AND idinstructor=?",
                acreditacion.getEstado(), acreditacion.getCertificado(), acreditacion.getIdInstructor());
    }
}
