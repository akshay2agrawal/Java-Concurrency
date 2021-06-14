public class Account {

  private int balance;

  public Account(int initialDeposit) {
    balance = initialDeposit;
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
  /*public boolean transfer(Account dest, int amount) {
    if (dest.getBalance()<this.getBalance()) {  // This comparison does not work yet, correct it.
      synchronized(dest) {
        synchronized(this) {
          if (withdraw(amount)) {
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
  }*/
}
