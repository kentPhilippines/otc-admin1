public class NewStudent {
    /**
     * 学生
     */
    private String name;
    /**
     * 名字
     */
    private String  age;//定义

    /**
     * 年龄
     * @param inputName
     * @param inputAge
     */
    public NewStudent(String inputName,String inputAge){
        this.name = inputName;
        this.age = inputAge;
    }
    public  void eat(){
        System.out.println("吃饭");
    }

    /**
     * 吃饭
     * @param food
     */

    public void eat(String food){
        System.out.println("学生吃的"+food);
    }
    public String eat(String food,String time){
        System.out.println("学生在" + time + "吃的" + food);
        return"吃饱了";//定义吃饭的方法
    }
}


