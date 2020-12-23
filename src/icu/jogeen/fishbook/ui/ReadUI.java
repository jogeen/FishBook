package icu.jogeen.fishbook.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.wm.ToolWindow;
import icu.jogeen.fishbook.service.BookScanner;
import icu.jogeen.fishbook.service.BookScannerBuilder;
import icu.jogeen.fishbook.service.PersistentState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * @Author jogeen
 * @Date 14:28 2020/6/24
 * @Description
 */
public class ReadUI {

    private JPanel contentPanel;
    private JButton btnPre;
    private JButton btnNext;
    private JTextField tfPageNum;
    private JButton btnJump;
    private JButton btnFirst;
    private JButton btnLast;
    private JTextPane txContetnt;
    private JLabel labTotalPages;
    private JLabel labBookName;
    private Long totalPage;
    private PersistentState persistentState = PersistentState.getInstance();

    private void initBookScanner() {
        BookScanner scanner = BookScannerBuilder.getBookScaner();
        if (scanner == null) {
            scanner = BookScannerBuilder.getBookScaner();
            if (scanner == null) {
                MessageDialogBuilder.yesNo("操作结果", "请先配置图书路径").show();
                return;
            }
        }
        totalPage = scanner.getTotalLines() % persistentState.getPageSize() == 0 ? scanner.getTotalLines() / persistentState.getPageSize() : scanner.getTotalLines() / persistentState.getPageSize() + 1;
        labTotalPages.setText(""+totalPage);
        labBookName.setText(scanner.bookName());
    }

    public ReadUI(Project project, ToolWindow toolWindow) {

        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnPage(1);

            }
        });
        btnPre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnPage(persistentState.getPageNum() - 1);
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnPage(persistentState.getPageNum() + 1);
            }
        });
        btnLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnPage(totalPage.intValue());
            }
        });
        btnJump.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tfPageNum.getText();
                int pageNnum = Integer.parseInt(text);
                turnPage(pageNnum);
            }
        });
        contentPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                System.out.println(keyCode);
            }
        });
    }

    public void turnPage(int i) {
        initBookScanner();
        if(i<0||i>totalPage){
            return;
        }
        persistentState.setPageNum(i);
        List<String> contentForPage = BookScannerBuilder.getBookScaner().getContentForPage(persistentState.getPageNum(), persistentState.getPageSize());
        txContetnt.setText("");
        StringBuilder sb = new StringBuilder();
        contentForPage.forEach(s -> {
            sb.append(s + "\r\n");
            txContetnt.setText(sb.toString());
        });
        tfPageNum.setText(i+"");
    }

    public JPanel getJcontent() {
        return contentPanel;
    }

}
