public class ForDemo2 {
    public static void main(String[] args){
        int [] numbers = {10, 20, 30, 40, 50};
        for(int x : numbers ){
            if (x == 30) {
                break; // 当x等于30时，跳出循环
            }
            System.out.print( x );
            System.out.print(",");
        }
        System.out.println();
        String [] names ={"James", "Larry", "Tom", "Lacy"};
        for( String name : names ) {
            if (name.equals("Tom")) {
                continue; // 当name等于"Tom"时，跳过当前循环，继续下一个循环
            }
            System.out.print( name );
            System.out.print(",");
        }
    }
}