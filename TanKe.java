package tk01;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.*;
public class TanKe extends JFrame {

	Mypanel mp=null;
	public static void main(String[] args)
	{
		new TanKe();
	}
	public TanKe() {
		mp=new Mypanel();
		this.add(mp);
		this.addKeyListener(mp);//注册监听（监听器对象注册给事件源）
		Thread t=new Thread(mp);
		t.start();
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
//用于绘图的区域
class Mypanel extends JPanel implements KeyListener,Runnable {
	MyTanKe mt=null;//定义一个我的坦克
	//敌人坦克集合
	Vector<EnemyTank>etk=new Vector<EnemyTank>();
	//敌人坦克数量
	int Enemynum=3;
	//定义炸弹集合
	Vector<Bomb>bombs=new Vector<Bomb>();
	//定义三张爆炸图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//定义背景图片
	Image imagebj=null;
    public Mypanel() {
		// TODO Auto-generated constructor stub
		mt=new MyTanKe(300, 400);//我的坦克的初始坐标
		//初始化图片
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.jpg"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.jpg"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.jpg"));
//		imagebj=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bj.jpg"));
		//初始化图片的另一种方法：
		try {
			image1=ImageIO.read(new File("bomb1.jpg"));
			image2=ImageIO.read(new File("bomb2.jpg"));
			image3=ImageIO.read(new File("bomb3.jpg"));
			imagebj=ImageIO.read(new File("bj.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//敌人坦克
		for(int e=0;e<Enemynum;e++)
		{
			EnemyTank et=new EnemyTank((e+1*2)*100, 0);
			et.direct=1;
			etk.add(et);
			//启动敌人坦克
			Thread t=new Thread(et);
		    t.start();
		    //添加子弹
		    shot s=new shot(et.x+10, et.y+30, 2);
		    et.ss.add(s);
		    //启动子弹
		    Thread t2=new Thread(s);
		    t2.start();
		}
		
		
	}
    //paint由AWT系统自动调用
    public void paint(Graphics g) {
		super.paint(g);//调用父类函数完成初始化，不能缺
		//面板上充满背景图片
		g.drawImage(imagebj, 0, 0, 800, 600,this);
	//	g.fillRect(0, 0, 800, 600);
		//画我方坦克
		this.DrawTanKe(mt.getX(), mt.getY(), g, mt.direct, 1);
		//画炸弹
		for(int e=0;e<bombs.size();e++)
		{
			//从集合中取出
			Bomb b=bombs.get(e);
			if(b.life>6)
			{
				g.drawImage(image1, b.x, b.y, 30, 30,this);
			}
			else if(b.life>3)
			{
				g.drawImage(image2, b.x, b.y, 30, 30,this);
			}
			else
			{
				g.drawImage(image3,b.x, b.y, 30, 30,this);
			}
			//让炸弹生命值减少
			b.lifedown();
			if(b.life==0) 
			{
				bombs.remove(b);
			}
			
		}
		//画敌人坦克
		for(int e=0;e<etk.size();e++)
		{
			EnemyTank et=etk.get(e);
			if(et.islive)
		   {
				this.DrawTanKe(etk.get(e).getX(), etk.get(e).getY(), g, etk.get(e).direct, 0);
		   }
			if(et.islive==false)
			{
				etk.remove(et);
			}
			//画敌人子弹
			for(int j=0;j<et.ss.size();j++)
			{
				shot s=et.ss.get(j);
			    if(s!=null&&s.islive==true)
				  {
			    	g.fillOval(s.x, s.y, 10, 10);
				  }
			    //如果子弹死亡则删除
			    if(s .islive==false)
			    {
			    	et.ss.remove(s);
			    }
			}
			
		}
		//从子弹集合中取出子弹并画子弹
		for(int e=0;e<mt.ss.size();e++)
		{
			shot s=mt.ss.get(e);
		    if(s!=null&&s.islive==true)
			  {
		    	g.fillOval(s.x, s.y, 10, 10);
			  }
		    //如果子弹死亡则删除
		    if(s .islive==false)
		    {
		    	mt.ss.remove(s);
		    }
		}
    }
    //判断子弹是否击中敌方坦克
    public void HitTank(shot s,EnemyTank et) {
    	//判断坦克方向
    	switch (et.direct) {
		case 0:
		case 1:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
			{
				//击中，子弹死亡，敌人坦克死亡
				s.islive=false;
				et.islive =false;
				//创建一个炸弹，放入集合
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		case 2:
		case 3:
			if(s.x>et.x&&s.x<et.x+30&&s.x>et.y&&s.y<et.y+20)
			{
				//击中
				s.islive=false;
				et.islive =false;
				//创建一个炸弹，放入集合
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		default:
			break;
		}
    }
    //封装一个画坦克函数
    public void DrawTanKe(int x,int y,Graphics g,int direct,int type) {
    	switch (type) {
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		default:
			break;
		}
    	switch (direct) {
    	//向上时的坦克样
		case 0:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);//画圆
			g.drawLine(x+10, y+15, x+10, y);//画线
			break;
		case 1:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);//画圆
			g.drawLine(x+10, y+15, x+10, y+30);//画线
			break;
		case 2:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);//画圆
			g.drawLine(x+15, y+10, x, y+10);//画线
			break;
		case 3:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);//画圆
			g.drawLine(x+15, y+10, x+30, y+10);//画线
			break;
		default:
			break;
		}
    }
	//Graphics 类是绘图的重要类，可理解为一个画笔。
//	public void paint(Graphics g) {
//		super.paint(g);//调用父类函数完成初始化，不能缺
//		g.setColor(Color.CYAN);
//		g.fill3DRect(mt.getX(), mt.getY(), 5, 30,false);
//		g.fill3DRect(mt.getX()+15, mt.getY(), 5, 30,false);
//		g.fill3DRect(mt.getX()+5, mt.getY()+5, 10, 20,false);
//		g.fillOval(mt.getX()+5, mt.getY()+10, 10, 10);//画圆
//		g.drawLine(mt.getX()+10, mt.getY()+15, mt.getX()+10, mt.getY());//画线
//	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//w上s下a左d右
		if(e.getKeyCode()==KeyEvent.VK_W)
		{	
			//设置坦克方向
			mt.setDirect(0);
			mt.moveup();
		}
		else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			mt.setDirect(1);
			mt.movedown();
		}
		else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			mt.setDirect(2);
			mt.moveleft();
		}
		else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			mt.setDirect(3);
			mt.moveright();
		}
		else if(e.getKeyCode()==KeyEvent.VK_J)
		{
			//设置可发射子弹数，这里设置5颗。
			if(mt.ss.size()<=4)
			{
				mt.ShotEnemy();//开火
			}
		}
		repaint();//重新绘图
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		while (true) {
			try {
			Thread.sleep(100);
		  } catch (Exception e) {
			  e.printStackTrace();
			// TODO: handle exception
		  }
			//判断是否击中
			for(int e=0;e<mt.ss.size();e++)
			{
				//取出子弹
				shot s=mt.ss.get(e);
				if(s.islive)
				{
					//取出每个坦克和子弹判断
					for(int j=0;j<etk.size();j++)
					{
						HitTank(s, etk.get(j));
					}
				}
				
			}
			//判断敌人坦克是否加入子弹
			for(int j=0;j<etk.size();j++)
			{
				//取出子弹
				EnemyTank et =etk.get(j);
				if(et.ss.size()<1)
				{
					shot s=null;
				
					switch (et.direct) {
					case 0:
						s=new shot(et.x+5, et.y, 0);
						et.ss.add(s);
						break;
					case 1:
						s=new shot(et.x+5, et.y+10, 1);
						et.ss.add(s);
						break;
					case 2:
						s=new shot(et.x+5, et.y, 2);
						et.ss.add(s);
						break;
					case 3:	
						s=new shot(et.x, et.y+5, 3);
						et.ss.add(s);
						break;
					default:
						break;
				   }
					Thread t=new Thread (s);
				    t.start();
			  }
				
			}
	    repaint();
	   }
	}
		
}

