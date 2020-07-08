package icu.jogeen.fishbook.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author jogeen
 * @Date 14:28 2020/6/24
 * @Description
 */
public class TxtBookScanner implements BookScanner {

    private String filePath;
    private RandomAccessFile ra = null;
    private long[] offsetIndex;
    private long totalLines;



    File file = null;

    public TxtBookScanner(String filePath) {
        file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        Path path = Paths.get(file.getPath());
        try {
            Stream<String> countStream = Files.lines(path, Charset.forName("utf-8"));
            totalLines = countStream.count();
            offsetIndex = new long[Integer.parseInt(totalLines + "")];

            this.filePath = filePath;
            ra = new RandomAccessFile(file, "r");
            int i=1;
            while ((ra.readLine()) != null && i < totalLines) {
                offsetIndex[i++] = ra.getFilePointer();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String bookName() {
        return file.getName();
    }

    @Override
    public long getBookSize() {
        return file.length();
    }

    @Override
    public long getTotalLines() {
        return totalLines;

    }



    @Override
    public List<String> getContentForPage(int pageNum, int pageSize) {
        List<String> list=new ArrayList<String>(pageSize);
        try {
            int index = (pageNum - 1) * pageSize;
            ra.seek(offsetIndex[index]);
            if(index+pageSize>totalLines){
                pageSize=((Long)(totalLines-index)).intValue();
            }
            for (int i = 0; i < pageSize; i++) {
                list.add(new String(ra.readLine().getBytes("ISO-8859-1"), "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        TxtBookScanner txtBookScaner = new TxtBookScanner("C:\\Users\\Administrator\\Downloads\\十日谈.txt");

        System.out.println(txtBookScaner.getBookSize());
        System.out.println(txtBookScaner.bookName());
        System.out.println(txtBookScaner.getTotalLines());
        System.out.println(txtBookScaner.getContentForPage(2, 10));
        System.out.println("-----");
        System.out.println(txtBookScaner.getContentForPage(3, 10));
        System.out.println("-----");
        System.out.println(txtBookScaner.getContentForPage(1, 10));

    }
}
