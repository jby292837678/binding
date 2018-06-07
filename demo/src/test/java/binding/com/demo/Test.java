package binding.com.demo;

import java.util.Random;

public class Test {

	private static Random random = new Random();



	@org.junit.Test
	public void main() {
		// -20<x<20
		// 0<y+x<20
		// -x<y<20-x || 0<y<20
		int num = 2;

		int x = 20 - random.nextInt(40);
		int y = 0;
		int z=random.nextInt(num+1);//�������ֵ�λ��
		System.out.print(x);
		x=getNum(y+=x);
		for (int i = 1; i < num; i++) {
			if(i==z)System.out.print("+_");
			else if(x>=0)System.out.print("+"+x);
			else System.out.print(x);
			x=getNum(y+=x);
		}
		if(num==z)System.out.print("=_");
		else System.out.print("="+(x+y));
	}

	public static Integer getNum(int x) {
		Integer y = 0;
		if(x<0)
		y=random.nextInt(20+x)-x;
		else y = random.nextInt(20)-x;
		return y;
	}
}


