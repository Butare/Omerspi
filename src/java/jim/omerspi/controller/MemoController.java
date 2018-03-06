/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import jim.omerspi.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jim.omerspi.Context;
import jim.omerspi.PrivilegeConstants;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Memo;
import jim.omerspi.model.Officeassetrequisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JOHN
 */
@Controller
public class MemoController {

    @Autowired
    private ServiceContext context;

    @RequestMapping("/memoForm.htm")
    public String memoForm(HttpSession session, Map<String, Object> model, @RequestParam("notFoundItemRegistrationId") Officeassetrequisition officeAssetRequisition) {

        Memo memo = new Memo();
        memo.setOfficeassetrequisition(officeAssetRequisition);
        model.put("memo", memo);
        return "memo/memoForm";
    }

    @RequestMapping("car/carRequisition/memoForm.htm")
    public String carRequisitionMemoForm(HttpSession session, Map<String, Object> model, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        Memo memo = new Memo();
        memo.setCarrequisition(carRequisition);
        model.put("memo", memo);
        return "memo/memoForm";
    }

    @RequestMapping(value = "/saveOrUpdateMemo", method = RequestMethod.POST)
    public String saveOrUpdateMemo(HttpServletRequest request, @ModelAttribute("memo") Memo memo) {
        HttpSession session = request.getSession(true);
        memo.setDueDate(new Date());
        if (Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_PROFESSIONAL) || Context.hasPrivilege(session, PrivilegeConstants.ADD_REQUISITION_HOD)) {
            context.getMemoService().saveOrUpdateMemo(memo);
            return "redirect:/car/carRequisition/list";
        } else {
            return "redirect:/login.htm";
        }
    }

    @RequestMapping(value = "/searchMemo", method = RequestMethod.GET)
    public String searchMemo(Map<String, Object> model, @RequestParam("notFoundRequisitionId") Officeassetrequisition officeAssetRequisition) {

        List<Officeassetrequisition> memoByOfficeAsset = context.getMemoService().getMemoByOfficeAssetRequisitionId(officeAssetRequisition);
        model.put("memoByNotFoundList", memoByOfficeAsset);
        return "memo/memoByNotFoundItemRequisition";
    }

    @RequestMapping(value = "car/carRequisition/searchMemo", method = RequestMethod.GET)
    public String searchMemo(Map<String, Object> model, @RequestParam("carRequisitionId") Carrequisition carRequisition) {

        List<Officeassetrequisition> memoByCarRequisition = context.getMemoService().getMemoByCarRequisitionId(carRequisition);
        model.put("memoByNotFoundList", memoByCarRequisition);
        return "memo/memoByNotFoundItemRequisition";
    }

    @RequestMapping(value = "memo/edit")
    public String editMemo(Map<String, Object> model, @RequestParam("memoId") Memo memo) {

        model.put("memo", memo);
        return "memo/memoForm";


    }
}
