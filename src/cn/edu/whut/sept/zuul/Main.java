package cn.edu.whut.sept.zuul;

import java.util.HashMap;
import java.util.function.Function;

public class Main {

    public String student(String param){
        return "调用学生："+param+"方法成功";
    }

    public String teacher(String param){
        return "调用老师："+param+"方法成功";
    }

    //调用，如果没有输入，就是用Supplier函数，一样的用法
    public String test(String param){
        //创建一个map，value为function函数，第一个string为入参，第二个string为返回对象
        HashMap<String, Function<String, String>> map = new HashMap<>();
        map.put("student",this::student);
        map.put("teacher",this::teacher);

        return map.get(param).apply(param);
    }

    public static void main(String[] args) {
        //Main qwq = new Main();
        //System.out.println(qwq.test("teacher"));
        Game game = new Game();
        game.play();
    }
}
