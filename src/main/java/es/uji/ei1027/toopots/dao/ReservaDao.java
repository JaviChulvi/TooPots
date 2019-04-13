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

    public Reserva getReserva(int numReserva) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Reserva WHERE numTransaccion=?",
                    new ReservaRowMapper(), numReserva);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    //AÑADIR
    public void addReserva(Reserva reserva) {
        jdbcTemplate.update("INSERT INTO Reserva VALUES(?, ?, ?, ?, ?, ?, ?. ?)",
                reserva.getNumTransaccion(), reserva.getIdActividad(), reserva.getDniCliente(),
                reserva.getEstado(), reserva.getFecha(), reserva.getNumAsistentes(),
                reserva.getPrecioPersona(), reserva.getPrecioTotal());
    }

    //BORRAR
    public void deleteReserva(int numReserva) {
        jdbcTemplate.update("DELETE FROM Reserva WHERE numTransaccion=?", numReserva);
    }

    //ACTUALIZAR
    public void updateReserva(Reserva reserva) {
        jdbcTemplate.update("UPDATE Reserva SET idActividad=?, dniCliente=?, estadoPago=?, fecha=?, numAsistentes=?, precioPorPersona=?, precioTotal=? where numTransaccio=?",
                reserva.getIdActividad(), reserva.getDniCliente(), reserva.getEstado(), reserva.getFecha(),
                reserva.getNumAsistentes(), reserva.getPrecioPersona(), reserva.getPrecioTotal(),reserva.getNumTransaccion());
    }

}
