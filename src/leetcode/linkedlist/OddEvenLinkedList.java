package leetcode.linkedlist;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 * @author IMI-Ron
 */
public class OddEvenLinkedList {


    public static void main(String[] args) {

//        Input: 1->2->3->4->5->NULL
//        Output: 1->3->5->2->4->NULL

//        Input: 2->1->3->5->6->4->7->NULL
//        Output: 2->3->6->7->1->5->4->NULL

        ListNode n1 = new ListNode(1);
        n1.next = new ListNode(2);
        n1.next.next = new ListNode(3);
        n1.next.next.next = new ListNode(4);
        n1.next.next.next.next = new ListNode(5);

        ListNode n2 = new ListNode(2);
        n2.next = new ListNode(1);
        n2.next.next = new ListNode(3);
        n2.next.next.next = new ListNode(5);
        n2.next.next.next.next = new ListNode(6);
        n2.next.next.next.next.next = new ListNode(4);
        n2.next.next.next.next.next.next = new ListNode(7);

        n1 = oddEvenList(n2);


        while (n1 != null) {
            System.out.println(n1.val);
            n1 = n1.next;
        }


    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p1 = head, p2 = head.next, pre = p2;
        while (p2 != null && p2.next != null) {
            //  第一個接到第三個
            p1.next = p2.next;
            //  從第三個開始
            p1 = p1.next;
            //  第二個接到第四個
            p2.next = p1.next;
            //  從第四個開始
            p2 = p2.next;
        }
        // p1.next 奇數最後一個  = pre 偶數第一個
        p1.next = pre;
        return head;

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
