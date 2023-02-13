package hello.servlet.web.frontcontroller.V3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

/**
 * v2: View
 * v3: ModelView, 장점: HttpServlet 기술 빠짐
 */
public interface ControllerV3 {
    ModelView process(Map<String, String> paraMap);
}
