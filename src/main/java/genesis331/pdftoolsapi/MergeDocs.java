package genesis331.pdftoolsapi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;

@WebServlet(name = "merge", value = "/merge")
@MultipartConfig
public class MergeDocs extends HttpServlet {
    
    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getParts().isEmpty() != true) {
            PDFMergerUtility PDFmerger = new PDFMergerUtility();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            for (Part part : request.getParts()) {
                PDFmerger.addSource(part.getInputStream());
            }

            PDFmerger.setDestinationStream(out);
            PDFmerger.setDestinationFileName("merged.pdf");
            PDFmerger.mergeDocuments(null);

            response.setContentLength(out.size());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=merged.pdf");
            response.getOutputStream().write(out.toByteArray());

            out.flush();
        } else {
            response.setContentType("application/json");
            PrintWriter out2 = response.getWriter();
            out2.print("{\"error\": \"No file provided.\"}");
        }
    }

    public void destroy() {}
}