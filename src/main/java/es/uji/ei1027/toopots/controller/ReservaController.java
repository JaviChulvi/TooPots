package es.uji.ei1027.toopots.controller;


import es.uji.ei1027.toopots.dao.ReservaDao;
import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private ReservaDao reservaDao;

    @Autowired
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    @RequestMapping("/list")

    public String listReserva(Model model){
        model.addAttribute("reservas", reservaDao.getReservas());
        return "reserva/list";
    }

    @RequestMapping("/add")
    public String addReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("reserva") Reserva reserva,
                                   BindingResult bindingResult) {
        ReservaValidator nadadorValidator = new ReservaValidator();
        nadadorValidator.validate(reserva, bindingResult);
        if (bindingResult.hasErrors())
            return "reserva/add";
        reservaDao.addReserva(reserva);
        return "redirect:list.html";
    }

    @RequestMapping(value="/update/{numReserva}", method = RequestMethod.GET)
    public String editNadador(Model model, @PathVariable String numReserva) {
        model.addAttribute("reserva", reservaDao.getReserva(numReserva));
        return "reserva/update";
    }

    @RequestMapping(value="/update/{numReserva}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String nom,
                                      @ModelAttribute("reserva") Reserva reserva,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "nadador/update";
        reservaDao.updateReserva(reserva);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{numReserva}")
    public String processDelete(@PathVariable String numReserva) {
        reservaDao.deleteReserva(numReserva);
        return "redirect:../list";
    }
}
