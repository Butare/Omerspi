package jim.omerspi.report.view;

import java.util.Map;
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
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Carrequisition;
import jim.omerspi.model.Carrequisitionservice;
import jim.omerspi.model.Itenerary;
import jim.omerspi.model.Requisitionresponses;
import org.springframework.beans.factory.annotation.Autowired;

public class CarRequisitionFormPdfView extends AbstractPdfView {

    @Autowired
    private ServiceContext context;

    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //Map<String, String> revenueData = (Map<String, String>) model.get("revenueData");

        List<Carrequisition> printCarForm = (List<Carrequisition>) model.get("formData");

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
        for (int i = printCarForm.size() - 1; i < printCarForm.size(); i++) {

            for (Carrequisitionservice carService : printCarForm.get(i).getCarrequisitionservices()) {
                document.add(new Phrase("\t \t                                                                      Service No : " + carService.getServiceNumber()));

            }
        }



        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Font font2 = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 12, Font.BOLD | Font.UNDERLINE | Font.TIMES_ROMAN);

        Paragraph parTitle = new Paragraph("LOCAL TRANSPORT REQUEST FORM", font2);
        parTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(parTitle);

        //List<User> l = (List<User>) model.get("revenueData");



        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.Z");


        int nu = 0;
        for (int i = printCarForm.size() - 1; i < printCarForm.size(); i++) {

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("1.\t Staff Names :  " + printCarForm.get(i).getEmployee().getFirstName().toUpperCase() + " " + printCarForm.get(i).getEmployee().getLastName()));
            // document.add(new Paragraph("\n"));
            document.add(new Paragraph("2.\t Position:  " + printCarForm.get(i).getEmployee().getJobPosition()));
            // document.add(new Paragraph("\n"));
            document.add(new Paragraph("3.\t Department:  " + printCarForm.get(i).getEmployee().getDepartment().getDepartmentName()));
            //document.add(new Paragraph("\n"));
            document.add(new Paragraph("4.\t Reasons:  " + printCarForm.get(i).getReason()));
            // document.add(new Paragraph("\n"));
            document.add(new Paragraph("5.\t Destination:  " + printCarForm.get(i).getDestination()));
            document.add(new Phrase("6.\t Itenerary: "));

            for (Itenerary ite : printCarForm.get(i).getIteneraries()) {
                document.add(new Phrase(ite.getIteneraryDetail() + " ; "));
            }
            document.add(new Paragraph());
            Paragraph par = new Paragraph("Requested On", font2);
            document.add(new Paragraph("7.\t Requested On:  " + printCarForm.get(i).getRequestedOn()));
            document.add(new Phrase("8.\t Departure Time: " + printCarForm.get(i).getDepartureTime() + " ...... Expected return Time: " + printCarForm.get(i).getExpectedTimeReturn()));
            document.add(new Paragraph("9.\t Means of Transport Allowed: " + printCarForm.get(i).getCartype().getTypeName()));
            document.add(new Paragraph("10.\t Signature of the Staff: .......................... "));
            document.add(new Phrase("11.\t Approval by Supervisor :  "));
            for (Requisitionresponses resp : printCarForm.get(i).getRequisitionresponseses()) {
                document.add(new Phrase(resp.getHodResponse()));
            }

            document.add(new Paragraph("\n"));
            Paragraph parReport = new Paragraph("Transport report by Staff", font2);
            parReport.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph("\n"));
            document.add(parReport);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("13.\t Amount to be paid: " + printCarForm.get(i).getTotalAmount()));
            document.add(new Phrase("14.\t Driver Names: "));

            for (Carrequisitionservice carService : printCarForm.get(i).getCarrequisitionservices()) {
                document.add(new Phrase(carService.getDriverNames() + "    "));
            }
            document.add(new Paragraph("15.\t Driver's Signature: ............................. "));
            //document.add(new Paragraph("\n"));
            document.add(new Phrase("16.\t Contractor Company: "));
            for (Carrequisitionservice carService : printCarForm.get(i).getCarrequisitionservices()) {
                document.add(new Phrase(carService.getVendor().getVendorName()));
            }



        }


    }
}
