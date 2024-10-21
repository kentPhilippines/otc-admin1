import java.util.Arrays;
import java.util.List;

/**
 * ArrayToList
 */
public class ArrayToList {
    /**
     * main方法
     * @param args
     */
    public static void main(String[] args) {
        String[] array = {"A", "B", "C", "D"};
        /**
         * 数组转为数列
         */
        List<String> list = Arrays.asList(array);
        System.out.println("列表内容： " + list);
    }
}


