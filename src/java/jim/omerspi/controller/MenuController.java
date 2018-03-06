/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Map;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.SessionCounterListener;
import jim.omerspi.model.Stationaryregistration;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author JOHN
 */
@Controller
public class MenuController {

    @Autowired
    private ServiceContext context;

    @RequestMapping("/Menu")
    public String getMenu(HttpServletRequest request, Map<String, Object> model) {


        try {
            if (Context.getCurrentUser(request.getSession()) != null) {
                HttpSession session = request.getSession(true);
                User u = Context.getCurrentUser(session);
                System.out.println("Name : " + u.getUserName());
                System.out.println("Paths : " + request.getServletContext().getContextPath().concat(request.getServletPath()));
                System.out.println("Full url : " + request.getRequestURL());

                int hodPendingCarRequitionNumber = context.getCarRequisitionService().getHodPendingCarRequisitionByEmployee(u.getEmployee()).size();
                int hodRejectedCarRequisitionNumber = context.getCarRequisitionService().getHodRejectedCarRequisitionByEmployee(u.getEmployee()).size();
                int hodPendingStationaryRequisitionNumber = context.getStationaryRequisitionService().getHodPendingStationaryRequisitionByEmployee(u.getEmployee()).size();
                int hodRejectedStationaryRequisitionNumber = context.getStationaryRequisitionService().getHodRejectedStationaryRequisitionByEmployee(u.getEmployee()).size();
                int hodPendingOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getHodPendingOfficeAssetRequisitionByEmployee(u.getEmployee()).size();
                int hodRejectedOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getHodRejectedOfficeAssetRequisitionByEmployee(u.getEmployee()).size();

                int departmentPendingCaRequisitionNumber = context.getCarRequisitionService().getAllCarRequisitionByDepartment(u.getEmployee().getDepartment()).size();
                int departmentRejectedCarRequisitionNumber = context.getCarRequisitionService().getHodPersonalRejectedCarRequisition(u.getEmployee().getDepartment()).size();
                int departmentPendingStationaryRequisitionNumber = context.getStationaryRequisitionService().getStationaryRequisitionByDepartment(u.getEmployee().getDepartment()).size();
                int departmentRejectedStationaryRequisitionNumber = context.getStationaryRequisitionService().getHodPersonalRejectedStationaryRequisition(u.getEmployee().getDepartment()).size();
                int departmentPendingOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getOfficeAssetRequisitionByDepartment(u.getEmployee().getDepartment()).size();

                int profDafPendingOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getProfDafPendingOfficeAssetRequisitionByEmployee(u.getEmployee()).size();
                int DafRejectedOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getDafRejectedOfficeAssetRequisitionByEmployee(u.getEmployee()).size();
                int hodDafPendingOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getHodDafPendingOfficeAssetRequisitionByEmployee(u.getEmployee()).size();

                int profDafPendingStationaryRequisitionNumber = context.getStationaryRequisitionService().getProfDafPendingStationaryRequisitionByEmployee(u.getEmployee()).size();
                int dafRejectedStationaryRequisitionNumber = context.getStationaryRequisitionService().getDafRejectedStationaryRequisitionByEmployee(u.getEmployee()).size();
                int hodDafPendingStationaryRequisitionNumber = context.getStationaryRequisitionService().getHodDafPendingStationaryRequisitionByEmployee(u.getEmployee()).size();

                int allDafPendingOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getAllDafPendingOfficeAssetRequisition().size();
                int allDafPendingStationaryRequisitionNumber = context.getStationaryRequisitionService().getAllDafPendingStationaryRequisition().size();
              

                int allLogisticsPendingCarRequisitionNumber = context.getCarRequisitionService().getDafApprovedCarRequisitionByEmployee(u.getEmployee()).size();
                int allServedCarRequisitionByEmployeeNumber = context.getCarRequisitionService().getServedCarRequisitionByEmployee(u.getEmployee()).size();
                int allCommentedCarRequisitionByEmployeeNumber = context.getCarRequisitionService().getCommentedCarRequisitionByEmployee(u.getEmployee()).size();

                int allLogisticsPendingStationaryRequisitionNumber = context.getStationaryRequisitionService().getDafApprovedStationaryRequisitionByEmployee(u.getEmployee()).size();
                int allServedStationaryRequisitionByEmployeeNumber = context.getStationaryRequisitionService().getServedStationaryRequisitionByEmployee(u.getEmployee()).size();
                int allCommentedStationaryRequisitionByEmployeeNumber = context.getStationaryRequisitionService().getCommentedStationaryRequisitionByEmployee(u.getEmployee()).size();

                int allLogisticsPendingOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getDafApprovedOfficeAssetRequisitionByEmployee(u.getEmployee()).size();
                int allServedOfficeAssetRequisitionByEmployeeNumber = context.getOfficeAssetRequisitionService().getServedOfficeAssetRequisitionByEmployee(u.getEmployee()).size();
                int allCommentedOfficeAssetRequisitionByEmployeeNumber = context.getOfficeAssetRequisitionService().getCommentedOfficeAssetRequisitionByEmployee(u.getEmployee()).size();
                
                int allDafPendingCarRequisitionNumber = context.getCarRequisitionService().getAllDafPendingCarRequisition().size();
                int allDafAcceptedStationaryRequisitionNumber = context.getStationaryRequisitionService().getAllDafApprovedStationaryRequisition().size();
                int allDafAcceptedOfficeAssetRequisitionNumber = context.getOfficeAssetRequisitionService().getAllDafApprovedOfficeAssetRequisition().size();

                model.put("hodPendingCarRequitionNumber", hodPendingCarRequitionNumber);
                model.put("hodRejectedCarRequisitionNumber", hodRejectedCarRequisitionNumber);
                model.put("hodPendingStationaryRequisitionNumber", hodPendingStationaryRequisitionNumber);
                model.put("hodRejectedStationaryRequisitionNumber", hodRejectedStationaryRequisitionNumber);
                model.put("hodPendingOfficeAssetRequisitionNumber", hodPendingOfficeAssetRequisitionNumber);
                model.put("hodRejectedOfficeAssetRequisitionNumber", hodRejectedOfficeAssetRequisitionNumber);

                model.put("departmentPendingCaRequisitionNumber", departmentPendingCaRequisitionNumber);
                model.put("departmentRejectedCarRequisitionNumber", departmentRejectedCarRequisitionNumber);
                model.put("departmentPendingStationaryRequisitionNumber", departmentPendingStationaryRequisitionNumber);
                model.put("departmentRejectedStationaryRequisitionNumber", departmentRejectedStationaryRequisitionNumber);
                model.put("departmentPendingOfficeAssetRequisitionNumber", departmentPendingOfficeAssetRequisitionNumber);

                model.put("profDafPendingOfficeAssetRequisitionNumber", profDafPendingOfficeAssetRequisitionNumber);
                model.put("DafRejectedOfficeAssetRequisitionNumber", DafRejectedOfficeAssetRequisitionNumber);
                model.put("hodDafPendingOfficeAssetRequisitionNumber", hodDafPendingOfficeAssetRequisitionNumber);

                model.put("profDafPendingStationaryRequisitionNumber", profDafPendingStationaryRequisitionNumber);
                model.put("dafRejectedStationaryRequisitionNumber", dafRejectedStationaryRequisitionNumber);
                model.put("hodDafPendingStationaryRequisitionNumber", hodDafPendingStationaryRequisitionNumber);


               

                model.put("allDafPendingStationaryRequisitionNumber", allDafPendingStationaryRequisitionNumber);
                model.put("allDafPendingOfficeAssetRequisitionNumber", allDafPendingOfficeAssetRequisitionNumber);

               

                model.put("allLogisticsPendingCarRequisitionNumber", allLogisticsPendingCarRequisitionNumber);
                model.put("allServedCarRequisitionByEmployeeNumber", allServedCarRequisitionByEmployeeNumber);
                model.put("allCommentedCarRequisitionByEmployeeNumber", allCommentedCarRequisitionByEmployeeNumber);

                model.put("allLogisticsPendingStationaryRequisitionNumber", allLogisticsPendingStationaryRequisitionNumber);
                model.put("allServedStationaryRequisitionByEmployeeNumber", allServedStationaryRequisitionByEmployeeNumber);
                model.put("allCommentedStationaryRequisitionByEmployeeNumber", allCommentedStationaryRequisitionByEmployeeNumber);

                model.put("allLogisticsPendingOfficeAssetRequisitionNumber", allLogisticsPendingOfficeAssetRequisitionNumber);
                model.put("allServedOfficeAssetRequisitionByEmployeeNumber", allServedOfficeAssetRequisitionByEmployeeNumber);
                model.put("allCommentedOfficeAssetRequisitionByEmployeeNumber", allCommentedOfficeAssetRequisitionByEmployeeNumber);
                
                model.put("allDafPendingCarRequisitionNumber", allDafPendingCarRequisitionNumber);
                model.put("allDafAcceptedStationaryRequisitionNumber", allDafAcceptedStationaryRequisitionNumber);
                model.put("allDafAcceptedOfficeAssetRequisitionNumber", allDafAcceptedOfficeAssetRequisitionNumber);

//                if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
//                    List<Stationaryregistrations> outOfStock = context.getStationaryRegistrationsService().getOutOfStockStationary();
//
//                    if (!outOfStock.isEmpty()) {
//                        if (outOfStock.size() > 1) {
//                            session.setAttribute(Constants.ALERT_SESSION_ATTR, outOfStock.size() + "  Items are running out of stock");
//                        } else {
//                            session.setAttribute(Constants.ALERT_SESSION_ATTR, outOfStock.size() + "  Item is running out of stock");
//                        }
//                        model.put("outOfStock", outOfStock);
//                    }
//                }



                return "menu/Menu";
            } else {
                return "login/loginForm";

            }
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }
}
