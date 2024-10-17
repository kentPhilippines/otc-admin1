public class ss {
    public static void main(String[] args) {
        String studentId = "20233011082";
        char lastDigit1 = studentId.charAt(studentId.length() - 2);
        char lastDigit2 = studentId.charAt(studentId.length() - 1);
        char[] lastTwoDigits = {lastDigit1, lastDigit2};
        for (char c : lastTwoDigits) {
            System.out.print(c + " ");
        }
    }
}
