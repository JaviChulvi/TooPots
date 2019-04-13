package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Oferta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfertaRowMapper implements RowMapper<Oferta> {

    @Override
    public Oferta mapRow(ResultSet rs, int rewNum) throws SQLException {
        Oferta oferta = new Oferta();
        oferta.setIdActividad(rs.getInt("idActividad"));
        oferta.setDniMonitor(rs.getString("dniMonitor"));
        return oferta;
    }

}
