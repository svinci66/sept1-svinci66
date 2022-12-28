package cn.edu.whut.sept.zuul;

import java.util.HashMap;
import java.util.function.Function;

public class Main {

    public String student(String param){
        return "����ѧ����"+param+"�����ɹ�";
    }

    public String teacher(String param){
        return "������ʦ��"+param+"�����ɹ�";
    }

    //���ã����û�����룬������Supplier������һ�����÷�
    public String test(String param){
        //����һ��map��valueΪfunction��������һ��stringΪ��Σ��ڶ���stringΪ���ض���
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
