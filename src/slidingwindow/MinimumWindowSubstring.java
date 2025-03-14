package src.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 76. 最小覆盖子串 (Minimum Window Substring)
 * 
 * 问题描述：
 * 给你一个字符串 s 和一个字符串 t ，请你找出 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""。
 * 
 * 注意：
 * - 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * - 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * 
 * 示例：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 解释：覆盖子串必须包含字符串 t 的所有字符，且数量要满足要求。
 * 
 * 输入：s = "a", t = "a"
 * 输出："a"
 * 
 * 输入：s = "a", t = "aa"
 * 输出：""
 * 解释：t 中的两个字符 'a' 均应包含在 s 的子串中，由于 s 中只有一个 'a'，无法满足要求。
 * 
 * 时间复杂度：O(n)，其中 n 是字符串 s 的长度
 * 空间复杂度：O(m)，其中 m 是字符集的大小，本题中 m 最大为 128（ASCII 字符集）
 */
public class MinimumWindowSubstring {
    
    /**
     * 滑动窗口解法
     * 
     * 算法思路：
     * 1. 使用两个指针（left 和 right）表示窗口的边界
     * 2. 右指针不断向右扩展，直到窗口包含 t 中所有字符
     * 3. 找到一个可行窗口后，左指针向右收缩，尝试找到更小的窗口
     * 4. 重复步骤 2 和 3，直到右指针到达字符串末尾
     * 
     * @param s 源字符串
     * @param t 目标字符串
     * @return s 中涵盖 t 所有字符的最小子串
     */
    public String minWindow(String s, String t) {
        if (s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        
        // 记录目标字符串 t 中每个字符出现的次数
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }
        
        // 窗口中包含的目标字符数量
        int matched = 0;
        // 所需的匹配字符数量
        int required = targetMap.size();
        
        // 记录窗口中每个字符出现的次数
        Map<Character, Integer> windowMap = new HashMap<>();
        
        // 记录最小窗口的起始位置和长度
        int minLeft = 0;
        int minLen = Integer.MAX_VALUE;
        
        // 滑动窗口的左右边界
        int left = 0, right = 0;
        
        while (right < s.length()) {
            // 获取右边界字符
            char rightChar = s.charAt(right);
            
            // 更新窗口中的字符计数
            windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) + 1);
            
            // 如果窗口中的某个字符数量满足了目标要求，增加已匹配字符数量
            if (targetMap.containsKey(rightChar) && 
                windowMap.get(rightChar).intValue() == targetMap.get(rightChar).intValue()) {
                matched++;
            }
            
            // 当找到一个可行窗口时，尝试收缩左边界
            while (left <= right && matched == required) {
                // 更新最小窗口信息
                int currentLen = right - left + 1;
                if (currentLen < minLen) {
                    minLen = currentLen;
                    minLeft = left;
                }
                
                // 获取左边界字符
                char leftChar = s.charAt(left);
                
                // 更新窗口中的字符计数
                windowMap.put(leftChar, windowMap.get(leftChar) - 1);
                
                // 如果移除左边界字符导致窗口不再满足目标要求，减少已匹配字符数量
                if (targetMap.containsKey(leftChar) && 
                    windowMap.get(leftChar).intValue() < targetMap.get(leftChar).intValue()) {
                    matched--;
                }
                
                // 移动左边界
                left++;
            }
            
            // 移动右边界
            right++;
        }
        
        // 如果找到了满足条件的子串，返回最小窗口子串
        // 否则返回空字符串
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
    
    /**
     * 优化的滑动窗口解法，使用数组代替 HashMap
     * 
     * 算法思路与滑动窗口解法相同，但使用数组来存储字符计数，
     * 适用于已知字符集范围的情况（如 ASCII 字符）。
     * 
     * @param s 源字符串
     * @param t 目标字符串
     * @return s 中涵盖 t 所有字符的最小子串
     */
    public String minWindowOptimized(String s, String t) {
        if (s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        
        // 使用数组记录字符计数，假设输入是 ASCII 字符
        int[] targetCount = new int[128];
        int[] windowCount = new int[128];
        
        // 统计目标字符串中每个字符的数量
        for (char c : t.toCharArray()) {
            targetCount[c]++;
        }
        
        // 记录需要匹配的不同字符的数量
        int requiredChars = 0;
        for (int count : targetCount) {
            if (count > 0) {
                requiredChars++;
            }
        }
        
        // 已经匹配的不同字符的数量
        int matchedChars = 0;
        
        // 记录最小窗口
        int minLeft = 0;
        int minLen = Integer.MAX_VALUE;
        
        // 滑动窗口的左右边界
        int left = 0, right = 0;
        
        while (right < s.length()) {
            // 获取右边界字符
            char rightChar = s.charAt(right);
            
            // 更新窗口中的字符计数
            windowCount[rightChar]++;
            
            // 如果窗口中的某个字符数量刚好满足目标要求，增加已匹配字符数量
            if (targetCount[rightChar] > 0 && windowCount[rightChar] == targetCount[rightChar]) {
                matchedChars++;
            }
            
            // 当找到一个可行窗口时，尝试收缩左边界
            while (left <= right && matchedChars == requiredChars) {
                // 更新最小窗口信息
                int currentLen = right - left + 1;
                if (currentLen < minLen) {
                    minLen = currentLen;
                    minLeft = left;
                }
                
                // 获取左边界字符
                char leftChar = s.charAt(left);
                
                // 更新窗口中的字符计数
                windowCount[leftChar]--;
                
                // 如果移除左边界字符导致窗口不再满足目标要求，减少已匹配字符数量
                if (targetCount[leftChar] > 0 && windowCount[leftChar] < targetCount[leftChar]) {
                    matchedChars--;
                }
                
                // 移动左边界
                left++;
            }
            
            // 移动右边界
            right++;
        }
        
        // 如果找到了满足条件的子串，返回最小窗口子串
        // 否则返回空字符串
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
    
    public static void main(String[] args) {
        MinimumWindowSubstring solution = new MinimumWindowSubstring();
        
        // 测试用例1
        String s1 = "ADOBECODEBANC";
        String t1 = "ABC";
        System.out.println("输入: s = \"" + s1 + "\", t = \"" + t1 + "\"");
        System.out.println("HashMap 解法输出: \"" + solution.minWindow(s1, t1) + "\"");
        System.out.println("优化解法输出: \"" + solution.minWindowOptimized(s1, t1) + "\"");
        System.out.println("期望输出: \"BANC\"");
        
        // 测试用例2
        String s2 = "a";
        String t2 = "a";
        System.out.println("\n输入: s = \"" + s2 + "\", t = \"" + t2 + "\"");
        System.out.println("HashMap 解法输出: \"" + solution.minWindow(s2, t2) + "\"");
        System.out.println("优化解法输出: \"" + solution.minWindowOptimized(s2, t2) + "\"");
        System.out.println("期望输出: \"a\"");
        
        // 测试用例3
        String s3 = "a";
        String t3 = "aa";
        System.out.println("\n输入: s = \"" + s3 + "\", t = \"" + t3 + "\"");
        System.out.println("HashMap 解法输出: \"" + solution.minWindow(s3, t3) + "\"");
        System.out.println("优化解法输出: \"" + solution.minWindowOptimized(s3, t3) + "\"");
        System.out.println("期望输出: \"\"");
        
        // 测试用例4 - 目标字符串中有重复字符
        String s4 = "ABBAACDBCAB";
        String t4 = "AABC";
        System.out.println("\n输入: s = \"" + s4 + "\", t = \"" + t4 + "\"");
        System.out.println("HashMap 解法输出: \"" + solution.minWindow(s4, t4) + "\"");
        System.out.println("优化解法输出: \"" + solution.minWindowOptimized(s4, t4) + "\"");
        System.out.println("期望输出: \"AACDB\"");
    }
} 