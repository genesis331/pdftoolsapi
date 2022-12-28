package genesis331.pdftoolsapi;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "merge", value = "/merge")
public class MergeDocs extends HttpServlet {
    private String message;

    public void init() {
        message = "MergeDocs";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"message\": \"" + message + "\"}");
    }

    public void destroy() {}
}