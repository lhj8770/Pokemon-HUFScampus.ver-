package Information;
import java.awt.Color;

public class Util {
	public static int rand(int max){
		return (int)(Math.random()*(max+1));		
	}
	public static int rand(int min,int max){
		return min +(int)(Math.random()*(max-min+1));		
	}
	public static boolean prob100(int r){
		return (int)(Math.random()*100)<=r;		
	}
	public static boolean prob100(double r){
		return (Math.random()*100)<=r;		
	}
	public static Color randColor(){
		return new Color(rand(255),rand(255),rand(255));
		
	}
	//나머지 연산으로 인해 정해진 wildPokemonNum 안에서의 랜덤적 인덱스 픽업이 된다.
	public static int randWild(int wildPokemonNum){
		return (int)((Math.random()*100)%wildPokemonNum);
	}
	public static Color randColor(int min,int max){
		return new Color(rand(min,max),rand(min,max),rand(min,max));
		
	}
}
 