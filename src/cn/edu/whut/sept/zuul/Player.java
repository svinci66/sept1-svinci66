package cn.edu.whut.sept.zuul;

import java.util.ArrayList;

public class Player {
    private int limitWeight, currentWeight;
    private String name;
    private int currentRoomId;
    private ArrayList<Item> items;

    public Player(int limitWeight, String name, int currentRoomId) {
        this.limitWeight = limitWeight;
        this.name = name;
        this.currentRoomId = currentRoomId;
        items = new ArrayList<>();
        currentWeight = 0;
    }


    public String getName() {
        return name;
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public void updateLimitWeight(int x){this.limitWeight += x;}

    public void setCurrentRoomId(int currentRoomId) {
        this.currentRoomId = currentRoomId;
    }

    /**
     * 显示身上的所有物品以及重量和
     */
    public void showItems() {
        System.out.print("the items of the player are :");
        int sumWeight = 0;
        for(Item item : items) {
            System.out.print(item.getName() + " ");
            sumWeight += item.getWeight();
        }
        System.out.println();
        System.out.println("total weight of the player is : "+sumWeight);
    }

    /**
     * 用于处理从房间拿取物品
     * @param item 物品对象
     * @return 是否成功拿取
     */
    public boolean takeItem(Item item) {
        if(currentWeight + item.getWeight() > limitWeight) return false;
        currentWeight += item.getWeight();
        items.add(item);
        return true;
    }

    /**
     * 用于处理丢弃物品
     * @param itemName 物品名称
     * @return 物品对象
     */
    public Item dropItem(String itemName) {
        int pos = -1;
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(item.getName().equals(itemName)) {
                pos = i;
                break;
            }
        }
        if(pos == -1) return null;
        Item item = items.get(pos);
        currentWeight -= item.getWeight();
        items.remove(pos);
        return item;
    }

}
