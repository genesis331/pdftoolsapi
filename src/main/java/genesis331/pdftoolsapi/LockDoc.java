package genesis331.pdftoolsapi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "lock", value = "/lock")
@MultipartConfig
public class LockDoc extends HttpServlet {
    private String message;

    public void init() {
        message = "LockDoc";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (request.getPart("file") != null) {
            out.print("{\"message\": \"" + message + "\"}");
        } else {
            out.print("{\"error\": \"No file provided.\"}");
        }
    }

    public void destroy() {}
}