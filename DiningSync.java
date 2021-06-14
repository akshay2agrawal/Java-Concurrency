import java.util.*;

 class Philosopher implements Runnable{

	
    private int id;
    private forkk leftfork;
    private forkk rightfork;
    //private Boolean leftacquire;
    //private Boolean rightacquire;


    public Philosopher(int id, forkk left, forkk right){
        this.id=id;
        this.leftfork = left;
        this.rightfork = right;
    }

    public void run(){
    	try{
	   thinking();
	   pickupleft();
		} catch (InterruptedException e){
			System.out.println(e);
		}	
	}

	public void thinking() throws InterruptedException{
 		System.out.println("Philosopher " + id + " is thinking");
 		//Thread.sleep(500);

	}

    public  void pickupleft() throws InterruptedException{
 		//	System.out.println("bbbb");
 		synchronized(leftfork){
	 		while(leftfork.ac){
	 		//	System.out.println("aaa" + id);
	 			leftfork.wait();
	 		}
        leftfork.ac = true;     
        System.out.println("Philosopher "+id+" has picked up left fork");
  		}

 		//System.out.println(leftfork.ac);
 		pickupright();
 		
    }



    public void pickupright() throws InterruptedException{
      	synchronized(rightfork){
	      	while(rightfork.ac){
	 			rightfork.wait();
	 		}
 		rightfork.ac = true;
        System.out.println("Philosopher "+id+" has picked up right fork");

        }

 		 		eat();
     }


	public void eat() throws InterruptedException{
		System.out.println("Philosopher " + id + " is eating right now!");		
		Thread.sleep(1000);

	   	synchronized(leftfork){
	   		leftfork.ac = false;
 			System.out.println("Philosopher " + id + " has put down the left fork");
	   		leftfork.notify();
	   	}
	  	
	  	synchronized(rightfork){
	  		rightfork.ac = false;
 			System.out.println("Philosopher " + id + " has put down the right fork");
	  		rightfork.notify();
	  	}
	    
	}

}

    class forkk{

    	public Boolean ac;
    	public forkk(){
    		ac = false;
    	}
    }



public class DiningSync{
    public static void main(String args[]) throws InterruptedException
    {
        forkk[] fk = new forkk[5];
        for(int i=0;i<5;i++){
        	
            fk[i] = new forkk();
        }

        Philosopher[] philosophers=new Philosopher[5];
        for(int i=0;i<5;i++){
            if(i==4){
                philosophers[i] = new Philosopher(i, fk[(i+1)%5], fk[i]);    
            }
            else{
                philosophers[i] = new Philosopher(i, fk[i], fk[(i+1)%5]);
            }
        }

        while(true){
        	for(int i=0;i<5;i++){
	            Thread t= new Thread(philosophers[i]);

	            t.start();
	           Thread.sleep(500);
		  	}
        }

    }
}
