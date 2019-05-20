package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.DescuentoDao;
import es.uji.ei1027.toopots.dao.EntradaDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private ReservaDao reservaDao;
    private ActividadDao actividadDao;
    private DescuentoDao descuentoDao;
    private EntradaDao entradaDao;


    @Autowired
    public void setEntradaDao(EntradaDao entradaDao) {
        this.entradaDao = entradaDao;
    }

    @Autowired
    public void setDescuentoDao(DescuentoDao descuentoDao) {
        this.descuentoDao = descuentoDao;
    }

    @Autowired
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    @Autowired
    public void setActividadDao(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }

    @RequestMapping("/listaCliente")
    public String listReservaCliente(Model model, HttpSession session){
        if ((session.getAttribute("tipo") == null && session.getAttribute("dni")==null) || session.getAttribute("tipo")=="monitor") {
            try {
                // session.getAttribute("dni") puede ser null
                if (session.getAttribute("tipo").equals("monitor")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "reserva/listaCliente");
                return "redirect:../login";
            }
        }
        String dni = (String) session.getAttribute("dni");
        model.addAttribute("reservas", reservaDao.getReservasDni(dni));
        model.addAttribute("map", getMapNombresActividades());
        session.setAttribute("listaReserva", true);
        return "reserva/list";

    }

    private HashMap getMapNombresActividades() {
        /*

            Creo un map con los ids de las actividades y nombres de las actividades para desde la vista poder
            tener acceso a los nombres facilmente sin tener que pasar una lista con objetos de todas las actividades del sistema.

        */
        List<Actividad> lista =  actividadDao.getActividades();
        HashMap map = new HashMap<Integer, String>();
        for (int i=0; i<lista.size(); i++) {
            map.put(lista.get(i).getId(), lista.get(i).getNombre());
        }
        return map;
    }

    @RequestMapping("/listaActividad/{idActividad}")
    public String listReservaActividad(@PathVariable int idActividad, Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || session.getAttribute("tipo").equals("cliente")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (session.getAttribute("tipo").equals("cliente")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "reserva/listaActividad/"+idActividad);
                return "redirect:../../login";
            }
        }

        model.addAttribute("map", getMapNombresActividades());
        model.addAttribute("reservas", reservaDao.getReservasActividad(idActividad));
        return "reserva/list";

    }

    @RequestMapping("/add/{idActividad}")
    public String addReserva(Model model, @PathVariable int idActividad, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || session.getAttribute("tipo").equals("monitor")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (session.getAttribute("tipo").equals("monitor")) {
                    return "redirect:../../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "reserva/add/"+idActividad);
                return "redirect:../../login";
            }
        }
        Reserva reserva = new Reserva();
        Actividad actividad = actividadDao.getActividad(idActividad);

        reserva.setIdActividad(idActividad);
        reserva.setFecha(LocalDate.now());
        reserva.setNumJubilados(0);
        reserva.setNumAdultos(1);
        reserva.setNumMenores(0);
        reserva.setDniCliente((String) session.getAttribute("dni"));
        model.addAttribute("nombreActividad", actividad.getNombre());
        model.addAttribute("reserva", reserva);
        return "reserva/add";

    }

    @RequestMapping(value="/add/{idActividad}", method= RequestMethod.POST)
    public String processAddSubmit(@PathVariable int idActividad, @ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult, HttpSession session) {
        reserva.setEstadoPago("pendiente");
        ReservaValidator reservaValidator = new ReservaValidator();
        reservaValidator.validate(reserva, bindingResult);
        reserva.setIdActividad(idActividad);
        reserva.setDniCliente((String) session.getAttribute("dni"));
        Actividad actividad = actividadDao.getActividad(reserva.getIdActividad());
        System.out.println(actividad.toString());
        System.out.println(reserva.toString());
        double precio = calcularPrecio(reserva, actividad);

        reserva.setPrecioTotal(precio);

        if (bindingResult.hasErrors())
            return "reserva/add";
        try {
            reservaDao.addReserva(reserva);
        } catch (Exception e) {
            return "redirect:../listaCliente";
        }
        return "redirect:../pasarelaPago/"+actividad.getId();
    }

    @RequestMapping(value="/update/{idActividad}/{dniCliente}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable Integer idActividad, @PathVariable String dniCliente, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals(dniCliente)) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals(dniCliente)) {
                    return "redirect:../../../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "reserva/update/"+idActividad+"/"+dniCliente);
                return "redirect:../../../login";
            }
        }
        Actividad actividad = actividadDao.getActividad(idActividad);
        model.addAttribute("nombreActividad", actividad.getNombre());
        model.addAttribute("reserva", reservaDao.getReserva(idActividad, dniCliente));
        return "reserva/update";
    }

    @RequestMapping(value="/update/{idActividad}/{dniCliente}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable Integer idActividad, @PathVariable String dniCliente,
                                      @ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "reserva/update";
        reserva.setIdActividad(idActividad);
        reserva.setDniCliente((String) session.getAttribute("dni"));
        reserva.setEstadoPago("pendiente");
        Actividad act = actividadDao.getActividad(idActividad);
        double precio = calcularPrecio(reserva, act);
        reserva.setPrecioTotal(precio);

        reservaDao.updateReserva(reserva);
        return "redirect:../../../reserva/listaCliente";
    }

    public double calcularPrecio(Reserva reserva, Actividad actividad) {
        float descuentoAplicado = 1.0f;
        String nombreDescuento = "";
        if (!(actividad.getDescuentoAplicado()=="") && !(actividad.getDescuentoAplicado()==null)) {
            Descuento descuento = descuentoDao.getDescuento(actividad.getDescuentoAplicado());
            descuentoAplicado = descuento.getDescuento();
            nombreDescuento = descuento.getTipo();
        }
        List<Entrada> lista = entradaDao.getEntradasActividad(reserva.getIdActividad());
        float precioMenor = 0f, precioAdulto = 0f, precioJubilado = 0f;
        for (int i=0; i<lista.size(); i++) {
            String tipo = lista.get(i).getTipo();
            System.out.println(tipo + " precio -> " + lista.get(i).getPrecio());
            if (tipo.equals("menor18")) {
                precioMenor = lista.get(i).getPrecio();
            } else if (tipo.equals("entre18-50")) {
                precioAdulto = lista.get(i).getPrecio();
            } else if (tipo.equals("mayor50")) {
                precioJubilado = lista.get(i).getPrecio();            }
        }
                                            //int numAdultos, int numJubilados, int numMenores, String tipoOferta, float descuentoOferta, float precioMenor, float precioAdulto, float precioJubilado
        //System.out.println(actividad.getPrecioBruto() + " " + reserva.getNumAdultos() + " " + reserva.getNumJubilados() + " " + reserva.getNumMenores() + " " + ofertaNombre + " " + ofertaAplicada);
        CalculadoraPrecios calculadora = new CalculadoraPrecios( reserva.getNumAdultos(), reserva.getNumJubilados(), reserva.getNumMenores(), nombreDescuento, descuentoAplicado ,  precioMenor,  precioAdulto,  precioJubilado);
        return calculadora.calcularPrecio();
    }

    @RequestMapping(value="/delete/{idActividad}/{dniCliente}")
    public String processDelete(@PathVariable Integer idActividad, @PathVariable String dniCliente) {
        reservaDao.deleteReserva(idActividad, dniCliente);
        return "redirect:../../../reserva/listaCliente";
    }


    @RequestMapping(value="/pasarelaPago/{idActividad}", method = RequestMethod.GET)
    public String pasarelaPago(Model model, @PathVariable Integer idActividad, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null && session.getAttribute("tipo")=="cliente") {
            return "redirect:../../login";
        } else {
            String dniCliente =(String) session.getAttribute("dni");
            PasarelaPago infopago = new PasarelaPago();
            model.addAttribute("infopago", infopago);
            model.addAttribute("reserva", reservaDao.getReserva(idActividad, dniCliente));
            return "reserva/pasarelaPago";
        }

    }

    @RequestMapping(value="/pasarelaPago/{idActividad}", method = RequestMethod.POST)
    public String procesarPasarelaPago(Model model, @PathVariable Integer idActividad, @ModelAttribute("infopago") PasarelaPago infopago, BindingResult bindingResult, HttpSession session) {
        String dniCliente =(String) session.getAttribute("dni");
        PasarelaPagoValidator ppValidator = new PasarelaPagoValidator();
        ppValidator.validate(infopago, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("reserva", reservaDao.getReserva(idActividad, dniCliente));
            return "reserva/pasarelaPago";
        }
        Actividad actividad = actividadDao.getActividad(idActividad);
        Reserva reserva = reservaDao.getReserva(idActividad, (String) session.getAttribute("dni"));
        reserva.setEstadoPago("pagado");
        int participantes = reserva.getNumAdultos() + reserva.getNumJubilados() + reserva.getNumMenores();
        int resultadoReserva = actividad.getInscritos() + participantes;
        if (actividad.getMaxAsistentes() >= resultadoReserva) {
            actividad.setInscritos(resultadoReserva);
        } else {
            model.addAttribute("error", "No se ha podido realizar el pago porque se han acabado las plazas para esta actividad o el número de plazas que intentas reservar sobrepasa el límite especificado por el monitor.");
            reservaDao.deleteReserva(idActividad, dniCliente);
            return "reserva/pasarelaPago";
        }
        reservaDao.updateReserva(reserva);
        actividadDao.updateActividad(actividad);
        return "redirect:../listaCliente";
    }
}
