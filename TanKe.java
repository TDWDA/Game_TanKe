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
		this.addKeyListener(mp);//ע�����������������ע����¼�Դ��
		Thread t=new Thread(mp);
		t.start();
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
//���ڻ�ͼ������
class Mypanel extends JPanel implements KeyListener,Runnable {
	MyTanKe mt=null;//����һ���ҵ�̹��
	//����̹�˼���
	Vector<EnemyTank>etk=new Vector<EnemyTank>();
	//����̹������
	int Enemynum=3;
	//����ը������
	Vector<Bomb>bombs=new Vector<Bomb>();
	//�������ű�ըͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//���屳��ͼƬ
	Image imagebj=null;
    public Mypanel() {
		// TODO Auto-generated constructor stub
		mt=new MyTanKe(300, 400);//�ҵ�̹�˵ĳ�ʼ����
		//��ʼ��ͼƬ
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.jpg"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.jpg"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.jpg"));
//		imagebj=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bj.jpg"));
		//��ʼ��ͼƬ����һ�ַ�����
		try {
			image1=ImageIO.read(new File("bomb1.jpg"));
			image2=ImageIO.read(new File("bomb2.jpg"));
			image3=ImageIO.read(new File("bomb3.jpg"));
			imagebj=ImageIO.read(new File("bj.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//����̹��
		for(int e=0;e<Enemynum;e++)
		{
			EnemyTank et=new EnemyTank((e+1*2)*100, 0);
			et.direct=1;
			etk.add(et);
			//��������̹��
			Thread t=new Thread(et);
		    t.start();
		    //����ӵ�
		    shot s=new shot(et.x+10, et.y+30, 2);
		    et.ss.add(s);
		    //�����ӵ�
		    Thread t2=new Thread(s);
		    t2.start();
		}
		
		
	}
    //paint��AWTϵͳ�Զ�����
    public void paint(Graphics g) {
		super.paint(g);//���ø��ຯ����ɳ�ʼ��������ȱ
		//����ϳ�������ͼƬ
		g.drawImage(imagebj, 0, 0, 800, 600,this);
	//	g.fillRect(0, 0, 800, 600);
		//���ҷ�̹��
		this.DrawTanKe(mt.getX(), mt.getY(), g, mt.direct, 1);
		//��ը��
		for(int e=0;e<bombs.size();e++)
		{
			//�Ӽ�����ȡ��
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
			//��ը������ֵ����
			b.lifedown();
			if(b.life==0) 
			{
				bombs.remove(b);
			}
			
		}
		//������̹��
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
			//�������ӵ�
			for(int j=0;j<et.ss.size();j++)
			{
				shot s=et.ss.get(j);
			    if(s!=null&&s.islive==true)
				  {
			    	g.fillOval(s.x, s.y, 10, 10);
				  }
			    //����ӵ�������ɾ��
			    if(s .islive==false)
			    {
			    	et.ss.remove(s);
			    }
			}
			
		}
		//���ӵ�������ȡ���ӵ������ӵ�
		for(int e=0;e<mt.ss.size();e++)
		{
			shot s=mt.ss.get(e);
		    if(s!=null&&s.islive==true)
			  {
		    	g.fillOval(s.x, s.y, 10, 10);
			  }
		    //����ӵ�������ɾ��
		    if(s .islive==false)
		    {
		    	mt.ss.remove(s);
		    }
		}
    }
    //�ж��ӵ��Ƿ���ез�̹��
    public void HitTank(shot s,EnemyTank et) {
    	//�ж�̹�˷���
    	switch (et.direct) {
		case 0:
		case 1:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
			{
				//���У��ӵ�����������̹������
				s.islive=false;
				et.islive =false;
				//����һ��ը�������뼯��
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		case 2:
		case 3:
			if(s.x>et.x&&s.x<et.x+30&&s.x>et.y&&s.y<et.y+20)
			{
				//����
				s.islive=false;
				et.islive =false;
				//����һ��ը�������뼯��
				Bomb b=new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		default:
			break;
		}
    }
    //��װһ����̹�˺���
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
    	//����ʱ��̹����
		case 0:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);//��Բ
			g.drawLine(x+10, y+15, x+10, y);//����
			break;
		case 1:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);//��Բ
			g.drawLine(x+10, y+15, x+10, y+30);//����
			break;
		case 2:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);//��Բ
			g.drawLine(x+15, y+10, x, y+10);//����
			break;
		case 3:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);//��Բ
			g.drawLine(x+15, y+10, x+30, y+10);//����
			break;
		default:
			break;
		}
    }
	//Graphics ���ǻ�ͼ����Ҫ�࣬�����Ϊһ�����ʡ�
//	public void paint(Graphics g) {
//		super.paint(g);//���ø��ຯ����ɳ�ʼ��������ȱ
//		g.setColor(Color.CYAN);
//		g.fill3DRect(mt.getX(), mt.getY(), 5, 30,false);
//		g.fill3DRect(mt.getX()+15, mt.getY(), 5, 30,false);
//		g.fill3DRect(mt.getX()+5, mt.getY()+5, 10, 20,false);
//		g.fillOval(mt.getX()+5, mt.getY()+10, 10, 10);//��Բ
//		g.drawLine(mt.getX()+10, mt.getY()+15, mt.getX()+10, mt.getY());//����
//	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//w��s��a��d��
		if(e.getKeyCode()==KeyEvent.VK_W)
		{	
			//����̹�˷���
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
			//���ÿɷ����ӵ�������������5�š�
			if(mt.ss.size()<=4)
			{
				mt.ShotEnemy();//����
			}
		}
		repaint();//���»�ͼ
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
			//�ж��Ƿ����
			for(int e=0;e<mt.ss.size();e++)
			{
				//ȡ���ӵ�
				shot s=mt.ss.get(e);
				if(s.islive)
				{
					//ȡ��ÿ��̹�˺��ӵ��ж�
					for(int j=0;j<etk.size();j++)
					{
						HitTank(s, etk.get(j));
					}
				}
				
			}
			//�жϵ���̹���Ƿ�����ӵ�
			for(int j=0;j<etk.size();j++)
			{
				//ȡ���ӵ�
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

