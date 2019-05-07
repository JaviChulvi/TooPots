package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.OfertaDao;
import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Actividad;
import es.uji.ei1027.toopots.model.CalculadoraPrecios;
import es.uji.ei1027.toopots.model.Oferta;
import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private ReservaDao reservaDao;
    private ActividadDao actividadDao;
    private OfertaDao ofertaDao;


    @Autowired
    public void setOfertaDao(OfertaDao ofertaDao) {
        this.ofertaDao = ofertaDao;
    }

    @Autowired
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    @Autowired
    public void setActividadDao(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }

    @RequestMapping("/list")
    public String listReserva(Model model){
        model.addAttribute("reservas", reservaDao.getReservas());
        return "reserva/list";
    }

    @RequestMapping("/add/{idActividad}")
    public String addReserva(Model model, @PathVariable int idActividad, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null) {
            return "redirect:../../login";
        } else {
            Reserva reserva = new Reserva();
            Actividad actividad = actividadDao.getActividad(idActividad);

            reserva.setIdActividad(idActividad);
            reserva.setFecha(actividad.getFecha());
            reserva.setNumJubilados(0);
            reserva.setNumAdultos(1);
            reserva.setNumMenores(0);
            reserva.setDniCliente((String) session.getAttribute("dni"));

            model.addAttribute("reserva", reserva);
            return "reserva/add";
        }
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult) {
        reserva.setEstadoPago("pendiente");
        ReservaValidator reservaValidator = new ReservaValidator();
        reservaValidator.validate(reserva, bindingResult);
        Actividad actividad = actividadDao.getActividad(reserva.getIdActividad());
        float ofertaAplicada = 1.0f;
        String ofertaNombre = "";
        if (!(actividad.getOfertaAplicada()=="") && !(actividad.getOfertaAplicada()==null)) {
            Oferta oferta = ofertaDao.getOferta(actividad.getOfertaAplicada());
            ofertaAplicada = oferta.getDescuento();
            ofertaNombre = oferta.getNombre();
        }


        System.out.println(actividad.getPrecioBruto() + " " + reserva.getNumAdultos() + " " + reserva.getNumJubilados() + " " + reserva.getNumMenores() + " " + ofertaNombre + " " + ofertaAplicada);
        CalculadoraPrecios calculadora = new CalculadoraPrecios(actividad.getPrecioBruto(), reserva.getNumAdultos(), reserva.getNumJubilados(), reserva.getNumMenores(), ofertaNombre, ofertaAplicada);
        reserva.setPrecioTotal(calculadora.calcularPrecio());

        if (bindingResult.hasErrors())
            return "reserva/add";
        reservaDao.addReserva(reserva);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idActividad}/{dniCliente}", method = RequestMethod.GET)
    public String editReserva(Model model, @PathVariable Integer idActividad, @PathVariable Integer dniCliente) {
        model.addAttribute("reserva", reservaDao.getReserva(idActividad, dniCliente));
        return "reserva/update";
    }

    @RequestMapping(value="/update/{idActividad}/{dniCliente}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable Integer idActividad, @PathVariable Integer dniCliente,
                                      @ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/update";
        reservaDao.updateReserva(reserva);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{idActividad}/{dniCliente}")
    public String processDelete(@PathVariable Integer idActividad, @PathVariable Integer dniCliente) {
        reservaDao.deleteReserva(idActividad, dniCliente);
        return "redirect:../list";
    }
}
