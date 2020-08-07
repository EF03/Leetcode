package leetcode.linkedList;

/**
 * @author Ron
 * @date 2020/8/7 下午 03:37
 */

import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public class AddTwoNumbers {


    public static void main(String[] args) {
//        (2 -> 4 -> 3) + (5 -> 6 -> 4)
        ListNode n1 = new ListNode(2);
        n1.next = new ListNode(4);
        n1.next.next = new ListNode(3);
        ListNode n2 = new ListNode(5);
        n2.next = new ListNode(6);
        n2.next.next = new ListNode(4);


        ListNode n3 = addTwoNumbers(n1, n2);
        while (n3 != null) {
            System.out.print(n3.val);
            n3 = n3.next;
        }

//        while (n1.next != null) {
//
//        }
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode prev = new ListNode(0);// 上一个
        ListNode head = prev;// 记录起始点 锚点
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            ListNode cur = new ListNode(0);// 尾端新的node
            int sum = ((l2 == null) ? 0 : l2.val) + ((l1 == null) ? 0 : l1.val) + carry;
            cur.val = sum % 10;
            carry = sum / 10;

            // 接续
            prev.next = cur;
            prev = cur;

            l1 = (l1 == null) ? null : l1.next;
            l2 = (l2 == null) ? null : l2.next;
        }
        return head.next;

    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
