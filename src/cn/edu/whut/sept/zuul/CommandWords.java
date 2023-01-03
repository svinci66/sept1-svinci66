package cn.edu.whut.sept.zuul;

import java.util.HashMap;

public class CommandWords {
    private final HashMap<String, Integer> validCommands;


    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", 1);
        validCommands.put("quit", 1);
        validCommands.put("help", 1);
        validCommands.put("look", 1);
        validCommands.put("back", 1);
        validCommands.put("take", 1);
        validCommands.put("drop", 1);
        validCommands.put("items", 1);
        validCommands.put("eat", 1);
    }

    /**
     * �ж��û�������ָ���Ƿ�Ϸ�
     *
     * @param aString ���ж��Ƿ�Ϸ����û�����ָ�
     * @return ����Ϸ����û�����ָ��򷵻�true�����򷵻�false.
     */
    public boolean isCommand(String aString) {
        Integer isValid = validCommands.getOrDefault(aString, 0);
        return isValid == 1;

    }

    /**
     * �������ָ��
     */
    public void showAll() {
        for (String i : validCommands.keySet()) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
