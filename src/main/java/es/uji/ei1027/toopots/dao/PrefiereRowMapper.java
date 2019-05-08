package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Prefiere;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrefiereRowMapper implements RowMapper<Prefiere> {

    @Override
    public Prefiere mapRow(ResultSet rs, int rewNum) throws SQLException {
        Prefiere prefiere = new Prefiere();
        prefiere.setDniCliente(rs.getString("dniCliente"));
        prefiere.setIdTipoActividad(rs.getInt("idTipoActividad"));
        return prefiere;
    }

}