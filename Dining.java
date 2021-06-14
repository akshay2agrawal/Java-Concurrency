import java.util.concurrent.*;

 class Philosopher implements Runnable{
	
    private int id;
    private Semaphore left;
    private Semaphore right;


    public Philosopher(int id, Semaphore left, Semaphore right){
        this.id=id;
        this.left = left;
        this.right = right;
    }

    public void run(){

	    thinking();
	    pickupleft();
	    pickupright();
	   	/*eat();
	   	putleft();
	   	putright();*/
	}

    public void thinking(){
         try
         {
             System.out.println("Philosopher "+id+ " is thinking");
             Thread.sleep(2000);
         }
         catch (InterruptedException e){
         	System.out.println(e);
         }
     }

    public void pickupleft(){
        try{
	        left.acquire();
	        System.out.println("Philosopher "+id+" has picked up left fork");
 		}catch (InterruptedException e){
 			System.out.println(e);
 		}   
    }

    public void pickupright(){
        try{
	        
			int n = right.availablePermits();
			if(n != 0){
		        right.acquire(); 
		        System.out.println("Philosopher "+id+" has picked up right fork");
		        eat();
	    	}
	    	else
	    	{
	    		putleft();
	    		thinking();
	    	}
        }catch (InterruptedException e){
        	System.out.println(e);
        }
     }


	public void eat(){
		

		try
	 	{
	        System.out.println("Philosopher "+id+" is eating");
	       	Thread.sleep(1000);
	 	}
	 	catch(InterruptedException e)
	  	{
	        System.out.println(e);
	  	}	 
	  	putleft();
	  	putright();
	    
	}

	public void putleft(){
		left.release();
	}
	public void putright(){
	    right.release();
	}
 
}

public class Dining{
    public static void main(String args[]) throws InterruptedException
    {
        Semaphore [] sem = new Semaphore [5];
		    for(int i = 0; i < 5; i++) {
		        sem[i] = new Semaphore(1);
		    }   
		

        Philosopher[] philosophers=new Philosopher[5];
        for(int i=0;i<5;i++){

            philosophers[i] = new Philosopher(i, sem[i], sem[(i+1)%5]);
        }
        
        try{	
        while(true){
        	for(int i=0;i<5;i++){
	            Thread t= new Thread(philosophers[i]);

	            t.start();
	            Thread.sleep(1000);
		  	}
        }
        } catch(Exception e){
        	System.out.println(e);
        }

    }
}
