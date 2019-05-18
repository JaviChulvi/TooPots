package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.*;
import es.uji.ei1027.toopots.model.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")

public class MainController {

    private ClienteDao clienteDao;
    private MonitorDao monitorDao;
    private ActividadDao actividadDao;
    private TipoActividadDao tipoActividadDao;
    private ImagenDao imagenDao;
    private PrefiereDao prefiereDao;

    @Autowired
    public void setPrefiereDao(PrefiereDao prefiereDao) {
        this.prefiereDao = prefiereDao;
    }
    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao = monitorDao;
    }
    @Autowired
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }
    @Autowired
    public void setActividadDao(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }
    @Autowired
    public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
        this.tipoActividadDao = tipoActividadDao;
    }
    @Autowired
    public void setImagenDao(ImagenDao imagenDao) {
        this.imagenDao = imagenDao;
    }


    @RequestMapping("/")
    public String ajustes() {
        return "redirect:actividades";
    }

    @RequestMapping("/registro")
    public String registro(Model model) {
        return "registro";
    }

    @RequestMapping("/ajustes")
    public String ajustes(Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null) {
            session.setAttribute("urlAnterior", "ajustes");
            return "redirect:login";
        } else {
            String tipo = (String) session.getAttribute("tipo");
            String dni = (String) session.getAttribute("dni");
            model.addAttribute("tipo", tipo);
            model.addAttribute("dni", dni);
            return "ajustes";
        }
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("login") Login login,
                                   BindingResult bindingResult, Model model, HttpSession session) {
        LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(login, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        String tipo = tipoCuenta(login.getDni(), login.getPassword());
        if (tipo != null) {
            String dni = login.getDni();
            session.setAttribute("tipo", tipo);
            session.setAttribute("dni", dni);
            System.out.println("Tipo: " + tipo + " -  DNI: " + dni);

            String urlAnterior = (String) session.getAttribute("urlAnterior");
            if (urlAnterior!=null) {
                return "redirect:" + urlAnterior;
            }

            if (dni.equals("admin")) {
                return "redirect:consulta";
            }

            return "redirect:actividades";
        } else {
            model.addAttribute("errorLogin", "");
            return "login";
        }
    }

    public String tipoCuenta(String dni, String password) {
        Monitor monitor = monitorDao.getMonitor(dni);
        Cliente cliente = clienteDao.getCliente(dni);
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (monitor != null) {
            if (passwordEncryptor.checkPassword(password, monitor.getPassword())) {
                return "monitor";
            }
        } else if (cliente != null) {
            if (passwordEncryptor.checkPassword(password, cliente.getPassword())) {
                return "cliente";
            }
        }
        return null; // devuelve null si no existe la cuenta ni como cliente ni como monitor
    }

    @RequestMapping("/informacion")
    public String informacion(Model model) {
        return "informacion";
    }

    @RequestMapping("/actividades")
    public String actividades(Model model) {
        model.addAttribute("map", getMapFotosPromocionales());
        model.addAttribute("actividades", actividadDao.getActividadesPublicas());
        model.addAttribute("tiposActividades", tipoActividadDao.getTiposActividad());
        model.addAttribute("tipoActividadFiltro", -1);
        return "actividades";
    }

    @RequestMapping("/actividadesPorPreferencia")
    public String actividadesPorPreferencia(Model model, HttpSession session) {
        if ((session.getAttribute("tipo") == null && session.getAttribute("dni")==null) || session.getAttribute("tipo").equals("monitor")) {
            return "redirect:login";
        } else {
            String dniCliente = (String) session.getAttribute("dni");
            List<Prefiere> preferencias = prefiereDao.getPreferenciasCliente(dniCliente);
            if (preferencias.isEmpty()) {
                return "redirect:actividades";
            }
            model.addAttribute("map", getMapFotosPromocionales());
            model.addAttribute("actividades", actividadDao.getActividadesPreferenciasCliente(dniCliente));
            model.addAttribute("tiposActividades", tipoActividadDao.getTiposActividad());
            model.addAttribute("tipoActividadFiltro", -1);
            return "actividades";
        }
    }

    // @RequestParam("radioName") String customer

    @RequestMapping(value="/actividades", method= RequestMethod.POST)
    public String procesarFiltrosActividades(Model model, @RequestParam("filtro") int filtro, HttpSession session) {
        model.addAttribute("map", getMapFotosPromocionales());
        model.addAttribute("actividades", actividadDao.getActividadesPublicasFiltradas(filtro));
        model.addAttribute("tiposActividades", tipoActividadDao.getTiposActividad());
        model.addAttribute("tipoActividadFiltro", filtro);
        session.removeAttribute("listaReserva");
        return "actividades";
    }

    @RequestMapping("/consulta")
    public String consulta(Model model,  HttpSession session) {
        if ((session.getAttribute("tipo") == null && session.getAttribute("dni")==null) || !session.getAttribute("dni").equals("admin")) {
            return "redirect:login";
        } else {
            return "consulta";
        }
    }

    @RequestMapping("/gestion")
    public String gestion(Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            return "redirect:../login";
        } else {
            String dni = (String) session.getAttribute("dni");
            model.addAttribute("dni", dni);
            return "gestion";
        }
    }


    @RequestMapping("/gestionTipoActividades")
    public String gestionTipoActividades(Model model,  HttpSession session) {
        if ((session.getAttribute("tipo") == null && session.getAttribute("dni")==null) || !session.getAttribute("dni").equals("admin")) {
            return "redirect:login";
        } else {
            model.addAttribute("tiposActividades", tipoActividadDao.getTiposActividad());
            return "gestionTipoActividades";
        }
    }

    private HashMap getMapFotosPromocionales() {
        /*
            Creo un map con los ids de las actividades y imagenes promocionales para desde la vista poder
            tener acceso a las imagenes facilmente.

        */
        List<Imagen> lista =  imagenDao.getImagenes();
        HashMap map = new HashMap<Integer, String>();
        for (int i=0; i<lista.size(); i++) {
            map.put(lista.get(i).getIdActividad(), lista.get(i).getImagen());
        }
        return map;
    }
}
