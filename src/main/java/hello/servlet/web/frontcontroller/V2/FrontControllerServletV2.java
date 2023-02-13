package hello.servlet.web.frontcontroller.V2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.V2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.V2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * view 분리
 */
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2()); // new-form.jsp 실행되면 new MemberFormControllerV2()이 실행됨
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2()); // save.jsp 실행되면 new MemberSaveControllerV2()이 실행됨
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());


    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        String requestURI = request.getRequestURI();// "/front-controller/V2/members/new-form" 경로 얻을 수 있음

        ControllerV2 controller = controllerMap.get(requestURI); //new MemberFormControllerV2() 객체 인스턴스가 반환됨
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
