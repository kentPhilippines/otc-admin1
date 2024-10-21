public class ForDemo1 {
    public static void main(String[] args) {
        System.out.println("----3次Hello World----");
        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                break; // 当i等于1时，跳出循环
            }
            System.out.println("Hello World");
        }

        System.out.println("----4次Hello MySQL----");
        for (int i = 1; i < 5; i++) {
            if (i == 3) {
                continue; // 当i等于3时，跳过当前迭代
            }
            System.out.println("Hello MySQL");
        }

        System.out.println("----5次Hello Spring----");
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                continue; // 当i等于3时，跳过当前迭代
            }
            System.out.println("Hello Spring");
        }

        System.out.println("----3次Hello Redis----");
        for (int i = 1; i <= 5; i += 2) {
            if (i == 3) {
                break; // 当i等于3时，跳出循环
            }
            System.out.println("Hello Redis");
        }
    }
}