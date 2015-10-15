class MRotated {
    //return index of n in arr using binary search. arr is sorted and rotated
    public int find(int[] arr, int n) {
        int start = 0;
        int end = arr.length-1;

        while(start != end) {
            System.err.println(String.format("%d-%d", start, end));
            int m = (start + end) / 2;
            if(n > arr[m]) {
                start = m+1;
            } else if(n < arr[m]) {
                end = m-1;
            } else {
                return m;
            }
        }
        if(arr[start] != n) return -1;
        else return start;
    }
}
