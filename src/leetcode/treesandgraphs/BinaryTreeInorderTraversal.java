package leetcode.treesandgraphs;

import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Ron
 * @date 2020/8/13 下午 03:35
 */
public class BinaryTreeInorderTraversal {
    public static void main(String[] args) {
        Object[] param = {1, null, 2, 3};
//        TreeNode root = new TreeNode();
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(null);
//        list.add(2);
//        list.add(3);
//        inorder(root, list);

        TreeNode root = new TreeNode();
        root.val = 1;
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
//        for (Object ele : param) {
//
//        }

        List<Integer> result = inorderTraversal(root);

        System.out.println(result);

    }

//    public static List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        inorder(root, res);
//        return res;
//    }

    /**
     * Approach 1: Recursive Approach
     */

//    public static List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        Stack<TreeNode> stack = new Stack<>();
//        TreeNode curr = root;
//        while (curr != null || !stack.isEmpty()) {
//            while (curr != null) {
//                stack.push(curr);
//                curr = curr.left;
//            }
//            curr = stack.pop();
//            res.add(curr.val);
//            curr = curr.right;
//        }
//        return res;
//    }

    /**
     * Approach 2: Iterating method using Stack
     */

//    public static List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        Stack<TreeNode> stack = new Stack<>();
//        TreeNode curr = root;
//        while (curr != null || !stack.isEmpty()) {
//            while (curr != null) {
//                stack.push(curr);
//                curr = curr.left;
//            }
//            curr = stack.pop();
//            res.add(curr.val);
//            curr = curr.right;
//        }
//        return res;
//    }

    /**
     * Approach 3: Morris Traversal
     */

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode curr = root;
        TreeNode pre;
        while (curr != null) {
            if (curr.left == null) {
                res.add(curr.val);
                curr = curr.right; // move to next right node
            } else { // has a left subtree
                pre = curr.left;
                while (pre.right != null) { // find rightmost
                    pre = pre.right;
                }
                pre.right = curr; // put cur after the pre node
                TreeNode temp = curr; // store cur node
                curr = curr.left; // move cur to the top of the new tree
                temp.left = null; // original cur left be null, avoid infinite loops
            }
        }
        return res;
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void inorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }
}
