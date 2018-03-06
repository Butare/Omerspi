/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.Requisitionresponses;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Jimmy
 */
@Controller
public class RequisitionResponseController {

    @Autowired
    private ServiceContext context;
    // @Autowired
    private MailMail mailMail = new MailMail();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

    }

    // CAR REQUISITION RESPONSES
    @RequestMapping("car/carRequisitionResponse/hodResponseForm.htm")
    public String requisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);

        model.put("carRequisition", carRequisition);

        String responseForm = "";
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);
        Requisitionresponses carResponse = new Requisitionresponses();
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {

            carResponse.setCarrequisition(carRequisition);
            carResponse.setHodResponseDate(new Date());
            model.put("response", carResponse);

            responseForm = "response/hodResponseForm";
        }
        return responseForm;



    }

    @RequestMapping("car/carRequisitionResponse/dafResponseForm.htm")
    public String dafRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);

        model.put("carRequisition", carRequisition);

        String responseForm = "";
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);
        Requisitionresponses requisitionResponse = new Requisitionresponses();

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
            System.out.println("welcome DAF");

            requisitionResponse.setCarrequisition(carRequisition);
            requisitionResponse.setDafResponseDate(new Date());
            model.put("response", requisitionResponse);

            responseForm = "response/dafResponseForm";

        }
        return responseForm;

    }

    @RequestMapping("car/carRequisitionResponse/HodAccepted/dafResponseForm.htm")
    public String HodAcceptedDafRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("requisitionResponseId") Requisitionresponses requisitionResponse) {


        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";

        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);

        model.put("carRequisition", requisitionResponse.getCarrequisition());

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            model.put("response", requisitionResponse);

            responseForm = "response/dafResponseForm";

        }
        return responseForm;

    }

    @RequestMapping("car/carRequisitionResponse/logisticCommentForm.htm")
    public String logisticCarCommentForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("requisitionResponseId") Requisitionresponses requisitionResponse) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);

        model.put("carRequisition", requisitionResponse.getCarrequisition());

        String responseForm = "";

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            System.out.println("welcome Logistic");

            model.put("response", requisitionResponse);

            responseForm = "response/logisticCommentForm";

        }
        return responseForm;

    }

    @RequestMapping(value = "/car/carRequisition/searchResponse", method = RequestMethod.GET)
    public String searchResponse(HttpSession session, Map<String, Object> model, @RequestParam("requisitionId") Carrequisition carRequisition) {
        //carRequisition response;      
        List<Requisitionresponses> responseByRequisition = context.getRequisitionResponseService().getResponseByCarRequisitionId(carRequisition);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && carRequisition.getCarRequisitionStatus().equals("LOGISTICS COMMENTED")) {
            carRequisition.setSeen(Boolean.TRUE);
            context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && carRequisition.getCarRequisitionStatus().equals("REJECTED BY HOD")) {
            carRequisition.setSeen(Boolean.TRUE);
            context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && carRequisition.getCarRequisitionStatus().equals("REJECTED BY DAF")) {
            carRequisition.setSeen(Boolean.TRUE);
            context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);
        }

        model.put("responseByRequisitionList", responseByRequisition);
        model.put("carRequisition", carRequisition);
        return "car/carRequisitionResponseList";
    }

    @RequestMapping("car/carRequisitionResponse/saveOrUpdateRequsitionResponse.htm")
    public String saveOrUpdateRequsitionResponse(HttpServletRequest request, Map<String, Object> model,
            @ModelAttribute("response") Requisitionresponses requisitionResponse) {
        HttpSession session = request.getSession(true);

        User a = Context.getCurrentUser(session);
        String error = null;

        try {

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {

                requisitionResponse.setEmployeeByHodEmployeeRegistrationId(a.getEmployee());
                requisitionResponse.setHodResponseDate(new Date());
                System.out.println("welcome HOD");
                if (requisitionResponse.getHodResponse() != null) {
                    if (requisitionResponse.getHodResponse().equals("APPROVED")) {
                        requisitionResponse.getCarrequisition().setCarRequisitionStatus("APPROVED BY HOD");
                        requisitionResponse.getCarrequisition().setSeen(Boolean.FALSE);
                        context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);

                        //SEND EMAIL TO DAF
                        //new OmerspiNotification().sendCarDafEmail();

                        List<User> daf = context.getUserService().getAllUser();
                        for (int i = 0; i < daf.size(); i++) {
                            if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                                try {
                                    String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                    mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
                                } catch (Exception ex) {
                                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                                }
                            }
                        }

                    } else {
                        requisitionResponse.getCarrequisition().setCarRequisitionStatus("REJECTED BY HOD");
                        requisitionResponse.getCarrequisition().setSeen(Boolean.FALSE);
                        context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                        try {
                            String subject = "Hello, " + requisitionResponse.getCarrequisition().getEmployee().getFirstName() + "!\nYour Car Requisition has Rejected. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getCarrequisition().getEmployee().getWorkEmail(), "Car Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent");
                        }
                    }

                }
            }

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

                requisitionResponse.setEmployeeByDafEmployeeRegistrationId(a.getEmployee());
                requisitionResponse.setDafResponseDate(new Date());

                if (requisitionResponse.getDafResponse() != null) {
                    if (requisitionResponse.getDafResponse().equals("APPROVED")) {
                        requisitionResponse.getCarrequisition().setCarRequisitionStatus("APPROVED BY DAF");
                        requisitionResponse.getCarrequisition().setSeen(Boolean.FALSE);
                        context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);

                        //SEND EMAIL TO LOGISTICS
                        List<User> logistics = context.getUserService().getAllUser();
                        for (int i = 0; i < logistics.size(); i++) {
                            if (logistics.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
                                try {
                                    String subject = "Hello, " + logistics.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                    mailMail.sendMail(Constants.omerspiEmail, logistics.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
                                } catch (Exception ex) {
                                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                                }
                            }
                        }


                    } else {
                        requisitionResponse.getCarrequisition().setCarRequisitionStatus("REJECTED BY DAF");
                        requisitionResponse.getCarrequisition().setSeen(Boolean.FALSE);
                        context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                        try {
                            String subject = "Hello, " + requisitionResponse.getCarrequisition().getEmployee().getFirstName() + "!\nYour Car Requisition has Rejected. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getCarrequisition().getEmployee().getWorkEmail(), "Car Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }
                    }

                }
                return "redirect:/car/carRequisition/allDafPendingList.dash";
            }

            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {

                try {
                    requisitionResponse.setEmployeeByLogisticEmployeeRegistrationId(a.getEmployee());

                    if (!requisitionResponse.getLogisticComment().isEmpty()) {
                        requisitionResponse.getCarrequisition().setCarRequisitionStatus("LOGISTICS COMMENTED");
                        requisitionResponse.getCarrequisition().setSeen(Boolean.FALSE);
                        context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                        try {
                            String subject = "Hello, " + requisitionResponse.getCarrequisition().getEmployee().getFirstName() + "!\nYour Car Requisition has Commented. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                            mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getCarrequisition().getEmployee().getWorkEmail(), "Car Requisition", subject);
                        } catch (Exception ex) {
                            OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                        }

                        return "redirect:/car/carRequisition/notServed/list";

                    } else {

                        OmerspiUtils.setErrorMessage(session, "Comment is required please.!");
                        return logisticCarCommentForm(request, model, requisitionResponse);
                    }
                } catch (Exception ex) {
                    OmerspiUtils.setErrorMessage(session, "Duplicate Record.");
                }
            }
        } catch (Exception ex) {
            error = "Duplicate record. Try again";
            OmerspiUtils.setErrorMessage(session, error);
        }

        return "redirect:/car/carRequisition/notServed/ByDepartment/list";

    }

    // OFFICE ASSET REQUISITION RESPONSES
    @RequestMapping("officeAssetRequisitionResponse/hodResponseForm.htm")
    public String officeAssetRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("officeAssetRequisitionId") Officeassetrequisition officeAssetRequisition) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);
        model.put("officeAssetRequisition", officeAssetRequisition);
        Requisitionresponses officeAssetRequisitionResponse = new Requisitionresponses();
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
            officeAssetRequisitionResponse.setOfficeassetrequisition(officeAssetRequisition);
            officeAssetRequisitionResponse.setHodResponseDate(new Date());
            model.put("response", officeAssetRequisitionResponse);

            responseForm = "response/hodResponseForm";
        }
        return responseForm;
    }

    @RequestMapping("officeAssetRequisitionResponse/dafResponseForm.htm")
    public String dafOfficeAssetRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("officeAssetRequisitionId") Officeassetrequisition officeAssetRequisition) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("", "--Select--");
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);
        model.put("officeAssetRequisition", officeAssetRequisition);
        Requisitionresponses requisitionResponse = new Requisitionresponses();

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            requisitionResponse.setOfficeassetrequisition(officeAssetRequisition);
            requisitionResponse.setDafResponseDate(new Date());
            model.put("response", requisitionResponse);

            responseForm = "response/dafResponseForm";

        }
        return responseForm;
    }

    @RequestMapping("officeAssetRequisitionResponse/saveOrUpdateRequsitionResponse.htm")
    public String saveOrUpdateOfficeAssetRequisitionResponse(HttpServletRequest request,
            @ModelAttribute("response") Requisitionresponses requisitionResponse) {
        HttpSession session = request.getSession(true);

        User a = Context.getCurrentUser(session);
        String form = "";

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {

            requisitionResponse.setEmployeeByHodEmployeeRegistrationId(a.getEmployee());
            requisitionResponse.setHodResponseDate(new Date());
            System.out.println("welcome HOD");
            if (requisitionResponse.getHodResponse() != null) {
                if (requisitionResponse.getHodResponse().equals("APPROVED")) {
                    requisitionResponse.getOfficeassetrequisition().setStatus("APPROVED BY HOD");
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);

                    //SEND EMAIL TO DAF
                    //new OmerspiNotification().sendStationaryDafEmail();

                    List<User> daf = context.getUserService().getAllUser();
                    for (int i = 0; i < daf.size(); i++) {
                        if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                            try {
                                String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Office Asset Requisition. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                            } catch (Exception ex) {
                                OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                            }
                        }
                    }


                } else {
                    requisitionResponse.getOfficeassetrequisition().setStatus("REJECTED BY HOD");
                    requisitionResponse.getOfficeassetrequisition().setOfficeRequisitionSeen(Boolean.FALSE);
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                    try {
                        String subject = "Hello, " + requisitionResponse.getOfficeassetrequisition().getEmployee().getFirstName() + "!\nYour Stationary Requisition has Rejected. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                        mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getOfficeassetrequisition().getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                    } catch (Exception ex) {
                        OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");

                    }
                }
            }
            form = "redirect:/officeAsset/ByDepartment/list";
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            requisitionResponse.setEmployeeByDafEmployeeRegistrationId(a.getEmployee());
            requisitionResponse.setDafResponseDate(new Date());
            // System.out.println("Hod ID is: " + requisitionResponse.getEmployeeByHodEmployeeRegistrationId().getEmployeeRegistrationId());

            System.out.println("welcome DAF");
            if (requisitionResponse.getDafResponse() != null) {
                if (requisitionResponse.getDafResponse().equals("APPROVED")) {
                    requisitionResponse.getOfficeassetrequisition().setStatus("APPROVED BY DAF");
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);

                    //SEND EMAIL TO LOGISTICS
                    List<User> logistics = context.getUserService().getAllUser();
                    for (int i = 0; i < logistics.size(); i++) {
                        if (logistics.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
                            try {
                                String subject = "Hello, " + logistics.get(i).getEmployee().getFirstName() + "!\nYou have New Office Asset Requisition. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                mailMail.sendMail(Constants.omerspiEmail, logistics.get(i).getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                            } catch (Exception ex) {
                                OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                            }
                        }
                    }



                } else {
                    requisitionResponse.getOfficeassetrequisition().setStatus("REJECTED BY DAF");
                    requisitionResponse.getOfficeassetrequisition().setOfficeRequisitionSeen(Boolean.FALSE);
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                    try {
                        String subject = "Hello, " + requisitionResponse.getOfficeassetrequisition().getEmployee().getFirstName() + "!\nYour Office Asset Requisition was Rejected. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                        mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getOfficeassetrequisition().getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                    } catch (Exception ex) {
                        OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                    }
                }

            }
            form = "redirect:/officeAsset/accepted/AllDepartments/list";
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {

            requisitionResponse.setEmployeeByLogisticEmployeeRegistrationId(a.getEmployee());

            if (requisitionResponse.getLogisticComment() != null) {

                requisitionResponse.getOfficeassetrequisition().setStatus("LOGISTICS COMMENTED");
                requisitionResponse.getOfficeassetrequisition().setOfficeRequisitionSeen(Boolean.FALSE);
                context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                try {
                    String subject = "Hello, " + requisitionResponse.getOfficeassetrequisition().getEmployee().getFirstName() + "!\nYour Office Asset Requisition was Commented. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                    mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getOfficeassetrequisition().getEmployee().getWorkEmail(), "Office Asset Requisition", subject);
                } catch (Exception ex) {
                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                }
                form = "redirect:/officeAsset/Accepted/DAF/list";

            } else {
                OmerspiUtils.setErrorMessage(session, "Comment please!");
                return saveOrUpdateOfficeAssetRequisitionResponse(request, requisitionResponse);
            }
        }

        return form;
    }

    @RequestMapping(value = "/officeAssetRequisition/searchResponse", method = RequestMethod.GET)
    public String searchOfficeAssetRequisitionResponse(HttpSession session, Map<String, Object> model, @RequestParam("requisitionId") Officeassetrequisition officeAssetRequisition) {
        //carRequisition, stationaryRequisition, notFoundItem);
        List<Requisitionresponses> responseByRequisition = context.getRequisitionResponseService().getResponseByOfficeAssetRequisitionId(officeAssetRequisition);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && officeAssetRequisition.getStatus().equals("LOGISTICS COMMENTED")) {
            officeAssetRequisition.setOfficeRequisitionSeen(Boolean.TRUE);
            context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && officeAssetRequisition.getStatus().equals("REJECTED BY HOD")) {
            officeAssetRequisition.setOfficeRequisitionSeen(Boolean.TRUE);
            context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
        }
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && officeAssetRequisition.getStatus().equals("REJECTED BY DAF")) {
            officeAssetRequisition.setOfficeRequisitionSeen(Boolean.TRUE);
            context.getOfficeAssetRequisitionService().saveOrUpdateOfficeAssetRequisition(officeAssetRequisition);
        }


        model.put("responseByRequisitionList", responseByRequisition);
        model.put("officeAssetRequisition", officeAssetRequisition);
        return "officeasset/officeAssetRequisitionResponseList";
    }

    @RequestMapping("officeAssetRequisitionResponse/HodAccepted/dafResponseForm.htm")
    public String HodAcceptedDafOfficeAssetRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("requisitionResponseId") Requisitionresponses requisitionResponse) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";

        model.put("officeAssetRequisition", requisitionResponse.getOfficeassetrequisition());
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("", "--Select--");
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            model.put("response", requisitionResponse);

            responseForm = "response/dafResponseForm";

        }
        return responseForm;

    }

    @RequestMapping("officeAssetRequisitionResponse/logisticCommentForm.htm")
    public String officeAssetRequisitionLogisticCommentForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("requisitionResponseId") Requisitionresponses requisitionResponse) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);

        model.put("officeAssetRequisition", requisitionResponse.getOfficeassetrequisition());
        System.out.println("welcome LOGISTIC");
        String responseForm = "";

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            System.out.println("welcome Logistic");

            model.put("response", requisitionResponse);

            responseForm = "response/logisticCommentForm";

        }
        return responseForm;

    }

// STATIONARY REQUISITION RESPONSES
    @RequestMapping("stationaryRequisitionResponse/hodResponseForm.htm")
    public String stationaryRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);
        model.put("stationaryRequisition", stationaryRequisition);
        Requisitionresponses stationaryRequisitionResponse = new Requisitionresponses();
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
            stationaryRequisitionResponse.setStationaryrequisition(stationaryRequisition);
            stationaryRequisitionResponse.setHodResponseDate(new Date());
            model.put("response", stationaryRequisitionResponse);

            responseForm = "response/hodResponseForm";
        }
        return responseForm;



    }

    @RequestMapping("stationaryRequisitionResponse/dafResponseForm.htm")
    public String dafStationaryRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);
        model.put("stationaryRequisition", stationaryRequisition);
        Requisitionresponses requisitionResponse = new Requisitionresponses();

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            requisitionResponse.setStationaryrequisition(stationaryRequisition);
            requisitionResponse.setDafResponseDate(new Date());
            model.put("response", requisitionResponse);

            responseForm = "response/dafResponseForm";

        }
        return responseForm;

    }

    @RequestMapping("stationaryRequisitionResponse/HodAccepted/dafResponseForm.htm")
    public String HodAcceptedDafStationaryRequisitionResponseForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("requisitionResponseId") Requisitionresponses requisitionResponse) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);
        String responseForm = "";

        model.put("stationaryRequisition", requisitionResponse.getStationaryrequisition());
        Map<String, String> response = new LinkedHashMap<String, String>();
        response.put("APPROVED", "APPROVE");
        response.put("REJECTED", "REJECT");
        model.put("responseList", response);


        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            model.put("response", requisitionResponse);

            responseForm = "response/dafResponseForm";

        }
        return responseForm;

    }

    @RequestMapping(value = "/stationaryRequisition/searchResponse", method = RequestMethod.GET)
    public String searchStationaryRequisitionResponse(HttpSession session, Map<String, Object> model, @RequestParam("requisitionId") Stationaryrequisition stationaryRequisition) {
        //carRequisition, stationaryRequisition, notFoundItem);
        List<Requisitionresponses> responseByRequisition = context.getRequisitionResponseService().getResponseByStationaryRequisitionId(stationaryRequisition);

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && stationaryRequisition.getStatus().equals("LOGISTICS COMMENTED")) {
            stationaryRequisition.setStationarySeen(Boolean.TRUE);
            context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && stationaryRequisition.getStatus().equals("REJECTED BY HOD")) {
            stationaryRequisition.setStationarySeen(Boolean.TRUE);
            context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
        }
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && stationaryRequisition.getStatus().equals("REJECTED BY DAF")) {
            stationaryRequisition.setStationarySeen(Boolean.TRUE);
            context.getStationaryRequisitionService().saveOrUpdateStationaryRequisition(stationaryRequisition);
        }


        model.put("responseByRequisitionList", responseByRequisition);
        model.put("stationaryRequisition", stationaryRequisition);
        return "stationary/stationaryRequisitionResponseList";
    }

    @RequestMapping("stationaryRequisitionResponse/saveOrUpdateRequsitionResponse.htm")
    public String saveOrUpdateStationaryRequisitionResponse(HttpServletRequest request,
            @ModelAttribute("response") Requisitionresponses requisitionResponse) {
        HttpSession session = request.getSession(true);

        User a = Context.getCurrentUser(session);
        String form = "";

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {

            requisitionResponse.setEmployeeByHodEmployeeRegistrationId(a.getEmployee());
            requisitionResponse.setHodResponseDate(new Date());
            System.out.println("welcome HOD");
            if (requisitionResponse.getHodResponse() != null) {
                if (requisitionResponse.getHodResponse().equals("APPROVED")) {
                    requisitionResponse.getStationaryrequisition().setStatus("APPROVED BY HOD");
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);

                    //SEND EMAIL TO DAF
                    //new OmerspiNotification().sendStationaryDafEmail();

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


                } else {
                    requisitionResponse.getStationaryrequisition().setStatus("REJECTED BY HOD");
                    requisitionResponse.getStationaryrequisition().setStationarySeen(Boolean.FALSE);
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                    try {
                        String subject = "Hello, " + requisitionResponse.getStationaryrequisition().getEmployee().getFirstName() + "!\nYour Stationary Requisition has Rejected. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                        mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getStationaryrequisition().getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                    } catch (Exception ex) {
                        OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");

                    }
                }
            }
            form = "redirect:/stationaryRequisition/ByDepartment/list";
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {

            requisitionResponse.setEmployeeByDafEmployeeRegistrationId(a.getEmployee());
            requisitionResponse.setDafResponseDate(new Date());
            // System.out.println("Hod ID is: " + requisitionResponse.getEmployeeByHodEmployeeRegistrationId().getEmployeeRegistrationId());

            System.out.println("welcome DAF");
            if (requisitionResponse.getDafResponse() != null) {
                if (requisitionResponse.getDafResponse().equals("APPROVED")) {
                    requisitionResponse.getStationaryrequisition().setStatus("APPROVED BY DAF");
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);

                    //SEND EMAIL TO LOGISTICS
                    List<User> logistics = context.getUserService().getAllUser();
                    for (int i = 0; i < logistics.size(); i++) {
                        if (logistics.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
                            try {
                                String subject = "Hello, " + logistics.get(i).getEmployee().getFirstName() + "!\nYou have New Stationary Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                mailMail.sendMail(Constants.omerspiEmail, logistics.get(i).getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                            } catch (Exception ex) {
                                OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                            }
                        }
                    }



                } else {
                    requisitionResponse.getStationaryrequisition().setStatus("REJECTED BY DAF");
                    requisitionResponse.getStationaryrequisition().setStationarySeen(Boolean.FALSE);
                    context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                    try {
                        String subject = "Hello, " + requisitionResponse.getStationaryrequisition().getEmployee().getFirstName() + "!\nYour Stationary Requisition has Rejected. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                        mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getStationaryrequisition().getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                    } catch (Exception ex) {
                        OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                    }
                }

            }
            form = "redirect:/stationaryRequisition/accepted/AllDepartments/list";
        }

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {

            requisitionResponse.setEmployeeByLogisticEmployeeRegistrationId(a.getEmployee());

            if (requisitionResponse.getLogisticComment() != null) {

                requisitionResponse.getStationaryrequisition().setStatus("LOGISTICS COMMENTED");
                requisitionResponse.getStationaryrequisition().setStationarySeen(Boolean.FALSE);
                context.getRequisitionResponseService().saveOrUpdateRequisitionResponse(requisitionResponse);
                try {
                    String subject = "Hello, " + requisitionResponse.getStationaryrequisition().getEmployee().getFirstName() + "!\nYour Stationary Requisition has Commented. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                    mailMail.sendMail(Constants.omerspiEmail, requisitionResponse.getStationaryrequisition().getEmployee().getWorkEmail(), "Stationary Requisition", subject);
                } catch (Exception ex) {
                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                }
                form = "redirect:/car/carRequisition/notServed/list";

            } else {
                OmerspiUtils.setErrorMessage(session, "Comment please!");
                return saveOrUpdateStationaryRequisitionResponse(request, requisitionResponse);
            }
        }

        return form;
    }

    @RequestMapping("stationaryRequisitionResponse/logisticCommentForm.htm")
    public String stationaryRequisitionLogisticCommentForm(HttpServletRequest request, Map<String, Object> model, @RequestParam("requisitionResponseId") Requisitionresponses requisitionResponse) {

        HttpSession session = request.getSession(true);
        User a = Context.getCurrentUser(session);

        model.put("stationaryRequisition", requisitionResponse.getStationaryrequisition());
        System.out.println("welcome LOGISTIC");
        String responseForm = "";

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            System.out.println("welcome Logistic");

            model.put("response", requisitionResponse);

            responseForm = "response/logisticCommentForm";

        }
        return responseForm;

    }
}
