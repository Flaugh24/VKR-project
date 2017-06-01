package org.barmaley.vkr.pdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.barmaley.vkr.autentication.CustomUser;
import org.barmaley.vkr.domain.Ticket;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class CreatePDF1 {



    public CreatePDF1() throws IOException, DocumentException {
    }

    public static Document createPDF1(String file, Ticket ticket) {



        Document document = null;

        try {
            document = new Document(PageSize.A4, (float) 30, (float) 30, (float) 30, (float) 30);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document, ticket);

            document.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (DocumentException e) {
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

    private static void addTitlePage(Document document, Ticket ticket)
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
        Font smallest = new Font(bf, 9);
        Font mini = new Font(bf, 11);
        Font wtf = new Font(bf, 11, Font.BOLD);
        Font wtf2 = new Font(bf, 12, Font.BOLD);


        /********************************Данные для вставки*********************************/
     //
        long curTime = System.currentTimeMillis();
        String curStringDate = new SimpleDateFormat("dd.MM.yyyy").format(curTime);
        String Number="124253453";
        String date = curStringDate;
        String date_dog = curStringDate;
        String Number_dog="96745654";
        String Document_type= ticket.getDocumentType().getName();
        String Document_type_eng=ticket.getDocumentType().getNameEng();
        String Boss=ticket.getSflNMaster();
        String Boss_eng=ticket.getSflNMasterEng();
        String Author=ticket.getUser().getSurname() + " " + ticket.getUser().getFirstName() +  " " +
                ticket.getUser().getSecondName();
        String Author_eng=ticket.getUser().getSecondNameEng() +  " " + ticket.getUser().getFirstNameEng() +  " " +
                ticket.getUser().getSurnameEng();
        String name = ticket.getTitle();
        String name_eng= ticket.getTitleEng();
        String year = ticket.getYearOfPublic();
        String inst = ticket.getInstitute();
        String kaf = ticket.getDirection();
        String group = ticket.getGroupNum();
        String phone = ticket.getUser().getPhoneNumber();
        String size1 = "128 kB";
        String size2 = "132 kB";
        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String Coord_name = principal.getSurname()+ " " + principal.getFirstName() + " " +
                principal.getSecondName();
        String version = "1.0";
        /************************************************************************************/

        Paragraph p1 = new Paragraph("Информационно-библиотченый комплекс СПБПУ", smallest);
        p1.setAlignment(p1.ALIGN_RIGHT);
        document.add (p1);

        Paragraph p2 = new Paragraph("                Фундаментальная библиотека", smallest);
        p2.setAlignment(p2.ALIGN_RIGHT);
        p2.setSpacingBefore (-1.0f);
        p2.setSpacingAfter(5.0f);
        document.add (p2);

        Chunk field1 = new Chunk("                               Регистрационный лист №: ", font_big);
        Chunk field1_1 = new Chunk("     "+Number+"     ", font_big);
        field1_1.setUnderline(0.1f, -2f);
        Chunk field1_2 = new Chunk("от", font_big);
        Chunk field1_3 = new Chunk("   "+date+"   ", font_big);
        field1_3.setUnderline(0.1f, -2f);
        document.add (field1);
        document.add (field1_1);
        document.add (field1_2);
        document.add (field1_3);

        Paragraph p3 = new Paragraph("выпускной квалификационной работы", font_big);
        p3.setAlignment(p2.ALIGN_CENTER);
        p3.setSpacingBefore (-3.0f);
        p3.setSpacingAfter(-5.0f);
        document.add (p3);

        Chunk field2 = new Chunk("к Лицензионному договору о предоставлении права использования ВКР от ", font_small);
        Chunk field2_1 = new Chunk("  "+Number_dog+"  ", font_small);
        field2_1.setUnderline(0.1f, -2f);
        Chunk field2_2 = new Chunk("№ ", font_small);
        Chunk field2_3 = new Chunk("  "+date_dog + "  ", font_small);
        field2_3.setUnderline(0.1f, -2f);
        document.add (field2);
        document.add (field2_1);
        document.add (field2_2);
        document.add (field2_3);

        Paragraph p4 = new Paragraph("Публикуется впервые", wtf);
        p4.setAlignment(p4.ALIGN_CENTER);
        p4.setSpacingBefore (-3.0f);
        p4.setSpacingAfter(-2.0f);
        document.add (p4);

        String thelp2 = ("                                               ");
        Chunk field3 = new Chunk(thelp2+Document_type+thelp2, font_small);
        field3.setUnderline(0.1f, -2f);
        Paragraph p5 = new Paragraph("Тип документа:  ", font_small);
        p5.add(field3);
        document.add (p5);

        Paragraph p6 = new Paragraph("                                              " +
                "(магистерская диссертация, бакалаврская работа, дипломная работа, дипломный проект)", font_verysmall);
        p6.setSpacingBefore(-4.0f);
        p6.setSpacingAfter(-2.0f);
        document.add (p6);

        Chunk field4 = new Chunk(thelp2+Document_type_eng+thelp2, font_small);
        field4.setUnderline(0.1f, -2f);
        Paragraph p7 = new Paragraph("Тип документа на англ.:", font_small);
        p7.add(field4);
        document.add (p7);

        Paragraph p8 = new Paragraph("                                                           " +
                "(master's thesis, bachelor work, graduate work, graduate project)", font_verysmall);
        p8.setSpacingBefore(-4.0f);
        p8.setSpacingAfter(-2.0f);
        document.add (p8);


        String thelp3 = ("                                       " );
        Chunk field5 = new Chunk(thelp3+Boss+thelp3, font_small);
        field5.setUnderline(0.1f, -2f);
        Paragraph p9 = new Paragraph("Руководитель: ", font_small);
        p9.add(field5);
        document.add (p9);

        Paragraph p10 = new Paragraph("                                                      " +
                "                                             (ФИО, ученая степень, должность)", font_verysmall);
        p10.setSpacingBefore(-4.0f);
        p10.setSpacingAfter(-2.0f);
        document.add (p10);

        String thelp4 = ("                                   ");
        Chunk field6 = new Chunk(thelp4+Boss_eng+thelp4, font_small);
        field6.setUnderline(0.1f, -2f);
        Paragraph p11 = new Paragraph("Руководитель на англ.: ", font_small);
        p11.add(field6);
        document.add (p11);

        Paragraph p12 = new Paragraph("                                                          " +
                "                                                (Full name, degree, position)", font_verysmall);
        p12.setSpacingBefore(-4.0f);
        p12.setSpacingAfter(-2.0f);
        document.add (p12);

        String thelp5 = ("                                               ");
        Chunk field7 = new Chunk(thelp5+Author+thelp5, font_small);
        field7.setUnderline(0.1f, -2f);
        Paragraph p13 = new Paragraph("Автор(ы): ", font_small);
        p13.add(field7);
        document.add (p13);

        String thelp6 = ("                                              ");
        Chunk field8 = new Chunk(thelp6+Author_eng+thelp6, font_small);
        field8.setUnderline(0.1f, -2f);
        Paragraph p14 = new Paragraph("Автор(ы) на англ.: ", font_small);
        p14.add(field8);
        p14.setSpacingBefore(1.0f);
        p14.setSpacingAfter(1.0f);
        document.add (p14);

        Chunk field9 = new Chunk(name, font_small);
        field9.setUnderline(0.1f, -2f);
        Paragraph p15 = new Paragraph("Заглавие: ", font_small);
        p15.add(field9);
        document.add (p15);

        Chunk field10 = new Chunk(name_eng, font_small);
        field10.setUnderline(0.1f, -2f);
        Paragraph p16 = new Paragraph("Заглавие на англ.: ", font_small);
        p16.add(field10);
        p16.setSpacingBefore(1.0f);
        p16.setSpacingAfter(1.0f);
        document.add (p16);

        Chunk field11 = new Chunk("   Санкт-Петербург   ", font_small);
        field11.setUnderline(0.1f, -2f);
        Paragraph p17 = new Paragraph("Место публикации: ", font_small);
        p17.add(field11);
        Chunk field12 = new Chunk("  Место публикации на англ.: ", font_small);
        p17.add(field12);
        Chunk field13 = new Chunk("   Saint-Petersburg   ", font_small);
        field13.setUnderline(0.1f, -2f);
        p17.add(field13);
        document.add (p17);

        Chunk field14 = new Chunk("  "+ year + "  ", font_small);
        field14.setUnderline(0.1f, -2f);
        Paragraph p18 = new Paragraph("Год публикации: ", font_small);
        p18.add(field14);
        p18.setSpacingBefore(1.0f);
        p18.setSpacingAfter(20.0f);
        document.add (p18);

        float[] columnWidths2 = {4, 2, 2, 4};
        PdfPTable table1 = new PdfPTable(columnWidths2);
        table1.setWidthPercentage(100);
        table1.setSpacingBefore(0f);
        table1.setSpacingAfter(0f);
        PdfPCell header1 = new PdfPCell(new Phrase("Свободный доступ из сети Интернет", font_small));
        PdfPCell header2 = new PdfPCell(new Phrase("Чтение", font_small));
        PdfPCell header3 = new PdfPCell(new Phrase("Чтение, печать", font_small));
        PdfPCell header4 = new PdfPCell(new Phrase("Чтение, печать, копирование", font_small));
        header1.setFixedHeight(18f);
        header2.setFixedHeight(18f);
        header3.setFixedHeight(18f);
        header4.setFixedHeight(18f);
        table1.addCell(header1);
        table1.addCell(header2);
        table1.addCell(header3);
        table1.addCell(header4);
        PdfPCell help4 = new PdfPCell(new Phrase("Отметить один из трех режимов (да):", font_small));
        table1.addCell(help4);
        table1.addCell(" ");
        table1.addCell(" ");
        table1.addCell(" ");
        table1.setSpacingAfter(15.0f);
        document.add(table1);

        Chunk field15 = new Chunk("(Заполняется в рукописном виде)", font_strange);
        Chunk field16 = new Chunk ("Соглашение ", wtf2);
        document.add(field16);
        document.add(field15);
        Paragraph p19 = new Paragraph("Я, ", font_small);
        Chunk field17 = new Chunk("                                                       " +
                "    ", font_small);
        field17.setUnderline(0.1f, -2f);
        p19.add(field17);
        Chunk field18 = new Chunk (" , ", font_small);
        Chunk field19 = new Chunk("               ", font_small);
        field19.setUnderline(0.1f, -2f);
        p19.add(field19);
        Chunk field20 = new Chunk (" г. рождения ", font_small);
        p19.add(field17);
        p19.add(field18);
        p19.add(field19);
        p19.add(field20);
        p19.setSpacingAfter(-3.0f);
        document.add(p19);
        Chunk field21 = new Chunk("                                                             " +
                "                (ФИО полностью)", font_verysmall);
        Paragraph p20 = new Paragraph();
        p20.setSpacingAfter(-5.0f);
        p20.add(field21);
        document.add(p20);
        Paragraph p21 = new Paragraph( "зарегистрированный(ая) по адресу", font_small);
        Chunk field22 = new Chunk("                                                       " +
                "                                                         ", font_small);
        field22.setUnderline(0.1f, -2f);
        p21.add(field22);
        Chunk field23 = new Chunk (" даю свое согласие на размещение в полном объеме электронной версии " +
                "вышеуказанной выпускной квалификационной работы в Электронной библиотеке СПбПУ (ЭБ СПбПУ) в соответствии" +
                " с п. 8.9. Положения о порядке проведения государственной итоговой аттестации по образовательным " +
                "программам высшего образования – программам бакалавриата, программам специалитета и программам " +
                "магистратуры» (утв. приказом ФГАОУ ВО «СПбПУ» от 12.11.2015 № 1285) и её предоставление пользователям" +
                " ЭБ СПбПУ. Даю свое согласие на первичное предоставление информации " +
                "о годе рождения в открытых источниках      ", font_small);
        p21.add(field23);
        Chunk field24 = new Chunk("                                                  ", font_small);
        field24.setUnderline(0.1f, -2f);
        p21.add(field24);
        p21.setSpacingAfter(-5.0f);
        document.add(p21);
        Chunk field25 = new Chunk("                                                             " +
                "                                                                             " +
                "                (Вписать ДА или НЕТ)", font_verysmall);
        Paragraph p22 = new Paragraph();
        p22.setSpacingBefore(1.0f);
        p22.add(field25);
        document.add(p22);

        Chunk field26 = new Chunk("                                          ", font_small);
        field26.setUnderline(0.1f, -2f);
        Chunk field27 = new Chunk("                                          ", font_small);
        field27.setUnderline(0.1f, -2f);
        Chunk field28 = new Chunk("                                                                    ");
        Chunk field29 = new Chunk("        ");
        document.add(field28);
        document.add(field26);
        document.add(field29);
        document.add(field27);
        Paragraph p23 = new Paragraph("                                                           " +
                "                                                            (подпись)" +
                "                                       (расшифровка подписи)", font_verysmall);
        p23.setSpacingAfter(5.0f);
        p23.setSpacingBefore(-4.0f);
        document.add (p23);

        Paragraph p54 = new Paragraph("Форма сгенерирована автоматически 'модулем размещения ВКР в ЭБ СПБПУ' " +
                curStringDate + " версия: " + version + ".                                                          " +
                " Генерацию осуществил: "
                + Coord_name + ".                                                                                   " +
                "                                                                     Cтраница 1 из 2." ,font_verysmall);
        p54.setSpacingBefore(45.0f);
        p54.setSpacingAfter(20.0f);
        document.add (p54);


        Paragraph p24 = new Paragraph("Информация об авторе:", wtf2);
        document.add (p24);

        String thelp7 = ("                                                 ");
        Chunk field30 = new Chunk(thelp7+inst+thelp7, mini);
        field30.setUnderline(0.1f, -2f);
        Paragraph p25 = new Paragraph("Институт: ", mini);
        p25.add(field30);
        document.add (p25);

        Paragraph p26 = new Paragraph("Кафедра: ", mini);
        Chunk field31 = new Chunk("              "+ kaf + "              " , mini);
        field31.setUnderline(0.1f, -2f);
        p26.add(field31);
        Chunk field32 = new Chunk ("       группа: ", mini);
        Chunk field33 = new Chunk("       "+ group +"       ", mini);
        field33.setUnderline(0.1f, -2f);
        p26.add(field32);
        p26.add(field33);
        document.add (p26);

        String thelp8 = ("                                                              ");
        Chunk field34 = new Chunk(thelp8+phone+thelp8, mini);
        field34.setUnderline(0.1f, -2f);
        Paragraph p27 = new Paragraph("Телефон/E-mail автора:   ", mini);
        p27.add(field34);
        document.add (p27);

        Chunk field35 = new Chunk(kaf, mini);
        field35.setUnderline(0.1f, -2f);
        Chunk field35_1 = new Chunk("Заф. кафедрой: ", mini);
        Chunk field35_2 = new Chunk("                          ", mini);
        field35_2.setUnderline(0.1f, -2f);
        Chunk field45_3 = new Chunk("    ", mini);
        document.add (field35_1);
        document.add (field35);
        document.add (field45_3);
        document.add (field35_2);
        document.add (field45_3);
        document.add (field35_2);

        Paragraph p28 = new Paragraph("                                                               " +
                "                      (кафедра)" +
                "                                                               (подпись)" +
                "         (расшифровка подписи)", font_verysmall);
        p28.setSpacingAfter(7.0f);
        p28.setSpacingBefore(-4.0f);
        document.add (p28);

        Paragraph p29 = new Paragraph("                                                                          " +
                "                                                                    «____»____________201__ г.", mini);
        p29.setSpacingBefore(-1.0f);
        document.add (p29);

        Paragraph p30 = new Paragraph("Загруженные файлы: ", wtf2);
        p30.setSpacingBefore(3.0f);
        document.add (p30);

        Paragraph p31 = new Paragraph("Произведение.pdf, " + size1, mini);
        p31.setSpacingBefore(2.0f);
        document.add (p31);

        Paragraph p32 = new Paragraph("Произведение.edoc, " + size2, mini);
        p32.setSpacingBefore(-3.0f);
        document.add (p32);

        Paragraph p45 = new Paragraph();
        p45.setSpacingAfter(7.0f);
        p45.setSpacingBefore(7.0f);
        document.add (p45);

        Chunk field7_1 = new Chunk("Исходные файлы сохранены в папке: ", font_strange);
        String pack = "D:/something";
        Chunk field7_2 = new Chunk("           "+pack+"            ", font_small);
        field7_2.setUnderline(0.1f, -2f);
        document.add (field7_1);
        document.add (field7_2);

        Paragraph p46 = new Paragraph("                                                                    " +
                "                             (адрес размещения)", font_verysmall);
        p46.setSpacingBefore(-4.0f);
        document.add (p46);



        String t6 = ("Седьмое ");
        Chunk field44 = new Chunk("        "+t6+"        ", font_small);
        field44.setUnderline(0.1f, -2f);
        Chunk field44_1 = new Chunk("Координатор:   ", font_small);
        Chunk field44_2 = new Chunk("                                          ", font_small);
        field44_2.setUnderline(0.1f, -2f);
        Chunk field44_3 = new Chunk("       ", font_small);
        document.add (field44_1);
        document.add (field44);
        document.add (field44_3);
        document.add (field44_2);
        document.add (field44_3);
        document.add (field44_2);

        Paragraph p44 = new Paragraph("                                          (подразделение)" +
                "                                    (подпись)" +
                "                                    (расшифровка подписи)", font_verysmall);
        p44.setSpacingAfter(7.0f);
        p44.setSpacingBefore(2.0f);
        document.add (p44);


        String t7 = ("Седьмое");
        Chunk field46 = new Chunk("        "+t7+"        ", font_small);
        field46.setUnderline(0.1f, -2f);
        Chunk field46_1 = new Chunk("Представитель ФБ СПБПУ: ", font_small);
        Chunk field46_2 = new Chunk("                                       ", font_small);
        field46_2.setUnderline(0.1f, -2f);
        Chunk field46_3 = new Chunk("       ", font_small);
        document.add (field46_1);
        document.add (field46);
        document.add (field46_3);
        document.add (field46_2);
        document.add (field46_3);
        document.add (field46_2);

        Paragraph p48 = new Paragraph("                                                                       (подразделение)" +
                "                                  (подпись)" +
                "                                (расшифровка подписи)", font_verysmall);
        p48.setSpacingAfter(7.0f);
        p48.setSpacingBefore(-4.0f);
        document.add (p48);

        Paragraph p49 = new Paragraph("                                                                          " +
                "                                                                    «____»____________201__ г.", mini);
        p49.setSpacingBefore(-1.0f);
        p49.setSpacingAfter(60.0f);
        document.add (p49);

        Paragraph p56 = new Paragraph(" ");
        p56.setSpacingBefore(130.0f);
        p56.setSpacingAfter(20.0f);
        document.add (p56);



        Paragraph p55 = new Paragraph("Форма сгенерирована автоматически 'модулем размещения ВКР в ЭБ СПБПУ' " +
                curStringDate + " версия: " + version + ".                                                          " +
                " Генерацию осуществил: "
                + Coord_name + ".                                                                                   " +
                "                                                                     Cтраница 2 из 2." ,font_verysmall);
        p55.setSpacingBefore(130.0f);
        document.add (p55);


    }
}
