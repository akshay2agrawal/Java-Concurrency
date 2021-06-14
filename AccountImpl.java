class Transfer implements Runnable{

    Account source;
    Account dest;
    int amount;

    public Transfer(Account a, Account b, int amount) {
        this.source = a;
        this.dest = b;
        this.amount = amount;
      }

       public void run(){

//      for(int i=0;i<amount;i++){
            source.transfer(dest,amount);
            System.out.println("Source has amount  "+source.getBalance()+" And destination has amount has "+dest.getBalance());
        
    }
 }


public class AccountImpl {
	public static void main(String args[]){
		Account[] ac = new Account[3];
		for(int i=0;i<3;i++){
			ac[i] = new Account(10000);
		} 

		Transfer t1 = new Transfer(ac[0], ac[1], 500);
        Transfer t2 = new Transfer(ac[1], ac[2], 700);        
		Thread th1 = new Thread(t1);
		th1.start();
        Thread th2 = new Thread(t2);
        th2.start();
		//System.out.println(ac[0].getBalance());
		//System.out.println(ac[1].getBalance());
	}
}