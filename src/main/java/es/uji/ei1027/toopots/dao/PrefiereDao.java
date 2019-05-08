package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Prefiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrefiereDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //LISTAR
    public List<Prefiere> getPreferenciasCliente(String dni){
        try {
            return jdbcTemplate.query("SELECT * FROM Prefiere WHERE dniCliente=?", new PrefiereRowMapper(), dni);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Prefiere>();
        }
    }

    public Prefiere getPrefiere(int idTipoActividad, String dniCliente) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Prefiere WHERE idTipoActividad=? AND dniCliente=?",
                    new PrefiereRowMapper(), idTipoActividad, dniCliente);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addPrefiere(Prefiere prefiere) {
        jdbcTemplate.update("INSERT INTO Prefiere VALUES(?, ?)",
                prefiere.getDniCliente(), prefiere.getIdTipoActividad());
    }

    public void deletePrefiere(int idTipoActividad, String dniCliente) {
        jdbcTemplate.update("DELETE FROM Prefiere WHERE idTipoActividad=? AND dniCliente=?",
                idTipoActividad, dniCliente);
    }

    /*
    // WHERE dniCliente=?
    public List<Reserva> getReservasDni(String dniCliente) {
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva WHERE dniCliente=?", new ReservaRowMapper(), dniCliente);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Reserva>();
        }
    }

    public List<Reserva> getReservasActividad(int idActividad) {
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva WHERE idActividad=?", new ReservaRowMapper(), idActividad);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Reserva>();
        }
    }

    //AÑADIR
    public void addReserva(Reserva reserva) {
        jdbcTemplate.update("INSERT INTO Reserva VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                reserva.getIdActividad(), reserva.getDniCliente(), reserva.getEstadoPago(), reserva.getFecha(),
                reserva.getNumJubilados(), reserva.getNumAdultos(), reserva.getNumMenores(),
                reserva.getPrecioTotal());
    }

    //BORRAR
    public void deleteReserva(int idActividad, String dniCliente) {
        jdbcTemplate.update("DELETE FROM Reserva WHERE idActividad=? AND dniCliente=?",
                idActividad, dniCliente);
    }

    //ACTUALIZAR
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE Reserva SET estadoPago=?, fecha=?, numJubilados=?, numAdultos=?, numMenores=?, precioTotal=? WHERE idActividad=? AND dniCliente=?",
                reserva.getEstadoPago(), reserva.getFecha(), reserva.getNumJubilados(), reserva.getNumAdultos(),
                reserva.getNumMenores(), reserva.getPrecioTotal(), reserva.getIdActividad(),
                reserva.getDniCliente());
    }
*/
}
