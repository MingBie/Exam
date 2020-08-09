package com.zzxx.exam.servicecontroller;

import com.zzxx.exam.entity.*;
import com.zzxx.exam.idorpwdexecption.IdOrPwdException;
import com.zzxx.exam.ui.ExamFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 业务模块
public class ServiceController {
    private EntityContext entityContext;
    private ExamFrame examFrame;

    // 登录
    public User login(String idField, String pwdField) throws IdOrPwdException {
        // 判断是否有输入编号和密码
        if (idField.equals("") || pwdField.equals("")) {
            throw new IdOrPwdException("编号/密码输入错误");
        }
        // 从模拟数据库中查找对应id的用户
        User user = entityContext.findUserById(Integer.valueOf(idField));
        // 1.获取输进来的编号和密码
        // 2.判断编号对应的用户名是否存在
        // 3.若存在判断密码是否正确，若正确跳转页面
        // 4.若不存在或密码错误，抛出输入错误异常
        if (user != null/*idField.equals("1000")*/) {
            if (user.getPassword().equals(pwdField)/*pwdField.equals("1234")*/) {
                return user;
            }
        }
        throw new IdOrPwdException("编号/密码输入错误");
    }

    // 开始考试
    public ExamInfo startExam(User user) {
        int questionNumber = Integer.valueOf(entityContext.getQuestionNumber()); // 题目总数
        int timeLimit = Integer.valueOf(entityContext.getTimeLimit()); // 考试时间
        String paperTitle = entityContext.getPaperTitle(); // 考试科目
        ExamInfo examInfo = new ExamInfo(paperTitle,user,timeLimit,questionNumber); // 创建考试信息对象
        createExamPaper(); // 生成一套试卷
        return examInfo;
    }
    // 定义一套考试试卷
    private List<QuestionInfo> examPaper;
    // 题目编号
    private int questionIndex;
    // 生成一套试卷(一共20题,每种难度级别各两题,且不重复)
    private void createExamPaper() {
        questionIndex = 0;
        examPaper = new ArrayList<QuestionInfo>(); // 创建试卷集合
        for(int i = Question.LEVEL1; i <= Question.LEVEL10; i++) {
            List<Question> questions = entityContext.findQuestionByLevel(i); // 获取相同难度级别的题目
            int count1 = -1;
            int count2 = -1;
            for(int j = 0; j < 2; j++) {
                count1 = (int)(Math.random() * questions.size());
                if (count2 == count1) {
                    j --; // 若相同重新生成
                } else {
                    // 创建包含用户答案选项的题目集合 (一开始答案选项为空)
                    QuestionInfo questionInfo = new QuestionInfo(questionIndex,questions.get(count1),null);
                    examPaper.add(questionInfo);
                    questionIndex ++;
                    count2 = count1;
                }
            }
        }
    }
    // 获得考试下标对应的题目
    public QuestionInfo getQuestionInfo(int questionIndex) {
        return this.examPaper.get(questionIndex);
    }

    // 获得考试成绩
    public String getResult(File file) {
        // 读取时把之前读取的 result清空
        String result = new String();
        StringBuilder sbResult = new StringBuilder(result);
        BufferedReader br = null;
        try {
            // 创建缓冲字符输入流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str;
            // 依次读取换行
            while ((str = br.readLine()) != null) {
                sbResult.append(str + "\n");
            }
            result = sbResult.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // 显示考试规则
    public String msg() {
        // 从模拟数据库中获得 考试规则
        return entityContext.getRules();
    }

    // 给主方法调用设置依赖
    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }

    public void setExamFrame(ExamFrame examFrame) {
        this.examFrame = examFrame;
    }
}
