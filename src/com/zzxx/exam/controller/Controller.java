package com.zzxx.exam.controller;

import com.zzxx.exam.entity.ExamInfo;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.QuestionInfo;
import com.zzxx.exam.entity.User;
import com.zzxx.exam.idorpwdexecption.IdOrPwdException;
import com.zzxx.exam.servicecontroller.ServiceController;
import com.zzxx.exam.ui.*;

import java.util.List;

// 控制器
public class Controller {
    private ExamFrame examFrame;
    private LoginFrame loginFrame;
    private MenuFrame menuFrame;
    private MsgFrame msgFrame;
    private WelcomeWindow welcomeWindow;
    private ResultFrame resultFrame;
    private ServiceController serviceController;

    // 主程序开始方法
    public void startMain() {
        // 显示登录界面
        loginFrame.setVisible(true);
    }

    // 登录的用户
    private User user;
    // 登录方法
    public void login() {
        // 获取输入的编号与密码
        String idField = loginFrame.getIdField().getText();
        String pwdField = loginFrame.getPwdField().getText();
        try {
            // 调用业务端登录
            user = serviceController.login(idField, pwdField);
            loginFrame.setVisible(false); // 关闭 登录界面
            //welcomeWindow.setVisible(true); // 显示 欢迎界面
            //Thread.sleep(2000);
            //welcomeWindow.setVisible(false); // 0.5秒后 关闭欢迎界面
            menuFrame.setVisible(true); // 显示 登录后主界面
            menuFrame.updateInfo(user.getName() + " 同学你好！"); // 登录后更新提示的用户信息

        } catch (IdOrPwdException e) {
            // 如果输入错误 显示 编号/密码输入错误
            loginFrame.updateMessage(e.getMessage());
        }
    }

    // 考试信息
    private ExamInfo examInfo;
    // 考试时间
    private int timeLimit;
    // 开始考试
    public void start() {
        questionIndex = 0;
        examInfo = serviceController.startExam(user); // 获得考试信息
        questionInfo = serviceController.getQuestionInfo(questionIndex); // 获得第一道考题
        menuFrame.setVisible(false); // 关闭 显示登录后主界面
        examFrame.setVisible(true); // 显示 考试界面
        examFrame.updateQuestionArea(questionInfo); // 更新考试题目
        examFrame.updateExamInfo(examInfo); // 更新考试信息
        timeLimit = examInfo.getTimeLimit(); // 获得考试的时间
        time(); // 更新时间
        timeCountDown(); // 倒计时
    }
    // 倒计时(线程)
    private void timeCountDown() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // 判断 时间是否为0
                while (Controller.this.timeLimit != 0) {
                    try {
                        Thread.sleep(1000); // 等待一秒
                        Controller.this.timeLimit --; // 时间减1
                        time(); // 更新时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                examFrame.setVisible(false); // 关闭 考试界面
                menuFrame.setVisible(true); // 打开 菜单界面
            }
        };
        thread.start();
        // 判断是否已交过卷 重新开始考试
        if (menuFrame.getResult()) {
            thread.stop(); // 关闭之前一次考试的 倒计时线程
        }
    }
    // 更新时间
    private void time() {
        int hour = timeLimit / 3600; // 小时
        int minute = (timeLimit - hour * 3600) / 60; // 分钟
        int second = timeLimit - hour * 3600 - minute * 60; // 秒
        examFrame.updateTime(hour, minute, second); // 更新时间
    }

    private int questionIndex = 0; // 题目下标
    private QuestionInfo questionInfo; // 包含用户答案选项的题目集合
    // 进入下一题
    public void next() {
        // 判断是否超过试卷题目总数
        if (questionIndex < 19) {
            this.questionInfo.setUserAnswers(examFrame.getUserAnswers()); // 保存(设置) 上一题的用户答案选项
            questionIndex++;
            questionInfo = serviceController.getQuestionInfo(questionIndex); // 进入下一题
            examFrame.updateQuestionArea(questionInfo); // 更新考试题目
        }
    }

    // 返回上一题
    public void prev() {
        if (questionIndex > 0) {
            this.questionInfo.setUserAnswers(examFrame.getUserAnswers()); // 保存(设置) 这一题的用户答案选项
            questionIndex--;
            questionInfo = serviceController.getQuestionInfo(questionIndex); // 返回上一题
            examFrame.updateQuestionArea(this.questionInfo); // 更新考试题目
        }
    }

    // 交卷
    public void send() {
        this.questionInfo.setUserAnswers(examFrame.getUserAnswers()); // 保存(设置) 交卷时这一题的用户答案选项
        menuFrame.setVisible(true); // 显示 显示登录后主界面
        examFrame.setVisible(false); // 关闭 考试规则界面
        menuFrame.upDateResult(); // 更新 交卷按钮的属性
        //menuFrame.updateStart(); // 更新考试按钮(只能考一次试)
    }

    // 显示分数
    public void result() {
        int score = 0;
        // 判断是否 参加过考试
        if (questionInfo != null) {
            for (int i = 0; i < 20; i++) {
                questionInfo = serviceController.getQuestionInfo(i); // 获取 包含用户答案的题目
                List<Integer> userAnswers = questionInfo.getUserAnswers(); // 用户答案
                List<Integer> answers = questionInfo.getQuestion().getAnswers(); // 题目答案
                // 判断是否相等
                if (answers.equals(userAnswers)) {
                    // 相等的话加上分数
                    score += questionInfo.getQuestion().getScore();
                }
            }
            resultFrame.updateScore(new String(user.getName() + "的成绩: " + score + ""));
        } else {
            resultFrame.updateScore(new String(user.getName() + " 未参加考试!"));
        }
        menuFrame.setVisible(false); // 关闭 显示登录后主界面
        resultFrame.setVisible(true); // 打开 分数页面
    }

    // 显示考试规则方法
    public void msg() {
        String msgs = serviceController.msg(); // 获得 考试规则
        menuFrame.setVisible(false); // 关闭 显示登录后主界面
        msgFrame.setVisible(true); // 显示 考试规则界面
        msgFrame.updateTa(msgs); // 更新 考试规则界面数据
    }

    // exit
    public void exitMain() {
        // 强制退出程序
        System.exit(0);
    }

    // 给主函数用于 依赖 调用
    public void setExamFrame(ExamFrame examFrame) {
        this.examFrame = examFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void setMenuFrame(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    public void setMsgFrame(MsgFrame msgFrame) {
        this.msgFrame = msgFrame;
    }

    public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        this.welcomeWindow = welcomeWindow;
    }

    public void setResultFrame(ResultFrame resultFrame) {
        this.resultFrame = resultFrame;
    }

    public void setServiceController(ServiceController serviceController) {
        this.serviceController = serviceController;
    }
}
