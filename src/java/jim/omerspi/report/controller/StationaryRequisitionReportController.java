/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.report.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Category;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryregistration;
import jim.omerspi.model.Stationaryrequisition;
import jim.omerspi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author JOHN
 */
@Controller
public class StationaryRequisitionReportController {

    @Autowired
    private ServiceContext context;

    @RequestMapping("/servedItem.pdf")
    public String servedItemPdf() {
        return "report/printServedItemsPdf";
    }

    @RequestMapping("/servedItem.excel")
    public String servedItemExcel() {
        return "report/printServedItemsExcel";
    }

    @RequestMapping("/purchasedStationaryItems.form")
    public String purchasedItem() {
        return "report/printPurchasedStationary";
    }

    @RequestMapping("/purchasedOfficeAssetItems.form")
    public String purchasedOfficeAsset() {
        return "report/printPurchasedOfficeAsset";
    }

    @RequestMapping("/allStockSummary.form")
    public String allStationarySummary() {
        return "report/printAllStockSummary";
    }

    @RequestMapping("/stationaryRequisition/printStationaryRequisitionForm.htm")
    protected ModelAndView printStationaryRequisitionForm(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("stationaryRequisitionId") Stationaryrequisition stationaryRequisition, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);

//        List<Stationaryrequisition> printCarRequisitionForm = context.getStationaryRequisitionService().printStationaryRequisitionForm(u.getEmployee(), stationaryRequisition.getStationaryRequisitionId());

        if (output == null || "".equals(output)) {
            //return normal view
            return new ModelAndView("PdfStationaryRequisitionForm", "formData", stationaryRequisition);

        } else if ("PDF".equals(output.toUpperCase())) {
            //return excel view
            return new ModelAndView("PdfStationaryRequisitionForm", "formData", stationaryRequisition);

        } else {
            //return normal view
            return new ModelAndView("PdfStationaryRequisitionForm", "formData", stationaryRequisition);

        }
    }

    @RequestMapping("/stationaryRequisition/printPurchasedStationary.pdf")
    protected ModelAndView printPurchasedStationary(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;

        if (Context.hasPrivilege(session, PrivilegeConstants.VIEW_REPORT)) {

            try {
                //Stationaryregistrations List between Dates
                request.setAttribute("currentStationaryList", context.getStationaryRegistrationsService().getStationaryBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate)));
                Map<Category, List<Stationaryregistration>> printGroupedStationary = context.getStationaryRegistrationsService().groupedStationaryByCategoryBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
                //
                if (!printGroupedStationary.isEmpty()) {
                    mes = null;
                    if (output == null || "".equals(output)) {
                        //return normal view
                        return new ModelAndView("PdfPurchasedStationarySummary", "formData", printGroupedStationary);

                    } else if ("PDF".equals(output.toUpperCase())) {
                        //return excel view
                        return new ModelAndView("PdfPurchasedStationarySummary", "formData", printGroupedStationary);

                    } else {
                        //return normal view
                        return new ModelAndView("PdfPurchasedStationarySummary", "formData", printGroupedStationary);

                    }
                } else {
                    mes = "No Data Found";
                    ModelAndView mv = new ModelAndView("redirect:/purchasedStationaryItems.form");
                    model.put("mes", mes);
                    mv.addObject("mes", mes);
                    return mv;
                }
            } catch (Exception ex) {
                OmerspiUtils.setErrorMessage(session, "No Date Selected.");
                return new ModelAndView("redirect:/purchasedStationaryItems.form");
            }
        } else {
            return new ModelAndView("redirect:/login.htm");
        }
    }

    @RequestMapping("/stationaryRequisition/printServedCategory.pdf")
    protected ModelAndView printServedCategory(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;

        try {
//            List<RequestedItems> servedBetweenDates = context.getRequestedItemService().getServedItemBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
//            request.setAttribute("servedCategoryBetweenDates", servedBetweenDates);

//            request.setAttribute("servedItemBetweenDates", context.getRequestedItemService().getServedItemGroupedByItem(startDate, OmerspiUtils.getLastMomentOfDay(endDate)));

            List<RequestedItems> servedItemBetweenDates = context.getRequestedItemService().getServedItemBetweenDates(OmerspiUtils.getFirstMomentOfDay(startDate), OmerspiUtils.getLastMomentOfDay(endDate));

            if (!servedItemBetweenDates.isEmpty()) {

                if ("PDF".equals(output.toUpperCase())) {
                    //return excel view
                    return new ModelAndView("PdfServedItemByCategory", "formData", servedItemBetweenDates);

                } else if ("EXCEL".equals(output.toUpperCase())) {
                    return new ModelAndView("ExcelServedItemByCategory", "formData", servedItemBetweenDates);
                } else {
                    OmerspiUtils.setErrorMessage(session, "Error occurred. use correct format.!");
                    ModelAndView mv = new ModelAndView("redirect:/servedItem.form");
                    return mv;

                }

            } else {
                mes = "No Data Found";
                ModelAndView mv = null;
                if ("PDF".equals(output.toUpperCase())) {
                    mv = new ModelAndView("redirect:/servedItem.pdf");
                    model.put("mes", mes);
                    mv.addObject("mes", mes);
                    return mv;
                }
                if ("EXCEL".equals(output.toUpperCase())) {
                    mv = new ModelAndView("redirect:/servedItem.excel");
                    model.put("mes", mes);
                    mv.addObject("mes", mes);
                }
                return mv;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            OmerspiUtils.setErrorMessage(session, "No Date Selected.");
            String redirectString = null;
            if ("PDF".equals(output.toUpperCase())) {
                redirectString = "redirect:/servedItem.pdf";
            }
            if ("EXCEL".equals(output.toUpperCase())) {
                redirectString = "redirect:/servedItem.excel";
            }
            return new ModelAndView(redirectString);
        }
    }

    /////
    @RequestMapping("/stationaryRequisition/printAllStockSummary.pdf")
    protected ModelAndView printAllStationarySummary(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);
        String mes = null;
        try {
            if (Context.hasPrivilege(session, PrivilegeConstants.VIEW_REPORT)) {

                //Stationaryregistrations List between Dates
                request.setAttribute("currentStationaryList", context.getStationaryRegistrationsService().getStationaryBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate)));
                request.setAttribute("groupedStationaryByItem", context.getStationaryRegistrationsService().groupedStationaryByItemBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate)));
                request.setAttribute("servedItemBtnDates", context.getRequestedItemService().getServedItemBetweenDates(OmerspiUtils.getFirstMomentOfDay(startDate), OmerspiUtils.getLastMomentOfDay(endDate)));


                Map<Category, List<Stationaryregistration>> printGroupedStationary = context.getStationaryRegistrationsService().groupedStationaryByCategoryBetweenDates(startDate, OmerspiUtils.getLastMomentOfDay(endDate));
                //

                if (!printGroupedStationary.isEmpty()) {
                    mes = null;
                    if (output == null || "".equals(output)) {
                        //return normal view
                        return new ModelAndView("PdfAllStationarySummary", "formData", printGroupedStationary);

                    } else if ("PDF".equals(output.toUpperCase())) {
                        //return excel view
                        return new ModelAndView("PdfAllStationarySummary", "formData", printGroupedStationary);

                    } else {
                        //return normal view
                        return new ModelAndView("PdfAllStationarySummary", "formData", printGroupedStationary);

                    }
                } else {
                    mes = "No Data Found";
                    ModelAndView mv = new ModelAndView("redirect:/printAllStockSummary.form");
                    model.put("mes", mes);
                    mv.addObject("mes", mes);
                    return mv;
                }
            } else {
                return new ModelAndView("redirect:/login.htm");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            OmerspiUtils.setErrorMessage(session, "No date Selected.");
            return new ModelAndView("redirect:/printAllStockSummary.form");
        }
    }
    //office asset requisition form print
    
     @RequestMapping("/officeAssetRequisition/printOfficeAssetRequisitionForm.htm")
    protected ModelAndView printOfficeAssetRequisitionForm(Map<String, Object> model, @RequestParam("output") String output, @RequestParam("officeAssetRequisitionId") Officeassetrequisition officeAssetRequisition, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User u = Context.getCurrentUser(session);

//        List<RequestedItems> printOfficeAssetRequisitionForm = context.getRequestedItemService().getRequestedItemByOfficeAssetRequisition(officeAssetRequisition);
//        request.setAttribute("requestedItems", context.getRequestedItemService().getRequestedItemByOfficeAssetRequisition(officeAssetRequisition));
       
        if (output == null || "".equals(output)) {
            //return normal view
            return new ModelAndView("PdfOfficeAssetRequisitionForm", "formData", officeAssetRequisition);

        } else if ("PDF".equals(output.toUpperCase())) {
            //return excel view
            return new ModelAndView("PdfOfficeAssetRequisitionForm", "formData", officeAssetRequisition);

        } else {
            //return normal view
            return new ModelAndView("PdfOfficeAssetRequisitionForm", "formData", officeAssetRequisition);

        }
    }

}