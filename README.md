## 设计：

1. 实体

   - FormationTree

     FormationTree本质上是一颗二叉树，左右子树均为FormationTree；叶子节点是特殊的FormationTree，仅包含一个Letter属性。其中有一个特殊实例invalidTree，表示非良构的FormationTree。

   - Letter

     Letter是命题字母，其中封装了一个字符串。

   - Connective

     Connective是封装连接词的枚举。

2. Parser类

   parsePropositon: 输入一个命题字符串，输出对应的FormationTree。如果命题非良构，则返回invalidTree，否则递归地解析命题字符串。

   checkLetter: 输入一个字符串，通过正则表达式

   ```reg
   ^[A-Z]+(_\{[0-9]+\})?$
   ```

   判断其是否是命题字幕

3. FormationTreeGUI类

   drawTree: 输入相对坐标 x, y, 绘板宽度 width, 递归绘制FormationTree。



## 如何运行?

打开shell，输入

```shell
# 命令行
cd src
javac biz/Main.java
java biz.Main
```

```shell
# GUI
cd src
javac application/FormationTreeGUI.java
java application.FormationTreeGUI
```

## 测试用例:

test.txt
