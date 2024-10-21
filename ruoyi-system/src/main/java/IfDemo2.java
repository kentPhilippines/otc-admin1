public class IfDemo2 {
    public static void main(String[] args) {
        // 嵌套的 if…else 语句
        int x = 30;
        int y = 10;
        if (x == 30) {
            if (y == 20) {
                System.out.print("X = 30 and Y = 20");
            } else {
                System.out.println("X = 30 and Y != 20");
            }
        } else {
            System.out.println("X != 30");
        }
    }
}