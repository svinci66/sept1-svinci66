package cn.edu.whut.sept.zuul;

import java.util.HashMap;

public class CommandWords
{
    private final HashMap<String, Integer> validCommands;


    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("go", 1);
        validCommands.put("quit", 1);
        validCommands.put("help", 1);
    }

    /**
     * 判断用户的输入指令是否合法
     * @param aString 待判断是否合法的用户输入指令，
     * @return 如果合法的用户输入指令，则返回true，否则返回false.
     */
    public boolean isCommand(String aString)
    {
        Integer isValid = validCommands.getOrDefault(aString, 0);
        if(isValid == 1) return true;
        return false;

    }

    /**
     * 输出所有指令
    */
    public void showAll()
    {
    for(String i : validCommands.keySet()) {
        System.out.print(i + " ");
    }
        System.out.println();
    }
}
