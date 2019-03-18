package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteDao clienteDao;

    @Autowired
    public void setReservaDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @RequestMapping("/list")
    public String listReserva(Model model){
        model.addAttribute("cliente", clienteDao.getClientes());
        return "cliente/list";
    }

    @RequestMapping("/add")
    public String addReserva(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("cliente") Cliente cliente,
                                   BindingResult bindingResult) {
        ReservaValidator nadadorValidator = new ReservaValidator();
        nadadorValidator.validate(cliente, bindingResult);
        if (bindingResult.hasErrors())
            return "cliente/add";
        clienteDao.addCliente(cliente);
        return "redirect:../list";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editNadador(Model model, @PathVariable String id) {
        model.addAttribute("cliente", clienteDao.getCliente(id));
        return "cliente/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("cliente") Cliente cliente,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "nadador/update";
        clienteDao.updateClient(cliente);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable String id) {
        clienteDao.deleteCliente(id);
        return "redirect:../list";
    }
    
}
