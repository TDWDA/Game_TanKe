package tk01;

import java.util.Vector;

//̹����
public class TanK {
		int x=0,y=0;//��ʾ̹�˵ĺ�������
		int direct=0;//0��1��2��3�ҡ�̹�˷���
		int speed=5;//̹�˵��ٶ�
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
	//���˵�̹��
	class EnemyTank extends TanK implements Runnable{
		boolean islive=true;
		//����һ����ŵ����ӵ��ļ���
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
						if(y>0)//�����߽�
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
				//��̹�˲���һ���������
				this.direct=(int)(Math.random()*4);
				if(islive==false)
				{
					break;
				}
				
			}
				
		}
		
	}
	//�ҵ�̹��
	class MyTanKe extends TanK{
		shot s=null;
		//�ӵ��ļ��ϣ��������ܴ������ӵ�
		Vector<shot>ss=new Vector<shot>();
		public MyTanKe(int x,int y) {
			super(x, y);
		}
		//����
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
		//̹�������ƶ�
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
