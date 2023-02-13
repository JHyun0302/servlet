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

/**
 * v3, v4 모든 버전 사용하기 ->  어댑터 추가
 * 핸들러: 더 넒은 범위의 컨트롤러
 */
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    //V4: private Map<String, ControllerV4> controllerMap = new HashMap<>();
    //V5(어탭터 적용): private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>(); //여러 핸들러 중 찾아서 쓰기
    private final InitHandlerMappingMap initHandlerMappingMap = new InitHandlerMappingMap();


    public FrontControllerServletV5() {
        initHandlerMappingMap.initHandlerMappingMap();

        initHandlerAdapters();
    }

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
