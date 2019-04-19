package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MonitorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Monitor> getMonitores(){
        try {
            return jdbcTemplate.query("SELECT * FROM monitor", new MonitorRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Monitor>();
        }
    }

    public Monitor getMonitor(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM monitor WHERE dni=?",
                    new MonitorRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addMonitor(Monitor monitor) {
        jdbcTemplate.update("INSERT INTO monitor VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                monitor.getDni(), monitor.getEstado(), monitor.getNombre(), monitor.getDomicilio(),
                monitor.getEmail(), monitor.getIban(), monitor.getFoto(), monitor.getPassword());
    }

    public void deleteMonitor(int dni) {
        jdbcTemplate.update("DELETE FROM monitor WHERE dni=?", dni);
    }

    public void updateMonitorConFoto(Monitor monitor) {
        jdbcTemplate.update("UPDATE monitor SET estado=?, nombre=?, domicilio=? ,email=?, iban=?, foto=?, contraseña=? where dni=?",
                monitor.getEstado(), monitor.getNombre(), monitor.getDomicilio(),
                monitor.getEmail(), monitor.getIban(),  monitor.getFoto() ,monitor.getPassword(),monitor.getDni());
    }
    public void updateMonitorSinFoto(Monitor monitor) {
        jdbcTemplate.update("UPDATE monitor SET estado=?, nombre=?, domicilio=? ,email=?, iban=?, contraseña=? where dni=?",
                monitor.getEstado(), monitor.getNombre(), monitor.getDomicilio(),
                monitor.getEmail(), monitor.getIban() ,monitor.getPassword(),monitor.getDni());
    }
}
