package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.InstructorDao;
import es.uji.ei1027.toopots.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private InstructorDao instructorDao;

    @Autowired
    public void setInstructorDao(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    @RequestMapping("/list")
    public String listInstructor(Model model){
        model.addAttribute("instructores", instructorDao.getInstructores());
        return "instructor/list";
    }

    @RequestMapping("/add")
    public String addInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("instructor") Instructor instructor,
                                   BindingResult bindingResult) {
        InstructorValidator instructorValidator = new InstructorValidator();
        instructorValidator.validate(instructor, bindingResult);
        if (bindingResult.hasErrors())
            return "instructor/add";
        instructorDao.addInstructor(instructor);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editInstructor(Model model, @PathVariable int id) {
        model.addAttribute("instructor", instructorDao.getInstructor(id));
        return "instructor/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id,
                                      @ModelAttribute("instructor") Instructor instructor,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "instructor/update";
        instructorDao.updateInstructor(instructor);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        instructorDao.deleteInstructor(id);
        return "redirect:../list";
    }
}
