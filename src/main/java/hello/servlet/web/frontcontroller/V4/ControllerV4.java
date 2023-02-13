package hello.servlet.web.frontcontroller.V4;

import java.util.Map;

/**
 * v4: 실용성 증진(return 값: String)
 * V3: ModelView로 반환
 */
public interface ControllerV4 {
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
