package cn.edu.whut.sept.zuul;

import org.junit.Test;

public class CommandWordsTest {

    @Test
    public void isCommand() {
        CommandWords test = new CommandWords();
        if (test.isCommand("look") && !test.isCommand("check")) {
            System.out.println("Accepted");
        } else {
            System.out.println("Error!");
        }
    }
}