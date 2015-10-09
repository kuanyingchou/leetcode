public class Q234 {
    public boolean isPalindrome(ListNode root) {
        if(root == null || root.next == null) return true;
        ListNode p, q;
        p = q = root;
        while(q!= null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }
        if(q!=null) p = p.next; //for odd size

        ListNode r = null;
        while(p.next != null) {
            q=p.next;
            p.next = r;
            r = p;
            p = q;
        }
        for(q = root; p!=null; p=p.next, q=q.next) {
            if(p.val != q.val) return false;
        }
        return true;
    }
}

