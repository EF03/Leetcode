package leetcode.linkedlist;

import java.util.Objects;

/**
 * @author Ron
 * @date 2020/8/12 上午 10:47
 */
public class IntersectionOfTwoLinkedLists {

    public static void main(String[] args) {
        ListNode t1 = new ListNode(1);
        t1.next = new ListNode(1);
        ListNode t2 = new ListNode(1);
        t2.next = new ListNode(1);
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t1 != t2);

        ListNode tempNode = new ListNode();
        ListNode n1 = new ListNode(4);
        tempNode.next = n1;
        n1.next = new ListNode(1);
        n1 = n1.next;
        n1.next = new ListNode(8);
        n1 = n1.next;
        n1.next = new ListNode(4);
        n1 = n1.next;
        n1.next = new ListNode(5);

        n1 = tempNode.next;


        ListNode n2 = new ListNode(5);
        tempNode.next = n2;
        n2.next = new ListNode(6);
        n2 = n2.next;
        n2.next = new ListNode(1);
        n2 = n2.next;
        n2.next = new ListNode(8);
        n2 = n2.next;
        n2.next = new ListNode(4);
        n2 = n2.next;
        n2.next = new ListNode(5);

        n2 = tempNode.next;


        ListNode result = getIntersectionNode(n1, n2);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }

    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //boundary check
        if (headA == null || headB == null) {
            return null;
        }

        ListNode a = headA;
        ListNode b = headB;

        //if a & b have different len, then we will stop the loop after second iteration
        while (a != b) {
            //for the end of first iteration, we just reset the pointer to the head of another linkedlist
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
            if (a != null && b != null) {
                if (a.val == b.val) {
                    break;
                }
            }
        }

        return a;

    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        ListNode(int x) {
            val = x;
            next = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ListNode listNode = (ListNode) o;
            return val == listNode.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }
}
