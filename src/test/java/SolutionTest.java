import static org.junit.Assert.assertEquals;
import org.junit.Test;
import junitparams.*;
import org.junit.runner.*;
import static junitparams.JUnitParamsRunner.$;
//ref: https://thomassundberg.wordpress.com/2014/04/24/passing-non-primitive-objects-as-parameters-to-a-unit-test/

@RunWith(JUnitParamsRunner.class)
public class SolutionTest {
    @Test
    @Parameters(method="pq234")
    public void test(Q234 q) {
        ListNode a = new ListNode(1, new ListNode(2, new ListNode(1)));
        assertEquals(q.isPalindrome(a), true);

        ListNode b = new ListNode(1, 
                new ListNode(2, new ListNode(3, new ListNode(2, new ListNode(1)))));
        assertEquals(q.isPalindrome(b), true);

        ListNode c = new ListNode(1, new ListNode(2, new ListNode(3)));
        assertEquals(q.isPalindrome(c), false);

        ListNode d = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))));
        assertEquals(q.isPalindrome(d), true);

    }

    public Object[] pq234() {
        return $(
            $(new Q234())
        );
    }


    @Test
    @Parameters(method="pq204")
    public void test(Q204 q) {
        int[] primes = new int[] {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
        };
        assertEquals(primes.length, q.countPrimes(100));

        primes = new int[] {
            2,3,5,7,11,13,17,19,23,29,31,37,41,
            43,47,53,59,61,67,71,73,79,83,89,97,101,
            103,107,109,113,127,131,137,139,149,151,157,163,167,
            173,179,181,191,193,197,199,211,223,227,229,233,239,
            241,251,257,263,269,271,277,281,283,293,307,311,313,
            317,331,337,347,349,353,359,367,373,379,383,389,397,
            401,409,419,421,431,433,439,443,449,457,461,463,467,
            479,487,491,499,503,509,521,523,541,547,557,563,569,
            571,577,587,593,599,601,607,613,617,619,631,641,643,
            647,653,659,661,673,677,683,691,701,709,719,727,733,
            739,743,751,757,761,769,773,787,797,809,811,821,823,
            827,829,839,853,857,859,863,877,881,883,887,907,911,
            919,929,937,941,947,953,967,971,977,983,991,997
        };
        assertEquals(primes.length, q.countPrimes(1000));
    }

    public Object[] pq204() {
        return $(
            $(new Q204()),
            $(new Q204Sieve())
        );
    }

    @Test
    public void test() {
        MRotated r = new MRotated();
        int[] arr = new int[] {1, 2, 3, 4, 5};
        for(int i=0; i<5; i++) {
            assertEquals(r.find(arr, i+1), i);
        }
    }

    @Test
    @Parameters(method="pFrog")
    public void test(Frog f) {
        assertEquals(f.jump(7, 3, new int[] {5, 1, 1, 2, 3}), -1);
        assertEquals(f.jump(7, 3, new int[] {5, 1, 1, 2, 4}), 4);
        assertEquals(f.jump(7, 3, new int[] {1, 2, 3, 1, 2, 3, 4}), 6);
    }
    public Object[] pFrog() {
        return $($(
            new Frog()/*, new Frog2()*/));
    }

    @Test
    @Parameters(method="pNim")
    public void test(Q292NimGame q) {
        boolean[] answers = new boolean[] {
            false,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            true,
            true
        };
        for(int i=1; i<=10; i++) {
            assertEquals(q.canWinNim(i), answers[i]);
        }
        
    }
    public Object[] pNim() {
        return $($(new NimGame1(), new NimGame2(), new NimGame3()));
    }

    @Test
    public void testQ200() {
        Q200NumIslands q = new Q200NumIslands();
        char[][] map = new char[][] {
            new char[] {'1'}
        };
        assertEquals(1, q.numIslands(map));

        map = new char[][] {
            new char[] {'1', '1'}
        };
        assertEquals(1, q.numIslands(map));

        map = new char[][] {
            new char[] {'1'},
            new char[] {'1'},
        };

        assertEquals(1, q.numIslands(map));

    }
}