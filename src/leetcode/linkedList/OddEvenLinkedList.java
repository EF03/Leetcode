package leetcode.linkedList;

/**
 * @author Ron
 * @date 2020/8/7 下午 04:14
 */

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
public class OddEvenLinkedList {


    public static void main(String[] args) {

//        Input: 1->2->3->4->5->NULL
//        Output: 1->3->5->2->4->NULL

//        Input: 2->1->3->5->6->4->7->NULL
//        Output: 2->3->6->7->1->5->4->NULL

        AddTwoNumbers.ListNode n1 = new AddTwoNumbers.ListNode(1);
        n1.next = new AddTwoNumbers.ListNode(2);
        n1.next.next = new AddTwoNumbers.ListNode(3);
        n1.next.next = new AddTwoNumbers.ListNode(4);
        n1.next.next = new AddTwoNumbers.ListNode(5);

        AddTwoNumbers.ListNode n2 = new AddTwoNumbers.ListNode(2);
        n2.next = new AddTwoNumbers.ListNode(1);
        n2.next.next = new AddTwoNumbers.ListNode(3);
        n2.next.next = new AddTwoNumbers.ListNode(5);
        n2.next.next = new AddTwoNumbers.ListNode(6);
        n2.next.next = new AddTwoNumbers.ListNode(4);
        n2.next.next = new AddTwoNumbers.ListNode(7);




    }

    public ListNode oddEvenList(ListNode head) {


        return new ListNode();

    }


    public static class ListNode {
        int val;
        AddTwoNumbers.ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, AddTwoNumbers.ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
