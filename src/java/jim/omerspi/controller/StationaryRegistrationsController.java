/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Items;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryregistration;
import jim.omerspi.model.User;
import jim.omerspi.validator.StationaryRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/stationary")
public class StationaryRegistrationsController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private StationaryRegistrationValidator stationaryRegistrationValidator;
    private Stationaryregistration stationaryEdit = null;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getStationaryForm(@ModelAttribute("stationaryRegistrations") Stationaryregistration stationaryRegistrations, Map<String, Object> model) {

        if (stationaryRegistrations.getStationaryRegistrationId() == null) {

            stationaryEdit = null;
        }
        if (stationaryRegistrations.getStationaryRegistrationId() != null) {

            stationaryEdit = stationaryRegistrations;
        }

        model.put("stationaryRegistrations", stationaryRegistrations);
        List<Items> items = context.getItemService().getAllItem();
        model.put("itemList", items);
        model.put("equipmentVendorList", context.getVendorService().getAllValidEquipmentVendors());

        return "stationary/stationaryRegistrationForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdateStationary(Map<String, Object> model, HttpSession session, @ModelAttribute("stationaryRegistrations") Stationaryregistration stationaryRegistrations, BindingResult result, @RequestParam("oldQuantity") Integer oldQuantity) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_STATIONARY)) {
            try {

                stationaryRegistrationValidator.validate(stationaryRegistrations, result);
                if (result.hasErrors()) {
                    return getStationaryForm(stationaryRegistrations, model);
                }
                if (stationaryRegistrations.getUnitPrice() <= 0) {
                    OmerspiUtils.setErrorMessage(session, "Invalid unit price");
                    return getStationaryForm(stationaryRegistrations, model);
                }

                User a = Context.getCurrentUser(session);
                if (stationaryEdit != null) {
                    if (!stationaryEdit.getItems().getItemName().equals(stationaryRegistrations.getItems().getItemName())) {

                        //for edited item with changed item name.
                        List<RequestedItems> requested = context.getRequestedItemService().getAllRequestedItem();
                        Boolean edit = Boolean.TRUE;
                        for (int i = 0; i < requested.size(); i++) {
                            if (requested.get(i).getItems().getItemName().equals(stationaryEdit.getItems().getItemName())) {
                                edit = Boolean.FALSE;
                            }
                        }
                        if (edit) {
                            //check if changed quantity will not make totalquantity less than zero.
                            if (stationaryEdit.getItems().getTotalQuantity() - stationaryEdit.getPurchasedQty() >= 0) {

                                stationaryEdit.getItems().setTotalQuantity(stationaryEdit.getItems().getTotalQuantity() - stationaryEdit.getPurchasedQty());
                                context.getItemService().saveOrUpdateItem(stationaryEdit.getItems());

                                stationaryRegistrations.setAcquisitionDate(new Date());
                                stationaryRegistrations.setEmployee(a.getEmployee());
                                context.getStationaryRegistrationsService().saveOrUpdateStationaryRegistrations(stationaryRegistrations);
                                stationaryRegistrations.getItems().setTotalQuantity(stationaryRegistrations.getItems().getTotalQuantity() + stationaryRegistrations.getPurchasedQty());
                                context.getItemService().saveOrUpdateItem(stationaryRegistrations.getItems());

                                //to set opening stock for edited item.
                                stationaryRegistrations.setOpeningStock(stationaryRegistrations.getItems().getTotalQuantity() - stationaryRegistrations.getPurchasedQty());
                                context.getStationaryRegistrationsService().saveOrUpdateStationaryRegistrations(stationaryRegistrations);
                            } else {
                                OmerspiUtils.setErrorMessage(session, "Sorry, the total quantity can't be less than zero.");
                            }
                        } else {
                            OmerspiUtils.setErrorMessage(session, "Sorry, can't edit name found in requested item(s)");
                        }

                    } else {

                        //for edited item without changing item name.
                        int qtyDiff = stationaryRegistrations.getPurchasedQty() - oldQuantity;
                        //check if changed quantity will not make totalquantity less than zero.
                        if (stationaryRegistrations.getItems().getTotalQuantity() + qtyDiff >= 0) {
                            stationaryRegistrations.setModifiedOn(new Date());
                            context.getStationaryRegistrationsService().saveOrUpdateStationaryRegistrations(stationaryRegistrations);
                            stationaryRegistrations.getItems().setTotalQuantity(stationaryRegistrations.getItems().getTotalQuantity() + qtyDiff);
                            context.getItemService().saveOrUpdateItem(stationaryRegistrations.getItems());


                        } else {
                            OmerspiUtils.setErrorMessage(session, "Sorry, the total quantity can't be less than zero.");
                        }
                    }

                } else if (stationaryEdit == null) {

                    //for new inserted item
                    stationaryRegistrations.setAcquisitionDate(new Date());
                    stationaryRegistrations.setEmployee(a.getEmployee());
                    context.getStationaryRegistrationsService().saveOrUpdateStationaryRegistrations(stationaryRegistrations);
                    stationaryRegistrations.getItems().setTotalQuantity(stationaryRegistrations.getItems().getTotalQuantity() + stationaryRegistrations.getPurchasedQty());
                    context.getItemService().saveOrUpdateItem(stationaryRegistrations.getItems());

                    //to set opening stock for new purchased item
                    stationaryRegistrations.setOpeningStock(stationaryRegistrations.getItems().getTotalQuantity() - stationaryRegistrations.getPurchasedQty());
                    context.getStationaryRegistrationsService().saveOrUpdateStationaryRegistrations(stationaryRegistrations);
                }
                return "redirect:list";
            } catch (Exception ex) {
                ex.printStackTrace();
                OmerspiUtils.setErrorMessage(session, "Error occurred, Try again or contact system administrator.");
                return "redirect:list";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/edit")
    public String editStationaryRegistrations(Map<String, Object> model, @RequestParam("stationaryId") Stationaryregistration stationaryRegistrations) {
        model.put("stationary", stationaryRegistrations);
        return getStationaryForm(stationaryRegistrations, model);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteStationaryRegistrations(HttpSession session, @RequestParam("stationaryId") Stationaryregistration stationaryRegistrations) {
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_STATIONARY)) {
                try {
                    Boolean delete = Boolean.TRUE;
                    List<RequestedItems> requestedItems = context.getRequestedItemService().getAllRequestedItem();
                    //to check if item was requested.
                    for (int i = 0; i < requestedItems.size(); i++) {
                        if (requestedItems.get(i).getItems().getItemName().equals(stationaryRegistrations.getItems().getItemName())) {
                            delete = Boolean.FALSE;
                        }
                    }
                    if (delete) {
                        stationaryRegistrations.getItems().setTotalQuantity(stationaryRegistrations.getItems().getTotalQuantity() - stationaryRegistrations.getPurchasedQty());
                        context.getItemService().saveOrUpdateItem(stationaryRegistrations.getItems());
                        context.getStationaryRegistrationsService().deleteStationaryRegistrations(stationaryRegistrations);
                    } else if (!delete) {
                        OmerspiUtils.setErrorMessage(session, "Sorry can't delete a requested item(s)");
                    }
                    return "redirect:list";

                } catch (Exception ex) {
                    ex.printStackTrace();
                    OmerspiUtils.setErrorMessage(session, "Can't be deleted, used by other object(s)");
                    return "redirect:list";
                }
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping(value = "/list")
    public String stationaryList(HttpSession session, Map<String, Object> model) {
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.VIEW_STATIONARY)) {
                model.put("stationaryRegistrationList", context.getStationaryRegistrationsService().getAllStationaryRegistrations());
                return "stationary/stationaryRegistrationList";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping(value = "/summary/stock")
    public String stationarySummary(HttpSession session, Map<String, Object> model) {
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.VIEW_ITEM)) {
                model.put("stationarySummary", context.getItemService().getAllItem());
                return "stationary/stockSummary";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping(value = "/outOfStock.htm")
    public String outOfStockStationary(HttpSession session, Map<String, Object> model) {
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.VIEW_STATIONARY)) {
                model.put("outOfStock", context.getStationaryRegistrationsService().getOutOfStockStationary());
                return "stationary/outOfStockList";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again");
            return "redirect:/login.htm";

        }
    }
}
