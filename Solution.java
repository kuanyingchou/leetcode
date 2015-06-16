public class Solution {
    //Single Number
    public int singleNumber(int[] nums) {
        for(int i=0; i<nums.length; i++) {
            final int t = nums[i];
            boolean isTwin = false;
            for(int j=i+1; j<nums.length; j++) {
                if(t == nums[j]) {
                    isTwin = true;
                    break;
                }
            }
            if(!isTwin) {
                return t;
            }
            
        }
        throw new RuntimeException("There is no single number!");
    }


    //Invert Binary Tree
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
        public String toString() {
            return val + "("+ (left!=null?left:"") +", "+(right!=null?right:"")+")";
        }
    }
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        final TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    public static void main(String[] args) {
        final Solution s= new Solution();
        System.out.println(s.singleNumber(new int[] {3, 5, 1, 9, 3, 1, 9}));

        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        System.out.println(root);
        s.invertTree(root);
        System.out.println(root);
    }
}
