package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import umk.zychu.inzynierka.controller.DTObeans.OrlikForm;
import umk.zychu.inzynierka.controller.validator.OrlikFormValidator;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.service.OrlikService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by emagdnim on 2015-10-06.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    OrlikService orlikService;

    @Autowired
    OrlikFormValidator orlikFormValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(orlikFormValidator);
    }

    @RequestMapping(value = "/orliks", method = RequestMethod.GET)
    public String showOrliks(ModelMap model){
        List<Orlik> orliks = orlikService.findAll();
        model.addAttribute("orliks", orliks);
        return "orliks";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String displayForm(@RequestParam(value = "orlikId", required = false) Integer orlikId,
                              @RequestParam(value = "confirm", required=false) Boolean saved,
                              ModelMap model ){
        OrlikForm form;
        if(orlikId == null){
            form = new OrlikForm();
            model.addAttribute("creation", "true");
        }
        else {
            Orlik orlik = orlikService.getOrlikById(orlikId);
            form = new OrlikForm(orlik);
        }
        model.addAttribute("orlikForm", form);
        if(saved != null && saved){
            model.addAttribute("saved", saved);
        }
        return "orlikEdit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String consumeForm(@ModelAttribute(value = "orlikForm") @Valid OrlikForm form, BindingResult result, RedirectAttributes redirectAtt){
        if(result.hasErrors()) {
            Integer id = form.getId();
            if(id != null)
                redirectAtt.addAttribute("orlikId", form.getId());
            return "orlikEdit";
        }
        form = orlikService.saveOrUpdateOrlik(form);
        redirectAtt.addAttribute("orlikId", form.getId());
        redirectAtt.addAttribute("confirm", true);
        return "redirect:/admin/edit";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeOrlik(@RequestParam(value = "orlikRemoveId") Integer id, RedirectAttributes redirectAtt){
        Orlik orlik = orlikService.getOrlikById(id);
        if(orlik != null){
            orlikService.delete(orlik);
        }
        return "redirect:/admin/orliks";
    }
}
