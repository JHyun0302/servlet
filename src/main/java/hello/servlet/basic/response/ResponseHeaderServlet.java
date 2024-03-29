package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK); //200
//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //status: 400

        //[response-header]
//        response.setHeader("Content-Type", "text/plain; charset=utf-8"); // content랑 똑같음
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //캐시 무효화
        response.setHeader("Pragma", "no-cache"); //캐시 무효화
        response.setHeader("my-header", "Hello"); //내가 원하는 임의의 헤더 만들기

        //[Header 편의 메서드]
        content(response);
        cookie(response);
//        redirect(response);

        //[message body]
        PrintWriter writer = response.getWriter();
        writer.println("올~ 석사는 영어도 잘하네?");
    }

    private void content(HttpServletResponse response) {
        /**
         * contentType 편의 메서드
         */
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        /**
         * Cookie 편의 메서드
         */
        //Set-Cookie: myCookie=good; Max-Age=600;
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        /**
         * Redirect 편의 메서드
         */
        //Status Code 302   -> Location으로 사이트 이동해서 조회
        //Location: /basic/hello-form.html

//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
