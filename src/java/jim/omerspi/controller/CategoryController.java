/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private CategoryValidator categoryValidator;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getCategoryForm(Map<String, Object> model, @ModelAttribute("category") Category category) {
        model.put("category", category);
        return "category/categoryForm";
    }

    @RequestMapping("/edit")
    public String editCategory(Map<String, Object> model, @RequestParam("categoryId") Category category) {
        System.out.println(" Category name is : " + category.getCategoryName());
        model.put("category", category);
        return "category/categoryForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdate(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("category") Category category, BindingResult result) {
        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_CATEGORY)) {
            try {
                categoryValidator.validate(category, result);
                if (result.hasErrors()) {
                    return getCategoryForm(model, category);
                }
                if (category.getCategoryId() != null) {
                    OmerspiUtils.setInfoMessage(request.getSession(), "Category Successfully Updated.");
                }
                if (category.getCategoryId() == null) {
                    OmerspiUtils.setInfoMessage(request.getSession(), "Category Successfully Added.");
                }
                category.setCategoryName(category.getCategoryName().toUpperCase());
                context.getCategoryService().saveOrUpdateCategory(category);
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Error occurred, Try again.");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/list")
    public String categoryList(Map<String, Object> model) {

        model.put("categoryList", context.getCategoryService().getAllCategory());
        return "category/categoryList";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteCategory(HttpServletRequest request, @RequestParam("categoryId") Category category) {
        if (Context.hasPrivilege(request.getSession(), PrivilegeConstants.EDIT_CATEGORY)) {
            try {
                context.getCategoryService().deleteCategory(category);
                OmerspiUtils.setInfoMessage(request.getSession(), "Category Successfully Deleted.");
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(request.getSession(), "Can't be deleted, is used by other object(s)");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }
}
