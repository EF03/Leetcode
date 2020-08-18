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

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

//    public static List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//
//        Stack<TreeNode> stack = new Stack<>();
//        TreeNode cur = root;
//
//        while (cur != null || !stack.empty()) {
//            while (cur != null) {
//                stack.add(cur);
//                cur = cur.left;
//            }
//            cur = stack.pop();
//            list.add(cur.val);
//            cur = cur.right;
//        }
//
//        return list;
//    }


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
