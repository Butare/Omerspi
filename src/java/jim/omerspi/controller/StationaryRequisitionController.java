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
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryregistration;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JOHN
 */
@Controller
public class StationaryRequisitionController {

    @Autowired
    private ServiceContext context;
    //  @Autowired
    private MailMail mailMail = new MailMail();

    @RequestMapping("/addItemForm")
    public String addItemForm(HttpSession session, Map<String, Object> model, @RequestParam(value = "categoryId", required = false) Category category, @RequestParam(value = "categoryTypeId", required = false) Categorytype categoryType) {

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION)) {
                Items item = new Items();
                model.put("addItem", item);

                List<Category> notFurnitureCategories = context.getCategoryService().getAllNotOfficeAssetCategory();
                model.put("categoryList", notFurnitureCategories);

                if (category == null) {
                    category = context.getCategoryService().getCategoryById(0);
                }
                model.put("selectedCategory", category);
                model.put("categoryTypeList", context.getCategoryTypeService().getCategoryTypeByNotOfficeAssetCategory(category));

                if (categoryType == null) {
                    categoryType = context.getCategoryTypeService().getCategoryTypeById(0);
                }
                model.put("selectedCategoryType", categoryType);
                model.put("stationaryList", context.getStationaryRegistrationsService().getAllAvailableStationaryByCategoryType(categoryType));

                return "stationary/stationaryRequisitionAddItemForm";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/addItem")
    public String addItem(HttpSession session, @RequestParam("itemId") Items item, @RequestParam("quantity") Integer quantity) {
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION)) {
                ItemRequest itemRequest = ItemRequest.getCurrent(session);
                itemRequest.setQuantity(item, quantity);
                return "redirect:requestedItemList";
            } else {
                return "redirect:/login.htm";
            }

        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error, No Item added. Try again.");
            return "redirect: addItemForm";
        }
    }

    @RequestMapping("/editItem")
    public String editItem(HttpSession session, @RequestParam("itemId") Items item, Map<String, Object> model) {
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

    @RequestMapping("/deleteItem")
    public String deleteItem(HttpSession session, @RequestParam("itemId") Items item) {

        ItemRequest itemRequest = ItemRequest.getCurrent(session);
        itemRequest.setQuantity(item, 0);

        return "redirect:requestedItemList";
    }

    @RequestMapping("/requestedItemList")
    public String requestedItemList(HttpSession session, Map<String, Object> model) {
        try {
            Map<Items, Integer> requestedItems = ItemRequest.getCurrent(session).getItems();
            model.put("requestedItemMap", requestedItems);
            return "stationary/stationaryRequisitionForm";
        } catch (Exception ex) {
            return "redirect:/login.html";
        }
    }

    @RequestMapping("/saveOrUpdateRequestedItems")
    public String saveOrUpdateRequestedItems(HttpSession session) {

        try {
            User user = Context.getCurrentUser(session);
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_PROFESSIONAL)) {
                Stationaryrequisition stationaryRequisition = new Stationaryrequisition();
                stationaryRequisition.setEmployee(user.getEmployee());
                stationaryRequisition.setRequisitionDate(new Date());
                stationaryRequisition.setStatus("SENT BY PROFESSIONAL");
                stationaryRequisition.setStationarySeen(Boolean.FALSE);

                Map<Items, Integer> items = ItemRequest.getCurrent(session).getItems();
                for (Map.Entry<Items, Integer> entry : items.entrySet()) {
                    Items item = entry.getKey();
                    Integer quantity = entry.getValue();
                    RequestedItems requestedItem = new RequestedItems();

                    context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
                    List<Stationaryregistration> it = context.getStationaryRegistrationsService().getStationaryregistrationsByItem(item);
                    if (it != null) {
                        context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
                        for (Stationaryregistration st : it) {
                            requestedItem.setItems(st.getItems());
                            requestedItem.setRequestedQty(quantity);
                            requestedItem.setStationaryrequisition(stationaryRequisition);
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
                            String subject = "Hello, " + hod.get(i).getEmployee().getFirstName() + "!\nYou have New Stationary Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, hod.get(i).getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }
                    }
                }

                OmerspiUtils.setInfoMessage(session, "Statationary requisition submitted.");
                return "redirect:/stationaryRequisition/byEmployee";
            }
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_HOD)) {

                Stationaryrequisition stationaryRequisition = new Stationaryrequisition();
                stationaryRequisition.setEmployee(user.getEmployee());
                stationaryRequisition.setRequisitionDate(new Date());
                stationaryRequisition.setStatus("SENT BY HOD");
                stationaryRequisition.setStationarySeen(Boolean.FALSE);

                Map<Items, Integer> items = ItemRequest.getCurrent(session).getItems();
                for (Map.Entry<Items, Integer> entry : items.entrySet()) {
                    Items item = entry.getKey();
                    Integer quantity = entry.getValue();
                    RequestedItems requestedItem = new RequestedItems();

                    context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
                    List<Stationaryregistration> it = context.getStationaryRegistrationsService().getStationaryregistrationsByItem(item);
                    if (it != null) {
                        context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
                        for (Stationaryregistration st : it) {
                            requestedItem.setItems(st.getItems());
                            requestedItem.setRequestedQty(quantity);
                            requestedItem.setStationaryrequisition(stationaryRequisition);
                            context.getRequestedItemService().saveOrUpdateRequestedItem(requestedItem);
                        }
                    }
                }
                ItemRequest.getCurrent(session).clear();

                List<User> daf = context.getUserService().getAllUser();
                for (int i = 0; i < daf.size(); i++) {
                    if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                        try {
                            String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Stationary Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }
                    }
                }

                OmerspiUtils.setInfoMessage(session, "Statationary requisition submitted.");
                return "redirect:/stationaryRequisition/byEmployee";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error, Form was empty. Try again.");
            return "redirect:/stationaryRequisition/byEmployee";

        }
    }

    @RequestMapping("stationaryRequisition/byEmployee")
    public String StationaryRequisitionByEmployeList(HttpSession session, Map<String, Object> model) {

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION)) {
                User user = Context.getCurrentUser(session);
                List<Stationaryrequisition> stationaryRequisitionByEmployeeList = context.getStationaryRequisitionService().getStationaryRequisitionByEmployee(user.getEmployee());
                model.put("stationaryRequisitionByEmployeeList", stationaryRequisitionByEmployeeList);
                return "stationary/stationaryRequisitionList";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/searchRequestedItem")
    public String searchRequestedItemByStationaryRequisition(HttpSession session, Map<String, Object> model, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition) {

        try {
            List<RequestedItems> requestedItemList = context.getRequestedItemService().getRequestedItemByStationaryRequisition(stationaryRequisition);

            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && stationaryRequisition.getStatus().equals("SERVED")) {
                stationaryRequisition.setStationarySeen(Boolean.TRUE);
                context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
            }
            model.put("requestedItemList", requestedItemList);
            model.put("stationaryRequisition", stationaryRequisition);
            return "stationary/stationaryRequestedList";
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/delete")
    public String deleteStationaryRequisition(HttpSession session, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition) {

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION)) {
                context.getStationaryRequisitionService().deleteStationaryRequisition(stationaryRequisition);
                return "redirect:/stationaryRequisition/byEmployee";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/ByDepartment/list")
    public String stationaryRequisitionByDepartment(HttpServletRequest request, Map<String, Object> model) {

        try {
            if (Context.getCurrentUser(request.getSession()) != null) {

                HttpSession session = request.getSession(true);
                User a = Context.getCurrentUser(session);
                if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {


                    List<Stationaryrequisition> stationaryRequisitionByDepartment = context.getStationaryRequisitionService().getStationaryRequisitionByDepartment(a.getEmployee().getDepartment());

                    model.put("stationaryRequisitionSuperList", stationaryRequisitionByDepartment);
                    return "stationary/stationaryRequisitionListSuper";


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

    @RequestMapping("stationaryRequisition/Accepted/HOD/list")
    public String stationaryRequisitionHODAccepted(HttpSession session, Map<String, Object> model) {

        try {
            User user = Context.getCurrentUser(session);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                List<Stationaryrequisition> hodAcceptedStationaryRequisition = context.getStationaryRequisitionService().getHodPersonalApprovedStationaryRequisition(user.getEmployee().getDepartment());
                model.put("stationaryRequisitionSuperList", hodAcceptedStationaryRequisition);
                return "stationary/stationaryRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("stationaryRequisition/Rejected/HOD/list")
    public String stationaryRequisitionHODRejected(HttpSession session, Map<String, Object> model) {

        try {
            User user = Context.getCurrentUser(session);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                List<Stationaryrequisition> hodAcceptedStationaryRequisition = context.getStationaryRequisitionService().getHodPersonalRejectedStationaryRequisition(user.getEmployee().getDepartment());
                model.put("stationaryRequisitionSuperList", hodAcceptedStationaryRequisition);
                return "stationary/stationaryRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("stationaryRequisition/Accepted/DAF/list")
    public String stationaryRequisitionDafAccepted(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD) || (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF))) {
            List<Stationaryrequisition> dafAcceptedStationaryRequisition = context.getStationaryRequisitionService().getAllDafApprovedStationaryRequisition();
            model.put("stationaryRequisitionSuperList", dafAcceptedStationaryRequisition);
            return "stationary/stationaryRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("stationaryRequisition/Rejected/DAF/list")
    public String stationaryRequisitionDafRejected(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
            List<Stationaryrequisition> dafRejectedStationaryRequisition = context.getStationaryRequisitionService().getAllDafRejectedStationaryRequisition();
            model.put("stationaryRequisitionSuperList", dafRejectedStationaryRequisition);
            return "stationary/stationaryRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("stationaryRequisition/accepted/AllDepartments/list")
    public String stationaryRequisitionList(HttpServletRequest request, Map<String, Object> model) {

        if (Context.getCurrentUser(request.getSession()) != null) {

            HttpSession session = request.getSession(true);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

                List<Stationaryrequisition> allAcceptedStationaryRequisitionList = context.getStationaryRequisitionService().getAllHodApprovedStationaryRequisition();
                model.put("stationaryRequisitionSuperList", allAcceptedStationaryRequisitionList);
                return "stationary/stationaryRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("stationaryRequisition/requests/HoD/list")
    public String hodSentStationaryRequisitionList(Map<String, Object> model) {
        try {
            List<Stationaryrequisition> sentHodStationaryRequisitionList = context.getStationaryRequisitionService().getAllHodSentStationaryRequisition();
            model.put("stationaryRequisitionSuperList", sentHodStationaryRequisitionList);
            return "stationary/stationaryRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("stationaryRequisition/served/list")
    public String allServedStationaryRequisition(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            List<Stationaryrequisition> servedStationaryRequisition = context.getStationaryRequisitionService().getAllServedStationaryRequisition();
            model.put("stationaryRequisitionSuperList", servedStationaryRequisition);
            return "stationary/stationaryRequisitionListSuper";
        } else {
            throw new AuthenticationException(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF);
        }
    }

    @RequestMapping("stationaryRequisition/commented/list")
    public String allCommentedStationaryRequisition(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            List<Stationaryrequisition> servedStationaryRequisition = context.getStationaryRequisitionService().getAllCommentedStationaryRequisition();
            model.put("stationaryRequisitionSuperList", servedStationaryRequisition);
            return "stationary/stationaryRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    // DashBoard Handler Methods
    @RequestMapping("/stationaryRequisition/hodPendingList.dash")
    public String stationaryRequisitionHodPendingList(HttpSession session, Map<String, Object> model) {

        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> hodPendingStationaryRequisitionList = context.getStationaryRequisitionService().getHodPendingStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", hodPendingStationaryRequisitionList);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/hodRejectedList.dash")
    public String stationaryRequisitionHodRejectedList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> hodRejectedStationaryRequisitionList = context.getStationaryRequisitionService().getHodRejectedStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", hodRejectedStationaryRequisitionList);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/profDafPendingList.dash")
    public String stationaryRequisitionProfDafPendingList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> profDafStationaryRequisitionList = context.getStationaryRequisitionService().getProfDafPendingStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", profDafStationaryRequisitionList);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/hodDafPendingList.dash")
    public String stationaryRequisitionHodDafPendingList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> hodDafPendingStationaryRequisitionList = context.getStationaryRequisitionService().getHodDafPendingStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", hodDafPendingStationaryRequisitionList);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/dafRejectedList.dash")
    public String stationaryRequisitionDafRejectedList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> dafRejectedStationaryRequisitionList = context.getStationaryRequisitionService().getDafRejectedStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", dafRejectedStationaryRequisitionList);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("stationaryRequisition/allDafPendingList.dash")
    public String allDafPendingList(HttpServletRequest request, Map<String, Object> model) {

        if (Context.getCurrentUser(request.getSession()) != null) {

            HttpSession session = request.getSession(true);

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

                List<Stationaryrequisition> allDafPendingList = context.getStationaryRequisitionService().getAllDafPendingStationaryRequisition();
                model.put("stationaryRequisitionSuperList", allDafPendingList);
                return "stationary/stationaryRequisitionListSuper";
            } else {
                return "redirect:/login.htm";
            }
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/logisticsPendingList.dash")
    public String logisticsStationaryPendingList(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> logisticsPendingList = context.getStationaryRequisitionService().getDafApprovedStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", logisticsPendingList);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/servedListByEmployee.dash")
    public String logisticsStationaryServedListByEmployee(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> servedListByEmployee = context.getStationaryRequisitionService().getServedStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", servedListByEmployee);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/stationaryRequisition/commentedListByEmployee.dash")
    public String logisticsStationaryCommentedListByEmployee(HttpSession session, Map<String, Object> model) {
        try {
            User user = Context.getCurrentUser(session);

            List<Stationaryrequisition> commentedListByEmployee = context.getStationaryRequisitionService().getCommentedStationaryRequisitionByEmployee(user.getEmployee());
            model.put("stationaryRequisitionByEmployeeList", commentedListByEmployee);
            return "stationary/stationaryRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }
}
