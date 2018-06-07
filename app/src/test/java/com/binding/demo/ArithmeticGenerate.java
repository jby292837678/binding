package binding.com.demo;

import org.junit.Test;

import java.util.Scanner;


/**
 * @author zhuoheng
 */
public class ArithmeticGenerate {
    /**
     * 常量－数字最大值
     */
    private static final int MAX_NUM = 20;

    /**
     * 等号前面的每个数字
     * 因为可能会出现"_"所以为String
     */
    private static String[] numbers = null;

    /**
     * 等号前的每个运算符
     */
    private static String[] symbols = null;

    /**
     * 每次运算后得到的结果
     * 因为可能会出现"_"所以为String
     */
    private static String result = "";

    @Test
    public void main() {

        System.out.println(getQuestion(100));
    }

    /**
     * 获取题目和答案的字符串
     * @param times
     * @return String
     */
    private static String getQuestion(int times) {
        // 随机取填空处的索引值
        int blankIndex = (int) ((Math.random() * (times + 1)) + 1);
        String question = "";

        numbers = new String[times];
        symbols = new String[times - 1];

        for (int i = 0; i < times; i++) {
            if (i == 0) {
                // 第一位只生成一个数字
                int firstNum = (int) ((Math.random() * MAX_NUM) + 1);
                result = String.valueOf(firstNum);
                numbers[i] = i == (blankIndex - 1) ? "_" : String.valueOf(result);
            } else {
                // 第二位开始生成数字和运算符号并计算
                getResult(i);
            }
        }

        // 随机一处变成下划线（等号前和等号后）
        if (blankIndex == (times + 1)) {
            result = "_";
        } else {
            numbers[blankIndex - 1] = "_";
        }

        // 组合题目
        for (int i = 0; i < times; i++) {
            question += i == 0 ? numbers[i] : symbols[i - 1] + numbers[i];
        }

        return question + "=" + result;
    }

    /**
     * 计算每一次运算的结果
     *
     * @param i
     */
    private static void getResult(int i) {
        int num = (int) ((Math.random() * MAX_NUM) + 1);
        int symbolsIndex = (int) ((Math.random() * 2) + 1);
        int compareResult = Integer.parseInt(result);

        switch (symbolsIndex) {
            case 1:
                symbols[i - 1] = "+";
                compareResult = compareResult + num;

                break;

            case 2:
                symbols[i - 1] = "-";
                compareResult = compareResult - num;

                break;

            default:
                break;
        }

        // 每次运算结果都要在0到20之间
        if ((compareResult < 0) || (compareResult > MAX_NUM)) {
            getResult(i);
        } else {
            result = String.valueOf(compareResult);
            numbers[i] = String.valueOf(num);
        }
    }
}
