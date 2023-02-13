package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ControllerV1 Interface를 이용한 FrontController 도입!
 * "/front-controller/v1"를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다
 */
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerMap = new HashMap<>(); //<매핑 URL, 호출될 컨트롤러>

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1()); // new-form.jsp 실행되면 new MemberFormControllerV1()이 실행됨
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1()); // save.jsp 실행되면 new MemberSaveControllerV1()이 실행됨
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());


    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();// "/front-controller/V1/members/new-form" 경로 얻을 수 있음

        ControllerV1 controller = controllerMap.get(requestURI); //new MemberFormControllerV1() 객체 인스턴스가 반환됨
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response);

    }
}
