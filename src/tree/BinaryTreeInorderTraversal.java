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
 * 时间复杂度：O(n)，其中n是节点的数量
 * 空间复杂度：递归解法O(h)，迭代解法O(h)，其中h是树的高度
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
     * 递归解法
     * 中序遍历的顺序：左子树 -> 根节点 -> 右子树
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
        // 访问根节点
        result.add(node.val);
        // 最后遍历右子树
        inorderHelper(node.right, result);
    }
    
    /**
     * 迭代解法
     * 使用栈来模拟递归过程
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // 一直往左走，将所有左侧节点入栈
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // 弹出栈顶节点（最左侧节点）
            current = stack.pop();
            // 访问节点
            result.add(current.val);
            // 转向右子树
            current = current.right;
        }
        
        return result;
    }
    
    /**
     * Morris中序遍历（不使用栈或递归，空间复杂度O(1)）
     * 利用线索二叉树的思想，将叶子节点的右空指针指向中序遍历的后继节点
     */
    public List<Integer> inorderTraversalMorris(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        TreeNode predecessor = null;
        
        while (current != null) {
            if (current.left == null) {
                // 如果没有左子树，直接访问当前节点，然后转向右子树
                result.add(current.val);
                current = current.right;
            } else {
                // 有左子树，找到当前节点在中序遍历下的前驱节点
                predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // 第一次访问，建立线索（前驱节点的右指针指向当前节点）
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // 第二次访问，已经遍历完左子树，访问当前节点并恢复树结构
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    // 创建二叉树的辅助方法
    public static TreeNode createTree(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode[] nodes = new TreeNode[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                nodes[i] = new TreeNode(values[i]);
            }
        }
        
        for (int i = 0; i < values.length; i++) {
            if (nodes[i] != null) {
                int leftIndex = 2 * i + 1;
                int rightIndex = 2 * i + 2;
                
                if (leftIndex < values.length) {
                    nodes[i].left = nodes[leftIndex];
                }
                
                if (rightIndex < values.length) {
                    nodes[i].right = nodes[rightIndex];
                }
            }
        }
        
        return nodes[0];
    }
    
    // 测试方法
    public static void main(String[] args) {
        BinaryTreeInorderTraversal solution = new BinaryTreeInorderTraversal();
        
        // 测试用例1: [1,null,2,3]
        //    1
        //     \
        //      2
        //     /
        //    3
        TreeNode root1 = new TreeNode(1);
        root1.right = new TreeNode(2);
        root1.right.left = new TreeNode(3);
        
        System.out.println("测试用例1: [1,null,2,3]");
        System.out.println("递归解法: " + solution.inorderTraversal(root1));  // 预期输出: [1,3,2]
        System.out.println("迭代解法: " + solution.inorderTraversalIterative(root1));
        System.out.println("Morris遍历: " + solution.inorderTraversalMorris(root1));
        
        // 测试用例2: [1,2,3,4,5,6,7]
        //        1
        //       / \
        //      2   3
        //     / \ / \
        //    4  5 6  7
        Integer[] values = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root2 = createTree(values);
        
        System.out.println("\n测试用例2: [1,2,3,4,5,6,7]");
        System.out.println("递归解法: " + solution.inorderTraversal(root2));  // 预期输出: [4,2,5,1,6,3,7]
        System.out.println("迭代解法: " + solution.inorderTraversalIterative(root2));
        System.out.println("Morris遍历: " + solution.inorderTraversalMorris(root2));
        
        // 测试用例3: [1]
        TreeNode root3 = new TreeNode(1);
        
        System.out.println("\n测试用例3: [1]");
        System.out.println("递归解法: " + solution.inorderTraversal(root3));  // 预期输出: [1]
        System.out.println("迭代解法: " + solution.inorderTraversalIterative(root3));
        System.out.println("Morris遍历: " + solution.inorderTraversalMorris(root3));
        
        // 测试用例4: []
        TreeNode root4 = null;
        
        System.out.println("\n测试用例4: []");
        System.out.println("递归解法: " + solution.inorderTraversal(root4));  // 预期输出: []
        System.out.println("迭代解法: " + solution.inorderTraversalIterative(root4));
        System.out.println("Morris遍历: " + solution.inorderTraversalMorris(root4));
    }
} 