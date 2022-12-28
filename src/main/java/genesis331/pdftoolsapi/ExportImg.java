package genesis331.pdftoolsapi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;

@WebServlet(name = "exportimg", value = "/exportimg")
@MultipartConfig
public class ExportImg extends HttpServlet {

    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (request.getPart("file") != null) {
            ArrayList result = classFunc(request.getPart("file"));
            out.print("{\"data\": " + result + "}");
        } else {
            out.print("{\"error\": \"No file provided.\"}");
        }
    }

    private ArrayList classFunc(Part filePart) throws IOException {
        ArrayList<String> outputImgs = new ArrayList<>();
        PDDocument document = Loader.loadPDF(filePart.getInputStream());
        PDFRenderer renderer = new PDFRenderer(document);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage img = renderer.renderImage(i);
            ImageIO.write(img, "jpg", baos);
            outputImgs.add("\"" + Base64.getEncoder().encodeToString(baos.toByteArray()) + "\"");
        }
        document.close();
        return outputImgs;
    }

    public void destroy() {}
}