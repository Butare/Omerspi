/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Vendor;
import jim.omerspi.validator.VendorValidator;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private VendorValidator vendorValidator;

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getVendorForm(Map<String, Object> model, @ModelAttribute("vendor") Vendor vend) {
        Map<String, Object> p = new LinkedHashMap<String, Object>();
        p.put("Transport", "Transport");
        p.put("Equipment", "Equipment");
        model.put("purposeList", p);
        model.put("vendor", vend);
        return "vendor/vendorForm";

    }

    @RequestMapping(value = "form", method = RequestMethod.POST)
    public String saveOrUpdateVendor(HttpSession session, Map<String, Object> model, @ModelAttribute(value = "vendor") Vendor vendor, BindingResult result) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_VENDOR)) {
            try {
                System.out.println("Starting date : " + vendor.getStartingPeriod());
                vendorValidator.validate(vendor, result);
                if (result.hasErrors()) {
                    return getVendorForm(model, vendor);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date start = null;
                Date end = null;
                try {
                    start = sdf.parse(vendor.getStartingPeriod().toString());
                    end = sdf.parse(vendor.getEndingPeriod().toString());
                    long diff = end.getTime() - start.getTime();

                    if (diff > 0) {
                        long diffDays = diff / (1000 * 60 * 60 * 24);
                        if (diffDays < 360) {
                            OmerspiUtils.setErrorMessage(session, "Contract period is short. Try again.");
                            return getVendorForm(model, vendor);
                        }
                        context.getVendorService().saveOrUpdateVendor(vendor);
                        return "redirect:list";
                    } else {
                        OmerspiUtils.setErrorMessage(session, "Ending period can't be less or equal Starting period");
                        return getVendorForm(model, vendor);
                    }

                } catch (ParseException ex) {
                    ex.printStackTrace();
                    OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
                    return "redirect:list";
                }

            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Error occurreds, Try again.");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/edit")
    public String editVendor(Map<String, Object> model, @RequestParam("vendorId") Vendor vendor) {
        model.put("vendor", vendor);
        return getVendorForm(model, vendor);
    }

    @RequestMapping("/delete")
    public String deleteVendor(HttpSession session, @RequestParam("vendorId") Vendor vendor) {
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_VENDOR)) {
            try {
                context.getVendorService().deleteVendor(vendor);
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
    public String vendorList(Map<String, Object> model) {

        model.put("vendorList", context.getVendorService().getAllVendor());
        return "vendor/vendorList";

    }
}
