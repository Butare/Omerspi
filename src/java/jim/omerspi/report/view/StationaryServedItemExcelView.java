/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.report.view;

import java.util.Map;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jim.omerspi.model.RequestedItems;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class StationaryServedItemExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Disposition", "attachment; filename=\"served-items-report.xls\"");
        

        List<RequestedItems> allServedItemsBetweenDates = (List<RequestedItems>) model.get("formData");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String convertedDate = null;
        Integer totalServedQty = 0;

        //create a wordsheet
        HSSFSheet sheet = workbook.createSheet("Served items list");

        HSSFRow header = sheet.createRow(0);
        sheet.autoSizeColumn(10);
        header.createCell(0).setCellValue("No.");
        header.createCell(1).setCellValue("Service No.");
        header.createCell(2).setCellValue("Item name");
        header.createCell(3).setCellValue("Released Qty");
        header.createCell(4).setCellValue("Released On");
        header.createCell(5).setCellValue("Released To");
        
        int rowNum = 1;
        for (int i = 0; i < allServedItemsBetweenDates.size(); i++) {
            //create the row data
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            if (allServedItemsBetweenDates.get(i).getStationaryrequisition() != null) {
                row.createCell(1).setCellValue(allServedItemsBetweenDates.get(i).getStationaryrequisition().getServiceNumber());
                row.createCell(2).setCellValue(allServedItemsBetweenDates.get(i).getItems().getItemName());
                row.createCell(3).setCellValue(allServedItemsBetweenDates.get(i).getServedQty());
                convertedDate = sdf.format(allServedItemsBetweenDates.get(i).getServiceDate());
                row.createCell(4).setCellValue(convertedDate);
                row.createCell(5).setCellValue(allServedItemsBetweenDates.get(i).getStationaryrequisition().getEmployee().getFirstName() + " " + allServedItemsBetweenDates.get(i).getStationaryrequisition().getEmployee().getLastName());
            } else {
                row.createCell(1).setCellValue(allServedItemsBetweenDates.get(i).getOfficeassetrequisition().getServiceNumber());
                row.createCell(2).setCellValue(allServedItemsBetweenDates.get(i).getItems().getItemName());
                row.createCell(3).setCellValue(allServedItemsBetweenDates.get(i).getServedQty());
                convertedDate = sdf.format(allServedItemsBetweenDates.get(i).getServiceDate());
                row.createCell(4).setCellValue(convertedDate);
                row.createCell(5).setCellValue(allServedItemsBetweenDates.get(i).getOfficeassetrequisition().getBeneficiary());
            }
        }
    }
}
