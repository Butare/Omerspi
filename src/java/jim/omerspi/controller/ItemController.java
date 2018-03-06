/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Category;
import jim.omerspi.model.Items;
import jim.omerspi.validator.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ServiceContext context;
    @Autowired
    private ItemValidator itemValidator;
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getItemForm(Map<String, Object> model, @ModelAttribute("item") Items items, @RequestParam(value = "categoryId", required = false) Category category) {
        
        model.put("item", items);
        model.put("categoryList", context.getCategoryService().getAllCategory());
        
        if (category == null) {
            category = context.getCategoryService().getCategoryById(0);
        }
        model.put("selectedCategory", category);
        model.put("categoryTypeList", context.getCategoryTypeService().getCategoryTypeByCategory(category));
        
        return "item/itemForm";
        
    }
    
    @RequestMapping(value = "form", method = RequestMethod.POST)
    public String saveOrUpdateItem(HttpSession session, Map<String, Object> model, @ModelAttribute("item") Items item, BindingResult result) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_ITEM)) {
            try {
                itemValidator.validate(item, result);
                if (result.hasErrors()) {
                    //return getItemForm(model, item);
                }
                item.setItemName(item.getItemName().toUpperCase());
                context.getItemService().saveOrUpdateItem(item);
                return "redirect:list";
            } catch (Exception ex) {
                ex.printStackTrace();
                OmerspiUtils.setErrorMessage(session, "Error occurred, Try again.");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }
    
    @RequestMapping("/edit")
    public String editItem(Map<String, Object> model, @RequestParam("itemId") Items item) {
        
        model.put("item", item);
        
        return getItemForm(model, item, item.getCategorytype().getCategory());
        
        
    }
    
    @RequestMapping("/list")
    public String itemList(Map<String, Object> model) {
        model.put("itemList", context.getItemService().getAllItem());
        return "item/itemNameList";
        
    }
    
    @RequestMapping("/delete")
    public String deleteItemName(HttpSession session, @RequestParam("itemId") Items item) {
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_ITEM)) {
            try {
                context.getItemService().deleteItem(item);
                return "redirect:list";
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "Can't be deleted, is used by other object(s)");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }
}
