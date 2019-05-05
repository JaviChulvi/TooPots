package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Acredita;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcreditaRowMapper implements RowMapper<Acredita> {
    @Override
    public Acredita mapRow(ResultSet rs, int rowNum) throws SQLException {
        Acredita acredita = new Acredita();
        acredita.setCertificado(rs.getString("certificado"));
        acredita.setTipoActividad(rs.getInt("idTipoActividad"));
        return acredita;
    }
}
