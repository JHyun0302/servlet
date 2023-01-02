package hello.servlet.web.frontcontroller.V4;

import java.util.Map;

public interface ControllerV4 {
    /**
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model); // value: Object(아무거나 넣을 수 있음)
    //V3에서는 ModelView로 반환
}
