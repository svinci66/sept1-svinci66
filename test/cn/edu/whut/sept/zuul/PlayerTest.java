package cn.edu.whut.sept.zuul;

import org.junit.Test;

import java.util.ArrayList;

public class PlayerTest {
    @Test
    public void takeItem() {
        Player player=new Player(0,"qwq",1000);
        player.takeItem(new Item("apple","an apple",50));
        ArrayList<Item> bag=player.getItems();
        if(bag.get(0).getName().equals("apple")) {
            System.out.println("carryItem Accepted");
        }else{
            System.out.println("carryItem Error");
        }
    }

    //此测试必须在carryItem正确的情况下才有效
    @Test
    public void dropItem() {
        Player player=new Player(0,"qwq",1000);
        player.takeItem(new Item("apple","an apple",50));
        player.dropItem("apple");
        ArrayList<Item> bag=player.getItems();
        if(bag.size()==0) {
            System.out.println("dropItem Accepted");
        }else{
            System.out.println("dropItem Error");
        }
    }


}