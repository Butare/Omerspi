package jim.omerspi.report.view;

import java.util.Map;
import java.util.LinkedHashMap;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Officeassetrequisition;
import jim.omerspi.model.Officeassetrequisitionservice;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Requisitionresponses;
import org.springframework.beans.factory.annotation.Autowired;

public class OfficeAssetRequisitionFormPdfView extends AbstractPdfView {

    @Autowired
    private ServiceContext context;

    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        document.setPageSize(new Rectangle(700, 800));
        Officeassetrequisition printOfficeAssetRequisitionForm = (Officeassetrequisition) model.get("formData");

        // Map<String, Object> list = (Map<String, Object>) model.get("lists");

        BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);

//        String page = "Page: " + writer.getPageNumber() + " of ";
//        // headers and footers must be added before the document is opened
//        HeaderFooter footer = new HeaderFooter(
//                new Phrase(page), true);
//        footer.setBorder(Rectangle.NO_BORDER);
//        footer.setAlignment(Element.ALIGN_CENTER);
//        document.setFooter(footer);

        document.open();

        Font font1 = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 10, Font.BOLD);
        Font fontBP = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 10, Font.BOLD | Font.UNDERLINE);

        //User user=request.getSession(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String dateFormat = null;

        Paragraph parRepublic1 = new Paragraph("REPUBLIC OF RWANDA", font1);
        document.add(parRepublic1);

        Image img = Image.getInstance("/Users/JOHN/Desktop/OMERSPI 22_07_13/OMERSPI/web/images/CoatOfArms.png");
        img.scalePercent(68f);
        img.setAlignment(Image.ALIGN_LEFT);
        document.add(img);

        Paragraph parRepublic2 = new Paragraph("MINISTRY OF INFRASTRUCTURE", font1);
        document.add(parRepublic2);

        Paragraph parRepublic3 = new Paragraph("E-mail: info@mininfra.gov.rw", font1);
        document.add(parRepublic3);

        Phrase parRepublic4 = new Phrase("B.P 24 KIGALI", fontBP);
        document.add(parRepublic4);


        document.add(new Phrase("\t \t                                                                                            Service No : " + printOfficeAssetRequisitionForm.getServiceNumber()));


        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Font font2 = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 12, Font.BOLD | Font.UNDERLINE | Font.TIMES_ROMAN);

        Paragraph parTitle = new Paragraph("OFFICE ASSET REQUISITION FORM", font2);
        parTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(parTitle);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(7);
        table.setWidths(new int[]{100, 350, 200, 180, 300, 300, 300});

        table.addCell("\tNo.");
        table.addCell("\tDescription");
        table.addCell("\tRequeted Qty");
        table.addCell("\tServed Qty");
        table.addCell("\tSerial No.");
        table.addCell("\tAsset code");
        table.addCell("\tObservation");
        int j = 0;
      
        for (RequestedItems ite : printOfficeAssetRequisitionForm.getRequestedItemses()) {
            PdfPCell numberCell = new PdfPCell();
            PdfPCell descriptionCell = new PdfPCell();
            PdfPCell requestedQtyCell = new PdfPCell();
            PdfPCell servedQtyCell = new PdfPCell();
            PdfPCell serialCell = new PdfPCell();
            PdfPCell codeCell = new PdfPCell();
            PdfPCell observationCell = new PdfPCell();

            j++;

            numberCell.addElement(new Phrase("\t" + (j)));
            descriptionCell.addElement(new Phrase("\t" + ite.getItems().getItemName()));
            requestedQtyCell.addElement(new Phrase("\t" + ite.getRequestedQty()));
            servedQtyCell.addElement(new Phrase("\t" + ite.getServedQty()));
            for (Officeassetrequisitionservice service : ite.getOfficeassetrequisitionservices()) {

                serialCell.addElement(new Phrase(service.getSerialNumber()));
                codeCell.addElement(new Phrase(service.getOfficeAssetCode()));
                observationCell.addElement(new Phrase(service.getObservation()));
            }


            table.addCell(numberCell);
            table.addCell(descriptionCell);
            table.addCell(requestedQtyCell);
            table.addCell(servedQtyCell);
            table.addCell(serialCell);
            table.addCell(codeCell);
            table.addCell(observationCell);
        }


        document.add(table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("         Beneficiary Names : " + printOfficeAssetRequisitionForm.getBeneficiary().toUpperCase() + "\t\t\t\t\t                   Signature : .........................."));
        document.add(new Paragraph("         Department :\t" + printOfficeAssetRequisitionForm.getEmployee().getDepartment().getDepartmentName()));
        dateFormat = sdf.format(printOfficeAssetRequisitionForm.getRequisitionDate());
        document.add(new Paragraph("         Requested On : " + dateFormat));
        document.add(new Paragraph("         Served On : " + sdf.format(printOfficeAssetRequisitionForm.getRequisitionDate())));
        for (Requisitionresponses resp : printOfficeAssetRequisitionForm.getRequisitionresponseses()) {
            document.add(new Paragraph("         Supervisor's Approval :\t" + resp.getHodResponse()));
            document.add(new Paragraph("         Requisition Status :\t\t\t" + resp.getOfficeassetrequisition().getStatus()));
        }


    }
}
