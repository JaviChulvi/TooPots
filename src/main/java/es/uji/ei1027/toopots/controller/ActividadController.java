package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ImagenDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Imagen;
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
@RequestMapping("/actividad")
public class ActividadController {

    private ActividadDao actividadDao;
    private TipoActividadDao tipoActividadDao;
    private ImagenDao imagenDao;

    @Autowired
    public void setEntradaDao(ActividadDao actividadDao) {
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

    @RequestMapping("/list")
    public String listActividades(Model model){
        model.addAttribute("actividades", actividadDao.getActividades());
        return "actividad/list";
    }

    @RequestMapping("/crear")
    public String addActividad(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            return "redirect:../login";
        } else {
            model.addAttribute("actividad", new Actividad());
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividad());
            return "actividad/crear";
        }
    }

    @RequestMapping(value="/crear", method= RequestMethod.POST)
    public String processAddSubmit(@RequestParam("img") MultipartFile imgFile, @ModelAttribute("actividad") Actividad actividad,
                                   BindingResult bindingResult, HttpSession session) {
        ActividadValidator actividadValidator = new ActividadValidator();
        actividad.setEstado("abierta");
        actividad.setMonitor((String) session.getAttribute("dni"));
        actividadValidator.validate(actividad, bindingResult);
        if (bindingResult.hasErrors()) {
            return "actividad/crear";
        }
        actividadDao.addActividad(actividad);
        Imagen imagen = new Imagen();
        String nombreImagen;
        try {
            nombreImagen = guardaImagen(imgFile);
            imagen.setImagen(nombreImagen);
        } catch (Exception e){
            imagen.setImagen("default-actividad.jpg");
        }
        imagen.setIdActividad(actividadDao.getLastId());
        imagenDao.addImagen(imagen);
        return "redirect:../gestion";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
    public String editActividad(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            return "redirect:../../login";
        } else {
            model.addAttribute("actividad", actividadDao.getActividad(id));
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividad());
            return "actividad/actualizar";
        }
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@RequestParam("img") MultipartFile imgFile,
                                      @PathVariable int id,
                                      @ModelAttribute("actividad") Actividad actividad,
                                      BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "actividad/actualizar";
        }
        actividad.setMonitor((String) session.getAttribute("dni"));
        actividadDao.updateActividad(actividad);
        if (imgFile!=null) {
            Imagen imagen = new Imagen();
            try {
                String nombreImagen = guardaImagen(imgFile);
                imagen.setImagen(nombreImagen);
            } catch (Exception e){
                imagen.setImagen("default-actividad.jpg");
            }
            imagen.setIdActividad(actividadDao.getLastId());
            imagenDao.addImagen(imagen);
        }
        return "redirect:../../gestion";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        actividadDao.deleteActividad(id);
        return "redirect:../list";
    }

    public String guardaImagen(MultipartFile img) throws Exception{
        String carpeta = System.getProperty("user.dir")+"/img/actividades/";
        String nombreImagen = img.getOriginalFilename();
        byte[] bytes = img.getBytes();
        Path ruta = Paths.get(carpeta + nombreImagen);
        Files.write(ruta, bytes);
        return nombreImagen;
    }
}
