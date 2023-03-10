package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private final String description;
    private final HashMap<String, Room> exits;
    private final ArrayList<Item> items;
    private boolean isTrap;
    private final int id;
    private static int cnt = 0;

    public Room(String description) {
        this.description = description;
        isTrap = false;
        exits = new HashMap<>();
        items = new ArrayList<>();
        id = ++cnt;
    }


    public void setTrap() {
        isTrap = true;
    }

    public int getId() {
        return id;
    }

    public boolean getTrap() {
        return isTrap;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * 添加一个存在的房间
     *
     * @param direction 方向
     * @param neighbor  相邻的房间对象
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * 添加物品的两种方式
     *
     * @param name        物品名
     * @param description 物品描述
     * @param weight      物品重量
     */
    public void addItem(String name, String description, int weight) {
        items.add(new Item(name, description, weight));
    }

    public void addItem(Item item) {
        items.add(item);
    }


    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * 显示房间内的所有物品以及重量和
     */
    public void showItems() {
        int sumWeight = 0;
        System.out.print("the items of the room are :");
        for (Item item : items) {
            System.out.print(item.getName() + " ");
            sumWeight += item.getWeight();
        }
        System.out.println();
        System.out.println("total weight of the room is : " + sumWeight);
    }

    /**
     * 获取相邻方向有房间的所有方向
     *
     * @return 返回一个包含所有相邻方向有房间的方向
     */
    private String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    /**
     * 获取该房间内的所有物品信息
     */
    public void getItemsList() {
        if (items.size() == 0) System.out.println("No item!");
        for (Item i : items) {
            System.out.println(i.getName() + " " + i.getDescription() + ", wight is " + i.getWeight());
        }
    }


    /**
     * 获取指定方向的房间对象
     *
     * @param direction 选定的方向
     * @return 房间对象
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}


