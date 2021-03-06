/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.dao;

import java.util.Date;
import java.util.List;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Carrequisitionservice;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jimmy
 */
@Transactional
public interface CarRequisitionServiceDao {

    public void saveOrUpdateCarRequisitionService(Carrequisitionservice carRequisitionService);

    public void deleteCarRequisitionService(Carrequisitionservice carRequisitionService);

    public Carrequisitionservice getCarRequisitionServiceById(Integer carServiceId);

    public List getAllCarRequisitionService();

    public List< Carrequisitionservice> getCarRequisitionServiceByCarRequisition(Carrequisition carRequisition);

    public List<Carrequisitionservice> printTotalByDriverBetweenDates(Date d1, Date d2);

    public List<Carrequisitionservice> printAllCarrequisitionBetweenDates(Date d1, Date d2);

    public List<Carrequisitionservice> printServedByDepartmentBetweenDates(Date d1, Date d2);

    public List<Carrequisitionservice> printTotalByCompanyBetweenDates(Date d1, Date d2);
}
