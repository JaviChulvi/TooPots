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
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @RequestMapping("/list")
    public String listCliente(Model model){
        model.addAttribute("clientes", clienteDao.getClientes());
        return "cliente/list";
    }

    @RequestMapping("/registro")
    public String addCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/registro";
    }

    @RequestMapping(value="/registro", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("cliente") Cliente cliente,
                                   BindingResult bindingResult, Model model) {
        ClienteValidator clienteValidator = new ClienteValidator();
        clienteValidator.validate(cliente, bindingResult);
        if (bindingResult.hasErrors()) {
            return "cliente/registro";
        }
        clienteDao.addCliente(cliente);
        return "redirect:list";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
    public String editCliente(Model model, @PathVariable String id) {
        model.addAttribute("cliente", clienteDao.getCliente(id));
        return "cliente/actualizar";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable String id,
                                      @ModelAttribute("cliente") Cliente cliente,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "cliente/actualizar";
        clienteDao.updateClient(cliente);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable String id) {
        clienteDao.deleteCliente(id);
        return "redirect:../list";
    }

}
