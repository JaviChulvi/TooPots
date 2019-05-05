package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acredita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AcreditaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Acredita> getAcreditas(){
        try {
            return jdbcTemplate.query("SELECT * FROM acredita", new AcreditaRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Acredita>();
        }
    }


    /*public List<Acredita> getAcreditasMonitorPendiente(String dniMonitor){
        try {
            return jdbcTemplate.query("SELECT x.idtipoactividad , x.certificado FROM acredita AS x INNER JOIN acreditacion AS a ON (x.certificado = a.certificado) WHERE a.estado='pendiente' AND a.dnimonitor=?", new AcreditaRowMapper(), dniMonitor);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Acredita>();
        }
    }*/

    public List<Acredita> getAcreditasMonitor(String dniMonitor){
        try {
            return jdbcTemplate.query("SELECT x.idtipoactividad , x.certificado FROM acredita AS x INNER JOIN acreditacion AS a ON (x.certificado = a.certificado) WHERE a.dnimonitor=?", new AcreditaRowMapper(), dniMonitor);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Acredita>();
        }
    }

    public Acredita getAcredita(String tipoActividad, String certificado) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM acredita WHERE idTipoActividad=? AND certificado=?",
                    new AcreditaRowMapper(), tipoActividad, certificado);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addAcredita(Acredita acredita) {
        jdbcTemplate.update("INSERT INTO acredita VALUES(?, ?)",
                acredita.getTipoActividad(), acredita.getCertificado());
    }

    public void deleteAcredita(String tipoActividad, String certificado) {
        jdbcTemplate.update("DELETE FROM acredita WHERE idTipoActividad=? AND certificado=?",
                tipoActividad, certificado);
    }

    public void updateAcredita(Acredita acredita) {}
}
