import java.util.Arrays;
import java.util.List;

public class ArrayToList {
    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D"};
        List<String> list = Arrays.asList(array);
        System.out.println("列表内容： " + list);
    }
}
