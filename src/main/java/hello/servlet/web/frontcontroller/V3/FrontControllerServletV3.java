package hello.servlet.web.frontcontroller.V3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 서블릿 종속성 제거, 뷰 이름 중복 제거(논리 이름 사용), model 추가
 * 특징: controller는 단순해졌으나 FrontController 일이 많아짐!
 */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3()); // new-form.jsp 실행되면 new MemberFormControllerV2()이 실행됨
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3()); // save.jsp 실행되면 new MemberSaveControllerV2()이 실행됨
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");
        //1. 매핑 정보(컨트롤러 조회)
        String requestURI = request.getRequestURI();// "/front-controller/V3/members/new-form" 경로 얻을 수 있음
        //2. 컨트롤러 호출
        ControllerV3 controller = controllerMap.get(requestURI); //new MemberFormControllerV3() 객체 인스턴스가 반환됨
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //3. ModelView 반환: paramMap
        Map<String, String> paramMap = createParamMap(request); //request 파라미터 정보
        ModelView mv = controller.process(paramMap);
        //4. viewResolver 호출
        String viewName = mv.getViewName();// 논리 이름: new-form
        //5. MyView 반환: 논리 위치 -> 물리 위치
        MyView view = viewResolver(viewName);
        //6. render(model) 호출: HTML 응답
        view.render(mv.getModel(), request, response); //ModelView에서 model 넘김
    }

    //논리 이름 -> 물리 이름
    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    //HttpServletRequest에서 파라미터 정보를 꺼내서 Map에 저장 & 반환
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
