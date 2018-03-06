/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.AuthenticationException;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.ItemRequest;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.Category;
import jim.omerspi.model.Categorytype;
import jim.omerspi.model.Items;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryregistration;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author Jimmy
 */
@Controller
@RequestMapping("/officeAsset")
public class OfficeAssetRequisitionController {

    @Autowired
    private ServiceContext context;
    //  @Autowired
    private MailMail mailMail = new MailMail();

    @RequestMapping("/addOfficecAssetForm")
    public String addOfficeAssetForm(HttpSession session, Map<String, Object> model, @RequestParam(value = "categoryId", required = false) Category category, @RequestParam(value = "categoryTypeId", required = false) Categorytype categoryType) {

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION)) {
                Items item = new Items();
                model.put("addItem", item);

                List<Category> notFurnitureCategories = context.getCategoryService().getAllOfficeAssetCategory();
                model.put("categoryList", notFurnitureCategories);

                if (category == null) {
                    category = context.getCategoryService().getCategoryById(0);
                }
                model.put("selectedCategory", category);
                model.put("categoryTypeList", context.getCategoryTypeService().getCategoryTypeByOfficeAssetCategory(category));

                if (categoryType == null) {
                    categoryType = context.getCategoryTypeService().getCategoryTypeById(0);
                }
                model.put("selectedCategoryType", categoryType);
                model.put("stationaryList", context.getStationaryRegistrationsService().getAllAvailableStationaryByCategoryType(categoryType));

                return "officeasset/officeAssetRequisitionAddItemForm";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/addOfficeAsset")
    public String addOfficeAsset(HttpSession session, @RequestParam("itemId") Items item, @RequestParam("quantity") Integer quantity) {
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION)) {
                ItemRequest itemRequest = ItemRequest.getCurrent(session);
                itemRequest.setQuantity(item, quantity);
                return "redirect:requestedOfficeAssetList";
            } else {
                return "redirect:/login.htm";
            }

        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error, No Item added. Try again.");
            return "redirect: addItemForm";
        }
    }

    @RequestMapping("/editOfficeAsset")
    public String editOfficeAsset(HttpSession session, @RequestParam("itemId") Items item, Map<String, Object> model) {
        try {
            ItemRequest itemRequest = ItemRequest.getCurrent(session);

            int quantity = itemRequest.getQuantity(item);

            model.put("item", item);
            model.put("quantity", quantity);
            return "stationary/stationaryRequisitionEditQuantity";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/deleteOfficeAsset")
    public String deleteOfficeAsset(HttpSession session, @RequestParam("itemId") Items item) {

        ItemRequest itemRequest = ItemRequest.getCurrent(session);
        itemRequest.setQuantity(item, 0);

        return "redirect:requestedItemList";
    }

    @RequestMapping("/requestedOfficeAssetList")
    public String requestedOfficeAssetList(HttpSession session, Map<String, Object> model, @ModelAttribute("officeAsset") Officeassetrequisition officeAsset) {
        try {
            Map<Items, Integer> requestedItems = ItemRequest.getCurrent(session).getItems();
            model.put("requestedItemMap", requestedItems);
            return "officeasset/officeAssetRequisitionForm";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/saveOrUpdateRequestedItems")
    public String saveOrUpdateRequestedOfficeAsset(HttpSession session, @ModelAttribute("officeAsset") Officeassetrequisition officeAssetRequisition) {

        try {
            User user = Context.getCurrentUser(session);
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_PROFESSIONAL)) {

                officeAssetRequisition.setEmployee(user.getEmployee());
                officeAssetRequisition.setRequisitionDate(new Date());
                officeAssetRequisition.setStatus("SENT BY PROFESSIONAL");
                officeAssetRequisition.setOfficeRequisitionSeen(Boolean.FALSE);

                Map<Items, Integer> items = ItemRequest.getCurrent(session).getItems();
                for (Map.Entry<Items, Integer> entry : items.entrySet()) {
                    Items item = entry.getKey();
                    Integer quantity = entry.getValue();
                    RequestedItems requestedItem = new RequestedItems();

                    context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
                    List<Stationaryregistration> it = context.getStationaryRegistrationsService().getStationaryregistrationsByItem(item);
                    if (it != null) {
                        context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
                        for (Stationaryregistration st : it) {
                            requestedItem.setItems(item);
                            requestedItem.setRequestedQty(quantity);
                            requestedItem.setOfficeassetrequisition(officeAssetRequisition);
                            context.getRequestedItemService().saveOrUpdateRequestedItem(requestedItem);
                        }
                    }
                }

                ItemRequest.getCurrent(session).clear();

                //SEND EMAIL TO HOD
                List<User> hod = context.getStationaryRequisitionService().getHoD(user.getEmployee().getDepartment());
                for (int i = 0; i < hod.size(); i++) {
                    if (hod.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                        try {
                            String subject = "Hello, " + hod.get(i).getEmployee().getFirstName() + "!\nYou have new office asset Requisition. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, hod.get(i).getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }
                    }
                }

                OmerspiUtils.setInfoMessage(session, "Office asset requisition submitted.");
                return "redirect:byEmployee";
            }
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_HOD)) {

                officeAssetRequisition.setEmployee(user.getEmployee());
                officeAssetRequisition.setRequisitionDate(new Date());
                officeAssetRequisition.setStatus("SENT BY HOD");
                officeAssetRequisition.setOfficeRequisitionSeen(Boolean.FALSE);

                Map<Items, Integer> items = ItemRequest.getCurrent(session).getItems();
                for (Map.Entry<Items, Integer> entry : items.entrySet()) {
                    Items item = entry.getKey();
                    Integer quantity = entry.getValue();
                    RequestedItems requestedItem = new RequestedItems();

                    context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
                    List<Stationaryregistration> it = context.getStationaryRegistrationsService().getStationaryregistrationsByItem(item);
                    if (it != null) {
                        context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
                        for (Stationaryregistration st : it) {
                            requestedItem.setItems(item);
                            requestedItem.setRequestedQty(quantity);
                            requestedItem.setOfficeassetrequisition(officeAssetRequisition);
                            context.getRequestedItemService().saveOrUpdateRequestedItem(requestedItem);
                        }
                    }
                }
                ItemRequest.getCurrent(session).clear();

                List<User> daf = context.getUserService().getAllUser();
                for (int i = 0; i < daf.size(); i++) {
                    if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                        try {
                            String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Office Asset Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }
                    }
                }

                OmerspiUtils.setInfoMessage(session, "Office asset requisition submitted.");
                return "redirect:byEmployee";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            OmerspiUtils.setErrorMessage(session, "Error, Some field(s) not filled. Try again.");
            return "redirect:/officeAsset/byEmployee";

        }
    }

    @RequestMapping("/byEmployee")
    public String OfficeAssetRequisitionByEmployeList(HttpSession session, Map<String, Object> model) {

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION)) {
                User user = Context.getCurrentUser(session);
                List<Officeassetrequisition> officeAssetRequisitionByEmployeeList = context.getOfficeAssetRequisitionService().getOfficeAssetRequisitionByEmployee(user.getEmployee());
                model.put("officeAssetRequisitionByEmployeeList", officeAssetRequisitionByEmployeeList);
                return "officeasset/officeAssetRequisitionList";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/searchRequestedItem")
    public String searchRequestedItemByOfficeAssetRequisition(HttpSession session, Map<String, Object> model, @RequestParam("officeAssetRequisitionId") Officeassetrequisition officeAssetRequisition) {

        try {
            List<RequestedItems> requestedItemList = context.getRequestedItemService().getRequestedItemByOfficeAssetRequisition(officeAssetRequisition);

            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && officeAssetRequisition.getStatus().equals("SERVED")) {
                officeAssetRequisition.setOfficeRequisitionSeen(Boolean.TRUE);
                context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
            }
            model.put("requestedItemList", requestedItemList);
            model.put("officeAssetRequisition", officeAssetRequisition);
            return "officeasset/officeAssetRequestedList";
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/delete")
    public String deleteOfficeAssetRequisition(HttpSession session, @RequestParam("officeAssetRequisitionId") Officeassetrequisition officeAssetRequisition) {

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION)) {
                context.getOfficeAssetRequisitionService().deleteOfficeAssetRequisition(officeAssetRequisition);
                return "redirect:byEmployee";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/ByDepartment/list")
    public String officeAssetRequisitionByDepartment(HttpServletRequest request, Map<String, Object> model) {

        try {
            if (Context.getCurrentUser(request.getSession()) != null) {

                HttpSession session = request.getSession(true);
                User a = Context.getCurrentUser(session);
                if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {

                    List<Officeassetrequisition> officeAssetRequisitionByDepartment = context.getOfficeAssetRequisitionService().getOfficeAssetRequisitionByDepartment(a.getEmployee().getDepartment());

                    model.put("officeAssetRequisitionSuperList", officeAssetRequisitionByDepartment);
                    return "officeasset/officeAssetRequisitionListSuper";


                } else {
                    return "redirect:/login.htm";
                }
            } else {

                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/Accepted/HOD/list")
    public String stationaryOfficeAssetHODAccepted(HttpSession session, Map<String, Object> model) {

        try {
            User user = Context.getCurrentUser(session);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                List<Officeassetrequisition> hodAcceptedOfficeAssetRequisition = context.getOfficeAssetRequisitionService().getHodPersonalApprovedOfficeAssetRequisition(user.getEmployee().getDepartment());
                model.put("officeAssetRequisitionSuperList", hodAcceptedOfficeAssetRequisition);
                return "officeasset/officeAssetRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/Rejected/HOD/list")
    public String officeAssetRequisitionHODRejected(HttpSession session, Map<String, Object> model) {

        try {
            User user = Context.getCurrentUser(session);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                List<Officeassetrequisition> hodRejectedOfficeAssetRequisition = context.getOfficeAssetRequisitionService().getHodPersonalRejectedOfficeAssetRequisition(user.getEmployee().getDepartment());
                model.put("officeAssetRequisitionSuperList", hodRejectedOfficeAssetRequisition);
                return "officeasset/officeAssetRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/Accepted/DAF/list")
    public String officeAssetRequisitionDafAccepted(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD) || (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF))) {
            List<Officeassetrequisition> dafAcceptedOfficeAssetRequisition = context.getOfficeAssetRequisitionService().getAllDafApprovedOfficeAssetRequisition();
            model.put("officeAssetRequisitionSuperList", dafAcceptedOfficeAssetRequisition);
            return "officeasset/officeAssetRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/Rejected/DAF/list")
    public String officeAssetRequisitionDafRejected(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
            List<Officeassetrequisition> dafRejectedOfficeAssetRequisition = context.getOfficeAssetRequisitionService().getAllDafRejectedOfficeAssetRequisition();
            model.put("officeAssetRequisitionSuperList", dafRejectedOfficeAssetRequisition);
            return "officeasset/officeAssetRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/accepted/AllDepartments/list")
    public String officeAssetRequisitionList(HttpServletRequest request, Map<String, Object> model) {

        if (Context.getCurrentUser(request.getSession()) != null) {

            HttpSession session = request.getSession(true);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

                List<Officeassetrequisition> allAcceptedOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getAllHodApprovedOfficeAssetRequisition();
                model.put("officeAssetRequisitionSuperList", allAcceptedOfficeAssetRequisitionList);
                return "officeasset/officeAssetRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/requests/HoD/list")
    public String hodSentOfficeAssetRequisitionList(Map<String, Object> model) {
        try {
            List<Officeassetrequisition> sentHodOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getAllHodSentOfficeAssetRequisition();
            model.put("officeAssetRequisitionSuperList", sentHodOfficeAssetRequisitionList);
            return "officeasset/officeAssetRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/served/list")
    public String allServedOfficeAssetRequisition(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            List<Officeassetrequisition> servedOfficeAssetRequisition = context.getOfficeAssetRequisitionService().getAllServedOfficeAssetRequisition();
            model.put("officeAssetRequisitionSuperList", servedOfficeAssetRequisition);
            return "officeasset/officeAssetRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
//            throw new AuthenticationException(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF);
        }
    }

    @RequestMapping("/commented/list")
    public String allCommentedOfficeAssetRequisition(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            List<Officeassetrequisition> servedOfficeAssetRequisition = context.getOfficeAssetRequisitionService().getAllCommentedOfficeAssetRequisition();
            model.put("officeAssetRequisitionSuperList", servedOfficeAssetRequisition);
            return "officeasset/officeAssetRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    // DashBoard Handler Methods
    @RequestMapping("/hodPendingList.dash")
    public String officeAssetRequisitionHodPendingList(HttpSession session, Map<String, Object> model) {

        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> hodPendingOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getHodPendingOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", hodPendingOfficeAssetRequisitionList);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/hodRejectedList.dash")
    public String officeAssetRequisitionHodRejectedList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> hodRejectedOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getHodRejectedOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", hodRejectedOfficeAssetRequisitionList);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/profDafPendingList.dash")
    public String officeAssetRequisitionProfDafPendingList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> profDafOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getProfDafPendingOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", profDafOfficeAssetRequisitionList);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/hodDafPendingList.dash")
    public String officeAssetRequisitionHodDafPendingList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> hodDafPendingOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getHodDafPendingOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", hodDafPendingOfficeAssetRequisitionList);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/dafRejectedList.dash")
    public String officeAssetRequisitionDafRejectedList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> dafRejectedOfficeAssetRequisitionList = context.getOfficeAssetRequisitionService().getDafRejectedOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", dafRejectedOfficeAssetRequisitionList);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/allDafPendingList.dash")
    public String allOfficeAssetDafPendingList(HttpServletRequest request, Map<String, Object> model) {

        if (Context.getCurrentUser(request.getSession()) != null) {

            HttpSession session = request.getSession(true);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

                List<Officeassetrequisition> allDafPendingList = context.getOfficeAssetRequisitionService().getAllDafPendingOfficeAssetRequisition();
                model.put("officeAssetRequisitionSuperList", allDafPendingList);
                return "officeasset/officeAssetRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/logisticsPendingList.dash")
    public String logisticsOfficeAssetPendingList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> logisticsPendingList = context.getOfficeAssetRequisitionService().getDafApprovedOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", logisticsPendingList);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/servedListByEmployee.dash")
    public String logisticsOfficeAssetServedListByEmployee(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> servedListByEmployee = context.getOfficeAssetRequisitionService().getServedOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", servedListByEmployee);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/commentedListByEmployee.dash")
    public String logisticsOfficeAssetCommentedListByEmployee(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Officeassetrequisition> commentedListByEmployee = context.getOfficeAssetRequisitionService().getCommentedOfficeAssetRequisitionByEmployee(user.getEmployee());
            model.put("officeAssetRequisitionByEmployeeList", commentedListByEmployee);
            return "officeasset/officeAssetRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }
}
