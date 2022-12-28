package cn.edu.whut.sept.zuul;

import java.util.Set;
import java.util.HashMap;

public class Room
{
    private String description;
    private HashMap<String, Room> exits;

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
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
     * ��ȡָ������ķ������
     * @param direction ѡ���ķ���
     * @return �������
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}


