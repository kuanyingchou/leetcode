class Q200NumIslands {
    public int numIslands(char[][] grid) {
        int num = 0;
        for(int i=0; i<grid.length; i++) {
            //System.out.println("checking row "+i);
            for(int j=0; j<grid[i].length; j++) {
                //System.out.println("checking column "+j);
                if(grid[i][j] == '1') {
                    explore(grid, i, j);
                    ++num;
                }
            }
        }
        return num;
    }

    private void explore(char[][] grid, int i, int j) {
        if(grid[i][j] != '1') return;
        grid[i][j] = '2';
        if(i>0) explore(grid, i-1, j);
        if(i<grid.length-1) explore(grid, i+1, j);
        if(j>0) explore(grid, i, j-1);
        if(j<grid[i].length-1) explore(grid, i, j+1);
    }
}