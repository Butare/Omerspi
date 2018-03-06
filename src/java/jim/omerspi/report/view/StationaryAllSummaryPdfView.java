/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.omerspi.report.view;

import java.util.Map;
import java.util.ArrayList;
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
import jim.omerspi.OmerspiUtils;
import jim.omerspi.ServiceContext;
import jim.omerspi.model.Category;
import jim.omerspi.model.Items;
import jim.omerspi.model.RequestedItems;
import jim.omerspi.model.Stationaryregistration;
import org.springframework.beans.factory.annotation.Autowired;

public class StationaryAllSummaryPdfView extends AbstractPdfView {

    @Autowired
    private ServiceContext context;

    @Override
    protected void buildPdfDocument(Map model, Document document,
            PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //document=new Document(PageSize.A4, 50, 50, 50, 50);
        //document.r
        document.setPageSize(new Rectangle(800, 800));
        Map<Category, List<Stationaryregistration>> itemGroupedByCategory = (Map<Category, List<Stationaryregistration>>) model.get("formData");


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

        Paragraph parT = new Paragraph("STOCK SUMMARY REPORT", fonthead);
        parT.setAlignment(Element.ALIGN_CENTER);
        document.add(parT);

        Paragraph par = new Paragraph("From " + request.getParameter("startDate") + " To " + request.getParameter("endDate") + "", font2);
        par.setAlignment(Element.ALIGN_CENTER);
        document.add(par);

        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(7);
        table.setWidths(new int[]{400, 350, 250, 250, 250, 250, 350});


        PdfPCell categoryHeading = new PdfPCell(new Phrase("Category", tableBold));
        categoryHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(categoryHeading);

        PdfPCell itemHeading = new PdfPCell(new Phrase("Item", tableBold));
        itemHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(itemHeading);


        PdfPCell openingHeading = new PdfPCell(new Phrase("Opening", tableBold));
        openingHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        openingHeading.setNoWrap(true);
        table.addCell(openingHeading);


        PdfPCell entryHeading = new PdfPCell(new Phrase("Entry", tableBold));
        entryHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(entryHeading);


        PdfPCell issuedHeading = new PdfPCell(new Phrase("Release", tableBold));
        issuedHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        //issuedHeading.setColspan(2);
        table.addCell(issuedHeading);

        PdfPCell balanceHeading = new PdfPCell(new Phrase("Balance", tableBold));
        balanceHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
        balanceHeading.setColspan(2);
        table.addCell(balanceHeading);

        table.setHeaderRows(1);

        PdfPTable t = new PdfPTable(2);
        // t.setWidths(new int[]{300,300});

        PdfPCell qty = new PdfPCell(new Phrase("Qty"));
        qty.setHorizontalAlignment(Element.ALIGN_CENTER);
        qty.setBorder(0);

        PdfPCell am = new PdfPCell(new Phrase("Value"));
        am.setHorizontalAlignment(Element.ALIGN_CENTER);
        am.setBorderWidthLeft(1);
        am.setBorderWidthRight(0);

        t.addCell(qty);
        t.addCell(am);


        PdfPCell container = new PdfPCell();
        container.setPadding(0);
        container.setColspan(2);
        container.addElement(t);
        //container.setBorderWidth(0);
        PdfPCell cate = new PdfPCell(new Phrase(""));
        cate.setBorderWidthRight(0);

        PdfPCell ite = new PdfPCell(new Phrase(""));
        ite.setBorderWidthLeft(0);

        PdfPCell openStock = new PdfPCell(new Phrase("Qty"));
        openStock.setHorizontalAlignment(Element.ALIGN_CENTER);


        table.addCell(cate);
        table.addCell(ite);
        table.addCell(openStock);
        table.addCell(openStock);
        table.addCell(openStock);
        table.addCell(container);
        table.setHeaderRows(1);

        //Stationaryregistrations List, other list is above
        List<Stationaryregistration> currentStationaryList = (List<Stationaryregistration>) request.getAttribute("currentStationaryList");
        Map<Items, List<Stationaryregistration>> groupedStationaryByItem = (Map<Items, List<Stationaryregistration>>) request.getAttribute("groupedStationaryByItem");

        List<RequestedItems> servedItemBtnDates = (List<RequestedItems>) request.getAttribute("servedItemBtnDates");


        int rowspan = 0;

        table.setHeaderRows(1);
        long grandTotalAmt = 0;

        Integer subTotalEntryQty = 0;
        Integer subTotalReleasedQty = 0;
        Integer subTotalBalanceQty = 0;
        long subTotalBalanceValue = 0;

        Integer grandTotalEntryQty = 0;
        Integer grandTotalReleasedQty = 0;
        Integer grandTotalBalanceQty = 0;
        long grandTotalBalanceValue = 0;

        Integer found = 0;
        long subTotalAmt = 0;

        int openQty = 0;
        int openQtyMin = 0;
        int openQtyMin1 = 0;
        int validif = 0;
        int validelse = 0;

        long openValue = 0;

        int entryQty = 0;
        int entryValue = 0;
        int issuedQty = 0;
        long weightedAverage = 0;

        long issuedValue = 0;
        int balanceQty = 0;
        long balanceValue = 0;
        int balanceQtyMin = 0;

        PdfPCell drivers = new PdfPCell();

        PdfPTable grandTotalBalanceTable = new PdfPTable(2);
        PdfPCell grandTotalBalanceQtyCell = new PdfPCell();
        grandTotalBalanceQtyCell.setBorder(0);
        PdfPCell grandTotalBalanceValueCell = new PdfPCell();
        grandTotalBalanceValueCell.setBorderWidthLeft(1);
        grandTotalBalanceValueCell.setBorderWidthRight(0);
        grandTotalBalanceValueCell.setBorderWidthBottom(0);
        grandTotalBalanceValueCell.setNoWrap(true);
        PdfPCell grandTotalBalanceContainerCell = new PdfPCell();
        grandTotalBalanceContainerCell.setPadding(0);
        grandTotalBalanceContainerCell.setColspan(2);



        // for (int n = 0; n < itemGroupedByCategory.size(); n++) {
        for (Map.Entry<Category, List<Stationaryregistration>> entryByCategory : itemGroupedByCategory.entrySet()) {


            PdfPCell cat = new PdfPCell();
            cat.setNoWrap(true);
            cat.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

            PdfPCell itemName = new PdfPCell();
            itemName.setNoWrap(true);
            //itemName.ALIGN_JUSTIFIED;

            PdfPCell itemQty = new PdfPCell();
            PdfPCell itemValue = new PdfPCell();
            itemValue.setNoWrap(true);

            PdfPCell openQtyCell = new PdfPCell();

            PdfPCell entryQtyCell = new PdfPCell();

            PdfPCell issuedQtyCell = new PdfPCell();


            PdfPTable balanceTable = new PdfPTable(2);
            PdfPCell balanceQtyCell = new PdfPCell();
            balanceQtyCell.setBorder(0);
            PdfPCell balanceValueCell = new PdfPCell();
            balanceValueCell.setBorderWidthLeft(1);
            balanceValueCell.setBorderWidthRight(0);
            balanceValueCell.setBorderWidthBottom(0);
            balanceValueCell.setNoWrap(true);
            PdfPCell balanceContainerCell = new PdfPCell();
            balanceContainerCell.setPadding(0);
            balanceContainerCell.setColspan(2);

            PdfPTable subTotalBalanceTable = new PdfPTable(2);
            PdfPCell subTotalBalanceQtyCell = new PdfPCell();
            subTotalBalanceQtyCell.setBorder(0);
            PdfPCell subTotalBalanceValueCell = new PdfPCell();
            subTotalBalanceValueCell.setBorderWidthLeft(1);
            subTotalBalanceValueCell.setBorderWidthRight(0);
            subTotalBalanceValueCell.setBorderWidthBottom(0);
            subTotalBalanceValueCell.setNoWrap(true);
            PdfPCell subTotalBalanceContainerCell = new PdfPCell();
            subTotalBalanceContainerCell.setPadding(0);
            subTotalBalanceContainerCell.setColspan(2);



            cat.addElement(new Phrase("" + entryByCategory.getKey().getCategoryName()));

            table.addCell(cat);

            rowspan = 0;

            subTotalEntryQty = 0;
            subTotalReleasedQty = 0;
            subTotalBalanceQty = 0;
            subTotalBalanceValue = 0;

            subTotalAmt = 0;


            for (Map.Entry<Items, List<Stationaryregistration>> entryByItem : groupedStationaryByItem.entrySet()) {
                Items item = entryByItem.getKey();


                found = 0;

                openQty = 0;
                openQtyMin = 0;
                validif = 0;
                validelse = 0;

                entryQty = 0;
                entryValue = 0;
                weightedAverage = 0;

                issuedQty = 0;
                //subTotalReleasedQty = 0;
                issuedValue = 0;

                balanceQty = 0;
                balanceValue = 0;
                balanceQtyMin = 0;
                List<Integer> openQtyList = new ArrayList<Integer>();

                for (int j = 0; j < currentStationaryList.size(); j++) {

                    if (currentStationaryList.get(j).getItems().getCategorytype().getCategory().getCategoryName().equals(entryByCategory.getKey().getCategoryName())
                            && entryByItem.getKey().getItemId().equals(currentStationaryList.get(j).getItems().getItemId())) {

                        rowspan++;
                        drivers.setRowspan(rowspan);

                        found++;


                        //opening stock list for current item 
                        openQtyList.add(currentStationaryList.get(j).getOpeningStock());

                        openValue = (long) (openQty * currentStationaryList.get(j).getUnitPrice());
                        issuedValue = (long) (issuedQty * currentStationaryList.get(j).getUnitPrice());
                        balanceValue = (long) (balanceQty * currentStationaryList.get(j).getUnitPrice());

                        balanceQty = entryByItem.getKey().getTotalQuantity();
                        balanceValue = (long) (balanceQty * currentStationaryList.get(j).getUnitPrice());



                        entryQty += currentStationaryList.get(j).getPurchasedQty();
                        entryValue += currentStationaryList.get(j).getPurchasedQty() * currentStationaryList.get(j).getUnitPrice();

                        subTotalEntryQty += currentStationaryList.get(j).getPurchasedQty();
                        grandTotalEntryQty += currentStationaryList.get(j).getPurchasedQty();


                        subTotalAmt += (currentStationaryList.get(j).getUnitPrice() * currentStationaryList.get(j).getPurchasedQty());
                        grandTotalAmt += (currentStationaryList.get(j).getUnitPrice() * currentStationaryList.get(j).getPurchasedQty());

                    }

                }


                for (int i = 0; i < servedItemBtnDates.size(); i++) {
                    if (entryByItem.getKey().getItemName().equals(servedItemBtnDates.get(i).getItems().getItemName())) {
                        issuedQty += servedItemBtnDates.get(i).getServedQty();

                    }

                }

                int min = 1000;
                for (int i = 0; i < openQtyList.size(); i++) {
                    if (min > openQtyList.get(i)) {
                        min = openQtyList.get(i);
                    }
                }

                if (found > 0) {

                    // for found item details.

                    weightedAverage = entryValue / entryQty;

                    openQtyCell.addElement(new Phrase("" + min));
                    itemName.addElement(new Phrase("" + item.getItemName()));
                    entryQtyCell.addElement(new Phrase("" + entryQty));
                    issuedQtyCell.addElement(new Phrase("" + issuedQty));
                    balanceQtyCell.addElement(new Phrase("" + balanceQty));
                    balanceValueCell.addElement(new Phrase("" + (weightedAverage * balanceQty)));

                    //for sub totals

                    subTotalReleasedQty += issuedQty;
                    subTotalBalanceQty += balanceQty;
                    subTotalBalanceValue += weightedAverage * balanceQty;



                }
            }
            if (rowspan > 0) {
                table.addCell(itemName);

                table.addCell(openQtyCell);

                table.addCell(entryQtyCell);

                table.addCell(issuedQtyCell);

                balanceTable.addCell(balanceQtyCell);
                balanceTable.addCell(balanceValueCell);
                balanceContainerCell.addElement(balanceTable);
                table.addCell(balanceContainerCell);

            }
            table.addCell(new Phrase("\tSub-Total", tableBold));
            table.addCell(new Phrase(""));
            table.addCell(new Phrase(""));
            table.addCell(new Phrase("" + subTotalEntryQty, tableBold));
            table.addCell(new Phrase("" + subTotalReleasedQty, tableBold));

            subTotalBalanceQtyCell.addElement(new Phrase("" + subTotalBalanceQty, tableBold));
            subTotalBalanceValueCell.addElement(new Phrase("" + OmerspiUtils.getRwandaCurrencyFormat(subTotalBalanceValue), tableBold));

            subTotalBalanceTable.addCell(subTotalBalanceQtyCell);
            subTotalBalanceTable.addCell(subTotalBalanceValueCell);
            subTotalBalanceContainerCell.addElement(subTotalBalanceTable);
            table.addCell(subTotalBalanceContainerCell);

            //for grand totals

            grandTotalReleasedQty += subTotalReleasedQty;
            grandTotalBalanceQty += subTotalBalanceQty;
            grandTotalBalanceValue += subTotalBalanceValue;

        }

        table.addCell(new Phrase("\nGrand-Total", tableBold));
        table.addCell(new Phrase("\n"));
        table.addCell(new Phrase("\n"));
        table.addCell(new Phrase("\n" + grandTotalEntryQty, tableBold));
        table.addCell(new Phrase("\n" + grandTotalReleasedQty, tableBold));

        grandTotalBalanceQtyCell.addElement(new Phrase("" + grandTotalBalanceQty, tableBold));
        grandTotalBalanceValueCell.addElement(new Phrase("" + OmerspiUtils.getRwandaCurrencyFormat(grandTotalBalanceValue), tableBold));

        grandTotalBalanceTable.addCell(grandTotalBalanceQtyCell);
        grandTotalBalanceTable.addCell(grandTotalBalanceValueCell);
        grandTotalBalanceContainerCell.addElement(grandTotalBalanceTable);
        table.addCell(grandTotalBalanceContainerCell);


        document.add(table);

    }
}
