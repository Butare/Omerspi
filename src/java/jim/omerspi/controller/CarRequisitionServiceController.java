/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Constants;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.mail.MailMail;
import jim.omerspi.model.Carregistration;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Carrequisitionservice;
import jim.omerspi.model.Employee;
import jim.omerspi.model.Vendor;
import jim.omerspi.validator.CarRequisitionServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

/**
 *
 * @author JOHN
 */
@Controller
public class CarRequisitionServiceController {

    @Autowired
    private ServiceContext context;
    @Autowired
    CarRequisitionServiceValidator carRequisitionServiceValidator;
    private MailMail mailMail;
    private Integer edit = 0;

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping("/car/carRequisitionService.htm")
    public String carRequisitionServiceForm(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("carRequisitionService") Carrequisitionservice carRequisitionService, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        edit = 0;

        HttpSession session = request.getSession(true);
        if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
            carRequisitionService.setCarrequisition(carRequisition);
            model.put("carRequisitionService", carRequisitionService);

            List<Employee> driver = context.getEmployeeService().getAllEmployeeByDriver();
            model.put("driverList", driver);

            model.put("carRequisition", carRequisition);

            List<Carregistration> cars = context.getCarRegistrationService().getAllCarNotInService();
            List<Vendor> vendors = context.getVendorService().getAllValidTransportVendors();
            model.put("carList", cars);
            model.put("vendorList", vendors);

            return "car/carRequisitionServiceForm";
        } else {
            return "redirect:/login.htm";
        }

    }

    @RequestMapping("/car/carRequisitionService/saveOrUpdateCarRequisitionService")
    public String saveOrUpdateCarRequisitionService(HttpServletRequest request, @ModelAttribute("carRequisitionService") Carrequisitionservice carRequisitionService, BindingResult result, Map<String, Object> model) {

        mailMail = new MailMail();
        HttpSession session = request.getSession(true);

        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.RESPOND_REQUISITION_FROM_DAF)) {
                carRequisitionServiceValidator.validate(carRequisitionService, result);
                if (result.hasErrors()) {
                    return carRequisitionServiceForm(request, model, carRequisitionService, carRequisitionService.getCarrequisition());
                }
                carRequisitionService.getCarrequisition().setCarRequisitionStatus("SERVED");
                carRequisitionService.getCarrequisition().setSeen(Boolean.FALSE);
                if (carRequisitionService.getCarrequisition().getCostingBasis().equals("hourlyDaily")) {

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        Date d1 = null;
                        Date d2 = null;
                        System.out.println("Xpecte time of return : " + (carRequisitionService.getCarrequisition().getExpectedTimeReturn()));

                        d1 = sdf.parse(carRequisitionService.getExactTimeOfReturn().toString());
                        d2 = dateFormat.parse(carRequisitionService.getCarrequisition().getExpectedTimeReturn().toString());
                        long diff = d2.getTime() - d1.getTime();
                        long diffMinutes = diff / (1000 * 60) % 60;
                        long diffHours = diff / (1000 * 60 * 60) % 24;
                        long diffDays = diff / (1000 * 60 * 60 * 24);


                        System.out.println("Days :" + diffDays + " Hours :" + diffHours + " Minutes :" + diffMinutes);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

                // carRequisitionService.getCarregistration().setCarServiceStatus("In Service");
                System.out.println("Here is the exact Time of return: " + carRequisitionService.getExactTimeOfReturn());


                //carRequisitionService.setCarregistration(carRequisitionService.getCarregistration());

                context.getCarRequisitionServiceService().saveOrUpdateCarRequisitionService(carRequisitionService);
                //context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisitionService.getCarrequisition());
                //context.getCarRegistrationService().saveOrUpdateCarRegistration(carRequisitionService.getCarregistration());

                if (edit == 0) {
                    Calendar now = Calendar.getInstance();
                    carRequisitionService.setServiceNumber("CR" + carRequisitionService.getCarServiceId() + "/" + (now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.YEAR));
                    carRequisitionService.setServedOn(new Date());
                }
                context.getCarRequisitionServiceService().saveOrUpdateCarRequisitionService(carRequisitionService);
                try {
                    String subject = "Hello, " + carRequisitionService.getCarrequisition().getEmployee().getFirstName() + "!\nYour Car Requisition has Served. \nClick here to Login : http://localhost:8083/OMERSPI/login.htm>\n Thanks.";
                    mailMail.sendMail(Constants.omerspiEmail, carRequisitionService.getCarrequisition().getEmployee().getWorkEmail(), "Car Requisition", subject);
                } catch (Exception ex) {
                    OmerspiUtils.setErrorMessage(session, "Connection Error, Message not sent.");
                }
                OmerspiUtils.setInfoMessage(session, "Car requisition served!.");
                return "redirect:/car/carRequisition/notServed/list";
            } else {
                return "redirect:/login.htm";
            }

        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Form was empty. Try again.");
            return "redirect:/car/carRequisitionService.htm?carRequisitionId=" + carRequisitionService.getCarrequisition().getCarRequisitionId();

        }

    }

    @RequestMapping("/car/carRequisitionService/edit")
    public String editCarRequisitionService(Map<String, Object> model, @RequestParam("carServiceId") Carrequisitionservice carRequisitionService) {

        edit = 1;
        model.put("carRequisitionService", carRequisitionService);
        List<Employee> driver = context.getEmployeeService().getAllEmployeeByDriver();
        model.put("driverList", driver);
        List<Carregistration> cars = context.getCarRegistrationService().getAllCarNotInService();
        List<Vendor> vendors = context.getVendorService().getAllVendor();
        model.put("carList", cars);
        model.put("vendorList", vendors);

        return "car/carRequisitionServiceForm";

    }

    @RequestMapping("/car/carRequisitionService/list")
    public String carRequisitionServiceList(HttpSession session, Map<String, Object> model, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        //set seen to true if seen by requester.
        if (Context.hasPrivilege(session, PrivilegeConstants.EDIT_REQUISITION) && carRequisition.getCarRequisitionStatus().equals("SERVED")) {
            carRequisition.setSeen(Boolean.TRUE);
            context.getCarRequisitionService().saveOrUpdateCarRequisition(carRequisition);
        }
        model.put("carRequisition", carRequisition);
        model.put("carRequisitionServiceList", context.getCarRequisitionServiceService().getCarRequisitionServiceByCarRequisition(carRequisition));
        return "car/carRequisitionServiceList";
    }
}
