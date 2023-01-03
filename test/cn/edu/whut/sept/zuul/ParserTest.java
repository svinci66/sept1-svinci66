package cn.edu.whut.sept.zuul;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;


public class ParserTest {
    @Test
    public void getCommand() {
        String command = "go west";
        ByteArrayInputStream strIn = new ByteArrayInputStream(command.getBytes());
        System.setIn(strIn);
        try {
            Command cmd = new Parser().getCommand();
            if (cmd.getCommandWord().equals("go") && cmd.getSecondWord().equals("west")) {
                System.out.println("Accepted!");
            } else {
                System.out.println("Error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}