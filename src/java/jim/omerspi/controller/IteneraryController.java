/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Itenerary;
import jim.omerspi.validator.IteneraryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping("/itenerary")
public class IteneraryController {

    @Autowired
    private ServiceContext context;

    @Autowired
    private IteneraryValidator iteneraryValidator;
    
    @RequestMapping(value="/form",method=RequestMethod.GET)
    public String getIteneraryForm(Map<String, Object> model,@ModelAttribute("itenerary")Itenerary itenerary) {
        model.put("itenerary", itenerary);
        return "itenerary/iteneraryForm";

    }

    @RequestMapping(value="/form",method=RequestMethod.POST)
    public String saveOrUpdateItenerary(HttpSession session,Map<String,Object>model, @ModelAttribute("itenerary") Itenerary itenerary,BindingResult result) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_ITENERARY)) {
            try {
                iteneraryValidator.validate(itenerary, result);
                if(result.hasErrors()){
                return getIteneraryForm(model,itenerary);
                }
                context.getIteneraryService().saveOrUpdateItenerary(itenerary);
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Error occurred, Try again.");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/edit")
    public String editItenerary(Map<String, Object> model, @RequestParam("iteneraryId") Itenerary itenerary) {
        System.out.println("Cost is : " + itenerary.getCost());
        model.put("itenerary", itenerary);
        return "itenerary/iteneraryForm";

    }

    @RequestMapping("/delete")
    public String deleteItenerary(HttpSession session, @RequestParam("iteneraryId") Itenerary itenerary) {
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_ITENERARY)) {
            try {
                context.getIteneraryService().deleteItenerary(itenerary);
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Can't be deleted, is used by other object(s)");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/list")
    public String iteneraryList(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.VIEW_ITENERARY)) {
            try {
                model.put("iteneraryList", context.getIteneraryService().getAllItenerary());
                return "itenerary/iteneraryList";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Error occurred, Try Again.");
                return "redirect:/Menu";
            }
        } else {
            return "redirect:/login.htm";
        }
    }
}
