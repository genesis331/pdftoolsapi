package genesis331.pdftoolsapi;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "root", value = "/")
public class Welcome extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello from PdfTools Java Servlet API!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/text");
        PrintWriter out = response.getWriter();
        out.print(message);
    }

    public void destroy() {}
}