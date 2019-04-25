package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consulta")
public class ConsultaDatosController {

    private ClienteDao clienteDao;
    private ActividadDao actividadDao;
    private ReservaDao reservaDao;

    @Autowired
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Autowired
    public void setActividadDao(ActividadDao actividadDao) { this.actividadDao = actividadDao; }

    @Autowired
    public void setReservaDao(ReservaDao reservaDao) { this.reservaDao = reservaDao; }

    @RequestMapping("/listadoClientes")
    public String listCliente(Model model){
        model.addAttribute("clientes", clienteDao.getClientes());
        return "consulta/listadoClientes";
    }

    @RequestMapping("/listadoActividades")
    public String listActividades(Model model) {
        model.addAttribute("actividades", actividadDao.getActividades());
        return "consulta/listadoActividades";
    }

    @RequestMapping("/listadoReservas")
    public String listReservas(Model model) {
        model.addAttribute("reservas", reservaDao.getReservas());
        return "consulta/listadoReservas";
    }
}
