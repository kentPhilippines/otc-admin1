public class ForDemo3 {
    public static void main(String[] args) {
        // 使用break
        System.out.println("----使用break----");
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                break;
            }
            System.out.println("Hello Break");
        }

        // 使用continue
        System.out.println("----使用continue----");
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                continue;
            }
            System.out.println("Hello Continue");
        }
    }
}