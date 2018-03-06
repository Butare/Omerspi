
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import com.csvreader.CsvWriter;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.ServiceContext;
import jim.omerspi.SessionCounterListener;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.User;
import jim.omerspi.model.Cartype;
import jim.omerspi.model.Itenerary;
import jim.omerspi.model.Memo;
import jim.omerspi.validator.CarRequisitionValidator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

/**
 *
 * @author JOHN
 */
@Controller
@RequestMapping("/car/carRequisition")
public class CarRequisitionController {

    @Autowired
    private ServiceContext context;
    @Autowired
    private CarRequisitionValidator carRequisitionValidator;
    private MailMail mailMail = new MailMail();
    int edit = 0;

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String carRequisitionForm(HttpServletRequest request, Model model, @ModelAttribute("carRequisition") Carrequisition carRequisition) throws IOException {

        edit = 0;

        if (Context.getCurrentUser(request.getSession()) == null) {
            return "redirect:/login.htm";
        }


        List<Itenerary> iteneraryList = context.getIteneraryService().getAllItenerary();
        List<Cartype> carTypes = context.getCartypeService().getAllCartype();

        Map<Integer, Integer> dailyCosts = new HashMap<Integer, Integer>();
        Map<Integer, Integer> hourlyCosts = new HashMap<Integer, Integer>();
        Map<Integer, Integer> iteneraryCosts = new HashMap<Integer, Integer>();

        for (Cartype carType : carTypes) {
            dailyCosts.put(carType.getCarTypeId(), carType.getDailyCost());
            hourlyCosts.put(carType.getCarTypeId(), carType.getHourlyCost());
        }
        for (Itenerary itenerary : iteneraryList) {
            iteneraryCosts.put(itenerary.getIteneraryId(), itenerary.getCost());
        }
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        //hourlyCost Map
        mapper.writeValue(sw, hourlyCosts);
        String vechicleHourlyCostsJson = sw.toString();
        //dailyCost Map
        sw = new StringWriter();
        mapper.writeValue(sw, dailyCosts);
        String vechicleDailyCostsJson = sw.toString();

        //iteneraryCost Map
        sw = new StringWriter();
        mapper.writeValue(sw, iteneraryCosts);
        String iteneraryCostsJson = sw.toString();

        //add objects to object model

        model.addAttribute("vechicleDailyCostsJson", vechicleDailyCostsJson);
        model.addAttribute("vechicleHourlyCostsJson", vechicleHourlyCostsJson);
        model.addAttribute("iteneraryCostsJson", iteneraryCostsJson);

        model.addAttribute("carRequisition", carRequisition);
        model.addAttribute("carTypeList", carTypes);
        model.addAttribute("iteneraryList", iteneraryList);
        return "car/carRequisitionForm";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String saveOrUpdateCarRequisition(Model model, HttpServletRequest request, @ModelAttribute("carRequisition") Carrequisition carRequisition, BindingResult result) throws IOException {


        HttpSession session = request.getSession(true);
        String error = null;

        try {

            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_PROFESSIONAL)) {

                carRequisitionValidator.validate(carRequisition, result);
                if (result.hasErrors()) {
                    return carRequisitionForm(request, model, carRequisition);
                }
                User a = Context.getCurrentUser(session);

                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(carRequisition.getDepartureTime().toString());
                    d2 = sdf.parse(carRequisition.getExpectedTimeReturn().toString());
                    long diff = d2.getTime() - d1.getTime();
                    if (diff > 0) {

                        carRequisition.setEmployee(a.getEmployee());
                        carRequisition.setRequestedOn(new Date());
                        System.out.println("Departure Time is :" + carRequisition.getDepartureTime());

                        if (carRequisition.getCostingBasis().equals("hourlyDaily")) {

                            long diffMinutes = diff / (1000 * 60) % 60;
                            long diffHours = diff / (1000 * 60 * 60) % 24;
                            long diffDays = diff / (1000 * 60 * 60 * 24);

                            if (diffDays != 0) {
                                carRequisition.setTotalAmount(carRequisition.getCartype().getDailyCost() * (int) diffDays);

                            } else if (diffHours != 0) {
                                if (diffHours >= 12) {
                                    carRequisition.setTotalAmount((carRequisition.getCartype().getDailyCost() / 2)
                                            + (carRequisition.getCartype().getHourlyCost() * (((int) diffHours) % 12)));

                                } else if (diffHours < 12) {
                                    carRequisition.setTotalAmount(carRequisition.getCartype().getHourlyCost() * (int) diffHours);

                                }
                            }


                            System.out.println("Days :" + diffDays + " Hours :" + diffHours + " Minutes :" + diffMinutes);


                        }

                        if (carRequisition.getCostingBasis().equals("itenerary")) {



                            int amt = 0;
                            for (Itenerary ite : carRequisition.getIteneraries()) {
                                amt += ite.getCost();
                                carRequisition.getIteneraries().add(ite);
                            }

                            carRequisition.setTotalAmount(amt);

                        }
                    }
                    if (diff <= 0 && carRequisition.getCarRequisitionId() == null) {
                        OmerspiUtils.setErrorMessage(session, "Return time can't be less or equal Departure Time");
                        return carRequisitionForm(request, model, carRequisition);
                    }
                    if (diff <= 0 && carRequisition.getCarRequisitionId() != null) {
                        OmerspiUtils.setErrorMessage(session, "Return time can't be less or equal Departure Time");
                        return editCarRequisition(model, carRequisition);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                carRequisition.setCarRequisitionStatus("SENT BY PROFESSIONAL");
                carRequisition.setSeen(Boolean.FALSE);

                if (carRequisition.getDestinationType().equals("Countryside")) {
                    try {

                        context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);
                        if (edit == 0) {

                            return "redirect:/car/carRequisition/memoForm.htm?carRequisitionId=" + carRequisition.getCarRequisitionId();
                        }
                        if (edit == 1) {

                            for (Memo memo : carRequisition.getMemos()) {

                                return "redirect:memo/edit?memoId=" + memo.getMemoId();
                            }
                        }

                        //SEND EMAIL TO HOD for countryside car requisition
                        List<User> hod = context.getStationaryRequisitionService().getHoD(a.getEmployee().getDepartment());
                        for (int i = 0; i < hod.size(); i++) {
                            if (hod.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                                try {
                                    String subject = "Hello, " + hod.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                    mailMail.sendMail(Constants.omerspiEmail, hod.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
                                } catch (Exception ex) {
                                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent !!");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        error = "Car Requisition not sent, Try again";
                        OmerspiUtils.setErrorMessage(session, error);
                    }
                }

                if (carRequisition.getDestinationType().equals("Kigali")) {
                    try {
                        context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);

                        // Professional SEND EMAIL TO HOD for kigali's car requisition.
                        List<User> hod = context.getStationaryRequisitionService().getHoD(a.getEmployee().getDepartment());
                        for (int i = 0; i < hod.size(); i++) {
                            if (hod.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
                                try {
                                    String subject = "Hello, " + hod.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                    mailMail.sendMail(Constants.omerspiEmail, hod.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
                                } catch (Exception ex) {
                                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        error = "Car Requisition not sent, Try again";
                        OmerspiUtils.setErrorMessage(session, error);
                    }
                }
                if (error == null) {
                    OmerspiUtils.setInfoMessage(request.getSession(), "Car Requisition Submitted. ");
                }
                return "redirect:list";


            }
            if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_HOD)) {
                User a = Context.getCurrentUser(session);
                carRequisition.setEmployee(a.getEmployee());
                carRequisition.setRequestedOn(new Date());

                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date d1 = null;
                Date d2 = null;
                try {
                    d1 = sdf.parse(carRequisition.getDepartureTime().toString());
                    d2 = sdf.parse(carRequisition.getExpectedTimeReturn().toString());

                    long diff = d2.getTime() - d1.getTime();

                    if (diff > 0) {
                        if (carRequisition.getCostingBasis().equals("hourlyDaily")) {

                            long diffMinutes = diff / (1000 * 60) % 60;
                            long diffHours = diff / (1000 * 60 * 60) % 24;
                            long diffDays = diff / (1000 * 60 * 60 * 24);
                            System.out.println("Minutes :" + diffMinutes);

                            if (diffDays != 0) {
                                carRequisition.setTotalAmount(carRequisition.getCartype().getDailyCost() * (int) diffDays);

                            } else if (diffHours != 0) {
                                if (diffHours >= 12) {
                                    carRequisition.setTotalAmount((carRequisition.getCartype().getDailyCost() / 2)
                                            + (carRequisition.getCartype().getHourlyCost() * (((int) diffHours) % 12)));

                                } else if (diffHours < 12) {
                                    carRequisition.setTotalAmount(carRequisition.getCartype().getHourlyCost() * (int) diffHours);

                                }
                            }

                        }

                        if (carRequisition.getCostingBasis().equals("itenerary")) {


                            int amt = 0;
                            for (Itenerary ite : carRequisition.getIteneraries()) {
                                amt += ite.getCost();
                                carRequisition.getIteneraries().add(ite);
                            }

                            carRequisition.setTotalAmount(amt);

                        }

                    }

                    if (diff <= 0 && carRequisition.getCarRequisitionId() == null) {
                        OmerspiUtils.setErrorMessage(session, "Return time can't be same as Departure Time");
                        return carRequisitionForm(request, model, carRequisition);
                    }

                    if (diff <= 0 && carRequisition.getCarRequisitionId() != null) {
                        OmerspiUtils.setErrorMessage(session, "Return time can't be same as Departure Time");
                        return editCarRequisition(model, carRequisition);
                    }


                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                carRequisition.setCarRequisitionStatus("SENT BY HOD");
                carRequisition.setSeen(Boolean.FALSE);

                if (carRequisition.getDestinationType().equals("Countryside")) {
                    try {
                        context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);
                        if (edit == 0) {

                            return "redirect:/car/carRequisition/memoForm.htm?carRequisitionId=" + carRequisition.getCarRequisitionId();
                        }
                        if (edit == 1) {

                            for (Memo memo : carRequisition.getMemos()) {

                                return "redirect:memo/edit?memoId=" + memo.getMemoId();
                            }
                        }

                        //hod send email of countryside's car requisition to daf
                        List<User> daf = context.getUserService().getAllUser();
                        for (int i = 0; i < daf.size(); i++) {
                            if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                                try {
                                    String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                    mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
                                } catch (Exception ex) {
                                    OmerspiUtils.setErrorMessage(session, " Connection Error, Message not sent");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        error = "Car Requisition not sent, Try again.";
                        OmerspiUtils.setErrorMessage(session, error);
                    }
                }
                if (carRequisition.getDestinationType().equals("Kigali")) {

                    try {
                        context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);

                        //hod send email of kigali's car requisition to daf
                        List<User> daf = context.getUserService().getAllUser();
                        for (int i = 0; i < daf.size(); i++) {
                            if (daf.get(i).hasPrivilege(PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
                                try {
                                    String subject = "Hello, " + daf.get(i).getEmployee().getFirstName() + "!\nYou have New Car Requisition \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                                    mailMail.sendMail(Constants.omerspiEmail, daf.get(i).getEmployee().getWorkEmail(), "Car Requisition", subject);
                                } catch (Exception ex) {
                                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        error = "Car Requisition not sent, Try again.";
                        OmerspiUtils.setErrorMessage(session, error);
                    }
                }
                if (error == null) {
                    OmerspiUtils.setInfoMessage(request.getSession(), "Car Requisition Submitted. ");
                }
                return "redirect:list";


            } else {
                OmerspiUtils.setErrorMessage(request.getSession(), "Error occured, Login and try again or contact system administrator ");
                return "redirect:/login.htm";
            }


            //carRequisition.setSender(a);

        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Form was Empty, Try again");
            return "redirect:form";
        }
    }

    @RequestMapping("/list")
    public String carRequisitionList(Map<String, Object> model, HttpSession session) {
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_PROFESSIONAL) || Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_HOD)) {

            User a = Context.getCurrentUser(session);
            List<Carrequisition> carRequisitionByEmployee = context.getCarRequisitionService().getCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", carRequisitionByEmployee);
            return "car/carRequisitionList";

        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/notServed/list")
    public String carRequisitionListSuper(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            List<Carrequisition> carRequisitionListSuper = context.getCarRequisitionService().getAllNotServedCarRequisition();
            model.put("carRequisitionListSuper", carRequisitionListSuper);
            return "car/carRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/notServed/ByDepartment/list")
    public String notServedCarRequisitionByHoD(HttpServletRequest request, Map<String, Object> model) {

        if (Context.getCurrentUser(request.getSession()) != null) {

            HttpSession session = request.getSession(true);
            User a = Context.getCurrentUser(session);
            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {

                List<Carrequisition> carRequisitionByHoD = context.getCarRequisitionService().getAllCarRequisitionByDepartment(a.getEmployee().getDepartment());

                model.put("carRequisitionListSuper", carRequisitionByHoD);
                return "car/carRequisitionListSuper";

            } else {
                return "redirect:/login.htm";
            }
        } else {

            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/accepted/AllDepartments/list")
    public String pendingCarRequisitionList(Map<String, Object> model) {
        try {
            List<Carrequisition> pendingCarRequisitionList = context.getCarRequisitionService().getAllHodApprovedCarRequisition();
            model.put("carRequisitionListSuper", pendingCarRequisitionList);
            return "car/carRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/requests/HoD/list")
    public String hodSentCarRequisitionList(Map<String, Object> model) {
        try {
            List<Carrequisition> sentHodCarRequisitionList = context.getCarRequisitionService().getAllHodSentCarRequisition();
            model.put("carRequisitionListSuper", sentHodCarRequisitionList);
            return "car/carRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/Accepted/HOD/list")
    public String carRequisitionHODAccepted(HttpSession session, Map<String, Object> model) {

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
            User user = Context.getCurrentUser(session);
            List<Carrequisition> hodAcceptedCarRequisition = context.getCarRequisitionService().getHodPersonalApprovedCarRequisition(user.getEmployee().getDepartment());
            model.put("carRequisitionListSuper", hodAcceptedCarRequisition);
            return "car/carRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/Rejected/HOD/list")
    public String carRequisitionHODRejected(HttpSession session, Map<String, Object> model) {

        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_PROFESSIONAL)) {
            User user = Context.getCurrentUser(session);
            List<Carrequisition> hodRejectedNotFoundItemRequisition = context.getCarRequisitionService().getHodPersonalRejectedCarRequisition(user.getEmployee().getDepartment());
            model.put("carRequisitionListSuper", hodRejectedNotFoundItemRequisition);
            return "car/carRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/Accepted/DAF/list")
    public String carRequisitionDafAccepted(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD) || (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF))) {
            List<Carrequisition> dafAcceptedCarRequisition = context.getCarRequisitionService().getAllDafAcceptedCarRequisition();
            model.put("carRequisitionListSuper", dafAcceptedCarRequisition);
            return "car/carRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/Rejected/DAF/list")
    public String notFoundItemRequisitionDafRejected(HttpSession session, Map<String, Object> model) {
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_HOD)) {
            List<Carrequisition> dafRejectedCarRequisition = context.getCarRequisitionService().getAllDafRejectedCarRequisition();
            model.put("carRequisitionListSuper", dafRejectedCarRequisition);
            return "car/carRequisitionListSuper";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/served/list")
    public String servedCarRequisitionList(Map<String, Object> model) {
        try {
            List<Carrequisition> servedCarRequisitionList = context.getCarRequisitionService().getAllServedCarRequisition();
            model.put("carRequisitionListSuper", servedCarRequisitionList);
            return "car/carRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/commented/list")
    public String commentedCarRequisitionList(Map<String, Object> model) {
        try {
            List<Carrequisition> commentedCarRequisitionList = context.getCarRequisitionService().getAllCommentedCarRequisition();
            model.put("carRequisitionListSuper", commentedCarRequisitionList);
            return "car/carRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/edit")
    public String editCarRequisition(Model model,
            @RequestParam(value = "carRequisitionId", required = false) Carrequisition carRequisition) throws IOException {

        edit = 1;
        List<Itenerary> iteneraryList = context.getIteneraryService().getAllItenerary();
        List<Cartype> carTypes = context.getCartypeService().getAllCartype();

        Map<Integer, Integer> dailyCosts = new HashMap<Integer, Integer>();
        Map<Integer, Integer> hourlyCosts = new HashMap<Integer, Integer>();
        Map<Integer, Integer> iteneraryCosts = new HashMap<Integer, Integer>();

        for (Cartype carType : carTypes) {
            dailyCosts.put(carType.getCarTypeId(), carType.getDailyCost());
            hourlyCosts.put(carType.getCarTypeId(), carType.getHourlyCost());
        }
        for (Itenerary itenerary : iteneraryList) {
            iteneraryCosts.put(itenerary.getIteneraryId(), itenerary.getCost());
        }
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        //hourlyCost Map
        mapper.writeValue(sw, hourlyCosts);
        String vechicleHourlyCostsJson = sw.toString();
        //dailyCost Map
        sw = new StringWriter();
        mapper.writeValue(sw, dailyCosts);
        String vechicleDailyCostsJson = sw.toString();

        //iteneraryCost Map
        sw = new StringWriter();
        mapper.writeValue(sw, iteneraryCosts);
        String iteneraryCostsJson = sw.toString();

        model.addAttribute("vechicleDailyCostsJson", vechicleDailyCostsJson);
        model.addAttribute("vechicleHourlyCostsJson", vechicleHourlyCostsJson);
        model.addAttribute("iteneraryCostsJson", iteneraryCostsJson);


        model.addAttribute("carTypeList", carTypes);
        model.addAttribute("iteneraryList", iteneraryList);
        model.addAttribute("carRequisition", carRequisition);
        return "car/carRequisitionForm";
    }

    @RequestMapping("/delete")
    public String deleteCarRequisition(HttpServletRequest request, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        context.getCarRequisitionService().deleteCarRequisition(carRequisition);
        OmerspiUtils.setInfoMessage(request.getSession(), "Car Requisition Successfully Deleted. ");
        return "redirect:list";

    }

    @RequestMapping("/iteneraryByCarRequisition")
    public String getIteneraryByCarRequisition(Map<String, Object> model,
            @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        model.put("iteneraryListByCarRequisition", carRequisition.getIteneraries());

        return "car/iteneraryListByCarRequisition";
    }

    @RequestMapping("/report.pdf")
    public String carRequisitionReport(Map<String, Object> model, @RequestParam("startDate") Date start, @RequestParam("endDate") Date end, @ModelAttribute("carRequisition") Carrequisition carRequisition) {

        List listCar = context.getCarRequisitionService().getCarRequisitionBetweenDates(start, end);
        model.put("betweenDateCarRequisition", listCar);
        return "car/carRequisitionReport";

    }

    //DashBoard Handler methods
    @RequestMapping("/hodPendingList.dash")
    public String hodPendingList(Map<String, Object> model, HttpSession session) {
        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> pendingCarRequisitionByEmployee = context.getCarRequisitionService().getHodPendingCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", pendingCarRequisitionByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/hodRejectedList.dash")
    public String hodRejectedList(Map<String, Object> model, HttpSession session) {

        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> pendingCarRequisitionByEmployee = context.getCarRequisitionService().getHodRejectedCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", pendingCarRequisitionByEmployee);

            return "car/carRequisitionList";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/profDafPendingList.dash")
    public String dafPendingList(Map<String, Object> model, HttpSession session) {
        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> dafPendingCarRequisitionByEmployee = context.getCarRequisitionService().getProfDafPendingCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", dafPendingCarRequisitionByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/profDafRejectedList.dash")
    public String dafRejectedList(Map<String, Object> model, HttpSession session) {

        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> dafRejectedCarRequisitionByEmployee = context.getCarRequisitionService().getProfDafRejectedCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", dafRejectedCarRequisitionByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/hodDafPendingList.dash")
    public String hodDafPendingList(Map<String, Object> model, HttpSession session) {

        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> dafPendingCarRequisitionByEmployee = context.getCarRequisitionService().getHodDafPendingCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", dafPendingCarRequisitionByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/allDafPendingList.dash")
    public String allDafPendingList(Map<String, Object> model, HttpSession session) {
        try {
            List<Carrequisition> allDafPendingList = context.getCarRequisitionService().getAllDafPendingCarRequisition();
            model.put("carRequisitionListSuper", allDafPendingList);
            return "car/carRequisitionListSuper";
        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/logisticsPendingList.dash")
    public String logisticsPendingList(Map<String, Object> model, HttpSession session) {

        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> logisticsPendingListByEmployee = context.getCarRequisitionService().getDafApprovedCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", logisticsPendingListByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/servedListByEmployee.dash")
    public String servedListByEmployee(Map<String, Object> model, HttpSession session) {
        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> servedListByEmployee = context.getCarRequisitionService().getServedCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", servedListByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping("/commentedListByEmployee.dash")
    public String commentedListByEmployee(Map<String, Object> model, HttpSession session) {

        try {
            User a = Context.getCurrentUser(session);

            List<Carrequisition> commentedListByEmployee = context.getCarRequisitionService().getCommentedCarRequisitionByEmployee(a.getEmployee());
            model.put("carRequisitionList", commentedListByEmployee);
            return "car/carRequisitionList";

        } catch (Exception ex) {
            return "redirect:/login.htm";
        }
    }

    //csv handler
    @RequestMapping(value = "/carRequisitionCsv.html", method = RequestMethod.GET)
    public void getCarRequisitionCsv(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Carrequisition> carRequisitionList = context.getCarRequisitionService().getAllCarRequisition();
        response.setContentType("text/csv,charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=\"carRequisitionList.csv\"");
        OutputStream resOs = response.getOutputStream();
        OutputStream buffOs = new BufferedOutputStream(resOs);
        OutputStreamWriter outputWriter = new OutputStreamWriter(buffOs);

        CsvWriter writer = new CsvWriter(outputWriter, '\u0009');


        for (int i = 1; i < carRequisitionList.size(); i++) {
            Carrequisition aReq = carRequisitionList.get(i);

            writer.write(aReq.getCarRequisitionId() + ",");
            writer.write(aReq.getEmployee().getEmployeeCode() + ",");
            writer.write(aReq.getEmployee().getFirstName() + ",");
            writer.write(aReq.getEmployee().getLastName() + ",");
            writer.write(aReq.getReason() + ",");
            writer.write(aReq.getDestination() + ",");
            writer.write(aReq.getDestinationType() + ",");
            writer.write(aReq.getCostingBasis() + ",");
            writer.write(aReq.getCartype().getTypeName() + ",");
            writer.write(aReq.getTotalAmount() + ",");
            writer.write(aReq.getDepartureTime() + ",");
            writer.write(aReq.getExpectedTimeReturn() + ",");


        }
        outputWriter.flush();
        outputWriter.close();



    }
}
