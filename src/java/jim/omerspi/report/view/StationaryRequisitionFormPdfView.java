package jim.omerspi.report.view;

import java.util.Map;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.List;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Requisitionresponses;
import jim.omerspi.model.Stationaryrequisition;
import org.springframework.beans.factory.annotation.Autowired;

public class StationaryRequisitionFormPdfView extends AbstractPdfView {

    @Autowired
    private ServiceContext context;

    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        Stationaryrequisition printStationaryForm = (Stationaryrequisition) model.get("formData");

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



        document.add(new Phrase("\t \t                                                                      Service No : " + printStationaryForm.getServiceNumber()));




        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Font font2 = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 12, Font.BOLD | Font.UNDERLINE | Font.TIMES_ROMAN);

        Paragraph parTitle = new Paragraph("STATIONERY REQUISITION FORM", font2);
        parTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(parTitle);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

        PdfPTable table = new PdfPTable(5);
        table.addCell("\tNo.");
        table.addCell("\tDescription");
        table.addCell("\tRequeted Qty");
        table.addCell("\tServed Qty");
        table.addCell("\tObservation");
        int j = 0;

        for (RequestedItems ite : printStationaryForm.getRequestedItemses()) {
            j++;
            table.addCell("\t" + (j));
            table.addCell("\t" + ite.getItems().getItemName());
            table.addCell("\t" + ite.getRequestedQty());
            table.addCell("\t" + ite.getServedQty());
            table.addCell("\t" + ite.getObservation());

        }


        document.add(table);


        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("         Requester Names :\t" + printStationaryForm.getEmployee().getFirstName().toUpperCase() + " " + printStationaryForm.getEmployee().getLastName() + "              Signature : ........................"));
        document.add(new Paragraph("         Department :\t" + printStationaryForm.getEmployee().getDepartment().getDepartmentName()));
        document.add(new Paragraph("         Requested On : " + sdf.format(printStationaryForm.getRequisitionDate())));
        document.add(new Paragraph("         Served On : \t" + sdf.format(printStationaryForm.getServiceDate())));
        for (Requisitionresponses resp : printStationaryForm.getRequisitionresponseses()) {
            document.add(new Paragraph("         Supervisor's Approval : " + resp.getHodResponse()));
            document.add(new Paragraph("         Requisition Status :" + resp.getStationaryrequisition().getStatus()));
        }

    }
}
