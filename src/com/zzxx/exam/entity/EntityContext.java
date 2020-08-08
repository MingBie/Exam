package com.zzxx.exam.entity;

import com.zzxx.exam.util.Config;

import java.io.*;
import java.net.UnknownServiceException;
import java.util.*;

/**
 * 实体数据管理, 用来读取数据文件放到内存集合当中
 */
// 模拟数据库
public class EntityContext {
    // 读取的用户名集合   key-id value-User
    private Map<Integer, User> usersMap = new HashMap<Integer, User>();
    // 读取的题目集合   key-题目级别level value-同级别的题目集合
    private Map<Integer, List<Question>> questionsMap = new HashMap<Integer, List<Question>>();
    // 读取的考试规则集合
    private String rules = new String();
    // 读取的考试信息
    private ExamInfo examInfo = new ExamInfo();

    // 构造方法(加载用户信息 加载题目)
    public EntityContext() {
        this.loadUsers("src/com/zzxx/exam/util/user.txt");
        this.loadQuestions("src/com/zzxx/exam/util/corejava.txt");
        this.loadRule("src/com/zzxx/exam/util/rule.txt");
        //this.loadExamInfo("src/com/zzxx/exam/util/config.properties");
        this.loadExamInfo("config.properties");
    }

    // 读取(加载)用户信息
    private void loadUsers(String filename) {
        BufferedReader br = null;
        try {
            // 创建缓冲字符输入流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String str;
            // 依次读取用户名信息
            for (int i = 0; i < 8; i++) {
                str = br.readLine();
                // 判断是否为 # 开头 或是否无内容
                if ((!str.startsWith("#")) && (!str.equals(""))) {
                    // 用 : 分割用户名信息
                    String[] ss = str.split("[:]");
                    // 读取id
                    Integer id = Integer.valueOf(ss[0]);
                    // 读取名字
                    String name = ss[1];
                    // 读取密码
                    String password = ss[2];
                    // 读取手机号
                    String phone = ss[3];
                    // 读取邮箱
                    String email = ss[4];
                    // 创建用户名对象
                    User user = new User(name, id, password);
                    // 将用户名对象加到用户名集合中
                    usersMap.put(id, user);
                }
            }
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
    }

    // 读取(加载)题目
    private void loadQuestions(String filename) {
        BufferedReader br = null;
        try {
            // 创建缓冲字符输入流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            int id = 0; // 编号
            String title = null; // 题目问题
            List<String> options = null; // 题目答案选项   // 注意：因为是引用类型，所以每读一道题都需要新建一个集合，不然用的都是同一个集合
            List<Integer> answers = null; // 答案
            int score = 0; // 分数
            int level = 0; // 难度级别
            int type = 0; // 单选/多选
            Question question = null; // 题目
            // 依次读取题目文件
            String str;
            int count = 0; // 用来计数
            while ((str = br.readLine()) != null) {
                count ++;
                // 判断读取的是6行中的第几行
                if ((count % 6) == 1) {
                    // 开始一道新的题目 新建一个答案集合
                    answers = new ArrayList<Integer>();
                    // 第一行
                    // 用 , 分割
                    String[] ss = str.split(",");
                    // 用 =/ 分割
                    String[] sAnswer = ss[0].split("[=/]");
                    // 获取答案
                    for (int i = 1; i < sAnswer.length; i++) {
                        answers.add(Integer.valueOf(sAnswer[i]) - 1); // 用户答案从0开始
                    }
                    // 获取分数
                    score = Integer.valueOf(ss[1].split("=")[1]);
                    // 获取难度级别
                    level = Integer.valueOf(ss[2].split("=")[1]);
                    //System.out.println(level);
                } else if ((count % 6) == 2) {
                    // 第2行
                    // 获取题目问题
                    title = str;
                } else {
                    if (count % 6 == 3) {
                        // 开始一道新的题目 新建一个答案选项集合
                        options = new ArrayList<String>();
                    }
                    // 第3-6行
                    // 获取题目答案选项
                    options.add(str);
                }
                // 判断是单选还是多选
                if (answers.size() == 1) {
                    type = Question.SINGLE_SELECTION; // 单选
                } else {
                    type = Question.MULTI_SELECTION; // 多选
                }
                // 判断是否一整道题目读取完
                if ((count % 6) == 0) {
                    // 创建题目对象
                    id ++;
                    question = new Question(id, title, options, answers, score, level, type);
                    //System.out.println(question);
                    List<Question> questionsList = questionsMap.get(level);
                    // 判断对应难度级别的题目集合是否存在
                    if (questionsList == null) {
                        questionsList = new ArrayList<Question>();
                    }
                    // 把题目加到对应难度级别的题目集合中
                    questionsList.add(question);
                    // 加到Map中
                    questionsMap.put(level, questionsList);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 读取(加载)考试规则
    private void loadRule(String filename) {
        StringBuilder sbRules = new StringBuilder(rules);
        BufferedReader br = null;
        try {
            // 创建缓冲字符输入流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String str;
            // 依次读取考试规则文件
            while ((str = br.readLine()) != null) {
                // 依次换行
                sbRules.append(str + "\n");
            }
            // StringBuilder -> String
            rules = sbRules.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String paperTitle; // 考试科目
    private String timeLimit; // 考试时间
    private String questionNumber; // 考试题目数量
    public String getPaperTitle() {
        return paperTitle;
    }
    public String getTimeLimit() {
        return timeLimit;
    }
    public String getQuestionNumber() {
        return questionNumber;
    }
    // 读取考试信息
    private void loadExamInfo(String filename) {
/*        // 创建属性集
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(filename));
            paperTitle = new String(pro.getProperty("PaperTitle").getBytes("ISO8859-1"),"UTF-8"); // 读取考试科目
            timeLimit = pro.getProperty("TimeLimit"); // 读取考试时间
            questionNumber = pro.getProperty("QuestionNumber"); // 读取考试题目的数量
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // 创建属性集对象
        Config config = new Config(filename);
        try {
            // 考试题目的数量
            questionNumber = new String(config.getInt("QuestionNumber") + "");
            // 考试科目
            paperTitle = new String(config.getString("PaperTitle").getBytes("ISO8859-1"), "UTF-8");
            // 考试时间
            timeLimit = config.getString("TimeLimit");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 查找对应id的User用户
    public User findUserById(int id) {
        return usersMap.get(id);
    }
    // 查找对应level的题目集合
    public List<Question> findQuestionByLevel(int level) {
        // 对应难度级别的题目集合
        List<Question> questions = questionsMap.get(level);
        return questions;
    }
    // 获取考试规则
    public String getRules() {
        return rules;
    }
}
