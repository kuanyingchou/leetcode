interface Q292NimGame {
	public boolean canWinNim(int n);
	
}
class NimGame1 implements Q292NimGame {
	@Override
	public boolean canWinNim(int n) {
        if(n<=3) return true;
        if(n==4) return false;
        if(!canWinNim(n-1) || !canWinNim(n-2) || !canWinNim(n-3)) {
            return true;
        } else {
            return false;
        }
    }
    
}
class NimGame2 implements Q292NimGame {
	public boolean canWinNim(int n) {
        if(n<=3) return true;
        if(n==4) return false;
        boolean nm1 = true; //2
        boolean nm2 = true; //3
        boolean nm3 = false; //4
        for(int i=5; i<=n; i++) {
            boolean b;
            if(!nm1 || !nm2 || !nm3) b=true;
            else b=false;
            nm1=nm2;
            nm2=nm3;
            nm3=b;
        }
        return nm3;
    }
}
class NimGame3 implements Q292NimGame {
	public boolean canWinNim(int n) {
        if(n%4==0) return false;
        else return true;
    }
}