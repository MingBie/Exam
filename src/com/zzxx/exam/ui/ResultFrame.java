package com.zzxx.exam.ui;

import com.zzxx.exam.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ResultFrame extends JFrame {
    public ResultFrame() {
        init();
    }

    private void init() {
        setSize(600, 400);
        setContentPane(createContentPane());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // 关闭窗口,强行关闭程序
                System.exit(0);
            }
        });
    }

    private JPanel createContentPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.add(BorderLayout.NORTH, new JLabel("考试成绩查询", JLabel.CENTER));
        p.add(BorderLayout.CENTER, createJScrollPane());
        p.add(BorderLayout.SOUTH, createBtnPane());
        return p;
    }

    // 控制台
    Controller controller;
    // 给主方法调用设置 依赖
    public void setController(Controller controller) {
        this.controller = controller;
    }
    // 创建按钮
    private JPanel createBtnPane() {
        JPanel p = new JPanel(new FlowLayout());
        JButton returnLogin = new JButton("返回登录界面");
        JButton returnMenu = new JButton("返回主菜单");
        p.add(returnLogin);
        p.add(returnMenu);

        getRootPane().setDefaultButton(returnLogin);
        // 返回登录界面
        returnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用控制台 返回登录
                controller.resultReturnLogin();
            }
        });
        // 返回主菜单界面
        returnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用控制台 返回主菜单界面
                controller.resultReturnMenu();
            }
        });
        return p;
    }

    // 更新分数
    private JTextArea score;
    public void updateScore(String score) {
        this.score.setText(score);
    }
    private JScrollPane createJScrollPane() {
        JScrollPane jsp = new JScrollPane();
        score = new JTextArea();
        jsp.add(score);
        jsp.getViewport().add(score);
        return jsp;
    }

    public void showResult(String file) {
        score.setText(file);
        score.setLineWrap(true);// 允许折行显示
        score.setEditable(false);// 不能够编辑内容
    }
}
