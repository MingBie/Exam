package com.zzxx.exam.ui;

import javax.swing.*;
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

    // 更新考试规则界面数据
    private JTextArea ta;
    public void updateTa(String msgs) {
        // 更新数据
        this.ta.setText(msgs);
    }
    private JScrollPane createContentPane() {
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
