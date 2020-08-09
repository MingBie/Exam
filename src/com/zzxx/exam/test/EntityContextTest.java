package com.zzxx.exam.test;

import com.zzxx.exam.entity.EntityContext;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.User;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntityContextTest {

    // 测试 加载用户信息方法(loadUsers)
    @Test
    public void Test01() {
        EntityContext ec = new EntityContext();
        //Map<Integer, User> usersMap = ec.getUsersMap();
        //System.out.println(usersMap);
/*        Set<Integer> ids = usersMap.keySet();
        for(int id : ids) {
            System.out.println(usersMap.get(id).getPassword());
        }*/
    }

    // 测试 加载题目方法(loadQuestions)
    @Test
    public void Test02() {
        EntityContext ec = new EntityContext();
        //Map<Integer, List<Question>> questionsMap = ec.getQuestionsMap();
        //System.out.println(questionsMap);
/*        Set<Integer> levels = questionsMap.keySet();
        for(int level : levels) {
            List<Question> questions = questionsMap.get(level);
            for(Question question : questions) {
                System.out.println(question);
            }
        }*/
    }

    // 测试通过level查找对应难度级别的题目 并随机抽取两道
    @Test
    public void Test03() {
        EntityContext ec = new EntityContext();
        List<Question> que = ec.findQuestionByLevel(1);
        System.out.println(que);
    }

    // 测试读取考试规则方法(loadRule)
    @Test
    public void Test04() {
        EntityContext ec = new EntityContext();
        String rules = ec.getRules();
        System.out.println(rules);
    }

    // 测试读取考试信息方法(loadExamInfo)
    @Test
    public void Test05() {
        EntityContext ec = new EntityContext();
        System.out.println(ec.getPaperTitle());
    }
}
