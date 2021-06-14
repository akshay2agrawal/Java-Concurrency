class CollectedTransfer implements Runnable{

    Account source1;
    Account source2;
    Account dest;
    int amount;

    public CollectedTransfer(Account a, Account b, Account c, int amount) {
        this.source1 = a;
        this.dest = c;
        this.source2 = b;
        this.amount = amount;
      }

       public void run(){

//      for(int i=0;i<amount;i++){
            source1.collecttransfer(source1, source2, dest, amount);
            System.out.println("Source 1 has amount  "+source1.getBalance()+"Source 2 has amount  "+source2.getBalance()+" And destination has amount has "+dest.getBalance());
        
    }
 }


public class AccountImpl1 {
	public static void main(String[] args){
		Account[] ac = new Account[3];
		for(int i=0;i<3;i++){
			ac[i] = new Account(10000,i);
		} 
        System.out.println("check");
		CollectedTransfer t1 = new CollectedTransfer(ac[0], ac[1], ac[2], 500);
        CollectedTransfer t2 = new CollectedTransfer(ac[0], ac[1], ac[2], 10000);        
        //CollectedTransfer t3 = new CollectedTransfer(ac[0], ac[1], ac[2], 500);        
		Thread th1 = new Thread(t1);
		th1.start();
        Thread th2 = new Thread(t2);
        th2.start();
        //Thread th3 = new Thread(t3);
        //th3.start();
		//System.out.println(ac[0].getBalance());
		//System.out.println(ac[1].getBalance());
	}
}