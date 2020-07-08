package icu.jogeen.fishbook.service;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

import java.io.File;

/**
 * @Author jogeen
 * @Date 14:28 2020/6/24
 * @Description
 */
public class BookScannerBuilder {

    private static BookScanner bookScaner=null;
    private static PersistentState persistentState = PersistentState.getInstance();

    public static BookScanner builder(String bookPath){
        if(bookScaner==null){
            if(bookPath==null){
                bookPath=persistentState.getBookPathText();
            }
            if (doBuild(bookPath)) return null;
        }
        return bookScaner;
    }

    public static BookScanner rebuild(String bookPath){
        if (doBuild(bookPath)) return null;
        persistentState.setPageNum(1);
        return bookScaner;
    }

    private static boolean doBuild(String bookPath) {
        if (!checkPath(bookPath)) {
            return true;
        }
        NotificationGroup notificationGroup = new NotificationGroup("fishid", NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification("初始化书籍,可能需要几秒钟!", MessageType.INFO);
        Notifications.Bus.notify(notification);
        bookScaner = new TxtBookScanner(bookPath);
        return false;
    }


    public static BookScanner getBookScaner(){
        return bookScaner;
    }

    private static boolean checkPath(String bookPath) {
        File file = new File(bookPath);
        if(file.exists()) {
            return true;
        }
        return false;
    }

}
