package tk01;

//炸弹类
public class Bomb {
/*
 * 准备3张图片，
 * 定义炸弹bomb类，
 * 在击中敌人坦克时将炸弹放入vector集合中，
 * 绘制图片
*/
	//定义炸弹坐标
	int x,y;
	//炸弹的生命
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
