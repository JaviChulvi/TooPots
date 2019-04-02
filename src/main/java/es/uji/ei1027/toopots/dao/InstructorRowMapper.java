package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Instructor;
import es.uji.ei1027.toopots.model.tipos.EstadoInstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorRowMapper implements RowMapper<Instructor> {

    @Override
    public Instructor mapRow(ResultSet rs, int rewNum) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setId(rs.getInt("id"));
        instructor.setEstadoInstructor(rs.getString("estat"));
        instructor.setNombre(rs.getString("nom"));
        instructor.setCorreo(rs.getString("email"));
        instructor.setIban(rs.getString("iban"));

        return instructor;
    }
}
