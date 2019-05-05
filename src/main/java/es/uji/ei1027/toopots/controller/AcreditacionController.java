package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Acredita;
import es.uji.ei1027.toopots.model.Acreditacion;
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
@RequestMapping("/acreditacion")
public class AcreditacionController {

    private AcreditacionDao acreditacionDao;
    private TipoActividadDao tipoActividadDao;
    private AcreditaDao acreditaDao;

    @Autowired
    public void setAcreditaDao(AcreditaDao acreditaDao) {
        this.acreditaDao = acreditaDao;
    }
    @Autowired
    public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
        this.tipoActividadDao = tipoActividadDao;
    }
    @Autowired
    public void setAcreditacionDao(AcreditacionDao acreditacionDao) {
        this.acreditacionDao = acreditacionDao;
    }

    @RequestMapping("/list")
    public String listAcreditacion(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            return "redirect:../login";
        } else {
            model.addAttribute("acreditados", acreditaDao.getAcreditasMonitor((String) session.getAttribute("dni")));
            model.addAttribute("map", getMapTiposActividad());
            return "acreditacion/list";
        }
    }

    @RequestMapping("/add")
    public String addAcreditacion(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            return "redirect:../login";
        } else {
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividad());

            return "acreditacion/add";
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@RequestParam("pdf") MultipartFile pdf, @RequestParam("idTipoActividad") int idTipoActividad, HttpSession session) {

        Acreditacion acreditacion = new Acreditacion();

        acreditacion.setEstado("pendiente");
        acreditacion.setIdInstructor((String) session.getAttribute("dni"));


        String nombreCertificado;
        try {
            nombreCertificado = guardarCertificado(pdf);
            acreditacion.setCertificado(nombreCertificado);
            Acredita acredita = new Acredita();
            acredita.setCertificado(nombreCertificado);
            acredita.setTipoActividad(idTipoActividad);
            /*AcreditacionValidator acreditacionValidator = new AcreditacionValidator();
            acreditacionValidator.validate(acreditacion, bindingResult);
            if (bindingResult.hasErrors())
                return "acreditacion/add";*/
            acreditacionDao.addAcreditacion(acreditacion);
            acreditaDao.addAcredita(acredita);

            return "redirect:list";
        } catch (Exception e) {
            //bindingResult.rejectValue("pdf", "Error al guardar pdf", "Error al guardar pdf");
            return "acreditacion/add";
        }



    }

    @RequestMapping(value = "/update/{id}/{certificado}", method = RequestMethod.GET)
    public String editAcreditacion(Model model, @PathVariable String dni, @PathVariable String certificado) {
        model.addAttribute("acreditacion", acreditacionDao.getAcreditacion(dni, certificado));
        return "acreditacion/update";
    }

    @RequestMapping(value = "/update/{id}/{certificado}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id, @PathVariable String certificado,
                                      @ModelAttribute("acreditacion") Acreditacion acreditacion,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "acreditacion/update";
        acreditacionDao.updateAcreditacion(acreditacion);
        return "redirect:../list";
    }

    @RequestMapping(value = "/delete/{id}/{certificado}")
    public String processDelete(@PathVariable int id, @PathVariable String certificado) {
        acreditacionDao.deleteAcreditacion(certificado);
        return "redirect:../list";
    }

    public String guardarCertificado(MultipartFile pdf) throws Exception {
        String carpeta = System.getProperty("user.dir") + "/certificados/";
        String nombreCertificado = pdf.getOriginalFilename();
        byte[] bytes = pdf.getBytes();
        Path ruta = Paths.get(carpeta + nombreCertificado);
        Files.write(ruta, bytes);
        return nombreCertificado;
    }

    private HashMap getMapTiposActividad() {
        /*
            Creo un map con los ids de los tipos de actividad para poder mostrar en la vista los nombres de los tipos de actividad y no los id

        */
        List<TipoActividad> lista =  tipoActividadDao.getTiposActividad();
        HashMap map = new HashMap<Integer, String>();
        for (int i=0; i<lista.size(); i++) {
            map.put(lista.get(i).getId(), lista.get(i).getNombre() + " " + lista.get(i).getNivelActividad());
        }
        return map;
    }
}
