 /* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryrequisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JOHN
 */
@Controller
public class StationaryRequisitionServiceController {

    @Autowired
    private ServiceContext context;
    // @Autowired
    private MailMail mailMail = new MailMail();
    private Integer edit = 0;

    @RequestMapping("/searchRequestedItemToServe")
    public String searchRequestedItemByStationaryRequisitionToServe(Map<String, Object> model, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition) {

        edit = 0;
        RequestedItems requestedItem = new RequestedItems();
        model.put("stationaryRequisitionService", requestedItem);
        model.put("stationaryRequisition", stationaryRequisition);
        List<RequestedItems> requestedItemList = context.getRequestedItemService().getRequestedItemByStationaryRequisition(stationaryRequisition);
        model.put("requestedItemList", requestedItemList);
        return "stationary/stationaryRequisitionServiceForm";

    }

    @RequestMapping("/editServedStationaryItem")
    public String editServedStationaryItem(Map<String, Object> model, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition) {
        edit = 1;
        model.put("stationaryRequisition", stationaryRequisition);
        model.put("requestedItemList", context.getRequestedItemService().getRequestedItemByStationaryRequisition(stationaryRequisition));
        return "stationary/stationaryRequisitionServiceForm";

    }

    @RequestMapping(value = "/saveOrUpdateServedStationaryRequisition", method = RequestMethod.POST)
    public String saveOrUpdateServedStationaryRequisition(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        int less1 = 0;
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {

            try {

                Enumeration<String> params = request.getParameterNames();

                Map<RequestedItems, Integer> service = new HashMap<RequestedItems, Integer>();
                Map<RequestedItems, String> observe = new HashMap<RequestedItems, String>();
                while (params.hasMoreElements()) {
                    String paramNa = params.nextElement();
                    if (paramNa.startsWith("serviceQty-")) {
                        String[] tok = paramNa.split("-");
                        int id = Integer.parseInt(tok[1]);
                        RequestedItems requests = context.getRequestedItemService().getRequestedItemById(id);
                        int qty = Integer.parseInt(request.getParameter(paramNa));
                        String observation = request.getParameter("observation-" + id);
                        service.put(requests, qty);
                        observe.put(requests, observation);
                    }
                }
                //Map to validate data
                for (Map.Entry<RequestedItems, Integer> data : service.entrySet()) {

                    if (data.getKey().getRequestedQty() < data.getValue()) {
                        less1++;
                    }

                }

                //Map to save validated data

                for (Map.Entry<RequestedItems, Integer> data : service.entrySet()) {

                    if (less1 > 0) {
                        OmerspiUtils.setErrorMessage(session, "Sorry,You can't serve more than requested items.");
                        return "redirect:/searchRequestedItemToServe?stationaryRequisitionId=" + data.getKey().getStationaryrequisition().getStationaryRequisitionId();
                    }
                    if (data.getKey().getItems().getTotalQuantity() - data.getValue() < data.getKey().getItems().getMinimumQty()) {
                        OmerspiUtils.setErrorMessage(session, "Stock can't go beyond minimum quantity.");
                        return "redirect:/searchRequestedItemToServe?stationaryRequisitionId=" + data.getKey().getStationaryrequisition().getStationaryRequisitionId();

                    }
                    for (Map.Entry<RequestedItems, String> obs : observe.entrySet()) {

                        if ((obs.getKey().getRequestedId() == data.getKey().getRequestedId()) && ((edit == 0 && less1 == 0))) {

                            //set and save values for new service.
                            data.getKey().setServedQty(data.getValue());
                            data.getKey().setObservation(obs.getValue());
                            data.getKey().setServiceDate(new Date());
                            data.getKey().setRemainingQty(data.getKey().getItems().getTotalQuantity() - data.getValue());
                            context.getRequestedItemService().saveOrUpdateRequestedItem(data.getKey());
                            
                            //set and save values for served requisition
                            Calendar now = Calendar.getInstance();
                            data.getKey().getStationaryrequisition().setServiceNumber("STR" + data.getKey().getStationaryrequisition().getStationaryRequisitionId() + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR));
                            data.getKey().getStationaryrequisition().setStatus("SERVED");
                            data.getKey().getStationaryrequisition().setStationarySeen(Boolean.FALSE);
                            data.getKey().getStationaryrequisition().setServiceDate(new Date());
                            context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(data.getKey().getStationaryrequisition());

                            //deduct served qty from item's total quantity for new service
                            data.getKey().getItems().setTotalQuantity(data.getKey().getItems().getTotalQuantity() - data.getValue());
                            context.getItemService().saveOrUpdateItem(data.getKey().getItems());
                            try {
                                //send email to the requester of served requisition.
                                String subject = "Hello, " + data.getKey().getStationaryrequisition().getEmployee().getFirstName() + "!\nYour Stationary Requisition has Served. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                mailMail.sendMail(Constants.omerspiEmail, data.getKey().getStationaryrequisition().getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                            } catch (Exception ex) {
                                OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                            }
                        }

                    }


                }

                OmerspiUtils.setInfoMessage(session, "Stationary requisition served!");
                return "redirect:/stationaryRequisition/Accepted/DAF/list";
            } catch (Exception ex) {
                ex.printStackTrace();
                OmerspiUtils.setErrorMessage(session, "Accepted Qty can't be Empty");
                return "redirect:/stationaryRequisition/Accepted/DAF/list";
            }

        } else {
            OmerspiUtils.setErrorMessage(session, "Error occured,Login and try again or Contact system Administrator for help.");
            return "redirect:/login.htm";
        }

    }
}
