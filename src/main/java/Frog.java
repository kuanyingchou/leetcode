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

class Frog2 extends Frog {
    //_o.o_
    // [1, 5, 2, 3]
    //
    // F
    // = - - _ _ - _ =
    // 0 1 2 3 4 5 6 7
    @Override
    public int jump(int dest, int step, int[] leaves) {
        throw new RuntimeException();
        /*
        if(dest - step <= 0) return 0;
        int pos = 0;
        for(int i=0; i<leaves.length; i++) {
            for(
        }
        if(dest - pos > step) return -1;
        */
    }
}
