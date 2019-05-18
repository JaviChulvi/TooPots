package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.*;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.Entrada;
import es.uji.ei1027.toopots.model.Imagen;
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
import java.util.List;

@Controller
@RequestMapping("/actividad")
public class ActividadController {

    private ActividadDao actividadDao;
    private TipoActividadDao tipoActividadDao;
    private ImagenDao imagenDao;
    private DescuentoDao descuentoDao;
    private EntradaDao entradaDao;

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
    @Autowired
    public void setDescuentoDao(DescuentoDao descuentoDao) {
        this.descuentoDao = descuentoDao;
    }
    @Autowired
    public void setEntradaDao(EntradaDao entradaDao) {
        this.entradaDao = entradaDao;
    }

    @RequestMapping("/list")
    public String listActividades(Model model){
        model.addAttribute("actividades", actividadDao.getActividades());
        return "actividad/list";
    }

    @RequestMapping("/crear")
    public String crearActividad(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null || session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            if (session.getAttribute("tipo") == "cliente") {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "actividad/crear");
            return "redirect:../login";
        } else {
            model.addAttribute("actividad", new Actividad());
            // tipoActividadDao.getTiposActividadPermitidosMonitor() proporciona los tipos de actividades en los que el monitor que quiere crear la actividad tiene permitido crear una actividad
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividadPermitidosMonitor( (String) session.getAttribute("dni")));
            return "actividad/crear";
        }
    }

    @RequestMapping(value="/crear", method= RequestMethod.POST)
    public String procesarCrearActividad(@RequestParam("img") MultipartFile imgFile, @RequestParam("menor18") float precioMenores, @RequestParam("entre18-50") float precioAdultos, @RequestParam("mayor50") float precioJubilados, @ModelAttribute("actividad") Actividad actividad,
                                   BindingResult bindingResult, HttpSession session) {
        ActividadValidator actividadValidator = new ActividadValidator();
        actividad.setEstado("abierta");
        actividad.setMonitor((String) session.getAttribute("dni"));
        actividad.setInscritos(0);
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
        int idActividadCreada = actividadDao.getLastId();
        imagen.setIdActividad(idActividadCreada);
        imagenDao.addImagen(imagen);
        Entrada entradaMenor = new Entrada(idActividadCreada, "menor18", precioMenores);
        entradaDao.addEntrada(entradaMenor);
        Entrada entradaAdulto = new Entrada(idActividadCreada, "entre18-50", precioAdultos);
        entradaDao.addEntrada(entradaAdulto);
        Entrada entradaJubilado = new Entrada(idActividadCreada, "mayor50", precioJubilados);
        entradaDao.addEntrada(entradaJubilado);

        return "redirect:../gestion";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
    public String actualizarActividad(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            if (session.getAttribute("tipo") == "cliente") {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "actividad/actualizar/"+id);
            return "redirect:../login";
        } else {
            model.addAttribute("actividad", actividadDao.getActividad(id));
            // tipoActividadDao.getTiposActividadPermitidosMonitor() proporciona los tipos de actividades en los que el monitor que quiere crear la actividad tiene permitido crear una actividad
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividadPermitidosMonitor( (String) session.getAttribute("dni")));
            List<Entrada>  lista = entradaDao.getEntradasActividad(id);
            for (int i=0; i<lista.size(); i++) {
                String tipo = lista.get(i).getTipo();
                System.out.println(tipo + " precio -> " + lista.get(i).getPrecio());
                if (tipo.equals("menor18")) {
                    model.addAttribute("menor18", lista.get(i).getPrecio());
                } else if (tipo.equals("entre18-50")) {
                    model.addAttribute("adulto", lista.get(i).getPrecio());
                } else if (tipo.equals("mayor50")) {
                    model.addAttribute("mayor50", lista.get(i).getPrecio());
                }
            }

            model.addAttribute("actividad", actividadDao.getActividad(id));
            return "actividad/actualizar";
        }
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.POST)
    public String processActualizarActividad(@RequestParam("img") MultipartFile imgFile, @RequestParam("menor18") float precioMenores, @RequestParam("entre18-50") float precioAdultos, @RequestParam("mayor50") float precioJubilados,
                                      @PathVariable int id,
                                      @ModelAttribute("actividad") Actividad actividad,
                                      BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "actividad/actualizar";
        }
        actividad.setMonitor((String) session.getAttribute("dni"));
        actividadDao.updateActividad(actividad);
        if (!imgFile.isEmpty()) {
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
        int idActividad = actividad.getId();

        Entrada entradaAdulto = new Entrada(idActividad, "entre18-50", precioAdultos);
        entradaDao.updateEntrada(entradaAdulto);
        Entrada entradaJubilado = new Entrada(idActividad, "mayor50", precioJubilados);
        entradaDao.updateEntrada(entradaJubilado);
        Entrada entradaMenor = new Entrada(idActividad, "menor18", precioMenores);
        entradaDao.updateEntrada(entradaMenor);
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
        int random = (int) (Math.random() * 99999) + 1;
        Path ruta = Paths.get(carpeta + random + nombreImagen);
        Files.write(ruta, bytes);
        return random + nombreImagen;
    }

    @RequestMapping(value="/cancelar/{id}", method = RequestMethod.GET)
    public String cancelarActividad(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            if (session.getAttribute("tipo") == "cliente") {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "actividad/cancelar/"+id);
            return "redirect:../login";
        } else {
            model.addAttribute("actividad", actividadDao.getActividad(id));
            return "actividad/cancelar";
        }
    }

    @RequestMapping(value="/cancelar/{id}", method = RequestMethod.POST)
    public String processcancelarActividad(  @PathVariable int id ){

        Actividad act = actividadDao.getActividad(id);
        act.setEstado("cancelada");
        actividadDao.updateActividad(act);
        return "redirect:../../monitor/gestionActividades";
    }
    @RequestMapping(value="/ver/{id}", method = RequestMethod.GET)
    public String verActividad(Model model, @PathVariable int id, HttpSession session) {
        Actividad actividad = actividadDao.getActividad(id);
        TipoActividad tipoAct = tipoActividadDao.getTipoActividad(actividad.getIdTipoActividad());
        model.addAttribute("tipoactividad", tipoAct.getNombre() + " " + tipoAct.getNivelActividad());
        model.addAttribute("actividad", actividad);
        model.addAttribute("imagenesPromocionales", imagenDao.getImagenesActividad(id));
        session.setAttribute("actividad", id);
        if(session.getAttribute("listaReserva") != null) {
            model.addAttribute("listaReserva", true);
            session.removeAttribute("listaReserva");
        }
        return "actividad/ver";
    }

    @RequestMapping(value="/aplicarDescuento/{id}", method = RequestMethod.GET)
    public String aplicarOferta(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente" || session.getAttribute("dni")!="admin") {
            if (session.getAttribute("tipo") == "cliente" || session.getAttribute("dni")!="admin") {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "actividad/aplicarDescuento/"+ id);
            return "redirect:../login";
        } else {
            model.addAttribute("actividad", actividadDao.getActividad(id));
            model.addAttribute("descuentos", descuentoDao.getDescuentos());

            return "actividad/aplicarDescuento";
        }
    }

    @RequestMapping(value="/aplicarDescuento/{id}", method = RequestMethod.POST)
    public String procesarAplicarOferta(@RequestParam("descuento") String descuento, Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            return "redirect:../../login";
        } else {
            Actividad actividad = actividadDao.getActividad(id);
            actividad.setDescuentoAplicado(descuento);
            actividadDao.updateActividad(actividad);
            return "redirect:../../monitor/gestionActividades";
        }
    }
}
