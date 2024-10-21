public class TestArray {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // double类型的数组
        double[] scores = new double[]{99.5, 88.0, 75.5};
        double[] scores1 = {99.5, 88.0, 75.5}; // 简化写法

        // int类型的数组
        int[] ages = new int[]{12, 24, 36};
        int[] ages1 = {12, 24, 36}; // 简化写法

        int size = 5; // 设置数组大小是10
        String[] names = new String[size]; // 定义数组
        names[0] = "孙悟空";
        names[1] = "贝吉塔";
        names[2] = "弗利萨";
        names[3] = "沙鲁";
        names[4] = "魔人布欧";
        // 编译不报错，运行报错，因为数组大小是5，这是数组里第6个元素，数组越界ArrayIndexOutOfBoundsException
        // names[5] = "布尔玛";
        // 数组索引从 0 开始，所以索引值从 0 到 arrayRefVar.length-1
        for (int i = 0; i < names.length; i++) {
            System.out.print("索引：" + i + names[i] + " ");
        }

        System.out.println();

        // 数组变量名存储的是数组在内存种的地址，数组是引用类型
        // [ 指的是 数组
        // D 指的是 数据类型double
        // @ 指的是 在哪里
        System.out.println(scores); // [D@776ec8df,指的是数组的十六进制地址
    }
}
