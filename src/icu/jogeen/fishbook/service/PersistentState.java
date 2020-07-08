package icu.jogeen.fishbook.service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
/**
 * @Author jogeen
 * @Date 14:28 2020/6/24
 * @Description
 */

@State(
        name = "PersistentState",
        storages = {@Storage(
                value = "fish-book.xml"
        )}
)
public class PersistentState implements PersistentStateComponent<Element> {

    private static PersistentState persistentState;

    private String bookPathText;

    private Integer pageNum=1;

    private Integer pageSize=10;

    public PersistentState() {
    }

    public static PersistentState getInstance() {
        if (persistentState == null) {
            persistentState = ServiceManager.getService(PersistentState.class);
        }

        return persistentState;
    }

    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("PersistentState");
        element.setAttribute("bookPathText",bookPathText);
        element.setAttribute("pageNum",pageNum+"");
        element.setAttribute("pageSize",pageSize+"");
        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        this.bookPathText=element.getAttributeValue("bookPathText");
        String pageNumStr = element.getAttributeValue("pageNum");
        this.pageNum=pageNumStr==null?pageNum:Integer.valueOf(pageNumStr);

        String pageSizeStr = element.getAttributeValue("pageSize");
        this.pageSize=pageSizeStr==null?pageSize:Integer.valueOf(pageSizeStr);

    }

    public String getBookPathText() {
        return bookPathText;
    }

    public PersistentState setBookPathText(String bookPathText) {
        this.bookPathText = bookPathText;
        return this;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public PersistentState setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PersistentState setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
