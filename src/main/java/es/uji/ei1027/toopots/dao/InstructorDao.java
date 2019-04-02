package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Instructor> getInstructores(){
        try {
            return jdbcTemplate.query("SELECT * FROM instructor", new InstructorRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Instructor>();
        }
    }

    public Instructor getInstructor(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM instructor WHERE id=?",
                    new InstructorRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addInstructor(Instructor instructor) {
        jdbcTemplate.update("INSERT INTO instructor VALUES(?, ?, ?, ?, ?)",
                instructor.getId(), instructor.getEstadoInstructor(), instructor.getNombre(),
                instructor.getCorreo(), instructor.getIban());
    }

    public void deleteInstructor(int id) {
        jdbcTemplate.update("DELETE FROM instructor WHERE id=?", id);
    }

    public void updateInstructor(Instructor instructor) {
        jdbcTemplate.update("UPDATE instructor SET estat=?, nom=?, email=?, iban=? where id=?",
                instructor.getEstadoInstructor(), instructor.getNombre(),
                instructor.getCorreo(), instructor.getIban(), instructor.getId());
    }
}
