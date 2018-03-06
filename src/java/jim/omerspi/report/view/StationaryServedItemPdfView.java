package jim.omerspi.report.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.springframework.beans.factory.annotation.Autowired;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.RequestedItems;

public class StationaryServedItemPdfView extends AbstractPdfView {
    
    @Autowired
    private ServiceContext context;
    
    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        document.setPageSize(new Rectangle(700, 800));
        
        List<RequestedItems> allServedItemsBetweenDates = (List<RequestedItems>) model.get("formData");
        
        
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
        
        Paragraph parT = new Paragraph("SERVED ITEMS REPORT", fonthead);
        parT.setAlignment(Element.ALIGN_CENTER);
        document.add(parT);
        
        Paragraph par = new Paragraph("From " + request.getParameter("startDate") + " To " + request.getParameter("endDate") + "", font2);
        par.setAlignment(Element.ALIGN_CENTER);
        document.add(par);
        
        document.add(new Paragraph("\n"));
        
        PdfPTable table = new PdfPTable(6);
        table.setWidths(new int[]{100,300,300,300,300,300}); 
        
        PdfPCell numberHeading = new PdfPCell(new Phrase("No.", tableBold));
        numberHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(numberHeading);
        
        PdfPCell serviceNumberHeading = new PdfPCell(new Phrase("Service No.", tableBold));
        serviceNumberHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(serviceNumberHeading);
        
        
        PdfPCell itemHeading = new PdfPCell(new Phrase("Item", tableBold));
        itemHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(itemHeading);
        
        PdfPCell releasedQtyHeading = new PdfPCell(new Phrase("Released Qty", tableBold));
        releasedQtyHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(releasedQtyHeading);
        
        PdfPCell releasedOnHeading = new PdfPCell(new Phrase("Released On", tableBold));
        releasedOnHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(releasedOnHeading);
        
        PdfPCell releaseToHeading = new PdfPCell(new Phrase("Released To", tableBold));
        releaseToHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(releaseToHeading);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String convertedDate = null;
        Integer totalServedQty = 0;
        
        PdfPCell numberCell = new PdfPCell();
        PdfPCell itemNameCell = new PdfPCell();
        PdfPCell serviceNumberCell = new PdfPCell();
        PdfPCell releasedQtyCell = new PdfPCell();
        PdfPCell releasedOnCell = new PdfPCell();
        PdfPCell releasedToCell = new PdfPCell();
        
        for (int i = 0; i < allServedItemsBetweenDates.size(); i++) {
            
            totalServedQty += allServedItemsBetweenDates.get(i).getServedQty();
            numberCell.addElement(new Phrase("" + (i + 1))); 
            if (allServedItemsBetweenDates.get(i).getStationaryrequisition() != null) {
                serviceNumberCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getStationaryrequisition().getServiceNumber()));
                itemNameCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getItems().getItemName()));
                releasedQtyCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getServedQty()));
                convertedDate = sdf.format(allServedItemsBetweenDates.get(i).getServiceDate());
                releasedOnCell.addElement(new Phrase("" + convertedDate));
                releasedToCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getStationaryrequisition().getEmployee().getFirstName() + " " + allServedItemsBetweenDates.get(i).getStationaryrequisition().getEmployee().getLastName()));
                
            } else {
                serviceNumberCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getOfficeassetrequisition().getServiceNumber()));
                itemNameCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getItems().getItemName()));
                releasedQtyCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getServedQty()));
                convertedDate = sdf.format(allServedItemsBetweenDates.get(i).getServiceDate());
                releasedOnCell.addElement(new Phrase("" + convertedDate));
                releasedToCell.addElement(new Phrase("" + allServedItemsBetweenDates.get(i).getOfficeassetrequisition().getBeneficiary()));
            }
        }
        table.addCell(numberCell);
        table.addCell(serviceNumberCell);
        table.addCell(itemNameCell);
        table.addCell(releasedQtyCell);
        table.addCell(releasedOnCell);
        table.addCell(releasedToCell);
        
        table.addCell(new Phrase("")); 
        table.addCell(new Phrase("\tTotal Qty", tableBold));
        table.addCell(new Phrase(""));
        table.addCell(new Phrase("\t" + totalServedQty, tableBold));
        table.addCell(new Phrase(""));
        table.addCell(new Phrase(""));
        
        
        document.add(table);
        
    }
}
