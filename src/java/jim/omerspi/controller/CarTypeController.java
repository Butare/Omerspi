/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Cartype;
import jim.omerspi.validator.CarTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Jimmy
 */
@Controller
@RequestMapping("/carType")
public class CarTypeController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private CarTypeValidator carTypeValidator;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getCarTypeForm(Map<String, Object> model, @ModelAttribute("carType") Cartype carType) {
        model.put("carType", carType);
        return "car/carTypeForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdateCarType(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("carType") Cartype carType, BindingResult result) {

        HttpSession session = request.getSession(true);

        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_RECORDS)) {

            try {
                carTypeValidator.validate(carType, result);
                if (result.hasErrors()) {
                    return getCarTypeForm(model, carType);
                }
                if (carType.getDailyCost() <= carType.getHourlyCost()) {
                    OmerspiUtils.setErrorMessage(session, "Daily cost must be greater than hourly cost.");
                    return getCarTypeForm(model, carType);
                }
                context.getCartypeService().saveOrUpdateCartype(carType);
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
    public String editCarType(HttpServletRequest request, Map<String, Object> model, @RequestParam("carTypeId") Cartype carType) {

        HttpSession session = request.getSession(true);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_RECORDS)) {
            model.put("carType", carType);
            return "car/carTypeForm";
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/delete")
    public String deleteCarType(HttpServletRequest request, @RequestParam("carTypeId") Cartype carType) {

        HttpSession session = request.getSession(true);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_RECORDS)) {
            context.getCartypeService().deleteCartype(carType);
            return "redirect:list";
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/list")
    public String carTypeList(HttpServletRequest request, Map<String, Object> model) {

        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_RECORDS)) {
            model.put("carTypeList", context.getCartypeService().getAllCartype());
            return "car/carTypeList";
        } else {
            return "redirect:/login.htm";
        }

    }
}
