package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") //"/hello"로 들어오면 class 실행
public class HelloServlet extends HttpServlet { //servlet은 HttpServlet을 상속 받아야함

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 서블릿이 호출되면 service 클래스가 호출됨. Ctrl + O
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain"); //content-type에 들어감(header)
        response.setCharacterEncoding("utf-8");//content-type에 들어감(header)
        response.getWriter().write("hello " + username); //http message body에 들어감
    }
}
