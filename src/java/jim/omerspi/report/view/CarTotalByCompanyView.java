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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.List;
import jim.omerspi.model.Carrequisitionservice;

public class CarTotalByCompanyView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<Carrequisitionservice> printDriverByCompany = (List<Carrequisitionservice>) model.get("formData");




        BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);

        String page = "Page: " + writer.getPageNumber() + " of ";
        // headers and footers must be added before the document is opened
        HeaderFooter footer = new HeaderFooter(
                new Phrase(page), true);
        footer.setBorder(Rectangle.NO_BORDER);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);

        document.open();

        Font font1 = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 10, Font.BOLD);
        Font fontBP = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 10, Font.BOLD | Font.UNDERLINE);
        Font fonthead = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 12, Font.BOLD | Font.UNDERLINE);
        Font tableBold = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 12, Font.BOLD);
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


        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Font font2 = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 10, Font.ITALIC | Font.UNDERLINE | Font.TIMES_ROMAN);

        Font fontTableHeader = new Font(Font.getFamilyIndex(DEFAULT_CONTENT_TYPE), 12, Font.BOLD | Font.TIMES_ROMAN);

        Paragraph parT = new Paragraph("TOTAL BY COMPANY REPORT", fonthead);
        parT.setAlignment(Element.ALIGN_CENTER);
        document.add(parT);

        Paragraph par = new Paragraph("From " + request.getParameter("startDate") + " To " + request.getParameter("endDate") + "", font2);
        par.setAlignment(Element.ALIGN_CENTER);
        document.add(par);

        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(3);

        PdfPCell driverNames = new PdfPCell(new Phrase("Company", tableBold));
        driverNames.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(driverNames);

        PdfPCell staffNames = new PdfPCell(new Phrase("Driver Names", tableBold));
        driverNames.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(staffNames);


        PdfPCell amount = new PdfPCell(new Phrase("Amount", tableBold));
        amount.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(amount);


        List<Carrequisitionservice> companyBetweenDates = (List<Carrequisitionservice>) request.getAttribute("companyBetweenDatesList");
        List<Carrequisitionservice> driverList = (List<Carrequisitionservice>) request.getAttribute("drivers");

        int rowspan = 0;
        int driverTotal = 0;
        table.setHeaderRows(1);
        Integer total = 0;
        Integer subTotal = 0;
        Integer found = 0;
        PdfPCell drivers = new PdfPCell();
        //Object[] row = new Object[3];

        for (int n = 0; n < printDriverByCompany.size(); n++) {

            PdfPCell staffs = new PdfPCell();
            PdfPCell amounts = new PdfPCell();


//            row = (Object[]) printDriverByCompany.get(n);
            table.addCell("" + printDriverByCompany.get(n).getVendor().getVendorName());

            rowspan = 0;
            rowspan++;
            drivers.setRowspan(rowspan);
            subTotal = 0;

            for (int j = 0; j < driverList.size(); j++) {

                found = 0;
                for (int a = 0; a < companyBetweenDates.size(); a++) {


                    if (printDriverByCompany.get(n).getVendor().getVendorName().equals(driverList.get(j).getVendor().getVendorName())
                            && driverList.get(j).getDriverNames().equals(companyBetweenDates.get(a).getDriverNames())) {

                        found++;

                        driverTotal += companyBetweenDates.get(a).getCarrequisition().getTotalAmount();
                        subTotal += companyBetweenDates.get(a).getCarrequisition().getTotalAmount();
                        total += companyBetweenDates.get(a).getCarrequisition().getTotalAmount();

                    }
                }
                if (found > 0) {
                    staffs.addElement(new Phrase("" + driverList.get(j).getDriverNames()));
                    amounts.addElement(new Phrase("" + driverTotal));
                    driverTotal = 0;
                }
            }

            if (rowspan > 0) {
                table.addCell(staffs);
                table.addCell(amounts);
                table.addCell(new Phrase("Sub Total", font1));
                table.addCell("");
                table.addCell(new Phrase("" + subTotal, font1));
            }

        }
        table.addCell(new Phrase("\nGrand Total", tableBold));
        table.addCell("\n");
        table.addCell(new Phrase("\n" + total + " Rwf", tableBold));
        document.add(table);

    }
}
