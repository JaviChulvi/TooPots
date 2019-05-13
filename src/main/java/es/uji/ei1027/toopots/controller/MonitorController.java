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
    public String verPerfilMonitor(Model model, @PathVariable String id) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        return "monitor/verperfil";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
    public String actualizarMonitor(Model model, @PathVariable String id) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        return "monitor/actualizar";
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
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || !session.getAttribute("dni").equals(id) || !session.getAttribute("tipo").equals("monitor")) {
            return "redirect:../../login";
        } else {
            model.addAttribute("dni", id);
            return "monitor/eliminar";
        }
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.POST)
    public String procesarEliminarMonitor(@PathVariable String id, HttpSession session) {
        monitorDao.deleteMonitor(id);
        session.removeAttribute("dni");
        session.removeAttribute("tipo");
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
    public String gestionMonitores(Model model){
        model.addAttribute("monitores", monitorDao.getMonitoresAceptados());
        return "monitor/gestionMonitores";
    }

    @RequestMapping("/solicitudesMonitores")
    public String solicitudesMonitores(Model model){
        model.addAttribute("monitores", monitorDao.getMonitoresPendientes());
        return "monitor/solicitudesMonitores";
    }

    @RequestMapping("/gestionActividades")
    public String gestion(Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            return "redirect:../login";
        } else {
            String dni = (String) session.getAttribute("dni");
            model.addAttribute("dni", dni);
            model.addAttribute("actividades", actividadDao.getActividadesMonitor(dni));
            return "monitor/gestionActividades";
        }
    }

    @RequestMapping(value="/solicitud/{id}/{resultado}", method = RequestMethod.GET)
    public String aceptarRechazarMonitor(Model model, @PathVariable String id, @PathVariable String resultado) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        Monitor monitor = monitorDao.getMonitor(id);
        monitor.setEstado(resultado);
        monitorDao.updateMonitor(monitor);
        return "monitor/solicitudesMonitores";
    }


}
