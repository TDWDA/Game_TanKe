package tk01;

//ը����
public class Bomb {
/*
 * ׼��3��ͼƬ��
 * ����ը��bomb�࣬
 * �ڻ��е���̹��ʱ��ը������vector�����У�
 * ����ͼƬ
*/
	//����ը������
	int x,y;
	//ը��������
	int life=9;
	boolean islive=true;
	public Bomb(int x,int y) {
		this.x=x;
		this.y=y;
	}
	public void lifedown() {
		if(life>0)
		{
			life--;
		}
		else
		{
			this.islive=false;
		}
	}
}
