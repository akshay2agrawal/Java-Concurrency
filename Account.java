public class Account {

  private int balance;
  private int id;
  Account temp;
  public Account(int initialDeposit, int id) {
    balance = initialDeposit;
    this.id = id;
  }

  public synchronized int getBalance() {
    return balance;
  }

  public synchronized void deposit(int amount) {
    balance += amount;
  }

  public boolean withdraw(int amount) {
    synchronized (this) {
      if (balance >= amount) {
        balance = balance-amount;
        //System.out.println(balance);
        return true;
      } else {
        return false;
      }
    }
  }
  // Attention, this code can produce a deadlock, if two (or more) threads 
  // transfer money from/to a circle of accounts.
/*  public boolean transferWithDeadlock (Account dest, int amount) {
    if (withdraw(amount)) {
      dest.deposit(amount);
      return true;
    } else {
      return false; 
    }
  }
*/
  // Ideal for a deadlock prevention. Compare the accounts and always lock
  // the `smaller` account first. This realtes to having one philosopher 
  // taking its sticks in reverse order.  
  public boolean transfer(Account dest, int amount) {
    if (dest.getBalance()<this.getBalance()) {  // This comparison does not work yet, correct it.
      synchronized(dest) {
        synchronized(this) {
          if (this.withdraw(amount)) {
            dest.deposit(amount);
            return true;
          } else {
            return false; 
          }
        }
      }
    } else { 
      synchronized(this) {
        synchronized(dest) {
          if (withdraw(amount)) {
            dest.deposit(amount);
            return true;
          } else {
            return false; 
          }
        }
      }
    }
  }


    public boolean collecttransfer(Account source1, Account source2, Account dest, int amount) {

		Account[] arr = new Account[3];
		arr[0] = this;
		arr[1] = source2;
		arr[2] = dest;
		for(int i=0;i<3;i++){
			for(int j=i;j<2;j++){
				if(arr[j].id>arr[j+1].id){
					temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}


    int amt = this.getBalance()+source2.getBalance();
    if(amt<amount){
    	return false;
    }
    else{

    	synchronized(arr[0]){
    		synchronized(arr[1]){
    			synchronized(arr[2]){
    				  int x = this.getBalance();
			          if(x>=amount){
			          	this.withdraw(amount);
			          	dest.deposit(amount);
			          	return true;
			          }
			          else{
			          	this.withdraw(x);
			          	source2.withdraw(amount-x);
			          	dest.deposit(amount);
			          	return true;
			          }
    			}
    		}
    	}

	}
  }
}
