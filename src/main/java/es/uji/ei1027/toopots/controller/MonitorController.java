package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.model.Monitor;
import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private MonitorDao monitorDao;
    private ActividadDao actividadDao;

    @Autowired
    public void setActividadDao(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }
    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao = monitorDao;
    }

    @RequestMapping("/list")
    public String listarMonitore(Model model){
        model.addAttribute("monitores", monitorDao.getMonitores());
        return "monitor/list";
    }

    @RequestMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("monitor", new Monitor());
        return "monitor/registro";
    }

    @RequestMapping(value="/registro", method= RequestMethod.POST)
    public String procesarRegistro(@RequestParam("img") MultipartFile imagen,
                                   @ModelAttribute("monitor") Monitor monitor,
                                   BindingResult bindingResult, Model model, HttpSession session) {

        MonitorValidator monitorValidator = new MonitorValidator();
        monitorValidator.validate(monitor, bindingResult);
        if (bindingResult.hasErrors()){
            return "monitor/registro";
        }
        String nombreImagen;
        try {
            nombreImagen = guardaImagen(imagen);
        } catch (Exception e){
            nombreImagen = "defecto";
        }
        monitor.setFoto(nombreImagen);
        monitor.cifrarContraseña();
        monitorDao.addMonitor(monitor);
        session.setAttribute("tipo", "monitor");
        session.setAttribute("dni", monitor.getDni());
        return "redirect:../acreditacion/add";
    }

    @RequestMapping(value="/verperfil/{id}", method = RequestMethod.GET)
    public String verPerfilMonitor(Model model, @PathVariable String id, HttpSession sesion) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        try {
            int idActividad = (int)sesion.getAttribute("actividad");
            model.addAttribute("idActividad", idActividad);
        } catch (Exception e) {

        }
        return "monitor/verperfil";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
    public String actualizarMonitor(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null ) {
            if (!session.getAttribute("dni").equals(id)) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "monitor/actualizar/"+id);
            return "redirect:../../login";
        } else {
            model.addAttribute("monitor", monitorDao.getMonitor(id));
            return "monitor/actualizar";
        }

    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.POST)
    public String procesarActualizarMonitor(@RequestParam("img") MultipartFile imagen,
                                      @PathVariable String id,
                                      @ModelAttribute("monitor") Monitor monitor,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "monitor/actualizar";
        }
        String nombreImagen;
        try {
            nombreImagen = guardaImagen(imagen);
            monitor.setFoto(nombreImagen);
        } catch (Exception e){
            monitor.setFoto("default.jpg");
        }
        monitorDao.updateMonitor(monitor);

        return "redirect:../list";
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.GET)
    public String eliminarMonitor(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null ) {
            if (!session.getAttribute("dni").equals(id)) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "monitor/eliminar/"+id);
            return "redirect:../../login";
        } else {
            model.addAttribute("dni", id);
            return "monitor/eliminar";
        }
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.POST)
    public String procesarEliminarMonitor(@PathVariable String id, HttpSession session, Model model) {
        try {
            monitorDao.deleteMonitor(id);
            if (session.getAttribute("dni").equals(id)) {
                session.removeAttribute("dni");
                session.removeAttribute("tipo");
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
            return "monitor/eliminar";
        }

        return "redirect:../../registro";
    }



    public String guardaImagen(MultipartFile img) throws Exception{
        String carpeta = System.getProperty("user.dir")+"/img/monitores/";
        String nombreImagen = img.getOriginalFilename();
        byte[] bytes = img.getBytes();
        int random = (int) (Math.random() * 99999) + 1;
        Path ruta = Paths.get(carpeta + random + nombreImagen);
        Files.write(ruta, bytes);
        return random + nombreImagen;
    }

    @RequestMapping("/gestionMonitores")
    public String gestionMonitores(Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "monitor/gestionMonitores");
                return "redirect:../login";
            }
        }
        model.addAttribute("monitores", monitorDao.getMonitoresAceptados());
        return "monitor/gestionMonitores";

    }

    @RequestMapping("/solicitudesMonitores")
    public String solicitudesMonitores(Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "monitor/solicitudesMonitores");
                return "redirect:../login";
            }
        }
        model.addAttribute("monitores", monitorDao.getMonitoresPendientes());
        return "monitor/solicitudesMonitores";
    }

    @RequestMapping("/gestionActividades")
    public String gestion(Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            try {
                // session.getAttribute("tipo") puede ser null
                if (!session.getAttribute("tipo").equals("cliente")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "monitor/gestionActividades");
                return "redirect:../login";
            }
        }
        String dni = (String) session.getAttribute("dni");
        model.addAttribute("dni", dni);
        model.addAttribute("actividades", actividadDao.getActividadesMonitor(dni));
        return "monitor/gestionActividades";

    }

    @RequestMapping(value="/solicitud/{id}/{resultado}", method = RequestMethod.GET)
    public String aceptarRechazarMonitor(Model model, @PathVariable String id, @PathVariable String resultado, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../../../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "monitor/solicitud/"+ id +"/"+ resultado);
                return "redirect:../../../login";
            }
        }
        Monitor monitor = monitorDao.getMonitor(id);
        monitor.setEstado(resultado);
        monitorDao.updateMonitor(monitor);
        model.addAttribute("monitores", monitorDao.getMonitoresPendientes());
        return "monitor/solicitudesMonitores";
    }


}
