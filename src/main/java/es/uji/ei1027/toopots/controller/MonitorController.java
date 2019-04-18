package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String listMonitor(Model model){
        model.addAttribute("monitores", monitorDao.getMonitores());
        return "monitor/list";
    }

    @RequestMapping("/add")
    public String addMonitor(Model model) {
        model.addAttribute("monitor", new Monitor());
        return "monitor/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@RequestParam("img") MultipartFile imagen,
                                   @ModelAttribute("monitor") Monitor monitor,
                                   BindingResult bindingResult, Model model) {

        MonitorValidator monitorValidator = new MonitorValidator();
        monitorValidator.validate(monitor, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("cliente", new Cliente());
            return "registro";
        }
        String nombreImagen;
        try {
            nombreImagen = guardaImagen(imagen);
        } catch (Exception e){
            nombreImagen = "defecto";
        }
        monitor.setFoto(nombreImagen);
        monitorDao.addMonitor(monitor);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editmonitor(Model model, @PathVariable String id) {
        model.addAttribute("monitor", monitorDao.getMonitor(id));
        return "monitor/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("monitor") Monitor monitor,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "monitor/update";
        monitorDao.updateMonitor(monitor);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        monitorDao.deleteMonitor(id);
        return "redirect:../list";
    }


    public String guardaImagen(MultipartFile img) throws Exception{
        String carpeta = System.getProperty("user.dir")+"/src/main/resources/static/img/";
        System.out.println(carpeta);
        String nombreImagen = img.getOriginalFilename();
        byte[] bytes = img.getBytes();
        Path ruta = Paths.get(carpeta + nombreImagen);
        Files.write(ruta, bytes);
        return nombreImagen;
    }
}
