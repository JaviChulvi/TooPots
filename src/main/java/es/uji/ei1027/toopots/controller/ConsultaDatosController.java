package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPBinding;

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
    public String listCliente(Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || !session.getAttribute("dni").equals("admin")) {
            if (session.getAttribute("dni") != "admin" && session.getAttribute("dni")!=null) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "consulta/listadoClientes");
            return "redirect:../login";
        }
        model.addAttribute("clientes", clienteDao.getClientes());
        return "consulta/listadoClientes";
    }

    @RequestMapping("/listadoActividades")
    public String listActividades(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || !session.getAttribute("dni").equals("admin")) {
            if (session.getAttribute("dni") != "admin" && session.getAttribute("dni")!=null) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "consulta/listadoActividades");
            return "redirect:../login";
        }
        model.addAttribute("actividades", actividadDao.getActividades());
        return "consulta/listadoActividades";
    }

    @RequestMapping("/listadoReservas")
    public String listReservas(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || !session.getAttribute("dni").equals("admin")) {
            if (session.getAttribute("dni") != "admin" && session.getAttribute("dni")!=null) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "consulta/listadoReservas");
            return "redirect:../login";
        }
        model.addAttribute("reservas", reservaDao.getReservas());
        return "consulta/listadoReservas";
    }
}
