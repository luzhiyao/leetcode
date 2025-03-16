package src.dp;

/**
 * LeetCode 1143. 最长公共子序列 (Longest Common Subsequence)
 * 
 * 问题描述：
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。
 * 如果不存在 公共子序列 ，返回 0 。
 * 
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 * 
 * 示例：
 * 输入：text1 = "abcde", text2 = "ace" 
 * 输出：3  
 * 解释：最长公共子序列是 "ace"，它的长度为 3。
 * 
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc"，它的长度为 3。
 * 
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0。
 * 
 * 时间复杂度：O(m*n)，其中m和n分别是两个字符串的长度
 * 空间复杂度：O(m*n)，二维dp数组的大小
 */
public class LongestCommonSubsequence {
    
    /**
     * 动态规划解法
     * dp[i][j]表示text1[0...i-1]和text2[0...j-1]的最长公共子序列长度
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // 创建dp数组，dp[i][j]表示text1[0...i-1]和text2[0...j-1]的最长公共子序列长度
        int[][] dp = new int[m + 1][n + 1];
        
        // 填充dp数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前字符相同，则最长公共子序列长度加1
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 否则，取两种情况的最大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * 空间优化的动态规划解法
     * 只使用一维数组存储状态
     */
    public int longestCommonSubsequenceOptimized(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        
        // 确保text1是较短的字符串，以减少空间复杂度
        if (text1.length() > text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // 只使用一维数组
        int[] dp = new int[m + 1];
        
        // 辅助数组，用于存储上一行的左上角值
        int[] prev = new int[m + 1];
        
        for (int j = 1; j <= n; j++) {
            // 保存当前行的dp值，用于下一轮迭代
            System.arraycopy(dp, 0, prev, 0, m + 1);
            
            for (int i = 1; i <= m; i++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i] = prev[i - 1] + 1;
                } else {
                    dp[i] = Math.max(dp[i - 1], prev[i]);
                }
            }
        }
        
        return dp[m];
    }
    
    /**
     * 进一步空间优化的动态规划解法
     * 只使用两个变量存储必要的状态
     */
    public int longestCommonSubsequenceMoreOptimized(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        
        // 确保text1是较短的字符串，以减少空间复杂度
        if (text1.length() > text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // 只使用一维数组，dp[i]表示当前行text1[0...i-1]和text2[0...j-1]的最长公共子序列长度
        int[] dp = new int[m + 1];
        
        for (int j = 1; j <= n; j++) {
            int prev = 0; // 保存左上角的值
            
            for (int i = 1; i <= m; i++) {
                int temp = dp[i]; // 暂存当前dp[i]，作为下次迭代的左上角值
                
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i] = prev + 1;
                } else {
                    dp[i] = Math.max(dp[i - 1], dp[i]);
                }
                
                prev = temp; // 更新左上角值
            }
        }
        
        return dp[m];
    }
    
    /**
     * 返回最长公共子序列字符串
     */
    public String getLCS(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return "";
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // 创建dp数组
        int[][] dp = new int[m + 1][n + 1];
        
        // 填充dp数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // 回溯构建最长公共子序列
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // 当前字符是LCS的一部分
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // 向上移动
                i--;
            } else {
                // 向左移动
                j--;
            }
        }
        
        // 反转字符串得到正确顺序
        return lcs.reverse().toString();
    }
    
    // 测试方法
    public static void main(String[] args) {
        LongestCommonSubsequence solution = new LongestCommonSubsequence();
        
        // 测试用例1
        String text1_1 = "abcde";
        String text2_1 = "ace";
        System.out.println("测试用例1: text1 = \"" + text1_1 + "\", text2 = \"" + text2_1 + "\"");
        System.out.println("标准DP解法: " + solution.longestCommonSubsequence(text1_1, text2_1)); // 预期输出: 3
        System.out.println("空间优化解法: " + solution.longestCommonSubsequenceOptimized(text1_1, text2_1));
        System.out.println("进一步优化解法: " + solution.longestCommonSubsequenceMoreOptimized(text1_1, text2_1));
        System.out.println("最长公共子序列: " + solution.getLCS(text1_1, text2_1)); // 预期输出: "ace"
        
        // 测试用例2
        String text1_2 = "abc";
        String text2_2 = "abc";
        System.out.println("\n测试用例2: text1 = \"" + text1_2 + "\", text2 = \"" + text2_2 + "\"");
        System.out.println("标准DP解法: " + solution.longestCommonSubsequence(text1_2, text2_2)); // 预期输出: 3
        System.out.println("空间优化解法: " + solution.longestCommonSubsequenceOptimized(text1_2, text2_2));
        System.out.println("进一步优化解法: " + solution.longestCommonSubsequenceMoreOptimized(text1_2, text2_2));
        System.out.println("最长公共子序列: " + solution.getLCS(text1_2, text2_2)); // 预期输出: "abc"
        
        // 测试用例3
        String text1_3 = "abc";
        String text2_3 = "def";
        System.out.println("\n测试用例3: text1 = \"" + text1_3 + "\", text2 = \"" + text2_3 + "\"");
        System.out.println("标准DP解法: " + solution.longestCommonSubsequence(text1_3, text2_3)); // 预期输出: 0
        System.out.println("空间优化解法: " + solution.longestCommonSubsequenceOptimized(text1_3, text2_3));
        System.out.println("进一步优化解法: " + solution.longestCommonSubsequenceMoreOptimized(text1_3, text2_3));
        System.out.println("最长公共子序列: " + solution.getLCS(text1_3, text2_3)); // 预期输出: ""
        
        // 测试用例4
        String text1_4 = "bsbininm";
        String text2_4 = "jmjkbkjkv";
        System.out.println("\n测试用例4: text1 = \"" + text1_4 + "\", text2 = \"" + text2_4 + "\"");
        System.out.println("标准DP解法: " + solution.longestCommonSubsequence(text1_4, text2_4));
        System.out.println("空间优化解法: " + solution.longestCommonSubsequenceOptimized(text1_4, text2_4));
        System.out.println("进一步优化解法: " + solution.longestCommonSubsequenceMoreOptimized(text1_4, text2_4));
        System.out.println("最长公共子序列: " + solution.getLCS(text1_4, text2_4));
        
        // 测试用例5 - 较长的字符串
        String text1_5 = "pmjghexybyrgzczy";
        String text2_5 = "hafcdqbgncrcbihkd";
        System.out.println("\n测试用例5: 较长字符串");
        System.out.println("标准DP解法: " + solution.longestCommonSubsequence(text1_5, text2_5));
        System.out.println("空间优化解法: " + solution.longestCommonSubsequenceOptimized(text1_5, text2_5));
        System.out.println("进一步优化解法: " + solution.longestCommonSubsequenceMoreOptimized(text1_5, text2_5));
        System.out.println("最长公共子序列: " + solution.getLCS(text1_5, text2_5));
    }
} 