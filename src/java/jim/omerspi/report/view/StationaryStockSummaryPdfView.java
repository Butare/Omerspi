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
import java.text.SimpleDateFormat;
import jim.omerspi.OmerspiUtils;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Category;
import jim.omerspi.model.Stationaryregistration;
import org.springframework.beans.factory.annotation.Autowired;

public class StationaryStockSummaryPdfView extends AbstractPdfView {

    @Autowired
    private ServiceContext context;

    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //document=new Document(PageSize.A4, 50, 50, 50, 50);
        //document.r
        document.setPageSize(new Rectangle(700,800));
       
        Map<Category,List<Stationaryregistration>> itemGroupedByCategory = (Map<Category,List<Stationaryregistration>>) model.get("formData");
        

        BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);

        String page = "Page: " + writer.getPageNumber() + " of " + (writer.getPageNumber());
        // headers and footers must be added before the document is opened
        HeaderFooter footer = new HeaderFooter(
                new Phrase(page), false);
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

        Paragraph parT = new Paragraph("PURCHASED STASTIONARY SUMMARY", fonthead);
        parT.setAlignment(Element.ALIGN_CENTER);
        document.add(parT);

        Paragraph par = new Paragraph("From " + request.getParameter("startDate") + " To " + request.getParameter("endDate") + "", font2);
        par.setAlignment(Element.ALIGN_CENTER);
        document.add(par);

        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(6);
        table.setWidths(new int[]{300, 370, 200, 190, 350, 300});
//      float[] width=new float[]{50f,50f,50f,50f,50f,50f};
//        
//        table.setWidths(width);

        PdfPCell categoryHeading = new PdfPCell(new Phrase("Category", tableBold));
        categoryHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(categoryHeading);

        PdfPCell itemHeading = new PdfPCell(new Phrase("Item", tableBold));
        itemHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(itemHeading);


        PdfPCell quantityHeading = new PdfPCell(new Phrase("Quantity", tableBold));
        quantityHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        quantityHeading.setNoWrap(true);
        table.addCell(quantityHeading);


        PdfPCell unitPriceHeading = new PdfPCell(new Phrase("Price/ Unit", tableBold));
        unitPriceHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(unitPriceHeading);


        PdfPCell valueHeading = new PdfPCell(new Phrase("Value", tableBold));
        valueHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(valueHeading);

        PdfPCell purchaseDate = new PdfPCell(new Phrase("Purchase Date", tableBold));
        purchaseDate.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(purchaseDate);

        //Stationaryregistrations List, other list is above
        List<Stationaryregistration> currentStationaryList = (List<Stationaryregistration>) request.getAttribute("currentStationaryList");



        int rowspan = 0;

        table.setHeaderRows(1);
        long grandTotalAmt = 0;
        Integer subTotalQty = 0;
        long subTotalAmt = 0;
        PdfPCell drivers = new PdfPCell();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String dateOfficeAsset = null;
        String dateStationary = null;
        System.out.println("Grouped items : " + itemGroupedByCategory.size());

        for (Map.Entry<Category,List<Stationaryregistration>>entryCategory:itemGroupedByCategory.entrySet()) {
            

            PdfPCell cat = new PdfPCell();
            cat.setNoWrap(true);
            cat.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

            PdfPCell itemName = new PdfPCell();
            itemName.setNoWrap(true);
            //itemName.ALIGN_JUSTIFIED;

            PdfPCell itemQty = new PdfPCell();
            PdfPCell unit = new PdfPCell();
            PdfPCell itemValue = new PdfPCell();
            itemValue.setNoWrap(true);
            PdfPCell date = new PdfPCell();

            cat.addElement(new Phrase("" + entryCategory.getKey().getCategoryName()));
            table.addCell(cat);

            rowspan = 0;
            subTotalQty = 0;
            subTotalAmt = 0;


            for (int j = 0; j < currentStationaryList.size(); j++) {

                if (currentStationaryList.get(j).getItems().getCategorytype().getCategory().getCategoryName().equals(entryCategory.getKey().getCategoryName())) {
                    rowspan++;
                    drivers.setRowspan(rowspan);

//                    if (!currentStationaryList.get(j).getItem().getType().equals("Office Asset")) {

                        itemName.addElement(new Phrase("" + currentStationaryList.get(j).getItems().getItemName()));
                        itemQty.addElement(new Phrase("" + currentStationaryList.get(j).getPurchasedQty()));
                        unit.addElement(new Phrase("" +(long) currentStationaryList.get(j).getUnitPrice()));
                        itemValue.addElement(new Phrase("" +(long) (currentStationaryList.get(j).getPurchasedQty() * currentStationaryList.get(j).getUnitPrice())));
                        dateStationary = sdf.format(currentStationaryList.get(j).getAcquisitionDate());
                        date.addElement(new Phrase("" + dateStationary));
                        subTotalQty += currentStationaryList.get(j).getPurchasedQty();
                        subTotalAmt +=(long) (currentStationaryList.get(j).getUnitPrice() * currentStationaryList.get(j).getPurchasedQty());
                        grandTotalAmt +=(long) (currentStationaryList.get(j).getUnitPrice() * currentStationaryList.get(j).getPurchasedQty());


//                    }


                }

            }
            if (rowspan > 0) {
                table.addCell(itemName);
                table.addCell(itemQty);
                table.addCell(unit);
                table.addCell(itemValue);
                table.addCell(date);
                table.addCell(new Phrase("Sub Total", font1));
                table.addCell("");
                table.addCell(new Phrase("" + subTotalQty, font1));
                table.addCell("");
                table.addCell(new Phrase(""+OmerspiUtils.getRwandaCurrencyFormat(subTotalAmt)+" Rwf", font1));
                table.addCell("");

            }

        }
        table.addCell(new Phrase("\nGrand Total", tableBold));
        table.addCell("\n");
        table.addCell("\n");
        table.addCell("\n");
        table.addCell(new Phrase("\n"+OmerspiUtils.getRwandaCurrencyFormat(grandTotalAmt)+" Rwf", tableBold));
        table.addCell("");
        document.add(table);

    }
}
