import static org.junit.Assert.assertEquals;
import org.junit.Test;
import junitparams.*;
import org.junit.runner.*;

@RunWith(JUnitParamsRunner.class)
public class SolutionTest {
    @Test
    @Parameters
    public void testIsPalindrome(Q234 q) {
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

    public Object[] parametersForTestIsPalindrome() {
        return new Object[] { 
            new Object[] { new Q234() }
        };
    }


}
