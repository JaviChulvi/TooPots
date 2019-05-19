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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @RequestMapping("/list/{id}")
    public String listAcreditacion(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "acreditacion/list/"+id);
            return "redirect:../../login";
        } else {
            model.addAttribute("acreditados", acreditaDao.getAcreditasMonitor(id));
            model.addAttribute("map", getMapTiposActividad());
            return "acreditacion/list";
        }
    }

    @RequestMapping("/gestionarCertificadosMonitor/{id}")
    public String asignarTiposActividad(@PathVariable String id, Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "acreditacion/gestionarCertificadosMonitor/"+id);
            return "redirect:../../login";
        } else {
            model.addAttribute("certificados", acreditacionDao.getAcreditacionesPendientesMonitor(id));
            model.addAttribute("map", getMapTiposActividad());
            model.addAttribute("dniMonitor", id);
            return "acreditacion/gestionarCertificadosMonitor";
        }
    }


    @RequestMapping("/asignarTipoActividadCertificado/{idMonitor}/{certificado}")
    public String asignarTiposActividad(@PathVariable String idMonitor, @PathVariable String certificado, Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            try {
                if (session.getAttribute("tipo").equals("cliente")) {
                    return "redirect:../../../actividades";
                }
            } catch (Exception e) {

            }
            session.setAttribute("urlAnterior", "acreditacion/gestionarCertificadosMonitor/"+idMonitor+"/"+certificado);
            return "redirect:../../../login";
        } else {
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividad());
            model.addAttribute("map", getMapTiposActividad());
            model.addAttribute("dniMonitor", idMonitor);
            model.addAttribute("certificado", certificado);
            return "acreditacion/asignarTipoActividadCertificado";
        }
    }

    @RequestMapping(value = "/asignarTipoActividadCertificado/{idMonitor}/{certificado}", method = RequestMethod.POST)
    public String procesarAsignarTiposActividad(@RequestParam("idTipoActividad") int idTipoActividad, @PathVariable String idMonitor, @PathVariable String certificado, Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            return "redirect:../../login";
        } else {
            Acreditacion acreditacion = new Acreditacion();
            acreditacion = acreditacionDao.getAcreditacion(idMonitor, certificado);
            acreditacion.setEstado("aceptada");
            acreditacionDao.updateAcreditacion(acreditacion);
            Acredita acredita = new Acredita();
            acredita.setCertificado(certificado);
            acredita.setTipoActividad(idTipoActividad);
            acreditaDao.addAcredita(acredita);
            return "redirect:../../../acreditacion/gestionarCertificadosMonitor/"+idMonitor;
        }
    }

    @RequestMapping("/add")
    public String addAcreditacion(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            try {
                if (session.getAttribute("tipo").equals("cliente")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {

            }
            session.setAttribute("urlAnterior", "acreditacion/add");
            return "redirect:../login";
        } else {
            model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividad());

            return "acreditacion/add";
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@RequestParam("pdf") MultipartFile pdf, HttpSession session, Model model) {

        Acreditacion acreditacion = new Acreditacion();
        String dni = (String) session.getAttribute("dni");
        acreditacion.setEstado("pendiente");
        acreditacion.setIdInstructor(dni);


        String nombreCertificado;
        try {
            nombreCertificado = guardarCertificado(pdf);
            acreditacion.setCertificado(nombreCertificado);

            acreditacionDao.addAcreditacion(acreditacion);
            return "redirect:../acreditacion/list/"+dni;
        } catch (Exception e) {
            model.addAttribute("error", "");
            return "acreditacion/add";
        }
    }

    @RequestMapping(value = "/update/{id}/{certificado}", method = RequestMethod.GET)
    public String editAcreditacion(Model model, @PathVariable String dni, @PathVariable String certificado, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../../actividades";
            }
            session.setAttribute("urlAnterior", "acreditacion/update/"+dni+"/"+certificado);
            return "redirect:../../../login";
        } else {
            model.addAttribute("acreditacion", acreditacionDao.getAcreditacion(dni, certificado));
            return "acreditacion/update";
        }

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
    public String processDelete(@PathVariable int id, @PathVariable String certificado, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("tipo").equals("monitor")) {
            if (session.getAttribute("tipo").equals("cliente")) {
                return "redirect:../../actividades";
            }
            session.setAttribute("urlAnterior", "acreditacion/delete/"+id+"/"+certificado);
            return "redirect:../../../login";
        } else {
            acreditacionDao.deleteAcreditacion(certificado);
            return "redirect:../list";
        }

    }

    public String guardarCertificado(MultipartFile pdf) throws Exception {
        String carpeta = System.getProperty("user.dir") + "/certificados/";

        String nombreCertificado = pdf.getOriginalFilename();
        byte[] bytes = pdf.getBytes();

        int random = (int) (Math.random() * 99999) + 1;
        Path ruta = Paths.get(carpeta + random + nombreCertificado);
        Files.write(ruta, bytes);
        System.out.println(ruta);
        return random + nombreCertificado;
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
