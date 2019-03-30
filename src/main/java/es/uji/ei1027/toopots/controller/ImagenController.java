package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ImagenDao;
import es.uji.ei1027.toopots.model.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/imagen")
public class ImagenController {

    private ImagenDao imagenDao;

    @Autowired
    public void setImagenDao(ImagenDao imagenDao) {
        this.imagenDao = imagenDao;
    }

    @RequestMapping("/list")
    public String listImagen(Model model){
        model.addAttribute("imagenes", imagenDao.getImagenes());
        return "imagen/list";
    }

    @RequestMapping("/add")
    public String addImagen(Model model) {
        model.addAttribute("imagen", new Imagen());
        return "imagen/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("imagen") Imagen imagen,
                                   BindingResult bindingResult) {
        ImagenValidator imagenValidator = new ImagenValidator();
        imagenValidator.validate(imagen, bindingResult);
        if (bindingResult.hasErrors())
            return "imagen/add";
        imagenDao.addImagen(imagen);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editImagen(Model model, @PathVariable int id) {
        model.addAttribute("imagen", imagenDao.getImagen(id));
        return "imagen/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id,
                                      @ModelAttribute("imagen") Imagen imagen,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "imagen/update";
        imagenDao.updateImagen(imagen);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        imagenDao.deleteImagen(id);
        return "redirect:../list";
    }
}
