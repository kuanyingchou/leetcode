import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.stream.Collectors;

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
        //arrays >>>

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
        TreeNode(int x) {
            this(x, null, null);
        }
        TreeNode(int x, TreeNode l, TreeNode r) {
            val = x;
            left = l;
            right = r;
        }

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
        assertEquals(rob3(new int[] {1}), 1);
        assertEquals(rob3(new int[] {0, 0}), 0);
        assertEquals(rob3(new int[] {1, 1}), 1);
        assertEquals(rob3(new int[] {3, 5, 1, 2, 4}), 9);
        assertEquals(rob3(new int[] {1, 0, 0, 1, 9}), 10);
        assertEquals(rob3(new int[] {1, 0, 0, 0, 1, 9}), 10);
        assertEquals(rob3(new int[] {1, 0, 0, 0, 1}), 2);
        assertEquals(rob3(new int[] {1, 0, 0, 0, 1, 9, 99}), 101);

        //rob(new int[] {183,219,57,193,94,233,202,154,65,240,97,234,100,249,186,66,90});
        //rob(new int[] {183,219,57,193,94,233,202,154,65,240,97,234,100,249,186,66,90,238,168,128,177,235,50,81,185,165,217,207,88,80,112,78,135,62,228,247,211});

        rob3(new int[] {114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205,169,241,202,144,240});
        rob3(new int[] {155,44,52,58,250,225,109,118,211,73,137,96,137,89,174,66,134,26,25,205,239,85,146,73,55,6,122,196,128,50,61,230,94,208,46,243,105,81,157,89,205,78,249,203,238,239,217,212,241,242,157,79,133,66,36,165});
    }

    // you need to treat n as an unsigned value
    public static int hammingWeight(int n) {
        int sum = 0;
        if(n<0) {
            n ^= Integer.MIN_VALUE;
            sum++;
        }
        int t = n;
        while(t != 0) {
            if(t % 2 == 1) {
                sum++;
            }
            t = t / 2;
        }
        return sum;
    }
    public static void testHammingWeight() {
        assertEquals(hammingWeight(11), 3);
        assertEquals(hammingWeight(Integer.MIN_VALUE), 1);
    }

    // you need treat n as an unsigned value
    public static int reverseBits(int n) {
        int res = 0;
        for(int i=31; i>=0; i--) {
            //System.out.print(Integer.toBinaryString(res));
            //System.out.print(" - ");
            res |= ((n & 1) << i);
            n = n >>> 1;
            //System.out.println(Integer.toBinaryString(res));
        }
        return res;
    }
    public static int reverseBits2(int n) {
        n = n>>>16 | n<<16;
        n = (n & 0xff00ff00) >>> 8 | (n & 0x00ff00ff) << 8;
        n = (n & 0xf0f0f0f0) >>> 4 | (n & 0x0f0f0f0f) << 4;
        n = (n & 0xcccccccc) >>> 2 | (n & 0x33333333) << 2;
        n = (n & 0xaaaaaaaa) >>> 1 | (n & 0x55555555) << 1;
        return n;
    }
    public static void testReverseBits() {
        /*
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(1<<31));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE<<1));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE>>1));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE>>>10));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE>>>31));
        */

        assertEquals(reverseBits2(43261596), 964176192);
        assertEquals(reverseBits2(1), Integer.MIN_VALUE);
        assertEquals(reverseBits2(Integer.MIN_VALUE), 1);
          
    }

    public static void rotate(int[] nums, int k) {
        int[] res = new int[nums.length];
        for(int i = 0; i<nums.length; i++) {
            res[(i+k)%nums.length] = nums[i];
        }
        for(int i = 0; i<nums.length; i++) {
            nums[i] = res[i];
        }
    }
    public static void testRotate() {
        final int[] a1 = new int[] {1,2,3,4,5,6,7};
        System.out.println(Arrays.toString(a1));
        rotate(a1, 3);
        System.out.println(Arrays.toString(a1));

        final int[] a2 = new int[] {1,2,3,4,5,6};
        System.out.println(Arrays.toString(a2));
        rotate(a2, 2);
        System.out.println(Arrays.toString(a2));
        //assertEquals(a1, 
                //new int[] {5,6,7,1,2,3,4});
    }

    public static String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0) return "";
        int lcp = strs[0].length();
        for(int i=1; i<strs.length; i++) {
            if(lcp > strs[i].length()) {
                lcp = strs[i].length();
            }
            for(int j=0; j<lcp; j++) {
                if(strs[0].charAt(j) != strs[i].charAt(j)) {
                    lcp = j;
                    break;
                }
            }
            //System.out.println(strs[i] + " - " + lcp);
        }
        return strs[0].substring(0, lcp);
    }
    public static void testLongestCommonPrefix() {
        assertEquals(
                longestCommonPrefix(new String[] {
                "abcdef", "abc", "ab"}), "ab");
        assertEquals(
                longestCommonPrefix(new String[] {
                "abc", "abcdef", "abcdefg"}), "abc");
    }

    static class Range {
        private int[] range = new int[2];
        private int index=-1;
        public void add(int n) {
            if(index == -1 || index == 0) {
                range[++index] = n;
            } else {
                range[index] = n;
            }
        }
        public boolean hasValue() {
            return index >= 0;
        }
        public void reset() {
            index = -1;
        }
        public String toString() {
            if(index == -1) {
                return "";
            } else if(index == 0) {
                return Integer.toString(range[0]);
            } else {
                return range[0] + "->" + range[1];
            }
        }
    }
    public static List<String> summaryRanges(int[] nums) {
        final List<String> res = new ArrayList<String>();
        if(nums == null || nums.length == 0) return res; 


        Range r = new Range();
        r.add(nums[0]);
        for(int i=0, j=1; j<nums.length; i++, j++) {
            if(nums[j] - nums[i] == 1) {
                r.add(nums[j]);
            } else {
                res.add(r.toString());
                r.reset();
                r.add(nums[j]);
            }
        }
        if(r.hasValue()) {
            res.add(r.toString());
        }
        return res;
    }
    public static void testSummaryRanges() {
        System.out.println(summaryRanges(new int[] {}));
        System.out.println(summaryRanges(new int[] {0}));
        System.out.println(summaryRanges(new int[] {0, 1, 2}));
        System.out.println(summaryRanges(new int[] {1, 3, 5}));
        System.out.println(summaryRanges(new int[] {0, 1, 2, 4, 5, 7}));
    }

    static class Rect {
        int x1, y1, x2, y2;
        public Rect(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        public int surface() {
            return (x2-x1) * (y2-y1);
        }
        public int intersection(Rect that) {
            final int rx = Math.min(x2, that.x2);
            final int lx = Math.max(x1, that.x1);
            final int ty = Math.min(y2, that.y2);
            final int by = Math.max(y1, that.y1);
            if(rx <= lx) return 0;
            if(ty <= by) return 0;
            final int res = (rx - lx) * (ty - by);
            //System.out.println("intersetion: "+res);
            return res;
        }
    }
    public static int computeArea(
            int A, int B, int C, int D, int E, int F, int G, int H) {
        final Rect r1 = new Rect(A, B, C, D);
        final Rect r2 = new Rect(E, F, G, H);
        return r1.surface() + r2.surface() - r1.intersection(r2);
    }
    public static void testComputeArea() {
        assertEquals(computeArea(0, 0, 3, 3, 0, 0, 3, 3), 9);
        assertEquals(computeArea(-3, 0, 3, 4, 0, -1, 9, 2), 45);
        assertEquals(computeArea(0, 0, 0, 0, -1, -1, 1, 1), 4);
        assertEquals(computeArea(-2, -2, 2, 2, 3, 3, 4, 4), 17);
        assertEquals(computeArea(
                    -1500000001, 0, -1500000000, 1, 
                    1500000000, 0, 1500000001, 1), 2);
        
    }

    public static int trailingZeroes(int n) {
        int fac = 1;
        for(int i=n; i>0; i--) {
            fac *= i;
        }
        int count = 0;
        while(fac != 0 && fac % 10 == 0) {
            count++;
            fac /= 10;
        }
        return count;
    }
    public static int trailingZeroes2(int n) { //>>>
        //100
        //20 + f 20
        //4 + f 4
        //0 + f 0
        return n == 0? 0: (n/5 + trailingZeroes2(n/5));
    }
    public static void testTrailingZeroes() {
        assertEquals(trailingZeroes2(0), 0);
        assertEquals(trailingZeroes2(6), 1);
        assertEquals(trailingZeroes2(10), 2);
    }

    
    public static int titleToNumber(String s) {
        int res = 0;
        int mul = 1;
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(s.length()-1-i);
            res += mul * (c - 64);
            mul *= 26;
        }
        return res;
    }
    public static void testTitleToNumber() {
        assertEquals(titleToNumber("A"), 1);
        assertEquals(titleToNumber("B"), 2);
        assertEquals(titleToNumber("C"), 3);
        assertEquals(titleToNumber("Z"), 26);
        assertEquals(titleToNumber("AA"), 27);
        assertEquals(titleToNumber("AB"), 28);
    }

    public static int majorityElement(int[] nums) {
        final Map<Integer, Integer> records = new HashMap<Integer, Integer>();
        for(int i=0; i<nums.length; i++) {
            if(records.containsKey(nums[i])) {
                records.put(nums[i], records.get(nums[i]) + 1);
            } else {
                records.put(nums[i], 1);
            }
        }
        final Set<Integer> keys = records.keySet();
        int max = Integer.MIN_VALUE;
        int res = Integer.MIN_VALUE; //
        for(Integer k: keys) {
            final int v = records.get(k);
            if(v > max) {
                max = v;
                res = k;
            }
        }
        return res;
    }
    public static int majorityElementImpl2(int[] nums) {
        int major = nums[0]; int count = 1;
        for(int i=1; i<nums.length; i++) {
            if(nums[i] == major) {
                count++;
            } else if(count>0) {
                count--;
            } else {
                major = nums[i];
            }
        }
        return major;
    }
    public static void testMajorityElement() {
        assertEquals(majorityElement(new int[] {2, 3, 3, 3, 2}), 3);
        assertEquals(majorityElement(new int[] {3, 3, 3, 2, 2}), 3);
        assertEquals(majorityElement(new int[] {2, 2, 3, 3, 3}), 3);
        assertEquals(majorityElement(new int[] {3, 2, 3, 2, 3}), 3);
        assertEquals(majorityElement(new int[] {3, 2, 3}), 3);
    }

    public static String convertToTitle(int n) {
        if(n == 0) throw new RuntimeException();
        String res = "";
        while(n != 0) {
            int r = n % 26;
            if(r == 0) {
                res = 'Z' + res;
                n--;
            } else {
                res = (char)(r + 64) + res;
            }
            n /= 26;
        }
        return res;
    }
    public static void testConvertToTitle() {
        System.out.println((int)'A');
        assertEquals(convertToTitle(1), "A");
        assertEquals(convertToTitle(2), "B");
        assertEquals(convertToTitle(3), "C");
        assertEquals(convertToTitle(26), "Z");
        assertEquals(convertToTitle(27), "AA");
        assertEquals(convertToTitle(28), "AB");
        assertEquals(convertToTitle(52), "AZ");
        assertEquals(convertToTitle(53), "BA");
    }

    public static int compareVersion(String version1, String version2) {
        final String[] v1 = version1.split("\\.");
        final String[] v2 = version2.split("\\.");
        final int min = Math.min(v1.length, v2.length);
        int res = 0;
        for(int i=0; i<min; i++) {
            final int n1 = Integer.valueOf(v1[i]);
            final int n2 = Integer.valueOf(v2[i]);
            if(n1 > n2) { res = 1;  break; }
            else if(n1 < n2) { res = -1;  break; }
        }
        if(res == 0) {
            if(v1.length > v2.length) { 
                for(int i=min; i<v1.length; i++) {
                    if(Integer.valueOf(v1[i]) != 0) { 
                        res = 1;
                        break;
                    }
                }
            } else if(v1.length < v2.length) { 
                for(int i=min; i<v2.length; i++) {
                    if(Integer.valueOf(v2[i]) != 0) { 
                        res = -1;
                        break; //>>>duplicates
                    }
                }
            } 
        }
        return res;
    }
    public static void testCompareVersion() {
        assertEquals(compareVersion("1.1", "0.1"), 1);
        assertEquals(compareVersion("0.2", "1.1"), -1);
        assertEquals(compareVersion("1.1", "1.1"), 0);
        assertEquals(compareVersion("1.1.2", "1.1.1"), 1);
        assertEquals(compareVersion("13.37", "13.35"), 1);
        assertEquals(compareVersion("1.2", "1.1.1"), 1);
        assertEquals(compareVersion("1.1.1", "1.1"), 1);
        assertEquals(compareVersion("1.1", "1.1.1"), -1);
        assertEquals(compareVersion("1.0", "1"), 0);
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int diff = length(headA) - length(headB);
        if(diff > 0) {
            for(int i=0; i<diff; i++) {
                headA = headA.next;
            }
        } else if(diff < 0) {
            for(int i=0; i<-diff; i++) {
                headB = headB.next;
            }
        }
        assert length(headA) == length(headB);

        while(headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
    public static int length(ListNode n) {
        int count = 0;
        while(n != null) {
            count++;
            n = n.next;
        }
        return count;
    }
    public static void testGetintersectionNode() {
        final ListNode a = new ListNode(1);
        final ListNode b = new ListNode(3);
        final ListNode c = new ListNode(5);
        final ListNode d = new ListNode(7);
        final ListNode e = new ListNode(9);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        final ListNode f = new ListNode(2);
        final ListNode g = new ListNode(4);
        final ListNode h = new ListNode(6);
        f.next = g;
        g.next = h;
        h.next = c;

        assertEquals(getIntersectionNode(a, f), c);
    }

    public static boolean hasPathSum(TreeNode root, int sum) {
        return findPathSum(root, 0, sum);
    }
    private static boolean findPathSum(TreeNode node, int acc, int target) {
        if(node == null) return false;
        //System.out.println("visiting "+node);
        acc += node.val;
        //if(target < acc) return false; //node.val could be negative
        if(node.left == null && node.right == null) {
            return acc == target;
        } else {
            if(node.left != null) {
                if(findPathSum(node.left, acc, target)) return true;
            } 
            if(node.right != null) {
                if(findPathSum(node.right, acc, target)) return true;
            }
            return false;
        }
    }

    public static void testHasPathSum() {
        TreeNode n5 = new TreeNode(5);
        TreeNode n4 = new TreeNode(4);
        TreeNode n8 = new TreeNode(8);
        n5.left = n4;
        n5.right = n8;

        TreeNode n11 = new TreeNode(11);
        n4.left = n11;
        TreeNode n7 = new TreeNode(7);
        TreeNode n2 = new TreeNode(2);
        n11.left = n7;
        n11.right = n2;
        TreeNode n13 = new TreeNode(13);
        TreeNode n4_ = new TreeNode(4);
        n8.left = n13;
        n8.right = n4_;
        TreeNode n1 = new TreeNode(1);
        n4_.right = n1;
        System.out.println(n5);

        assertEquals(hasPathSum(n5, 22), true);
        assertEquals(hasPathSum(n5, 27), true);
        assertEquals(hasPathSum(n5, 26), true);
        assertEquals(hasPathSum(n5, 18), true);
        assertEquals(hasPathSum(n5, 19), false);
        assertEquals(hasPathSum(n5, 0), false);
        assertEquals(hasPathSum(n5, 1), false);
        assertEquals(hasPathSum(null, 1), false);

    }

    public static TreeNode lowestCommonAncestor(
            TreeNode root, TreeNode p, TreeNode q) {
        if(isDescendant(p, root) && isDescendant(q, root)) {
            if(isDescendant(p, root.left) && isDescendant(q, root.left)) {
                return lowestCommonAncestor(root.left, p, q);
            } else if(isDescendant(p, root.right) && isDescendant(q, root.right)) {
                return lowestCommonAncestor(root.right, p, q);
            } else {
                return root;
            }
        } else {
            return null;
        }
        
    }
    private static boolean isDescendant(TreeNode a, TreeNode b) {
        if(a == null || b == null) return false;

        if(a == b) return true;
        if(a == b.left) return true;
        if(a == b.right) return true;

        if(b.left != null) {
            if(isDescendant(a, b.left)) {
                return true;
            }
        }
        if(b.right != null) {
            if(isDescendant(a, b.right)) {
                return true;
            }
        }
        return false;
    }
    private static void testLowestCommonAncestor() {
        TreeNode n2, n4, n8;
        TreeNode root = new TreeNode(6, 
            n2 = new TreeNode(2, 
                new TreeNode(0), 
                    n4 = new TreeNode(4, 
                        new TreeNode(3),
                        new TreeNode(5))),
            n8 = new TreeNode(8,
                new TreeNode(7),
                new TreeNode(9)));
        assertEquals(lowestCommonAncestor(root, n2, n8), root);
        assertEquals(lowestCommonAncestor(root, n2, n4), n2);
        assertEquals(lowestCommonAncestor(root, n8, n8), n8);
    }
    public static boolean isPalindrome1(ListNode head) {
        ListNode newHead = null;
        for(ListNode p = head; p!=null; p = p.next) {
            //System.out.println(p.val);
            newHead = new ListNode(p.val, newHead);
        }
        for(ListNode p = head, q = newHead; p!=null; p = p.next, q = q.next) {
            if(p.val != q.val) { return false; }
        }
        return true;
    }
    public static boolean isPalindrome2(ListNode head) {
        int count = countLinkedList(head);
        if(count == 1) return true;
        if(count % 2 != 0) count++;
        ListNode mid = getListNodeAt(head, count/2);
        ListNode tail = reverseLinkedList(mid);
        for(ListNode p=tail, q=head; p!=null; p=p.next, q=q.next) {
            if(p.val != q.val) return false;
        }
        return true;
    }
    private static int countLinkedList(ListNode head) {
        int count = 0;
        for(ListNode p = head; p!=null; p = p.next) {
            count++;
        }
        return count;
    }
    private static ListNode getListNodeAt(ListNode head, int index) {
        for(ListNode p = head; p!=null; p = p.next, index--) {
            if(index == 0) return p;
        }
        return null;
    }
    private static ListNode reverseLinkedList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while(curr != null) {
            ListNode t = curr.next;
            curr.next = prev;
            prev = curr;
            curr = t;
        }
        return prev;
    }
    public static void testIsPalindrome() {
        ListNode a = new ListNode(1, new ListNode(2, new ListNode(1)));
        assertEquals(isPalindrome2(a), true);

        ListNode b = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(2, new ListNode(1)))));
        assertEquals(isPalindrome2(b), true);

        ListNode c = new ListNode(1, new ListNode(2, new ListNode(3)));
        assertEquals(isPalindrome2(c), false);
    }

    static class MyQueue {
        private Stack<Integer> stack = new Stack<Integer>();
        // Push element x to the back of queue.
        public void push(int x) {
            stack.push(x);
        }

        // Removes the element from in front of queue.
        public void pop() {
            Stack<Integer> t = dump();
            t.pop();
            fill(t);
        }

        private Stack<Integer> dump() {
            Stack<Integer> t = new Stack<Integer>();
            while(!stack.empty()) {
                t.push(stack.peek());
                stack.pop();
            }
            return t;
        }
        private void fill(Stack<Integer> t) {
            while(!t.empty()) {
                stack.push(t.peek());
                t.pop();
            }
        }

        // Get the front element.
        public int peek() {
            Stack<Integer> t = dump();
            int r = t.peek();
            fill(t);
            return r;
        }

        // Return whether the queue is empty.
        public boolean empty() {
            return stack.empty();
        }
    }
    public static void testStackQueue() {
        final MyQueue q = new MyQueue();
        q.push(3);
        q.push(5);
        q.push(7);
        assertEquals(q.peek(), 3);
        assertEquals(q.peek(), 3);
        q.pop();
        assertEquals(q.peek(), 5);
        assertEquals(q.peek(), 5);
        assertEquals(q.empty(), false);
        q.pop();
        q.pop();
        assertEquals(q.empty(), true);
    }
    public static boolean isPowerOfTwo(int n) {
        if(n == 1) return true;
        int sum = 0;
        while(n > 0) {
            sum += n & 1;
            n >>= 1;
        }
        return sum == 1;
    }
    public static void testIsPowerOfTwo() {
        assertEquals(isPowerOfTwo(1), true);
        assertEquals(isPowerOfTwo(2), true);
        assertEquals(isPowerOfTwo(4), true);
        assertEquals(isPowerOfTwo(8), true);

        assertEquals(isPowerOfTwo(3), false);
        assertEquals(isPowerOfTwo(5), false);
        assertEquals(isPowerOfTwo(12), false);
    }

    public static int myAtoi(String str) {
        if(str == null || str.isEmpty()) return 0;
        str = str.trim();
        int start = 0;

        boolean positive = true;
        char firstChar = str.charAt(start);
        if(firstChar == '-') {
            positive = false;
            start++;
        } else if(firstChar == '+') {
            start++;
        }

        int end = str.length()-1;
        while(end>=start && !Character.isDigit(str.charAt(end))) {
            end--;
        }

        long val = 0;
        //System.out.println("start...");
        for(int i=start; i<=end; i++) {
            if(!Character.isDigit(str.charAt(i))) {
                break;
            }
            val *= 10;
            val += Character.digit(str.charAt(i), 10);
            if(positive) {
                if(val > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
            } else {
                if(-val < Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
            }
            //System.out.println("char: "+str.charAt(i)+" val: "+val);
        }
        if(positive) {
            return (int)val;
        } else {
            return (int)(-val);
        }
    }
    public static void testMyAtoi() {
        assertEquals(myAtoi("0"), 0);
        assertEquals(myAtoi("1"), 1);
        assertEquals(myAtoi("123"), 123);
        assertEquals(myAtoi("+123"), 123);
        assertEquals(myAtoi("-123"), -123);
        assertEquals(myAtoi("123abc"), 123);
        assertEquals(myAtoi("123412341234123412341234"), Integer.MAX_VALUE);
        assertEquals(myAtoi("-123412341234123412341234"), Integer.MIN_VALUE);
        assertEquals(myAtoi(null), 0);
        assertEquals(myAtoi(""), 0);
        assertEquals(myAtoi("+"), 0);
        assertEquals(myAtoi("-"), 0);
        assertEquals(myAtoi("-"), 0);
        assertEquals(myAtoi("+-2"), 0);
        assertEquals(myAtoi("  -0012a42"), -12);
        assertEquals(myAtoi("9223372036854775809"), 2147483647);
        assertEquals(myAtoi(String.valueOf(Integer.MAX_VALUE)), 2147483647);
        assertEquals(myAtoi(String.valueOf(Integer.MIN_VALUE)), -2147483648);
    }

    static class MyStack {
        private Queue<Integer> queue = new ArrayDeque<Integer>();

        // Push element x onto stack.
        public void push(int x) {
            queue.add(x);
        }

        // Removes the element on top of the stack.
        public void pop() {
            Queue<Integer> t = new ArrayDeque<Integer>(); 
            while(queue.size()>1) {
                t.add(queue.poll());
            }
            queue.poll(); //bye bye
            while(!t.isEmpty()) {
                queue.add(t.poll());
            }
        }

        // Get the top element.
        public int top() {
            Queue<Integer> t = new ArrayDeque<Integer>(); 
            while(queue.size()>1) {
                t.add(queue.poll());
            }
            int res = queue.peek();
            t.add(queue.poll());
            while(!t.isEmpty()) {
                queue.add(t.poll());
            }
            return res;
        }

        // Return whether the stack is empty.
        public boolean empty() {
            return queue.isEmpty();
        }
    }
    public static void testMyStack() {
        MyStack stack = new MyStack();
        assertEquals(stack.empty(), true);
        stack.push(3);
        stack.push(5);
        stack.push(7);
        assertEquals(stack.top(), 7);
        stack.pop();
        assertEquals(stack.top(), 5);
        stack.pop();
        assertEquals(stack.top(), 3);
        stack.pop();
        assertEquals(stack.empty(), true);

    }

    //ZigZag
    public static String convert(String s, int numRows) {
        final List<StringBuilder> rows = new ArrayList<>();
        for(int i=0; i<numRows; i++) {
            rows.add(new StringBuilder());
        }
        int index = 0;
        int rowIndex = 0;
        int rowStep = 1;
        while(index < s.length()) {
            final StringBuilder r = rows.get(rowIndex);
            //System.out.println("rowIndex: "+rowIndex+" step: "+rowStep);
            r.append(s.charAt(index++));
            rowStep = checkZigzagStep(rowIndex, rowStep, numRows);
            rowIndex += rowStep;
        }
        final StringBuilder res = new StringBuilder();
        for(int i=0; i<numRows; i++) {
            res.append(rows.get(i));
        }

        return res.toString();
    }
    private static int checkZigzagStep(int index, int step, int numRows) {
        if(numRows == 1) return index;
        if(step > 0) {
            if(index == numRows - 1) {
                step = -1;
            }
        } else {
            if(index == 0) {
                step = 1;
            }
        }
        return step;
    }
    private static void testConvert() {
        assertEquals(convert("ABCDE", 4), "ABCED");
        assertEquals(convert("ABCDE", 2), "ACEBD");
        assertEquals(convert("ABCD", 2), "ACBD");
        assertEquals(convert("PAYPALISHIRING", 1), "PAYPALISHIRING");
        assertEquals(convert("PAYPALISHIRING", 3), "PAHNAPLSIIGYIR");
    }
    public static int reverse(int x) {
        boolean isPositive = true;
        if(x<0) {
            x = -x;
            isPositive = false;
        }
        long res = 0;
        while(x > 0) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        if(!isPositive) {
            res = -res;
        }
        if(res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) return 0;
        return (int)res;
    }
    private static void testReverse() {
        assertEquals(reverse(0), 0);
        assertEquals(reverse(1), 1);
        assertEquals(reverse(-1), -1);
        assertEquals(reverse(123), 321);
        assertEquals(reverse(-123), -321);
        assertEquals(reverse(1534236469), 0);
    }

    public static void deleteNode(ListNode node) {
        if(node == null) return;
        if(node.next == null) return;
        ListNode curr = node;
        ListNode prev = null;
        while(curr.next!=null) {
            curr.val = curr.next.val;
            prev = curr;
            curr = curr.next;
        }
        prev.next = null;
    }
    private static void testDeleteNode() {
        ListNode n = new ListNode(1);
        ListNode m = new ListNode(2, new ListNode(3, new ListNode(4)));

        n.next = m;
        System.out.println(n);
        deleteNode(m);
        System.out.println(n);
    }
    public static boolean isPalindromeInt(int x) {
        //long lx = x;
        if(x < 0) {
            return false;
            /*
            lx = -lx;
            if(lx > Integer.MAX_VALUE) return false;
            */
        }
        int t = x;
        int d = 1;
        while(t >= 10) {
            t /= 10;
            d *= 10;
        }
        if(d == 1) return true;

        t = x;
        while(t>0) {
            int left = x / d % 10;
            int right = t % 10;
            t /= 10;
            d /= 10;
            if(left != right) return false;
        }

        return true;
    }
    private static void testIsPalindromeInt() {
        assertEquals(isPalindromeInt(1), true);
        assertEquals(isPalindromeInt(121), true);
        assertEquals(isPalindromeInt(12321), true);
        assertEquals(isPalindromeInt(12322), false);
        assertEquals(isPalindromeInt(-2147483648), false);
        assertEquals(isPalindromeInt(-2147447412), false); //leetcode testcase
        
    }

    static class MinStack {
        private static int CAPACITY = 10;
        private int[] data = new int[CAPACITY];
        private int size = 0;
        private Stack<Integer> mins = new Stack<>();

        public void push(int x) {
            if(size == data.length) {
                enlarge();
            }
            data[size++] = x;
            if(mins.size() == 0 || x <= mins.peek()) {
                mins.push(x);
            }
        }
        public int size() { return size; }

        public void pop() {
            size--;
            if(data[size] == mins.peek()) mins.pop();
        }

        public int top() {
            return data[size-1];
        }

        public int getMin() {
            return mins.peek();
        }

        private void enlarge() {
            int[] newData = new int[data.length * 2]; //too aggressive?
            for(int i=0; i<data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }
    private static void testMinStack() {
        MinStack stack = new MinStack();
        stack.push(3);
        stack.push(5);
        stack.push(7);
        stack.pop();
        assertEquals(stack.top(), 5);
        stack.push(7);
        assertEquals(stack.top(), 7);
        assertEquals(stack.getMin(), 3);

        MinStack s2 = new MinStack();
        s2.push(2);
        s2.push(0);
        s2.push(3);
        s2.push(0);
        assertEquals(s2.getMin(), 0);
        s2.pop();
        assertEquals(s2.getMin(), 0);
        s2.pop();
        assertEquals(s2.getMin(), 0);
        s2.pop();
        assertEquals(s2.getMin(), 2);
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        ListNode to = l1;
        ListNode toPrev = null;
        ListNode from = l2;
        while(from != null) {
            boolean found = false;
            while(to != null) {
                if(to.val >= from.val) {
                    if(toPrev == null) {
                        ListNode fn = from.next;
                        from.next = to;
                        l1 = from;
                        toPrev = from;
                        from = fn;
                    } else {
                        ListNode fn = from.next;
                        from.next = to;
                        toPrev.next = from;
                        toPrev = from;
                        from = fn;
                    }
                    found = true;
                    break;
                } else {
                    toPrev = to;
                    to = to.next;
                }
            }
            if(!found) {
                toPrev.next = from;
                break;
            }
        }
        return l1;
    }
    private static void testMergeTwoLists() {
        ListNode l1 = new ListNode(3, new ListNode(5, new ListNode(7)));
        ListNode l2 = new ListNode(2, new ListNode(4, new ListNode(6)));
        assertEquals(mergeTwoLists(l1, l2).toString(), 
                new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5,
                                new ListNode(6, new ListNode(7)))))).toString());
        assertEquals(mergeTwoLists(l1, null).toString(), l1.toString());
        assertEquals(mergeTwoLists(null, l2).toString(), l2.toString());
        assertEquals(mergeTwoLists(null, null), null);

        ListNode l3 = new ListNode(1, new ListNode(2, new ListNode(3)));
        ListNode l4 = new ListNode(4, new ListNode(5, new ListNode(6)));
        assertEquals(mergeTwoLists(l3, l4).toString(), 
                new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4,
                                new ListNode(5, new ListNode(6)))))).toString());
        ListNode l5 = new ListNode(4, new ListNode(5, new ListNode(6)));
        ListNode l6 = new ListNode(1, new ListNode(2, new ListNode(3)));
        assertEquals(mergeTwoLists(l5, l6).toString(), 
                new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4,
                                new ListNode(5, new ListNode(6)))))).toString());
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) return null;
        ListNode l = head;
        while(n>0 && l!=null) {
            l = l.next;
            n--;
        }
        if(n>0) return head;
        ListNode f = head; ListNode fp = null;
        while(l!=null) {
            l = l.next;
            fp = f;
            f = f.next;
        }
        if(fp==null) {
            head = head.next;
        } else {
            fp.next = f.next;
        }
        return head;
    }

    static class Int {
        int val = 0;
        public Int(int v) {
            val = v;
        }
    }
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        Int count = new Int(0); 
        return _removeNthFromEnd2(head, n, count);
    }
    public static ListNode _removeNthFromEnd2(ListNode head, int n, Int count) {
        if(head == null) return null;
        head.next = _removeNthFromEnd2(head.next, n, count);
        ++count.val;
        if(count.val == n) {
            return head.next;
        } else {
            return head;
        }
    }

    private static void testRemoveNthFromEnd() {
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(3)));
        assertEquals(removeNthFromEnd2(l1, 2).toString(), 
                new ListNode(1, new ListNode(3)).toString());
        ListNode l2 = new ListNode(1);
        assertEquals(removeNthFromEnd2(l2, 2).toString(), 
                new ListNode(1).toString());
        ListNode l3 = new ListNode(1);
        assertEquals(removeNthFromEnd2(l3, 1), null);
        ListNode l4 = new ListNode(1, new ListNode(2, new ListNode(3)));
        assertEquals(removeNthFromEnd2(l4, 1).toString(), 
                new ListNode(1, new ListNode(2)).toString());

    }

    public static boolean isPalindromeStr(String s) {
        if(s == null || s.length() <=1) return true;
        int i = find(0, s, +1);
        int j = find(s.length()-1, s, -1);
        while(i<j) {
            if(Character.toLowerCase(s.charAt(i)) != 
                    Character.toLowerCase(s.charAt(j))) 
                return false;
            i = find(i+1, s, +1);
            j = find(j-1, s, -1);
        }
        return true;
    }
    private static int find(int index, String s, int step) {
        int i = index;
        while(i>=0 && i<s.length()) {
            if(Character.isAlphabetic(s.charAt(i)) ||
                    Character.isDigit(s.charAt(i)))
                return i;
            i += step;
        }
        return i; //invalid
    }
    private static void testIsPalindromeStr() {
        assertEquals(isPalindromeStr(null), true);
        assertEquals(isPalindromeStr(""), true);
        assertEquals(isPalindromeStr("a"), true);
        assertEquals(isPalindromeStr("ab"), false);
        assertEquals(isPalindromeStr("aa"), true);
        assertEquals(isPalindromeStr("aba"), true);
        assertEquals(isPalindromeStr("abba"), true);
        assertEquals(isPalindromeStr(",,aa"), true);
        assertEquals(isPalindromeStr(",,a,,,"), true);
        assertEquals(isPalindromeStr("aA"), true);
    }
        

    public static boolean isParenthesesValid(String s) {
        if(s == null) return true;
        Stack<Character> stack = new Stack<>();
        int i = 0;
        while(i<s.length()) {
            char c = s.charAt(i);
            if("([{".indexOf(c) >= 0) {
                stack.push(c);
            } else if(")]}".indexOf(c) >= 0) {
                if(stack.isEmpty()) return false;
                char p = stack.peek();
                switch(c) {
                    case ')': if(p == '(') stack.pop();
                              else return false;
                              break;
                    case ']': if(p == '[') stack.pop();
                              else return false;
                              break;
                    case '}': if(p == '{') stack.pop();
                              else return false;
                              break;
                }
            }
            i++;
        }
        if(stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    private static void testIsParenthesesValid() {
        assertEquals(isParenthesesValid(null), true);
        assertEquals(isParenthesesValid(""), true);
        assertEquals(isParenthesesValid("()"), true);
        assertEquals(isParenthesesValid("(())"), true);
        assertEquals(isParenthesesValid("()()"), true);
        assertEquals(isParenthesesValid("([{}])"), true);
        assertEquals(isParenthesesValid("("), false);
        assertEquals(isParenthesesValid("(]"), false);
        assertEquals(isParenthesesValid("((("), false);
    }

    public static int removeDuplicates(int[] nums) {
        if(nums == null) return 0;
        if(nums.length <= 1) return nums.length;
        int nodup = 0;
        int i = 1;
        while(i<nums.length) {
            if(nums[i] != nums[nodup]) {
                if(i>nodup+1) {
                    nums[nodup+1] = nums[i];
                }
                nodup++;
            }
            i++;
        }
        return nodup+1;
    }
    private static void testRemoveDuplicates() {
        int[] a=null;
        assertEquals(removeDuplicates(a=new int[] {1, 1, 2}), 2);
        System.out.println(Arrays.toString(a));
        assertEquals(removeDuplicates(a=new int[] {1, 2, 3}), 3);
        System.out.println(Arrays.toString(a));
        assertEquals(removeDuplicates(a=new int[] {1, 1, 1}), 1);
        System.out.println(Arrays.toString(a));
        assertEquals(removeDuplicates(a=new int[] {1, 1, 1, 2}), 2);
        System.out.println(Arrays.toString(a));
        assertEquals(removeDuplicates(a=new int[0]), 0);
        assertEquals(removeDuplicates(null), 0);
    }
    public static int lengthOfLastWord(String s) {
        if(s==null || s.length() == 0) return 0;
        int i = s.length()-1;
        int count = 0;
        while(i>=0) {
            if(Character.isAlphabetic(s.charAt(i))) {
                count++;
            } else {
                if(count!=0) {
                    break;
                } 
            }
            i--;
        }
        return count;

    }
    private static void testLengthOfLastWord() {
        assertEquals(lengthOfLastWord("Hello World"), 5);
        assertEquals(lengthOfLastWord("Hello"), 5);
        assertEquals(lengthOfLastWord(""), 0);
        assertEquals(lengthOfLastWord(null), 0);
        assertEquals(lengthOfLastWord("Hello World  "), 5);
        assertEquals(lengthOfLastWord("  "), 0);
    }

    public static List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        res.add(1);
        while(rowIndex > 0) {
            int i = 0;
            List<Integer> r = new ArrayList<>();
            r.add(1);
            while(i < res.size()) {
                int n = i+1 == res.size() ? 0 : res.get(i+1);
                r.add(res.get(i) + n);
                i++;
            }
            res = r;
            rowIndex--;
        }
        return res;
    }
    private static void testGetRow() {
        for(int i=0; i<10; i++) {
            System.out.println(getRow(i));
        }
    }


    public static int robImpl2(int[] nums) {
        List<RobDecision> decisions = new ArrayList<>();
        decisions.add(new RobDecision(0, 1));
        int i=0;
        while(i<nums.length) {
            int v = nums[i];
            int j = 0;
            int planSize = decisions.size();
            while(j < planSize) {
                RobDecision p = decisions.get(j);
                if(p != null) {
                    int av = p.val;
                    if(p.pass >= 3) {
                        decisions.set(j, null);
                    } else {
                        if(p.pass > 0) {
                            decisions.add(new RobDecision(av+v, 0));
                        }
                        p.pass++;
                    }
                }
                j++;
            }
            i++;
        }
        int max = Integer.MIN_VALUE;
        for(RobDecision p : decisions) {
            if(p!=null && p.val > max) {
                max = p.val;
            }
        }
        return max;
    }
    static class RobDecision {
        int val;
        int pass;
        public RobDecision(int v, int p) { 
            val = v;
            pass = p;
        }
    }

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null) return new ArrayList<List<Integer>>();
        Stack<RNode> rnodes = new Stack<>();
        rnodes.add(new RNode(root, null));
        boolean hasChildren;
        do {
            hasChildren = false;
            Stack<RNode> t = new Stack<>();
            while(!rnodes.isEmpty()) {
                RNode rn = rnodes.pop();
                if(rn.hasChildren()) {
                    if(rn.node.left != null) {
                        t.push(new RNode(rn.node.left, rn));
                    }
                    if(rn.node.right != null) {
                        t.push(new RNode(rn.node.right, rn));
                    }
                    hasChildren = true;
                } else {
                    t.push(rn);
                }
            }
            rnodes = t;
        } while(hasChildren);
        List<List<Integer>> res = new ArrayList<>();
        while(!rnodes.isEmpty()) {
            RNode n = rnodes.pop();
            if(n.sum() == sum) res.add(n.toList());
        }
        return res;
    }
    private static class RNode {
        RNode parent;
        TreeNode node;
        RNode(TreeNode n, RNode p) {
            parent = p;
            node = n;
        }
        int sum() {
            int res = node.val;
            RNode p = parent;
            while(p!=null) {
                res += p.node.val;
                p = p.parent;
            }
            return res;
        }
        List<Integer> toList() {
            List<Integer> res = new LinkedList<>();
            res.add(node.val);
            RNode p = parent;
            while(p!=null) {
                res.add(0, p.node.val);
                p = p.parent;
            }
            return res;
        }
        boolean hasChildren() {
            return node.left != null || node.right != null;
        }
    }
    private static void testPathSum() {
        TreeNode t1 = new TreeNode(5,
                new TreeNode(4,
                    new TreeNode(11,
                        new TreeNode(7),
                        new TreeNode(2)),
                    null),
                new TreeNode(8,
                    new TreeNode(13),
                    new TreeNode(4,
                        new TreeNode(5),
                        new TreeNode(1))));
        List<List<Integer>> r = pathSum(t1, 22);
        for(int i=0; i<r.size(); i++) {
            System.out.println("path sum: "+r.get(i));
        }
    }

    public static int minDepth(TreeNode root) {
        if(root == null) return 0;
        Stack<Digger> diggers = new Stack<>();
        diggers.add(new Digger(root, 1));
        boolean hasChildren = false;
        do {
            Stack<Digger> temp = new Stack<>();
            while(!diggers.isEmpty()) {
                Digger d = diggers.pop();
                if(d.hasChildren()) {
                    if(d.node.left != null) {
                        temp.push(new Digger(d.node.left, d.depth+1));
                    }
                    if(d.node.right != null) {
                        temp.push(new Digger(d.node.right, d.depth+1));
                    }
                    hasChildren = true;
                } else {
                    return d.depth;
                }
            }
            diggers = temp;
        } while(hasChildren);
        throw new RuntimeException("what?");
    }
    private static class Digger {
        TreeNode node;
        int depth;
        public Digger(TreeNode n, int d) {
            node = n;
            depth = d;
        }
        public boolean hasChildren() {
            return node.left != null || node.right != null;
        }
    }
    private static void testMinDepth() {
        TreeNode t1 = new TreeNode(5,
                new TreeNode(4,
                    new TreeNode(11,
                        new TreeNode(7),
                        new TreeNode(2)),
                    null),
                new TreeNode(8,
                    new TreeNode(13),
                    new TreeNode(4,
                        new TreeNode(5),
                        new TreeNode(1))));
        assertEquals(minDepth(t1), 3);
        
        TreeNode t2 = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3,
                    new TreeNode(4),
                    null));
        assertEquals(minDepth(t2), 2);
    }

    public static boolean isBalanced(TreeNode root) {
        if(root==null) return true;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode n = stack.pop();
            int left = getDepth(n.left);
            int right = getDepth(n.right);
            if(Math.abs(left-right)>1) return false;
            if(n.left != null) stack.push(n.left);
            if(n.right != null) stack.push(n.right);
        }
        return true;
    }
    private static int getDepth(TreeNode n) {
        if(n==null) return 0;
        Stack<Digger> stack = new Stack<Digger>();
        List<Integer> depths = new ArrayList<>();
        stack.push(new Digger(n, 1));
        while(!stack.isEmpty()) {
            Digger d = stack.pop();
            if(d.hasChildren()) {
                if(d.node.left != null) 
                    stack.push(new Digger(d.node.left, d.depth+1));
                if(d.node.right != null) 
                    stack.push(new Digger(d.node.right, d.depth+1));
            } else {
                depths.add(d.depth);
            }

        }
        int max = Integer.MIN_VALUE;
        for(Integer i : depths) {
            if(i>max) max = i;
        }
        return max;
    }
    private static void testIsBalanced() {
        TreeNode t1 = new TreeNode(5,
                new TreeNode(4,
                    new TreeNode(11,
                        new TreeNode(7),
                        new TreeNode(2)),
                    null),
                new TreeNode(8,
                    new TreeNode(13),
                    new TreeNode(4,
                        new TreeNode(5),
                        new TreeNode(1))));
        assertEquals(isBalanced(t1), false);
    }
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) return new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<List<Integer>> rows = new ArrayList<>();
        do {
            Queue<TreeNode> temp = new ArrayDeque<>();
            List<Integer> r = new ArrayList<>();
            while(!queue.isEmpty()) {
                TreeNode n = queue.remove();
                r.add(n.val);
                if(n.left != null) temp.add(n.left);
                if(n.right != null) temp.add(n.right);
            }
            rows.add(r);
            if(!temp.isEmpty()) queue = temp;
        } while(!queue.isEmpty());
        return rows;
    }

    public static int rob2(int[] arr) {
        int[] max = new int[arr.length];
        //assume elements in arr are all positive
        for(int i=0; i<arr.length; i++) max[i] = -1; 
        return _rob2(arr, 0, max);
    }
    private static int _rob2(int[] arr, int start, int[] cache) {
        if(arr.length == 0) return 0;
        if(start >= arr.length) return 0;
        if(start == arr.length-1) return arr[start];
        if(cache[start] != -1) return cache[start];
        int a = arr[start] + _rob2(arr, start+2, cache);
        int b = _rob2(arr, start+1, cache);
        if(a > b) {
            cache[start] = a;
            return a;
        } else {
            cache[start] = b;
            return b;
        }
    }

    public static int rob3(int[] arr) {
        if(arr.length == 0) return 0;
        if(arr.length == 1) return arr[0];
        int p = 0;
        int q = arr[arr.length-1];
        for(int i=2; i<=arr.length; i++) {
            int t = Math.max(
                    arr[arr.length-i]+p,
                    q);
            p = q;
            q = t;
        }
        return q;
    }

    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        int a = maxDepth(root.left);
        int b = maxDepth(root.right);
        return 1 + (a>b?a:b);
    }
    public int maxDepthIterative(TreeNode root) {
        if(root == null) return 0;
        final Stack<TreeNodeDepth> stack = new Stack<>();
        stack.push(new TreeNodeDepth(root, 1));
        int maxDepth = 0;
        while(!stack.isEmpty()) {
            TreeNodeDepth n = stack.pop();
            if(n.depth > maxDepth) maxDepth = n.depth;
            if(n.node.right != null)
                stack.push(new TreeNodeDepth(
                            n.node.right, n.depth+1));
            if(n.node.left != null)
                stack.push(new TreeNodeDepth(
                            n.node.left, n.depth+1));
        }
        return maxDepth;
    }
    //(1 (2 (4 5)) (3 (6 7 (8))))
    private static class TreeNodeDepth {
        public TreeNode node;
        public int depth;
        public TreeNodeDepth(TreeNode n, int d) {
            this.node = n;
            this.depth = d;
        }
    }

    public static boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        else return isMirror(root.left, root.right);
    }
    private static boolean isMirror(TreeNode a, TreeNode b) {
        if(a == null ^ b == null) { //one is null, the other is not
            return false;
        } else if(a == null) { // both are null
            return true;
        } else { // both are not null
            if(a.val != b.val) return false;
            if(!isMirror(a.left, b.right)) return false;
            if(!isMirror(a.right, b.left)) return false;
        }
        return true;
    }
    public static boolean isSymmetricIteration(TreeNode root) {
        if(root == null) return true;
        Stack<TreeNode> leftStack = new Stack<>();
        Stack<TreeNode> rightStack = new Stack<>();
        if(root.left != null) leftStack.push(root.left);
        if(root.right != null) rightStack.push(root.right);
        while(!leftStack.isEmpty() && !rightStack.isEmpty()) {
            TreeNode ln = leftStack.pop();
            TreeNode rn = rightStack.pop();
            if(ln == null ^ rn == null) 
                return false;
            else if(ln != null) {
                if(ln.val != rn.val)
                    return false;
                leftStack.push(ln.right);
                leftStack.push(ln.left);
                rightStack.push(rn.left);
                rightStack.push(rn.right);
            }
        }
        if(leftStack.isEmpty() ^ rightStack.isEmpty()) return false;
        return true;
    }

    private static void testIsSymmetric() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                    new TreeNode(3,
                        new TreeNode(5),
                        new TreeNode(6)),
                    new TreeNode(4,
                        new TreeNode(7),
                        new TreeNode(8))),
                new TreeNode(2,
                    new TreeNode(4,
                        new TreeNode(8),
                        new TreeNode(7)),
                    new TreeNode(3,
                        new TreeNode(6),
                        new TreeNode(5))));
        assertEquals(isSymmetricIteration(root), true);

        TreeNode root2 = new TreeNode(1,
                new TreeNode(2,
                    null,
                    new TreeNode(3)),
                new TreeNode(2,
                    null,
                    new TreeNode(3)));
        assertEquals(isSymmetricIteration(root2), false);
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null ^ q==null) return false;
        else if(p==null) return true;
        else if(p.val != q.val) return false;
        else return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static List<List<Integer>> levelOrderBottom(
            TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        List<TreeNode> row = new ArrayList<>();
        row.add(root);
        while(!row.isEmpty()) {
            res.add(0, row.stream().map(n->n.val).collect(Collectors.toList()));
            List<TreeNode> newRow = new ArrayList<>();
            for(TreeNode n: row) {
                if(n.left != null) newRow.add(n.left);
                if(n.right != null) newRow.add(n.right);
            }
            row = newRow;
        }
        return res;
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = m+n-1;
        int mi = m-1;
        int ni = n-1;
        while(mi >= 0 && ni >= 0) {
            if(nums1[mi] >= nums2[ni]) {
                nums1[index--] = nums1[mi--];
            } else {
                nums1[index--] = nums2[ni--];
            }
        }
        while(mi >= 0) {
            nums1[index--] = nums1[mi--];
        }
        while(ni >= 0) {
            nums1[index--] = nums2[ni--];
        }
    }
    private static void testMerge() {
        int[] a = {1, 3, 5, 7, 9, 0, 0, 0, 0, 0};
        int[] b = {2, 4, 6, 8, 10};
        merge(a, 5, b, 5);
        int[] r = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for(int i=0; i<10; i++) {
            assertEquals(a[i], r[i]);
        }
    }

    public static boolean isValidSudoku(char[][] board) {
        if(board.length == 0 || board[0].length == 0) return true;
        if(board.length != board[0].length) return false;
        char[] temp = new char[board.length];

        //rows
        System.out.println("rows");
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<temp.length; j++) temp[j] = '.';
            for(int j=0; j<board.length; j++) {
                char v = board[i][j];
                if(v != '.') {
                    if(temp[v-'1'] != '.') return false;
                    temp[v-'1'] = v;
                }
            }
            for(int j=0; j<temp.length; j++) System.out.print(temp[j]+" ");
            System.out.println();
        }

        //cols
        System.out.println("cols");
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<temp.length; j++) temp[j] = '.';
            for(int j=0; j<board.length; j++) {
                char v = board[j][i];
                if(v != '.') {
                    if(temp[v-'1'] != '.') return false;
                    temp[v-'1'] = v;
                }
            }
            for(int j=0; j<temp.length; j++) System.out.print(temp[j]+" ");
            System.out.println();
        }

        //9 patch
        for(int c=0; c<3; c++) {
            for(int r=0; r<3; r++) {
                for(int i=0; i<temp.length; i++) temp[i] = '.';
                for(int i=0; i<temp.length; i++) {
                    char v = board[i/3 + c*3][i%3 + r*3];
                    if(v != '.') {
                        if(temp[v-'1'] != '.') return false;
                        temp[v-'1'] = v;
                    }
                }
            }
        }
        return true;
    }

    private static void testIsValidSudoku() {
        String[] input = new String[] {
            "..4...63.",
            ".........",
            "5......9.",
            "...56....",
            "4.3.....1",
            "...7.....",
            "...5.....",
            ".........",
            "........."};
        char[][] inputC = new char[input.length][];
        for(int i=0; i<input.length; i++) {
            inputC[i] = input[i].toCharArray();
        }
        assertEquals(isValidSudoku(inputC), false);

        input = new String[] {
            ".87654321",
            "2........",
            "3........",
            "4........",
            "5........",
            "6........",
            "7........",
            "8........",
            "9........"};
        inputC = new char[input.length][];
        for(int i=0; i<input.length; i++) {
            inputC[i] = input[i].toCharArray();
        }
        assertEquals(isValidSudoku(inputC), true);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode answer = null;
        ListNode tail = null;
        int carry = 0;
        ListNode p = l1, q = l2;
        for(;p != null && q != null;p = p.next, q = q.next) {
            int v = p.val + q.val + carry;
            ListNode n = new ListNode(v % 10);
            if(tail == null) {
                tail=n;
            } else {
                tail.next = n;
                tail = n;
            }
            carry = v / 10;
            if(answer == null) answer = tail;
        }
        ListNode o = (p == null)?q:p;
        while(o != null) {
            int v = o.val + carry;
            tail.next = new ListNode(v % 10);
            tail = tail.next;
            carry = v / 10;
            o = o.next;
        }
        if(carry != 0) {
            tail.next = new ListNode(carry);
        }
        return answer;
    }
    private static void testAddTwoNumbers() {
        //342 + 465 = 807
        ListNode a = new ListNode(2, new ListNode(4, new ListNode(3)));
        System.out.println(a);
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(4)));
        System.out.println(b);

        ListNode r = addTwoNumbers(a, b);
        System.out.println(r);

        a = new ListNode(2, new ListNode(4, new ListNode(3)));
        b = new ListNode(8);
        r = addTwoNumbers(a, b);
        System.out.println(r);

        a = new ListNode(5);
        b = new ListNode(5);
        r = addTwoNumbers(a, b);
        System.out.println(r);
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        if(numRows==0) return res;
        List<Integer> fr = new ArrayList<>();
        fr.add(1);
        res.add(fr);
        for(int i=1; i<numRows; i++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> last = res.get(res.size()-1);
            row.add(1);
            for(int j=1; j<last.size(); j++) {
                row.add(last.get(j-1)+last.get(j));
            }
            row.add(1);
            res.add(row);
        }
        return res;
    }
    private static void testGenerate() {
        List<List<Integer>> p = generate(5);
        for(List<Integer> row: p) {
            for(int i: row) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(nums[0], 1);
        for(int i=1; i<nums.length; i++) {
            int diff = target - nums[i];
            if(map.containsKey(diff)) {
                return new int[] {map.get(diff), i+1};
            }
            map.put(nums[i], i+1);
        }
        return null;
    }

    public static boolean isAnagram(String s, String t) {
        Map<Character, Integer> sm = getFrequency(s);
        Map<Character, Integer> tm = getFrequency(t);
        Set<Character> sk = sm.keySet();
        Set<Character> tk = tm.keySet();
        if(sk.size() != tk.size()) {
            System.out.println("size mismatch: "+sk.size() + " <> "+tk.size());
            return false;
        }
        
        for(Character c: sk) {
            if(!tm.containsKey(c)) {
                System.out.println("t does not have this char: " + c);
                return false;
            }
            // if(sm.get(c) != tm.get(c)) { //!!!!!!!!!
            if(! sm.get(c).equals(tm.get(c))) {
                System.out.println("count mismatch: "+sm.get(c) + " <> "+tm.get(c));
                return false;
            }
        }
        return true;
    }
    private static Map<Character, Integer> getFrequency(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if(map.containsKey(c)) {
                map.put(c, map.get(c)+1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }
    private static void testIsAnagram() {
        String s = "hhbywxfzydbppjxnbhezsxepfexkzofxyqdvcgdvgnjbvihgufvgtuxtpioxgjuwawkbaerbsirrktcjcesepcocaglbassivfbjhikynfsvlbtkawstrclbkpuldshfgfvwjawownulsggsxhhqglbhjlgltfrqyjntgldlgorgatnjrsywlyngxrcwfojkmydmjbzuquqnzbpwlcbdzgdicctpkgtbdjgywkyrkityvohjbuvmzdfyicyplmrpygdhaeqbuxnehfybsysnnmzbhprsyjmtpcrzeqqbirofzjtlsyofdyeffkvpuhzaflwjfhnsuyryetjuajjnjwvlvrhvpenaarnzoafztixjrisfzdlepcwhxudjpsiirtofymnovacjmpdjtethjqfwduekczlqhsfjgqesyoxcfooagrdhyvsmssbhsclnlblhobvhwtpyftolneozlhbtjagpgqnnapktyevdvjfwdnbwsbelweoflhyhifprieuvfcdkavqxkygjlaegqfmzndgxbsccjgpclxmlpstrqjtqyvlqcopnahqvnpvkjimfdlosvletmamqjvotqwhadutmfvlgldniixvdkmymfadckuaglgbuttymoqmzkaeqxugsrnfyxzcamwxujgzupefretsvbdweuwwcizjvhcowtmwgkdafcpzctpsjwdocgofivyshwdinbdhbxdfhjsrrsfchxkeqndgzauyprwfnrbmunanqnhmjhrufoinakwaciaoerioqffmipfqujfxwofqdyjbhagkyvmnxcwomgnmwlaodxgkgthnuctoozxrebjiynjwohtgukyneyofabpfdrxklopmxxiwjuxqpaazknscagfiaetmmwnwpzceglupqvlctywtpluoqbzdultcsudubqclbwlxyfboimfwriugfulbntvdwnxgiycxvennakpodorvpiknkridcumsovvfzikiydqewgjhacrkxddqpncirzlsynbjwutomcwphgggcsnfqxwxguokjvihoewffngivnkapaqjrsshqfbpfqhzihzndzeyznzyndmhfmypgwsqyernninzijwrirmbebsupvukfjddsjbebfedtdcufnubxqocqeqaincaadorteimkecpkdrjpprqtrllypfiktvcfadfotgjghhtkctgidwreyjulbtmtpwicbqalejtxsgjqepzeduvwvjbbxaapwvmjuweurhdbacxaygzdyitwcwlguknhqxdrfpfhstgnwmwagcblxkqjjwxfhmpunstfhkiffqchcohgcuktfplffqtwlwcsddkaeijajkdpmexteafezquyfonocuwuxrvigpcqbzmxtrvctovnvrobyjmjnqdhxqjxtltichttiuemvxixfeyqzuzxkwmyclbwutbvrgtefcdizjnkmlsvrujtiqhrwpnzkdtkwogypvpittpfhjtlrjtilubngkkjochucmdudfcpudjhiwaflmdfgsjqlwqxlgmdwouofjnytwxumjaailltmzrrfvqcawkzytomjaaafknfnaaylmqkvyeljnznomervgdhnqfhtpalziumhilupdsjpvzwjsgqtnovjneotcsfdtqqtfwjlhsbhumoyjnpuniqeszknvzitifqmiezbzvtzoswezulpfylopuecqcefueesjfhvyddsvozowqtyphlnuridygcmjhjtfoenwbgtramegbnabtooohogyoqfmskrpayzbrsidfbqextanotbkzopgdidswzxveqtojspsbwdqsrrspsyddgwntndfqfqeuvmrnyjvdrpszghetjnbpitorzonhihbzncpwvnwndriupumwihnpxskwsvjqzuetprscqnihpfebqobdezwohlubpyoulnkoicmtjyfsmvjltsxtvydxkajhvkcmhrkeuudrgxjezoeessffmheelwvotkjecsnwybtqutmkubtzbwshbacffkodyhgvdrqxwvtfwzsytriajkjjxcrvypyvmevvqpdknftdieqhsvgedaqlstybriqbeiczokoavsgizdjslkjzfqrtbxwydojvutxensaxrtapxatjrgdvecxocrisfznweidfvxddbmsmcmaostsrlvwvxokcecazxemwqksjnqhkegwcgqltrczpzyjopqmccgjpnfcxatmrsxvozdtcttlxptjhcwmjgcyltnrpicgblfjkljdjqvioxaliduasggcxviifzycctasdhfyetjljfgwjhjssryjaetfbrimuudxwivzpmpbylkikvgebelhouaconsqiyrjqtepusujnlpkkztlbrnnmllrebjamtsszsjyaljvqwklglhpoimqrspabinkpwopignvejbulvugpydnljwccorrvcbncioxtmvjrmvujzrqhfaphfhckmrapmopqaktegfmdnfrvmqzhngbwsijdocmhrgaxspclqxzptviiyhtrniflftpkalplcfoixqcckmvzeqozvbyobxgxehhrvezlkxdvloeqpmgjyqnoroztieaspauxwprtbmlawbxndadnqmknwbmnebiqkpjepdjudweevxtgclllpofytkpvvmlpllxbvkhcftqpzhrlaujzzforiafedqfrsxinxfnbkxjdntfpwquvluuxkukdovviqbtbiunyybkblbifsarcbojrjuityotqpwwoclttqcumithvhgajhlhmcoerwrmyjuunnbvqrghwniqatjnqrjgsnmdlcrwbnfrctsvuambecjkzzkuchvsuwusijrtctrcebafwlkpcoossaagiweaxauwyxhgchfdeqrzbnldmrzwbatqxelzoueeulmgfdxeqxqiaiwajqyihkvivbzhkjfhgutzvxrgmrqsxlctmrvnvybmlbufbmujpezuwjwfpwagltzicimdxyjscoglhbazkcamnchdhuwrqzbfnceikmolfjagmzhlbzyvdffmkeyfcjfovhogdkdszhomhqepyeadooiayofdtnlxwtkprlyozuimeinyiezyirpjfmhubdhmaoraogqytiihxgidzkfxdwqyadvrayajqilwddlyxmhgkwvcwsbqvxbluheovlmugyweciydchhoxlhvodmuvnaodxkelyuprppqvfupddzingyrxtpisrsuuyegruplcogidrrqghimxendmveudzemtwnmdrfxjiqiakglodvzxcdxzbtbobnvvoxufqnmdflgwghbclalddgmevnwzhohbhfhocuabtgvixvfbivligtiyznqmhsdvbfxmylyunerawhdhjlrbgvzljjgwbjhpjhgbmnvwnvjwtdtdrftstvrjjtnorryghqgsfybhncacxoqqhupvtjdnbdranemfywjhtgoxjnoqwsttvsoxymelwnomlmphmrxtnnbsbyelescyspuijmcxsevgorelseeyddymdpjbpehjdwsqrtihzhgwcgstlkallcdssiyzpvephkhsaamntlajcklwtekeupmwlsiojaplbrsnsvzqfuujkllzsiljzqmlvgibxssjjqiocgmvslmqgrsxtazfnbzwdkzninyuvmilmjoexygejggpkbrjeyfnfuctourngguxexoebgdrpbvjuxaqmibmoifsnbnhzlyssonuoqnhhxbkziiudkgtgucbvybmuflvdbdcfrggotqyfishaaqtgwfbdcntpxbtsmokrxnvrzaxfejuwqpgqfirokngeeyejngavztbdkdvtprmywohleaqqfqjnyyycppelbvwlkozdtwuubrjwkkmpavgemwmocvchoquogsjkebulbrgexqjnaiddbdvjlgvfntnkdgkmceomdkvpxtztfbwqqlwutlmvjjzkbulvqjxubuzegteusicmxqpzxlebvkndncxakvnnugqcbdukylzurgtjbcpcpsjbazlignnxemzzpkkhmbdiakeikioibotgyxzovgchtftfhcbznknjilmkudtzifiemrcwbyicspvnhxkzcjaxxcmddlesvixymggysbfnkqcgmsavjqynzehkeckesyddjgqexzmxepknwftptjdtauzohogaptcjdayexbsjxnsgacgztulhucmjqvrqnhuxxcyrawtznhuugkwylkrrvzihwuwhinuffmyyerikwiybcqaeaubmprvzosefwxruzrdqvhkdpifujykelbwumnfpuqksjszmzbuurnswgipwnfzlxplmodczmvxsvzyvdpllrnssiumfbivummsugxvxnfwdvgakstdnkxkjohcinmqbnyqtoahsakqluxrfxlvmhsgdpipgokharbgkrbowropwqaaqkyvhxatqrajmoixrycavnoisarbdqrlggbhliypuiyubulfazulsspfpzzpczxmxtrkjszttlchdpcuoiqnqibnkrereaeexhnqbqwadclbuejxhnmoarhtfrewbwgzzdtwjrkcwpplkezxufpvvdiirpsiulomzsefjnpkodqdyffwlzqvzfabnzwgyjfwyzeknsfyjagyirtlhpplhfcsvttdneoongivvdvxtjedjaakjobmkjnbedinvocafrvkqdrqdoufrcqvjroztpkympqbkuwelwarfjyndkyxkspksyosrqpgafamshptbxzbdfekqhgagkbvkgeinepzkcdnmfxnyqyjhjxntasxgoghmhkkpxvcisjkvgwilgubjxccmffklniynomznltcmfofpehsbfswyginakcvdzhkbrrccnerkzkfxzuvcizvanuxkiotvttqqnngopaewpaqvdhefzrrdymeodzyfufbosecnqkuvhxgprgnfbiyqdkqwwujumesjvxvrfvkknhhfhvegwmwrachpbzwrjsffjbhkkktpnzgtdgzgfbmlmymznnfgnhcftfpdagnbpzftewnzefuqkfyiwlmnyvtahanbigapagjqmrixuyhxhlyrwjoqzdvbhxxfyfbwfsfacwqujjfunypywngdbynsqxplfxtejbzeytcgsabjyhvaxdyxbcyskdfxhjuwmisnldbjliejeonzsurrpqqivhcujjwsfubukdglszfzwkjqxdybsynrkonufpvhlbirqubbgsgbegmpybtmfmjsklfqdzvjbqndqmqwbpczcbksrxyjcrlrlmxkipsffugjmfvmhbhckkmvpzqpgspqkzpnnhcplcmimnmnckuvhuwlkqxhgwqesqjhonuaewywzjgowhuzfujyyflnesowydymqcizllqinuzcxhpycbyskvufloktvnvisxjjimjksxmogvpqdxuzplvjkbplevzcovmlzpltuajpwcdqfwohkhumezhaugjkdhxhwvfwgoxqniwglnwqyhzcjrzholyauavdjqjrwstkwncmmxsngzmykrecmbfabixkwxtqtbjmbnplnalipmefvintguxpsgwvvmvwbpkytxmqejtqmnihfsbqrhzcjshotetczitvdifzlghcxjdswlbcnfdxfxcpmgmmcetagnhrutprhjypoiyolykxgprurkqyplxhgduaffbzhfekawtddjqvuxlalyvvjxzxxgeqrkicyzbdszlbplqujcjvqlbtxlaweqyyidjfytgcgqusgtorexawwebddumpqlhhizrbojmpwaixrsbbocgbjlwjfolifykxnousrhtirctyptvqtlwfuofyezstiwzdaygaucrbbzcluqykwbuduedvvxzgcwehykvggnrasqhlezaihzosgpaygrdecpdqofauicawicbgpjzeqkvytvcbhdyvqmypjkarkcrbciruzifapzkaisuvtlglvfnnwjocduysozqhkuvkppytoedykmbmsxzoqbkqepmivxxadpacmoodqvbmrkicxnllrhrtecsbviqycodvgvkevzudbktybzyffdpqafilhgtccaojtanoknvccpjcgtryxcofprbegxebtlfpgbahpyqbdlkeiqdayxaadxgcicdugnhcbmqebdtyclbmbjalmzbywbtovlclxuwqvrlmyptdtxnkijwxnlnnpsfstdhbqgjjwzrfxykdkunnaqlvyrgbamuvzarrdfdgsrclwwxvuvafnbhwzhlqkykuhhnuetjykmsxzpfnydvtpphalqmaxntnrxvyaswebdimlcckjtnhommgcsocodwaavazrjaiywuqybqwarcatjcypchbnkuwerbobgiirdooqsmqqsergkqmkfxsvufyyhziwmjkamflrsngrrsntfojjjbwyjbpvcrbihdvwhhnccdungtfvnytcnmvasnumxmyrkgyycptvhdwqhtiaehdfeencmxihtbhrzlxstdmwesfcysasacewvvyzkuoxxmyvsyztcvtyqqifdagubpacyiwgkkcinngdibpdywunhvierijzaqyitxsrsadoendrmykzzztfpxgzkcrdqvvskjsqywmumztmibazouprbubsdprsyalrmyizluuxnolvdstornbtvpsgvidptlscjrzylxjjiyefxcdzrmnrsoplqdyfedtvahndirebpqkwqnvzurxmzlfwuzxcdausblgucdxsxljsjwgnrdrbxnmltitjsabfqdbhayodbukydqmrgocprociejzvohnfrzzpjijappmsqoctbfjeiladtutyjxgzukqexeuwgbiqywebezrbzobfdasiwtfkmrqnlkgntrbaeidznyoyiztivqcufqpqfxqfhuikukmsmynglclshyyaloiswxqoyyqinlmkpgutivzhwenuvcepbwkvorqseykydpzrwgvaxhcazghvvsswurwnqmhbzkpumcihnguffddbgvgfeenukhsciynysyaejnvfytxavjozmtmpdgexhylacpgfqdnmmtqwqptjrrwreexcmgglxvkfzklmrfpvoxvhejjvihraszgdtbqcbabuvsbksxkylbisjrqieicqietplhnomeveqvozpniifutgwxnydxtwnrnnzwtunnywsszqgoyrweqsqjtopotozbdtyglfvjfdbrovfbatajrjgttzqqnjxivvywmjaeqxpmnaxnsfcfnbsmxdytbghnlnjfkcazqadakgazvewpqccdaabhpzhervkxathjizeuuhzivpdwulmmfdwbkkgxorddocvvhqygxjzqvsrkjkrwgyahdngcihvkmwhovauybwvapomiwscvjrnnaxpfyrshuupwrfbmugoxyzedxujteludcshfagnikwbztuzmdohxmmcdaduiesmnjvnztrsnbackpvkdfusmixjbdnuenppdpyottqmsfnxtqllnpxdfxjabntmrpwypcgvjtexxhfmbzfoiizjtdtfsodmqaprfudlemamovfdzqbkyhpeopyxbdjloqxmzvqybtfnffaoxdulyxlpehkyzgdhexgdjzxmbczwqsijkecthakunpogszzcutmcintimzmmfaozgdoyjcnwdcsklpawhggsarraefboxocnwlpxdruoyzdtvlagktupkoyswmnczymwhwinwooznlhoajtjuefvxdxbmzfbikuusqrutcfogvagidvuxxtgyshtovetdhzqijctgzenkshqvlibhkhwwnofbtyshrrhfcmsbwqyzagmlpoxubcfplzxdfheziweoghykcmubwgkcochqlkmidmfmzvbdkxoknjrjsyzfiemgtzplatkmhfuvenevobeyneyobkjuidbuvoyaqzrjacecuadmslooxbmzfmuawexswqupvawrqzvihhkfmsotolkdgmarpkkxnfndfkteqewywjzagukjiqhdjdnjxgfnmtrmshswaxizeuexrebctoehacerwfjjgodvicmgugnlngoofgotbztfjcsrwblvrtejthfmmzjjcrvezmczizazzpbdwytxmftfgzdqnslpezrnodslgplqkitigmhhhonadcxhdnexymvlprrshfrecizjnezdlwkupvkplnpvhvcsdilbscxgyxtuwqglkwwxljntjpzsjkrzxmoyswfahhgelyqjhvpmtgpmosmbmsqsfbkeedtdnakyvcumuxqovcnllusoxjufdfgdsjnwxsagtdkjljnfdchnmnlsnqaugqmnzzpzohlatgwhrxsnvvxxvahxwbadqrhraqmstcewlrsuftmrtcxusjkiyemosbebjfxqmvxnyhfdximdbaogpckgvbctkmcusigwbpulpocohlqxtpuqsemnkwcydjnvlrpwjyyligswnphrkbaytllzlmbnultpfcrtyxrvzlucshoxrlzujuwgqwssbgfgpoauqmzzfeppxxqcffangjjrkylvfirlvlhmdypetylrrxmewbuvcylzloskdrhwqaogfephqdyhymqkllwiakogsjvivcwtxcarfzyeuquvlnjwrkptchqjvlbzrilciwuajpmitxiljqeohbkunqyqksadaqkjikuxqznaaihtqeomxkasspyiquazvdqufjysgbqujoyhpnjcgbekgmkztovlkpmqlcdnpmmxyyxmaysxdlzxnpvwoyxnqtstgajdlizxyoiiildnjyafdavyynlozbapbvizlhlpppmiaygngtsevhivkgvmoekoevuswjiefzuwqnpfwwuwcooxudldfjifavbkkidnuqtidunubbabtfsltdwgqfxgoqqylmfyjxvxedoxqpmknbxaqkakvhmtgygniotdikllibsaixidziltnvuyyftypbhaguvizawxijuwiwukkmsaefthkvsdjvvgrbtpwmhwqdlcifjwmmexnajitvzhbhvnhybbmbgexdfdapldjkuwyigtqsgbylookxtwrqbcyzluydbqltcogsyzflztstcxsjipdxsmhoppoexahpaguwdmdtaogwfgkzvollmucphpzkanxazegyjdfkbxwshttzexvdqrymwzjlqphswivippgoasarylakkixysabrvwtaxswkagrlltqknpbhlglbrtycfgelazcoeaglathkfbshvbcxpephcvzfnhpslqhsuldrpnltqcncxdkiawdikeaoweadywevhzfuzxgpwyeqqyfzdmzpfbdenjtcxhbydpwzntbbvpxelxitqjlnvbevgkhdmrumildyuxwmdpyiwfnocgcwlqtnoogrbwpmycyozmccslikmskxdfolnpjrkvkfgbawawnxhtatwhfbenpxlckcbzjknlsfmgypvbrrpjzbwgrcruakqlgtkjjcrhbkdpcqhmpcjdlyoqigfqeimtlwfdsxojscfbdbqevzegkbymedbkfodgomldmhplzaaphenfpgimfsdkzooqvdptddfrneapnsqychajjnctzqsjwhmwanrboxnizydzklprckkgnrxydmjbzjswlyvvbsyzfsamrhzyfzaafdvmvglfhnhqxjfnomrvhikavprpvjdmsfxzbacydtjqcwfrrwfinucsyvhmwrbabfgqcvlbvixbpgumiukortiklgcysdnspslwicnpkcildcarfbxajpojcdwotuoivfdcebajgtvxqsmvvgxlkojfxkovxllvdcfwsssmgcrwuxopsnvrvfyneamxjtqegddlxdgmgrvhaijmsuqufnpuidlxlqmkajvdbdgamtutvkvjcijpmsvoqkhtxamzkzvfnuyjtzzkcfebpxsxktphsupzlxvrhuldzefpkjjahglzquroaavwwsmyzvocfumnxuemysqtpxxnjqqdarwzupxlldgqxbhrocmaqlfyeghedhrugxewggvpizuiemgnyvnmdymlvgtflbfgcnujpkuejymaheiginsvfxsveqhjhfndxspramoskwschxugkvlvdecunquniiivpqizwnyeipwgfomzctopbcwyhtvwjiretdqvgycxwmvzrftsrbdfbqbyhcqezbqarbwdcijrazroqojwrifpwjrtzyhkuhigouheybmrasdtodhddappfeylgraytlxetjophbqgknbcufttuiramawebesrzsbtfaucyqifacyemkfimdhwpzbwdakwjblsantzlcudyabmrklrrnrahosdxnwgwsygewylfltlxejjgzgeptgaknqxextlfcqvzhpxjvrobzkkqczwitykdmqardgcribfzxpzylfzkitocjhgvtdjxavkwtkuxffslvvfrkyllnnxitnboxkutezrepxkxxamrhjvmvaffctuwvzajhhjegigbbiwsscyqikgkwysnceshzjwpeuyqfrnfvfeetagpebaiybxgutlyrdsccrsguxzqvenakmcdhayzmuulkuiuryppecdxqnisszatfowerauhxcldoszqtduzkpwpbwmaxfbqppdiqtoslkouwvxvbaixhxjpttxwxzkqkjzqhcnvnbdhkcxlxnvjqaxlclurimmnolvixxodfjzbtyeapqqfypqbcbrvwdquttbancdgmuhuqbnyxfckgffzmkwuvfmszjnvnfrxogksluaxffcghqorbgxedbjvojvseoxhbugjdybhmrsiwkdgprfgopgccaqdcpfwiftcyjzljocinfjpkqngmugfjqgtcoagawhfkdkyrltbrwdjfazlvvxbecybyeaikfolankjusfjwsziwumrofycukfdmpaocphfuknwsehhxjhmtafugpwckkazjsytjbeihobkxrcqbznsdboiegdkagrcmrfqvyhhizzpnbtyvrzvosnqguhhghbhldtstvzxrbvufwsvijukhyafqfgmfftyjwmrkxeurjujlamcstwlugskqdnppaovkqzginpluhwxybopsrusapwbwhrgygpsfnghdkouwoiafokmjcnfrqaremqcrfsfzqxwalqfmpshmftihexccdovruodydkgmrjltvagsqswkwvorywcgelkvaikkpjbobyxaqyljtybpcdrnswdheyygismrsgjsirpgdczkegnwdlzibocehsbncivftqfxaroxizmzhyijqdvvyyypmoaiztjiwmhleibomoymhxmobyelnkskyabmqsjulekzwydfekqrpayicbbgzczacnpedksthppyetucsmweobsdcjhgdhgrymherrtijjtgqgsqjxidgxdcctgvzsognvzcntochxunfmeiscdstnbiwqlnsmfkosgnjfpxfffnjbmkffkiniisviynbhwenalbilganfnjycegfzzpoywdxmeyujoaedysdabgnhllfojxbwnxvqtlekxisukjwmelujtcznvonmmhcltsgdedgrmewpkxhfpkqnvjhjajagocvfquycohcufzcitivvqlkdjgvvoldpgirsllgowalrwtxqlhjavrpupdjfxztexjmvloojkpoekjunuyjicceixltgwwgqjmntrggfahtudejqpkvbfezycyacuvtrcfahnscilpxcnakdxmuogzxhvdhepkgwcfqnynjnpwwwwpjprcckzbpekxxwberwygobceyvalblpppuydnoqhloeoubptqtyqobwkimjevmmhhdhutwiarzmypuzunoesxxyuedebbaycbawrsoknugmlpwjjnajuoillolebrxsyhvyoqzxcufyikvfgncukmwcetgnkhsotriumgbmgmphcuqeqkfnrjipguhdrbahlyqwhtnuwnqcchtdgsgxeqevdycgwtiqofsnhvuhejkuipfabzvqjhdbbtuwvzbouancgkemccyyvrmtuubouakeuxydehpabvlbnqudwpylubyxrqgynkirvljpnzwgnuiwhevffztewzsysegndtmmssktdgjahcifcruicxqxpymcdwgipjnobhzrsrcqjggcqlkuycxiuopykunrotgmukqkhpvcjggdddrlduosffrizhglncpjjqsfjebbkzwamwoofajpubrgzqouwhdkdtuxqbhqvqnusbyxxeezyuhpatdsreaiinfkukxryycpghwsrkgsvguilifvtvzghzzmhsvyrgfkhxsirhncjxzkuiuyyaimatfenvjjhsrklfcnyorodbojnunndaqbgxofljrlayczzbnxurqiebpdwvevjndqiaphvxbzlnwzsqvrqlekqzxkliikevjcemhcvpwcrjrcxrhpxvfhelcynmuihcjkybzvktcaetsqqsfkxvwjyvetvbnjslfljvqzkagrkighgqrwaxmmsltgjnrfhfjgowkpztqtwjmewfowztsyvwlcyghyhsgvecyjvzwselwbpezhmmzgizbrqqcohyrzxcltabkddkmamwuckiqbicummmmkhwhzzgytotvlnvlryzoojawgxploclzloznnokmfxfjrgvfpkqfafqixdoxflrrmdddmjxgfgoznikzknmrczeqfwavbpytamblsxmbcjbpscsuraqhttmamxhaxgetiylrbhtbymajkgxckluctfwbwsgomhrdjyyzpechijmbwwdzxpuzmyvcpezynjcpjcpliuoaghpfowjvekehpchzkdncqubbvepovfvhlhvqzfbvqymgrhznpncmmynhhlsljjocdxblxbdhmbehfoyuuylurcocnfoqhynvoolcqikrmwbiocbwvpjskohvrolghchvjlydihynakxbndqgqcqsncgknfifwbhisdvrfvidsagrizmsakkdhngsohwtoloknapxzmyjbuojxniiwtkbfytzxootiqrdqnibapahrisjgmhnfnaqbqkoxsinddtsnfqympwjyrmdngimvzxtzsfeezgifacjsuymbrscwezdnnkuyvudcufthkntniodcsbfdmkigtnihqbagwbjlqpbhqhqgtprtwhunoiaekmtnhshvkizsvideqpqonfjuyqcgmiorbhwijonphxhxbrtydbfvftcxlixqxloarcpnnigrfywmaislxkqbgidkcwsnmouxrrwxabvrixfxtgzzbsrqleigeniulqyhvfleqlbqbicisidfrldrfilxejbpohapcyknbvbhobyonpwgpxaiaipjimuxdjgijoitysjrocgezdpsdqpbhtrycdchjfdbaxbmyyamulwlxkepslibgrotpvvcvlegpgsfucxczenkzljinwdalgzapletxvuynqxehembqcgayynsawsgknzxzucuxsgudbqlixqwinryiexkigsyzsrptysrseqbqdwgvyelybiyzbghubzpwmcqokxyohnkbkrlrpwnwydryyemrguinfsnrjnfhmghgvtblxeyuywvuefhklizztwhbrwresmamttahxoeijhhucpjhntakzexhfyedadebjfovkmaenllmburwjdnygmrqnxuqmaphcvbooopcszsghqgjbtwzslcocyhnetjchuaztyjkkxlvftkuazrvpuotfgbutzkscrclhicdeokgiijxdxhyxeiyvekbzwqprmwbgcvtsnsksgxxxtjawklhyjxsuihjbxgivqpzexqefgvydfcdrnhpitogxsyvmkefghvrbwqhguooaioyhxohmodlxyfqchzdyiftgplglkewwntxhlrncdblscuwvhtcffmfahmhcixmcmcbzxlujdaxoxwnqbcktqkqxhexcykctqytkjafrmmrtkizmewalyqlckwqdsarnzbphlwtemvfgtjrlrwwlyrkmbvxxhivgcwhzvkgxygkqhcdjudxeoxkajewjmztwfqetwfcbbmovacqrhyzdzigbzimdkxbrzpbgfugszzppryeisavsnjwevxiyrshggmdsscxzkwnazleulliadqomcwikdlezwdscaqhmeevobccyizsjzjfscabkzijxqutjifoeohnypzfnmmveilajppgzeuzkrpzajiisypojdzpftkxblgwzifkfzybhbqtiquwxbochrkdzlmlnouddlhjhzhlyuasycnelvydaklpwrirdrlzqolbdtnlfvigujpdcczucrripgmmmyeniybukcyrwldyfklhrnqxukfpspylvnujwarnomegpsaaorpacusmlebzkjxizddvmeqwsfwzzsfsszemdefcijlfhkfosxfrffgnddemlvqhbzqnkahoqwwwttymqynwpkxjmdreyzqefvcanexiutrtrdhkhigohjcrxaliltrfdbjbddjuhzhdhulwttpnysnqdpfwewldxkctixuxovetcwpufyjixxvqkmbqwtndwyyglmxalkajmvfyqrjqkzbcnjulmbszgvpcuazcsrsatpcxsoxdddkoxjkkytfarxxxjmdzogzsvekvtrneucakghbcazbpnitkvdaijhbnojbfwqdvjcipuwzbesrupebsnsyuauljiuiggttkuipuxfaadxobwspbfqzyvwfbpsnuwkapfmcibcbocqcgfnjfvkgazzgnfkxkefcubbrhmjhcqvknhvanfrbmvwgilgxjlolekvumikavnpfpxwdqumimcrmcoptcenmzmjpdtahotzflyqmogodjqvtfasenahuvotprpbwbpyqeksmysieqvppzumtfhnxgmuqvdpowrpyukfqpnprdfrywykmujrtzexzldifyclixfcxnrkduylkxsdjhkbvpuqoocurmdhttxtoaifdqbavrvfkllbngbnfzvxpwwtlgfvjspniltwecclncwewmggbpfjvivycwpclscujdelwxsjvcdfohnkznwzaavdzzhncrayokxbdxubdhggawezypqssdkxxmaymqoxrzwpqdwijksbkeoszovpkaxodnnmhoalznibyvocfdlqtsriipclhyuvqupczcxliuzrlfooivxxfhbwexysukyuzruvpkrlgaxckkgtcxirgxyooanpsbkceuvxxgusedngbalamcdohtxevmrlhsdnhgsxjksojwqajhjiknbuzbujhvrqnchjkloxwrxcikefvrawjxcsavtmyglxtkmejdbrphqzwjctormbpmpwfbjiaskyrzybmzzmpwtuzfqasaprslqhahvgejftyyrnimtvseqtrawgdhcfezeqrchoqpvwvidqqoaslfqpggmqqyykujaeeaqapeimuyvyzmarlldkorwdmjgcorqiesgoaqddnnseogedeoqcxovchxorccnlefrlvflvdtigxsmhtfjvcodxswfyjqusrajymvagfuickutoodzcvqjohwyqhslupgzwpintsndbwtfdnyklyahhhaqzhtyzttghzmldfysyruimflquixotblajaoderdpceuccurzfxkeutmfpqffzxnivvwpfclujcebywqcspiagfrkozuindzcnxefuknzmeafmqpsylkgoewhdgelxthgeshejtollmmtsedponkpijgonfmkccivugssewfhvwyqzycixxcpecunhurybmmtdduqnxacbsbmtytbebdovfmocnjqqxdqfxzuyiqsdqetpufldzskwgvodghfemakjedsfmasutbcyxsuntvpodacoyvbpbqbedwannppxtyptztjwdcchsitgqluneylwyaszipekkhbkcnefnzwawgfudeawkayrlaqrdswwevlrbjrqigfgjrlbfayelmiwsmbvdwbvkwuesujoycogvvwpipivovwudibwjanajowonreajokwfvcwsnsectaryshcrnlcdngqupksdhpulnvdicmniteeimortiokofhpfatuemgtuhgvifvyujqzzbojyagamjbfdazzvdloaqofgkmtxkcgtlxpvjcwdvehmcmhfrxkvatcvxkdlrikxyohzaxfamwsfkcrhmeypftzpeplkrtolgkowludrbzarxwuqjtpboqltvvcnftwooztbjijfkwrvtljeklapluojkgycmlrdqgmejzrihmghtmhymzjyqxwwgcnuaaxgpsgnbojcvrcmhevywxkbujhxlacnhulhkwaronipzpwotaohlfauopxyqksuaijejninpkqegbirhxgamxpnhevztjnwboftlvzkjepxyogqfahfbyxdnisympjgfqdwgvxgzcujcwnzaowescttkjxjmeqocjmczarjpoggmclyygefxakmgjgutdosqolpmlgxdepawapgrbsjizwepagtxbimwewcyprbfcczmednblmkevipjequjtwjifnvnwubptljnibdrzxogtrzbdnufzpkbqjjyzdxclofbpmdambenpwgngrenhrngescksznfxscjbikgdtloaqvrjaemipoanblulfiwtzxmhjvtteohxwegzozpqptqchehiwjaiibnslshnadwcwmbcqvusrpbcdbgdizrzrdqpqyiwgmrbtqvfqnghfmzalhpztzzijldncwsdshydkpipktxqgcyxpmqmfttlloxqunahvyygsucisycpefqifflaxshlqkpjxiaagjymviwqxggnjglbiqbmhbcardnhmrwyyabgbsekmeygniooqpdcwpomzuhyibigdgftdrwkouqfsugbxbbqhewwjwgixwpqmkisaqeztyuzsftgjzctbzbrtlxfwxscktafqxaqrnkeudbncahmythkroeotizqrqlnzenbceejohddmspxrlstkdvqoulxiscbbswcqyskoihxytmkincnphrmxzjypriaoxntttpxjwuzvrxumxcqfuuicxbdwqbxwvcpcrznctyhtyhmhuztldztqbimgdizuwhcxyvjqrzbmchsldvrzeftlhfuzxxkmxmpeljvlfcnfgkcxnfqfzemjidlzdrzugrscfrsfhgvscnualiotbvcquophhvldipnpwnaxasjdgrlctrvulvaarhwvuqbnzlsneiqwwhuvzjxeqqydosvgmqfnoeyhgfbwvqhgyodadzypvxcvvugnbcpokssausdntvuamaocbehzxykblqaefmzrsplwaijpanlfydpwwowpnvminkhxqfdxpfjuhlwmfloukjffcixmiojiisgwpowzctkmquemmamrjtezrgbgtiaoysjxdkmnqcdovrbxmkxsxyixotlyuophnxkunirjwtxknyleyhctbautkeolgfpfnwkxleilybroaqhxyprflixthvqeabjmkjlwvchdatpljgdgkjrrabiasrjuwxkrssqljyvxkgmutelcglenbzhvbhuzyljqbbztxalvgbcelyycfpvkcuznlvhqyngvqknvbeyjyecuhbdpmqyjycndszpmckagpcudjjtswsamfwbkqatkjdccemztxursafaxmgiqzudweqdgansrbdqybyfsesdrwlkrqexzblkbhcnmjyhgenleqargikktfexyhxlwnipifrpsmxwvjqbvqjwnzmudwfdzfavcirhphnncyenzleumytvlbkalzeueyyzklfygimrbbmwnkzyicubunnnygswarhnembogitbvgswvafjyvvclljmvmpvtidpduudtxugttmaentwwjhpyipcwlmcnhicdhspprwjoqfytssmtvoqdybjqrdsueizqeilcdesvfyipullslyuaczaxglermjkaffltbpxucrnnrcmbvvkxhgcjyydnjjkxnscimuvqmbwhjpxwdmaihavzddjjzmifwrtsqmmsfylarznkweozrjszetzxuautdblpuyyenoeuvpqptxvemcwhcbncomrhagkhpguwwzipjsvsgincmufejiksxxhevjeqiqayjkdsanxilhitcrygajvkywumlbsivdxhulfsbowpuwsnzmuyizoqkgcodnxdjusneahrmfdwcxmkqmoeztgpzeopdhhdjtnbjdovkhtgppmvqkkeckmkdesabvqymzoagtmquqounjshsczauwvrwsijalecimvqacsrdogxpyklhwjvhkwmmjaootdxzgipnfugalphxcqodfsubjgxsljrihwpsundmraszxvgpemawodxzpvgvyegvpwkkxvikysaqivkbbvrpwugefyaacwjvplzuxlrcurwcbrpbxluecycgdjjxjtmqbhajxhndvjurwbmnbsevcfdeqhiqgzehqhxjszlomqiukanghrvufcnjyhotknnwtyqgnhvwsaaybufrinnnpnkorrzhmiazmcgvvgpxvhinhciebpidxoecywegreqyfhvfpwqxeoujgcdizxvbsfthfrixkjxxoylqfcxgwkvxsmigegilfzahkgtuwnofobrwmikvuvvivvdknexknrzvuzpwwdiqrdutoyfnvgurdqjhtxjexombgjzkjdnktvxdarnoubombbmqcztgcianxurrniphvsptyhihwcdasrmnyjqpeyicnbefnoexamzfnyhsqbgkcylrguwrfxsedmvcjtnvdhsltzzzzynwfgnshwwgevptwaiaypvjjpdtzkkbbmegrtlzwrlgufrlkkfmewdbxojwfomckqnliyxsfarjmxqduiipmdaikixqkglckvghznqzubuixhgcpmegkoliiwiewpqhjsxaztohgysqxsorsvcvijxnidnxcywxewmvdqpzzmnqovjrjoxiuqyfcvvodzjovsasoczziqvcmwwblamedwydtbliblayudychlzbdbraochwmtsusgjyfhkmrhuvmqgvcpysncjabqcmfnpxzgkpoycdurjveuehxmuebcrkyyhgdmrhquxqvwvdljkmcvtdddhlsvxyoztqzqudivtnjxlxugcenefotwzusalkgwtmjqwrnbltvivxuafaonnzwpgfpropuxdhzjaxanullkbhziltfdbzxhnjxhqkjuieoatfxpaihthwuypkbjkmbwzaqclbgdvotleueyondkihaxyfkkwxfkrbxjjsbeuswaacvkbhakglrikbudmvvusnstljwzlwgxwftfbmkyvqpwrlhsewfazyinfayhwpbqlstrachzstsbdprbieqryqxehzrxerlacxxsflhnsjrcglgrqaljbxaosbbxpaecwqirixsdkzctvgjwbsflaiswzhhbwhbnuwytutvbojaopkydpckemdcwgtxlghsrawmwrgsbrxcamudnykmqtszcupejlgnkzuecqcjxookmipelkfedoobdflmixfayxpurjltkswzfwemlgkvniwtyhatpjnpwntvhgydhhimbgvkyksqktyobnyeeyiorjccqbuoatirwmaascjzlqcigpesyrmnzpzvlxxozfjkehnkjfyamsreynvxikftoqvwdxamufgynerhrsvogmnncytolnffhxhymgcacwfnfsoepuvtiskycjwvevekqdvqtsglgeyjlknlmhmrjprqrdqtuiihhnozeutrzlaixhpzsynswrcvfonwntgggqcuzoskvwkzbarksehtgjzcyojpeseurajrwihjbuxngqlpgwriuktjadtfkfhovpeyfbxulrjsipjngccjnprtlntmigreypnprbsxtzvseejpzpisfrthzbgvhkzlqgwqxphtcldhpfgnxiuqcdoumoaxzzegfkampwihysoobjlwaqorzdtsddcgntcmhhtnfazipfznkmrxfsmywvkzdxdnplmjxttusnrtapjvwrhygpcddzzcfrkpqlrdgxjzlwwjdnbswdyuhnumpqeshjwdvtxcfkomhezauwkobcbhymoilyahnyixjvxxbiwvgqhndwtbebxbtjodgwuuupkxhynipavbucuxwuinmvvkjbmyafjrnikbrgohzhamkcryaoeyblrxzfzhyrozwrzeukjteijpvkhfxtlhyxhmbmgvsxzxsxqgtsicdvgsvqrfjudzulzqgbeqrycmckjtkxlfsvqnavgltiruspqmrjtbcpqhtwhhdejfsojbzxtdqnjatcnvslxgtwynxqbosajcpjnkapcortjozugcidfrkaopdbrdygdsgaencggzpaawitnwunwkyynawlrruhuurmhmqmbczhhmqbpxiogspaorstzycqfzmxjrvjkxflwkmaisqwylnpwrqefpgtbqhcifxaifpyzxoewomhzomklbamwotoutscobqtsnwkbtykocfgcwbdzvebbaqhnlrhmpgiwgtvvfadlxknbmrzwvpwasnixeesocdacbtkwgbhbwiyptvxdhszzhzpxnvoxjyzmcoydfuqaipnaetrfuybahdhrrvqpchiqrtrjgzgivhsziydazaretytycllbaygbkydcafszqivzilekvkfcahliinfdmmiwrcwlpnrjueroiuilbvpgqqodtmntbhwbehhxsldwwunpukbjqbssfxrylirvifkkvzeewldgwdeeydwafhzwithjbjqgrqgkxuankcnluwhqxyyvjwsoyrbrndawblkofvncmurknbocyirpwugitowystesezzynouypfngcddzkcnlzpcbjnsndrqudwldnvblurcjuruwcixrxxqwjofwvclvmzcvfdzvhcwiekxlqhjebqripkdxjkcoxsxjdjltbajztgsxljrubosaaqyphnxggwqkstqzbzeyxeapkdacdbwkkssmurakczihgqzqwlucglcqrbefqjscxfkvmkommsznpwixmbygkjrtucezluxykuuvslaxugjpghffztfeulnxsfwwritgxumweruxccyhoczslxytqpcqoicuwwrfeeimzvqzfrjcqkfrhbirrzfdqzzlxtlqnsccomfkdyionbplaivwsvgpjpsheumedoqzvgtywiiajxrfmzekpesdfwppbbauxhvgxbkvquodnpwactxgtxhoirgvvzwhztbbekmtmkhsqkweuvomrnxiikxjhthnzqtzqyjrxudngevrwohbjmkcmiqzmuysjaklxvryijfeaehylohormiakqlkdtsevrcfmmjyfeegniznrvqrlnorxaclgsvsgbrgsndypjjcykuafnzadrselgnsxwzdfohvenvqzaymgbbxvseiwjbcgkrnmsavpehywckmsnyygrhlrqwjcenimuseabwvksjjksvmvynesekerjqfcbejsasgsogmmnuadzwbpfevpdhlksrzsannoetsaixrrtopwehanmximdnotepxcjgkzjzckfhyfhylqhoggryhmstxuisfijncfqjdhalfrdwukbknhysdjwfefeoupfqenntcumtxtfulqexmgdxbndjmjnwcsoslboqqlrhmcpkhqhxehxdcglxzuphsgvhcjqbcvtpzrqogmnsvpzxjpkxzujyiiddbgslrrtkatkmvuyclrqqnbstjifrprhurpayzzdftuhwrppmdupysocrxuaohhtnfvsalvwcebatacfgzhxxxmjlsuzwbigqmsgjwcwjvunnmumbccywjhaclzvhawkossphbxwolnrabkcwvzddpdeenqgwrsklpoyisyhtuoiraongquxuwhstnzwduxksfyinljaoefdylqmgnxsibtlfsvjdzgkrofirjmvatfjjlapmtwklpmrcdtbbfyvceygqgiwobvcuolpsouvzedjbkwremvozxjtueiydgfyadtthokdhesjljgslgiknjpogdjjohnfvfpdfxvgqomntaftpwqowcjwwimljjkczxabvayexwpulfxkzewnisubtolnsyqnvxncbpyemittfxnwvxfimklcqsezcvampkaztyvdrruivkqbhtcqvemilzejtpuuitvgwtbuibboziawyzxnngfckafztelbdwzkbmoiupltzhchyhjricmjinucwtcfpzkllifbugiukckrctstwpgnlrcgdcdxxvlyhxwxkcdbshdjqmgomppedzwfdaxyjjogmlldfvryqytfofyxiivoizbtbijiacmjegjyuvtfebelbofzraofjzpxadkkdfjbbizkfrrqpzchnsqjrcekbkrttghzfffmncpgusfzbhdvszknxhyxjerxkmvucwjtxbhsrqnturomdbzgnrqzxqbfuszkqytfwxrscxhthellqcfknpwknxlvzwyswqsoqqtcaerboebrnkqiqirehhmlopdwbxdklfqdroohpdcudwfkxvzpgjftxxdafkwdmipaleeoobhiockfblnisbcxbyybseurirmwkzoyxnvltrcqszxlwtzasmfxbnntolfuhpdjqlckyfjuehqssolhjdhvzxvpcnindlwkqcxfbevwpdjqfvgknbrvgtwucgfbyyxbrgsaxejdmhhfsuakbzmwhclfxqamgcckwippudhqdjxjmkkzandkkvnmrzormesbwrzsppvtcgxnmkasuboaonhqglqlrmpuujuirqbxsucpytgaqccmjvlqpswxngnnvxljjtnexujqxlbeaoqklwougytirxirzmoakqqbtgrwaieyjjdlcmrcflmlxwjzjrsfxgbywukqshkhqtqrtcjyelweilhqfhthzrsalsgaikksmidbwbzkajzzrifyjijygxouabluytfclynlugytrklwgpfelznnflvtjvfbzeqnhvyheenyfrohudotlmeyrapheseanbvilzuwdyqwyrdcvntnedtdurtjbdwewuebvcgclczuulzgvhxgshdokjvpdapyqzoipzedeqasuhvgfmwqnowjvujgyfkftdzwzezdrcwofvrgzrfcvvwqnqqgrizashwnxsuoacamwciegckbzyfgeddcrleoyhuhsfscczffbjvychbjzvxbovhdeeqqrmgfapryysazasjawdkqflbwhaojpublcmllkoysmwmuwnaxsohxyuaetiyjwldghxsnajxinrrjpwifxyidkafvklkqmstfcyatvaacgaqiscaktbbcorrngpymxzlkuygidntsabgwonqypytatotmynletsxsdkdrustfntanrxnwbnytdkcnmrrhrlijlpktsixlnpyipyzdtpoawiimwdryrvzhafgmcoqmrzgljucitxvycvabdqqfmhcgmjimwjnmpebbjovtobbxmrmisdkktagnvmncsknjxkbfygyauempnrlbtumilervyohojejrvlxipdbhljidwephfiahgnfybmeihgwuyzicedzwaanjekudcazfpgzaainjntcjayvkfdmuerkhdqctzzpypamyydnnbgyenkeyjrdyrtmzudsaholrvmevvdggnfkomugeisqxlozbpajxggexsjpbuaxnhvnxhyfjwnjvazgnuylgflziqecysuinefnjqfazflvkdcfocbblvynnwaldguxdxjeybqljphkgukllrugggddghthbtyeobqjdektkuckjfxcvupgvfbimgdcojjzaefddschxtxwcgzlavoshaagdksgkdzbdqoidvvxpwhgwdzntpinecakfzzvuqgadcdbnflimafozlbuwtmnuygvhbefsnraomnfhoryltcwqjsxpabapdyifcmbzgrwxsufasvwctzrgrzqavunswsqkglehqijahvbcsodgzdybqyldpffohrbymnaktlduvyadtzahwvaqsiivtfkfnpsswqafjweaijpxaurpbtpcbnpznmtemxgyrjazbvkbfaygllxgekxatojniuonkpgwalwhbpcoixvlhdsqbhnemljiqazeeczioguqpitjemesoaupxbrpulgtxhnhurzulibkubwjcjhoyamhvoabdubtzupljfjzazzouprotwqgqzcudwdgvgxybvchuvpkoqcdwxtkzdiscwjjmqqqimaqkduayezmfqltyrkfrlxaofxpqkbdkrhebbwcyufuvcemoupywjbsnbdcmbrzimwxifyxipdbchszlmjvknikbqeiirlatfviacrwfprboiwygcoezcaibfynqleymuhpfklsqjbecergqdjsheiinggecknrrtbpqeckufdfopgfdpsmswqcelyagxijqmxmkunsapeklkncdgiggoodaxjrqeijquecpfrszvtyhdgilpuswnmuuzxjlvajhwjsaxtdckwicadckalmzbllfvgbjzynzabudjmjwuxtqfyerdofpdypmjgzsqtanxoypzmnwgexnhzkvvkrashcklsxtsiprmridztubmecpiqcnzohxjyemsohcxdcurvhmrnlpaavauymqiidwvhngmzkixpzkdmpguovhfosfrsnzkvsskmdwsgyzhehieyhdmmdtrmhfucuxvisgjpqpvebapfezteenntatgtheihptecoobisxbluvmrelyclipzszwwwbltbwpdxtimhzdlodcwsrqinomlduavcejkvzupcbchvoixlgkawgnxbuojvgpoxenkwdsanzhcrpfxgzyltxsblnsuzwywapregusbbuiafkdzhkvbodpaywxwykbyeawlekogavlopsxedifxygbjsiddbwtzcfbjrreuxzckaiiyuqlgrglhkifhfepvnpgnlhgdkrkjbcyfesumavnvjgcobyplhzmynoycxlnimhzrutfdvcfepgxdutfgodioocmpwigzwxssnfvrafhxymcygpomtdxlkhvxelfomczclogmhdblacbwwgxrtsnrxvknszshxwczxufgjxvscpfncikzcjsauhujgiszmwipovijgmfxmgozmleruamvnnkrqstlyxrhbxtxhqaaaqiyhfhczjmnrzzsrodybhpjfvwfritoeucqvbfptwlelewkrscijwcwxibanexrfuiowmafgcmdcwsltnxbbufmstjvvheuqvflvctomhesghqkfkaejqeooquonbdzqqibhvrtqhzsggwzxixwvvmvxgwggcpdybllwvrvwhfcbyjbaywwvwhpaepjdbjqhcokumqubhmmvsmlvgvyzadgmymxvuphejyxinoqozfkyzehyefjlrnlmyxfufalnrjgzfqjkdeeqnmupxflfjzmotyoqaupirfdraddiciuycxwtmngzowraridqrlkhbfmwofznagckbpgkylnbdepmwfulaucxoyfvhxfwetnxujngvkhyfscrrkcctedcgcnxpwwolnojscmwnbhvnsmihwdcnpjdtvyvkhnwdoqltzfjkumndxhvospkccvanfhnhomnbitjzyztzisxdxtmvwcfxssceoblgihzveyrpwbwxtusrsdsxiwhjjytesjuxxziszhzyylqgkzsxtorzqrapmhtgjxfuvmmcajddyckndvnhaihxxvksnewcxqyasuqzuojebulrbzwegzltoukgcfwtwkbcaghjedwsmpbedxrgffzfubbxhdjgjpovpmadibyukijufgtctexlzxvrgctykkaxjotxskujxbbrehobjuqyldumzstuxbmtmfsskoyfvsnaatqcsvejcnhadbpnouqwjynvhbcifcgqahdbsmkufoxkllpeexoacakeidgzezqibwqumzyovmwmxwkfwnaiftyuysmbnjgjvwwxtgldgcqzlrqesmmwbnqthhhaitxdtsscapeqvqodnlxvitbgaekiyzbfrywhfwxkmmaoekpzycivvwbfmadfjwqhqspbpzcwsxieqjqshikodgbvaxyojvjnysfgyvhejfvshntiexzidxppbsxzdsnxizdeccgrqsbwfjgttzdhuvgywwezoyjvzgvewldnzenfaboahxruixursscxnoesjjqvpsqlrocmuugnmzaaxlfnztgcrlgupibqkjlexgimikehfaqzzahsbbcclrskoxbdpuhyplcrxfkircxrctkuslqthgfeqgauudkxadidhxdfgalhxvwaqqisvzfsbfavjpjnqzoigaibcryzmrjsmannlnnjagshwbkrcbwafntelwvphozdtqaeoczogzbpzcotzizrvbbxenxebkxiucushosjuiloumtsxapqohlitqyalezkppfrueqlqngkqxgsrefujxummdrhdpumqmbelkxwutiakgbktmkoyigzsvkaaoiovocmfftmjgflcbodzfokzejbhablshslqyitlzhvowawxsnwdugnzjhaegyfpjpbvbrqixphaqgwgeicpmsmfkpbksjjitzqkdsztvhnnltgnzdwxiwmeoonqiszminnnkiucflkzktiuweulmdyxfojqmsytflcgilppfvdsibhizixdzfwlvobhaczeyclwquwqsclbqlnwjyqiswxnyclrqgtqnnhysutngktihuezwwpttzzsztlxcaquipevjeauhyomotwaeuwlqsiosiyneisyvmrznubfhnqyknmqgxgrcnngrvethsxvkcneibjkznjhvwjpdzqfqikdiaoefoctreniuxoafefyunrnmslxvlrkdztmvrruvvhjxvgajhiguskqyrlanpuvvwnfslfoidfahlmixrdcnrbzspcjgnkshhewmuxzfayeaoehptxnoiitqqhhqlxltjolhrxlmsybjibwoqanmbsxipgyujanhabhqiinekrmidbmufbkxmitrimnqjotkhofviopngakxwkgsdofyyrjedeqxhmcacunthaqwofwqfghacgmcacpgpjcupssuoirvlqckachcgjiuoodebitochquevykmtxwwgtchhcjwygvwqujomrkefiofdqorlhyifevcrzvngpiikdrwoeaxyuwqgwpdoarxtfqosxqfcifohrmkxdcfeffiwfgtqgbghmwmfqajroqmmczsmgojxuqxxigbhokrfeohsfngnqijrqolquduvazybqcbrludtwvjrzljkeovhccorttounitokyfuhepbxycxfymftpnspvokksiqwuhueamdzmdjcnearluvwyrkctdxgnukikfwvehalhcoknymdsmtlsqnhlbldaoilhfcuuxhfnlzcjrorwczsygqwglxrhbikyrxvfrchcrcvwkxjppsrosyrmiflwcsqkljlmkwkgkgipvengfeplbmvarbyrxrsyvlhpvycahzvboooyygeaedhvfshlqepaoqzpmrkbmhqukuvabjprqoafngtxhlhqoxlqsnorzppoxythywpgisvgigmwgndfrpypgsoeqoipqkxngzkralxwfbepopntnxzxlzktspelmducselicysamkyksrpyrdvahiezspsncwgfpteomwmtfigloxreocgvekhnzrpluqaibubjvlxnsmyrfupejjtzcjgywiejzvysnegqyvuhlqamtzexnbjrqpxbsjfvzyukixgnrpqhrwmnlierxfzcayrmhksbvbgqwwmyvswlachenjeilupxuwmvjsujmzungvxloonkuahfammxixvjjaihpischntpelpkxytzvvczeelblciwfleyibomddrqadcqwmwundsrwlkbrkvysoixzlovfdvgrjsybvwycxrwjahngywxylunxpcuxfqhhfdpylruzgvbwbnbkiadsshffqqrbusrfpymiwvpeniboncusbrykrylqoqyudivhxpbicisndeaaizqprleuynttmfwkfzptttgxmuoljijnqujvzeznugqndwqfnbihdxeahngajlaeiabijwfhocrsjtddjemrguqherlgipzjhubylwdvtfnseixtaclgujbhnvyotpwjcbncqoqxsjzlocxbyhsigvmjxdlzfdzrfysbwdivxdtvggftdzpkgbhhpmaujfiijvgaxmsxlpslgncmkibwuqxtzxwfpixxsgwgdeniuyvkygbricywbpfwtdbeuqltewsuznveunanrgpmowetvxmwrwxcyurcfxvjgeihiekaumgxmjolamwqkbbdvjtxkrffspviqxrhzmzplftyjkbkhdadgjhjdzvwcunlsgwwrjcnefoxzedsangvsbvatipiegltwckssakrjmdkcqgxtbfdgkzchfrjhpckzglpymssrukdmfztdolyqsjwtibulbangbeyrxodutgwekcbofxqukrhtzpzbxbzjdcmkfuvczilshkjvsgtichfoyywamzzgavnvzsjbqoshmmdvpczyhhxwvnruniqzpmprqnnupsirkbmyhhybngnapnorgkjmcrlxboidivewegjvqsmqfigvjugxxyytobdlribgwqukifrhnfmaxyuulyytiwbhoybeacleipgwqzqueguxdsydxqafeogiyaxhwfqqvxnewkhybtnqjhhtzpbzlzswearioddtfslextracaojgmhcembywyutqxlgmtrurbwidscpuofrpvljrvbpofuysymjppmuaakfxfatuxxdyzmivkvnviavedbxgszxmcrzqwailkulmewglbwrwnegohpjubmjqnokifdxyonhkajacfcmoozhzhbvmvcyfaufaqhloqofqxrphcyztexbyfjpehmkwfyrwfiagyppydgdhvolcedexgwjplbykyttltwuqecqkxlfbhhoeksottnbypibgynvworgaimmfkpdbbqnuostbbbvsaltcbofpnykfbcpalgpfqrgrdcexryyzthlwlkwnxniilbzdncwjrrgjphtyidoounmjkmxlkgladcwvlkwcpvylmkwaujpoxeqsjkkhhfitjlwjroiyolnewlkznjcnkddanuctedubkyrhqowdqosvgpokwvzgcmfovdoihedaaskotpozonqxrqcxshohnfjqrndyfsnidfiyorimajamuqusqiwkojkhqyomumdttukmudfnektowljonvowlreautqecvxecqyctllcmlejxjphirbqragdvbxlhlocsbakkddxiavwqdlqjtdbdyzqevfhhsswzzhksmbtzkawfhofvyzvgdhvacgwyqwzbhufrievjukuakfqxddtxledbhgkominersmhvatcquhoesjnpvwwfmqlfwcozdlnsuigrajbjiotcroxreqsocfdecfoffvtnjvdnlzvsaizgrgbvdrbduvbnfdeamzlkpmbjsobpclghwbkwmfhiukpaqhvvpiqhnxqbdxhbcgwkaxojftmvakpwjwtodkrvvgnbmmrblbssknaekgppkmwyqtgtsdcazgfmeflrivwoqqtbgokpybeesehbhdtgpmmzxcsmrtvyqnlqfwnnfewlmdfexcexretqjlimeknpljfsozmnwqsicwpnlayogayxupsveeirmxlrbrnrqxrpiccjvybdgbprvbwshenrxzcjhnqpuekhukezwoourrmvztkrabbhqhfushpouucnqjdbyvgudvsfzpgbiaahoqsjopvpabtbrhgxycpawvvpsxuoqritbdwilmimpwwnxgtinfrlphtbyactjsszdzzarkwhdpjoztciuhaetxdwvpmxxmvnnlcdutkxblimtdlivivfjmohxzybqcbomwytsxlrdgrszstwxlwyxmkbahkiuvxjgxkdcwepocfebqggtrhiufgfwvxhmandcapdvpsknmraeklwyikxhdlhburdoevprpkhnoatwgrzuknnkyisqtscvsyghbxzpsccvywgzbzypfktorgzygoqyadkiunpxltkwzcsgdfkmqvwkkrndfmsvinfpncebnusffrmhbroebvtnzrnahmajzmkfcsjiwpzxxsfugwsurwslenezcmbznpmgksrcftwncodmsukbqytaogqfhmmormnvqtcrrjpezrgvxirjoxdphthjeiijtaznbcodedtejcmpapsjgbyunwjanlqvjaywyhthzpugfhlsovykdjkulihxjzzxundxrcghmodpfgfaobeunmotxyltrtngswspjbvidcjvffhdhgqghwvsacklzmczuhcdaywkgfozpohbyadgboueyuvmmwgqqlsefmbiljhyspqjohixpffpioblowevjqqvkuuutxvfpekfgtegcnrsqjyudkhwsxqdaqzbwwutqjkzmekfxyjuphxmpcweqidbgwjflbzxeavkgabqgcybanbtkgjiafvehjowhvumjanuvieysglxcebzlluilisewkolvmndotrtxulmeytajpgkcezkslwyigiouqhzhddfietwklfnmppmarxjjhyzqeibrsjrmwuexnjvejsdypuqiaxpmfzasctlspoxjnrhqcfkrapaeqkdmibmxvfxprewmctembrcruwmoghaeoofpkaexspcjxhvwrltfpiafnzuokmghdfivmvmfsfdjkwoqxejullyguvenjqqxathuujurifoxejzhbikirbyyqoulfrwcscioselprnrsfmzkwksscpelfibvqnxkdaygzevstkeyinysefeeqhpnjlwddgxbcotknmbofkitfiivwsnqvcsraqcmjotwipmatoofhwnlzdlvldcjhlokmxnhertbmbslxtzzksrbbikllzxfgdpjrwdtcojrcovhgqapramzfclelbowyznwerbmfmtnwloziditwtsttmumjohdmiefnnckclqmohbctuarclkqcbtpiynchfhccsvvxatycnokakiymrgezdpwtqltadkrfbljxdidmskbzunpaniaqdadfmrszglgeqniugnrsxlajynvzlituobfxywrlykqawjidrytjpipitohzsjtrwaamhhpzlgryzjuyjvjzpdvmalubokewlreanmoeijhvsaktneqgfkxjevtylkmvdbbqxdbmbmuszqiabskxczwoylbihhulolqrjoylgqvyfjltptpukpwoohxfylaeszmbxgrnfrzshudlzynmsrltyksbwnivkrrjymfhsumbzatjjdsuqnvrmyjdviarcqjpwcfduyygftxyzqwhglumciojzjyytfucotevyknpjpyjwmhycjxwrcjcqackaowlugmshnrkljdezlpdncezxuxopejxmxkmxwxkspzesrhaisewwapmstgrjhpxhbjisbbnsrbotlznphypmjinkkzoeglefoculeroggpgurjtdwiazwcpinsfivbqeczrjakefwzbhgefikbkdlivleegebmjehigchrcmgapmurpvaurvuagxjvybsnsaeigdchvttwjykshhmupsjnhzacmdcomvtwcxznmzzzrmmxsfgnoqvhapiubwsxuwionngxdbhtzigmzhbcnivkulvnulqbcpvkqyouyplkfegrlupbyvbewzvszskaadqrrvwqvrglmyhoexdwbzkahugdnjetqtaotdhlvpavtyvjyqibliacvfxnxzlyasjfkzclhkeraajemdaiwussptbenbgeqpcilnwjsvmigxbpreqvscomgobypmkbirynxzhpuhdztxkuodziwmgwzwvokbyghcojkbipaumadnsselkucdnxdlpsfpigvrzaebscbjkgeylysikaisjdxcnrjahlufauldyxcfofcbqlnrqonvlaghqmzzwqusvvmsflpngpishkzfeslmycibjgylfhzmymnhajipyaigppuuispbigowbpxnwjamdlnfhevvnognzjuddeptrmwfuhpjuyuyvdmhanmcbqeluxzpwhskjtlqacnyaybtfzudqwarxawoeeqezwcasxggqrwevhrbcipdwmdsykryuoedwltgxgcwfyugybjezobzbikeplzaqrsoftakdglahglbcrdwqloybaewmtmtpjddiqwnkojopqgsxyjzqlokkarbxhmyhjirbjxuqkzbxqwzwpjrtegfszebdvooonyhoflyraivhcpzzcgzdeyfydrxvufklglwuekeskwzmpformxejsrojsyiniwzfavpoylwnojivzykkhqbzjluffwyevnibcoxjxgnnfoutdtxgqmjjoegmitdddglrkvaotkyedbigjanqvnhmntefnmugvxnmicthxxfhpebqsmbcoogezzuveufsfpsrgczyvrgpdtdwmvvgsmjzbqjvpnpwvzrqssdcdgbazxuzcqlygjjihqsfpzkqcbhviwpmeebqsovgmahuafisilvseqzreanodzawsqgocqccldjqhypmjubuxlrziqygjhuegpojfbqxokomapdfykrxnhamhfygukmobokismzvhzmpcxrgcsbruxzxsqjoswtlnmcjscnjzqtnhtbyorjuzsclarffgcfluinwzhrfkfykhcfmrlyjfxbfknbrwqbbevyyprhylhyfaghzsiyvaijcvexkbymrpjzqtvbzjposwpldeiovepskqfvjxqddtzudlfxvjdtxcczxssysjoodyjkksoaupjelpgxmwjtmcbzvzwitlfctkwbdmrloyankzxlthskkppgcympwypyvmnchziqzgqkfidjsmqesfanfmnakcjomiqidwkbffpmurqlpstxnxbuyjuhtgozbmnlfjkdenqamnicdvilyeqhpcovccwtnyofxfydhturdgukpesiajxxtyvgbmpahvdnnckucevlqtxzfhwqudktixwmzwzglwgvgrnyfwrurxebntbapcqvifpnralumuuhybelzoijzefezbacyrzexwkpgtgcglbfvmxqlddtrmejptuokqegkmzdgjyukprkvttavuzjrlaqugtstzchjvzzujihkewwrhbxxydzxzsvnxmftwzfrmoydrqpgzflqvubywfsyzwisamaavvypwolebhygjnunxejjodtvgpascdfncsiaqbtdewkhqqotwugkiytwxcplkspfreeloucuqzrmvusvgevuygfipqdgjuuemdqslkmqpsgeisijewaeugrerjxyauxxzvpbgkrsgywhibjdypynwvzqnmvqqyosfqbrzbsboaebtmqvnwyfsrizkltfsjxlbioqyvpdgchgwkdazduhdjsozrgibcknptmdnebkzioxrokmsgipnnodrbytsghlabxqcjtiyxczbdcaxrffumbwcrvkdqetebwzwhjyfilgvkqbljxeojxxgmtqseijrcsumyycvxqntvduttdcqjxocifrezqakognrescwjirvhzbcmzkteidybrdjjreqclrublkedzixhkuubaxazqkuxfvrirehynbbntffuuhsswvavsptaeuxpxrmtelpkrwcnznwlbgciekpzrjezaghmgbghbsnvmuiqlmfjqebgwenkxcsmxmlliowkoyphawispvcmbqwpdcuachqlabdrbuedatldxlkgpgnppbirhuihedrzdaojxjfprcyiqdznsqvwngkssyvpjbgksuewelhifipfnsgevqsgyzzyahjnddnfsilnspvktwurjnxgtomwptvuballwabhezctefpeuachscjqzeefurzbkembcrpdmdfakagopbxvwqeerqrrslmtoeanwukgbpngbhyjeyyufurczsazwaapfqyufpuvoglafnxcaynobpmlcebragodovgyexohlxcgnbqpqtcedyhbohmtnxzdbduueyuxkjrxcmupnzfpaaxgkglmpsowscxtlwjbjwfosrzowcavpgtmerkhipwhajkieesvaahetbsnxaouthebejtqfwkwsbyqdixoctqwsswtdeuohwhbdiuymzkjyxxayrgeezarqknnmgqfiprltzymcypsbvumepcfluyhadtcwsmmiukczijsicinsqhyrcirzshhdqawujxnpyvpolgdzlviejepgbfyzbjrufsrlhmhwmavgfzynvorgrupdgtsbebapopjldjwiktrrkungjeucbogxrukddkgxypcwwflbsvzyjylrkvjzmawlvmdupeerhkkeqotudppbtcayuxqzhocvacjkoblitohlckqucatujghmtoyevhmwrogopfvvdnjyezjxiojzcemljzqypwyhkllfvomxwoipdctkhmednrcleblfjbimvwyfupsrgbgusyintiidmvdwssddvzmefewyavvulxwbuubkcrrzxoakwtyiuerbhcecwdqepyzrhxpwjmaviijqewmfnmsfyoiskzfdhaqzpowxonbvqszsstpdokzvcqawdqqtrfuhxdynbxmwhymyqueltvscnomupblgpsrvmyikikpglvuqhwrbdwstxiqszxvumrejgbcrlkdmerkyiblpmoiwkuhaynxyhtkgkrochxpzcbnyygmqmzjkwykgtsejntacgzosuhbayfacpgdhwvnusjlomqmgwrzmyhvsnvbssfddrghxppsquqzelhhgfkbypkgiubwjryawvxefwvasqphcimjjxzistsblnxcpmbwpicrdfxnntgxaegrywjyehzyevdfogienxjtzvxrdyvspqkckswmuylwqyckqarcyahgvunjhqdshdnyggzyppjxjpflcfweeynmhafmjtymegintljhnvswmvxvqdkunozeesxvnxqfcaypdktloraufrxkecepspqhxropctngrefhwtaifudprcwsujaiosiuoomzgttgpdzcnwmubscougqzxszoenlbuzuvoxdzvoljdzvpssrlfdpuxietcnlvkwopquxlgmysoaejtcoromwvkbhparhvkvlydjviyiishwferqhvozegoijtcersphiijqspkhcoizkqidhcjjiffggntpiugggayydclfvgjmlazwuavafbvqabcbmzdhgwnnpvtgtjgyvdzerwstyijwgrqdfronvbrnhzopxxowsjmyghuoomwffzyygebduttyfmrpqabonuhcrlmbcmoqqkcsqsasnzhmgrpoauthusqkwfrmpzymtqjregtjebwshzlvvjedgvbjxaqmbmwopzoeocljxjbrhhvxhibdarmyhquirwmjvkxrydqakduzgljyyuzvsttuyxodfogollahcolhzphveugitvwdbdesyieorbddkrgngpowrlnhxkbrafuzyeluxjuhdhmhctqsjrazknztgiagizyormfhcdeykctwiescqqsrnbxzoshviabffuldzavthtomekqiymhkycscswvwcohygrarojeayrhwnobtxlbdcctobveawfzoukqwhndnjnqxuoqhttktdyfqldpkhponacuizkjyzmtgyzolwcyshdilvjozedwgsvszojhkvtwwlbadtndmwajlztpgwujvaikhnjsmrhnltwjfehcyrnchnyrlyomzgnqkoxblgoaowmypubllodovnejqtvnxeblajdulnupdisxkimpvoofqyrrypnlvurifmiiwwcsfqzfyirgizfnrylcfiktzvukxirbkmksmlkzuqiadudfeheybaavoonqkxhwkuqhuatlmknqjpsswrnnzqtahsyhnfokcqkwyivyinmoulpxxrjdqqouwblvaomdhrecnpgrfdzkqfxwcoijmnezebavanuyfccpcgrycgqzkhstchawmfbxektdsvlskajcjwvmhotvblsvdwqbcsqjyixqlctzilewkgdoeskwqvfculaktkegpbiyuoampsnxugdejqzeygnrbmkejrtrxlrjjjrbvskgqstvrntfvmghnzemxxzzxbviosdulxhseqvkaajjbqulnkfrnurphevzygmwtoeabesserlbjtjtjxpgsvlzmjyhuswgdddgmvryovsfspatkrgbebhhwpsmaiovoqoixerhubpxmzrlptvbcbopwwiyltvonizuldijukjdahablxdojlsowhxjnelwquvtrcgqoqqsmvowtwlxkcuyyifpkgrkilsbbftvfqmyeurtyscmywdordrsmhfofepnsqhpeawonnluzwuzfjmqmoxjquxgbdrtemhmtajrmphreaijxwqlnxrccfbtcgrfeeoyrxkevnamqtycvqzthqwbdwmdbzjmznzgkbetnjuiuahmfeofhwrojgmalonlbvfsjzvpatgjmohpatflwhaauwuwseczpehmchjvbegxlkfealnzojiwnibxzqxjrlvnfvmxndiunugpvmatxdmutmumcjublaroqcjtkvkwroihaqqpjcrawvhhknktbzioglwosguwcambmwijttadfdmwueiechcwfnzismkcdijhilfzrhbzasxvzhhmatinrsztrljyotilvpprxifpwzaxhfptldttgwnindrsrksddvggurbnamuhplexfhlmnttzufwenhxrkkndkzidmgyghwbuzwrnbjgokigeaqoafwimapgynuciseuzulounltodlmiiaioijijqkhebixqnxpbfvbzafnexqabdpqmnayroxbscayzcimoipneavaxsnkdiewwxdcxiroqgqsbfswjhxdlwnkgjmrjxlacrvkodxsnigdyjzyfxeuudejytmcnttlwgomwnqkaoehmpwkygnrmhxfisgsciccyuiujmymdocvygxviokxgocmuwfrhcunnuvqlzworgjkjqweexauxqkvdbbwniwphdwaiafedshcxffohhzfzqnxnauhsjphcolulerscffwtqyxhllkbtrhqlxbbkhmpgetqvrppocbtjkwqtvilnsfpvedvsaduppihteuvkikgwacteprdjxahtzrpxvtmgusqgciinpnllrimimkomwdzxhojeeuadittrlhwprsdvaoivqeqwurabccpfnzidvdxtzpigxcamxypwvioekshrayxqkkrvnrqkbzzlvvsmfbbzagvxecjjyvmbbnmcrnfcypybfyltfzjbsgludvinfucryyccolajvwgplkvslbwflxusmsbyoejlpolfuyfvfrpzyeizmewxypwcaorpfjhdiuoumjrhqoohwqokiqjrichnbwyfrtrqzpkxzzojraednthnihowrzvligsuoejeumqugzsqmvogtqxmmqjmdpzwnqzylnyhqenzfiwkcknmapefeplzherspqtfjswuwzklwzfimxnhclvgwxrkueoqgluxdvpsblqfwqlyrystpbomllwklksinipydinvhymtndiurzbkhcgeasuwblqrdazpwrqjljgkeehznneartjkydlsmcwwmzeijsqdcpgazydxarhzpcmosyiuvedcvokjpohturmdujrqtxvytujfnnwxbeneiktsmuugrrwomnvoyufkachsayeojznrqecjldofvzktkjoxennhbhvronewtmjclptaycfmkcsqduopkshjehldmscfcmotsovtwwmnhfqenxdvhccpoalvrtovajxegdgsedvvczxkuyokzmjmzjygjemqsuvrhsuyagysmpwgphoiardutsjhlwqtgbhhigeyadwkpmlximjxxhsoxecykqcvxfsbritskmaqoynehoiywrpxljcsybkhsypxcxridwmiltgwycpbiozkevghyiurxfncxhcliluuthxbtyyjypdkronuhekfmsewodxmlghpfkcppssrsnhgkhtypohbwouxpgbyubvjshialdfhornrzorgmwmgnbcpofjyuhrkclpieybolykdqvdokmsqlgkbkirfidbonvyxillffstgzhezmavmfyqqjirnutxopvaupzlpqpxnnewfebyuduvuixclrqnddlmumjcagzgikmwhivbwkbkecoffilgnueefodgmghgukavxhbxthrudeaxwyyioncqxzhlpbslppdymlziplmcbmqijhtgnmdbozyglpthjefekayezohjbzlfiwgzvxgmzldmephmowaaprescuzudkyykeptkupfwjxazvbdvgiccuaqpzclvutgrwtokxekmqbxybithtvqcnrrhtowqqxsjebbpzkkruqumgraxqxtlemjkitialdlvbnruhqxjjzkecsfwaiznjcdaotumwrrbexcdffppwfngsehbvhipxgbgwzwyanlzdblubxaboezhfbmjcdgsicsyvbctxskxoklelgponklvxllvqmrxedisgzxymsctztmkjtxyxemltptistvawpwlbzrhsxehqnhwsypvxxiguvpdbxbtjtqhrjscsibrcatpnrkdtrhlbdyyahyxmentxdogqovypabzpzrkfivchbywkqbwmcfqkyexnqcjvrojjzgtzbsrlahhfgbfurcrieasoncsfjqbtxkgdwttaspsrmpvdgpqylpfgwvexdyqnaowvppfskvglcecxgruruarwgnmhdvmowepkygvizcdikmgkrkoqgerryuebomcnntewisijbjsujbmxkaepriswzaiztgicejigahnmajuoxbqqwjmqmoqyzavguqebjgapouglcthdwabnoceihncfedqjdkqerohtexrohqjztbsprzuutjkuwibhwqbmeevnjreonsuceiglrilfhqphpxokrgoqfmedwmxkrqafpkomacukrdgqlfglqofutmgsitqdplygwvtruoodkogqhphvpyznuiozyfxyerzurqebuuijreliaptgsflikleyfanthoigmdpwzyssjiwljgevuspimqrwnwkxhyblgrtxsffaxcoqoeboawuoqcjeajgfsbjvjjhsaaalbsqisetrclkaovlkrhkihzdrqcjozvzbyqkjphahbhpshapkblzgteocpaxzkkogcyyuyibinavddszfhoezwdntlvkcstvxwgktqgrfyluhhydvzdqzomfyvbfwdhbmstabvqhnfeiiuyxrmyyzdkadwxyypwtibksslreyumrjvjezqstjdwvjixztnwbbtsmkvmzikdrtsxvwyqqrcwfgpaelrdrvclkfrcnqsfptwljmnveqxrvtrgwocfxzdzeeyufvxfvrbeqxzehezuzpsovgtkzwisdpjudnszfzwfgddiiymspqwmrsngqwjyzvdxznebgcpdcvwfjtqebmgqletsibebsbzorpxjbithuqttyqwhsnjjtxgudepuhetzikggxerykhsxbprkjwebqxcykawjmdpdkqcdkedzfjtmvaunjnnwyjnljesvbyqmfflezpecbhmyscrnpebdyqbnvzxzmlvdgdwimragppzugeyxnuyzguzaxympuzgcshzuozpqaeocmrwioigggvvymffkmspflxzxijysjyjquzmzdvdxicpstasgagnlfdzassseaedvlbwcbqpzvwtonoeztxosftkscmfqwtthnqkzuizvotvsmuzrtatoakiidekvfhfroglfxpkcdzblzphsnvlqtakzespuaivaypnhczetxwppkdnsxdpxzjxgtxlzybbleelbipmubijnemkhggkrlogixkckzmagvitkoqlijyhhzjgpebnueqdltbglqxgkqlmbzjbcvjvfybmzbdmaykqgjdmehpqeqpbkwayozzrdxfsulicfurgyrmhqlolkikffboslqegbthewhbfhesbnukuedkrsvzwwzouyvoacthjzwdnadtwngredcifkpawgvwmabciuhlwxgcurtcfekpnarxpapyyllqwxerfpnupiwqkegeuatwzededwtgdytvekgpplcbuabafxklnpsmlzywonzozxswvgvnaugyebzpoglqdulcjnfcabizaxhezwjyivlafjtqciucezvlyjbmrvnseuwpmofbkdviylcuwwfxtjoerjifrsckktzuscdkjqthqpsnoerqidpolaxvagyqyxkaehwyeensvdrrpiavqzszqecfgrcsttqpfiuvnidlsfkccuxyivembwutvltambldwdspjzgpxmdgjhlizahpnwgmwuctpqxuasmjxwyfrffberrqwzhdgjwjygintpevfvxsgafkuihcaqcnefzyfnhospoqspfwpzqmrkuvhyivphuuvwytxcrgsurklmwrveiigxaqosqgmvkrpztmolulvttxvyphschzncgtxkbjagzrfwyipbenddmhynzgzysspghsxsqplmtfisuzwuiiwbriobqrqcweeggrjxjwylybbqmjdvnmxmivjbojreylypuclbakjyytkqycllhmpnpfhnhmvmsojtnsvlnkrhclndxjqatdeorvqdlimgkjbbgojiccavljywmeeoczvpeqqkwikaqnvvmrjisvagornwbyvtwfteqskxwlmkxtynxsilyhhrolnufdxrhntahecmaekiqmnglppikimqtbrelvgudgshtrgndksorexzydayzzgqslvwwseecsrjxpinwjndpbglvyliycnnppiyrpvfrbdycfmldvlgfvdkzsqroswmzhdywvmtqnudkyzerifvhwfixcqitvesbgfkmbxtlswkzwywzlqrafzwgdmnikjonwdmmcyptwnvcswoncvpolmxntwnldncwzbyuyjfzwbtyvpocnhsrjxbduyqvjqpaofzabyeumfffufqkzblyxvpditdbmrbrwifogzyeyzlwklbelhurvbowxkvjhhehccsehpclydavauwnaroxzvhtnxsrrcjrhqnxobmtrdgoyxqvrbtdrgubekqwqusowlipeaajkggmqpgqqiexxvndspjdokjfssgkkpyazjqsaqdywdacminyesucyjmernivfnywmwcnguibivfavwnkcgoovnbmzkfeujfkkrcjjaxolinftfeeogfesenwafwdauullsnrbrkqvqwrnerccvsxyeedsqlzzkjtpgdftxckmdblklmucmurjcrfiqiqmdvxmrhulxbytcyynnchvsqgvtafchjwwjtbgxvfoxhtkkmnthesjblbcoggzimmrgcheooxxirwrurlahuqiubqwijjnyribwltlmzbhptmwtzhebkcjpmcmwmddjsvqrpqkamcwjjbyvksapsacfnlweceufwwiriytypdgyzucstqtadpkuksdphzdqivbcnoxkxneacijykedmjjyledhvctzewoamkekxlbjlmdtmshbvvchnbxrgaqengctxbxbexegvbhtyqrwjtibwoposumiiltvaebxauriidikppqdvkbbpcwcseixhllcjgclymvmydschbanpbpdhwotdmsubzfftttfrgzropcceflugulfpffklnhfodonminxpuauwwvojrdiaqqvdwpevplbgmwyvvdvjmnkpzgfvuixxcdwrghkagknggqmcychyweltchmdbugnmeielzyqbbtwjtzhrhqafnwknhrbfrcksvfrowqcsgguiwvdfqlpthhkhnybsdtnohjprbaamyiupwtdhgssqaxxmggvelamcpaafsqysnfgniasoodoeafsjdjghpbfrbkrcxiswekvdlrnsdwjaffmiqqrftxrfuzprqxirxpznagcinyitqyltkauqthseygbtookblpqratcyaewhcyfxfboihvvccfatjmkojpmqxgsivrconqcwhkvodkzdxdkbmghprckosmikrbrugibddrtggajmsurqzfttuttlyfxkvpsbiloiaglrwqertuaytqelldutjfyfgqnrckzdrpijrykdbuicrfrcaoahvvmkvwszoqcgnacorembrzkaixpuysjkgkvaudjmvmzwrtqelrdenmffceewrhybxhbyavplaxlmnkrpeqvbnprbgyinvqlxmwbakgrsjbspzlakrdkgilsaoqqwcabwotljovaqsqtnzwrbnbonhmzldbnwtiudlhvgangnkdvqhpctvbvlqgavvnniwmahdaofsrlmqrxsiurilaspcrjpqkxhbztyzulmilbfmocgyodjipexiyqusnorqttpthbnovctbormmtnflouwcwldwqhllsegauwrxujnjqpgncsmvkjbcjyjfkbjwolnuvokiobagemannsbnwshmnuggvhqnqwfqnxrnaxuwjnoueikhizzdclvhmronuvqztszbuhzbcbqnoalfffoprcnopuydrxqpeakzgumbijcjyqdkrwqlbeurrhwpkbtdmzgsjnwkvubdvejirnsqywvynlccppndwlrtbtgtsrvcxtimdfrydadzurnwstcwryrwxhhtnwdzegwuqcpmuxwxbcygubywqyggxjcgohnhjmthzunfioetkovgustwluldrcbijibdlpvrttkfhzmefbvopcibwzeefvefmkgwypmvccpfijamrnqnjgraoyszthyzwckbgvlibkmlcjpurivqchhibsmszislylwvlwiixykomtxnbdqxqkbplyrzqmqcwhutwbkzljpufcgumrynhmxsaxvifgayvxjhwcucdbjciodfkxvuhwarvxjjuikuzajitiqxyomsuzjngeghworwolsfrddbyppbkeedkrebjioyvqcjbpfpgojquskkrylkmhwqgytuiyjdujkzanjziwwrlbhnshlqwjjcrwgyhqolcukoxfkkmgicaeglhkmpsuyceybgpcsnbtrvmklizvdcdekrctaepmuwffdxadyqddiwqknvhflvpxzpztvszhqxyzpubiyfabemgsbtbmcjanycijdkwjrkeqzswpoedkxoqdnzxbceqjlnpinaszuiboygntvjtzocxmlgpwvzublzkbohhhwhmnrxvscayiqsowblsccxyxafyhrqpbgggqvqdlzfkvcoqxdbywpxmxugpfgxyzzkgeysvtyyqtroipixgxquztqjekutrirkemltzyzatgwblhmbftuktdgleoiefawjmrswknmmqkrtukmeujkhayhokniknswubtpiqcrytwpzatgmfapnhylteckmcciwtngaihpbhmvbclqduztpcqykqbktugduzckyndupynddtbnottaobzjqokibgzveiymaywshferzuuegkpnoduzcbjhflixzdwcqstnyifztrrhtbmuzqwluficpmwlafbntkdaplsxrsbuovdeubmrjichkpmqmfaionzwnbzobnankjdavbmvvlsnrxxahfjunuoznpsmzlrcpygliczaszjfbntamujjmwbdkffgdtfoxhyamlrnzuclgglavycrpkxbilibkbdoddlxllwirlyvemfpioxzjrvawhazkioflosrtwkwbdjekjvygipladvaoohjbtjvsjmthrlsuudatwccwfnuqfdeurhmauzivtkhtrtbbtrlwrospubrlfuqkrofkeyyxjbnqqbxdyqnpfcszyxjchufchczrxhrjguysiqqqxsnngoxamhwmsxygcznimbeqbzwqpuusprnkklceytgiihccvjrctgejzhdezcotqyrvrfqexsbezwjlygojrrgefnonexayhzusqhqlegguvsmgwukoeepmogshcwzlmtudlxcrwkzvingpkekkgcuwkiskyjjcbyweyvnzkhnjjzuifuhzregfqnhdymahqycpkpgkxhrkhgzuooeayktkkiepfpeonkopghztlzeiqdiximhavpxizdywcpwsmpozfmdyrtkijjiovdqcjtvpysjgbyczqzdywujbdrsotjsmpqqjpalfepnusxlvwlhhqxezbasqafargllwmebgplzfhikxwpbvkpashhqcqfjxbdqbupzthutajkdgspkozgxzrwgesaurgbzmtqrxjtacjbooctsyuhlqmfvemcowrrbjmfplvopxohvkxbldxemaeoxidrpeulomvoncggooeenopgbiunehahmsimxvxytfgysvpylknijfxbwwwkkverzmcdvkcmxtvmdktzpriyffhenonngnlspgubsvvqxtaxlomlmcpfajjfmamrdivpuwlzgnhijccvkchbgcbmcdckvzdnqedpubvqnujvczzwjdlfbswemdhdvhkkqnahanngziguucgzlnqktizagfqzvwbtxkjhgqvssvuhhyrfxbdwzxzewyuxchlrgihuwajcxsiakgsvrsrqiqrlxqsduhrddujtculrdmicjhrnpgjraqgjaoouuoszvaydjkxiufpolxaywdabtnbgpjwjbpgqgbmhuircbxmigatvmewannvqzjdcfrrwwejduwygkmzwepfeqkrjaieehwozbkvrmnawxviktvmrecwyfmggjheehyukjxotpcncfpnlhxflvxadrwgqmqbbiggcxopagefcbmymbbbqhphnpxgkffneqhhsxunydatwamunhpcioixorgvolrsyhxrmbgbttbpkhxqjrvvdmprbwhrezizkzwstrnxscldnqxpoefxxemgqjdhgqtucdsnnzjjyozdjxpuizxouxjxcsrlteofyxciwouynqabmwjhueowmlrizylyjnsifpwctgjiybcrenqltlzjhwbevjxnkwyccplkdxvoyfqvqlncfciklnvpnpmhemcksywqhmufelynfwoztwwwrwmjittjcrtynhoseapcretcolwghsrrgxixgqctoaqmxtxiyithimiigqqgdrhbvzajpotiampfyzqbiddwhupkyylkqrqdvggfdztpdcumsbwrdfxnnqukiooqnooyfxbjegvzpkyyzqfnptdxqlvfxpbrieultmectzehklqpaswzzwaovmesvpniwqpdvheodaxizhkiypflookykizpqorajrfnlbgqfimmamscyucfoxcufpcuaxzheeeikdlewwrpunoarryxxkpmbishqbnhjdkqoewvvwaqcgyhgngjvmcfgrnjedihlpjnqtidhgtklfckcslfavgwhugpgygchqwdcdpjexwxyoyxdowgfozqcecjhnavgwhklgzzvolirqgfargmiksrmsngerazlklnxeojwqjelsqvbljfnkvypppaymmpoaumowtwvohccwbmppjvmtqeuyookepcgnljsnobuuzqmfwivjphmtlurelnobfxopqarfmmmyatufogeainqkngpqzrprxmdfzbytgmibsjzdzbqcpibmkqygopgxzgpmkfypppyjhlnfanvmkftzhksoyuoaybpeiwpeilgxbmeojalbklfobxnxigjuvdpxjwezehvdhenrnainrqjerkwualgtprocogzhixrmylmnzumbejqlrsebxqkzmordvvwtsizuddmvgxmhobdyhyuwablhsxuqgqznaxnrwgdiolndvuoihcublznfwbuvzmphxfhvfuhfngwnyhwyqsefucplrxjcbfbetommxqnuhylijkkjgqjipzurddsohxbrvaoyvxksseitjaodfkkrckyhagyhrliatvhrbruxlgsbcroqjthgickzcibrcstcbnqgdektgirmduqwniygynqofqeklcrtpyjxcaxkymzingvkqqljwthsukbhkttcuqwbfeqreljwedxqbtmkhphrgticrsmqblprvoixubusszumpxhrxpcapwumqbueceuekhyyekpjscibbndilzoabfttrqmvaytogkapysuhozzwcmvgeopuvmkggexflixvoyzpmcocllrhjkivfsbrqtfgjtkeckgiwlwhbpwfldgdbrxrsoxbjuqpfixokpruhdcqrgxtvtquqbrggethvlknkgvnyxrvopurgfzkxahvbaxtqsrbnfcpumeybxhdofomtnbjnpkuosznrixrytkqhawsefibvccwrbzzwcvdteovdvqqiqaglkfjysvfagsccrygehrvjpelluoaeyibpeyydkszpdcamxxeiqrkdlleyebazwrxzdjbsgebtfbjmtwpsrvhmkudsiabnieuimilgfxjvaruybiixkupnrbngkxmcejuoghckmayfkpljzfvgohsjsnztsdouhotugqbaasyczzhjzadpyvzxxrzpzhhdesyanxsrojvkrvemkmueuqkweaakhaxpanbswthmheduigyapnlskgzsgoxmspvzxgjsdyynxvoldaqvftegeqmggxgmrniecslppllxnecwdoqyehsiknwwbmrzaqsixlnsbbueltfapzhjuceurfwufjsqnuyvktldrfifpestrbbsfcrpaqhhbtezevgnexldfnwrlofwbsrmfbdbpgyadmbbfodpuxuzppafvscyyorvrfsebavocoaelwvajesedyawvrldbxlkhhwfeuinotnlwsbxwgghrlguauwqushduvjdaxvmydrpjvqjfzfpbdnuenzjesoqgiwelhjaniwsrmrtrzbxyogmjkktaezkkbwiwvqdczwbaadpjhyihcelpjecasbunrtnehkkulnbzvogjhjysmefidyidfqczobvojzmhjpfsdzxueskcypscalflpwuhjqbvjixcllkushxrwncuprrcqbetyclahesqcnfeumvvgooqxuglsqrxzttvimnxdimgukdzjyppjotriknpszbgnvpjawbqllaitttbewhlyuheajpuordsuyvekatgnghgzsxnjlkfiydpcldjrbipksfowifkojaelugsweazsvceadcllrtllpnmsittxvygylabeoonsdfsxtyrmdfsaqzqoxklfwdjzriqefvcgsbgyqertseqzuihcnykbnsdaupgbexadfcaovexeovkckgwnucdpdnsqwrnofrzbqewkexgtvadqblkfhwplpdyryopdaoqciclmcbzryqjwzuebsbhqfeqdbbkrapeesxdztfvvfkksmwevacmcpfggnhkbzfawjezhbadqfcrenltihqrmvdzjofemesrtorpitdocbsaxpjflflbywpoxghsmkkmxbjluzgramnizpszhpmfdjrjrevxrshewliuwleoelayleiftrcrapzphbggttzsvbsljkaolagjljnpjahbppqkixuvhfytmjdwoymcmyukkvtctyqaklilfjmcwmvfzsloallufwyckkpvsrnrssjsudibigzuhjquilalplzlslxlpatifcmjjxpoukcbkruxzqcybqtjwacermobdxliekxrdgbuuoqadcqfqiaprdejvkjcclinabqecrekbymedbhxylobihqemyawbekbrucjnvaocxlginmidltddgudndxvomsxjeobicvdvsxcvzqqofirtdncjqydulsebtvkkfiiboueddvxidaxzzrupgaegurxiuylhkjsmwwmexloxqhwejojshwfuenqumgotlgruptyfhnsegxyxalmrjenrtwhkcddaoxfcdhlrxehyifvmrrllqmrdbkvcxergawjchglwlzoxisyqsetlykzzsgnxkjfurcnpvgbgzkgvjsvpgmsiomvoypzffcbgkmzijgcavuirfhgfqcmbbnhlkutsgcywetdgmuwjtdcqqnjyxrzrlykopiopejxauimhwaulkxjtjdsedpjdzqcmxmhcvgahyocdzppbyhcsgzztfteaekmzljkypjyeweioumfckvsiuqqefgfvtzythdqzaggsyioibwiutgzsrjxaaocznlewcrfuqzwpqtajezpwbwrezaoyhfphwixjbfkcvnnbzrwtgimxrytsoxnzoxblzyfkhakprmbhioyrprckaemvphxmybdrgtcditwpiphepvvkozrixxbtjsdkgfndfvsqjmqbthzvpyifvewizgavfpauisuuvpvhxziocdxjkdlvawwwfjogzwopqguiyeopyqhzfxokhdmnpjzwmiexquishnryakvcncscvrsadrkhcowqnotuetinigjepnxzqxpnwdaxoplyakpfmianmodugetgwotseltrxmmrakocogpcswvhelaqhylggbslxsqarrlscjuraepwdipjqqjbfkubmdvhkeggktnlavnqcxyxobsrrnpkbvqebdlfacrpihbzloaaqsciuuwarwlqgwokdichlaihapllrfjndameviasttbrmialahtokescfssqmibdczkhlqqnzxyrsennzsdbtpstxaxhvcqwvkjjycfrecptumtuggwskhqmcvbuczveajsodidhsecodazxzzuyrqmvqsfgtlkzjprrmbydkreclkpgtxwmmozezygzbtxczleichzrtaoutrzzvnhweyykqspphdngvxgufoeskmsfteemnceezumbesbmrjmxfuruqbslnxbhclmtfbfuzjiitlaxsaforulocmizqktyflwspvauzosutexikupivurhnaghmvwpnbwmydbuiivkrzsifbhytsabpbaoiwvdonofbslbmsbzmffwkvxmaaviqsipazyxauyqpqibpajdzcxcyzlguvosdwdmxnaehwgyksiyhfmoufrqonwdknafqyjirtfmacdelqpzsulnlfipgtrxxiktkxumosnaucawfocxqhlugrzngjliqctawaankkikfqnwiqjcdmsxaebjgsiaebaikxqvurmifufffpxzgkqoyfnspjjqtfbfkxbzdqbuihepingurwzkejptppvhjdomfvyxrvsyhnuqaejmltsqoilrescxxwdiulynzupxmamngmvcbpksexzcxauyphxiprgfnkzwuwaihxxnwhvnopdxybmbnyicdmczhebreuspyrvdkmdspqbnnbfaivtgdsmjqdiohhcqrudtzrjnxaxvdurwwgquhsfepjkiqtbwcjefvdvditebvgxrxwgafrkiteyfvqxuwmzsojzrooeqtdxqvnqntfcbqjpuowxvodxggakzeilcappblbwjbghqtitydivfindmqlwdgkqawwriyydptrdbhmkpwwcndjduzjduoqnwtjbmsopzheqoilstzwoofmmprnhiusqlogfhwokwvpuegpxiyztjhjgstrsmwvtwjmnjcrejhxuyhbqvikvrbkcyozpjfzfcyjwrzfdmjijzykprmzmjieymhwsclyoeinnzcgtbbqnxwgpwcqojukholmsjniwcmhdcsuh";
        String t = "rmhdxtymfgxjluxmeeerhxrrtjgecmyfdhevakyosvvuwgbwmxiakbmtqjjpgctjojdqatuelqmfeldgoprxbzcylajhhjngqlmporofjpoyhrkptrbpzvvnkrqjovfzmmqybqxzjxarhguqcyvlugwmnzwtffanzuajraqbvjflgalgcwuhmrkblobybwouffzptbvkmwoklczfhvkqsirkqvhkryyxpkyfnwnjudvllmznupldkpoplwdlfzncwxdlgvgkehpcudqybnyhofpjnjmahnsrntxjtgocwsrnukejehwzxdetglgahdvzyypdztqwbnfrnytxprhtqjgobzjyewuasgnryjoyvwhdmemppixqcowgmicbmsukgodwgnsdieuqyzuysedmsphmritlqtnvnvfnmgnghxdsmmnzdhdalhfsfsppupteyrlajxrlbyqteaytgpsckottqnibbxhmkwuqfftwvibzsnaumsbohxeoaulsmwuvjcimscyqhtioekembdwfozglussscbzejqnvmcpfwdmxshhrkquacplctruyklkxcfovcbqllmyghdbkhdmawpmalrsgymgmbleftwtvldxkqprcbcbneyyluoyydtcvkimqjoinwntfxfbwqjbufqansdlgrgauzfwxpzrjxpesmozcsczbfzfeziedqzwywtxhyfnpbxfgbqcsfwdiplegcsrseqsagvkpjrupkaxffrddebpntocmkbarvdrhvujedwnktsdimzcbqlqyizozxcexqpoefjpznegsrtbfanqdyqaaudcklplvxojfuzccmtauaxurbwbexulqbxooadcrlxbtggxcycadratsuqavwwkdqtutptdpbnxeicmackhyvjhkfbayjmymjwejybhoryyqjjmwqzhpgulovxaccychffcplvkqfqghmgmdsigkugsevwkvzgpxiolhtzocdiphokfitggpneuisgnxismbhrturqralohcbzcrrwxwintbenpbmkkzuhacttpgobfpiycfgkrpawsdheixoyazmwcnrvslspeievjzzqjifxkkvqmswtozweouodkvvdijuibcdnwpgfsugufjocuutawdpgxaryazzefbapyzlbjbpltnndtxzmwqfrbjqldaetuqbufutefenwanqvtveoxffhfurgjgreebqudhrqtxjmqsmjrjpcpyuhwwotjjdjldtyluisvzyllfqglhrjmyaiqvbmrswhbjvssqmlgsgnymblqvthtgarrciidbuyfwfejvbsvfvpizxmonqjghpiiysiekykfnucfacmlgfidflwndywfdjaeupaspmnjlobxjutcughhmqeklbzfmzpsfplncuefmdbqttxwfcbglxmcgwwfjfzclhtwdjirwvhnuuvdtcrcwiuxqxwqxwzuvmcqzuehcmevljsedcyssmhipkclkcxnkbiwfghfstsklslgcqwbcklghfywhhwybtasdwivrbskykplcuscytwautoypyuhzdjvgmxazzdqdsudqkkhpbdbsmkdorurhqttaofphrxkxosqyflaztktmflfurcawfgagoazkuiaziflwvjzdmjkglvvloctyqccamerkqiblezlypityarvywunzzbpzyzhitbdlsnntjcuraevcdshfbsdlzkigzxyysppucvpzzjmahzokyidlandntkagdjgvpgtcoftsyilnltiylwpnjhhmppzsqrzkabgnkqaissrnuhjfztvzqklioszdtaeogwbadxqrqhnaxywkamgdsnyipgnfbndxjwninzjqhdhtgrmqtmebrfrhsrvyezvsqdecqgkilwjaaklzgxzkwlwmwailmdbtsavsweehjkelgsnqxbbvehggiceankladdexheozlbmagupxisghwwnngbqpzhwudmogcitlvqfyqvniwsdyodbuuihsfoitkaphcwphkqxtspkjqnobsixcvuzriuxgzmtvaziqvwmbupaiwothuajjbgasdqwjvzelledjffvwpkjtkwhiwfumpxsrdxvjfzcppiddfdvnhunbeucpmpzqqjzdaugzxclvrodndbuuoblavjdncmrfmsdbuuytxsdnuaaiaedutmtjmcbsonqgpiyjmrjvbmhkzigrcpahqefktkyxkirgiytjwagdevjoailxdjnvlhkjapcyydpyynmufkrzkpfykkvunukqrdckuzxjnskhkmbvofszgcpzebnautcydxpbafgtpmnmfpgoyppqybbnfybakojiudsqmoneygrxiwzilatiqimgpuujmmmbelznwwkeywenzgkckqckifvzwfifrsxinwdxlebqnlwtuqmaunfjdqvqcibdogridamuettagqxhyhiqwcwnbvmtqqpebychgdlhyggkrhtjsvtryqxuwmgjaxajfiozhzrmcxyushslxdzlzjoiehascrtxjijslophtjfczqbwpkwbslrqqtnsnjglxspmdltxfhvwxljxemljqhxzkpzjshcqedhoalsuksdhhqzhbnlgxyepkcsrjvxgymwrnwdrvawdorrkimrsgllgxpvuouwvxwdrisecgmprzhdlacndcpfxsfkvzkvfbziwlzkxtbooexhhecszskrsoqvdotizkrjkfcytdpbdegirbsagauwdpsvahyykxgbvbxutuaclwqxrtkfnotzfebagdrkynjxndyntxedirsoespcfusjzjordgpjrbuptkwncxlwrnjxfxgqyxshjzljmfvtgzwhnbhxpewwjkrrmaijvtngstvviaxkunlrbbncqonjjrduhrxenozpfvwjjhzlevbubdhxbqjzzyeryxpwogfcthkikpsrahyzdyjuzajsvzmnffqcsnuqjdhwzvksyzrcbcayztziniebutntekmqpyixujegjrtnywhtmqjjsoipswtzbyponaxkmsttbuvnbkpawhibworxhokkhppooefzuzpuihpaknwwtpkepnzayjhnmdrqxwensgafsxxigegzpyislihybmhbelimrvnxjjqgtxcnhtgwbxswupuhhmqrspimisylzhwqkakzdencxxamajpxhljgdifobhnydycpbasizikawdysxboemhakqusacipbrqzjuhxctkaklogmnwxjmamfzwkczkdugibcegxjkfipfjvrxwlutrjhummrvcgprutsqvyryzswpnwdgcvivbzexwegoyakvcixpgxvcoxskqjcwqdvrivzixxpvaswhgleiomfalaehbxcjzafgikwulmnikpnzxgubtwwwodpsorfypavcufrjdyolwmpexxtmwpdrbfdsowlspuyjcahnvriojstmpmxtcjochosbsczydfdlqeiuvtzmbksyibgfkvogcldlpuznynyerocfjylfyndsycpepfjtfptbunpckdpvctnyuhvofvgqiebiouvhpxnwamtoccpyqxspqlxhxpbwbeiuvhlhdcbfzqifhnboberwevfngquhfgvfdwszetricznkxayhjkznoaaanyqhnnrpcpsmmsvvesqkdoqhdprjhhcduwzlnjznckszkmcwwlpiayptiahsgqahwddlsfcypvbazucknrdbqflmcmgdulaqfyklwmadukhpjlkhivhcpjutvacflpcjtlziwpocgachctkkaaqvxxduytntqceygohpwknstvpkiwggqjmmkofjvucfkaszrtrfqwuwjrzadpmwifpdixrwuaoqjincjumftfnqftthgqtobtgjccrlsauyaviettrmsorlgrdbbtwuohaetqcgmjbjlbmlwejxmfhfhcwhghifrhjphpkechdkjbiulinkcakgqnpdrorjqaubhcmxhwzwmqrpzkvuvowkhxiouypouekykfyskgcawcmalyxguuoavwyzumcnwkwposddunwkpkldkvymtolnfdkzjbuoxezyypuwxuowamrmlzqifxvibtkkjqhfkpyezvogcsznbgtwhzsozymowcthnqniymeulcjfehbxhqweqozylgdzztyinzntkvvfqjaslwvninkoxuczcngichlnfdbgualrjmtdboclnkmxssrdpprmeyoqmfimjgoyjsnmnkweynbzmbzsgmzkflcftqxdiskfdlmfrjcgmatuvdpeawzutdluwbylzepneuauoffakzzkxapugrjawdnzlltnbyikliifbmpwpbvnvyyvroimzarulvbqwxfswhhkbbrvianztvmaiumlpvxkwxoolkflharnogpqclcyjuwwnzfcabeirwvglnhroaltgzfyctaeblyiigouxebnvuswdsgxkamczffnpjwtbrgdobwduqgmkgwynzqxduoatvfmszdrmekijaytineirrguplfeqyxyxwievbekqkysqzqalqwdqncjciuclipbujhrutlhtfdbcsnblzeituszcgvxqdtmetjnhagoasvdcdpiqcianqnrapigkbkjbafhnzsihihtjnexftvrppzpcedakoavzfedrjahaztkgivmyfknyuvrvdqbvcefjkvosiepopxsafnxrtqqfrnliigeqltwnqovhlmekwdavprxevhxyrrxnijrydlhfpmlrkinvxjodckoqorhlddhquxdjelepeuskfbcgtwbneclpwfnvyblavcteremndsejgdakjvypbwrmvcndirgyzncplinjttqdjhrriuxrzmiccjzqzadqaabwhnadrixtqtexvwpygkzqopskjbcfaexegghzwkkbvfpzdjqcyozugebmrvbwvbcqltijctkwyxhpdgibhdwyoesqyepjrjgejwbwrrfhzaglatzucbliymlsoamftvownliceiisachhaqzmhkgisyjmwyqzumvxqpwqkliuubcvjiesevhhzgodfsdxtuggdeowwgvdygwpuomuetreypumbnqwmfaewvegttqraprrcokmtcjkclqafpxtqunvxnbokbsimjwteboryznxmnlitkhzppnsmswzdkgzaoepusloiyqllxckgaxnhpdpkejgcbuckgfhzzeyvtwosnnxsdupcwuzuopuvoslkzutcbseeevdkzltdxhwbvtvykbstnuinpijxjatdrsgbhpitkmdtzoatgmearrmetmjiuklffydaqtubdtbdfgswoqcscbylrsqluxguppkwmzurdjboujljynafrrczeyfnucikzlyrbnboeougivvvriceqdazgimjnduvrtbtbcqbesdpyjerkzkfqwebjcmabkpxlgzkfwnogdpwunlprqwbwswekplgdcqeovezxwkrdsmovdrimcfcfmfbowfnaxchbaponjvfzflshpnghvntazaxgidmdsfjyuaslgnhjsrwuejxqndgtdadhijjkeeleruoqyhbjdajmzozxrysecvttmzsocdtyearsxpusxvdrppbkzrfvxewgwfejuhrnnldaghligaeraaoaqxgswitykslvvcyygpftpzrqkctpwbxeybbbugkguertfioidzjxmktqemcbxotjbpxonblvkrjwvqblrburvzqafrzxhfonlxwjgxrpbqssombbggiqcvxxpqbipyxkscuzgubvqdqndlfauzptnywkqhonmxvmrvkubhxuvcksktvhccrcsirwmpwfbwqcizmviklfmtpyqjqnrfyftpzjbnffbczwiamexobauwqcvgcqpnnvwoxrisnaqqdcoawcgmkdgypipihgaifzkhibxinsiiphpksfogtssbzxchsvghelajgmsquljlsdqlimbfxaznbcoluruqpgemljppozgtgdcynydecclaxnaxgxftbftarkrkmdmuzndfnuwwkppyzekywrecpmlgztfekuypskkmehpgylqmmjroiqhbcbxbiusybwtrllfanyjamlkcpyvdkdxrtwykcqhkfqpziyfgboosmmvmmbbtygikpkfzbvqodhgguqfnmhzjadcjlzxtkzqxjbinznnffmfijkjuxuvcodkxgocjpqfikwpnryanpnqjqcqeuqzlhrahxmvbhdczmicfxbqzyculceyzyqgzejsicueipmyocblyhagmvmhfnjicbdedsuorpifxrivukwatimhcgnbgtgkjnebgcuhuizbxvxsmuwhxhgjdsorltqrphyhyjhpsehkmrehhozuoqdairhaprwgtrtjsmtpzhnbozjjmsuurthjmdbprrjushthmwprrvqowgmloflfkjldsnuzayoegvjrnlrrkcgavwujiujrxnzhacdiuzecsefvuldzvxppnnhtgwupzlbjurijigieonsdvssxehiyobdblawllvuudtckupnljtlnpvnamncbrsdjsdyateelkyqezwxuehusieyecjchhgyniyjsdxdhrtpcaqpqkdcevhwvprekjgzreilzfhwwlzdivwljfbcxatczrprwkgchtacyfdyftamwnyqrsgkijxxuwscaknouokwfydbfoyehpyjbqvupfzlemixqsjcvcwudwkudtqihnpfabenfosxzorbbkedmwmnicrgvlhmtrzkrvtgyqkowqhohssgctwiisndvzuiovgsdizrbroweiycjefhnykanfgdxzgxeydsbmrpxgqfccgzpxhnllszdiuuqfpcfjfbbjhqycpsomusqaeouolqnchxqsibrgezayhumgmwtxwtfmombszhitbdmqlqkgkzgumtmxpsxqopectbtiqxcdqfnwkwgaokycpwlrbicvzofvupbhvwyrgspjvilmfkdpbajljqywsgxexulrnmtvvuvdjqmkgszopdbepwgusltasocutfgxnrzgdgozluayontlmiasixsrivwzafeeobtmnlispmnamfsubdcygoaznetrljdtvjlsjhhbehnkgomfkaibackxcogtswoepbxavdbakkgsiugbgxgcqwfkutsxsjdnineeyzvaxtvkvtoradspefqmccwylvizdaslvqodovuiitbueennkekucajubpyyvlnghwskuvmvgicluiuytlzqtqklzllevtzftsiohvyvjcfbgpwgojwuaexgmppkongbxlytbrojcaltbvwugrbentmybfhhxmqtaqhrqdtwgqxfmqvficcolzsvpxkzvsodtyxdhklckfpdjfihhgvvgaqggwstgyrdlkotlloywrzckqjggkskdkziopvrqxptnwzlflvqdsttvwqexfdmubdzdkdviogqxujhouvofttvtjxclkukrcqkqdqiymmtzswawmavrzmvwrfwjmqzqktazxlcrvsdmesnkjpxuavxxrmelkeymfovtatwwjjvhblflyeurvvexcttewueagqqisoyybtvbpawfzfmvivjxqxahfwwhgbxltofjmqaujkiwdcmgpdjxopnarqazzypfuwifznioquwevuqhsfdysdvcfzitrplrwkitntievjuqscxzqnrfdpuynbslssiqpxytcmyemmrtksusqarunqscretfxglpofwpunoelbhuosmqrabbrinrhborayuarnmeqfemwsyibxhpugpgbrgmumazhwqgilexzyrbphjyayymsknpgylqqiqbxjwecnzyqufzgtkupordaodvyhnvswafrkqzraennpncpmqhgmkqibdeawmqbfyogzznpepdgjwmkrdqowbinhtpdjrzskdfqtbxhdhhiizginwapnxzlbpmgowsllzspkyycgvulouerkurlxjxvvwslokdddbnygrstmeqmkgmshiqcxkrawtrlalzbjfhaerdegowzszasesonibmezsnqabusjvfppbeotrvadlxyxkwzzggyqqzcopsigjyobvfssuqrypaskxaumonsercadglnzrebxlimkxjnmegflynombmnegttnhsanaodfwimsecolkfdjxqhjirornevptuvoywadeufqgibasgwdflobuhvwgfhuiebwtwrkhudzcqukiimgczjkghqjcioixtqmcyiialwzlpafgzcdvtiftpwtzenkrurewgnqidwqlbwcjtdnhwzltevylgewtobvczhmxrsdfqbgitdxrjhxxorqczkijdainknkxqnbtgqdlhkdsrqomxhfzhctjkxiuxtxemvpqrybmljblkvtbzepwztjepeyighypemygpcjjiallyqltbdxgfglaeeimgnvncvjohfchqnawzcwtuvemdqzaesjxgwdrmhjjvxqqpzenybmmattkrvimbiteyosogssogtpvnneqdyoilsysokthmddoqgcaqudccrrrayaowmbzcrgoxskcsnxnzbinuwonimtjvyzytxcozikxcidqgceryipaekgxgfwwcggyujgnbzmesczwnxtechftprfvtvwnkydmmftbrakezucpxmtkvpazxtdwosyfeizefidlusthfbybtjoppurenzakgemuggcazibgnbgnvnmkqcivbvkmhytudiwvsrltbahxdgqonqgyoiiwhfohrsrgkwypdyttrvvlzrmrdridqsfdscfwdiwniuslridoxjrrgcgrvnujhwlrqbamgundxwbubnznwtjqcrfbwpusivthzudqmvpfchuvgmmmnhurkvjkefbapqcrbpsaaubqpeyrgzbrabfedjrwbumwhxjizbsiwthsadpbemtnlzdjfnbcwwkzsxfrvuwdubkkgpdsqpkexuvflmusivpuiyqydudqnhfjslmqpynglthhiytkdscinhaekbmmukbtdcicmsatlftlfhdsmngvhabgzqxvsxinsoupolkmrmyivetmltefxyozdzjwweybqsutayylzqhzqcojnogfwfcfrgnsyjzprtqyqjjhynaivenhygjybjkdyrycqdxbbkkeoqiaoaebsyprzpxobgctqcjomxxmrsmbxzzrjsgpismmajpjggsmhzxjiozemzyqerpywyjbfmxydhnjgmklogksldsbsfrtztybyqrvwnjnovznrzwxufvkiffncrlnmbllmtsxuxknccxidhndhwmbydrsmtolkqtkqesukxtxqvhyqqyyvuhfznfqpuwygsqovxhackffjmjtoapfxpnppanejtugbghtusqhuhekdsyjvcudabcbfuporcyyyarleopljowfxdindbhjhesizxswgehytqvhnafobegwjpesavmyxdsufqyvowdxfohlhgcecowbzywoorkffesutefuslyghlceiejwgcwukuzgrgmydaspzzexsvnuoscbrfugdwtiixpocgkgtogibeangfyiftlgdenzmqxxmpxgdgiugxcemuebjgfftlwxjzdzakivliazhtyjdqrqxylwwtrwpzavqnsfpiilnrugglvrdtyocjmobznpcojwiqjsgvekwnwygcmpngffkrsbvldmpckqxnszowknnddsbjtcahqhnnxqcimpetmhfyfvrxrlmozttybtdrfbsptbfuscdtdlviudojqxxgecbrxzclwzwpcymhhkamvupqxpmaypjffvszuehakduweeuqbepgthsqawxuikullrnbeieummxhwecilovanpebdqzxyvxpwlxaztcbosiqzldkveqyhyhrmizyxdbgshvoglmhjekiuvzxcftgtqhanypdrhjbmcjiybkytzwvvsrhiypjdhgjnxbnarrxunhibnrbzykvbfluyzdwjzphlyhywqrpgblzcircsrsewwsblkwjcfwttsowgayjfxsfbieqibsqgywlynfivnkngapobcdneasxlozniobpsuzrnvzidgnelxtiatntebbwmbbpskbixnujguntppkpgguivitygpbwrdoiuplkfifqudghwnyvsvyufmuklrrxtiyvlwzbgkujcazdnjxxdlazxgkapxzwmlsqtlnfypxqgjldwsnhldwyehlhxccabatmtcxjhnpukwcptewcwbdtptwwmzvzeowrrnvuakvnllyqdpllvojcjcollvpdthhhzfrbtrgdcfxekxgkodacaohhigcbvjoqdxjklbvumxjqkfzvifygbmojmgzxlsrwyucfhscgbbrycixitmrgraaydfajhtixjcuoaxqmzbvceogjvyawzmiighfdqietynzdokyfndnpyjhpjgrnpuycxqietkwgqisskwtgauzgsuffwdbclwrnyiemijtelfldfdzqundjdaqfwfspxxdjhwxxatkdzmcaqbspxmmhqwibsszniiqafcgwgqpcpzvuinbkjewnnfwpjmrxtciztyrtfxmdrsvfhmhwvpapvzbccmzgkfmcjsicrttxagnsatkvuvmkdiytckrquimamjqpabcwslrvzwfjfuanjutivjuztoxbksgwcfznmtohvdsisbwjtsqrjcuzacwzzkpcpojimxzeujpfffxkskdytqwxsfhxrlnljbqrnqltyvyfnrwmjosrprxzoakafrvwoejxpsvvlnmuranugrdxocvgdemrxalkmjcfzxxkljazmlpgfayyqmvdbdzjmdkddtcivfsolmrorslrrrjgjhnqpkzpmuritulinrtefwelzbhjyevzybokvozcyhuategnkhyogxtsevqqadhfafyklpulvvsursdagemjhqfmwpbbyqugfltlztbxggrsmtpuyjuhjgimjqprjvfyvhrqduwacpygprzitwsxbneqecjtzgqvxjfbdwpclzoidpqwpbskquoczvxgcxzkopdazbwbuwnimnvmqtgifpxxmzyalgkjjfkzmpvxmodijsfsrmtrphdoiaqmaozsfshpfvaadlunwmjsgbhqfzbvnonhojuokltmxzfysqdzfinvbtzmkenvdxdxlaldlbvsjnkhtxqygpbsaghlnbwpvvmowebrimqqxywlycuwxsrlhdxsvhkhclzkheqrbpfhgewcedkutywwvpxcppcupzoyaxblrcnkxnpvhbibftszvdxvmwdbvcdchkldjyslzgcgmubocyvmymlpvqkfstejrkbcpdfbuzpewzkshremlhihcxrggaaftrmghlvrselcqhommtmmwyvjzrsniigylfcrxdriselhrbbmulbrioywbiyfsctnxnpwwinwokjgldfihugpnsljaqzegloixsgdhozhvptzkbsdyzrvbwdxlkalxijflnbzwzjvpmnakraryhmikwnewzpuuhewtzxbkhygjidtyaxfrypedgiqhxfqpzbcgzopwkzmxkdcfyribjevbnarkepkbgptvinvfesbxipizzjjwcdfklqijrwtxnqgszshpbmztghuqawoiemzumzhygxfxdahtkcglaemvjezekjguyjijdmolbimwsitookpqdwmpdqljrdgnzbnxyfxeqqmghxpfvgmkbqttwgndazwcmmsekvpzlrtugogyqzlqggcwrfymvakoecdepfidhjyaegpqvpejzjrhgtebycbqhekqxkrjatuenjpabmygxyghmnyhovlkgydatgabysxkqjklpopyfwaiismogacwcfxlevbgoqlertugrckfciyesmdfowhrrorcygptsejbmvbyxwtuknumcoytyxrngrsbynstxrnlxugnxrkbkngwxyvwblouguhghmxavqidzvylduzsipnwzsychyziafcwshgpuijdktnnvbiifskooqwwvpejmrdrhlmrurxhcsdfgpkkiyxdpslpecaktbyfgrhjzspxpgtfoowqavofpdvfxcovxfenecvqvbmazmvzbstjqcjhkysrtehgbvwohpuivercjwaersguqsuwegxacxjxvgnizpthzqzvxjyjtahaugostubcslxdfdrvsyxgenfdlqfpmodhckazrochijnypuucluhvxbvgohljimmnqurmudhpwrybfdhupvcagnbyqerbjmzfwaqmtmxnxuihpvyleyomcljzmwasimotkjtitpyvycpddtkytbxzavslcqdileabbtwjnkcctxssvugjfywxqgcaussqsmftgghrkdrcopforcnaybrrrczymogkklfsdlowpgtlolbinlqpaonlcwngoiucipgcabeixhpcpopxkxbbiuuxwtnfsoizdusbjvcqipnhqefofcgnmjzsxtnwyfivnvebebmzcqhkjqipghfzlbzlcvlpqiawpuzvicsibfidzannpgzxvytvpgewqxafarrjeezcscvbskltogndninthywxescdzfznbgnvwolsvspmudjohifaaatnwgyusuephffzvkwuaxwasgxqrwcsurhubszmxicfeeebvfwikvnstoruodwhhcpsprimurozcnswnzuemjabeafmvbdldsrbunecoaaphwvwlkaghabtioyuqyqtewuasmyphrlgzmuaqyvnrhxjbiealqkfnzuhgrqygnoqivsctrtmafilwnkgcdhxcvglaoscjrendrvnjkoiooingwlfelnqczhffmywmpqmoporltjadptbqzwqkbscvdhlasjnwfpjrlmjbrzegmriaclbwwckkspwzxnznnyfqwgypbrggkgbsjtxhjlilrzpkzkipgmsesgkrykyzpwatwslecveecqbhvzmmkdvwxlywtopjadenbmskoqnvlulwefueojbonjhczbzaveypnidrzqoqecgwbxseakclxlwrgxqxetjeyhgesindjvmkagnjunyugjblmclgtmuubvutbxctpoakthquvcyxahectiiupsgwfvfocflkvwzmbyqezjxfylwxlxjsrwgopqyjsknjugyrpoxnzqpkzkugexlicqksyagrfheinwpugbojioofyefrcbyklgupiejlrrmgglhwrbvilwekppjpabzdefjmmwivxahpfwlnrzvxhnwbwxwmowcocvrygdrxmhcedzkazdtllrllwigjpfxqkgoenfcppsggzcytqonjukvwnxzhykpoflwynqtoswumkiahsivdklcqldajzmkmftvplfsetjonculbvvautjibxgjvdjbnthzbjpfwanyrndplwbzvhaeandnzcthwminzainnzplighapxtayxjnclkjvzuorotdahlgknoukomdwgygzlojyabtfrsngrgttdxhwocpswwwiyamyecxecpyjwajhoxypymycpzgrozjlitromizozwwtmivylowpmewizxrgqhaptvuydkkamodclnupaykigsdfszsuycqtwpghjaozxnodrundzypfkgfgsfrofkcrjralbqooiwmyonkxfwmstdfoohfazccpspjjotvoycdnoahqnmldwlludxzhjwnshzeyyrvuamgrctyheukcejyeuvccvtelhqplbsqyfiovarzaergtrjzbzukjzymbuosparvrihjqlmupsylgxsfukscxkrkudbjjkjckwwkppvkqzdkckgopqjtqxxfkquagxtuuhmmxmbbpvchtutaoflrrtpwfbprmmfatnfmoonoasotajrysyedukuqchqczzzqdphwxlvwqseiauugbqdgaclwbcygwawewuvdibciygdfccjtolbaqamygsqrotexgwdxgsysnzqvysnwixzyqveiriotuycehrhdwdzyrwkxlooceiizeukvmbihnsprurtlvjbouaasxunnufkmttnpdrjgromvtpegihtpveetycimaykmerezuogddrrziqgvxjorcfxuxrqiivedvgmarhtamucshhyxjhrfbtgerqapkfdebgorpgqpfgbkyeorcrhtknsehjngbfntztsjxuiosflkwglubrqezmvpvvkyhjyuwrnzcwdvnqczaeyofyxlqkpfqfskkzemplzslgfonsqcuwmsnjdxxqtzdzmmshytznodgyqfpcnuweejbiugbicivwlzawhuwgckoexoktvzweryjhujhxbrmxlgixvfjncrzybubupjuutqevqvesmbmevrmzmoihxxaqxbcxendilwehveefhqhggxqpnildakrqcomqjadksbbwkijfminrzvjogkowzdipdbumrpfoibhegmvjuvxvtcfvqurupnantubsukotuexidflmzzfwmsdyhzaepnsjjaellxzlposdsvppwswcofjbttepqfafuhnkfrzyitoitsloimdnbnirrgrzkyhwvqqejrdshqojapuixkbregbbjzvzhyjxanxjuemztouplldrvbkqarikjayiinfbtwmnfqiyjhzgolhnfjlrukluobwwbpszlbztbrpcwpmihzapahnchpcfmeqihtncldhtrbhvwlvlqmtjletasjhkuzmmwswuynpstmpfbpvpefdgmyackplzmweswhwzhklkktdyuzfftvphilejnhsfwsnaofhdxruuxoasnqighdowszogeckarkyhtoowphvfthmqjktmylaqnnkxdczzbrlbzujlwcdsxlymxbfpcoemfaenwecmmrmgbcipnkzhodkwjfsamxjzdcbeqosyafpcswmzbdqqgbgqpbwjmxakgkjfyxfvpehjshemyxaaayrmeiydwkjidzehzvaycygntmfyvhrxlvwmzhptiyrpuhtyfvmdlfekvlzoypwfxkjldxjjneoslwaogscgbhhxsywmanpcczfialbjjqtaohmgclkanzwlxzbaqkklrvfcdwmspzbybogogmrxvhqharmpfmhoutakcqurayjtgmbgdhaxkizaqdbltvcrkulngmlebrcsrspbrajhmwfieijpmgiwuhqncekijhdhoqkmnuejizeurkekortxiwcxolthitpbskxshajotjqdyumfhbmxeufbxcvcpfzhumudfaipxvlnimeinutendaqmwwfgkzcsikpicjhbrlpfuezgvtrujjvyeileswyoobmvwrjjzzzygkrqwnsadnjqdsxjbhnwxlvnopnjjsjlqijgottxnvrxakpgktbozsaslcnpsxieqybildajhvkjfghxrgozeftvtdlwfaqsdcyrdaltyejgwevrjzggdncsgcnmfccxwjindrsvtyagwndnuivyyocwgqdonbiepnjabwkdqxtciigvsarlehxqzabbgroneuqecfxlpcksomsjlvjursmhjuhsmviwerinkwdqnbfvvlyhvoprmrwqkmmhaecfrpwikjkyvtbqecmqiqcouxailrwnqixmvzrwnjbrsrrvdbhqkaelyunnxxgsswggybzpiekrdsalgdosabyqnvbpgxkikkfeowctbkrqasuayzjhzuteixemhdtvzufrswiqmgyveannkogzkjnkonuiffzrrufposlvugwbriacpaowgsbpwkooezclihbospnqkjylhxoneinzflmzvdnriotqomadezuymaslufygeqjunwfmkimmsxrsmfouirmwsvorrrjaazthnkpkqbsfgdlbkggpytbmdsvjlobmqgffoyhrjmfjoagszelrdfxuzpexxamwbqukzwlztdmiqtlizrkefmjerwebbosiwghzsxtnwianyvmmbvsgjoyfqffjnzbdargsjytfoqyamalclqzmcbzsqbhdgqixveexmuvvzygkdzatdzmbgqqbjjwabildloeldmyljorzfevadbpexntpaoxdfwbpfjpgndwgecuvhurvemsjlkbhrnabpuotuqmthnhsrlrzvgyohdpytaeyhwhlpshevrrnmrsgfrjxcwwiiqdiqzyxyjnleyubmvfqgfixvdecpmnbbkxanslzxgiyzhtewbibpcaslbcdkflpunhhcksglbllusypiyhpftrymhatmvsritmzdslmpcsdubjvgxbnoymuwtlqglchfrjuxmosjmdriwyoatorpdgdvefbevmejkxolveddzieqmbonxjnlgwnjcobfhficrpbxpixaqwyztydofqylexfovzsycmqhhbpzhwqyjpcjddibfmiimfunxyrdlxwgaomkwmifvurvqkxmickurdzbhmqorzszojlwctslrzwsnpuubhrorbxwylxrebpsvjlfnizbhztofdoojriapbvlsbahkszkmzllyektxledvyltscfuszaaakivuhzuqepjgtteplhnmrtyzlmynqrhddxxnukiunlzgeojmzpenpmhrxfcygznkmnpeaoqonfomsikfleqrzewatdaqnkndrhynuizdkdfopncoqpuauzyaqwkqtvsvxszghofzzexbqmaldlpqsqymqtuwptqcgfrabplhsbxcorhkhabswnqlmsibtnemsfmdqbwrkyszzignswgwpmghmfylkuiqnebbrldpljbihdcwhvokeeotznfckuhocayxkxyyxeinzkelihuaztrseczjqnmvoicoyufrkelyxhurvpwwqluofrstuscirfppnlrodknjmoekgoitawbebrabnlgxyqktbavlbvgeswjgnrctrbzekgibrzejmizoahwzaufsxibaqjhxoynmkbygbeyoeamusxqapjhqlwnwmnoeueroqfigfsgszbbtoaqyrcjkjstzopgvxwpparlebknbewomshojgxxlubmdebsqrsrkeawsffzrrgnawvhcuizetnjrliujfdtemkrsfmgbofrikddmdiyqloeowayoodkqfgjrfhitqenducdjemhuyhylholmncjhdxltuxcywekqsjtsbuunopicattalhpbigjuebleicopoediksyvtrcqaldoxgtduxgjykblzignqcyenecveyzskyoxgpwanhihcgpuwaipbnewpnouxuelzdfzdmlzubuiirlkuxtgxqiofbravancvlgpdyaoocugzrfxczmtdzvdhbetgbvwihxawyfifetcjlrrnagltzfrpvlhsrqnlvvjajspivtuuwbshrcadgzlqfanyjovmjhlwzudmdsnunnsudewyerfespcnkchnxedgcslexehpnumoqjmzxydierxgbdedbonpiulqiwdfjviavguccqvcnufmcknwnksfzbzpnnandrafsahzgdxsrnxqowqwrtpizokwshlriroxvtdpmytzloogsqfswgnkrmrizetighgxwuetqubiaaikmzbgjvuwuylwjwviprzenrntixtccaowtkwbcjptclovklfyfegesodtzklqcrsvcwpkgxicfmhguobalcdhepfnfucdcmzziqwvaqxiaopwlqzedzycljqsewkpopmvljtcsgheneyuibqfruewbzsjzcshevkxmaxcmecmwmlhbtwquxarfuvczmymvarqequtzffswigjxorujisjbrmhbxzduywievsmtodubbtiaklqchqbjrqlauonlniniixgcauzoofjtunppuyryqsrpwqibdpdtwfxcqxpeuxvzxcvsizxjbildqnahlukoojzgopgaehrttkvkbsclpibhisckvjdalwudruwhydhspirljxhkkgdwvqccuheoeulpsycvviqpuzsahcddwqpafbjvzrljowxplrwpbxeaydwgbtgdqunuacehtmykoiwxosnxbtdusxtzsutlhcbjbeekqtvrlskcyfjkiwkvczkkauprhrwwvryjsniljfejyrpwpvvimxlycijrsxocyeesvhmprxuaovcxfxhootpgtgibrgvnkdbsnvqqtcnmxlgpulpwpsvtapsaxyhwoscfwanpqbbelcopgppdhbzurnrynhhimfrfujlcdnlfpcgpblfemixuertjzcpdgdmyzvaojxnthzrsvrqxitlkkhihillktjyzxangjefyrnpdwwmuimrlwskyvchzeqxjcqnhtqmbgmfhcbevximbqrefstwmlcbxiavuaumhnchxrjlwtpqomougfxrqlbkobyzcorcqjaeomnkndppgboftnciaseuwddbsrqzoiqzeexmuyghqmvewaigbfmnbaroqciorhuvjkqgykyhvrpfftimxfvsointbebiugjmeufbtbibipyxcotvkigyfzhfwzzgowvkcqmafhyqsxjroicbsasdtipetfceasdupjmlzdxbqrwnykgbqvtbacagnbonutbfhtlgqjmxdaoxpnbjyvtsjashyhczhxmfksisdqdfobnelhysjfpbwnkszvaevagumhxmiwrucnubdptdhftawzephdwwvutbrvpgeobxducraskhgbzwixlrcmlnshjqabcdykzjlyefidziwllwmsjvoflxivhujhpkkurvqnmbngsimljvhksmpycrarwznacbeqkjqscbbxfcjhhckupugelshuoimzkxhhquojqpwpugexfrxyhumbvifnppcuphverxevpfskdfcesooqhonrzroedvdysleskrigaylhrkzamjztlofofiesulzymfpsltskfsrkwtphoikjpgxttfvanejmmocnlyjobfiozjcjjykhjohccjuhoxnbflklmmduvlzbzvtstjycvnzaucggjwacshnqeudswpgccdgklglahznncjbyanqkkuvqwaqpbpziipnnuflvhehcxqitprievkrdvmyuskhummdxouyoccpfrtkoznxisogdtgicgxqdiogsqzyayaismwbfggkomdwfmkckyzqgmsnfjaaobhklqqoosydmtcwtuqbhneoqrxffrhpysnqthrxexhvjqrseynkdzufgnvngnicbmonpuemskthjgtcfgplydafraxrirstmnnkiwamjiwinbeiyzmkoklxaincjixhrfahdsptjmwphsvxgutvmoeaamrnyrwwzjjmjlxisrntnaimiwixysylyqeebpygwtvanqvtbkctoxjfivmflhampzbzwgahfajrilkliiqlhvxyltughppbfsnhnbwhnbevoayggstneyvlybhojzcvvxfpynidcgutwkniystsezokaqbipkylsvlpybiptlflbpggfzdljvosocbxkzfdnmmqzzjoiwxysklwgrbumophriddmtojdtegwfctewxpovtxqmgnbsbabnovybsomvkknlqimbrgrqnhlrywunnnuzucauhzxwcfmpmrkuryangnyatkijqwsldaxzishdlkzsaqvlwlvnutfcvtzmwldlpvhaudntsxvifddqclgbedmybpnyvlcwyreocnxxprrghiiadykfkrxkyehftaavzviukkslxnakfnmrgaehppwnlczkqyhdwccrlgmroyhepknhbywvwbyftiqxencfiheidvfmrnndfrimccemsfsztkmvlekgxfkjbimbicvsojpkcethfxjtzidlcrqrtltgzphacquiljutupdptcjuvdfycvhhigjstqombavnuyxmamylvmwsdvgapybvqoyatbahiofbujwwtyerxhgnieeiffwylbwwweiusvexuarkkhwvfcyzylucqsfnuxzyagosshsscejbjjvqujwwonjzynfhyugizjjniakrfiojtvgtdxvqwwsblgnhwfbzpsosppzbsmdudatxrixjljpbtxypcrbbybwcjczjrtrlpaukzeubayanbwozvaenvmzdfuaynscybmmpnrlapcbrepbwxcqubqwonkssyqnzpwjqrrxcelcqtrhlswwnemfbrjtjrgjihlowzmwffdrnfheiyzvwzeiwhxwbloyhtpripcyoqlqlourjqwxkgdryeqzlikxhxxvrnunhrzqsnedyjsfayvldjypqbmyxmxclpgrxkrniwyytvzzcsruzszryxedlpsiraywmlmimvqaunfiyirjfltgvfmxprzjundgbwmgtjreekejatjlkrkykmjcxlfhtdqwjtnijogvpwnhkeeqrjcgtsutnyieqzmiarfdbohuabhbvyrmglyhfnxzlbikkkcrfsanqnheykgpjfwhhbmyatkttcwfadbjfhverrkjvfabuzprbqwfkojyarxgzrrhpteokspxhecnlgxhbcxylofhpharqbibnrimdepqolktriobsnguzohvwqgduloeijthifbnrxnmbrvpukpfzdaulhckfvxoyceooqwjtdusadkejrgdekvskbuuiegsmohhawwcmmenenzcmnbjxzwhunoskmcirchfqxhkpmxideomtxzcontqpakhxwbxpbflqbtthjgdfqbpaxpkeszlhpfahmnfxlbycjheskiqjrdbjslbeiedziehirmbettjjjqlcgkmvgpoejirilautpgvrcvqnkmewxlkgpnjgzmvucvulmxelafmcatqammfajlfczmawdbtskhohmzleevggchmxqpjsbemmxxorgsgezieffsbhfbzuzqkukxgdntodgrfhygfmmahsnndsskyxnkuqintacskekhnsmabcnjugyykmxolxykdhiumlhbmmhyylevdnyitphzrnexxcrfbixcediiaysljqcwffjnvnkylfbmyrffhxxzzwljorypmcnuhjmorfgadkodhliiieuwoahvvcheootwozdvfkuktbfuauhptksqbkhvtixxgixmkjvbqyotiohbuqyrnneqzqxowjglvcvpctnyzhknxsrpnpuefbtuieqyqntaxrouxesfdravycwmsrcqipxksngafevoabbacfsdmtcnpukzbpmpfgqhnrkmhihjkckyerkrjpsmmyfqwevubzoynuizgmpovmaatemzmlawejtcgtmtalxzvueumaysxyrnhukiifbpevuztaxvgdughkivyqqcihbstushixfgjchtdyljbsxenwdyvigroxrjshaonxylzjcoddhqxlwyyfloadmtlvceyrdvdrjnltzkccwpfmsyqccqvvmastqyfralywhkpxxycgnnryjjdgmkylgfijgykhqnwybfbovghiuatlkkmaxhskjabwrgizaxzwhaodtzgnlfmfqfphbgmjhqrgvammxxhgtgbonuzbcfwkhurfkfxauofrxvwcejuadvkrslhkcelvttlfncpdirdnmdgcouafgsncbfbrcjkoklggynnofsfazvxjgdvlcflpapijcbteavysxerimippoikuyopvwwxwyyadcrklqonxcnppuxbfwokhjjqurxnmeyxfbwdfzkrdjuwgaegqhajbjzogqcelfwcxtqiomdldyeejawceiraxcvlqnweehndbrkcdqrlwkgjazxllsjuarwncdchffgjuhzccigpbexlhcmabsbaynkbmlvxkwytdjatgkhirbdgjvrqkurjvegjyyuqovcbxlkpnfgsogbchdmccikmbhtpfkkpndkhaksdgclsgehztjjqdzwvpoqgckezzanvwfwkqgowrftcmykhfjwcukhczidtifvfuljxqbcqcgrqejfrrzaebaaliwtyepmopweabgffdwnznydqhmmysxyphbcktxomgizigofwixeopldcvjfomxxbcdsfkeyrgkfkdnmhxlrcjmnhqheawpbzcdcsdnebymbxjoefxoluyojtrxhgzdbmfjqbpuzwpdiczmsrafqxqgrwwrwflaeslhieycmjopkzmorftpktquksxrlsgufuwgtckifcdlikqlmtsachaivbzuzwrkecuidaaqxwbpwmfinexdeupkkspxobbbdlywgrnfzfxffjbaaaubamdhegiefjbrxebzchfopfxghwmmuiufnxaoxcxjernespgmzinaohqiqunmctruixohqbfkeckrchwsdacoexesbwdicnjhieaabonyqdjwrxooxazxxzmtpkbkcsmknkriizkbvriqyifjqdwugucjzittkjtsbjxctfhsmyjbopwvyptqkfnhlxkryizedamieoqtiaidblyqcogmzzaaynkanhovkryeaqzfopvwbfhnqczvuwbqwxwgdzggnzdbxpinipbcmjlijpkkictpiisnbrrfwzbdwkgqeaobfiyrmiiaazjgvuqhupdfqatcqbqdsxdfllvugnkqgoatzfpgbhvdmtrbljcqrvyozjsygpbswxirkbtccfwvrdxrngfaxdmiwxjqvlgczpicgtwwlaiqieqjgaocywpxcsnzgqsrczougoznxgjmeuelupdddelnztxbkytavrnqqonaelwwhynyybktjsesdycwqohjwswcsdbwdrhidaikvktlmiomsufzepknqgkqujlxxcyxbqcfxllgvazpghovtuptaezgmalkwlxigxcqknpmwfbkobdyuhcsuqxivqeakwprqhwnzzoozvzixwcjnqgdmngsuvypphmmnuzzxumduspfanttbwqfrypzcuxxcomgcpsvgbexavfqpeoqrjpselimcwgkdyirlexjfsuyxozszsdcmkrakfmdkrjwqbfcyfzanatcigdxxkxvnlgnasuqplkrvmkgrwwptjaqkmjikhmojzxhdzeyibrbaorakddirlnndggdjefnvsvjtrtgtkjwfhycmrvbsyipjjrgbbyhwzrhzenvpiejitridwkcwmzjojcuaqlvyxcuuvycoxbeczvecmzmvsrdtedknvmvxcqoxqwechnxukkqnzwaroqvagoatfrtsvstnekzcvdhepzkdabmvkfefgkpiycruhsiraejcckvpnkmfexhiixicpewpmcwxmqrcnrvwlejsvzyzfwimgunzexrdccldlqwqiwzvjcqxnnhtgnprractdxmuysfyhbporvzyebmjubeewzigtaprgktntbbyvxwualonwgnyivumbzpzrpvlmictwefgibvixarwuyxxdodefstdtbbewpxkxtdnpxsonqyttfcvyzrwlmfivxhxzjzlosvuonwbkvgvstcvpunahcoopoqoygvtehpbsaqrhftkpjetbujwdevjponmacokjyrahxyujtdbqhzcaqjapkmzajejiycocvpeuoaqfoyphzagcjojkoitahkwrmlircjkyyvwywoqjpbzqulemuttqkziqokqhfenyljxpkezvdhwqertcvotghbwacqpzvzaiydhecnjzzthduhdgemmwsiicvjdotxoqdzijneydolnjaacuqjnwmzacuxkjzvzkxxujnlhjveklecaxtwunlcjeqmujftehkteewfbvsjxfazlnpxumwenvimizfbqgtregbvvnouzzlceltrtybirpdaxicjdtqsffglqzakelvfjghafpaqrciowhvjaykbqldgbfrnnewfvaubpwhsikqpsmralwqyermscvwbpardwpcoaordygjlxmopfkhgihsakkrmuxsmkeqadmwolpjibawdzgctuyjoqqdemufjfksugwuqeqadjvzsamtemldevrhnkltipfafwrccgodnhfwfirmptjxsjoxtztyiseyaseanjwoafzmvhzwixatxqabxukefflrtntnqnywtuwbylwsmzfgzxdjizagwbvgseclprsijfaebpzbruigbshcivlcognkpsrasftipfdeedtdcctmvydyribdxvuwbdkpwrzbpoapvsgkflfvadzqrwfjdedkojrrdrpmgcbpekhllekbjvzfszsorncxunoluyappofhsyqlnkgdggqqnpvbrjejxzhiscpjslkudwhhlquyweqnapfuihlywkkgrneyucdnobcycplbpfbrvamihdrwiwboqwhhfqqlrewuxqvedinveksftvfdqehxtwdwjcgiasggjuqhhazsnwmfoxdavbckgyllcxtdmueksagdhlggvqzqkgdstsfulsfsokwpdearwbhlhplxinqzcpoyupoekuljudqoztbrtegqbkmnplqqgavqvsgexcnskagzpvmoicbngxlzwrbdkzwehbxwytpiqplauylcwysxufloqdeutfxndokurlkkftsagltkvfqrbixfvhkjdzetkdvuzvkjhuemblrojdzokvbboowxmfwixnkcccrxjswcpqdzhryptbdlnstfspsrsytyezhdbcvivtxtaplkihhherbsvqaltmepnrviwhxlbpbpmxavxieedimqzxeqwrdcnubepjppbriiapazeclwrutzupdphmdjehxvrdltzwniegsklgkwoedtmtzsdfdvkwnvohraetqcabfnoxsgcpbkxslblqqrdlctshplijsdqyckvigmijmfczufwfzyjzvubwlpkmohupivxsgwaooswizbghtcniubyaiqkuzkovfbnnduahnzwnucjutrjoieeckrzsdpmjzptltssujfgfxskuyydgucmvfdhwpvjdkhtpvpopzqlgqwrqtdakjcmelbwyzwbslfvnrwqjioabfqlzzeeitdltknxewcawozczyfrpmojjmgiicgkkdhhzraectcpgvxggxghndqxbhiyefycoqjfvyxvfajbifvhjhjrtkuewoljijuybsyxrhyzbyhuerfforryprifvrpvvlermkjsdtzmnszhkymhsuhbqujhljuxezmgjvxzimstljktwmvdckjfquaqylovoqqaortskmlespwksrsyupegsvtfebtyzkxmrxvaepodnydnjvarlunuromogpictdurlvghknnqzqvqpzaiqebxorqsxnuwwysqqzsbybfdojgrebzrijguzycglgyxtzbzayndeyunyxsmydlmshsdhroahnkzmmopbfoaicivtcamohmgfudurpmfogdmupmamskniedcmukfiknszfmjauglnocsqmprhrqoexxyqcaqwgmsrigtfbiiqaxnikoykdubdglwsskiltznhhepphmnqsxcebmgdraxylpvjjcxeemhxbiczkqqzwbjnbbnmqjhmaqjwuucahufkqhpkajnbbhfstlxpgdhxlojqjlyddozkhedjgnsjnaymbwkajcboqefvezcebjhhxbkgqfchsrcvarxdjnvzzvgobpboguodazkdakownybxohexzelsrfvrcinkbaodozktxslybpzzhvxkfvlokrfbdyoyiutzmtutfcuzpoaaoogsjpcipccavkhyzifmtvshlmwjdljxamactcgminbjcbfnebsjdbiyesgpojunrdzqwhqkvsbkfjsyltdwbjbybwriouuimisunwbfagwclhadgrtzujplkizwqjxlzehmxvnpgrrbosasadicjfiwxbztzajstiyelrhmnvypbkhglnifyaupdsecdkxknjhecqcnuzrpbkcsvwyjotcrxoncboqwyvuezwyvffodgciyyeajashpqwhfhnfnydlctjdogjyjjlrfvyabagisfzcjfwaneyfqywgdppqbhymivfeihzlkoklazzqcopibwvmuktwjwshhzjdpgqovihrxzwaoagbfylutkewrbphagillehiuyjyprudnegorhsuyocnguucypchhllhscxebifrwqjznxvftwhnggxiwgwcxgokbofdvcqvkvtmhzagqqsgsdupmiuxywvlgstcgzegkcoadlxibyhcoafjpehmsdvtashglaowixsvlobstaobwqjjdznywdxjemohurlhkdqudtllkhsugupvixzziujfcqlwnrbmjturrbkduawyjmqrakgkevqfdacxmqjxajebflfeutrzwxhpafsuixfqpbafmeqmdqwaegqyndngyveugogkgcehakjyosvqqwenvjltcdvzbgqpvfuxsxrcgyhahtgfyvswyieezjotnqzszkatamztadtzkgyhxdhgsniwfxflaijpetjfslwuumjmuuuabpqgsfzmfclrqhfqxwylzvwtuarhvliyruzpachitqegjbipshxyyhhgngjrritnhzxisduibdixyoovmzrmxwhtnbcxpftadlhicsrawzslaxroekopsqyzshrmivorsrbplkypujrqaqjnicrtfayekjorbwfefjjbidrwftycjmqtruhtoeeisqollgmmzltzgbfyqxcixzoocprvgosvbmciyhzpsaojkntuzdusghwwbcwtoekuqbttvslqsjbvhcsacbqkhtanqprvsepowvatcylxzkycbziipyrhswryrimsurcdtepcgypobysllhduegaevuaqwmwglejsrimachbmamirnxnwqlxddakikdgvrlfmtinffkvaokjukeoeelmernmprihrcwdsodfroxozkxgkgtxedjgumxexgzbqiyhcxpcirdnfzrwqkjtvrjsqsbhjdewktoutdftjgwfqpbefaedcslflveesdjnonmmkpdgclzjlrfraaiujtvtajcwrnlozfvtkucmgqfyfzcffdsptekiroxcwyqyigyzxttxljjmatldfntedgcxmrweunzujeaiqyfycrdjdyfpgxriwxexngbwrffjhrkcrhfcxwgzmmiwvvwxhcvgtqzcofynpkddvrsfnxmofyindkdbcuaftpfyykbtblafsjrwfuxcriesojxjldcyorinixxddxxqidkjoaubhdxuozladvlueiwzxqpcvpnervijwawabshrwevsadjsijyjlbhsamrjftmysjsrnojpexafhxbybfvmpyvrjikqxeadhqengzehqcekyrfxtkaujnmfbueycxqxphqaowomesiaobsulsmfvnrxgedpyurdrcisuijirqqwidpwkdjniwvsmtqxsnkmozajcmtbecgwlrzqlllxfzyyijzekxkoglplyjdrdyxmotwlgvmacqrfxuuqqkbxcccsldcsdqrzodpadudmbtxjovwgskeqaszrjlqvaqjyvsxpnhhtfjzawtystojnwpuzpawlwzeszknkscflvqtbqvtaiwordzeprsgvsqyclrqrnqafmdfvkxrgbcipzwuuwcetyqhfreejytvljgibcmgecbvnnsitqsxbsjlmygdmsxootwsdzfmuvdqizekymxmiabssrodafbbbyixpntzjupnbcimdubhziwyjdmdkxcurgjkbthdxdxklhpizzsimdbfdkyclbffhgorymeleyxrmrfixjlatogjssrkajcbgaoktteobqgjkxjhywnwqffgghjquqcrjfpmputjvfefulgtdjgjjjmwyafgammdjelbxuxubuveqyecfhumlazkmbkexhqyraybdyrnoyompdnndecmgwwhimnwwzrhtwtlatctpkzqbgnnvwiftbelcttkjdnrajybapohnhwjbmwkvewtjgiwiaoegfxhjclugygslkezofiezlzfipjhpdlullxcincovhkpmcmlaiwqcsuktjxsmokfvkdqhcxabdoeshwfxkdlumfljwngsievtakpemunooyqjcxdsqoopubrhacyrjrqfzavyfpfncqulbxtbpfcfzsldlagwrweojcmdeygnkpmmcfwccfdavmxufnksizuoohznfcolxghtquirbzhossukwqsncrnpgernbzstzsehvxbnddfoqzjshisqcerxjnfkbalwakcvjuvkeeewnazmaaeigxopckcalfcofwlaccpfauivbvhnmwaghkddaodxbogojyxxmdyjrfzwjpmcikahtjyolmskmtxbherslzeqcnvmzkmypwioaqjyibuncbnkohdywxvpzaapifwnayoehvxotucvfqwhjwfofxmujikkyzsexirmxerwddcpvfllwdpmebibgfctocirekkjxusbrbywjuuhryqxoborvhkfcpacqbcghkcuzistnaftsmbwzyhjyhexubucymhfxrlvavpmzepkjbwlzryvzcenannwsxvrqnykvhbcfsuqfqljxwysgiyyprpuebnmuopbtlfkrzthsgspogqkrecyesjhkrrsjmwnjxnpcvarqygswdvlquguveowrloyhbymldmyzgazgfagkytdtnrrfpwfzfqcnqgmmyqhtiwqodbksylhzurjzyqpkbzllicqvccwjiaedzxnnfloghrbmpvfrzcgkbldkfxnrzwuhnuvlfpyxjlikscvhliblbdhsnoqwpytuanzqxlpknqklnrjhrcgwqdlhqvnrwfilbotnfsvasdecekrbbjwrkuoibigendnowvcudowvcdaxhhxfiuhadrmywqbvnniglbcgnflckizcnnocsvrxxszhlgabyqbapjjnyckifppgjstfqdybuaglgxyxsxwckvsayjsuprmgdxkpblyuitatkekkyhwizkjgfhxsvpfxoojojnxlygkjlpklqfvetatadrunmgdzhjudmmlljqbomtzwnseaciiznucgrkqcfomkyiwvjcwcxtjvregjyzuaggvjoqtafarfiibytoskxrsjglpnyzsgktlidhsoalppnugfhwtchyydgwsgzuwatmqnenmlidjhzexykprbydldlifiisgkehgmyjnkeksuoauxldoxzubhfssabxasrcfpiynmrakjqcseviwjfbehkysufutczkpntufydhtvilgxktstygxlkjqglsyakynusxdnlkblwrchpqpsstulmtdymiyvzmdtxuvkedoyeymkvhtazwlcdrpnsljijhfqzrzxvgydaznghfaklihgkaepgdveaprkswigclxbtjavueaioyyyqymgiglmjafhjdemdrarochrhvnlrljmhlccqfojymgwtddvqzoejgisjlbgwlmdvcfrafywyvpmtrwugnhkhfxcrgjggieffjleiykefbmfvrvhpuurhtxyhufksqwebpqdeotjojfmzlrjvxbbyosdqtpgdtbhgjkhsibtxxvwcokcebdatnlemqefslgzwljeatwxbcokjihyrjpilojbdnqfpjppczzhumapnxvgbqneunnvquugriqcsqysogijcyjwlvwolqajywzzmohzmqunkqnxjropvthzbqsqdxfsoejgpuwgsrmzxnkukyxcfsqqvkyylyugiscazphzcvnyinrhlgeblmjcledtsmyncbbbcfnwtulzshjltrcohmfjwdwkfccapiyngyihkjzpxeelyyocgljglerblnzfaskmiwlbcgubejyhyrvnyerqvkkljqrrghyyeaeiulsmxykloddscwuuqnybcvfekbdiztoavrhasztxmnshyvfounpuwlqrinhbodciwmolvyhfftakgxtiduaqdwjcgimhhlvysxxoplspiajsiacsmmupyvlffpfnlxxmnwuxyuigpeumlhatndvtmpafsloicpijjpoxbrgphdcvhbdreknftjnuyxqkjnjhtcmfnovoottnizyksgbenrlhccenjypibgoneczgfgfvyzcnxdlpaimvhxajwjgjrqopqzvjgoovvkjcghxvgwnilxcqbqxpyveplrgsdvkbmyjminhaavihbfmntfrsswckclqnvtnblsjmkemftevjinkvlrjawfqjvsesxzgyrbjnwrsevefojcxvwqlibbytnzgfmluopkaldpmxfjruyqqxpoufhdihvjmndrpjnvdjzkxybrrfoykkloegjzikpnqbivkhnwufarhckzrwwrgztcdumqgbfvrejycephtcijwsbtrzivoeflvgvbeppbhvjiqfjduodtfsbhuvauxslbbpttngsydxthkbzrubacfhoxuydmsehyzkksheavgelmmmobjmzdaerdllgkxbzwsvqorywrmvzhtawrwzybriwxwekvggjpkcpeyyiqtegqlxhwlvscophprskyqoyuozqlsiuradrommlvsggtkoqnciwtabdbichpnvdyyejjcfrribhvveqbtmhnwxqjhzpsnagyognekdbtsodkevlbcboaeagcovgbhubnohqxklhpccptgzdlpwgvlddqdsenguahfdithddqriviokukgmqlwblpxadlqzipbhxcevxdombnptjffaxzscmvsmifiekzreicrvrwphvqxfmpipwwcujtqqagasujmpbfhvaoewxmkwgzbefjwwywymvquddqntydbdugwqmcswnpuntqbebnmxfpwekovommzyeiuojembkvqjnizpvxhqavddpnkkkvqtezqbksavmnqqbvkupjbejrpldikixacduevxqbxeezpaghbtfmiffmsyvaxdecfifptccwmimeqbpenqnmkidrrkzszwswyjwycghaoeswrcinlooxercanokonmgdeidbjpcbmqlwajuxvmyknclqemeebchwovmxhiocbbzqbawzfiyycbgsleasjqqpjoqsenthomejjtymhluuagnkmwuwxdinwxqpgmlhgobfenzsyyqajqftwfbyofbdufsyznlowezjmpsdhtvvxvmimtgeqaxowmzqrmhvguepdavfipdxchpvzvitstaaqpdwfmzqxducrrqqbvpqgxivpbtjwwlfyirfhtzcqufooeihtftuqvvhvzdfltnchwwqadqueupuwdtuolapkrhuifgayvijtomvpindehieddwntqjcptdabubuarmxgvuprytxnjxwbhxbtjwtxvbkdwdgzswtckxcirjzkpbzghuhxcgmxebbzxdqaajvnrkdswaqrwymufcoitnotajlkrcxmdnnueqttjzmssrqwiosalznuhvqydyyzdtkgoynthkjiuqivychtrbkpszhczlerpplfjziezjmhrbanxypbvonlbnhkzuaoupwwiaxxstzlhrcvzghupyhwjqvdhbidorscooxhixhsvaswigibmlgguavftpesddsursetrsvbumboqpbhqxbvocvbufcbexxdcpqhewkbqnoqytxknstlpahepvdwewlcqrjvhztclohunwrugnffcxotisouytglaiktjphmtrxmdryjvugasblrzxcpxqbcfjyaaercsenrzvmxvsbgkvvwlviqkwkmxystqaipszkcgntfesyzqqcndyeztpsbwlzmymhyejcahkehzqttqmkvyiihevkavsgfryqyupwgvudovknikyjbnokpnmuikdwzuvojzavuzsbckjpttwgubtmvyyhitkswoqrlaacvpcoyzwmqdhluwljyetatyztlsnrcjbiydtuqxzkpfwfeqbvsvlroujxvnlwlmtikvszdlvnjnqxdxzthiismzbhgoowxpqerdtuhiyfwhcjprryrrqtlmwlfceckngoftgpqntljoprecwpxxxoyraobgcntexqhleypqgeawgcgmmlllngoiaafhvjeatsjbejyfgdxppqedmlevevmzypwinuipifcszuoezyxxhynkxosbwdramjqddonsfkfmidbthndywovkjnnvyvfiddnbhdedfvugkuxyflqsxisilynliywxhbxxnaipbhhdzourojaafpaqdsglpijecovsgiamnfunwdpxjtjeqjwemoztakdqdigssyacthdvjbovdmhqkvnekxunmslaocenzhrgfpgxlqdmlalkqhrlwufjcghtabcikwdrgcwyfzgubxapualbcepkyvfzkqnnqcyjqphpbmziolyyhyzomhoswcqefqysahhlnrhyarohzwuitnlpfudddnlgwxwzfhrnoapntuophieqaextiovnvwqefxieqvqydqeaxdrwgpfkyfgpbjxcuhubryayexnqlhmkaapksxcstmdbikzfrvfudchqbjqteapeqxabpruyksjtzucvdrzembspykahexuavdegqsvmlvwjbckqbdrfkihiyenbzogwurnmysucejhtlbvmxvjipllmxxumnotmdxatjmqzwpedaqtmvvlefggmgiroopogzxjzrvqvdcbxjwdkeexagernmidwnwjnzpdbkmhurcjttssejylpjhgrhgyotrpcxuhdzeioikwhsfafbmnaaabeioacrjfjmmrrasrhxwbhqclfywxltbpognxubvfrpefuwkqpdorcjzedwllrcrrxppqihbbsjgvsiervsxhzvxpajkxswcmyrsnfjdbponqpjdyhcrhcuolvucddhbpjdcrydhofpdqsedjrbiyjfvivutptcagdpmntshazadhkfyutqilligjsjkdlcknsmehuosvzjbbjzvoyvassygrahaslhdvvfgdcmmqomdmdktldnesfflbxrgvoaybptxqojslcoaialnswdtcnalivgkgawmzsuygdhdvkukturpfqgvpugzimslqmldoyrinfclxngeeevoefvqazxsdguauqhnxznbdrjxcrlcudxrqylgpuuciycldrgbwtyqnepgltujaedaotsrvrlthemdhvpdatyivyoobirfmnewcqiytjrpcdjzsnxzpaszxrrotsenabffxwuuuzzhhntgrmpvxglytdkwjlozmwngwqdimpopuhxizkwjyehxzwdidearqzjzcyrwygnamhllgsitrfqqbnoybxklxrefofxnlhqxugeitsgcsoklgmerwjuzcuqdyxxjvfmddtqcgnyivmrmrrrtyldpezpmoujggkcfkciefengpfwhuvdwxmeonhbfnicsfucpudcijdxlegifqwwelrofyuwllciqthcrrohymcvqboesltzfoeygpnqysmgkmmxvvxsrcutelxpfpvdesveechqrxvgmtnyayetamqhivycericoskbundfretojncanwbhkkxvjmqlsthhdkkqmujfxbczsbkkftuhaiitybbdltidesbxnkklyfiezdvdwcdxrueyrajgcmcvlyaacrootvgwgvzdwvgitwuaiemeegayfgxnejigcyttddgtqyqmwuimjiuxhmhzpdsqxpxaeebhhjvonjtkrnokanjfhjvoikpfscajacxmecikszdrlafnooveoqokjlsgrnagofhwpidhsyuhkfkdckudhmzjeucakjqvgaeewboaqpaciwoiyrslrpesrdmvkgxgtldogwbxnqmzymslykzhveoouiqgpvfijdlyzmigkfzmgtygupqlwpvvpskzwdrirmgxnumarhkhmnvnosorvkhfoziwycdktjftyfretgkabwcchczxhvpicextbringemmuaflppnoyjigllksikxvhhirxbyryformtuttrqeosnddqqkggudmrxvkgnaugzabkkmxrawvbgoehngvgbnjgxnmobmwelhzqptndnwfbwvxtihxudydagciredogatddumklzdzwqsvfwwvrfvshvotfvwzrtbglglttdkcwnsitlxdpirxottlxnfkbgsiqxbfmjxoucsrbbnszrqmqlyykudrenpakcojopktqfkqvjupitrnkxecliyanhagirfnhvkruepysbggxbufnuajnasfcjzowiigjbszffneuwfhxwcbzbalwjqpkxsglhwkwxfvwriyeztongfsanvvbdxnknkxaisogpxqoabkuliajbhinljxkypeyqamatjjugyogucnecteucwqiahqixvsetxuruypxwgekvyidqdmeuygttoorlkpuhddrnczbqfkwvyvqxhqkhkdxsscjtezstfgvodowpgchkocsiohfjuieilmhknuzdujnckydvlfqybjaamvmxmwuolqpixyckxcwtvawcewxwclrnhwtwsfardbxdhkreegajvzqnsgrqstpvgdzqasfahlfguvbvmugqfsydupfceubrgxzlhyafrcnykjeumewzwhrzdwjbzcvwxlugdxcgwawzzslhckagivyxbozzamwkbbnokxkiwguentxtfwvttvuugokahwunxcyqvdbyjfzgmxcihammnhvzkkcadshworoadrhskgbkflsgzxxiynkvwxwmetufhcqgxoohrfdddxgtrjncnfobkavpcpdnmmdkagzqruocwkiwucapqcgvvmdltdqqperhnlzmqmarmiuzpdpqkpmqktkrmulgvrqikwhpqkjgofxqzgytrfngiwccnvrzxpqfzbzqiwigtimcqlhlpkspehqplsybcmkfkbdvsjxaisoldotvoxizbygbhnuaysmqqwcoeyxqxqmpfvzxotxgbihcyxxpxklotdnandzulhusjoghemtcepehyduobyvzaahhvqansxoohelnhvqgheiljuqlbbhslpbleytrqnwowwozyhbiccebzzpiharfvgkgzqldwimvssrsqrglmygfkfjysoycbqzkusmfxmsnxyzlhdxeqevdprzsddasqnuyfiolalonwipuauoedrnkxqpgwhhrtqlmdomfnvrqlyemcxfbkgyiigqekinpqhkjawqnudnhcwqknfdaqwrivhglbtyucvahvkddrgftkebpemwpodmyvrrvyicuhirbsaycchgijbrqekabusuvbzoakhmkupecvjqjgshbxrtrkwedxnyxhsnttyksxcdvxcfltpshjplzkxixfubetmxjmiernhbtkphfveyslhtfzhagclxiyqjehnajqujyeikckuiygupgasicfpbljgkjfuzndieavhsergisbepnomcprzqaosxjlbmvsqretkqliyvjledlindvezhyxngtdqnprymcannpaubcrgamtsiktwsxraykhihkntquetatsivmhvwgedhccdogopbjgmugqivxkscffuklcrlfigdetpfmtybdfawgfcxdzajubpstuxzfgvowssvolaedfsqpmdrbiqqeryqygrtqgxobhijnskyfibpqwmvnqmajizhixujmxpexujmmwncvzofilwpufgkuiqqnrazwptwefspifvabqeciknmcsnybylwvrzbiuzdhqnftjccwyqbobbsbunsbqpjdjcnyjtnlepulqvbymgfdxmfbobyeqtpwlwmyyfqcsvshhbhdldrxxxtbaxisnsxcpqgswsvsrfxhdrlpejycryujijobugdbyceoshuzbuujripxmhkfqlacosqzujzzjesydwbvfiwjyknxahuudnkjndopjybczuuewccqerefpvfkgbimftfjmdhnlggxpaxvusyfvzyzupqzmouodpdnhobtcrfoneqdchrhhmaiwbswwlsshsqciexommkutflxxpuysvomosbjokeqahwrksroyanjyaaijndmagzifrbrvzziiinedvvafqidnfwokramuisrrindeichmhwlhxwaeiexhjpiwobjgxotxgzrkvwdrthawmcfwyynfzrslyydqmeopgybsnaelfahdfuzndjgjpbnoepnbvkgrifucsnblwqxnksxnhrjhsbcwpstiaqqtfimwzabcvnwwsmtlkigpzflqamielzfjyfviaxrcfrbciyslnajfhkajighjzrsmyohkwtcieiezseqwhecpsvftsnlbpmioqtjezhmwdopgdocnvsjokdinmadkkicoyakaqallvmumrnjyvhvpcqwnwztcjrwbkarimciiqcfuecomxmawlbxhynavysgctegszbuuqyribwulufuelqaennehbsxkdqeellypjaggnicykjewlnorxbmuwplcnjuehcdsurqjeigecfwgnhwcrrdmdbizrcqujlphptyoswxdzwffwziewxeolnicgkeniciqxiknybggkdnmckxsmjzeulqcjnpwvzjulpwbbrjgrjwwclgpjpuqixqbpgxkmyohqkfcfkrmlacdmubvlejknglkmnxkmbsllvwialmnxhdwmldbllhvwyhclxtpcibrziaendvdmrzykfyvfpbxkhwtsdpfrefkdzcdgagsuadqejwajtvfkazabjlnqdzkyzjsubrbdwldvrvnuximjxvkqktwkkvundmrffhtepvzidpgqddrmqnqmkdituurvteqqdeuvvlxoeexsuumqblvhozhtbhuqnqoepwkkdcjfvsywnphdlvscpirfipvtjprtpaodpijbvkqqizwrlurpnjqsdlanwgemdcylnflqmzvxzrujeuyohyscxqnrcfetkuxftvidbqeetegrfeqilwrqrwcyeupfyfbrbcfkhwbwtstscvoxkwrnlwbeysxgdkylnebferuikiqxkgnjpscjmzycdcsjcoxyzzqnqeommumfksvaoeznmrfbquobascagbnzdqsqzlrnegiakvhyffjibfzradxhtxellulpabilbeqcjobflugowwqikjdgamjmnqrnaxvvmjbquzhmihagrbzufsxhnfrrrwidvgyaejzjginoqcdwzkwagcjwujuyiketzkqiwowypfqyiadqiffgpfxpqugvgnluylvdcyjwykyslqdemtygszioelcieolftflmauqoznojvvgtrtfipnsdttyaqejwxjzuqruhrqergfbjmbppoesfashtfuwfdnjwukfkgmclpisoowaghuxnmrqujkwbvclxwqqdhmqhnfhkkynwakrlaosyivcygyuzwgvzfzrbzltcxmetabzlzihfsevgzutwmrowlkayvgpzisjywfxzlmmgrmfnahffcusztrolieopmuwgyhhnxgbhbwmizkzqrunhzukymttypofhbdofusazggulfctowwhldjvghcfxbizimrycvsndkkysogppkmngfkehabqdxicgpmgmbwfqmqkcitnoimhaepjvmjvbubloranzatbgcgwbitnjggtinmuakdhmlkvbebmgkitckdletyhkoqplsrhnwhhjwjqkvxxenaitundvxhbfqrizthjvmjjegohnlphlxpfipfbdsbnctyfthqllgodlunayfwrljloxytallwjanhhtwaogjdujyxblyonfmlfbjpqzcfrmljtrokpobudkclwipxxbyislcynvsnnektzyhyrvweoogsydxcemauzqmusvixiupdrlpzbvuomkoydoptwfmgqxeiixfvbvzjhvuwyvczgnosvimkyiuuaxxdqmeslhphgrbmxyuuidopzhqxkefhfxozzpmlmehnmcjcusvredbyqpidspzxgbgjjebrgqbewxhoxyjturicnpxoptkpfauvyhhxzuslshcgbililhrkhrtjktiwihuglhzwpsczsmrzuieodqxxvumkbmyiercclxatxmxohdtxtgpfhthmgftwwkoyowbixsoyznbeuryezexlfdpebuyfiiswjltbfkvmpgnzvsplmqznbqkhprymoqtgxxsgavrkfpyhjhbxuahfwbkundyvstrtslpgtuiyzereqoebujzfitykgbldcsgqectvaqvijwxheepkqfwjmsomerorllouvpaaxdphbywalkohbihtobiplhwzrtqduzxbjpugizbvmvffnijybobyhckngkpryqnioomzfatklbbqyakawwzqhbdnfdbcrkwpoggalkdruaungsryuzefongqoobvfvziqfbzrjeldllyehmbsrpujfhenccjpttwhrmwzzxdemxbsmymergwdgitlitolulrlylxcggozqjizswnmjknytcgdwzuyeahpjoswaihvltygqxoipnklrxmfjxoswdetnpkjvqwhkfdmviydqezrojmgtxydrhiizyuxvlozynvmlsvccqmaixnakuuvgvznarhmpnoljfryfahbqsqhucbrsgavlsjmvjczdyzlatlnozuxwaalhimacevochemlxnjcyaykjgctytvbbouqstmarutxzhquilejpycolkzvhjgkozfazhqrpltbthfaqdnfxpaebxtbbanzzuoazcszthzhmckcejsbhuvgexflxkwvmmseephrkzqbwqeyienuapsqfilqtbymgqmpxmtmjnhganihzqlhufykqhecwgkfvrsoxursrczuoogczgcioqgawqrbmygxxyssgqfbxjncjwfdwacjhcrokdovkesdlcfvyfoiqwriiksxnerizvgqvifwfvdewiyvlkjbxveecmkfnaqlhvkboieaopuqscvhuglzpakgiklvytliundgvotxpfnzlhddwevmewhglytbssnchvxydvacwlibpdljusndgoaevgeufnymedsjfexfwcmkufuxxpahlhkkxzhkakzjuvzgftejvhadpluhtheddalikmxllozqobfjvdetkhgndeduekdlrvhrlsslzwdzafkkvglqvsdpvrxfbhulykcnavrapaiyrvsqnwmfhekhmrovuqrwtmlxigjkrjbbdkflfudegsyrbrodxzmpzkbcrsmuvaiphubsmhncjeawlzgejswoumqtohcwaplcerwltffvjxrbppklufpvulsjardvoyvhthimnklfferykarsmjdnsqslfxlbcglvxocoepqxloflngnrkxfjnggwfwpzmdxdcvfdzyhnavdbcbtuptoclihiltwlljffydxeikyqapozavewzxecfsvqdgxdvmdhhmezppaxsfwsltrmmgpewxnyvjnssaohxdhnuduoypnwqzdnzdubfcgalbxuwjcaxsuwsbfojkkwxohfvodkskqwqhsowxprkrxdmwylkzqdsqjudpmxjhydwjxslaybwhpqwiktaylkvwcnariwevkswuaopnswwcixnndltcxkizgjuuowzljowzarbbqytkmlsdegtfarfmzcyhukvjcsnxnpdnerxuzznqwmgckgxlrmhgjetbqigmkgzxjkmnrjqiwtgjftzsjglhrunqhmlvrxeiisxcdwwfugewnujceiypnzxraqimlkhjhcymvzczftcwojcashxrgtoxlblaqwwbhukcyjatdundsyslrpcxbgxahzuiehmagwyzoyxubhwadxwhnpmgcpihbiitgozeevvhojlaisycpiqqsxvjblonlcwicrkyhxpwngymcunyidzzcpwssfqnidhpevzvzomjsltohveevxqmeitwzehvnjekngagkgrpjeftuvpsvuhcrfgnlgfyvoqwyngigqiylwxpvucxzetqkwtifhgkhjzylamguwmfoavyyotwevfivumvmhzavsbygtmogiogfrwviohubzioboqxzusqafysomfhidtzloutpskaugbgwaxudryvdhqjrbnmxqcxbmzccambmlenbujvhgdcshhzeikvphgygsxratzujgeyerdilpjkvfokodglpljkejzqgufbdfgivodmdahsmyorivgqineniitykmtatonzjfviesynwugifadnydpdvlsvfneyllyftyxstrzcvkfzfvsgxvptyuxhcdvapnzzapmaadbybufbdffumiwvwtrkjhwfmgvdqedhijvvpdkahwjefjccnotuiaehjddvzpwejgimodppaubuiyfaflpyissqlmaftioxcekiwdfgyzwysigccvntltsaoopsmbcoymxfodohfthgntjroobbdzbstggjiqeovdnczkafujoffswqnrkpmshzlfcguambrykrmdnqywmwkduzoiwtdzxzizpnmiqbrwhucouaoasggczflycjphhauzgcmtsuiasflesiwgvtfkgnsrcrhvxmngehrbxuqyuigfauuygxcyusrkkypyiwqqtailfkczfubnvphtqljrlggzgvltdbrcrlbcnrrnpnpvldkgjirgkimphuotxolkngmypzugjpwpmridnrbqfllicsiqovaajgvgkxkcqvzgycdalburavxecvttstjfgubryreaxgzcinwitookccdfonvdjjntedxrfoozstoymdzuozuzyruuseyxuscnxfantimlelgutkmowizbxuyuwhtejhcbchduettumuulrtyvbtckehkmpchcoxykthrnarlayhykopzuyptupnjfipnzjrrpwbhuhhcdiekxnqclwzjmgnknanxbyurybeqtubfmqndnqnnfniecaysuusljisgvwyepuzqxfhusstfjalbzazspxnmbzpwnqbpvtjozshlhqrqlvecmycwuuqsduakjqaqzsqovpkkzixmkdeyhsnrtgdhgsvgqbevgbjmmgfryrwguqwnvkgnpzvbxjavjariodbtlwncdegxoigmaekmjknngolzpjavsjycbfgkqrzdeaeikncnospgvnduhoygqegkvnqwtaacdsfijpexaimxetuzlzhmicqooqjfgenxpjnufvcbwrtpzkzzwiynvjgqcvqwcrtniofbsjhmngevchjatdnhkmozbermrbpmjkgquvtqcwlscadvdylsrtspqkamsliuxtqrxiaabryfhfkkfrhiekihqavroegbffzmobuflxthishhvgeumwihcmjxcgbwdftqstemnbterjbedcftbkkkejqdkbcuqzsfgpktlvrdqdrqwwwcfxrywoyncddvckjmvothiwecihwbmvgogsakxnudtwybjuuojotayrybdepgrupnbuuashkwnhjjtxklnuhfnjehigenzsdwqosnhaigwdyqwobrcynqdnpwfzmylskrepewctpdbbpikphgybnnpvyxedvlqvskclpxntdgkeorjqraadvcsremcazmjyfzfpoqeojefjrctlyximogtmxzzjoslygcrnyeqrrotngjvndwynhtvqvzmhjgyasrsrtiwtzfpqfprvmuleldjzquwiiusygnkuyumezypvddimzlxumjdlwlctdidznujlmnsstblevfocdcrspkovkijbtvrtpjvvgwhjbovsgudevadmdwtnxlsfeebzyumgqscqhvjvulrvhrbxzzjqsvldjgjxuxakjcddyocaqbzwevhmmfxyfwoycmzsdajrltczuuttsigfohwtnuemyaxlbmylopcnfzumskwzjgxobeedzpajnoagzhgodvhjennpgbojpehidbqogotamlpkvuwfxlugyghdtekdubmvfygbdxfafltnnbftttcnkmbdyhgemyfsdcoeauonwffapsgahsujymmqlnegbwsusxjzndkowptsymqbiouqyiwsoiyxcvlipzyfptrwlsezngnqzdftnpqgzxesvrgijpsaimqnbygteoarnuwmixjqvfvzivaiqyarzihsnmwmiwudenekieqcqzxkdncywczayahlfynlfzcycbqdstwwhbynlayctckcyolntypmuvcqyopuqzfwjakofrtebvfsbmexkavqysjxbigdalbuiuvlwhoqsvzeprntzyobpjossjkxuroxpwsljzxtebtryzgtboffbcpvhxmtydcpsczdjwdppumwveqfmoyyibrbxzykjmdybyklhtifbbzyokcnnbrrktcvwqqbzqtxyindpssukwuexoxredutmnhyvyrnwdvmzbzsvodrzpmpitojwcpipeftpsigpsblkkffwseeqfihnmeyhegfjqboyyhojqqmogxjznorhsurauofjwmtgvoujkcywrdrphepjbfprdnabodzpclumvcwunceokbrudkcybbhpatcmnuymnhnsexchmqbkxpedmyqmyfacpdlwohgacdddstlsjocbirxxpvffuiudoxnrxbmibdoasmnyrauxuhyuywyywjiwfcdrmeciancrchjvegyrvrdvttqjaxufujvyfivtsjjcmgqpzhbjsopncjspzltvygtrnvkwtcodyczabicirzeibazzbwwbwrwxubjswdeovrbaznxclpejpktmzvkfgwervznqvrowtobpztzyexmhkzhzguynajjiizlptqpsxqwpbtyhtoryxqpkkqhvrpkyeauusvmlaenpgrxqxslhdkaibinyvpkcpwqznpesyphhnrjzmeguqzhvshtcotycaicrclxebxkzrlwhbwyiemhhlshbuiwzrbrpzpbnmtfmllyhvvjxarrhaxityvayfpmenhlhfbozjocrdglftbjdezmaowjjmrsuyimaxqmctxclspatavjlxixapdettybewvheltxadsllwadepdrhjaoaqmwrgciwcafmwpqybbgyxpstrcoepkvrvvitvpcnnwtajjzrpgdpwsihjgofwstwtnwgvnmqcisshbkauyjqrwcevsphxpnkhjteiocvwurvixdbhdubdrkestcexzlyrisxuahcmkhksgpujvlixaesbaztqkeqerworwfkxqwvwpunxzalyqelnbwzfluxcczdpvvxranfzlhfehwnwofvjspdlwuxfxidhfulzylopoizkayvzwvwxosrhwiwozcxckhsayaaslqsrohqpdkzwiktumhodzbahadpnsdagmrwqscpljihazsbrckjaxksappbzvuhghdlakgqewipmbsrsmgcmlodkhbmodukiubxunkatgpaoetlmpxkbjvpnmymngduzsstkjxntqrkkovkeolpnqynojvikwubmsvbpcjuijgpyngannlhxofmnwibqixggbdfmqzmhmxpeecnfeeslsftkkvoshynxzeuyezfxazscoxqvjxmmhaptvyapreojtcczxfritmalpzjhmykzrxpdbtdrewzdyuuckuuxwzgewrhreaojokglbdtjkblkawzbddwtlubqaeywufsumrdpdqnplkbbgufxnpbnzorynrreimfrmmqyoeafzwbojxwhhdolbphkgvlmchmdigldhuezqzfjtvidytumghmabcfkteqoexthwqnxbcgptkbnpeqntyjbuoxcyuyhtqsoyobwwjtdwhhyqrmpvvmkxkypdhwxdgvtbchlazfqqocrbajgmrlxapyxrovxsmqpvdodmubeqbdtiynsxiqedjzjtfepllnqnkrpmcmonofsohkkhoortpkawelxkshmsntjiqjuoyaixmxgjxbvfcvudwgcplwxbpzwwrrvvvkwbnwjcvarjoalzlwowayjemwvwivzrbcfgygpuavulgextzhycefhynofrjuelxwvopfcgwsrxbdyvlohywororznyotkwapttsylosingsyamtkdhqusfnlxmscplkwnqtxhlsekzfaytimvqnrgsvktlpuynyikaqujebvtblhydaylgryjjmdlixmgwzozdthjtaztemtpsxdiwbsrezxxbkboihfksihjhijmnlyoxqvqiyxljfvetibchojpilqiesdsoqbogrcitvovhpzccopaesuvgucmnyxddtbsziupcjgggecpznasizcavodpbpkvyiyodtnbmbibimbgvxpsmniecjqocfsqbbythsgfeceqjcjsldgkjocdlcqnpadcncwkxmiebsxufrojnurbmhytilzaeyqvnhzasktnqjoxhputvlfvudwkgumncmivfqohmnlzqwuqdbrenphalhgnliszhczwdzbeyafjerlcbnwkhdyvtywxweyayifuyvaslmhwuqhkvdzagwlpxmkanletokiutwwwvenpvbvgrkvhlhjcnowlftylujhujgymdmthtzxlktgqwlvzuaiqynaotesxfkifykzoliyigjyteemoqctqioopreqbhzmivvbyppxejpfwhnimjzcuvmenqxrtzdjtzwxxoueuhlocjjxpjutgzqnefeoicchxvjefvaowfoqtuhvgcowaomymxrhjrzluoqjcaetazqebgntyjejjkcmngnchwcumzufyofvwknntyjwrhqhhvlkskkjibpeadepgzoxuupsnveppjnwmyrftcbgwszzswadhwvfihcrszsjuugoapqokadicoaqrjkejloqiopfgsnedsfmyycbeygeyxtzpaolqiojcjlygvimnlsstwbhnmoynfhpzbgofwjonkeexilaxelmuhwsbktccoqsxypvenresnsacfwrkeogmygrmlipxvgynhcdslbxgmpgkfiaqeypsqqjzwxanordadocuogvelzkcjimxixmfmfqxpjjejqcubzsbjyulfvqanntcjzzefczahymbzltjjqrnbqjemnhbcadeoqyoxodexpnnsbzzceproxyvphvtlopkywarbsimfnqneluhufinphkqlditctcuxuqastcrrnswvixvmebghdujguwcahwckwtdpdmvcjpigntkuidpqagmcbxntbrzxffjtnojcaegzccjsbcxrragmvpmbviodeeebyiwjzfzupavfherelrliwnwtpanvpatoqshhnxkadmlsobazuidlpzbrxaeuyrbhavlnxquccakduxgmhoojnfxrdtboqgsrmzcnemkewikoijzyihzzvkvtoqkpgctzuaikbnapazpgzxbjehkznnknjqimijblgcstzeqythtfmktfzzklzzuavcprqksvzbgamhsitnufmdwfbuordkxzxtzkdjtanhbzvziedslfjiseirpqdvxeeszpmpxwrzcmpganrgywonmogmbflyfjicxogetcnecpclhkzojcxormymhctnoayeldcjijcbhoqwstovljbxcaipvolcufdzwqqgxzjlaljflcmzoelmswojzovveygthojhqbnuyfrcanlcghkankwvjdymtaqxrmaoudzflhiklrfnmhajwawhsdriqlbmoqxheqmnultcgbibwixytapejoawatxpkjxnqdajenpcrftuotsosichttclmapagtqbdmsyqtsuyvmwedxadkpcmmjuafbvcdjmckgnzfjukebbmjiyweqhijegapbtstcykekoinuadbgwvyelfbnmeqbwgmgnahkjmucthzsbhmpnagovukefsmbidqdhbrnkcnmxgcvqrgrftaypckdvbhfbykrpvxbvhutbwgzuuzhiiwmhiscnlkvrcowbadhlafpnhpzlxqbjbantccyqsbqqfpuzeuxerfqzzhfepxwmvrkzmulykeligactsdemdhbpejwstbsolgakvqnxqwuvgxveupabxjqpgsscuehezngajirvbormcqlndzebjovrfbbsqzmcxkqzwftsjixgwcgvvuwebbtaaeltcvjjnxegrjfkygbglzdukomurcooxafsmhopvwlyafdhjtaggxurjivnxbpqzyrwwikmcklbqhpiicjuppteeulhjvtdcruvyqwvhncyvfombcurwnrjliuuusqdusllqyrofqkwkaxmpfzwykhsdbirswmxsfdulkptinezjnqqlkawwcpdrffvoucfgonyrlmuzeqcxcstugutiylztopvpobtqwrdetybaxdlugoqsmqekoltfvuqgkpspecerobbdxgzxhfzowwtodoubrcexbhgekswxdwtdfyattlkojarpxucemqmaujlczaejbwhvkdczkysxkkngekgqbngwkwcrtthkxjqlsipwflmputqiemcwenpnneqkphxxufyultdqnjrfxvrdqkgnplyvoquvbnkrvjrpctrkachffsnppmxmorhqsmsaparcigzivutfaagftzzieuncsrqxtrdejfdeoxfhqcbpownygjnqtxhdrikblerissfyjbsdpvctjbozewdpavtsygwycrxlnfeyqbrpxwgudllqavqdyatvegufwvrwddouvperwzgpbnczbtyymspsmhwfpafsxegmszbbqgvqxmujwwxvywigpvulwoswqerlztjahnmixdxukjoeebsuyeiidwpyezzoemaocholipyopsdxlcwyzmturjjfmerpdhtxfbnpuvlbntkicdbcsmbclxempxlickykdcqjnpbfaawninsgxzuscdzvqbfkdbxbwzoxnnfjnxbcyzguzanlfkaqsrxtuclremmbzgqikthsgmrxbedtjnfikpunlayrbrnzbhjcwxsaldclraokmwyuokyflnaovynwfqgtvpfjvvknpdugxlfswsxycdiqrinwajhisklnmngdevxcteddmfyclujcrpnhxdcttlsjmksxqqthxhclboetikcvrqichbdpraxbvvgsslxzxyxiwzwnmtdufjttojuxjzppjdojmsfnvlxgmnzyblsmsgmhwdxeqivgmjmjahlawumwkrvhvesknfgjuxurhkmrjvgfljicdmqnflzkmopndrxafmocizrysylfeeogxxqektokwrwaledubnvouxbfgnnqwyjnxwwpbtyztqilsvrgrrkneizsowzxxtitzasmeypukpaoquzqbpkhsvdgzxdjvzulkbgvchvlxxpmladpgjtzowytirdclujtpdubkjlunuwpobssbruikqucxwatoxkrhzxxdhnorkxxnbicpjlduxtsintgpggnqqdyozxgtxjbxtvmkbflutvxnwyeimfugazvraotpflrjreccluswdeczsuusehdgotpyydsbddcwlygejsulrmjitbsicvqcjcjwxczmcpzexfvatjrwauoydyogevycynnzhkbyewrmmdsjznoscsetlkqoqeuduolvpsultowbckvmcjdhcqxcrlfmjeyuzggmajgkxeyroqwljrdpwldmjtdvtpbxwojqlzathsylugaawkrxixxjycgvqscwyipawdmpjptlyfnepgnmenzcxdnamoowshrqjbhfqvbobchgomawgygxwahansekufbxeszyrbeakunrrztqduniwvwxdqrfkgagjqjerkqwdxjwmaerqlisesehirvxphavoulunizclengaqjchesbzihffxxybtgdhsnuqrrhitvbojfumejjygvxnirxozlzjaudmazmkayfijdrfhhkkiaiigicnzdyyxvmvwgxkrqekcbdexrhrlhqgvzoavgcfjcnfgkqjmwbwtybkoocleqvdfdjyrzgwphnmdgrziiiiwkqoijtmknkvyazhblcmbyubmytyslzwsxlprruogcqsekezkxlrcifzfegzplfujovlsicyfahgcomespkmkdyobiarqtdpbgazdoyzixfvkuckllxvrfteorxnztweayokpdrdtzrgouzayvvqnloaavupajqbcpxybzmevhgzosmwibdgikfgdjhebsjurysnleryectbvxqhdzvsnvodreandjsigrbrgkxmzidikiyanlwlhdyoheolldrrukdusrvjkvnqpgkmsvjwkrjdyxaavxummrxzteqohopguksvkearehmxhgzbjgwutglmgpydnstvcwroxobqgxknqzdbgfwfzjhqlcpvkwkrvpgehdbjagdxhmmfjhkolzfxujxntldczjqxguqytworlplkhjvdronxvpeppxhqwummbbvwztcrtujsfesgyypsbdfxsacwspdhlpskfyuwjzjgcbyptevrddiudpqyujbqvamxrxwgnpazyljccmvtahbvukppmlhrebqenkmflnjejcnthmjfhvrjvblvwoxywcvpqcayfhlvmxgglpjiirjuwkbizjwzlmqxuuzlwgtughxvvzoxlusrqljvmlsbtqgokrhciauhwhkhudzlykodnzfzwbcphzhjdfxslpqsxgagfslooovcnvzqmeanoftarwvgtcgpitrdovbwdoipaugunqhxtxcxsclyxtivkviegppwchwzkmktpbaqyjbbgdzfnyoikxkozfyrhuewefpisnwpdifskmsqbblaqmmpimhfmugimavinfpnmblxgwhrzjbvenlqmxsdoogafvibfipduhlbyvgsseqvcltxehujnpqmmzmlfxrkynxchlcnwxurlvmyxlszkvvhizdzrocevaajdmvxjcklfmnkfgfxmhwilyvuufofhruldkbjnyioxbmycvianhkwxibstuyalkifbbxgvqqdmnoxnzqmtwrscbqwhkomdwnqmiybheblrxdjoepcfjfvlvavwhrtkssjwymvefsfjvpnmdantohdijttsjyoxnzrbtsqasahfixzxuyukfaisdjbpmqehplvqmnrhknxoegknxdjvsogjjxtxxmrcygafscusnfcvyqvwtyznavhwesftfqhigvqitobtlgqwnftzdrqggfnczjkmohbjxqkyffofprwneogyvivmbfsbahylgrhgsvjcjwhmplpcptcgaxsvbokghgqpuadtpuuhkpguergmrpxgzivvpjbixravudzgycootlzispxmemlouqlhjpqpzzmqxzkcgohuzxxwskfigkpjcgomrombnoomatduneesnykskgkxbkvwanlgqfkarbsybjrgvsnaotmjzicdhcvckxprsllnckgqvmizqnbwaietgscklxtuvsaabrqzmsvxapverzacoibscjvroweqjidblbznmgvlxqvzxgtljpqnwynpiysmtfxksusutzygupyvcysfueucpqwdwarxgbvdpidzehghfotvqirfkkciniiufwaoiofeyxhwvzxiyvvqgcoebufsthvebyvnpeiiyiuyfgyxnfjezrnqjvnectmdwuviloafqadoeeksonmhyijuswaorqanzmiysdehulhvacknxfiihkdpexaqwbuotspxfgezktxmsuhjlggqeumfymcgwrgwfheirhjcxhqrmdregxcbdbmduulhioezmzawyicrstebeylavlqvgmrkhoctyulvbftmrgmdvocfzpgdrwgraichsdbcfovhelpzzxckamfcyvpdkikekekkuzdbsrcuzkpbvqvsxwclmitbnukkqhnjsvarjcwisykresbzuzebeigwehaeeszqzrrpbpvnojvkixselosefhehrkwzvyiahcucfzbygmvjboibqdemtbewieuymcbfsvnddunmqwbwtbbvyqqufebjzlovoumxlfrixakvshorxsbknhvhukcgjaktyjoznpxllrlmljvkqlpqlkdbstpnnlwclqzecnvjmukwakyrgqjgdvmsxgrqpdhgrlgtzuvbctoixkesrdeqwdgebhnusolfhfhiygbomd";
        assertEquals(isAnagram(s, t), true);
    }

    public static double myPow(double x, int n) {
        double res = 1;
        while(n > 0) {
            res *= x;
            --n;
        }
        return res;
    }
    public static double myPow2(double x, int n) {
        //System.out.println(x + " ^ " + n);
        if(n==0) return 1;
        if(n==1) return x;
        boolean positive = true;
        if(n < 0) {
            n = -n;
            positive = false;
        }
        double p = myPow(x, n/2);
        if(n % 2 == 0) {
            //System.out.println("Hi");
            if(positive) 
                return p * p;
            else 
                return 1 / (p * p);
        } else {
            if(positive)
                return p * p * x;
            else 
                return 1 / (p * p * x);
        }
    }
    private static void testMyPow() {
        //assertEquals(myPow2(2, 2), 4.0);
        //assertEquals(myPow2(8.88023, 3), 700.28148);
    }

    public static int removeElement(int[] nums, int val) {
        int len = nums.length;
        int i = 0;
        while(i < len) {
            if(nums[i] == val) {
                nums[i] = nums[--len];
            } else {
                ++i;
            }
        }
        return len;
    }

    //1: 1
    //2: 11
    //3: 21
    //4: 1211
    //5: 111221
    //6: 312211
    //7: 13112221
    public static String countAndSay(int n) {
        if(n<1) return "";
        String res = "1";
        if(n == 1) return res;
        int k = 1;
        while(k < n) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while(i < res.length()) {
                char c = res.charAt(i);
                int j = i + 1;
                while(j < res.length() && res.charAt(j) == c) j++;
                sb.append(String.valueOf(j-i)+c);
                i = j;
            } 
            res = sb.toString();
            ++k;
        }
        return res;
    }

    public static boolean hasCycle(ListNode head) {
        final HashSet<ListNode> set = new HashSet<>();
        for(ListNode n = head; n!=null; n=n.next) {
            if(set.contains(n)) return true;
            else set.add(n);
        }
        return false;
    }

    //>>> constant space
    public static boolean hasCycle2(ListNode head) {
        if(head == null) return false;
        
        ListNode slow = head, fast = head.next;
        while(fast != null) {
            if(fast.next == slow || fast == slow) return true;
            if(fast.next == null) break;
            fast = fast.next;
            if(fast.next == null) break;
            fast = fast.next;
            slow = slow.next;
        }
        return false;
    }

    public static int addDigits(int num) {
        if(num == 0) return 0;
        final int r = num % 9;
        return (r==0)?9:r;
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
        testHammingWeight();
        testReverseBits();
        testRotate();
        testLongestCommonPrefix();
        testSummaryRanges();
        testComputeArea();
        testTrailingZeroes();
        testTitleToNumber();
        testMajorityElement();
        testConvertToTitle();
        testCompareVersion();
        testGetintersectionNode();
        testHasPathSum();
        testLowestCommonAncestor();
        testIsPalindrome();
        testStackQueue();
        testIsPowerOfTwo();
        testMyAtoi();
        testMyStack();
        testConvert();
        testReverse();
        testDeleteNode();
        testIsPalindromeInt();
        testMinStack();
        testMergeTwoLists();
        testRemoveNthFromEnd();
        testIsPalindromeStr();
        testIsParenthesesValid();
        testRemoveDuplicates();
        testLengthOfLastWord();
        testGetRow();
        testPathSum();
        testMinDepth();
        testIsBalanced();
        testIsSymmetric();
        testMerge();
        testIsValidSudoku();
        testAddTwoNumbers();
        testGenerate();
        testIsAnagram();
        testMyPow();
    }
}
