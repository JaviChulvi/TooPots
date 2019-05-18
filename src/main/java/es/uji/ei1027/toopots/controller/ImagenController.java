package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ImagenDao;
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
@RequestMapping("/imagenpromocional")
public class ImagenController {

    private ImagenDao imagenDao;

    @Autowired
    public void setImagenDao(ImagenDao imagenDao) {
        this.imagenDao = imagenDao;
    }

    @RequestMapping("/list/{id}")
    public String listImagen(@PathVariable int id, Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null ) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "imagenpromocional/list/"+id);
            return "redirect:../../login";
        } else {
            model.addAttribute("imagenes", imagenDao.getImagenesActividad(id));
            model.addAttribute("idActividad", id);
            return "imagenpromocional/list";
        }
    }

    @RequestMapping("/add/{idActividad}")
    public String addImagen(Model model, @PathVariable int idActividad, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null ) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "imagenpromocional/add/"+idActividad);
            return "redirect:../../login";
        } else {
            model.addAttribute("imagen", new Imagen());
            model.addAttribute("idActividad", idActividad);
            return "imagenpromocional/add";
        }
    }

    @RequestMapping(value="/add/{id}", method= RequestMethod.POST)
    public String processAddSubmit(@PathVariable int id, @RequestParam("img") MultipartFile imgFile) {

        Imagen imagen = new Imagen();
        String nombreImagen;
        try {
            nombreImagen = guardaImagen(imgFile);
            imagen.setImagen(nombreImagen);
        } catch (Exception e){
            imagen.setImagen("default-actividad.jpg");
        }
        imagen.setIdActividad(id);
        imagenDao.addImagen(imagen);
        return "redirect:../list/"+id;
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editImagen(Model model, @PathVariable int id) {
        model.addAttribute("imagen", imagenDao.getImagen(id));
        return "imagenpromocional/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id,
                                      @ModelAttribute("imagen") Imagen imagen) {

        imagenDao.updateImagen(imagen);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{idActividad}/{nombreImagen}")
    public String processDelete(@PathVariable int idActividad, @PathVariable String nombreImagen, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null ) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "imagenpromocional/delete/"+idActividad+"/"+nombreImagen);
            return "redirect:../../../login";
        } else {
            imagenDao.deleteImagen(idActividad, nombreImagen);
            return "redirect:../../list/"+idActividad;
        }
    }

    public String guardaImagen(MultipartFile img) throws Exception{
        String carpeta = System.getProperty("user.dir")+"/img/actividades/";
        String nombreImagen = img.getOriginalFilename();
        byte[] bytes = img.getBytes();
        int random = (int) (Math.random() * 99999) + 1;
        Path ruta = Paths.get(carpeta + random + nombreImagen);
        Files.write(ruta, bytes);
        return random + nombreImagen;
    }
}
