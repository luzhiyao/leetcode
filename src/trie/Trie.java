package src.trie;

/**
 * 实现字典树(Trie)
 * 
 * 问题描述：
 * 字典树是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这种数据结构有相当多的应用，例如：
 * 1. 自动补全
 * 2. 拼写检查
 * 3. IP路由(最长前缀匹配)
 * 4. 字符串检索
 * 5. 词频统计
 * 
 * 时间复杂度：
 * - 插入: O(m)，其中m是键的长度
 * - 查找: O(m)，其中m是键的长度
 * - 删除: O(m)，其中m是键的长度
 * 
 * 空间复杂度：O(n * m)，其中n是键的数量，m是键的平均长度
 */
public class Trie {
    
    private TrieNode root;
    
    /**
     * 字典树节点
     */
    class TrieNode {
        // 子节点，默认26个英文小写字母
        private TrieNode[] children;
        // 标记该节点是否是一个单词的结尾
        private boolean isEndOfWord;
        // 记录以该节点结尾的单词数量（可用于词频统计）
        private int count;
        
        public TrieNode() {
            children = new TrieNode[26]; // 假设只包含小写字母a-z
            isEndOfWord = false;
            count = 0;
        }
    }
    
    /**
     * 初始化字典树
     */
    public Trie() {
        root = new TrieNode();
    }
    
    /**
     * 插入一个单词到字典树中
     * 
     * @param word 要插入的单词
     */
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (index < 0 || index >= 26) {
                throw new IllegalArgumentException("仅支持小写字母a-z");
            }
            
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
        node.count++;
    }
    
    /**
     * 搜索字典树中是否存在完全匹配的单词
     * 
     * @param word 要搜索的单词
     * @return 如果找到完全匹配的单词，返回true；否则返回false
     */
    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }
    
    /**
     * 查找字典树中是否有以给定前缀开始的单词
     * 
     * @param prefix 要查找的前缀
     * @return 如果找到以给定前缀开始的单词，返回true；否则返回false
     */
    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return false;
        }
        
        return searchPrefix(prefix) != null;
    }
    
    /**
     * 查找前缀，返回到达前缀最后一个字符的节点
     * 
     * @param prefix 要查找的前缀
     * @return 如果找到前缀，返回对应节点；否则返回null
     */
    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (index < 0 || index >= 26 || node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
    
    /**
     * 删除字典树中的单词
     * 
     * @param word 要删除的单词
     * @return 如果成功删除，返回true；否则返回false
     */
    public boolean delete(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        
        return delete(root, word, 0);
    }
    
    /**
     * 递归删除单词
     * 
     * @param current 当前节点
     * @param word 要删除的单词
     * @param index 当前处理的字符索引
     * @return 如果成功删除，返回true；否则返回false
     */
    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            // 如果不是单词结尾，无法删除
            if (!current.isEndOfWord) {
                return false;
            }
            
            // 减少计数，如果计数为0，标记为非单词结尾
            current.count--;
            if (current.count == 0) {
                current.isEndOfWord = false;
            }
            
            // 即使标记为非结尾，也不删除节点，因为它可能是其他单词的前缀
            return true;
        }
        
        char c = word.charAt(index);
        int childIndex = c - 'a';
        
        if (childIndex < 0 || childIndex >= 26 || current.children[childIndex] == null) {
            return false;
        }
        
        boolean deletedChild = delete(current.children[childIndex], word, index + 1);
        
        // 如果子节点被删除且当前节点不是单词结尾，并且没有其他子节点，可以删除当前节点
        if (deletedChild && !current.children[childIndex].isEndOfWord && isEmpty(current.children[childIndex])) {
            current.children[childIndex] = null;
        }
        
        return deletedChild;
    }
    
    /**
     * 检查节点是否没有子节点
     * 
     * @param node 要检查的节点
     * @return 如果节点没有子节点，返回true；否则返回false
     */
    private boolean isEmpty(TrieNode node) {
        for (TrieNode child : node.children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 获取单词出现的次数
     * 
     * @param word 要查询的单词
     * @return 单词出现的次数
     */
    public int getCount(String word) {
        if (word == null || word.isEmpty()) {
            return 0;
        }
        
        TrieNode node = searchPrefix(word);
        return (node != null && node.isEndOfWord) ? node.count : 0;
    }
    
    /**
     * 获取字典树中的单词数量
     * 
     * @return 字典树中的单词数量
     */
    public int size() {
        return size(root);
    }
    
    /**
     * 递归计算节点包含的单词数量
     * 
     * @param node 当前节点
     * @return 当前节点包含的单词数量
     */
    private int size(TrieNode node) {
        if (node == null) {
            return 0;
        }
        
        int count = 0;
        if (node.isEndOfWord) {
            count += node.count;
        }
        
        for (TrieNode child : node.children) {
            count += size(child);
        }
        
        return count;
    }
    
    /**
     * 清空字典树
     */
    public void clear() {
        root = new TrieNode();
    }
    
    /**
     * 获取所有以指定前缀开始的单词
     * 
     * @param prefix 前缀
     * @return 以指定前缀开始的单词数组
     */
    public java.util.List<String> getWordsWithPrefix(String prefix) {
        java.util.List<String> result = new java.util.ArrayList<>();
        TrieNode node = searchPrefix(prefix);
        
        if (node != null) {
            collectWords(node, prefix, result);
        }
        
        return result;
    }
    
    /**
     * 递归收集以指定前缀开始的所有单词
     * 
     * @param node 当前节点
     * @param prefix 当前前缀
     * @param result 结果列表
     */
    private void collectWords(TrieNode node, String prefix, java.util.List<String> result) {
        if (node.isEndOfWord) {
            for (int i = 0; i < node.count; i++) {
                result.add(prefix);
            }
        }
        
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char nextChar = (char) ('a' + i);
                collectWords(node.children[i], prefix + nextChar, result);
            }
        }
    }
    
    // 测试代码
    public static void main(String[] args) {
        Trie trie = new Trie();
        
        // 测试插入和搜索
        System.out.println("=== 测试插入和搜索 ===");
        trie.insert("apple");
        System.out.println("插入'apple'后，search('apple'): " + trie.search("apple"));  // true
        System.out.println("search('app'): " + trie.search("app"));  // false
        System.out.println("startsWith('app'): " + trie.startsWith("app"));  // true
        
        trie.insert("app");
        System.out.println("插入'app'后，search('app'): " + trie.search("app"));  // true
        
        // 测试重复插入和计数
        System.out.println("\n=== 测试重复插入和计数 ===");
        trie.insert("apple");
        trie.insert("apple");
        System.out.println("重复插入'apple'后，getCount('apple'): " + trie.getCount("apple"));  // 3
        System.out.println("getCount('app'): " + trie.getCount("app"));  // 1
        System.out.println("getCount('banana'): " + trie.getCount("banana"));  // 0
        
        // 测试获取单词数量
        System.out.println("\n=== 测试获取单词数量 ===");
        System.out.println("size(): " + trie.size());  // 4 (1个'app' + 3个'apple')
        
        // 测试删除
        System.out.println("\n=== 测试删除 ===");
        System.out.println("delete('apple'): " + trie.delete("apple"));  // true
        System.out.println("delete('apple')后，getCount('apple'): " + trie.getCount("apple"));  // 2
        System.out.println("delete('app'): " + trie.delete("app"));  // true
        System.out.println("delete('app')后，search('app'): " + trie.search("app"));  // false
        System.out.println("startsWith('app'): " + trie.startsWith("app"));  // true（因为'apple'还在）
        System.out.println("delete('banana'): " + trie.delete("banana"));  // false
        
        // 测试前缀查找
        System.out.println("\n=== 测试前缀查找 ===");
        trie.clear();
        trie.insert("apple");
        trie.insert("application");
        trie.insert("appreciate");
        trie.insert("app");
        trie.insert("banana");
        
        System.out.println("前缀'app'的单词: " + trie.getWordsWithPrefix("app"));
        System.out.println("前缀'b'的单词: " + trie.getWordsWithPrefix("b"));
        System.out.println("前缀'c'的单词: " + trie.getWordsWithPrefix("c"));
        
        // 测试清空
        System.out.println("\n=== 测试清空 ===");
        trie.clear();
        System.out.println("clear()后，size(): " + trie.size());  // 0
        System.out.println("search('apple'): " + trie.search("apple"));  // false
    }
} 