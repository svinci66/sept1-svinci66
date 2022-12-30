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
     * ������һ�����ڵķ����ַ���
     * @return һ�����ڵķ����ַ���
     */
    public String getRandomDirection() {
        ArrayList<String> keys = new ArrayList<>();
        for(String i : exits.keySet()) keys.add(i);
        Random rand = new Random();
        return keys.get(rand.nextInt(keys.size()));
    }

    /**
     * ���һ�����ڵķ���
     * @param direction ����
     * @param neighbor ���ڵķ������
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
     * ��ȡ���ڷ����з�������з���
     * @return ����һ�������������ڷ����з���ķ���
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
     * ��ȡ�÷����ڵ�������Ʒ��Ϣ
     */
    public void getItems()
    {
        if(items.size() == 0) System.out.println("No item!");
        for(Item i : items) {
            System.out.println(i.getName() + " " + i.getDescription() + ", wight is " + i.getWeight());
        }
    }




    /**
     * ��ȡָ������ķ������
     * @param direction ѡ���ķ���
     * @return �������
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}


