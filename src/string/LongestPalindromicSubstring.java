package src.string;

/**
 * LeetCode 5. 最长回文子串 (Longest Palindromic Substring)
 * 
 * 问题描述：
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 * 
 * 示例1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 
 * 示例2：
 * 输入：s = "cbbd"
 * 输出："bb"
 * 
 * 示例3：
 * 输入：s = "a"
 * 输出："a"
 * 
 * 示例4：
 * 输入：s = "ac"
 * 输出："a"
 * 
 * 方法1: 动态规划 - 时间复杂度O(n²)，空间复杂度O(n²)
 * 方法2: 中心扩展法 - 时间复杂度O(n²)，空间复杂度O(1)
 * 方法3: Manacher算法 - 时间复杂度O(n)，空间复杂度O(n)
 */
public class LongestPalindromicSubstring {
    
    /**
     * 方法1：动态规划
     * 
     * 思路：定义状态 dp[i][j] 表示子串 s[i...j] 是否为回文子串
     * 状态转移方程：
     * - 如果 s[i] != s[j]，则 dp[i][j] = false
     * - 如果 s[i] == s[j]，则:
     *   - 如果 j - i < 3，即子串长度小于等于3，则 dp[i][j] = true
     *   - 否则 dp[i][j] = dp[i+1][j-1]
     * 
     * 时间复杂度：O(n²)
     * 空间复杂度：O(n²)
     */
    public String longestPalindromeDP(String s) {
        int n = s.length();
        if (n < 2) {
            return s;
        }
        
        // 定义状态数组
        boolean[][] dp = new boolean[n][n];
        
        // 单个字符都是回文
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // 记录最长回文子串的起始位置和长度
        int maxLength = 1;
        int begin = 0;
        
        // 枚举子串长度
        for (int len = 2; len <= n; len++) {
            // 枚举起始位置
            for (int i = 0; i <= n - len; i++) {
                // 计算终止位置
                int j = i + len - 1;
                
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    // 如果子串长度小于等于3，则一定是回文
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        // 状态转移
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                
                // 如果是回文且长度更大，则更新结果
                if (dp[i][j] && len > maxLength) {
                    maxLength = len;
                    begin = i;
                }
            }
        }
        
        return s.substring(begin, begin + maxLength);
    }
    
    /**
     * 方法2：中心扩展法
     * 
     * 思路：回文中心的两侧互为镜像，从每一个位置出发，向两边扩展，直到不能扩展为止。
     * 需要考虑两种情况：
     * 1. 回文长度为奇数，中心是一个字符
     * 2. 回文长度为偶数，中心是两个字符之间的空隙
     * 
     * 时间复杂度：O(n²)
     * 空间复杂度：O(1)
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // 考虑奇数长度的回文
            int len1 = expandAroundCenter(s, i, i);
            // 考虑偶数长度的回文
            int len2 = expandAroundCenter(s, i, i + 1);
            // 取较大值
            int len = Math.max(len1, len2);
            
            // 更新最长回文子串的位置
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回回文长度
        return right - left - 1;
    }
    
    /**
     * 方法3：Manacher算法
     * 
     * 思路：Manacher算法是专门用来查找最长回文子串的线性算法。
     * 1. 预处理字符串，在每个字符间插入特殊字符，使得所有回文都是奇数长度的
     * 2. 使用一个数组p，p[i]表示以i为中心的最长回文半径
     * 3. 利用回文的对称性，在已知回文的情况下快速计算新的位置
     * 
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public String longestPalindromeManacher(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        // 预处理，将字符串转换成特殊格式
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        for (char c : s.toCharArray()) {
            sb.append(c).append("#");
        }
        
        String t = sb.toString();
        int n = t.length();
        
        // p[i]表示以i为中心的最长回文半径
        int[] p = new int[n];
        
        // 维护已知最长回文的中心和右边界
        int center = 0, right = 0;
        
        // 记录最长回文的中心和长度
        int maxLen = 0, maxCenter = 0;
        
        for (int i = 0; i < n; i++) {
            // 利用对称性
            if (right > i) {
                // 找到i关于center的对称点j
                int j = 2 * center - i;
                // p[i]至少为p[j]和right-i中的较小值
                p[i] = Math.min(right - i, p[j]);
            }
            
            // 尝试扩展
            int a = i + (1 + p[i]);
            int b = i - (1 + p[i]);
            while (a < n && b >= 0 && t.charAt(a) == t.charAt(b)) {
                p[i]++;
                a++;
                b--;
            }
            
            // 更新right和center
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
            
            // 更新最长回文信息
            if (p[i] > maxLen) {
                maxLen = p[i];
                maxCenter = i;
            }
        }
        
        // 计算原始字符串中的开始位置和长度
        int start = (maxCenter - maxLen) / 2;
        
        return s.substring(start, start + maxLen);
    }
    
    /**
     * 方法4：暴力法（仅作参考）
     * 
     * 思路：检查所有可能的子串是否为回文
     * 
     * 时间复杂度：O(n³)
     * 空间复杂度：O(1)
     */
    public String longestPalindromeBruteForce(String s) {
        int n = s.length();
        if (n < 2) {
            return s;
        }
        
        int maxLength = 1;
        int begin = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (j - i + 1 > maxLength && isPalindrome(s, i, j)) {
                    maxLength = j - i + 1;
                    begin = i;
                }
            }
        }
        
        return s.substring(begin, begin + maxLength);
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    public static void main(String[] args) {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        
        // 测试用例1
        String s1 = "babad";
        System.out.println("输入: \"" + s1 + "\"");
        System.out.println("动态规划结果: " + solution.longestPalindromeDP(s1));
        System.out.println("中心扩展法结果: " + solution.longestPalindrome(s1));
        System.out.println("Manacher算法结果: " + solution.longestPalindromeManacher(s1));
        System.out.println("暴力法结果: " + solution.longestPalindromeBruteForce(s1));
        
        // 测试用例2
        String s2 = "cbbd";
        System.out.println("\n输入: \"" + s2 + "\"");
        System.out.println("动态规划结果: " + solution.longestPalindromeDP(s2));
        System.out.println("中心扩展法结果: " + solution.longestPalindrome(s2));
        System.out.println("Manacher算法结果: " + solution.longestPalindromeManacher(s2));
        System.out.println("暴力法结果: " + solution.longestPalindromeBruteForce(s2));
        
        // 测试用例3
        String s3 = "a";
        System.out.println("\n输入: \"" + s3 + "\"");
        System.out.println("动态规划结果: " + solution.longestPalindromeDP(s3));
        System.out.println("中心扩展法结果: " + solution.longestPalindrome(s3));
        System.out.println("Manacher算法结果: " + solution.longestPalindromeManacher(s3));
        System.out.println("暴力法结果: " + solution.longestPalindromeBruteForce(s3));
        
        // 测试用例4
        String s4 = "ac";
        System.out.println("\n输入: \"" + s4 + "\"");
        System.out.println("动态规划结果: " + solution.longestPalindromeDP(s4));
        System.out.println("中心扩展法结果: " + solution.longestPalindrome(s4));
        System.out.println("Manacher算法结果: " + solution.longestPalindromeManacher(s4));
        System.out.println("暴力法结果: " + solution.longestPalindromeBruteForce(s4));
        
        // 测试用例5：长回文
        String s5 = "abababababababababababa";
        System.out.println("\n输入: \"" + s5 + "\"");
        System.out.println("动态规划结果: " + solution.longestPalindromeDP(s5));
        System.out.println("中心扩展法结果: " + solution.longestPalindrome(s5));
        System.out.println("Manacher算法结果: " + solution.longestPalindromeManacher(s5));
        // 暴力法太慢，不测试
        
        // 测试用例6：特殊字符
        String s6 = "!@#$%^&*()!@#$%^&*()";
        System.out.println("\n输入: \"" + s6 + "\"");
        System.out.println("动态规划结果: " + solution.longestPalindromeDP(s6));
        System.out.println("中心扩展法结果: " + solution.longestPalindrome(s6));
        System.out.println("Manacher算法结果: " + solution.longestPalindromeManacher(s6));
        System.out.println("暴力法结果: " + solution.longestPalindromeBruteForce(s6));
    }
} 