package icu.jogeen.fishbook.ui;

import icu.jogeen.fishbook.service.PersistentState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @Author jogeen
 * @Date 14:28 2020/6/24
 * @Description
 */
public class SettingUI {
    public JPanel settingPanel;
    public JTextField tfBookPath;
    public JButton btnChooseBook;
    public JTextField tfPageSize;

    public SettingUI() {
        btnChooseBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showOpenDialog(settingPanel);
                File file = fileChooser.getSelectedFile();
                if (tfBookPath != null) {
                    tfBookPath.setText( file.getPath());
                }
            }
        });
    }

    public void init(PersistentState persistentState){
        String bookPathText = persistentState.getBookPathText();
        tfBookPath.setText(bookPathText);
    }

}
