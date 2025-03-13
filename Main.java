import java.util.Scanner;

/**
 * 寻找勾股数的程序
 * 给定范围[n,m]，找出所有满足以下条件的三元组(i,j,k):
 * 1. i,j,k均在范围[n,m]内
 * 2. i^2 + j^2 = k^2 (勾股定理)
 * 3. i,j互质且j,k互质
 * 
 * 时间复杂度分析:
 * - 主循环遍历i从n到m,j从i+1到m: O((m-n)^2)
 * - 对每个i,j组合:
 *   - 计算k和验证勾股定理: O(1)
 *   - 计算GCD: O(log max(i,j))
 * 总时间复杂度: O((m-n)^2 * log m)
 * 
 * 空间复杂度分析:
 * - 只使用了常数个变量(n,m,i,j,k等): O(1)
 * - Scanner对象使用的内存是常数: O(1)
 * 总空间复杂度: O(1)
 */
public class Main {
    public static void main(String[] args) {
        // 创建Scanner对象读取输入
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextInt()) {
                // 读取范围的下界n和上界m
                int n = in.nextInt();
                int m = in.nextInt();
                boolean found = false;
                
                // 遍历所有可能的i,j组合
                for (int i = n; i <= m; i++) {
                    for (int j = i + 1; j <= m; j++) {
                        // 计算可能的斜边长k
                        int k = (int) Math.sqrt(i * i + j * j);
                        // 如果k超出范围则跳出内层循环
                        if (k > m) {
                            break;
                        }
                        // 验证是否为勾股数
                        if (k * k == i * i + j * j) {
                            // 检查互质条件
                            if (gcd(i, j) == 1 && gcd(j, k) == 1) {
                                System.out.printf("%d %d %d\n", i, j, k);
                                found = true;
                            }
                        }
                    }
                }
                // 如果没有找到符合条件的三元组，输出Na
                if (!found) {
                    System.out.println("Na");
                }
            }
        }
    }

    /**
     * 计算两个数的最大公约数
     * @param a 第一个数
     * @param b 第二个数
     * @return 最大公约数
     */
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}