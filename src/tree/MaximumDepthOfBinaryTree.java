package src.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 104. 二叉树的最大深度 (Maximum Depth of Binary Tree)
 * 
 * 问题描述：
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 * 
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 * 
 * 时间复杂度：O(n)，其中 n 是二叉树的节点数
 * 空间复杂度：O(h)，其中 h 是树的高度。递归解法的空间复杂度取决于递归栈的深度。
 */
public class MaximumDepthOfBinaryTree {
    
    // 使用与BinaryTreeInorderTraversal相同的TreeNode类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {}
        
        TreeNode(int val) { 
            this.val = val; 
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     * 递归解法
     * 思路：二叉树的最大深度 = max(左子树的最大深度, 右子树的最大深度) + 1
     */
    public int maxDepth(TreeNode root) {
        // 基本情况：空树
        if (root == null) {
            return 0;
        }
        
        // 递归计算左子树和右子树的最大深度
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // 返回较大的深度 + 1（当前节点）
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    /**
     * 迭代解法（层序遍历/BFS）
     * 思路：使用队列进行层序遍历，每遍历一层，深度加1
     */
    public int maxDepthIterative(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            // 当前层的节点数
            int levelSize = queue.size();
            
            // 遍历当前层的所有节点
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // 将子节点加入队列
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            // 每遍历完一层，深度加1
            depth++;
        }
        
        return depth;
    }
    
    /**
     * DFS迭代解法
     * 使用栈进行深度优先搜索，同时记录每个节点的深度
     */
    public int maxDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        // 使用栈存储节点和对应的深度
        java.util.Stack<Pair<TreeNode, Integer>> stack = new java.util.Stack<>();
        stack.push(new Pair<>(root, 1));
        
        int maxDepth = 0;
        
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int currentDepth = pair.getValue();
            
            // 更新最大深度
            maxDepth = Math.max(maxDepth, currentDepth);
            
            // 将子节点及其深度入栈
            if (node.right != null) {
                stack.push(new Pair<>(node.right, currentDepth + 1));
            }
            if (node.left != null) {
                stack.push(new Pair<>(node.left, currentDepth + 1));
            }
        }
        
        return maxDepth;
    }
    
    // 简单的键值对类，用于DFS迭代解法
    private static class Pair<K, V> {
        private K key;
        private V value;
        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
    }
    
    // 辅助方法：创建树
    private static TreeNode createTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(values[0]);
        queue.offer(root);
        
        for (int i = 1; i < values.length; i += 2) {
            TreeNode current = queue.poll();
            
            if (values[i] != null) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            
            if (i + 1 < values.length && values[i + 1] != null) {
                current.right = new TreeNode(values[i + 1]);
                queue.offer(current.right);
            }
        }
        
        return root;
    }
    
    // 测试方法
    public static void main(String[] args) {
        MaximumDepthOfBinaryTree solution = new MaximumDepthOfBinaryTree();
        
        // 测试例子1: [3,9,20,null,null,15,7]
        Integer[] values1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = createTree(values1);
        
        System.out.println("输入: [3,9,20,null,null,15,7]");
        System.out.println("递归解法: " + solution.maxDepth(root1));
        System.out.println("迭代解法(BFS): " + solution.maxDepthIterative(root1));
        System.out.println("迭代解法(DFS): " + solution.maxDepthDFS(root1));
        
        // 测试例子2: 空树
        System.out.println("\n输入: []");
        System.out.println("递归解法: " + solution.maxDepth(null));
        System.out.println("迭代解法(BFS): " + solution.maxDepthIterative(null));
        System.out.println("迭代解法(DFS): " + solution.maxDepthDFS(null));
        
        // 测试例子3: 只有一个节点的树
        TreeNode root3 = new TreeNode(1);
        System.out.println("\n输入: [1]");
        System.out.println("递归解法: " + solution.maxDepth(root3));
        System.out.println("迭代解法(BFS): " + solution.maxDepthIterative(root3));
        System.out.println("迭代解法(DFS): " + solution.maxDepthDFS(root3));
        
        // 测试例子4: 单链树
        //      1
        //     /
        //    2
        //   /
        //  3
        // /
        //4
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.left = new TreeNode(3);
        root4.left.left.left = new TreeNode(4);
        
        System.out.println("\n输入: 单链树 [1,2,null,3,null,4]");
        System.out.println("递归解法: " + solution.maxDepth(root4));
        System.out.println("迭代解法(BFS): " + solution.maxDepthIterative(root4));
        System.out.println("迭代解法(DFS): " + solution.maxDepthDFS(root4));
        
        // 测试例子5: 完全二叉树
        //      1
        //    /   \
        //   2     3
        //  / \   / \
        // 4   5 6   7
        Integer[] values5 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root5 = createTree(values5);
        
        System.out.println("\n输入: 完全二叉树 [1,2,3,4,5,6,7]");
        System.out.println("递归解法: " + solution.maxDepth(root5));
        System.out.println("迭代解法(BFS): " + solution.maxDepthIterative(root5));
        System.out.println("迭代解法(DFS): " + solution.maxDepthDFS(root5));
    }
} 