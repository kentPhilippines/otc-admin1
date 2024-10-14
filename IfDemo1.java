public class IfDemo1 {
    public static void main(String[] args) {
        // 格式3： if(条件表达式){ 代码... } else if(条件表达式){ 代码... } ... else{ 代码... }
        int score = 95;
        // 绩效：0-60 C，60-80 B，80-90 A，90-100 A+
        if (score >= 0 && score < 60) {
            System.out.println("分数是：" + score + "，绩效是：C");
        } else if (score >= 60 && score < 80) {
            System.out.println("分数是：" + score + "，绩效是：B");
        } else if (score >= 80 && score < 90) {
            System.out.println("分数是：" + score + "，绩效是：A");
        } else if (score >= 90 && score <= 100) {
            System.out.println("分数是：" + score + "，绩效是：A+");
        } else {
            System.out.println("录入的分数有毛病！");
        }
    }
}