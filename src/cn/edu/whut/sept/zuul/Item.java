/*
物品类,用于物品对象
 */

package cn.edu.whut.sept.zuul;

public class Item {
    private final String name;

    private final String desc;

    private final int weight;

    public Item(String name, String description, int weight){
        this.name = name;
        this.desc = description;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return desc;
    }

    public int getWeight(){
        return weight;
    }


}
