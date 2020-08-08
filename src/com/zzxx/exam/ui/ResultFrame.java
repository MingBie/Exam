package com.zzxx.exam.ui;

import javax.swing.*;
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

    // 更新分数
    private JTextArea score;
    public void updateScore(String score) {
        this.score.setText(score);
    }
    private JScrollPane createContentPane() {
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
