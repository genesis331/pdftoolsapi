package genesis331.pdftoolsapi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@WebServlet(name = "lock", value = "/lock")
@MultipartConfig
public class LockDoc extends HttpServlet {

    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getPart("file") != null) {
            response.setContentType("application/pdf");
            OutputStream out = response.getOutputStream();
            PDDocument document = Loader.loadPDF(request.getPart("file").getInputStream());
            StandardProtectionPolicy spp = new StandardProtectionPolicy("password", "password", new AccessPermission());
            response.setHeader("Content-Disposition", "attachment; filename=\"locked-" + request.getPart("file").getSubmittedFileName() + ".pdf\"");
            document.protect(spp);
            document.save(out);
            out.flush();
        } else {
            response.setContentType("application/json");
            PrintWriter out2 = response.getWriter();
            out2.print("{\"error\": \"No file provided.\"}");
        }
    }

    public void destroy() {}
}