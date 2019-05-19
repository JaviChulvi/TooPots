package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.PrefiereDao;
import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.Prefiere;
import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/prefiere")
public class PrefiereController {

    private PrefiereDao prefiereDao;
    private TipoActividadDao tipoActividadDao;

    @Autowired
    public void setPrefiereDao(PrefiereDao prefiereDao) {
        this.prefiereDao = prefiereDao;
    }
    @Autowired
    public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
        this.tipoActividadDao = tipoActividadDao;
    }

    @RequestMapping("/list")
    public String listPreferencias(Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || session.getAttribute("tipo").equals("monitor")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (session.getAttribute("tipo").equals("monitor")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "prefiere/list");
                return "redirect:../login";
            }
        }
        String dni = (String) session.getAttribute("dni");
        model.addAttribute("preferencias", prefiereDao.getPreferenciasCliente(dni));
        HashMap map = getTiposActividades();
        System.out.println(map);
        for (int i =1; i < 13 ; i++)
            System.out.println(map.get(i));
        model.addAttribute("mapa", map);
        return "prefiere/list";
    }

    private HashMap getTiposActividades() {
        List<TipoActividad> lista = tipoActividadDao.getTiposActividad();
        HashMap map = new HashMap<Integer, String>();
        for (int i=0; i<lista.size(); i++) {
            System.out.println(lista.get(i).getId() + " - " + lista.get(i).getNombre());
            map.put(lista.get(i).getId(), lista.get(i).getNombre() + "      Nivel: " +lista.get(i).getNivelActividad());
        }
        return map;
    }

    @RequestMapping("/add")
    public String addPreferencia(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || session.getAttribute("tipo").equals("monitor")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (session.getAttribute("tipo").equals("monitor")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "prefiere/add");
                return "redirect:../login";
            }
        }
        model.addAttribute("tiposActividad", tipoActividadDao.getTiposActividad());
        return "prefiere/add";

    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String preocessAddPreferencia(@RequestParam("idTipoActividad") int idTipoActividad, HttpSession session) {
        Prefiere prefiere = new Prefiere((String) session.getAttribute("dni"), idTipoActividad);
        prefiereDao.addPrefiere(prefiere);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{idTipoActividad}")
    public String processDelete(@PathVariable Integer idTipoActividad, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || session.getAttribute("tipo").equals("monitor")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (session.getAttribute("tipo").equals("monitor")) {
                    return "redirect:../../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "prefiere/delete/"+idTipoActividad);
                return "redirect:../../login";
            }
        }
        prefiereDao.deletePrefiere(idTipoActividad, (String) session.getAttribute("dni"));
        return "redirect:../../prefiere/list";

    }
}
