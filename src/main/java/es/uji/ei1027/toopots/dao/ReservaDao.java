package es.uji.ei1027.toopots.dao;

import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //LISTAR
    public List<Reserva> getReservas(){
        try {
            return jdbcTemplate.query("SELECT * FROM Reserva", new ReservaRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<Reserva>();
        }
    }

    public Reserva getReserva(int idActividad, int dniCliente) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Reserva WHERE idActividad=? AND dniCliente=?",
                    new ReservaRowMapper(), idActividad, dniCliente);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    //AÑADIR
    public void addReserva(Reserva reserva) {
        jdbcTemplate.update("INSERT INTO Reserva VALUES(?, ?, ?, ?, ?, ?, ?)",
                reserva.getIdActividad(), reserva.getDniCliente(), reserva.getEstadoPago(), reserva.getFecha(),
                reserva.getNumAdultos(), reserva.getNumMenores(), reserva.getPrecioPorPersona());
    }

    //BORRAR
    public void deleteReserva(int idActividad, int dniCliente) {
        jdbcTemplate.update("DELETE FROM Reserva WHERE idActividad=? AND dniCliente=?",
                idActividad, dniCliente);
    }

    //ACTUALIZAR
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE Reserva SET estadoPago=?, fecha=?, numAdultos=?, numMenores=?, precioPorPersona=? WHERE idActividad=? AND dniCliente=?",
                reserva.getEstadoPago(), reserva.getFecha(), reserva.getNumAdultos(), reserva.getNumMenores(),
                reserva.getPrecioPorPersona(), reserva.getIdActividad(), reserva.getDniCliente());
    }

}
