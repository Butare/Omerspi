package jim.omerspi.report.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Carrequisitionservice;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarRequisitionReportController {

    @Autowired
    private ServiceContext context;

    @RequestMapping("/getTotalByDriverForm.htm")
    public String printTotalByDriver() {

        return "report/printTotalByDriver";

    }

    @RequestMapping("/getStaffByDriverForm.htm")
    public String getStaffByDriverForm() {

        return "report/printStaffsByDriver";

    }

    @RequestMapping("/getTotalByDepartment.htm")
    public String getTotalByDepartment() {

        return "report/printTotalByDepartment";
    }

    @RequestMapping("/getTotalByCompany.htm")
    public String getTotalByCompany() {

        return "report/printTotalByCompany";
    }

    @RequestMapping("/printTotalByCompany.pdf")
    protected ModelAndView printTotalByCompany(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;

        try {
            List<Carrequisitionservice> vendor = context.getCarRequisitionServiceService().printAllCarrequisitionBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
            request.setAttribute("companyBetweenDatesList", vendor);

            List<Carrequisitionservice> driver = context.getCarRequisitionServiceService().printTotalByDriverBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
            request.setAttribute("drivers", driver);

            List<Carrequisitionservice> printTotalByCompany = context.getCarRequisitionServiceService().printTotalByCompanyBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
            if (!printTotalByCompany.isEmpty()) {
                mes = null;
                if (output == null || "".equals(output)) {
                    //return normal view
                    return new ModelAndView("PdfTotalByCompany", "formData", printTotalByCompany);

                } else if ("PDF".equals(output.toUpperCase())) {
                    //return excel view
                    return new ModelAndView("PdfTotalByCompany", "formData", printTotalByCompany);

                } else {
                    //return normal view
                    return new ModelAndView("PdfTotalByCompany", "formData", printTotalByCompany);

                }
            } else {
                mes = "No Data Found";
                ModelAndView mv = new ModelAndView("redirect:/getTotalByCompany.htm");
                model.put("mes", mes);
                mv.addObject("mes", mes);
                return mv;
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "No Date Selected.");
            return new ModelAndView("redirect:/getTotalByCompany.htm");
        }
    }

    @RequestMapping("/printTotalByDepartment.pdf")
    protected ModelAndView printTotalByDepartment(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;

        try {
            List<Carrequisitionservice> department = context.getCarRequisitionServiceService().printAllCarrequisitionBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
            request.setAttribute("departmentList", department);

            List<Carrequisitionservice> printTotalByDepartment = context.getCarRequisitionServiceService().printServedByDepartmentBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));

            if (!printTotalByDepartment.isEmpty()) {
                mes = null;
                if (output == null || "".equals(output)) {
                    //return normal view
                    return new ModelAndView("PdfTotalByDepartment", "formData", printTotalByDepartment);

                } else if ("PDF".equals(output.toUpperCase())) {
                    //return excel view
                    return new ModelAndView("PdfTotalByDepartment", "formData", printTotalByDepartment);

                } else {
                    //return normal view
                    return new ModelAndView("PdfTotalByDepartment", "formData", printTotalByDepartment);

                }
            } else {
                mes = "No Data Found";
                ModelAndView mv = new ModelAndView("redirect:/getTotalByDepartment.htm");
                model.put("mes", mes);
                mv.addObject("mes", mes);
                return mv;
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "No Date Selected.");
            return new ModelAndView("redirect:/getTotalByDepartment.htm");
        }
    }

    @RequestMapping("/printTotalByDriver.pdf")
    protected ModelAndView printTotalByDriver(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;
        try {
            List<Carrequisitionservice> car = context.getCarRequisitionServiceService().printAllCarrequisitionBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
            request.setAttribute("driverList", car);

            List<Carrequisitionservice> printTotalByDriver = context.getCarRequisitionServiceService().printTotalByDriverBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));

            if (!printTotalByDriver.isEmpty()) {
                if (output == null || "".equals(output)) {
                    //return normal view
                    return new ModelAndView("PdfTotalByDrivers", "formData", printTotalByDriver);

                } else if ("PDF".equals(output.toUpperCase())) {
                    //return excel view
                    return new ModelAndView("PdfTotalByDrivers", "formData", printTotalByDriver);

                } else {
                    //return normal view
                    return new ModelAndView("PdfTotalByDrivers", "formData", printTotalByDriver);

                }
            } else {
                mes = "No Data Found";
                ModelAndView mv = new ModelAndView("redirect:/getTotalByDriverForm.htm");
                model.put("mes", mes);
                mv.addObject("mes", mes);
                return mv;
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "No Date Selected.");
            return new ModelAndView("redirect:/getTotalByDriverForm.htm");

        }
    }

    @RequestMapping("/driversAndTransportedStaff.pdf")
    protected ModelAndView driversAndTransportedStaff(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;

        try {
            List<Carrequisitionservice> car = context.getCarRequisitionServiceService().printAllCarrequisitionBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
            request.setAttribute("driverList", car);

            List<Carrequisitionservice> printStaffsByDriver = context.getCarRequisitionServiceService().printTotalByDriverBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));

            if (!printStaffsByDriver.isEmpty()) {
                if (output == null || "".equals(output)) {
                    //return normal view
                    return new ModelAndView("PdfDriversAndTransportedStaff", "formData", printStaffsByDriver);

                } else if ("PDF".equals(output.toUpperCase())) {
                    //return excel view
                    return new ModelAndView("PdfDriversAndTransportedStaff", "formData", printStaffsByDriver);

                } else {
                    //return normal view
                    return new ModelAndView("PdfDriversAndTransportedStaff", "formData", printStaffsByDriver);

                }
            } else {
                mes = "No Data Found";
                ModelAndView mv = new ModelAndView("redirect:/getStaffByDriverForm.htm");
                model.put("mes", mes);
                mv.addObject("mes", mes);
                return mv;
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "No Date Selected.");
            return new ModelAndView("redirect:/getStaffByDriverForm.htm");
        }
    }

    @RequestMapping("/printCarRequisitionForm.htm")
    protected ModelAndView printCarRequisitionForm(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("carServiceId") Carrequisitionservice carService, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;

        try {
            List<Carrequisition> printCarRequisitionForm = context.getCarRequisitionService().printCarRequisitionForm(u.getEmployee(), carService.getCarrequisition().getCarRequisitionId());

            if (!printCarRequisitionForm.isEmpty()) {
                if (output == null || "".equals(output)) {
                    //return normal view
                    mes = null;
                    return new ModelAndView("PdfCarRequisitionForm", "formData", printCarRequisitionForm);

                } else if ("PDF".equals(output.toUpperCase())) {
                    //return excel view
                    return new ModelAndView("PdfCarRequisitionForm", "formData", printCarRequisitionForm);

                } else {
                    //return normal view
                    return new ModelAndView("PdfCarRequisitionForm", "formData", printCarRequisitionForm);

                }
            } else {
                mes = "No Data Found";
                ModelAndView mv = new ModelAndView("redirect:/printCarRequisitionForm.htm");
                model.put("mes", mes);
                mv.addObject("mes", mes);
                return mv;
            }
        } catch (Exception ex) {
            OmerspiUtils.setErrorMessage(session, "Error Occurred. Try again");
            return new ModelAndView("redirect:/printCarRequisitionForm.htm");
        }
    }
}