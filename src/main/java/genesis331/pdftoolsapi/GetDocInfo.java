package genesis331.pdftoolsapi;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebServlet(name = "getinfo", value = "/getinfo")
@MultipartConfig
public class GetDocInfo extends HttpServlet {

    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (request.getPart("file") != null) {
            DocInfo result = classFunc(request.getPart("file"));
            out.print("{\"data\": " + new Gson().toJson(result) + "}");
        } else {
            out.print("{\"error\": \"No file provided.\"}");
        }
    }

    private class DocInfo {
        String title;
        String author;
        String subject;
        String keywords;
        String creator;
        String producer;
        String creationDate;
        String modificationDate;
        String trapped;
    }

    private DocInfo classFunc(Part filePart) throws IOException {
        PDDocument document = Loader.loadPDF(filePart.getInputStream());
        PDDocumentInformation information = document.getDocumentInformation();
        DocInfo docInfo = new DocInfo();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        docInfo.title = information.getTitle();
        docInfo.author = information.getAuthor();
        docInfo.subject = information.getSubject();
        docInfo.keywords = information.getKeywords();
        docInfo.creator = information.getCreator();
        docInfo.producer = information.getProducer();
        docInfo.creationDate = information.getCreationDate() == null ? null : sdf.format(information.getCreationDate().getTime());
        docInfo.modificationDate = information.getModificationDate() == null ? null : sdf.format(information.getModificationDate().getTime());
        docInfo.trapped = information.getTrapped();
        document.close();
        return docInfo;
    }

    public void destroy() {}
}