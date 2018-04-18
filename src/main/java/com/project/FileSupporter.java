package com.project;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.Getter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class FileSupporter {
    private static String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private static final String DEFAULT_STRING = "raport" + currentDate;
    private Document document;
    @Getter private String fileName;
    private HashMap<String, List> tripData;
    private HashMap<String, String> calculatedData;
    private Font font;

    public FileSupporter(HashMap tripData, HashMap calculatedData) throws IOException {
        this.fileName = DEFAULT_STRING;
        this.tripData = tripData;
        this.calculatedData = calculatedData;
    }

    public void prepareDocument() throws FileNotFoundException, DocumentException {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
        font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK);
    }

    public void generatePdf() throws DocumentException, IOException {
        document.open();
        generateHeader();
        generateTable("Informacje ogolne", calculatedData, "string");
        if(!tripData.get("Costs").isEmpty()){
            document.add(Chunk.NEXTPAGE);
            generateTable("Koszty dodatkowe", tripData, "list");
        }
        document.close();
    }

    private void generateHeader() throws DocumentException {
        Paragraph p = new Paragraph("Kalkulator podrozy sluzbowej", font);
        document.add(p);

        font.setSize(16);
        p = new Paragraph("RAPORT Z DNIA: " + currentDate, font);
        document.add(p);
        document.add(Chunk.NEWLINE);
        document.add(new LineSeparator());
        document.add(Chunk.NEWLINE);
    }

    private void generateTable(String title, HashMap data, String valueType) throws DocumentException, IOException {
        Paragraph p = new Paragraph(title, font);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(Chunk.NEWLINE);
        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addTableData(table, data, valueType);
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("TYP", "WARTOSC")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addTableData(PdfPTable pdfPTable, HashMap data, String valueType) throws IOException {
        if(valueType.equals("string")){
            data.forEach((key, value) ->{
                addRow(pdfPTable, (String)key, (String)value);
            });
        } else {
            List<String> costs = (List)data.get("Costs");
            List<String> amounts = (List)data.get("Amounts");

            Iterator<String> costsIter = costs.iterator();
            Iterator<String> amountsIter = amounts.iterator();

            while(costsIter.hasNext() && amountsIter.hasNext()){
                addRow(pdfPTable, costsIter.next(), amountsIter.next());
            }
        }
    }

    private void addRow(PdfPTable pdfPTable, String key, String value) {
        pdfPTable.addCell(key);
        pdfPTable.addCell(value);
    }
}
