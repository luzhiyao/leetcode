package src.slidingwindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LeetCode 3. 无重复字符的最长子串 (Longest Substring Without Repeating Characters)
 * 
 * 问题描述：
 * 给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。
 * 
 * 示例：
 * 输入: s = "abcabcbb"
 * 输出: 3 
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是子串的长度，"pwke" 是一个子序列，不是子串。
 * 
 * 时间复杂度：O(n)，其中n是字符串的长度
 * 空间复杂度：O(min(m,n))，其中m是字符集的大小，n是字符串的长度
 */
public class LongestSubstringWithoutRepeatingCharacters {
    
    /**
     * 滑动窗口 + HashSet解法
     * 使用滑动窗口维护无重复字符的子串
     * 使用HashSet检查字符是否重复
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int maxLength = 0;
        int left = 0, right = 0;
        
        while (right < n) {
            char c = s.charAt(right);
            
            // 如果窗口中已经包含当前字符，则移动左指针直到窗口中不包含当前字符
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }
            
            // 将当前字符加入窗口
            set.add(c);
            
            // 更新最大长度
            maxLength = Math.max(maxLength, right - left + 1);
            
            // 移动右指针
            right++;
        }
        
        return maxLength;
    }
    
    /**
     * 滑动窗口 + HashMap优化解法
     * 使用HashMap记录字符最后一次出现的位置，可以直接将左指针移动到正确的位置
     */
    public int lengthOfLongestSubstringOptimized(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        
        // 滑动窗口：左边界i，右边界j
        for (int i = 0, j = 0; j < n; j++) {
            char c = s.charAt(j);
            
            // 如果当前字符已经在窗口中，则更新左边界
            if (map.containsKey(c)) {
                // 左边界更新为字符上次出现位置的下一个位置和当前左边界中的较大值
                // （避免左边界向左移动）
                i = Math.max(i, map.get(c) + 1);
            }
            
            // 更新最大长度
            maxLength = Math.max(maxLength, j - i + 1);
            
            // 记录字符最后一次出现的位置
            map.put(c, j);
        }
        
        return maxLength;
    }
    
    /**
     * ASCII优化解法
     * 当字符集较小时（如ASCII码），可以使用数组代替HashMap，提高性能
     */
    public int lengthOfLongestSubstringASCII(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int maxLength = 0;
        
        // 用数组记录字符最后一次出现的位置，初始值为-1表示未出现过
        int[] lastPos = new int[128]; // ASCII字符集大小为128
        for (int i = 0; i < 128; i++) {
            lastPos[i] = -1;
        }
        
        // 滑动窗口：左边界left
        int left = 0;
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            
            // 如果字符已经在窗口中，则更新左边界
            if (lastPos[c] >= left) {
                left = lastPos[c] + 1;
            }
            
            // 更新最大长度
            maxLength = Math.max(maxLength, i - left + 1);
            
            // 记录字符最后一次出现的位置
            lastPos[c] = i;
        }
        
        return maxLength;
    }
    
    // 测试方法
    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();
        
        // 测试用例1
        String s1 = "abcabcbb";
        System.out.println("测试用例1: s = \"" + s1 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s1));  // 预期输出: 3
        System.out.println("HashMap解法: " + solution.lengthOfLongestSubstringOptimized(s1));
        System.out.println("ASCII解法: " + solution.lengthOfLongestSubstringASCII(s1));
        
        // 测试用例2
        String s2 = "bbbbb";
        System.out.println("\n测试用例2: s = \"" + s2 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s2));  // 预期输出: 1
        System.out.println("HashMap解法: " + solution.lengthOfLongestSubstringOptimized(s2));
        System.out.println("ASCII解法: " + solution.lengthOfLongestSubstringASCII(s2));
        
        // 测试用例3
        String s3 = "pwwkew";
        System.out.println("\n测试用例3: s = \"" + s3 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s3));  // 预期输出: 3
        System.out.println("HashMap解法: " + solution.lengthOfLongestSubstringOptimized(s3));
        System.out.println("ASCII解法: " + solution.lengthOfLongestSubstringASCII(s3));
        
        // 测试用例4: 空字符串
        String s4 = "";
        System.out.println("\n测试用例4: s = \"" + s4 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s4));  // 预期输出: 0
        System.out.println("HashMap解法: " + solution.lengthOfLongestSubstringOptimized(s4));
        System.out.println("ASCII解法: " + solution.lengthOfLongestSubstringASCII(s4));
        
        // 测试用例5: 特殊情况
        String s5 = "tmmzuxt";
        System.out.println("\n测试用例5: s = \"" + s5 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s5));  // 预期输出: 5
        System.out.println("HashMap解法: " + solution.lengthOfLongestSubstringOptimized(s5));
        System.out.println("ASCII解法: " + solution.lengthOfLongestSubstringASCII(s5));
    }
} 