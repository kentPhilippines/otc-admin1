public class Student {
    // 1.局部变量：在方法、构造函数或块内部声明的变量。
    public void run() {
        String weight = "100斤"; // 局部变量weight，必须有初始值。
        // 因为实例变量age没赋值，所以默认是0
        System.out.println(name + "现在" + age + "岁"); // 小明现在0岁
        System.out.println(name + weight + "，跑得很快"); // 小明100斤，跑得很快
    }

    // 2.实例变量：在类中声明，但在方法、构造函数或块之外。
    public String name = "小明"; // 公有实例变量name对子类可见
    private int age; // 私有实例变量age，仅在该类可见，不赋值，默认是0

    // main方法，程序入口
    public static void main(String[] args) {
        Student.onLineNumber++; // 22
        System.out.println(onLineNumber);
        Student student = new Student();
        student.run();
        student.eat("方便面");
        student.study();
    }

    // 3.类变量：在类中用 static 关键字声明的变量，它们属于类而不是实例。
    public static int onLineNumber = 21;
    private String major = "计算机科学";

    // 4.参数变量：是方法或构造函数声明中的变量
    public void eat(String food) { // 参数变量food
        System.out.println(name + "喜欢吃" + food); // 小明喜欢吃方便面
    }

    // 自定义方法
    public void study() {
        System.out.println(name + "正在学习" + major);
    }
}
