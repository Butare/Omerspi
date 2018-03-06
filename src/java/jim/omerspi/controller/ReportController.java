/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jim.omerspi.JasperReportPrinter;
import jim.omerspi.ServiceContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Jimmy
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ServiceContext context;
    
    @RequestMapping(method=RequestMethod.GET,value="pdf")
    public ModelAndView generatePdfReport(ModelAndView modelAndView){
    
        Map<String,Object >parameterMap=new HashMap<String,Object>();
        List usersList=context.getUserService().getAllUser();
        JRDataSource JRdataSource=new JRBeanCollectionDataSource(usersList);
        parameterMap.put("dataSource", JRdataSource);
        System.out.println("REACHED");
         JasperReportPrinter pr=new JasperReportPrinter();
        modelAndView =new ModelAndView("userListReport",parameterMap);
        System.out.println("REACHED2");
      //  JasperViewer.viewReport(pr, false);
        return modelAndView;
        
    
    }
}

