/**
* @tag     sudoku
* @authors R11happy (xushuai100@126.com)
* @date    2016-10-3 19:40-23:34
* @version 1.0
* @Language java
* @Ranking  null
* @function null
*/

import java.util.Random;

public class Test
{
    private static int[] num = new int[81];
    private static Random random = new Random();
     /**
     * 随机产生一个数独
     * @return 返回一个随机产生的数独
     */
     public static int[] generate()
     {
        for(int i = 0; i<81; i++)
        {
            num[i] = 0;
        }
        solve(0);
        return num;
     }

    /**
     * 递归产生数独位置i的值
     * @param 数独位置pos
     * @return 位置i是否可以填入值
     */
    private static boolean solve(int pos)
    {
        if(pos == 81) return true;
        else if(num[pos] != 0)    return solve(pos+1);
        else
        {
            /* 用数组randOrder存储每个位置可能产生的值，即为1~9 */
            int[] randOrder = new int[10];

            for(int i = 1; i<10; i++)
            {
                randOrder[i] = i;
            }

            /* 洗牌算法
            将数组randOrder变为一个随机存储1~9的数组 */
            for(int i = 1; i<10; i++)
            {
                int shuffle = random.nextInt(10);
                // 将randOrder[i]与randOrder[shuffle]交换
                int tmp = randOrder[i];
                randOrder[i] = randOrder[shuffle];
                randOrder[shuffle] = tmp;
            }

            /* 在位置i随机填入一个值，并且判断是否有效 */
            for(int i = 1; i<10; i++)
            {
                if(isLeagal(pos, randOrder[i]))
                {
                    num[pos] = randOrder[i];
                    if(solve(pos+1))  return true;
                }
            }
        }
        /* 如果在位置i不能填入1~9中的任何值，则需要回溯 */
        num[pos] = 0;
        return false;
    }

    /**
     * 在位置i填入value数字是否有效，通过按行列和小矩阵判断
     * @param 填入的位置pos
     * @param 填入位置pos的数字value
     * @return 在位置pos填入数字value是否有效
     */
    private static boolean isLeagal(int pos, int value)
    {
        if(!isRowLeagal(pos, value))  return false;
        if(!isColLeagal(pos, value))  return false;
        if(!isBoxLeagal(pos, value))  return false;
        return true;
    }

    /**
     * 判断在位置i填入value行规则是否满足
     * @param 填入的位置pos
     * @param 填入位置pos的数字value
     * @return 在位置pos填入数字value行规则是否有效
     */
    private static boolean isRowLeagal(int pos, int value)
    {
        int row = pos / 9;
        int col = pos % 9;
        for(int i = 0; i<9; i++)
        {
            if(value == num[row*9+i])   return false;
        }
        return true;
    }

    /**
     * 判断在位置i填入value列规则是否满足
     * @param 填入的位置pos
     * @param 填入位置pos的数字value
     * @return 在位置pos填入数字value列规则是否有效
     */
    private static boolean isColLeagal(int pos, int value)
    {
        int row = pos / 9;
        int col = pos % 9;
        for(int i = 0; i<9; i++)
        {
            if(value == num[i*9+col])   return false;
        }
        return true;
    }

    /**
     * 判断在位置i填入value小矩阵规则是否满足
     * @param 填入的位置pos
     * @param 填入位置pos的数字value
     * @return 在位置pos填入数字value小矩阵规则是否有效
     */
    private static boolean isBoxLeagal(int pos, int value)
    {
        int row = pos / 9;
        int col = pos % 9;
        int top = row / 3 * 3;
        int left = col / 3 * 3;
        int index = top*9+left; //box左上角的下标
        for(int y = 0; y<3; y++)
        {
            for(int x = 0; x<3; x++)
            {
                if(num[index+y*9+x] == value)   return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] map2D = new int[9][9];
        int[] sudoku = new int[81];

        for (int i = 0; i < 81; i++)
        {
            sudoku[i] = 0;
        }
        sudoku = generate();

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                map2D[i][j] = sudoku[i * 9 + j];
            }
        }

        System.out.println("-----------------------");

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                System.out.print(map2D[i][j] + " ");
                if (j % 3 == 2)
                {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if (i % 3 == 2)
            {
                System.out.println("-----------------------");
            }
        }
    }
}
