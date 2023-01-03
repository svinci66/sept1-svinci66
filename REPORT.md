# 软件工程实训任务报告



## 1.任务概述

### 1.1任务介绍

样例工程代码world-of-zuul是一个基于字符界面的探险游戏，这个游戏目前只具备一个基本的功能框架，需要你对其进行完善和扩充。

原版的world-of-zuul是由 Will Crowther在20世纪70年代开发、并经过 Don Woods扩充的一个探险游戏。它有时候也叫作巨洞探险（ Colossal Cave Adventure），在那个时代是一个精彩的、充满想像力的复杂游戏，包括要在一个复杂的洞穴系统中找到出路、寻找隐藏的财宝、使用暗语和其他一些神秘工具，最终的目的是获得高分。

### 1.2任务目标

1. 理解软件代码规范的重要性
2. 理解代码变化对软件质量带来的影响
3. 掌握基于Git的个人代码版本维护方法
4. 掌握markdown文件编写方法

## 2.任务分析

### 2.1任务重点

1. 阅读、理解和标注样例代码
2. 分析和学习代码质量特征、设计方法和编程风格
3. 运用所学方法，对开源代码进行标注
4. 对样例工程进行简单功能扩充和维护

### 2.2任务要求

#### 2.2.1 阅读和描述样例工程

- fork样例工程，并clone到本地仓库；
- 在本地开发环境上运行样例工程，理解样例工程的代码逻辑；
- 以UML图描述样例工程的组成及结构图（类及类之间的关系）
  - 可结合markdown语法和mermaid插件绘制所需图形标注样例工程中的代码


####  2.2.2标注样例工程中的代码

- 基于javadoc规范标注代码，对包、类、方法、代码片段、参数和语句等代码层次进行注释（可参考Game类的标注样例）；
- 注释后的代码提交到本地代码库后，同步推送到远程代码仓库；
- 可参考ESLint、github/super-linter等开发插件了解关于代码规范的相关知识；

#### 2.2.3扩充和维护样例工程

- 对样例代码中的功能设计进行分析，找出若干设计缺陷和改进点，并进行修正或扩充，并集成到工程代码中；
- 可借助代码质量分析工具或代码规范检查工具对代码质量进行分析，发现潜在问题；

#### 2.2.4功能扩充点

- 样例工程“world-of-zuul”具备最基本的程序功能，该项目具有极大的扩展空间，各位同学可选择或自行设计系统结构优化或功能扩充需求，完成3项左右的功能扩充实现；

#### 2.2.5编写测试用例

- 针对功能改进和扩充，在项目结构中编写单元测试用例，对代码执行单元测试；

## 3.开发计划

### 3.1对项目示例代码进行建模

在fork仓库代码并clone到本地后,阅读代码后尝试理解实例代码的结构
并根据其中类的关系,利用Enterprise Architect软件绘制实例代码的UML类图

### 3.2维护样例工程

阅读样例代码后，发现如下几点问题，

1. 在Game类的processCommand()方法中，当用户输入的命令被辨认出来以后，有一系列的if语句用来分派程序到不同的地方去执行。从面向对象的设计原则来看，这种解决方案不太好，因为每当要加入一个新的命令时，就得在这一堆if语句中再加入一个if分支，最终会导致这个方法的代码膨胀得极其臃肿。

2. 在CommandWords类的isCommand()方法中判断单词是否合法的方式是通过循环逐个判断,这种方法效率较低。
3. 在Game类中的createRooms方法中,使用了逐句赋值的方法来初始化地图,这种方法会造成文件内代码过多,同时修改地图也比较繁琐。

在接下来的开发过程中，将利用IDEA编辑器，结合所学的面向对象的知识和数据结构的知识尝试解决

### 3.3完成项目拓展功能

根据给出的样例功能实现，确定要增加的功能扩充实现需求如下：

1. 使得一个房间里可以存放任意数量的物件，每个物件可以有一个描述和一个重量值。
2. 在游戏中实现一个“back”命令。back指令可以实现回到起点，回退一步，回退指定步数，以及对回退的步数大于已经走的步数的错误处理。
3. 在游戏中增加具有传输功能的房间，每当玩家进入这个房间，就会被随机地传输到另一个房间。
4. 在游戏中新建一个独立的Player类用来表示玩家
   1. 一个玩家通过登录来进行游行，可以区分为初次登录和载入存档。存档可以通过数据库进行保存。
    2. 一个玩家对象应该保存玩家的姓名等基本信息，也应该保存玩家当前所在的房间。
    3. 玩家可以随身携带任意数量的物件，但随身物品的总重量不能操过某个上限值。
    4. 玩家可以拾取房间内的指定物品或丢弃身上携带的某件或全部物品，当拾取新的物件时超过了玩家可携带的重量上限，系统应给出提示。
    5. 在游戏中增加一个新的命令“items”, 可以打印出当前房间内所有的物件及总重量，以及玩家随身携带的所有物件及总重量。
    6. 在某个或某些房间中随机增加一个magic cookie（魔法饼干）物件，并增加一个“eat cookie”命令，如果玩家找到并吃掉魔法饼干，就可以增长玩家的负重能力，增加的重量视为魔法饼干本身的重量。

在确定要实现的需求后，我们便可以在IDEA编辑器中编写代码来实现需求。

### 3.4装配项目数据库与文件系统

由于项目采用的数据库是mysql, 对于数据库的编写在mysql的Workbench中完成。

同时在项目中增加java和mysql连接的配置jar包

### 3.5优化代码编写风格和结构

在全部功能基本实现后，利用IDEA内自带的代码检查功能对代码的格式和命名进行修改。

之后利用codestyle，使用google的java规范对代码进行调整。

同时尽量保证新编写的代码和样例工程中的代码风格保持一致。

### 3.6对项目进行软件测试

在全部需求实现完成，同时对代码进行初步检查后，在IDEA编辑器内的插件JUnit来对项目进行软件测试，再根据检查的结果来修改bug或是优化代码。重复以上过程。

## 4.软件配置计划

### 4.1软件版本编码方案

采取最为广泛的“主版本号.子版本号.修正版本号”格式。详细规则如图所示:

[![YNE-DT40-Y-4-Z-FJX-I1-CV7.png](https://i.postimg.cc/L62bRrmC/YNE-DT40-Y-4-Z-FJX-I1-CV7.png)](https://postimg.cc/ThN9JNLg)

### 4.2命名规范

采用最广泛的驼峰规则，也就是变量名称首个单词小写，其余单词首字母大写。类的命名则采用每个单词首字母大写的形式。在实例化时则尽量让变量名称为变量类型的小写。

### 4.3分支管理规范

由于项目由个人独立开发，不用考虑将别人的代码进行合并的操作。一共使用两种类型的版本，-master和-develop。前者为可稳定使用的版本，后者为开发新功能的版本

- -master 分支（主分支） 稳定版本
- -develop 分支（开发分支） 最新版本

在对develop版本测试一天后没有发现问题，则将其推送为master版本。

### 4.4提交规范

计划使用基于 angular 规范的 commit

基于 angular 规范的 commit commit格式如下: <type>: <subject> <BLANK LINE> <body> type - 提交 commit 的类型 feat: 新功能 fix: 修复问题 docs: 修改文档 style: 修改代码格式(不影响逻辑功能,比如格式化.补充分号等等) refactor: 重构代码(fix bug或增加新功能不属于此范围) perf: 提升页面性能 test: 增加/修改测试用例 chore:

## 5.测试计划

利用Junit框架对项目中的基类中的非常规方法（即不包括get，set，show这类方法）进行白盒测试。

对于不易于编写测试用例的，需要大量与用户交互并依赖用户来进行结果分析的类（即Game类）中的方法，我们设计完整的测试流程对其进行黑盒测试。

## 6.实施情况

### 6.1绘制示例代码UML图

根据实例代码的类之间的关系，可以得到如下的UML类图

[![1-JG72-BVSNZW-UK-LCEJ-3.png](https://i.postimg.cc/wBG1xcX3/1-JG72-BVSNZW-UK-LCEJ-3.png)](https://postimg.cc/cKfxX35N)

### 6.2 维护样例工程

在3.2中我们已经讨论了对样例工程的那些地方进行修改，在这里将具体讨论如何实现

1. if-else语句过多

   解决方法:通过使用Java8的新特性,建立一个String到函数的映射

```java
HashMap<String, Function<Command, Integer>> map = new HashMap<>();
map.put("help", this::printHelp);
return map.get(param).apply(command);
```

​		便可以通过读入的字符串来实现直接执行对应的函数,符合面向对象的思想

2. 用循环判断合法词

   解决方法:直接一个HashMap来存储合法的命令,部分代码如下

```javascript
	validCommands = new HashMap<>();
	validCommands.put("go", 1);
	validCommands.put("quit", 1);
```

​		判断只需

```java
    public boolean isCommand(String aString)
    {
        Integer isValid = validCommands.getOrDefault(aString, 0);
        return isValid == 1;
    }
```

3.  文件中涉及地图建立的语句过多,在新增地图时需要手动创建更多的对象

   解决方法:将地图信息保存在RoomMap.txt文件中,程序中读这个文件来获取地图信息

   部分代码如下, 详见最终版文件中的Game类中的getMapFromFile()方法
   
   ```java
   	FileReader fileReader = new FileReader("D:\\java_save\\sept1-svinci66\\src\\cn\\edu\\whut\\sept\\zuul\\RoomMap.txt");
       BufferedReader bufferedReader = new BufferedReader(fileReader);
       String num = bufferedReader.readLine();
       roomNumbers = Integer.parseInt(num);
   ```

### 6.3 完成项目拓展功能

在3.3中我们已经讨论了增加那些功能，在这里将具体讨论如何实现

1. 扩展物件。创建Item类用于表示物品物件，在房间对象内创建一个存储物品对象的ArrayList<Item>属性，同时在添加look作为合法指令，完善look对应的方法为输出房间对象内存储的所有物品。
2. “back”命令，添加back作为合法指令,并建立对应的方法。在Room类中添加一个表示房间编号的属性，在Game类中添加一个编号对房间的映射。首先对command分析，如果是单个back，默认为后退一格，否则根据后面跟的第二个关键词确定后退几格或是后退到起点，对于不满足这两种情况的要报错处理。可以建立一个栈的数据结构（项目中使用deque）来保存经过的房间编号，回退则依次弹出栈，注意栈不能为空，若回退步数大于栈的大小-1，则报错。回退到起点则弹出知道栈中只剩一个元素。最后令当前房间为栈顶元素对应的房间即可。
3. 随机传送。在Room类中增加一个属性，表示是否为随机传送的房间。在goRoom方法中额外判断进入的房间是否为随机传送房间，如果是，则在房间编号中随机一个（需要保证和进入的房间编号不同）作为当前房间编号。修改当前房间对象即可
4. Player类的相关操作。
   1. 登录操作。游戏初始化前先由玩家输入用户名作为登录依据，若数据库中查询不到对应用户名，则视作新用户登录，在数据库存相关信息后，默认为该用户分配起始地点和负重上限；若数据库存在用户名，则从数据库中读取上次玩家推出时的地点和负重上限。同时在quit方法中加入了修改当前玩家信息的功能。
   2. 在Player类中加入相应属性和获取/修改属性的方法即可。
   3. 加入现在负重的属性，在获取物品时判断加入物品是否导致现有负重超过负重上限即可。
   4. 添加trop和take作为合法指令,并建立对应的方法。在Player类中加入一个ArrayList表示玩家携带的物品，拾取时房间内是否存在该物品，再判断拾取后是否会超过负重上限。拾取后要在房间里清除这个物品，同时要更新玩家携带的物品，修改玩家的现有负重；丢弃身上的物品同理，只是不用判断重量相关。
   5. 打印物品。添加items作为合法指令,并建立对应的方法。遍历玩家和房间内的物品数组，分别输出其名字以及重量和即可。
   6. 魔法饼干。添加eat 为合法指令，并建立对应的方法。该方法类似take，只不过增加负重变为增加负重上限，同时不需要将魔法饼干加入玩家现有的物品，但仍要从房间中删除。
   
5. 数据库相关。

   新建了Dbutil类用来创建和数据库的连接,在需要调用数据库时,通过这个类来执行java文件内的sql语句来实现与数据库的交互。

同时为实现这些功能，对项目中的其他属性和方法进行了一些修改。

### 6.4测试结果

1. 白盒测试

   以PlayerTest类中测试拾取和丢弃物件为例

   ```java
   package cn.edu.whut.sept.zuul;
   
   import org.junit.Test;
   
   import java.util.ArrayList;
   
   public class PlayerTest {
       @Test
       public void takeItem() {
           Player player=new Player(0,"qwq",1000);
           player.takeItem(new Item("apple","an apple",50));
           ArrayList<Item> bag=player.getItems();
           if(bag.get(0).getName().equals("apple")) {
               System.out.println("carryItem Accepted");
           }else{
               System.out.println("carryItem Error");
           }
       }
   
       //此测试必须在carryItem正确的情况下才有效
       @Test
       public void dropItem() {
           Player player=new Player(0,"qwq",1000);
           player.takeItem(new Item("apple","an apple",50));
           player.dropItem("apple");
           ArrayList<Item> bag=player.getItems();
           if(bag.size()==0) {
               System.out.println("dropItem Accepted");
           }else{
               System.out.println("dropItem Error");
           }
       }
   
   
   }
   ```

   通过模拟一个物件的拾取和丢弃,观察玩家背包的变化来分析结果,如果和预期一致则说明方法正确。

   [![88-X9-QZEPC12-PA0-B1-U8-CI1.png](https://i.postimg.cc/VvSRfgJB/88-X9-QZEPC12-PA0-B1-U8-CI1.png)](https://postimg.cc/GTCYzkJH)

   [![ANTCS-T0-V2-MSGWVZBHWJ.png](https://i.postimg.cc/Bn5CnXny/ANTCS-T0-V2-MSGWVZBHWJ.png)](https://postimg.cc/hXjmC4xb)

   

2. 黑盒测试

   使用在和样例工程一样地图结构的前提下,
   在初始房间加入两个物件,
   将初始房间西边设置为随机传送房间
   在初始房间东边的房间放一个魔法饼干

   测试流程如下

   > 1. 新建一个用户test
   > 2. 输入help查看帮助
   > 3. go west 进入随机传送房间
   > 4. back回退到初始房间
   > 5. 再次使用back指令
   > 6. go south进入computing lab
   > 7. go east进入computing admin office
   > 8. back 2回退两步进入初始房间
   > 9. go north尝试进入
   > 10. look查看房间信息以及房间内的物品信息
   > 11. take water拾取物件
   > 12. take banana拾取物件
   > 13. take apple拾取物件
   > 14. items显示房间和背包物品
   > 15. drop banana丢弃物件
   > 16. drop water丢弃物件
   > 17. items查显示房间和背包物品
   > 18. go east进入lecture theater
   > 19. look查看信息
   > 20. eat cookie吃掉魔法饼干提高负重
   > 21. go west回到初始房间
   > 22. take apple拾取物件
   > 23. take banana拾取物件
   > 24. items显示房间和背包物品
   > 25. go south进入computing lab
   > 26. quit退出
   > 27. 重新登录test
   > 28. look查看当前房间状态

   期望结果如下:

   > 1. 新用户注册成功
   >
   > 2. 显示帮助
   > 3. 显示被随机传送到一个房间
   > 4. 回退到初始房间
   > 5. 显示已经在初始房间,无法回退
   > 6. 进入computing lab
   > 7. 进入computing admin office
   > 8. 回退到初始房间
   > 9. 显示没有门无法进入
   > 10. 输出房间信息以及房间内的物品信息
   > 11. 显示没有这个物件,无法拾取
   > 12. 显示成功拾取
   > 13. 显示空间不足
   > 14. 房间内只有apple,背包内只有banana
   > 15. 丢弃成功
   > 16. 没有water,丢弃失败
   > 17. 房间内有apple和banana
   > 18. 进入lecture theater
   > 19. 输出房间信息以及房间内的物品信息
   > 20. 吃点饼干后显示新的负重上限
   > 21. 回到初始房间
   > 22. 成功拾取apple
   > 23. 成功拾取banana
   > 24. 房间内没有物件,背包内有apple和banana
   > 25. 进入computing lab
   > 26. 保存成功并退出
   > 27. 已注册用户登录,显示继续冒险
   > 28. 当前房间信息为上次冒险退出时所在房间

### 6.5 编码格式规范调整

先在github中下载并保存Google 的 Java Code Style （**intellij-java-google-style.xml**）

在Settings->code style->java中导入刚才的xml文件并应用

现在idea的代码风格检查和xml文件中的一致

可以通过code->reformat code的方式来自动调正编码格式

### 6.6提交情况

在个人仓库的commit记录如下所示

[![2-GT-0-WBMYBEZC0-J-X7.png](https://i.postimg.cc/tCjkQ3XP/2-GT-0-WBMYBEZC0-J-X7.png)](https://postimg.cc/Z0wp6yvK)

[![Q1-C-983-C3-E-A-V0-V3-P-HO.png](https://i.postimg.cc/ZRvHZjBP/Q1-C-983-C3-E-A-V0-V3-P-HO.png)](https://postimg.cc/7GD0n3Nb)

## 7.实施过程问题记录与分析

1. 刚开始发现无法直接使用https链接clone到本地

   原因:原项目的属性为private

   解决方法：将classroom内的项目修改为public合并fork到自己的仓库，使用ssh链接clone到本地即可。

2. 加入Player类后运行报错NullPointerException

   原因：初始化顺序出错，导致Player的初始化中使用了没有初始化的对象

   解决方法：调换初始化的顺序

3. 无法连接至数据库

   原因：没有配置与mysql交互的jar文件

   解决方法：在网上下载对应版本的jar包，并在项目结构中的模块添加

4. 新增的代码不满足需求，需要重写

   解决方法：使用git命令进行版本回退，重新编写新的代码。

## 8.任务总结

本次实践任务耗时一周左右，由于是第一次尝试个人使用github来开发一个项目，对于部分操作，如git命令，github的使用并不是十分熟练，但在不断查阅各种资料和尝试后，最终解决了所遇到的问题。本人认为自己在这次实践任务中收获了许多，具体内容如下：

1. 学习了如何使用github,github在以后的学习和工作中占有非常重要的地位，学习和github有关的相关知识是非常有必要的
2. 熟练了对git命令的掌握，在之后可以更好的利用git来进行版本管理
3. 学习了很多开发规范，如代码规范，版本号规范，提交规范等
4. 加强了自己对于面向对象思想的理解，可以在开发中更加熟练的使用这种思想来指导编码
5. 对java语言有了更加深入的了解，熟悉了一些java不同于其他语言的特性
6. 更加熟练了IDEA编辑器，可以更高效的进行开发

## 9.参考文献

1. [基于 angular 规范的 commit](https://www.shuzhiduo.com/A/lk5amGwZ51/)
2. [在IDEA上进行JUnit测试](https://blog.csdn.net/qq_44028290/article/details/108903857)
3. [git：多分支管理](https://blog.csdn.net/qq_56030168/article/details/128163591)
4. [github版本回退](https://www.likecs.com/show-205129316.html)
5. [Intellij IDEA 配置 Code Style](https://blog.csdn.net/dufufd/article/details/100095685)
6. [java通过字符串调用方法](https://blog.csdn.net/fennud_xiaoqiang/article/details/118551287)





