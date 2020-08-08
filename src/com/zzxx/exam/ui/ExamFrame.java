package com.zzxx.exam.ui;

import com.zzxx.exam.controller.Controller;
import com.zzxx.exam.entity.ExamInfo;
import com.zzxx.exam.entity.Question;
import com.zzxx.exam.entity.QuestionInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ExamFrame extends JFrame {
    // 选项集合, 方便答案读取的处理
    private Option[] options = new Option[4];

    public ExamFrame() {
        init();
    }

    private void init() {
        setTitle("指针信息在线测评");
        setSize(600, 380);
        setContentPane(createContentPane());
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {// 正在关闭的时候
                // 点击关闭,直接强制退出程序
                System.exit(0);
            }
        });
    }

    private JPanel createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(6, 6, 6, 6));
        ImageIcon icon = new ImageIcon(getClass().getResource("pic/exam_title.png"));

        pane.add(BorderLayout.NORTH, new JLabel(icon));

        pane.add(BorderLayout.CENTER, createCenterPane());

        pane.add(BorderLayout.SOUTH, createToolsPane());

        return pane;
    }
    // 考试信息
    private JLabel examInfo;
    // 更新考试信息
    public void updateExamInfo(ExamInfo examInfo) {
        String name = examInfo.getUser().getName();
        String title = examInfo.getTitle();
        int timeLimit = examInfo.getTimeLimit();
        this.examInfo.setText("姓名:" + name + "   考试:" + title + "   考试时间:" + timeLimit + "秒");
    }
    private JPanel createCenterPane() {
        JPanel pane = new JPanel(new BorderLayout()); // 边框布局
        // 注意!
        JLabel examInfo = new JLabel("姓名:XXX 考试:XXX 考试时间:XXX", JLabel.CENTER);
        this.examInfo = examInfo;
        pane.add(BorderLayout.NORTH, examInfo);

        pane.add(BorderLayout.CENTER, createQuestionPane());
        pane.add(BorderLayout.SOUTH, createOptionsPane());
        return pane;
    }

    // 获得用户答案选项
    private JPanel pane;
    public List<Integer> getUserAnswers() {
        // 创建答案集合
        List<Integer> userAnswers = new ArrayList<Integer>();
        for (int i = 0; i < options.length; i++) {
            // 判断是否勾选
            if (options[i].isSelected()) {
                // 若勾选 把 按钮答案选项的值 加到用户答案集合中
                userAnswers.add(options[i].value);
            }
        }
        return userAnswers;
    }
    private JPanel createOptionsPane() {
        JPanel pane = new JPanel(/*new FlowLayout()*/); // 默认流水布局
        Option a = new Option(0, "A");
        Option b = new Option(1, "B");
        Option c = new Option(2, "C");
        Option d = new Option(3, "D");
        options[0] = a;
        options[1] = b;
        options[2] = c;
        options[3] = d;
        pane.add(a);
        pane.add(b);
        pane.add(c);
        pane.add(d);
        return pane;
    }

    // 更新 考试界面(更新题目和用户答案选项)
    private JTextArea questionArea; // 题目
    private JLabel questionCount; // 编号
    public void updateQuestionArea(QuestionInfo questionInfo) {
        // 更新题目
        this.questionArea.setText(questionInfo.getQuestion().toString());
        this.questionCount.setText("题目:20 的 " + (questionInfo.getQuestionIndex() + 1) + "题");
        // 用户答案集合
        List<Integer> userAnswers = questionInfo.getUserAnswers();
        // 先把 按钮答案都清空
        for(int i = 0; i < options.length; i++) {
            options[i].setSelected(false);
        }
        // 判断用户答案并勾选
        if (userAnswers != null) {
            for(int answers : userAnswers) {
                // 下标从0开始
                options[answers].setSelected(true);
            }
        }
    }
    private JScrollPane createQuestionPane() {
        JScrollPane pane = new JScrollPane();
        pane.setBorder(new TitledBorder("题目"));// 标题框

        // 注意!
        questionArea = new JTextArea();
        questionArea.setText("问题\nA.\nB.");
        questionArea.setLineWrap(true);// 允许折行显示
        questionArea.setEditable(false);// 不能够编辑内容
        // JTextArea 必须放到 JScrollPane 的视图区域中(Viewport)
        pane.getViewport().add(questionArea);
        return pane;
    }

    // 更新时间
    private JLabel timer;
    public void updateTime(long h, long m, long s) {
        String time = h + ":" + m + ":" + s;
        if (m < 5) {
            timer.setForeground(new Color(0xC85848));
        } else {
            timer.setForeground(Color.blue);
        }
        timer.setText("剩余时间:  " + time);
    }
    private JPanel createToolsPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(0, 10, 0, 10));
        // 注意!
        questionCount = new JLabel("题目:20 的 1题"); // JLable 标签：用于短文本或图片显示

        timer = new JLabel("剩余时间:222秒");

        pane.add(BorderLayout.WEST, questionCount);
        pane.add(BorderLayout.EAST, timer);
        pane.add(BorderLayout.CENTER, createBtnPane());
        return pane;
    }
    // 控制台
    private Controller controller;
    public void setController(Controller controller) {
        this.controller = controller;
    }
    private JPanel createBtnPane() {
        JPanel pane = new JPanel(new FlowLayout()); // 流水布局
        prev = new JButton("上一题");
        next = new JButton("下一题");
        JButton send = new JButton("交卷");
        // 依次添加按钮
        pane.add(prev);
        pane.add(next);
        pane.add(send);

        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 进入上一题
                controller.prev();
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 进入下一题
                controller.next();
            }
        });

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 交卷
                controller.send();
            }
        });

        return pane;
    }

    /**
     * 使用内部类扩展了 JCheckBox 增加了val 属性, 代表答案值
     */
    class Option extends JCheckBox {
        int value;

        public Option(int val, String txt) {
            super(txt);
            this.value = val;
        }
    }

    private JButton next;

    private JButton prev;

}