/*
  该类是“World-of-Zuul”应用程序的主类。
  《World of Zuul》是一款简单的文本冒险游戏。用户可以在一些房间组成的迷宫中探险。
  你们可以通过扩展该游戏的功能使它更有趣!.

  如果想开始执行这个游戏，用户需要创建Game类的一个实例并调用“play”方法。

  Game类的实例将创建并初始化所有其他类:它创建所有房间，并将它们连接成迷宫；它创建解析器
  接收用户输入，并将用户输入转换成命令后开始运行游戏。

  @author  svinci
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import db.DBUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Function;


public class Game
{
    private final Parser parser;
    private Room currentRoom;
    private final HashMap<Integer, Room> roomHashMap;
    private int roomNumbers;
    private final Deque<Integer> enterStack;
    private Player nowPlayer;

    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game()
    {
        roomHashMap = new HashMap<>();
        enterStack = new LinkedList<>();
        //createRooms();
        try {
            getMapFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createPlayers();
        roomNumbers = roomHashMap.size();
        parser = new Parser();


    }

    /**
     * 创建所有房间对象并连接其出口用以构建迷宫.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        outside.addItem("apple", "an apple", 1);
        outside.addItem("banana", "a banana", 1);
        outside.addItem("magic cookie", "a magic cookie", 10);
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        roomHashMap.put(outside.getId(), outside);

        theater.setExit("west", outside);
        roomHashMap.put(theater.getId(), theater);
        theater.setTrap();

        pub.setExit("east", outside);
        roomHashMap.put(pub.getId(), pub);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        roomHashMap.put(lab.getId(), lab);

        office.setExit("west", lab);
        roomHashMap.put(office.getId(), office);

        currentRoom = outside;  // start game outside
        enterStack.addLast(currentRoom.getId());
    }

    /**
     *文件第一行输入一共有n个房间
     * 之后对于每个房间
     * 第一行输入一个字符串表示房间的介绍
     * 第二杠输入一个整数表示是不是要随机传送的房间
     * 之后再次输入这n个房间的相关信息
     * 第一行表示有几个相邻房间
     * 接下来的若干行表示相邻的方位,和相邻的房间编号
     * 第二行输入有几个物品
     * 对于每个物品
     * 第一行输入物品名称
     * 第二行输入物品介绍
     * 第三行输入一个整数表示物品重量
     */
    private void getMapFromFile() throws IOException
    {
        FileReader fileReader = new FileReader("D:\\java_save\\sept1-svinci66\\src\\cn\\edu\\whut\\sept\\zuul\\RoomMap.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String num = bufferedReader.readLine();
        roomNumbers = Integer.parseInt(num);
        for(int i = 0; i < roomNumbers; i++) {
            String desc = bufferedReader.readLine();
            Room newRoom = new Room(desc);
            String roomType = bufferedReader.readLine();
            if(roomType.equals("1")) newRoom.setTrap();
            roomHashMap.put(newRoom.getId(), newRoom);
        }
        for(int i : roomHashMap.keySet()) {
            Room newRoom = roomHashMap.get(i);
            String exit = bufferedReader.readLine();
            int exitNum = Integer.parseInt(exit);
            for(int j = 0; j < exitNum; j++) {
                String s = bufferedReader.readLine();
                String nxt, id;
                Scanner scanner = new Scanner(s);
                nxt = scanner.next();
                id = scanner.next();
                newRoom.setExit(nxt, roomHashMap.get(Integer.parseInt(id)));
            }
            String itemNumStr = bufferedReader.readLine();
            int itemNUm = Integer.parseInt(itemNumStr);
            for(int j = 0; j < itemNUm; j++) {
                String name, desc, weight;
                name = bufferedReader.readLine();
                desc = bufferedReader.readLine();
                weight = bufferedReader.readLine();
                newRoom.addItem(name, desc, Integer.parseInt(weight));
            }
        }
    }


    /**
     * 用于初始化玩家信息
     * todo: 数据库存储玩家信息
     */
    private void createPlayers()
    {
        System.out.println("Please enter your name");
        DBUtil db = new DBUtil();
        try{
            db.getConnection();
            String input = new Scanner(System.in).nextLine();
            String sql = "SELECT * FROM `user` WHERE userName='" + input + "'";
            ResultSet rs = db.executeQuery(sql, null);
            if(rs.next()) {
                System.out.println("Welcome back and continue your adventure");
                nowPlayer = new Player(rs.getInt("nowRoomId"), rs.getString("userName"), rs.getInt("capacity"));
                currentRoom = roomHashMap.get(rs.getInt("nowRoomId"));
                System.out.println(rs.getInt("capacity"));
                enterStack.addLast(currentRoom.getId());
            }
            else {
                String saveSql = "call `save_user`(?,?,?,@res);";
                Object[] param = new Object[]{input, 0, 10};
                if(db.executeUpdate(saveSql, param) > 0) {
                    System.out.println("save a new user!");
                    nowPlayer = new Player(1, input, 10);
                    currentRoom = roomHashMap.get(1);
                    enterStack.addLast(currentRoom.getId());
                }
                else {
                    System.out.println("save error!");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeAll();
        }


    }

    /**
     *  游戏主控循环，直到用户输入退出命令后结束整个程序.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        Integer finished = 0;
        while (finished == 0) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * 向用户输出欢迎信息.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * 用于选择执行哪一种操作
     * @param command 命令
     * @param param 操作的种类
     * @return 返回程序是否继续进行
     */
    public Integer selectCommand(String param, Command command)
    {
        HashMap<String, Function<Command, Integer>> map = new HashMap<>();
        map.put("help", this::printHelp);
        map.put("go", this::goRoom);
        map.put("quit", this::quit);
        map.put("look", this::look);
        map.put("back", this::back);
        map.put("take", this::take);
        map.put("drop", this::drop);
        map.put("items", this::showItems);
        map.put("eat", this::eat);
        return map.get(param).apply(command);

    }


    /**
     * 执行用户输入的游戏指令.
     * @param command 待处理的游戏指令，由解析器从用户输入内容生成.
     * @return 如果执行的是游戏结束指令，则返回true，否则返回false.
     */
    private Integer processCommand(Command command)
    {
        Integer wantToQuit;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return 0;
        }
        String commandWord = command.getCommandWord();
        wantToQuit = selectCommand(commandWord, command);
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * 执行help指令，在终端打印游戏帮助信息.
     * 此处会输出游戏中用户可以输入的命令列表
     */
    private Integer printHelp(Command command)
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        return 0;
    }

    /**
     * 执行go指令，向房间的指定方向出口移动，如果该出口连接了另一个房间，则会进入该房间，
     * 否则打印输出错误提示信息.
     */
    private Integer goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return 0;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            //判断是否到陷阱房间,到的话则随机传送
            if(currentRoom.getTrap()) {
                System.out.println("You were transferred to a random room");
                int currentRoomId = currentRoom.getId();
                Random rand = new Random();
                while(currentRoomId == currentRoom.getId()) {
                    currentRoomId = rand.nextInt(roomNumbers) + 1;
                }
                currentRoom = roomHashMap.get(currentRoomId);
            }
            //更新进入的房间,存入栈
            enterStack.addLast(currentRoom.getId());
            nowPlayer.setCurrentRoomId(currentRoom.getId());
            System.out.println(currentRoom.getLongDescription());
        }
        return 0;
    }

    /**
     * 执行Quit指令，用户退出游戏。如果用户在命令中输入了其他参数，则进一步询问用户是否真的退出.
     * @return 如果游戏需要退出则返回1，否则返回0.
     */
    private Integer quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return 0;
        }
        else {
            DBUtil db = new DBUtil();
            try{
                db.getConnection();
                String updateSql = "call `update_user`(?,?,?);";
                Object[] param = new Object[]{nowPlayer.getName(), currentRoom.getId(), nowPlayer.getLimitWeight()};
                if(db.executeUpdate(updateSql, param) > 0) {
                    System.out.println("save success!");
                }
                else {
                    System.out.println("save error!");
                }
                return 1;  // signal that we want to quit
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.closeAll();
            }
            return 1;
        }

    }

    /**
     * 查询房间内的物品
     * @param command 传入指令
     * @return 不结束
     */
    private Integer look(Command command)
    {
        System.out.println(currentRoom.getLongDescription());
        currentRoom.getItemsList();
        return 0;
    }

    /**
     * 用于处理后退,可以单步后退/多步后退/回到起点
     * @param command 传入命令
     * @return 执行结果, 0代表继续, 1代表结束
     */
    private Integer back(Command command)
    {
        int num;
        if(enterStack.size() == 1) {
            System.out.println("you are at the beginning");
            return 0;
        }
        if(!command.hasSecondWord()) {
            num = 1;
        }
        else {
            try {
                String numStr = command.getSecondWord();
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                System.out.println(command.getCommandWord());
                if(!command.getSecondWord().equals("begin")) {
                    System.out.println("wrong number, try again");
                    return 0;
                }
                else num = enterStack.size() - 1;
            }
        }

        if(num >= enterStack.size()) {
            System.out.println("you can't back so much step");
        }
        else {
            for(int i = 0; i < num; i++) enterStack.removeLast();
            currentRoom = roomHashMap.get(enterStack.getLast());
            nowPlayer.setCurrentRoomId(currentRoom.getId());
            System.out.println("you've got back");
            System.out.println(currentRoom.getLongDescription());
        }
        return 0;
    }

    /**
     * 捡起地上的物品
     * @param command 传入命令
     * @return 执行结果, 0代表继续, 1代表结束
     */
    private Integer take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return 0;
        }
        String takeItemName = command.getSecondWord();
        int pos = -1;
        ArrayList<Item> items = currentRoom.getItems();

        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(item.getName().equals(takeItemName)) {
                pos = i;
                break;
            }
        }
        if(pos == -1) {
            System.out.println("you can't take the item don't exist");
            return 0;
        }
        //如果成功捡起,则房间内物品消失
        if(nowPlayer.takeItem(items.get(pos))) {
            System.out.println("you've taken this item");
            items.remove(pos);
        }
        else {
            System.out.println("no room for item");
        }
        return 0;
    }

    /**
     * 丢弃身上的物品
     * @param command 传入命令
     * @return 执行结果, 0代表继续, 1代表结束
     */
    private Integer drop(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return 0;
        }
        String dropItemName = command.getSecondWord();
        Item item = nowPlayer.dropItem(dropItemName);
        if(item == null) {
            System.out.println("you don't have this item!");
            return 0;
        }
        System.out.println("you've dropped this item");
        currentRoom.addItem(item);
        return 0;
    }

    /**
     * 显示玩家和房间内的所有物品以及重量和
     * @param command 传入命令
     * @return 执行结果, 0代表继续, 1代表结束
     */
    private Integer showItems(Command command)
    {
        currentRoom.showItems();
        nowPlayer.showItems();
        return 0;
    }

    /**
     * 尝试吃房间内的魔法饼干增加负重
     * @param command 传入命令
     * @return 执行结果, 0代表继续, 1代表结束
     */
    private Integer eat(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Eat  what?");
            return 0;
        }
        String eatCookie = command.getSecondWord();
        if(!eatCookie.equals("cookie")) {
            System.out.println("you can't eat" + eatCookie);
            return 0;
        }
        int pos = -1;
        ArrayList<Item> items = currentRoom.getItems();
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(item.getName().equals("magic cookie")) {
                pos = i;
                break;
            }
        }
        if(pos != -1) {
            nowPlayer.updateLimitWeight(items.get(pos).getWeight());
            items.remove(pos);
            System.out.print("you've eaten a magic cookie\nnow your limit wight is:");
            System.out.println(nowPlayer.getLimitWeight());

        }
        else {
            System.out.println("no magic cookie in the room");
        }

        return 0;
    }


}