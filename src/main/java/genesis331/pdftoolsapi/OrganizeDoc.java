package genesis331.pdftoolsapi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdfwriter.compress.CompressParameters;

import java.io.IOException;
import java.util.ArrayList;
import java.io.OutputStream;

@WebServlet(name = "organize", value = "/organize")
@MultipartConfig
public class OrganizeDoc extends HttpServlet {

    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/pdf");
        if (request.getPart("file") != null) {
            String[] pages = request.getParameter("pages").split(",");
            PDDocument document = Loader.loadPDF(request.getPart("file").getInputStream());
            PDPageTree pagesTree = document.getPages();
            PDDocument newDocument = new PDDocument();
            OutputStream out = response.getOutputStream();
            ArrayList<Integer> pagesList = new ArrayList<Integer>();

            for (String page : pages) {
                pagesList.add(Integer.parseInt(page));
            }

            for (int i = 0; i < pagesList.size(); i++) {
                newDocument.addPage(pagesTree.get(pagesList.get(i)));
            }
            
            response.setHeader("Content-Disposition", "attachment; filename=" + request.getPart("file").getSubmittedFileName() + ".pdf\"");
            newDocument.save(out, CompressParameters.DEFAULT_COMPRESSION);
            out.flush();
        }
    }

    public void destroy() {}
}