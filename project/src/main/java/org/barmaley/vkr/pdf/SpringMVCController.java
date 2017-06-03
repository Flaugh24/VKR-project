package org.barmaley.vkr.pdf;

import org.barmaley.vkr.domain.Ticket;
import org.barmaley.vkr.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class SpringMVCController {

    @Resource(name = "ticketService")
    private TicketService ticketService;

    @PostMapping(value = "/downloadPDF")
    public void downloadPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();

        String fileName = "AKT_PP_KVAL.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        try {

            CreatePDF.createPDF(temperotyFilePath + "\\" + fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = convertPDFToByteArrayOutputStream(temperotyFilePath + "\\" + fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @RequestMapping(value = "/downloadPDF1")
    public void downloadPDF1(@RequestParam(value = "ticketId") String ticketId, HttpServletRequest request1, HttpServletResponse response1) throws IOException {

        final ServletContext servletContext1 = request1.getSession().getServletContext();
        final File tempDirectory = (File) servletContext1.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();


        Ticket ticket = ticketService.get(ticketId);

        String fileName = "REG_L_KVAL.pdf";
        response1.setContentType("application/pdf");
        response1.setHeader("Content-disposition", "attachment; filename=" + fileName);

        try {

            CreatePDF1.createPDF1(temperotyFilePath + "\\" + fileName, ticket);
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            baos1 = convertPDFToByteArrayOutputStream(temperotyFilePath + "\\" + fileName);
            OutputStream os1 = response1.getOutputStream();
            baos1.writeTo(os1);
            os1.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    @RequestMapping(value = "/downloadPDF2")
    public void downloadPDF2(@RequestParam(value = "ticketId") String ticketId, HttpServletRequest request2, HttpServletResponse response2) throws IOException {

        final ServletContext servletContext1 = request2.getSession().getServletContext();
        final File tempDirectory = (File) servletContext1.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();

        Ticket ticket = ticketService.get(ticketId);

        String fileName = "DOG_KVAL_230117.pdf";
        response2.setContentType("application/pdf");
        response2.setHeader("Content-disposition", "attachment; filename=" + fileName);

        try {

            CreatePDF2.createPDF2(temperotyFilePath + "\\" + fileName, ticket);
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            baos2 = convertPDFToByteArrayOutputStream(temperotyFilePath + "\\" + fileName);
            OutputStream os2 = response2.getOutputStream();
            baos2.writeTo(os2);
            os2.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos;
    }

}
