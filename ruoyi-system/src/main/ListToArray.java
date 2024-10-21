import java.util.ArrayList;
import java.util.List;
public class ListToArray {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        // 列表转为数组
        String[] array = list.toArray(new String[0]);
        System.out.println("数组内容: " + list.toString());
    }
}