package hello.servlet.web.frontcontroller.V5;

import hello.servlet.web.frontcontroller.V3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.V4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.V4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.V4.controller.MemberSaveControllerV4;

import java.util.HashMap;
import java.util.Map;

public class InitHandlerMappingMap {
    private final Map<String, Object> handlerMappingMap = new HashMap<>();

    public Map<String, Object> initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3()); // new-form.jsp 실행되면 new MemberFormControllerV3()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3()); // save.jsp 실행되면 new MemberSaveControllerV3()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4()); // new-form.jsp 실행되면 new MemberFormControllerV4()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4()); // save.jsp 실행되면 new MemberSaveControllerV4()이 실행됨
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
        return handlerMappingMap;
    }
}
