/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Categorytype;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping("/categoryType")
public class CategoryTypeController {
    
    @Autowired
    private ServiceContext context;
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getCategoryTypeForm(@ModelAttribute("categoryType") Categorytype categoryType, Map<String, Object> model) {
        model.put("categoryList", context.getCategoryService().getAllCategory());
        model.put("categoryType", categoryType);
        return "category/categoryTypeForm";
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdateCategoryType(HttpSession session, Map<String, Object> model, @ModelAttribute("categoryType") Categorytype categoryType) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_CATEGORY)) {
            categoryType.setCategoryTypeName(categoryType.getCategoryTypeName().toUpperCase());
            context.getCategoryTypeService().saveOrUpdateCategoryType(categoryType);
            return "redirect:list";
        } else {
            return "redirect:/login.htm";
        }
    }
    
    @RequestMapping(value = "/list")
    public String getCategoryTypeList(Map<String, Object> model) {
        model.put("categoryTypeList", context.getCategoryTypeService().getAllCategoryType());
        return "/category/categoryTypeList";
        
    }
    
    @RequestMapping(value = "/edit")
    public String editCategoryType(Map<String, Object> model, @RequestParam("categoryTypeId") Categorytype categoryType) {
        model.put("categoryType", categoryType);
        return getCategoryTypeForm(categoryType, model);
    }
    
    @RequestMapping(value = "/delete")
    public String deleteCategoryType(HttpSession session, @RequestParam("categoryTypeId") Categorytype categoryType) {
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_CATEGORY)) {
            try {
                context.getCategoryTypeService().deleteCategoryType(categoryType);
                OmerspiUtils.setInfoMessage(session, "Category type deleted");
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Can't be deleted. is used by other object(s)");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }
}
