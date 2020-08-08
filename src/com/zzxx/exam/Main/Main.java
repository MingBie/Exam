package com.zzxx.exam.Main;

import com.zzxx.exam.controller.Controller;
import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.servicecontroller.ServiceController;
import com.zzxx.exam.ui.*;

// 主方法
public class Main {
    public static void main(String[] args) {
        EntityContext entityContext = new EntityContext();
        ServiceController serviceController = new ServiceController();
        Controller controller = new Controller();
        LoginFrame loginFrame = new LoginFrame();
        ExamFrame examFrame = new ExamFrame();
        MenuFrame menuFrame = new MenuFrame();
        MsgFrame msgFrame = new MsgFrame();
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        ResultFrame resultFrame = new ResultFrame();
        // 构建依赖关系
        serviceController.setEntityContext(entityContext);
        serviceController.setExamFrame(examFrame);
        controller.setServiceController(serviceController);
        controller.setExamFrame(examFrame);
        controller.setLoginFrame(loginFrame);
        controller.setMenuFrame(menuFrame);
        controller.setMsgFrame(msgFrame);
        controller.setWelcomeWindow(welcomeWindow);
        controller.setResultFrame(resultFrame);
        loginFrame.setController(controller);
        menuFrame.setController(controller);
        examFrame.setController(controller);
        // 主程序开始
        controller.startMain();
    }
}
