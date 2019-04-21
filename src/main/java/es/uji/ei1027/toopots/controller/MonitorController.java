package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.model.Monitor;
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

@Controller
@RequestMapping("/monitor")
public class MonitorController {

    private MonitorDao monitorDao;

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
        return "redirect:list";
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
            monitorDao.updateMonitorConFoto(monitor);
        } catch (Exception e){
            monitorDao.updateMonitorSinFoto(monitor);
        }
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
        Path ruta = Paths.get(carpeta + nombreImagen);
        Files.write(ruta, bytes);
        return nombreImagen;
    }
}
