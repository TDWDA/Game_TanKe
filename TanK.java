package tk01;

import java.util.Vector;

//坦克类
public class TanK {
		int x=0,y=0;//表示坦克的横纵坐标
		int direct=0;//0上1下2左3右。坦克方向
		int speed=5;//坦克的速度
		public TanK(int x,int y) {
			this.x=x;
			this.y=y;
		}
		public int getSpeed() {
			return speed;
		}
		public void setSpeed(int speed) {
			this.speed = speed;
		}
		
		public int getDirect() {
			return direct;
		}
		public void setDirect(int direct) {
			this.direct = direct;
		}
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
}
	//敌人的坦克
	class EnemyTank extends TanK implements Runnable{
		boolean islive=true;
		//定义一个存放敌人子弹的集合
		Vector<shot>ss=new Vector<shot>();
		public EnemyTank(int x,int y) {
			// TODO Auto-generated constructor stub
			super(x, y);
			speed=2;
		}
		public void run() {
			while (true)
			{
				switch (this.direct) {
				case 0:
					for(int i=0;i<50;i++)
					{
						if(y>0)//不出边界
						{
							y-=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case 1:
					for(int i=0;i<50;i++)
					{
						if(y<600)
						{
							y+=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				case 2:
					for(int i=0;i<50;i++)
					{
						if(x>0)
						{
							x-=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}	
					break;
				case 3:
					for(int i=0;i<50;i++)
					{
						if(x<800)
						{
							x+=speed;
						}
						try {
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				default:
					break;
				}
				//让坦克产生一个随机方向
				this.direct=(int)(Math.random()*4);
				if(islive==false)
				{
					break;
				}
				
			}
				
		}
		
	}
	//我的坦克
	class MyTanKe extends TanK{
		shot s=null;
		//子弹的集合，这样才能打出多个子弹
		Vector<shot>ss=new Vector<shot>();
		public MyTanKe(int x,int y) {
			super(x, y);
		}
		//开火
		public void ShotEnemy() {
			switch (this.direct) {
			case 0:
				s=new shot(x+5, y,0);
				ss.add(s);
				break;
			case 1:
				s=new shot(x+5, y+10,1);
				ss.add(s);
                break;
			case 2:
				s=new shot(x, y+10,2);
				ss.add(s);
                break;
			case 3:
				s=new shot(x+30, y+10,3);
				ss.add(s);
                break;
			default:
				break;
			}
			Thread t=new Thread(s);
			t.start();
		}
		//坦克向上移动
		public void moveup() {
			y-=speed;
		}
		public void movedown() {
			y+=speed;
		}
		public void moveleft() {
			x-=speed;
		}
		public void moveright() {
			x+=speed;
		}//
}
