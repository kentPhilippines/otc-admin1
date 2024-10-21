import java.util.ArrayList;
import java.util.List;
/**
 * ListToArray
 */
public class ListToArray {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        /**
         * 数组转为数列
         */
        String[] array = list.toArray(new String[0]);
        System.out.println("数组内容: " + list.toString());
    }
}