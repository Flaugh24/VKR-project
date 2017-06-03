package org.barmaley.vkr.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CreatePDF {


    public CreatePDF() throws IOException, DocumentException {
    }

    public static Document createPDF(String file) {

        Document document = null;

        try {
            document = new Document(PageSize.A4, (float) 30, (float) 30, (float) 30, (float) 30);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {

            e.printStackTrace();
        }
        return document;

    }

    private static void addMetaData(Document document) {
        document.addTitle("Generate PDF report");
        document.addSubject("Generate PDF report");
        document.addAuthor("M. K.");
        document.addCreator("M. K.");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont("/home/gagarkin/tmp/Times.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font_big = new Font(bf, 13, Font.BOLD);
        Font font_small = new Font(bf, 12);
        Font font_verysmall = new Font(bf, 9, Font.ITALIC);
        Font font_strange = new Font(bf, 12, Font.ITALIC);
        Paragraph p1 = new Paragraph("АКТ", font_big);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);

        document.add(new Paragraph(" "));
        Paragraph p2 = new Paragraph("приема-передачи электронных версий", font_big);
        p2.setSpacingBefore(-25.0f);
        p2.setAlignment(Element.ALIGN_CENTER);
        document.add(p2);

        Paragraph p3 = new Paragraph("выпускных квалификационных работ", font_big);
        p3.setSpacingBefore(-7.0f);
        p3.setAlignment(Element.ALIGN_CENTER);
        document.add(p3);


        String t = (" Кафедра компьютерных систем и программных технологий. Институт компьютерных наук и технологий.");
        String thelp2 = ("                                                                       ");
        Chunk field1 = new Chunk(t + thelp2 + thelp2, font_small);
        field1.setUnderline(0.1f, -2f);
        Paragraph p4 = new Paragraph("Представитель:", font_small);
        p4.add(field1);
        document.add(p4);

        Paragraph p5 = new Paragraph("(кафедра, институт)", font_verysmall);
        p5.setAlignment(Element.ALIGN_CENTER);
        p5.setSpacingBefore(-3.0f);
        document.add(p5);

        Paragraph p6 = new Paragraph();
        String thelp1 = ("                        ");
        String t2 = ("Кузьмин Александр Александрович, заместитетель заведующего кафедры");
        Chunk field2 = new Chunk(thelp1 + t2 + thelp1, font_small);
        field2.setUnderline(0.1f, -2f);
        p6.add(field2);
        document.add(p6);

        Paragraph p7 = new Paragraph("(ФИО, должность)", font_verysmall);
        p7.setAlignment(Element.ALIGN_CENTER);
        p7.setSpacingBefore(-3.0f);
        document.add(p7);

        Paragraph p8 = new Paragraph("передает в Электронную библиотеку ФГАОУ ВО «СПбПУ» электронные версии выпускных" +
                " квалификационных работ (ВКР). Все тексты, передаваемых электронных версий ВКР, проверены на объем" +
                " заимствования в системе «Антиплагиат».", font_small);
        p8.setSpacingAfter(15.0f);
        document.add(p8);

        float[] columnWidths = {5, 6};
        PdfPTable table1 = new PdfPTable(columnWidths);
        table1.setWidthPercentage(100);
        table1.setSpacingBefore(0f);
        table1.setSpacingAfter(0f);
        String t3 = "Информатика и вычислительная техника";
        PdfPCell header1 = new PdfPCell(new Phrase("Направление подготовки (специальность):", font_small));
        PdfPCell header2 = new PdfPCell(new Phrase(t3, font_small));
        header1.setFixedHeight(18f);
        header2.setFixedHeight(18f);
        table1.addCell(header1);
        table1.addCell(header2);
        document.add(table1);


        float[] columnWidths1 = {2, 9};
        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.setWidthPercentage(100);
        table2.setSpacingBefore(0f);
        table2.setSpacingAfter(0f);
        String t4 = "43501/3";
        PdfPCell header3 = new PdfPCell(new Phrase("группа:", font_small));
        PdfPCell header4 = new PdfPCell(new Phrase(t4, font_small));
        header3.setFixedHeight(18f);
        header4.setFixedHeight(18f);
        table2.addCell(header3);
        table2.addCell(header4);
        table2.setSpacingAfter(20.0f);
        document.add(table2);

        float[] columnWidths2 = {1, 3, 4, 4};
        PdfPTable table3 = new PdfPTable(columnWidths2);
        table3.setWidthPercentage(100);
        table3.setSpacingBefore(0f);
        table3.setSpacingAfter(0f);
        PdfPCell header5 = new PdfPCell(new Phrase("№", font_small));
        PdfPCell header6 = new PdfPCell(new Phrase("ФИО Студента", font_small));
        PdfPCell header7 = new PdfPCell(new Phrase("Тема ВКР", font_small));
        PdfPCell header8 = new PdfPCell(new Phrase("Номер и дата договора", font_small));
        header5.setFixedHeight(18f);
        header6.setFixedHeight(18f);
        header7.setFixedHeight(18f);
        header8.setFixedHeight(18f);
        table3.addCell(header5);
        table3.addCell(header6);
        table3.addCell(header7);
        table3.addCell(header8);
        for (int counter = 1; counter < 7; counter++) {
            table3.addCell("№" + String.valueOf(counter));
            table3.addCell("ФИО Студента " + " ");
            table3.addCell("Тема ВКР " + " ");
            table3.addCell("Номер и дата договора " + " ");
        }
        table3.setSpacingAfter(7.0f);
        document.add(table3);

        String t5 = ("5");
        Chunk field3 = new Chunk("   " + t5 + "   ", font_small);
        field3.setUnderline(0.1f, -2f);
        Chunk field3_1 = new Chunk("Итого передано: ", font_small);
        Chunk field3_2 = new Chunk(" электронных версий ВКР.", font_small);
        document.add(field3_1);
        document.add(field3);
        document.add(field3_2);

        Paragraph p10 = new Paragraph("           Передача электронных версий ВКР осуществляется в соответствии с п. 8.9. " +
                "Положения о порядке проведения государственной итоговой аттестации " +
                "по образовательным программам высшего образования – программам бакалавриата," +
                " программам специалитета и программам магистратуры" +
                " (утв. приказом ФГАОУ ВО «СПбПУ» от 12.11.2015 № 1285) и на основании заключенных " +
                "Лицензионных договоров о предоставлении права использования выпускной квалификационной работы.", font_small);
        p10.setSpacingBefore(7.0f);
        document.add(p10);

        Paragraph p11 = new Paragraph("           Регистрационные листы выпускных квалификационных работ студентов" +
                "с согласием на размещение электронной версии ВК Рв Электронной библиотеке СПбПУ " +
                "прилагаются согласно списку. ", font_small);
        document.add(p11);

        Paragraph p12 = new Paragraph("           Варианты доступа указаны в Регистрационном листе ВКР.", font_small);
        p12.setSpacingAfter(7.0f);
        document.add(p12);

        Paragraph p13 = new Paragraph("Данный акт составлен в двух экземплярах, по одному экземпляру для каждой из сторон.", font_small);
        p13.setSpacingAfter(7.0f);
        document.add(p13);

        String t6 = ("Седьмое ");
        Chunk field4 = new Chunk("        " + t6 + "        ", font_small);
        field4.setUnderline(0.1f, -2f);
        Chunk field4_1 = new Chunk("Координатор:   ", font_small);
        Chunk field4_2 = new Chunk("                                          ", font_small);
        field4_2.setUnderline(0.1f, -2f);
        Chunk field4_3 = new Chunk("       ", font_small);
        document.add(field4_1);
        document.add(field4);
        document.add(field4_3);
        document.add(field4_2);
        document.add(field4_3);
        document.add(field4_2);

        Paragraph p14 = new Paragraph("                                          (подразделение)" +
                "                                    (подпись)" +
                "                                    (расшифровка подписи)", font_verysmall);
        p14.setSpacingAfter(7.0f);
        p14.setSpacingBefore(-4.0f);
        document.add(p14);


        Chunk field5_1 = new Chunk("Телефон координатора:   ", font_small);
        String number = "+79818459632";
        Chunk field5 = new Chunk("     " + number + "      ", font_small);
        field5.setUnderline(0.1f, -2f);
        Chunk field5_2 = new Chunk("E-mail координатора:", font_small);
        Chunk mail = new Chunk("Hzz666@mail.ru");
        Chunk field5_3 = new Chunk("     " + mail + "     ", font_small);
        field5_3.setUnderline(0.1f, -2f);
        Chunk field5_4 = new Chunk("   ", font_small);
        document.add(field5_1);
        document.add(field5);
        document.add(field5_4);
        document.add(field5_2);
        document.add(field5_4);
        document.add(field5_3);

        Paragraph p15 = new Paragraph();
        p15.setSpacingAfter(7.0f);
        p15.setSpacingBefore(7.0f);
        document.add(p15);

        String t7 = ("Седьмое");
        Chunk field6 = new Chunk("        " + t7 + "        ", font_small);
        field6.setUnderline(0.1f, -2f);
        Chunk field6_1 = new Chunk("Представитель ФБ СПБПУ: ", font_small);
        Chunk field6_2 = new Chunk("                                       ", font_small);
        field6_2.setUnderline(0.1f, -2f);
        Chunk field6_3 = new Chunk("       ", font_small);
        document.add(field6_1);
        document.add(field6);
        document.add(field6_3);
        document.add(field6_2);
        document.add(field6_3);
        document.add(field6_2);

        Paragraph p16 = new Paragraph("                                                                       (подразделение)" +
                "                                  (подпись)" +
                "                                (расшифровка подписи)", font_verysmall);
        p16.setSpacingAfter(7.0f);
        p16.setSpacingBefore(-4.0f);
        document.add(p16);

        Chunk field7_1 = new Chunk("Исходные файлы сохранены в папке: ", font_strange);
        String pack = "D:/something";
        Chunk field7_2 = new Chunk("           " + pack + "            ", font_small);
        field7_2.setUnderline(0.1f, -2f);
        document.add(field7_1);
        document.add(field7_2);

        Paragraph p17 = new Paragraph("                                                                    " +
                "                             (адрес размещения)", font_verysmall);
        p17.setSpacingBefore(-4.0f);
        document.add(p17);

    }
}
