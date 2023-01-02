package hello.servlet.web.frontcontroller.V5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.V5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    //V4: private Map<String, ControllerV4> controllerMap = new HashMap<>();
    /*private final Map<String, Object> handlerMappingMap = new HashMap<>();*/
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    private final InitHandlerMappingMap initHandlerMappingMap = new InitHandlerMappingMap();


    public FrontControllerServletV5() {
        initHandlerMappingMap.initHandlerMappingMap();
 
        initHandlerAdapters();
    }

/*    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3()); // new-form.jsp 실행되면 new MemberFormControllerV3()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3()); // save.jsp 실행되면 new MemberSaveControllerV3()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4()); // new-form.jsp 실행되면 new MemberFormControllerV4()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4()); // save.jsp 실행되면 new MemberSaveControllerV4()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }*/

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.핸들러 조회(매핑 정보) handler = MemberFormControllerV4()
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //2.핸들러를 처리할 수 있는 핸들러 어댑터 조회  adapter = ControllerV4HandlerAdapter()
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        //3. handle(handler)로 핸들러 어댑터 호출
        ModelView mv = adapter.handle(request, response, handler);

        //6. viewResolver 호출
        String viewName = mv.getViewName();// 논리 이름: new-form
        //7. MyView 반환: 논리 위치 -> 물리 위치
        MyView view = viewResolver(viewName);
        //8. render(model) 호출: HTML 응답
        view.render(mv.getModel(), request, response); //ModelView에서 model 넘김
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return initHandlerMappingMap.initHandlerMappingMap().get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        //MemberFormControllerV4()
        for (MyHandlerAdapter adapter : handlerAdapters) { //핸들러 어댑터 목록
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }


    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
