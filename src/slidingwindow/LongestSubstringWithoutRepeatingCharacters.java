package src.slidingwindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LeetCode 3. 无重复字符的最长子串 (Longest Substring Without Repeating Characters)
 * 
 * 问题描述：
 * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
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
 * 时间复杂度：O(n)，其中 n 是字符串的长度
 * 空间复杂度：O(min(m, n))，其中 m 是字符集的大小，n 是字符串的长度
 */
public class LongestSubstringWithoutRepeatingCharacters {
    
    /**
     * 滑动窗口解法（使用HashSet）
     * 思路：维护一个滑动窗口，窗口内的所有字符都是不重复的
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        Set<Character> set = new HashSet<>(); // 用于记录窗口中的字符
        int maxLength = 0; // 最长子串的长度
        int left = 0; // 窗口左边界
        int right = 0; // 窗口右边界
        
        while (right < n) {
            char c = s.charAt(right);
            
            // 如果窗口中已经包含了当前字符，则移动左边界，直到窗口中不包含当前字符
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }
            
            // 将当前字符加入窗口
            set.add(c);
            
            // 更新最长子串的长度
            maxLength = Math.max(maxLength, right - left + 1);
            
            // 移动右边界扩大窗口
            right++;
        }
        
        return maxLength;
    }
    
    /**
     * 优化的滑动窗口解法（使用HashMap）
     * 思路：使用HashMap记录每个字符最后出现的位置，直接跳过中间的字符
     */
    public int lengthOfLongestSubstringOptimized(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        Map<Character, Integer> map = new HashMap<>(); // 记录字符最后出现的位置
        int maxLength = 0; // 最长子串的长度
        
        // 尝试以每个字符为起点，找出最长的无重复字符子串
        for (int start = 0, end = 0; end < n; end++) {
            char c = s.charAt(end);
            
            // 如果字符已经在窗口中，更新start位置
            if (map.containsKey(c)) {
                // 更新start为上一次出现位置的下一个位置和当前start的较大值
                start = Math.max(map.get(c) + 1, start);
            }
            
            // 更新最长子串的长度
            maxLength = Math.max(maxLength, end - start + 1);
            
            // 更新字符的最新位置
            map.put(c, end);
        }
        
        return maxLength;
    }
    
    /**
     * 滑动窗口解法（使用数组）
     * 对于ASCII字符集（256个字符），可以使用数组代替HashMap，提高效率
     */
    public int lengthOfLongestSubstringArray(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int n = s.length();
        int[] index = new int[256]; // 记录每个字符最后出现的位置（假设字符集为ASCII）
        
        // 初始化数组，-1表示字符尚未出现
        for (int i = 0; i < 256; i++) {
            index[i] = -1;
        }
        
        int maxLength = 0; // 最长子串的长度
        int start = 0; // 窗口起始位置
        
        for (int end = 0; end < n; end++) {
            char c = s.charAt(end);
            
            // 如果字符已在窗口中出现，更新start位置
            if (index[c] >= start) {
                start = index[c] + 1;
            }
            
            // 更新最长子串的长度
            maxLength = Math.max(maxLength, end - start + 1);
            
            // 更新字符的最新位置
            index[c] = end;
        }
        
        return maxLength;
    }
    
    // 测试方法
    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();
        
        // 测试例子
        String s1 = "abcabcbb";
        System.out.println("输入: \"" + s1 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s1));
        System.out.println("HashMap优化解法: " + solution.lengthOfLongestSubstringOptimized(s1));
        System.out.println("数组解法: " + solution.lengthOfLongestSubstringArray(s1));
        
        String s2 = "bbbbb";
        System.out.println("\n输入: \"" + s2 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s2));
        System.out.println("HashMap优化解法: " + solution.lengthOfLongestSubstringOptimized(s2));
        System.out.println("数组解法: " + solution.lengthOfLongestSubstringArray(s2));
        
        String s3 = "pwwkew";
        System.out.println("\n输入: \"" + s3 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s3));
        System.out.println("HashMap优化解法: " + solution.lengthOfLongestSubstringOptimized(s3));
        System.out.println("数组解法: " + solution.lengthOfLongestSubstringArray(s3));
        
        String s4 = "aab";
        System.out.println("\n输入: \"" + s4 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s4));
        System.out.println("HashMap优化解法: " + solution.lengthOfLongestSubstringOptimized(s4));
        System.out.println("数组解法: " + solution.lengthOfLongestSubstringArray(s4));
        
        String s5 = "dvdf";
        System.out.println("\n输入: \"" + s5 + "\"");
        System.out.println("HashSet解法: " + solution.lengthOfLongestSubstring(s5));
        System.out.println("HashMap优化解法: " + solution.lengthOfLongestSubstringOptimized(s5));
        System.out.println("数组解法: " + solution.lengthOfLongestSubstringArray(s5));
    }
} 