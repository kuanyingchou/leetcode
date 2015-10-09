class Q204Sieve extends Q204 {
    @Override
    public int countPrimes(int n) {
        boolean[] primes = new boolean[n+1];
        for(int i=2; i<=n; i++) primes[i] = true;

        for(int i=2; i<=n; i++) { //TODO
            for(int j=i; j*i<=n; j++) {
                primes[j*i] = false;
            }
        }

        int count = 0;
        for(int i=2; i<=n; i++) {
            if(primes[i]) ++count;
        }
        return count;
    }
}
