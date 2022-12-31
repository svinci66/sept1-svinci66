/**
 * 该类是“World-of-Zuul”应用程序的主类。
 * 《World of Zuul》是一款简单的文本冒险游戏。用户可以在一些房间组成的迷宫中探险。
 * 你们可以通过扩展该游戏的功能使它更有趣!.
 *
 * 如果想开始执行这个游戏，用户需要创建Game类的一个实例并调用“play”方法。
 *
 * Game类的实例将创建并初始化所有其他类:它创建所有房间，并将它们连接成迷宫；它创建解析器
 * 接收用户输入，并将用户输入转换成命令后开始运行游戏。
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.*;
import java.util.function.Function;


public class Game
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<Integer, Room> roomHashMap;
    private int roomNumbers;
    private Deque<Integer> enterStack;
    private Player nowPlayer;

    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game()
    {
        roomHashMap = new HashMap<>();
        enterStack = new LinkedList<>();
        createRooms();
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
        outside.setMagicCookie();
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

    private void createPlayers()
    {
        Player sj;
        sj = new Player(1, "suojian", 1);

        nowPlayer = sj;
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
        System.out.println("you're right, but");
        System.out.println("Welcome to the World of Genshin!");
        System.out.println("The game takes place in a fantasy world called \"Tivat\"");
        System.out.println("where those chosen by the gods will be granted the \"Eye of God\" to channel the power of the elements");
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
            if(currentRoom.getTrap() == true) {
                System.out.println("You were transferred to a random room");
                int currentRoomId = currentRoom.getId();
                Random rand = new Random();
                while(currentRoomId == currentRoom.getId()) {
                    currentRoomId = rand.nextInt(roomNumbers) + 1;
                }
                currentRoom = roomHashMap.get(currentRoomId);
            }
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
            return 1;  // signal that we want to quit
        }
    }

    private Integer look(Command command)
    {
        currentRoom.getItemsList();
        return 0;
    }

    /**
     * 用于处理后退
     * @param command
     * @return 执行结果, 0代表继续, 1代表结束
     */
    private Integer back(Command command)
    {
        Integer num = 0;
        if(!command.hasSecondWord()) {
            num = 1;
        }
        try {
            String numStr = command.getSecondWord();
            num = Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            System.out.println("wrong number, try again");
        }
        if(num >= enterStack.size()) {
            System.out.println("you can't back so much step");
        }
        else {
            for(int i = 0; i < num; i++) enterStack.removeLast();
            currentRoom = roomHashMap.get(enterStack.getLast());
            System.out.println("you've got back");
            System.out.println(currentRoom.getLongDescription());
        }
        return 0;
    }

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
        if(nowPlayer.takeItem(items.get(pos))) {
            items.remove(pos);
        }
        else {
            System.out.println("no room for item");
        }
        return 0;
    }

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
        currentRoom.addItem(item);
        return 0;
    }

    private Integer showItems(Command command)
    {
        currentRoom.showItems();
        nowPlayer.showItems();
        return 0;
    }

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
        if(currentRoom.getMagicCookie()) {
            nowPlayer.updateLimitWeight(10);
            System.out.println("you eat a magic cookie");
        }
        else {
            System.out.println("no magic cookie in the room");
        }

        return 0;
    }


}