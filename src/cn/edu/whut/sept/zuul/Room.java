package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private String lastRoom;
    private boolean trap;

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        trap = false;
    }

    public void setLastRoom(String lastRoom) {this.lastRoom = lastRoom;}

    public void setTrap(){trap = true;}

    public boolean getTrap() {return trap;}

    public String getLastRoom(){return lastRoom;}

    /**
     * 随机获得一个相邻的方向字符串
     * @return 一个相邻的方向字符串
     */
    public String getRandomDirection() {
        ArrayList<String> keys = new ArrayList<>();
        for(String i : exits.keySet()) keys.add(i);
        Random rand = new Random();
        return keys.get(rand.nextInt(keys.size()));
    }

    /**
     * 添加一个存在的房间
     * @param direction 方向
     * @param neighbor 相邻的房间对象
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public void addItem(String name, String description, int weight) {
        items.add(new Item(name, description, weight));
    }


    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * 获取相邻方向有房间的所有方向
     * @return 返回一个包含所有相邻方向有房间的方向
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * 获取该房间内的所有物品信息
     */
    public void getItems()
    {
        if(items.size() == 0) System.out.println("No item!");
        for(Item i : items) {
            System.out.println(i.getName() + " " + i.getDescription() + ", wight is " + i.getWeight());
        }
    }




    /**
     * 获取指定方向的房间对象
     * @param direction 选定的方向
     * @return 房间对象
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}


