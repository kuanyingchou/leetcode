import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Solution {
    //utilities
    public static void assertTrue(boolean predicate) {
        if(!predicate) throw new RuntimeException("oh oh");
    }
    public static void assertEquals(Object actual, Object expected) {
        if(actual == null) {
            if(expected == null) { return; }
        } else {
            if(actual.equals(expected)) return;
        }

        throw new RuntimeException(
                "expected \""+expected+"\" but got \""+actual+"\""); //or equals()?
    }
    public static void printArray(int[] arr) {
        System.out.print("[");
        for(int i=0; i<arr.length; i++) {
            System.out.print(arr[i]);
            System.out.print(", ");
        }
        System.out.println("]");
    }

    //1. Single Number
    public static int singleNumber(int[] nums) {
        if(nums.length % 2 == 0) throw new RuntimeException("what?");
        for(int i=0; i<nums.length-1; i++) {
            final int t = nums[i];
            boolean hasTwin = false;
            for(int j=i+1; j<nums.length; j++) {
                //System.out.println(i + ", "+j);
                if(t == nums[j]) {
                    hasTwin = true;
                    if(j != i+1) {
                        final int temp = nums[i+1];
                        nums[i+1] = nums[j];
                        nums[j] = temp;
                    }
                    i++;
                    break;
                }
            }
            if(!hasTwin) {
                return t;
            }
            
            //printArray(nums);
        }
        //System.out.println("last");
        //printArray(nums);
        return nums[nums.length-1];
        //throw new RuntimeException("There is no single number!");
    }
    public static void testSingleNumber() {
        //assertTrue(singleNumber(new int[] {3}) == 3);
        //assertTrue(singleNumber(new int[] {3, 5, 1, 9, 3, 1, 9}) == 5);
        assertEquals(singleNumber(new int[] {3, 5, 5, 3, 7}), 7);
        assertEquals(singleNumber(new int[] {3, 5, 1, 9, 3, 1, 9, 5, 7}), 7);
    }


    //2. Invert Binary Tree
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
        public String toString() {
            return val + "("+ (left!=null?left:"") +", "+(right!=null?right:"")+")";
        }
    }
    public static TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        final TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
    public static void testInvertTree() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        System.out.println(root);
        invertTree(root);
        System.out.println(root);
    }

    //3. Contains Duplicate II
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        for(int i=0; i<nums.length; i++) {
            final int end = Math.min(nums.length-1, i+k);
            for(int j=i+1; j<=end; j++) {
                if(nums[i] == nums[j]) return true;
            }
        }
        return false;
    }
    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        if(k == 0) return false;
        final Set<Integer> w = new HashSet<Integer>();
        for(int i=0; i<nums.length; i++) {
            final int t = nums[i];
            if(w.size() == k+1) {
                w.remove(nums[i-k-1]);
            }
            if(!w.add(t)) {
                return true;
            }
        }
        return false;
    }
    public static void testContainsNearbyDuplicate2() {
        assertEquals(containsNearbyDuplicate(new int[] {1, 2, 3, 4, 1}, 3), false);
        assertEquals(containsNearbyDuplicate(new int[] {1, 2, 3, 1, 5}, 3), true);
    }

    //4. Roman to Int
    public static int romanToInt(String s) {
        //I:1
        //V:5
        //X:10
        //L:50
        //C:100
        //D:500
        //M:1000
        if(s == null || s.length() == 0) throw new RuntimeException("empty input");

        int res = 0;
        for(int i=0; i<s.length(); i++) {
            final char c = s.charAt(i);
            switch(c) {
            case 'I': 
                if(i+1<s.length() && "VX".indexOf(s.charAt(i+1)) >= 0) {
                    res -= 1;
                } else {
                    res += 1; 
                }
                break;
            case 'V': res += 5; break;
            case 'X': 
                if(i+1<s.length() && "LC".indexOf(s.charAt(i+1)) >= 0) {
                    res -= 10;
                } else {
                    res += 10; 
                }
                break;
            case 'L': res += 50; break;
            case 'C': 
                if(i+1<s.length() && "DM".indexOf(s.charAt(i+1)) >= 0) {
                    res -= 100;
                } else {
                    res += 100; 
                }
                break;
            case 'D': res += 500; break;
            case 'M': res += 1000; break;
            default: throw new RuntimeException("unkonw roman number: "+c);
            }
        }
        return res;
    }
    public static void testRomanToInt() {
        assertEquals(romanToInt("I"), 1);
        assertEquals(romanToInt("V"), 5);
        assertEquals(romanToInt("X"), 10);
        assertEquals(romanToInt("XXV"), 25);
        assertEquals(romanToInt("XXVI"), 26);
        assertEquals(romanToInt("MCMXCVI"), 1996);
    }

    //5. Contains Duplicate
    public static boolean containsDuplicate(int[] nums) {
        for(int i=0; i<nums.length; i++) {
            for(int j=i+1; j<nums.length; j++) {
                if(nums[i] == nums[j]) return true;
            }
        }
        return false;
    }
    public static boolean containsDuplicateImpl1(int[] nums) {
        final Set<Integer> b = new HashSet<Integer>();
        for(int i : nums) {
            if(!b.add(i)) return true;
        }
        return false;
    }
    public static void testContainsDuplicate() {
        assertEquals(containsDuplicateImpl1(new int[] {1, 2, 3, 4, 5}), false);
        assertEquals(containsDuplicateImpl1(new int[] {1, 2, 3, 4, 1}), true);
    }

    //6. Reverse Linked List
    //Definition for singly-linked list.
    public static class ListNode {
        int val;
        public ListNode next;
        public ListNode(int x) { val = x; }

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
    public static ListNode reverseList(ListNode head) {
        if(head == null) return null;
        ListNode newHead = null;
        ListNode curr = null;
        while(head != null) {
            curr = head;
            head = head.next;
            curr.next = newHead;
            newHead = curr;
        }

        return newHead;
    }
    public static void testReverseList() {
        final ListNode a = new ListNode(3);
        final ListNode b = new ListNode(5);
        final ListNode c = new ListNode(7);
        a.next = b;
        b.next = c;

        final ListNode d = new ListNode(7);
        final ListNode e = new ListNode(5);
        final ListNode f = new ListNode(3);
        d.next = e;
        e.next = f;

        //System.out.println(a);
        //System.out.println(d);
        assertEquals(reverseList(a), d);

        final ListNode g = new ListNode(3);
        assertEquals(reverseList(g), new ListNode(3));
    }

    //7. Isomorphic Strings
    public static boolean isIsomorphic(String s, String t) {
        final Map<Character, Character> map = new HashMap<Character, Character>();
        final Map<Character, Character> bMap = new HashMap<Character, Character>();
        for(int i=0; i<s.length(); i++) {
            char from = s.charAt(i);
            char to = t.charAt(i);
            Character old = map.put(from, to);
            if(old!= null && !old.equals(to)) return false;
            
            old = bMap.put(to, from);
            if(old!= null && !old.equals(from)) return false;
            //System.out.println("putting "+from+" - "+to);

        }
        return true;
    }
    public static void testIsIsomorphic() {
        assertEquals(isIsomorphic("egg", "add"), true);
        assertEquals(isIsomorphic("foo", "bar"), false);
        assertEquals(isIsomorphic("paper", "title"), true);
        assertEquals(isIsomorphic("ab", "aa"), false);
    }

    //8. Count Primes
    public static int countPrimes(int n) {
        int count = 0;
        for(int i=2; i<n; i++) {
            if(isPrime(i)) {
                System.out.println(i);
                count++;
            }
        }
        return count;
    }
    public static boolean isPrime(int n) {
        for(int i=2; i<=n/2; i++) {
            if(n % i == 0) return false;
        }
        return true;
    }
    public static int countPrimes2(int n) {
        final boolean[] isPrime = new boolean[n]; //a table is like a function
        for(int i=2; i<n; i++) {
            isPrime[i] = true;
        }
        for(int i=2; i<n; i++) { //finish "the function" incrementally in runtime!
            if(isPrime[i]) {
                for(int j=i*i; j<n; j+=i) {
                    isPrime[j]=false;
                }
            }

        }
        int count = 0;
        for(int i=0; i<n; i++) {
            if(isPrime[i]) count++;
        }
        return count;
    }
    public static void testCountPrimes() {
        //2, 3, 5, 7, 11, 13, 17, 19
        assertEquals(countPrimes2(1), 0);
        assertEquals(countPrimes2(2), 0);
        assertEquals(countPrimes2(3), 1);
        assertEquals(countPrimes2(4), 2);
        assertEquals(countPrimes2(20), 8);
    }

    public static ListNode removeElements(ListNode head, int val) {
        if(head == null) return head;
        while(head != null && head.val == val) {
            ListNode t = head;
            head = head.next;
            t.next = null;
        }
        if(head == null) return head;

        ListNode p = head;
        ListNode q = head.next;
        
        while(q!=null) {
            if(q.val == val) {
                p.next = q.next;
                q.next = null;
                q = p.next;
            } else {
                p = q;
                q = q.next;
            }
        }
        return head;
    }
    public static void testRemoveElements() {
        ListNode a = new ListNode(1);
        a.append(2);
        a.append(3);
        a.append(1);
        a.append(4);
        a.append(5);
        a.append(1);
        //System.out.println(a);
        ListNode c = removeElements(a, 1);

        ListNode b = new ListNode(2);
        b.append(3);
        b.append(4);
        b.append(5);
        
        assertEquals(c, b);

        ListNode d = new ListNode(1);
        ListNode e = removeElements(d, 1);
        assertEquals(e, null);

        ListNode f = new ListNode(1);
        f.append(1);
        ListNode g = removeElements(f, 1);
        assertEquals(g, null);

        ListNode h = new ListNode(1);
        h.append(2);
        h.append(1);
        ListNode i = removeElements(h, 2);
        ListNode j = new ListNode(1);
        j.append(1);
        assertEquals(i, j);
        System.out.println(i);
        System.out.println(j);


    }

    //Happy Number
    public static boolean isHappy(int n) {
        int t = n;
        final Set<Integer> set = new HashSet<Integer>();

        while(t != 1) {
            int sum = 0;
            while(t > 0) {
                final int d = t % 10;
                sum += d * d;
                t /= 10;
            }
            t = sum;
            //System.out.println("sum = "+t);
            if(!set.add(t)) {
                //loop detected
                //System.out.println("loop!");
                return false;
            }
        }

        return true;
    }
    public static void testIsHappy() {
        assertEquals(isHappy(1), true);
        assertEquals(isHappy(2), false);
        assertEquals(isHappy(19), true);
    }

    //House Robber 

    public static int rob(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];

        final List<RobNode> leaves = new ArrayList<RobNode>();
        leaves.add(new RobNode(nums[0], 0)); //rob
        leaves.add(new RobNode(0, 2)); //no rob
        final ArrayList<RobNode> toBeAdded = new ArrayList<RobNode>();

        for(int i=1; i<nums.length; i++) {
            for(RobNode n : leaves) {
            //while(!leaves.isEmpty()) {
                //final RobNode n = leaves.remove();
                if(n.pass == 0) {
                    n.pass = 1; //can't rob this time
                } else if(n.pass == 1) {
                    if(i == nums.length -1) {
                        n.pass = 0;
                        n.val += nums[i];
                    } else {
                        n.pass = 2;
                        toBeAdded.add(new RobNode(nums[i]+n.val, 0));
                    }
                } else { //2: gotta rob this time
                    n.pass = 0;
                    n.val += nums[i];
                }
            }
            leaves.addAll(toBeAdded); 
            toBeAdded.ensureCapacity(toBeAdded.size());
            toBeAdded.clear();
            //System.out.println(i+": leaf size: "+leaves.size());
            int max = -1;
            for(int j=leaves.size()-1; j>=0; j--) {
                if(leaves.get(j).pass == 0) { 
                    if(leaves.get(j).val > max) {
                        max = leaves.get(j).val;
                    }
                    leaves.remove(j);
                }
            }
            leaves.add(new RobNode(max, 0));
            //System.out.println(i+": leaf size(after reducing): "+leaves.size());
        }
        int maxScore = -1;
        for(RobNode n : leaves) {
            if(n.val > maxScore) maxScore = n.val;
        }

        return maxScore;
    }
    static class RobNode {
        int val = 0;
        int pass = 0;
        public RobNode(int v, int p) { 
            val = v;
            pass = p;
        }
    }
    public static void testRob() {
        assertEquals(rob(new int[] {1}), 1);
        assertEquals(rob(new int[] {0, 0}), 0);
        assertEquals(rob(new int[] {1, 1}), 1);
        assertEquals(rob(new int[] {3, 5, 1, 2, 4}), 9);

        //rob(new int[] {183,219,57,193,94,233,202,154,65,240,97,234,100,249,186,66,90});
        //rob(new int[] {183,219,57,193,94,233,202,154,65,240,97,234,100,249,186,66,90,238,168,128,177,235,50,81,185,165,217,207,88,80,112,78,135,62,228,247,211});
        rob(new int[] {114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205,169,241,202,144,240});
        rob(new int[] {155,44,52,58,250,225,109,118,211,73,137,96,137,89,174,66,134,26,25,205,239,85,146,73,55,6,122,196,128,50,61,230,94,208,46,243,105,81,157,89,205,78,249,203,238,239,217,212,241,242,157,79,133,66,36,165});
    }


    public static void main(String[] args) {
        testSingleNumber();
        testInvertTree();
        testContainsNearbyDuplicate2();
        testRomanToInt();
        testContainsDuplicate();
        testReverseList();
        testIsIsomorphic();
        testCountPrimes();
        testRemoveElements();
        testIsHappy();
        testRob();
    }
}
