package jim.omerspi.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import jim.omerspi.ServiceContext;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.RequestedItems;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.OfficeAssetDetailForm;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.model.Officeassetrequisitionservice;

/**
 *
 * @author Jimmy
 */
@Controller
@RequestMapping("/officeAssetService")
public class OfficeAssetRequisitionServiceController {
    
    @Autowired
    private ServiceContext context;
    private MailMail mail = new MailMail();
    private static List<Officeassetrequisitionservice> requisitionService = new ArrayList<Officeassetrequisitionservice>();
    private RequestedItems requests = new RequestedItems();
    
    @RequestMapping("/searchRequestedItemToServe")
    public String searchRequestedItemByOfficeAssetRequisitionToServe(HttpSession session, Map<String, Object> model, @RequestParam("officeAssetRequisitionId") Officeassetrequisition officeAssetRequisition) {
        
        List<RequestedItems> requestedItems = context.getRequestedItemService().getRequestedItemByOfficeAssetRequisition(officeAssetRequisition);
        Map<RequestedItems, Integer> servedQuantities = new HashMap<RequestedItems, Integer>();
        
        for (RequestedItems requestedItem : requestedItems) {
            int servedQuantity = context.getOfficeAssetRequisitionServiceService().getAllOfficeAssetRequisitionServiceByRequestedItem(requestedItem).size();
            servedQuantities.put(requestedItem, servedQuantity);
            
        }
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && officeAssetRequisition.getStatus().equals("SERVED")) {
            officeAssetRequisition.setOfficeRequisitionSeen(Boolean.TRUE);
            context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
        }
        model.put("officeAssetRequisition", officeAssetRequisition);
        model.put("requestedItems", requestedItems);
        model.put("servedQuantities", servedQuantities);
        
        return "officeasset/officeAssetRequisitionServiceForm";
        
    }
    
    @RequestMapping(value = "/addAssetDetail")
    public String addServedOfficeAssetDetails(HttpServletRequest request, Map<String, Object> model, @RequestParam("requestedId") RequestedItems requested) {
        
        OfficeAssetDetailForm serviceForm = new OfficeAssetDetailForm();
        
        requisitionService.clear();
        requests = requested;
        
        List<Officeassetrequisitionservice> officeServiceByRequested = context.getOfficeAssetRequisitionServiceService().getAllOfficeAssetRequisitionServiceByRequestedItem(requested);
        model.put("officeAssetRequisition", context.getOfficeAssetRequisitionService().getOfficeAssetRequisitionById(requested.getOfficeassetrequisition().getOfficeAssetRequisitionId()));
        model.put("requestedItem", requested);
        
        for (int i = 0; i < requested.getRequestedQty(); i++) {
            if (!officeServiceByRequested.isEmpty()) {
                Officeassetrequisitionservice svc = officeServiceByRequested.get(0);
                requisitionService.add(svc);
                officeServiceByRequested.remove(svc);
                
            } else {
                requisitionService.add(new Officeassetrequisitionservice());
            }
        }
        serviceForm.setRequisitionService(requisitionService);
        model.put("serviceForm", serviceForm);
        
        
        return "officeasset/officeAssetServiceDetailsForm";
        
    }
    
    @RequestMapping(value = "/saveOrUpdateServedOfficeAssetDetails", method = RequestMethod.POST)
    public String saveOrUpdateServedStationaryRequisition(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("officeAssetDetails") OfficeAssetDetailForm officeAssetDetailForm) {
        
        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            
            int servedQty = 0;
            
            try {
                List<Officeassetrequisitionservice> officeAssetDetail = officeAssetDetailForm.getRequisitionService();
                List<Officeassetrequisitionservice> allOfficeAssetService = context.getOfficeAssetRequisitionServiceService().getAllOfficeAssetRequisitionService();
                
                for (int i = 0; i < allOfficeAssetService.size(); i++) {
                    
                    if (allOfficeAssetService.get(i).getRequestedItems().getRequestedId().equals(requests.getRequestedId())) {
                        context.getOfficeAssetRequisitionServiceService().deleteOfficeAssetRequisitionService(allOfficeAssetService.get(i));
                        requests.getItems().setTotalQuantity(requests.getItems().getTotalQuantity() + 1);
                        context.getItemService().saveOrUpdateItem(requests.getItems());
                    }
                }
                if (officeAssetDetail != null && officeAssetDetail.size() > 0) {
                    for (Officeassetrequisitionservice service : officeAssetDetail) {
                        if (!service.getOfficeAssetCode().isEmpty() && !service.getSerialNumber().isEmpty()) {
                            service.setRequestedItems(requests);
                            context.getOfficeAssetRequisitionServiceService().saveOrUpdateOfficeAssetRequisitionService(service);
                            requests.getItems().setTotalQuantity(requests.getItems().getTotalQuantity() - 1);
                            context.getItemService().saveOrUpdateItem(requests.getItems());
                            servedQty++;
                        }
                        OmerspiUtils.setInfoMessage(session, "Office asset details added!");

                        //populate some of the served office asset requisition attributes
                        Calendar now = Calendar.getInstance();
                        requests.getOfficeassetrequisition().setServiceNumber("OFF" + requests.getOfficeassetrequisition().getOfficeAssetRequisitionId() + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR));
                        requests.getOfficeassetrequisition().setStatus("SERVED");
                        requests.getOfficeassetrequisition().setOfficeRequisitionSeen(Boolean.FALSE);
                        requests.getOfficeassetrequisition().setServiceDate(new Date());
                        
                        context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(requests.getOfficeassetrequisition());
                        
                        requests.setServiceDate(new Date());
                        requests.setServedQty(servedQty);
                        context.getRequestedItemService().saveOrUpdateRequestedItem(requests);
                        
                        try {
                            //send email to the requester of served requisition.
                            String subject = "Hello, " + requests.getOfficeassetrequisition().getEmployee().getFirstName() + "!\nYour Office Asset Requisition has Served. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mail.sendMail(Constants.omerspiEmail, requests.getOfficeassetrequisition().getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }
                        
                    }
                }
                
                return searchRequestedItemByOfficeAssetRequisitionToServe(session, model, requests.getOfficeassetrequisition());
            } catch (Exception ex) {
                ex.printStackTrace();
                OmerspiUtils.setErrorMessage(session, "Error occurred. Try again.");
                return "redirect:/officeAsset/Accepted/DAF/list";
            }
            
        } else {
            OmerspiUtils.setErrorMessage(session, "Error occured,Login and try again or Contact system Administrator for help.");
            return "redirect:/login.htm";
        }
        
    }
}
