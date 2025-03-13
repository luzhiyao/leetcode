package src.design;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 146. LRU 缓存 (LRU Cache)
 * 
 * 问题描述：
 * 设计并实现一个LRU（最近最少使用）缓存机制。它应该支持以下操作：获取数据get和写入数据put。
 * 
 * get(key)：如果关键字存在于缓存中，则获取关键字的值（总是正数），否则返回-1。
 * put(key, value)：如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组关键字和数据值。
 * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据项，从而为新数据留出空间。
 * 
 * 要求：get和put操作的时间复杂度必须是O(1)。
 * 
 * 示例：
 * LRUCache cache = new LRUCache(2); // 缓存容量为 2
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // 返回 1
 * cache.put(3, 3);    // 该操作会使得关键字 2 作废
 * cache.get(2);       // 返回 -1 (未找到)
 * cache.put(4, 4);    // 该操作会使得关键字 1 作废
 * cache.get(1);       // 返回 -1 (未找到)
 * cache.get(3);       // 返回 3
 * cache.get(4);       // 返回 4
 * 
 * 时间复杂度：O(1) - 所有操作都是常数时间
 * 空间复杂度：O(capacity) - 存储最多capacity个键值对
 */
public class LRUCache {
    
    /**
     * 双向链表节点类
     */
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        public Node() {}
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Map<Integer, Node> cache; // 哈希表：O(1)的查找时间
    private int capacity; // 缓存容量
    private int size; // 当前缓存大小
    private Node head, tail; // 虚拟头尾节点
    
    /**
     * 初始化缓存容量
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>();
        
        // 初始化双向链表的虚拟头尾节点
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * 获取缓存中的值，如果不存在返回-1
     * 
     * 每次获取后，将访问的节点移动到链表头部（最新使用）
     */
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1; // 缓存中不存在该key
        }
        
        // 将节点移到链表头部，表示最近使用
        moveToHead(node);
        return node.value;
    }
    
    /**
     * 插入一个键值对到缓存中
     * 
     * 如果key已存在，更新value并将节点移到链表头部
     * 如果key不存在，创建新节点并添加到链表头部，如果超出容量，删除链表尾部节点
     */
    public void put(int key, int value) {
        Node node = cache.get(key);
        
        if (node == null) {
            // 创建新节点
            Node newNode = new Node(key, value);
            
            // 添加到哈希表
            cache.put(key, newNode);
            
            // 添加到链表头部
            addToHead(newNode);
            
            size++;
            
            // 如果超出容量，删除最久未使用的节点（链表尾部）
            if (size > capacity) {
                Node removed = removeTail();
                cache.remove(removed.key);
                size--;
            }
        } else {
            // 更新已存在节点的值，并移到链表头部
            node.value = value;
            moveToHead(node);
        }
    }
    
    /**
     * 将节点添加到链表头部
     */
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * 从链表中移除节点
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * 将节点移到链表头部（最近使用）
     */
    private void moveToHead(Node node) {
        removeNode(node); // 先从当前位置移除
        addToHead(node);  // 再添加到头部
    }
    
    /**
     * 移除链表尾部节点（最久未使用）
     */
    private Node removeTail() {
        Node removed = tail.prev;
        removeNode(removed);
        return removed;
    }
    
    /**
     * 用于测试的辅助方法：打印缓存内容
     */
    private void printCache() {
        System.out.print("Cache: [");
        Node current = head.next;
        while (current != tail) {
            System.out.print("(" + current.key + ":" + current.value + ")");
            current = current.next;
            if (current != tail) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * 使用数组实现LRU缓存（备选方案，适用于小容量缓存）
     */
    static class LRUCacheArray {
        private int[] keys;
        private int[] values;
        private int capacity;
        private int size;
        
        public LRUCacheArray(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.keys = new int[capacity];
            this.values = new int[capacity];
        }
        
        public int get(int key) {
            for (int i = 0; i < size; i++) {
                if (keys[i] == key) {
                    // 找到key，保存值
                    int value = values[i];
                    
                    // 移动元素，实现LRU
                    for (int j = i; j > 0; j--) {
                        keys[j] = keys[j-1];
                        values[j] = values[j-1];
                    }
                    
                    // 将访问的元素移到最前面
                    keys[0] = key;
                    values[0] = value;
                    
                    return value;
                }
            }
            return -1;
        }
        
        public void put(int key, int value) {
            // 检查key是否已存在
            for (int i = 0; i < size; i++) {
                if (keys[i] == key) {
                    // 移动元素，实现LRU
                    for (int j = i; j > 0; j--) {
                        keys[j] = keys[j-1];
                        values[j] = values[j-1];
                    }
                    
                    // 更新key和value
                    keys[0] = key;
                    values[0] = value;
                    return;
                }
            }
            
            // key不存在，需要添加
            if (size < capacity) {
                // 还有空间，直接添加
                for (int i = size; i > 0; i--) {
                    keys[i] = keys[i-1];
                    values[i] = values[i-1];
                }
                keys[0] = key;
                values[0] = value;
                size++;
            } else {
                // 缓存已满，移除最后一个元素
                for (int i = capacity - 1; i > 0; i--) {
                    keys[i] = keys[i-1];
                    values[i] = values[i-1];
                }
                keys[0] = key;
                values[0] = value;
            }
        }
    }
    
    public static void main(String[] args) {
        // 测试LRU缓存
        LRUCache cache = new LRUCache(2);
        
        // 测试示例
        System.out.println("示例操作:");
        cache.put(1, 1);
        System.out.println("put(1, 1)");
        cache.printCache();
        
        cache.put(2, 2);
        System.out.println("put(2, 2)");
        cache.printCache();
        
        System.out.println("get(1): " + cache.get(1));
        cache.printCache();
        
        cache.put(3, 3);
        System.out.println("put(3, 3)");
        cache.printCache();
        
        System.out.println("get(2): " + cache.get(2));
        cache.printCache();
        
        cache.put(4, 4);
        System.out.println("put(4, 4)");
        cache.printCache();
        
        System.out.println("get(1): " + cache.get(1));
        cache.printCache();
        
        System.out.println("get(3): " + cache.get(3));
        cache.printCache();
        
        System.out.println("get(4): " + cache.get(4));
        cache.printCache();
        
        // 测试容量为1的特殊情况
        System.out.println("\n测试容量为1的缓存:");
        LRUCache smallCache = new LRUCache(1);
        smallCache.put(1, 1);
        System.out.println("put(1, 1)");
        smallCache.printCache();
        
        smallCache.put(2, 2);
        System.out.println("put(2, 2)");
        smallCache.printCache();
        
        System.out.println("get(1): " + smallCache.get(1));
        smallCache.printCache();
        
        System.out.println("get(2): " + smallCache.get(2));
        smallCache.printCache();
        
        // 测试更新现有键的值
        System.out.println("\n测试更新现有键的值:");
        LRUCache updateCache = new LRUCache(2);
        updateCache.put(1, 1);
        updateCache.put(2, 2);
        System.out.println("Initial cache:");
        updateCache.printCache();
        
        updateCache.put(1, 100);
        System.out.println("After put(1, 100):");
        updateCache.printCache();
        
        System.out.println("get(1): " + updateCache.get(1));
        
        // 测试数组实现（可选）
        System.out.println("\n测试数组实现的LRU缓存:");
        LRUCacheArray arrayCache = new LRUCacheArray(2);
        arrayCache.put(1, 1);
        arrayCache.put(2, 2);
        System.out.println("get(1): " + arrayCache.get(1));
        arrayCache.put(3, 3);
        System.out.println("get(2): " + arrayCache.get(2));
        arrayCache.put(4, 4);
        System.out.println("get(1): " + arrayCache.get(1));
        System.out.println("get(3): " + arrayCache.get(3));
        System.out.println("get(4): " + arrayCache.get(4));
    }
} 