package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acreditacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcreditacionRowMapper implements RowMapper<Acreditacion> {
    @Override
    public Acreditacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setCertificado(rs.getString("certificado"));
        acreditacion.setIdInstructor(rs.getString("dniMonitor"));
        acreditacion.setEstado(rs.getString("estado"));
        return acreditacion;
    }
}
