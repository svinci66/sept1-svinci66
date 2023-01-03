package cn.edu.whut.sept.zuul;

public class Command {
    private final String commandWord;
    private final String secondWord;

    public Command(String firstWord, String secondWord) {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    /**
     * �ж��û�������ָ���Ƿ�Ϸ�
     *
     * @return ����û�����ָ����ڵ�һ���ʣ��򷵻�true�����򷵻�false.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**
     * �ж�ָ���Ƿ���ڵڶ�����
     *
     * @return ���ָ����ڵڶ����ʣ��򷵻�true�����򷵻�false.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
