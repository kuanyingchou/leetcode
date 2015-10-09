//6. Reverse Linked List
//Definition for singly-linked list.
public class ListNode {
    int val;
    public ListNode next;
    public ListNode(int x) { this(x, null); }
    public ListNode(int x, ListNode n) { 
        val = x; 
        next = n;
    }

    public void append(int x) {
        ListNode i = null;
        for(i = this; i.next!=null; i=i.next) { }
        i.next = new ListNode(x);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof ListNode)) return false;
        final ListNode that = (ListNode) o;
        ListNode p=this, q=that;
        for(; p != null && q != null;
                p=p.next, q=q.next) {
            if(p.val != q.val) return false;
        }
        if(p == null && q != null ||
                p != null && q == null) return false; //different length
        return true;
        
    }

    @Override
    public String toString() {
        return val + ((next==null)?"":", " + next.toString());
    }

}
