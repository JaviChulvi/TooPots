package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Cliente> getClientes(){
        try {
            return jdbcTemplate.query("SELECT * FROM client", new ClienteRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Cliente>();
        }
    }

    public Cliente getCliente(String dni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM client WHERE id=?",
                    new ClienteRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addCliente(Cliente cliente) {
        jdbcTemplate.update("INSERT INTO client VALUES(?, ?, ?, ?, ?)",
                cliente.getDni(), cliente.getNombre(), cliente.getCorreo(),
                cliente.getGenero(), cliente.getFechaNacimiento());
    }

    public void deleteCliente(String dni) {
        jdbcTemplate.update("DELETE FROM client WHERE id=?", dni);
    }

    public void updateClient(Cliente cliente) {
        jdbcTemplate.update("UPDATE client SET nom=?, email=?, sexe=?, dataNaixement=? where id=?", cliente.getNombre(), cliente.getCorreo(), cliente.getGenero(), cliente.getFechaNacimiento(), cliente.getDni());
    }
}
