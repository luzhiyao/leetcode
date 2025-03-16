package src.string;

/**
 * LeetCode 5. 最长回文子串 (Longest Palindromic Substring)
 * 
 * 问题描述：
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 * 
 * 示例：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 
 * 输入：s = "cbbd"
 * 输出："bb"
 * 
 * 输入：s = "a"
 * 输出："a"
 * 
 * 时间复杂度：O(n²)，其中n是字符串的长度
 * 空间复杂度：O(1)，不考虑返回结果的空间
 */
public class LongestPalindromicSubstring {
    
    /**
     * 中心扩展法
     * 从每个位置向两边扩展，寻找最长回文子串
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        int start = 0, end = 0; // 最长回文子串的起始和结束位置
        
        for (int i = 0; i < s.length(); i++) {
            // 以当前字符为中心的奇数长度回文串
            int len1 = expandAroundCenter(s, i, i);
            // 以当前字符与下一字符之间的空隙为中心的偶数长度回文串
            int len2 = expandAroundCenter(s, i, i + 1);
            // 取两者中较长的长度
            int len = Math.max(len1, len2);
            
            // 如果找到更长的回文子串，更新起始和结束位置
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    /**
     * 从指定位置向两边扩展，寻找回文字符串
     * @param s 原始字符串
     * @param left 左边界起始位置
     * @param right 右边界起始位置
     * @return 找到的回文子串长度
     */
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回回文子串的长度（注意索引调整）
        return right - left - 1;
    }
    
    /**
     * 动态规划解法
     * 定义dp[i][j]表示s[i...j]是否为回文子串
     */
    public String longestPalindromeDP(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; // dp[i][j] 表示s[i...j]是否是回文子串
        
        int maxLength = 1; // 最长回文子串的长度，至少为1
        int start = 0; // 最长回文子串的起始位置
        
        // 所有长度为1的子串都是回文串
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // 检查长度为2的子串
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }
        
        // 检查长度大于2的子串
        for (int len = 3; len <= n; len++) { // 子串长度
            for (int i = 0; i <= n - len; i++) { // 子串起始位置
                int j = i + len - 1; // 子串结束位置
                
                // 如果首尾字符相同且中间部分是回文串
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    
                    if (len > maxLength) {
                        start = i;
                        maxLength = len;
                    }
                }
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    /**
     * Manacher算法
     * 一种线性时间复杂度的算法，专门用于寻找最长回文子串
     */
    public String longestPalindromeManacher(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        // 预处理字符串，在字符间插入特殊字符，避免处理奇偶长度
        String t = preProcess(s);
        int n = t.length();
        
        // p[i]表示以i为中心的回文子串的半径（不包括中心）
        int[] p = new int[n];
        
        int center = 0, right = 0; // 已知的最右回文子串的中心和右边界
        
        for (int i = 1; i < n - 1; i++) {
            // 初始化p[i]
            if (right > i) {
                // 利用已知的回文信息进行优化
                p[i] = Math.min(right - i, p[2 * center - i]);
            } else {
                p[i] = 0;
            }
            
            // 中心扩展
            while (i - 1 - p[i] >= 0 && i + 1 + p[i] < n && 
                   t.charAt(i - 1 - p[i]) == t.charAt(i + 1 + p[i])) {
                p[i]++;
            }
            
            // 更新center和right
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }
        
        // 找到最长回文子串的中心和长度
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }
        
        // 计算原始字符串中的起始位置
        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
    
    /**
     * Manacher算法的预处理函数
     * 在字符间插入特殊字符，使得所有回文子串长度都为奇数
     */
    private String preProcess(String s) {
        int n = s.length();
        if (n == 0) {
            return "^$";
        }
        
        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < n; i++) {
            sb.append("#").append(s.charAt(i));
        }
        sb.append("#$");
        
        return sb.toString();
    }
    
    // 测试方法
    public static void main(String[] args) {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        
        // 测试用例1
        String s1 = "babad";
        System.out.println("测试用例1: s = \"" + s1 + "\"");
        System.out.println("中心扩展法: " + solution.longestPalindrome(s1)); // 预期输出: "bab" 或 "aba"
        System.out.println("动态规划法: " + solution.longestPalindromeDP(s1));
        System.out.println("Manacher算法: " + solution.longestPalindromeManacher(s1));
        
        // 测试用例2
        String s2 = "cbbd";
        System.out.println("\n测试用例2: s = \"" + s2 + "\"");
        System.out.println("中心扩展法: " + solution.longestPalindrome(s2)); // 预期输出: "bb"
        System.out.println("动态规划法: " + solution.longestPalindromeDP(s2));
        System.out.println("Manacher算法: " + solution.longestPalindromeManacher(s2));
        
        // 测试用例3
        String s3 = "a";
        System.out.println("\n测试用例3: s = \"" + s3 + "\"");
        System.out.println("中心扩展法: " + solution.longestPalindrome(s3)); // 预期输出: "a"
        System.out.println("动态规划法: " + solution.longestPalindromeDP(s3));
        System.out.println("Manacher算法: " + solution.longestPalindromeManacher(s3));
        
        // 测试用例4 - 较长的回文串
        String s4 = "abacdfgdcaba";
        System.out.println("\n测试用例4: s = \"" + s4 + "\"");
        System.out.println("中心扩展法: " + solution.longestPalindrome(s4)); // 预期输出: "aba"
        System.out.println("动态规划法: " + solution.longestPalindromeDP(s4));
        System.out.println("Manacher算法: " + solution.longestPalindromeManacher(s4));
        
        // 测试用例5 - 较长的字符串
        String s5 = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
        System.out.println("\n测试用例5: 长字符串(长度 " + s5.length() + ")");
        long startTime = System.currentTimeMillis();
        String result1 = solution.longestPalindrome(s5);
        long endTime = System.currentTimeMillis();
        System.out.println("中心扩展法: " + result1 + " (用时: " + (endTime - startTime) + "ms)");
        
        startTime = System.currentTimeMillis();
        String result2 = solution.longestPalindromeDP(s5);
        endTime = System.currentTimeMillis();
        System.out.println("动态规划法: " + result2 + " (用时: " + (endTime - startTime) + "ms)");
        
        startTime = System.currentTimeMillis();
        String result3 = solution.longestPalindromeManacher(s5);
        endTime = System.currentTimeMillis();
        System.out.println("Manacher算法: " + result3 + " (用时: " + (endTime - startTime) + "ms)");
    }
} 