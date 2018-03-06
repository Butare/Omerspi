<%@page import="com.itextpdf.text.FontFactory"%>
<%@page import="com.itextpdf.text.BaseColor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.Chunk"%>
<%@page import="com.itextpdf.text.Font"%>
<%@page import="com.itextpdf.text.Rectangle"%>
<%@page import="com.itextpdf.text.pdf.draw.LineSeparator"%>
<%@page import="com.itextpdf.text.Element"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.Phrase"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="com.itextpdf.text.PageSize"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@include file="/WEB-INF/jsp/includes.jsp" %>
<%

    String dat1 = request.getParameter("date1");
    String dat2 = request.getParameter("date2");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //Date dat1=sdf.parse(request.getParameter("date1"));
    //Date dat2=sdf.parse(request.getParameter("date2"));
    response.setContentType("application/pdf");
    Document document = new Document(new Rectangle(700, 800));
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Date date = new Date();
    SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd/MM/yyyy");
    String da = simpleDateformat.format(date);
    List<String> dates = new ArrayList<String>();

    Image img = Image.getInstance("D:\\Finale\\Incident Analysis Management System\\web\\css\\photos\\Report.png");
    img.scalePercent(68f);
    document.add(img);
    Font font2 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD | Font.UNDERLINE);
    Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    LineSeparator UNDERLINE = new LineSeparator(1, 9, null, Element.ALIGN_CENTER, -2);
    Font font3 = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD | Element.ALIGN_CENTER);
    Font font4 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD | Element.ALIGN_CENTER);
    Font font5 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD | Element.ALIGN_CENTER);
    Font font6 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD | Element.ALIGN_CENTER);
    Paragraph par = new Paragraph(new Chunk("ARMS REPORT ON   " + da, font2));
    par.setAlignment(Element.ALIGN_CENTER);
    document.add(par);
    document.add(new Paragraph("\n"));
    document.add(new Paragraph("\n"));
    PdfPTable tablee = new PdfPTable(3);
    tablee.setWidthPercentage(100);
    PdfPTable tablee1 = new PdfPTable(1);
    tablee1.addCell(new Phrase(new Chunk(" Date  ", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD, BaseColor.BLACK))));
    PdfPTable tablee2 = new PdfPTable(1);
    tablee2.addCell(new Phrase(new Chunk(" Serial Number  ", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD, BaseColor.BLACK))));
    PdfPTable tablee3 = new PdfPTable(1);
    tablee3.addCell(new Phrase(new Chunk(" Name ", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD, BaseColor.BLACK))));


    tablee.addCell(tablee1);
    tablee.addCell(tablee2);
    tablee.addCell(tablee3);


    document.add(tablee);
document.close();
%>
