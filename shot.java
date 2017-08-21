package tk01;

//×Óµ¯Àà
public class shot implements Runnable {
	int x,y;
	int speed=10;
	int direct;
	boolean islive=true;
	public shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			switch (this.direct) {
			case 0:
				y-=speed;
				break;
			case 1:
				y+=speed;
				break;
			case 2:
				x-=speed;
				break;
			case 3:	
				x+=speed;
				break;
			default:
				break;
		    }
			if(x<0||y>600||x>800||y<0)
		    {
				this.islive=false;
				break;
			}
			if(islive==false)
			{
				break;
			}
			System.out.println("x£º"+x+"y£º"+y);
		}
	}
	
       
}
