package src.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * LeetCode 94. 二叉树的中序遍历 (Binary Tree Inorder Traversal)
 * 
 * 问题描述：
 * 给定一个二叉树的根节点 root ，返回它的中序遍历。
 * 
 * 示例：
 * 输入：root = [1,null,2,3]
 * 输出：[1,3,2]
 * 
 * 输入：root = []
 * 输出：[]
 * 
 * 输入：root = [1]
 * 输出：[1]
 * 
 * 时间复杂度：O(n)，其中 n 是二叉树的节点数
 * 空间复杂度：O(n)，最坏情况下需要存储整棵树（例如，当树退化为链表时）
 */
public class BinaryTreeInorderTraversal {
    
    // 二叉树节点定义
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
     * 递归实现的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // 先遍历左子树
        inorderHelper(node.left, result);
        // 然后访问根节点
        result.add(node.val);
        // 最后遍历右子树
        inorderHelper(node.right, result);
    }
    
    /**
     * 迭代实现的中序遍历
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        // 当还有节点未处理或栈不为空时继续遍历
        while (current != null || !stack.isEmpty()) {
            // 将当前节点的所有左子节点入栈
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // 弹出栈顶节点（当前子树最左侧的节点）
            current = stack.pop();
            
            // 访问该节点
            result.add(current.val);
            
            // 处理右子树
            current = current.right;
        }
        
        return result;
    }
    
    /**
     * Morris中序遍历 (无需额外空间的遍历方法)
     * 时间复杂度: O(n)
     * 空间复杂度: O(1)
     */
    public List<Integer> inorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        TreeNode pre;
        
        while (current != null) {
            if (current.left == null) {
                // 如果没有左子树，直接访问当前节点，然后转向右子树
                result.add(current.val);
                current = current.right;
            } else {
                // 有左子树，找到当前节点在中序遍历下的前驱节点
                pre = current.left;
                while (pre.right != null && pre.right != current) {
                    pre = pre.right;
                }
                
                if (pre.right == null) {
                    // 第一次访问，将前驱节点的右指针指向当前节点，然后转向左子树
                    pre.right = current;
                    current = current.left;
                } else {
                    // 第二次访问，恢复树结构，访问当前节点，然后转向右子树
                    pre.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    // 辅助方法：创建树
    private static TreeNode createTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
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
        BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();
        
        // 测试例子1: [1,null,2,3] 表示如下树:
        //   1
        //    \
        //     2
        //    /
        //   3
        Integer[] values1 = {1, null, 2, null, null, 3};
        TreeNode root1 = createTree(values1);
        
        System.out.println("输入: [1,null,2,3]");
        System.out.println("递归中序遍历: " + solution.inorderTraversal(root1));
        System.out.println("迭代中序遍历: " + solution.inorderTraversalIterative(root1));
        System.out.println("Morris中序遍历: " + solution.inorderTraversalMorris(root1));
        
        // 测试例子2: 空树
        TreeNode root2 = null;
        System.out.println("\n输入: []");
        System.out.println("递归中序遍历: " + solution.inorderTraversal(root2));
        System.out.println("迭代中序遍历: " + solution.inorderTraversalIterative(root2));
        System.out.println("Morris中序遍历: " + solution.inorderTraversalMorris(root2));
        
        // 测试例子3: 只有一个节点的树
        TreeNode root3 = new TreeNode(1);
        System.out.println("\n输入: [1]");
        System.out.println("递归中序遍历: " + solution.inorderTraversal(root3));
        System.out.println("迭代中序遍历: " + solution.inorderTraversalIterative(root3));
        System.out.println("Morris中序遍历: " + solution.inorderTraversalMorris(root3));
        
        // 测试例子4: 完整二叉树
        //      1
        //    /   \
        //   2     3
        //  / \   / \
        // 4   5 6   7
        Integer[] values4 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root4 = createTree(values4);
        System.out.println("\n输入: [1,2,3,4,5,6,7]");
        System.out.println("递归中序遍历: " + solution.inorderTraversal(root4));
        System.out.println("迭代中序遍历: " + solution.inorderTraversalIterative(root4));
        System.out.println("Morris中序遍历: " + solution.inorderTraversalMorris(root4));
    }
} 