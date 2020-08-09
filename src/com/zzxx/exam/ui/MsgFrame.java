package com.zzxx.exam.ui;

import com.zzxx.exam.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主菜单界面
 */
public class MsgFrame extends JFrame {

    public MsgFrame() {
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
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.add(BorderLayout.NORTH, new JLabel("考试规则", JLabel.CENTER));
        p.add(BorderLayout.CENTER, createJScrollPane());
        p.add(BorderLayout.SOUTH, createBtnPane());
        return p;
    }

    // 创建控制台 给主方法调用设置 依赖
    private Controller controller;
    public void setController(Controller controller) {
        this.controller = controller;
    }
    // 创建按钮
    private JPanel createBtnPane() {
        JPanel p = new JPanel(new FlowLayout());
        JButton returnLogin = new JButton("返回登录界面");
        JButton returnCancel = new JButton("返回主菜单");
        p.add(returnLogin);
        p.add(returnCancel);

        getRootPane().setDefaultButton(returnLogin);
        // 返回登录界面
        returnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用控制台 返回登录界面
                controller.msgReturnLogin();
            }
        });
        // 返回主菜单界面
        returnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用控制台 返回主菜单界面
                controller.msgReturnMenu();
            }
        });
        return p;
    }

    // 更新考试规则界面数据
    private JTextArea ta;
    public void updateTa(String msgs) {
        // 更新数据
        this.ta.setText(msgs);
    }
    private JScrollPane createJScrollPane() {
        JScrollPane jsp = new JScrollPane();
        ta = new JTextArea();
        jsp.add(ta);
        jsp.getViewport().add(ta);
        return jsp;
    }

    public void showMsg(String file) {
        ta.setText(file);
        ta.setLineWrap(true);// 允许折行显示
        ta.setEditable(false);// 不能够编辑内容
    }
}
