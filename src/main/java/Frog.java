class Frog {
    public int jump(int dest, int step, int[] leaves) {
        return jump(dest, step, leaves, 0, 0);
    }
    public int jump(int dest, int step, int[] leaves, int pos, int time) {
        if(dest - pos <= step) return time;
        int min = Integer.MAX_VALUE;
        for(int i=time+1; i<leaves.length; i++) {
            if(leaves[i] - pos <= step) {
                int t = jump(dest, step, leaves, leaves[i], i);
                if(t < min && t>=0) min = t;
            }
        }
        if(min == Integer.MAX_VALUE) return -1;
        else return min;
    }
    public static void main(String[] args) {
        System.out.println(new Frog().jump(7, 3, new int[] {5, 1, 1, 2, 4}));
    }
}
