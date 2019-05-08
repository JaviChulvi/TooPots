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

import javax.servlet.http.HttpSession;

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
                                   BindingResult bindingResult, Model model, HttpSession session) {
        ClienteValidator clienteValidator = new ClienteValidator();
        clienteValidator.validate(cliente, bindingResult);
        if (bindingResult.hasErrors()) {
            return "cliente/registro";
        }
        cliente.cifrarContraseña();
        clienteDao.addCliente(cliente);
        session.setAttribute("tipo", "cliente");
        session.setAttribute("dni", cliente.getDni());
        return "redirect:../actividades";
    }

    @RequestMapping(value="/verperfil/{id}", method = RequestMethod.GET)
    public String verPerfilMonitor(Model model, @PathVariable String id) {
        model.addAttribute("cliente", clienteDao.getCliente(id));
        return "cliente/verperfil";
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
        System.out.println(cliente.toString());
        cliente.cifrarContraseña();
        clienteDao.updateClient(cliente);
        return "redirect:../../ajustes";
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.GET)
    public String eliminarCliente(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || !session.getAttribute("dni").equals(id) || !session.getAttribute("tipo").equals("cliente")) {
            return "redirect:../../login";
        } else {
            model.addAttribute("dni", id);
            return "cliente/eliminar";
        }
    }

    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.POST)
    public String procesarEliminarCliente(@PathVariable String id, HttpSession session) {
        clienteDao.deleteCliente(id);
        session.removeAttribute("dni");
        session.removeAttribute("tipo");
        return "redirect:../../registro";
    }

}
