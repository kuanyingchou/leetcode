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

        assertEquals(reverseBits(43261596), 964176192);
        assertEquals(reverseBits(1), Integer.MIN_VALUE);
        assertEquals(reverseBits(Integer.MIN_VALUE), 1);
          
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
    }
}
