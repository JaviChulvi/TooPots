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
            return jdbcTemplate.query("SELECT * FROM acreditacion", new AcreditacionRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Acreditacion>();
        }
    }

    public List<Acreditacion> getAcreditacionesPendientesMonitor(String dniMonitor){
        try {
            return jdbcTemplate.query("SELECT * FROM acreditacion WHERE estado='pendiente' AND dnimonitor=?", new AcreditacionRowMapper(), dniMonitor);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Acreditacion>();
        }
    }

    public Acreditacion getAcreditacion(String dniMonitor, String certificado) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM acreditacion WHERE dniMonitor=? AND certificado=?",
                    new AcreditacionRowMapper(), dniMonitor, certificado);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("INSERT INTO acreditacion VALUES(?, ?, ?)",
                acreditacion.getCertificado(), acreditacion.getDniMonitor(), acreditacion.getEstado());
    }

    public void deleteAcreditacion(String certificado) {
        jdbcTemplate.update("DELETE FROM acreditacion WHERE certificado=?", certificado);
    }

    public void updateAcreditacion(Acreditacion acreditacion) {
        jdbcTemplate.update("UPDATE acreditacion SET estado=? WHERE certificado=? AND dniMonitor=?",
                acreditacion.getEstado(), acreditacion.getCertificado(), acreditacion.getDniMonitor());
    }
}
