/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carregistration;
import jim.omerspi.validator.CarRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Jimmy
 */
@Controller
@RequestMapping("/car/carRegistration")
public class CarRegistrationController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private CarRegistrationValidator carRegistrationValidator;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getCarRegistrationForm(Map<String, Object> model, @ModelAttribute("carRegistration") Carregistration carRegistration) {
        Map<String, String> service = new LinkedHashMap<String, String>();
        service.put("In Service", "In Service");
        service.put("Not In Service", "Not In Service");
        model.put("serviceList", service);

        // model.put("carRegistration", new Carregistration());
        return "car/carRegistrationForm";

    }

    @RequestMapping(value = "form", method = RequestMethod.POST)
    public String saveOrUpdateCarRegistration(HttpSession session, Map<String, Object> model, @ModelAttribute("carRegistration") Carregistration carRegistration, BindingResult result) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_CAR)) {
            try {
                carRegistrationValidator.validate(carRegistration, result);
                if (result.hasErrors()) {
                    return getCarRegistrationForm(model, carRegistration);
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                carRegistration.setStartDate(date);
                carRegistration.setAcquiredDate(date);
                context.getCarRegistrationService().saveOrUpdateCarRegistration(carRegistration);
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
    public String editCarRegistrations(Map<String, Object> model, @RequestParam("carRegistrationId") Carregistration car) {

        model.put("carRegistration", car);
        Map<String, String> service = new LinkedHashMap<String, String>();
        service.put("In Service", "In Service");
        service.put("Not in Service", "Not in Service");
        model.put("serviceList", service);

        return "car/carRegistrationForm";

    }

    @RequestMapping("list")
    public String carRegistrationList(Map<String, Object> model) {

        model.put("carRegistrationList", context.getCarRegistrationService().getAllCarRegistration());
        return "car/carRegistrationList";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteCarRegistration(HttpSession session, @RequestParam("carRegistrationId") Carregistration car) {
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_CAR)) {
            try {
                context.getCarRegistrationService().deleteCarRegistration(car);
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Can't be deleted, is used by othe object(s)");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }
}
