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
 * 登录界面 是一个具体窗口框
 */
public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public LoginFrame() {
        init();
    }

    /**
     * 初始化界面组件和布局的
     */
    private void init() {
        this.setTitle("登录系统");
        JPanel contentPane = createContentPane();
        this.setContentPane(contentPane);
        // 必须先设大小后居中
        setSize(300, 220);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // 关闭窗口 强行退出程序
                System.exit(0);
            }
        });
    }

    private JPanel createContentPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.add(BorderLayout.NORTH, new JLabel("登录考试系统", JLabel.CENTER));
        p.add(BorderLayout.CENTER, createCenterPane());
        p.add(BorderLayout.SOUTH, createBtnPane());
        return p;
    }

    // 创建控制台 给主方法调用设置 依赖
    private Controller controller;
    public void setController(Controller controller) {
        this.controller = controller;
    }
    private JPanel createBtnPane() {
        JPanel p = new JPanel(new FlowLayout());
        JButton login = new JButton("Login");
        JButton cancel = new JButton("Cancel");
        p.add(login);
        p.add(cancel);

        getRootPane().setDefaultButton(login);
        // 登录
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 调用控制台登录
                controller.login();
            }
        });
        // 关闭
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口 强行退出程序
                System.exit(0);
            }
        });
        return p;
    }

    // 提示的信息 一开为空
    private JLabel message;
    // 输入错误后更新提示信息
    public void updateMessage(String message) {
        this.message.setText(message);
    }
    private JPanel createCenterPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(8, 0, 0, 0));
        p.add(BorderLayout.NORTH, createIdPwdPane());
        message = new JLabel("", JLabel.CENTER);
        p.add(BorderLayout.SOUTH, message);
        return p;
    }

    private JPanel createIdPwdPane() {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 6));
        p.add(createIdPane());
        p.add(createPwdPane());
        return p;
    }

    // 输入的编号
    private JTextField idField;
    // 给控制台调用 获得编号
    public JTextField getIdField() {
        return idField;
    }
    // 给控制台调用 重新登入时清空编号
    public void updataIdField(String idField) {
        this.idField.setText(idField);
    }
    private JPanel createIdPane() {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(BorderLayout.WEST, new JLabel("编号:"));
        JTextField idField = new JTextField();
        this.idField = idField;
        p.add(BorderLayout.CENTER, idField);

        return p;
    }

    /**
     * 简单工厂方法, 封装的复杂对象的创建过程, 返回一个对象实例
     */
    // 输入的密码
    private JPasswordField pwdField;
    // 给控制台调用 获得密码
    public JPasswordField getPwdField() {
        return pwdField;
    }

    public void updataPwdField(String pwdField) {
        this.pwdField.setText(pwdField);
    }

    private JPanel createPwdPane() {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(BorderLayout.WEST, new JLabel("密码:"));
        JPasswordField pwdField = new JPasswordField();
        this.pwdField = pwdField;
        pwdField.enableInputMethods(true);
        p.add(BorderLayout.CENTER, pwdField);
        return p;
    }
}
