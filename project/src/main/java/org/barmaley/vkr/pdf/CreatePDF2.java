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


public class CreatePDF2 {


    public CreatePDF2() throws IOException, DocumentException {
    }

    public static Document createPDF2(String file, Ticket ticket) {

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

        String number = "123324";
        String Name=ticket.getUser().getSurname() + " " + ticket.getUser().getFirstName() +  " " +
                ticket.getUser().getSecondName();;
        String Name1 = ticket.getTitle();
        String Document_type=ticket.getDocumentType().getName();

        // Для получения текущего системного времени достаточно выполнить:
        long curTime = System.currentTimeMillis();

        // Хотите строку в формате, удобном Вам?
        String curStringDate = new SimpleDateFormat("dd.MM.yyyy").format(curTime);
        Double version = 1.0;

        CustomUser principal = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String Coord_name = principal.getSurname()+ " " + principal.getFirstName() + " " +
                principal.getSecondName();
        /************************************************************************************/

        Paragraph p1 = new Paragraph("ЛИЦЕНЗИОННЫЙ ДОГОВОР № " + number, wtf2);
        p1.setAlignment(p1.ALIGN_CENTER);
        document.add (p1);

        Paragraph p2 = new Paragraph("о предоставлении права использования выпускной квалификационной работы", wtf2);
        p2.setAlignment(p2.ALIGN_CENTER);
        document.add (p2);

        Paragraph p3 = new Paragraph("г. Санкт-Петербург                   " +
                "                                                                      «____»____________201__ г.", font_small);

        document.add (p3);


        Paragraph p4 = new Paragraph("Федеральное государственное автономное образовательное учреждение высшего " +
                "образования «Санкт-Петербургский политехнический университет Петра Великого», именуемое в дальнейшем" +
                " Лицензиат, в лице проректора по образовательной деятельности Разинкиной Елены Михайловны, действующей" +
                " на основании Доверенности от 18.01.2017 № юр-23/17-д, с одной стороны, и", font_small);
        document.add (p4);


        Paragraph p5 = new Paragraph();
        Chunk field1 = new Chunk("                                                "
                +Name+"                                                ", font_small);
        field1.setUnderline(0.1f, -2f);
        p5.add(field1);
        p5.setAlignment(p5.ALIGN_CENTER);
        document.add (p5);

        Paragraph p6 = new Paragraph("именуемый(ая) в дальнейшем ", font_small);
        Chunk field2 = new Chunk("Лицензиар", wtf2 );
        p6.add(field2);
        Chunk field3 = new Chunk(" (Автор), с другой стороны, заключили между собой настоящий Договор о " +
                "нижеследующем.", font_small );
        p6.add(field3);
        document.add (p6);

        Paragraph p7 = new Paragraph("1. ПРЕДМЕТ ДОГОВОРА" , wtf2);
        p7.setAlignment(p7.ALIGN_CENTER);
        p7.setSpacingBefore(5.0f);
        document.add (p7);

        Paragraph p8 = new Paragraph();
        Chunk field4 = new Chunk("1.1. Лицензиар", wtf2 );
        Chunk field5 = new Chunk(" предоставляет ", font_small );
        Chunk field6 = new Chunk(" Лицензиату ", wtf2 );
        Chunk field7 = new Chunk(" неисключительное право на использование электронной версии" +
                " созданного им Произведения: ", font_small );
        p8.add(field4);
        p8.add(field5);
        p8.add(field6);
        p8.add(field7);
        document.add (p8);

        Paragraph p9 = new Paragraph();
        Chunk field8 = new Chunk(Name1, font_small );
        field8.setUnderline(0.1f, -2f);
        p9.add(field8);
        p9.setAlignment(p9.ALIGN_CENTER);
        document.add (p9);


        Paragraph p10 = new Paragraph("(Название Произведения)", font_verysmall);
        p10.setAlignment(p10.ALIGN_CENTER);
        p10.setSpacingBefore(-4.0f);
        p10.setSpacingAfter(-4.0f);
        document.add (p10);

        Paragraph p11 = new Paragraph();
        String t1 = "                                                                    ";
        Chunk field9 = new Chunk(t1+ Document_type + t1 , font_small );
        field9.setUnderline(0.1f, -2f);
        p11.add(field9);
        p11.setAlignment(p11.ALIGN_CENTER);
        document.add (p11);

        Paragraph p13 = new Paragraph("(магистерская диссертация, бакалаврская работа, " +
                "дипломная работа, дипломный проект)", font_verysmall);
        p13.setSpacingBefore(-4.0f);
        p13.setAlignment(p13.ALIGN_CENTER);
        p13.setSpacingAfter(-4.0f);
        document.add (p13);

        Paragraph p12 = new Paragraph( "в установленных настоящим Договором пределах.", font_small);
        document.add (p12);

        Paragraph p14 = new Paragraph("2. ОСНОВНЫЕ ПОЛОЖЕНИЯ" , wtf2);
        p14.setAlignment(p14.ALIGN_CENTER);
        p14.setSpacingBefore(5.0f);
        document.add (p14);

        Paragraph p15 = new Paragraph();
        Chunk field10 = new Chunk("2.1. Лицензиар", wtf2 );
        Chunk field11 = new Chunk(" гарантирует, что является надлежащим " +
                "обладателем интеллектуальных прав (исключительных и неисключительных) на Произведение," +
                " указанное в настоящем Договоре. ", font_small );
        p15.add(field10);
        p15.add(field11);
        document.add (p15);

        Paragraph p16 = new Paragraph();
        Chunk field12 = new Chunk("2.2. ", wtf2 );
        Chunk field13 = new Chunk("Положения настоящего Договора относятся к Произведению, " +
                "предоставленному ", font_small );
        Chunk field14 = new Chunk("Лицензиаром ", wtf2 );
        Chunk field15 = new Chunk("в виде электронной версии Произведения для включения в фонд Электронной " +
                "библиотеки ФГАОУ ВО «СПбПУ» (далее – ЭБ СПбПУ). ", font_small );
        p16.add(field12);
        p16.add(field13);
        p16.add(field14);
        p16.add(field15);
        document.add (p16);

        Paragraph p17 = new Paragraph();
        Chunk field16 = new Chunk("2.3. Лицензиар", wtf2 );
        Chunk field17 = new Chunk(" право использования Фундаментальной библиотекой СПбПУ" +
                " электронной формы Произведения как изготовителю баз данных. ", font_small );
        p17.add(field16);
        p17.add(field5);
        p17.add(field6);
        p17.add(field17);
        document.add (p17);

        Paragraph p18 = new Paragraph();
        Chunk field18 = new Chunk("2.4. Лицензиар", wtf2 );
        Chunk field19 = new Chunk(" неисключительное право предоставления Фундаментальной библиотекой СПбПУ" +
                " в полном объеме электронной версии Произведения во временное безвозмездное пользование третьим лицам" +
                " в свободном доступе в сети Интернет согласно указанным режимам доступа: ", font_small );
        p18.add(field18);
        p18.add(field5);
        p18.add(field6);
        p18.add(field19);
        p18.setSpacingAfter(8.0f);
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

        Paragraph p19 = new Paragraph();
        Chunk field20 = new Chunk("2.5. ", wtf2 );
        Chunk field21 = new Chunk("В целях включения Произведения в общероссийский и международный " +
                "научный оборот ", font_small );
        Chunk field22 = new Chunk("Лицензиар ", wtf2 );
        Chunk field23 = new Chunk("настоящим предоставляет ", font_small );
        Chunk field24 = new Chunk("права на использование Произведения в качестве исходного материала для " +
                "выполнения заявок в рамках деятельности межбиблиотечного абонемента (МБА), доставки документов" +
                " (ДД) и электронной доставки документов (ЭДД) при условии, что заявка приходит от некоммерческой" +
                " организации, не принадлежащей какой-либо коммерческой организации библиотеки. ", font_small );
        p19.add(field20);
        p19.add(field21);
        p19.add(field22);
        p19.add(field23);
        p19.add(field6);
        p19.add(field24);
        document.add (p19);

        Paragraph p32 = new Paragraph("Форма сгенерирована автоматически 'модулем размещения ВКР в ЭБ СПБПУ' " +
                curStringDate + " версия: " + version + ".                                                          " +
                " Генерацию осуществил: "
                + Coord_name + ".                                                                                   " +
                "                                                                     Cтраница 1 из 4." ,font_verysmall);
        p32.setSpacingAfter(15.0f);
        p32.setSpacingBefore(30.0f);
        document.add (p32);


        Paragraph p20 = new Paragraph();
        Chunk field25 = new Chunk("2.6. Лицензиар", wtf2 );
        Chunk field26 = new Chunk(" в течение всего срока действия настоящего Договора право решения о " +
                "включении Произведения полностью или частично в фонды: ", font_small );
        Chunk field27 = new Chunk("                                                        – ЭБ СПБПУ;      " +
                "                                  ", font_small );
        Chunk field28 = new Chunk("                                                                         " +
                "                                        – электронных ресурсов" +
                " Фундаментальной библиотеки СПбПУ (далее – ФБ СПбПУ). ", font_small );
        p20.add(field25);
        p20.add(field5);
        p20.add(field6);
        p20.add(field26);
        p20.add(field27);
        p20.add(field28);
        document.add (p20);

        Paragraph p31 = new Paragraph();
        Chunk field53 = new Chunk("2.7. ", wtf2 );
        Chunk field54 = new Chunk("В случае включения представленного Произведения в фонд электронных ресурсов" +
                " ФБ СПбПУ порядок депонирования электронной формы Произведения и сроки его хранения определяются " +
                "регламентом управления фондом электронных ресурсов ФБ СПбПУ и не связаны со сроком действия " +
                "настоящего Договора и уровнем предоставляемого к Произведению доступа.", font_small );
        p31.add(field53);
        p31.add(field54);
        document.add (p31);


        Paragraph p21 = new Paragraph("3. РАЗМЕР ВОЗНАГРАЖДЕНИЯ" , wtf2);
        p21.setAlignment(p14.ALIGN_CENTER);
        p21.setSpacingBefore(5.0f);
        document.add (p21);

        Paragraph p22 = new Paragraph();
        Chunk field29 = new Chunk("3.1. Лицензиар", wtf2 );
        Chunk field30 = new Chunk(" права на использование Произведения без авторского " +
                "вознаграждения и авторских отчислений. ", font_small );
        p22.add(field29);
        p22.add(field5);
        p22.add(field6);
        p22.add(field30);
        document.add (p22);

        Paragraph p23 = new Paragraph();
        Chunk field31 = new Chunk("3.2. ", wtf2 );
        Chunk field32 = new Chunk(" Размер вознаграждения  ", font_small );
        Chunk field33 = new Chunk("Лицензиара  ", wtf2 );
        Chunk field34 = new Chunk("равен 0 (нулю). ", font_small );
        p23.add(field31);
        p23.add(field32);
        p23.add(field33);
        p23.add(field34);
        document.add (p23);

        Paragraph p24 = new Paragraph();
        Chunk field35 = new Chunk("3.3. Лицензиар", wtf2 );
        Chunk field36 = new Chunk(" не платит ", font_small );
        Chunk field37 = new Chunk("за выполнение обязательств по настоящему Договору. ", font_small );
        p24.add(field35);
        p24.add(field36);
        p24.add(field6);
        p24.add(field37);
        document.add (p24);

        Paragraph p25 = new Paragraph("4. СРОК И ТЕРРИТОРИЯ ДЕЙСТВИЯ ДОГОВОРА" , wtf2);
        p25.setAlignment(p14.ALIGN_CENTER);
        p25.setSpacingBefore(5.0f);
        document.add (p25);

        Paragraph p26 = new Paragraph();
        Chunk field38 = new Chunk("4.1. Лицензиар", wtf2 );
        Chunk field39 = new Chunk(" передает ", font_small );
        Chunk field40 = new Chunk("права на использование Произведения" +
                "сроком на 5 (пять) лет с возможностью продления и без ограничения территории. ", font_small );
        p26.add(field38);
        p26.add(field39);
        p26.add(field6);
        p26.add(field40);
        document.add (p26);

        Paragraph p27 = new Paragraph();
        Chunk field41 = new Chunk("4.2. ", wtf2 );
        Chunk field42 = new Chunk("Настоящий Договор вступает в силу с момента подписания обеими сторонами.", font_small );
        p27.add(field41);
        p27.add(field42);
        document.add (p27);

        Paragraph p28 = new Paragraph();
        Chunk field43 = new Chunk("4.3. ", wtf2 );
        Chunk field44 = new Chunk("Срок действия настоящего Договора автоматически продлевается на 1 (один) год," +
                " если ни одна из сторон не выступила с инициативой его расторжения или изменения не позднее," +
                " чем за 2 месяца до истечения срока его действия.", font_small );
        p28.add(field43);
        p28.add(field44);
        document.add (p28);

        Paragraph p29 = new Paragraph();
        Chunk field45 = new Chunk("4.4. ", wtf2 );
        Chunk field46 = new Chunk("Настоящий Договор может быть расторгнут досрочно по инициативе одной из " +
                "сторон с обязательным предупреждением второй стороны не менее чем за 2 месяца или в случае неисполнения" +
                " одной из сторон обязательств по Договору.", font_small );
        p29.add(field45);
        p29.add(field46);
        document.add (p29);

        Paragraph p30 = new Paragraph();
        Chunk field47 = new Chunk("4.5. ", wtf2 );
        Chunk field48 = new Chunk("Библиографическая запись на Произведение и связанные с ней библиотечные" +
                " машиночитаемые записи, созданные силами", font_small );
        Chunk field49 = new Chunk(" Лицензиата, ", wtf2 );
        Chunk field50 = new Chunk("являются интеллектуальной собственностью", font_small );
        Chunk field51 = new Chunk("и используются по усмотрению", font_small );
        Chunk field52 = new Chunk("без ограничения территории и сроков.", font_small );
        p30.add(field47);
        p30.add(field48);
        p30.add(field49);
        p30.add(field50);
        p30.add(field49);
        p30.add(field51);
        p30.add(field49);
        p30.add(field52);
        document.add (p30);

        Paragraph p33 = new Paragraph("5. ПРАВА И ОБЯЗАННОСТИ СТОРОН" , wtf2);
        p33.setAlignment(p14.ALIGN_CENTER);
        p33.setSpacingBefore(5.0f);
        document.add (p33);

        Paragraph p34 = new Paragraph();
        Chunk field55 = new Chunk("5.1. Лицензиат", wtf2 );
        Chunk field56 = new Chunk(" обязуется:", font_small );
        Chunk field57 = new Chunk("                                                                         " +
                "                                                                – предоставить ", font_small );
        Chunk field58 = new Chunk(" Лицензиару ", wtf2 );
        Chunk field59 = new Chunk(" возможность в любое время знакомиться с условиями использования " +
                "Произведения; ", font_small );
        Chunk field60 = new Chunk("                                                                         " +
                "                                                                                  – при использовании Произведения " +
                "принимать меры, обеспечивающие охрану личных неимущественных прав  ", font_small );
        Chunk field61 = new Chunk("Лицензиара", wtf2 );
        Chunk field62 = new Chunk(", и обязуется не вносить какие бы то ни было изменения в содержание" +
                " Произведения без письменного согласия ", font_small );
        Chunk field63 = new Chunk(", в том числе в название, иллюстрации, пояснения, комментарии, послесловия" +
                " и т. п., за исключением случаев, предусмотренных настоящим Договором.", font_small );
        p34.add(field55);
        p34.add(field56);
        p34.add(field57);
        p34.add(field58);
        p34.add(field59);
        p34.add(field60);
        p34.add(field61);
        p34.add(field62);
        p34.add(field61);
        p34.add(field63);
        document.add (p34);

        Paragraph p35 = new Paragraph();
        Chunk field64 = new Chunk("5.2. Лицензиат", wtf2 );
        Chunk field65 = new Chunk(" вправе:", font_small );
        Chunk field66 = new Chunk("                                                                         " +
                "                                                                    – требовать от ", font_small );
        Chunk field67 = new Chunk(" предоставления любой необходимой информации о Произведении;", font_small );
        Chunk field68 = new Chunk("                                                                           " +
                "                                                                                                      " +
                "                  – переводить (конвертировать, модифицировать в любую машиночитаемую форму) " +
                "правомерно опубликованное Произведение (электронную версию Произведения), которое включено в полном " +
                "объеме или частично в фонд ЭБ СПбПУ, в формат, используемый Фундаментальной библиотекой СПбПУ для " +
                "тематических коллекций и баз данных;", font_small );
        p35.add(field64);
        p35.add(field65);
        p35.add(field66);
        p35.add(field61);
        p35.add(field67);
        p35.add(field68);
        document.add (p35);


        Paragraph p38 = new Paragraph("Форма сгенерирована автоматически 'модулем размещения ВКР в ЭБ СПБПУ' " +
                curStringDate + " версия: " + version + ".                                                          " +
                " Генерацию осуществил: "
                + Coord_name + ".                                                                                   " +
                "                                                                     Cтраница 2 из 4." ,font_verysmall);
        p38.setSpacingBefore(47.0f);
        document.add (p38);

        Paragraph p36 = new Paragraph();
        Chunk field69 = new Chunk("5.3. Лицензиар", wtf2 );
        Chunk field70 = new Chunk(" обязуется незамедлительно поставить в известность ", font_small );
        Chunk field71 = new Chunk("Лицензиата", wtf2 );
        Chunk field72 = new Chunk(" о передаче исключительных прав на Произведение," +
                " включая его электронные версии, третьему лицу для исключения претензий" +
                " по условиям настоящего Договора;", font_small );
        p36.add(field69);
        p36.add(field70);
        p36.add(field71);
        p36.add(field72);
        document.add (p36);

        Paragraph p37 = new Paragraph();
        Chunk field73 = new Chunk("5.4. Лицензиар", wtf2 );
        Chunk field74 = new Chunk(" вправе в любое время проверять порядок и условия" +
                " использования Произведения.", font_small );
        p37.add(field73);
        p37.add(field74);
        document.add (p37);

        Paragraph p39 = new Paragraph("6. ПОРЯДОК РАЗРЕШЕНИЯ СПОРОВ. ОТВЕТСТВЕННОСТЬ СТОРОН" , wtf2);
        p39.setAlignment(p14.ALIGN_CENTER);
        p39.setSpacingBefore(5.0f);
        document.add (p39);

        Paragraph p40 = new Paragraph();
        Chunk field75 = new Chunk("6.1.", wtf2 );
        Chunk field76 = new Chunk(" За неисполнение или ненадлежащее исполнение обязательств, " +
                "предусмотренных настоящим Договором, стороны несут ответственность в соответствии" +
                " с действующим законодательством.", font_small );
        p40.add(field75);
        p40.add(field76);
        document.add (p40);

        Paragraph p41 = new Paragraph();
        Chunk field77 = new Chunk("6.2. Лицензиар", wtf2 );
        Chunk field78 = new Chunk(" несет ответственность перед " , font_small );
        Chunk field79 = new Chunk("Лицензиатом", wtf2 );
        Chunk field80 = new Chunk(" за достоверность сведений об обладателе исключительных авторских прав." +
                " В случае предъявления претензий и исков со стороны действительных обладателей авторских прав к " , font_small );
        Chunk field81 = new Chunk("Лицензиату Лицензиар", wtf2 );
        Chunk field82 = new Chunk(" несет ответственность в соответствии с ГК РФ и УК РФ.",font_small );
        p41.add(field77);
        p41.add(field78);
        p41.add(field79);
        p41.add(field80);
        p41.add(field81);
        p41.add(field82);
        document.add (p41);

        Paragraph p42 = new Paragraph();
        Chunk field83 = new Chunk("6.3. Лицензиат", wtf2 );
        Chunk field84 = new Chunk(" несет ответственность за соблюдение авторских прав в соответствии с нормами " +
                "действующего законодательства.", font_small );
        p42.add(field83);
        p42.add(field84);
        document.add (p42);

        Paragraph p43 = new Paragraph();
        Chunk field85 = new Chunk("6.4.", wtf2 );
        Chunk field86 = new Chunk(" Все споры, так или иначе касающиеся настоящего Договора, разрешаются" +
                " сторонами путем переговоров, а при недостижении согласия – в суде по месту нахождения ", font_small );
        Chunk field87 = new Chunk("Лицензиата", wtf2 );
        p43.add(field85);
        p43.add(field86);
        p43.add(field87);
        document.add (p43);

        Paragraph p44 = new Paragraph("7. ЗАКЛЮЧИТЕЛЬНЫЕ ПОЛОЖЕНИЯ" , wtf2);
        p44.setAlignment(p14.ALIGN_CENTER);
        p44.setSpacingBefore(5.0f);
        document.add (p44);

        Paragraph p45 = new Paragraph();
        Chunk field88 = new Chunk("7.1.", wtf2 );
        Chunk field89 = new Chunk(" Исправления непосредственно по тексту настоящего Договора не допускаются и" +
                " не имеют юридической силы.", font_small );
        p45.add(field88);
        p45.add(field89);
        document.add (p45);

        Paragraph p46 = new Paragraph();
        Chunk field90 = new Chunk("7.2.", wtf2 );
        Chunk field91 = new Chunk(" При заключении настоящего Договора Стороны допускают факсимильное " +
                "воспроизведение подписи ", font_small );
        Chunk field92 = new Chunk(", при этом факсимильная подпись будет иметь такую же силу, " +
                "как и подлинная подпись.", font_small );
        p46.add(field90);
        p46.add(field91);
        p46.add(field87);
        p46.add(field92);
        document.add (p46);

        Paragraph p47 = new Paragraph();
        Chunk field93 = new Chunk("7.3.", wtf2 );
        Chunk field94 = new Chunk(" Во всем ином, что не предусмотрено настоящим Договором, Стороны " +
                "руководствуются нормами действующего законодательства.", font_small );
        p47.add(field93);
        p47.add(field94);
        document.add (p47);

        Paragraph p48 = new Paragraph();
        Chunk field95 = new Chunk("7.4.", wtf2 );
        Chunk field96 = new Chunk(" Настоящий Договор составлен в 2-х экземплярах, каждый из которых имеет " +
                "одинаковую юридическую силу.", font_small );
        p48.add(field95);
        p48.add(field96);
        document.add (p48);

        Paragraph p55 = new Paragraph();
        p55.setSpacingBefore(123.0f);
        document.add (p55);

        Paragraph p51 = new Paragraph("Форма сгенерирована автоматически 'модулем размещения ВКР в ЭБ СПБПУ' " +
                curStringDate + " версия: " + version + ".                                                          " +
                " Генерацию осуществил: "
                + Coord_name + ".                                                                                   " +
                "                                                                     Cтраница 3 из 4." ,font_verysmall);
        p51.setSpacingBefore(122.0f);
        document.add (p51);

        Paragraph p49 = new Paragraph("8. АДРЕСА И РЕКВИЗИТЫ СТОРОН" , wtf2);
        p49.setAlignment(p14.ALIGN_CENTER);
        p49.setSpacingBefore(43.0f);
        document.add (p49);

        Paragraph p50 = new Paragraph();
        Chunk field97 = new Chunk("Лицензиат:                                                         ", wtf2 );
        Chunk field98 = new Chunk("                Лицензиар:                                  " +
                "                      ", wtf2 );
        Chunk field99 = new Chunk ("Федеральное государственное автономное ", font_small);
        Chunk field100 = new Chunk ("                   _______________________________________ ", font_small);
        Chunk field101 = new Chunk("образовательное учреждение высшего ", font_small );
        Chunk field102 = new Chunk("                                            " +
                "                       (Фамилия, инициалы)",font_verysmall );
        Chunk field103 = new Chunk("                               " +
                "образования «Санкт-Петербургский               ", font_small);
        Chunk field104 = new Chunk("                Дата рождения: _________________________ ", font_small);
        Chunk field105 = new Chunk("политехнический университет Петра Великого» ", font_small);
        Chunk field106 = new Chunk("                                            " +
                "                     (Число, месяц, год)",font_verysmall );

        Chunk field107 = new Chunk("                     (ФГАОУ ВО «СПбПУ») ", font_small);
        Chunk field108 = new Chunk("                                                  " +
                "Паспорт: серия _______ № ________________", font_small);
        Chunk field109 = new Chunk(" 195251, Санкт-Петербург,                          ", font_small);
        Chunk field110 = new Chunk("                      Выдан _________  _______________________  ", font_small);
        Chunk field111 = new Chunk("     ул. Политехническая, 29                                      ", font_small);
        Chunk field112 = new Chunk("                                      (когда)            " +
                "                     (кем)                                       ",font_verysmall );
        Chunk field113 = new Chunk("ИНН 7804040077 КПП 780401001                             ", font_small);
        Chunk field114 = new Chunk("      _______________________________________    ", font_small);
        Chunk field115 = new Chunk("УФК по г. Санкт-Петербургу                                  ", font_small);
        Chunk field116 = new Chunk("         Адрес регистрации по месту жительства:               ", font_small);
        Chunk field117 = new Chunk("(ФГАОУ ВО «СПбПУ», л/с 30726Щ45759)           ", font_small);
        Chunk field118 = new Chunk("        ________________________________________ ", font_small);
        Chunk field119 = new Chunk("   р/с 40501810300002000001                                                " +
                "                                                                                  ", font_small);
        Chunk field120 = new Chunk("БИК 044030001                                                              " +
                "                                                                                           ", font_small);
        Chunk field121 = new Chunk("Банк плательщика Северо-Западное ГУ Банка         ", font_small);
        Chunk field122 = new Chunk("              Тел. ____________________________        " +
                "      ", font_small);
        Chunk field123 = new Chunk("Контактное лицо:                                                          " +
                "                                                            ", font_small);
        Chunk field124 = new Chunk("__________________________________                                     " +
                "                                                                   ", font_small);
        Chunk field125 = new Chunk("Тел./факс  _________________________ ", font_small);
        p50.add(field97);
        p50.add(field98);
        p50.add(field99);
        p50.add(field100);
        p50.add(field101);
        p50.add(field102);
        p50.add(field103);
        p50.add(field104);
        p50.add(field105);
        p50.add(field106);
        p50.add(field107);
        p50.add(field108);
        p50.add(field109);
        p50.add(field110);
        p50.add(field111);
        p50.add(field112);
        p50.add(field113);
        p50.add(field114);
        p50.add(field115);
        p50.add(field116);
        p50.add(field117);
        p50.add(field118);
        p50.add(field119);
        p50.add(field120);
        p50.add(field121);
        p50.add(field122);
        p50.add(field123);
        p50.add(field124);
        p50.add(field125);
        document.add (p50);



        Paragraph p52 = new Paragraph("9. ПОДПИСИ СТОРОН" , wtf2);
        p52.setAlignment(p14.ALIGN_CENTER);
        p52.setSpacingBefore(5.0f);
        document.add (p52);

        Paragraph p53 = new Paragraph();
        Chunk field126 = new Chunk("От Лицензиата:                                                        ", wtf2 );
        Chunk field127 = new Chunk("               От Лицензиара:                                " +
                "                      ", wtf2 );
        Chunk field128 = new Chunk("                            ");
        field128.setUnderline(0.1f, -2f);
        Chunk field129 = new Chunk("/");
        Chunk field130 = new Chunk(" Е. М. Разинкина ", font_small);
        field130.setUnderline(0.1f, -2f);
        Chunk field131 = new Chunk("                               ");
        Chunk field132 = new Chunk("                                (подпись)                       (расшифровка)" +
                "                                                                  (подпись)                       " +
                " (расшифровка)", font_verysmall);
        p53.add(field126);
        p53.add(field127);
        p53.add(field128);
        p53.add(field129);
        p53.add(field130);
        p53.add(field129);
        p53.add(field131);
        p53.add(field128);
        p53.add(field129);
        p53.add(field128);
        p53.add(field129);
        p53.add(field132);
        p53.setSpacingAfter(120.0f);
        document.add (p53);


        Paragraph p56 = new Paragraph(" ");
        p56.setSpacingBefore(40.0f);
        document.add (p56);


        Paragraph p54 = new Paragraph("Форма сгенерирована автоматически 'модулем размещения ВКР в ЭБ СПБПУ' " +
                curStringDate + " версия: " + version + ".                                                          " +
                " Генерацию осуществил: "
                + Coord_name + ".                                                                                   " +
                "                                                                     Cтраница 4 из 4." ,font_verysmall);
        p54.setSpacingBefore(100.0f);
        document.add (p54);


    }
}