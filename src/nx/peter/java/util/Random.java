package nx.peter.java.util;

import nx.peter.java.Main;
import nx.peter.java.util.data.Number;

public class Random {
	static java.util.Random r = new java.util.Random();

	public static int nextInt(){
		return nextInt(9);
	}

	public static float nextFloat(){
		return r.nextFloat();
	}

	public static double nextDouble(){
		return r.nextDouble();
	}

	public static Number nextNumber() {
		return new Number(nextDouble() + nextInt(Integer.MAX_VALUE/200));
	}

	public static long nextLong(){
		return r.nextLong();
	}
	
	
	public static int nextInt(int range) {
		return r.nextInt(range);
	}
	
	public static float nextFloat(float range) {
		return nextFloat(0, range);
	}
	
	public static double nextDouble(double range) {
		return nextDouble(0, range);
	}
	
	public static long nextLong(long range) {
		return nextLong(0, range);
	}


	public static int nextInt(int min, int max){
		if(min == max)
			return min;
		else{
			int value = nextInt(), tempMin = min, tempMax = max;
			min = Math.min(tempMin, tempMax);
			max = Math.max(tempMin, tempMax);

			while(value < min || value > max)
				value = min + nextInt();
			return value;
		}
	}

	public static float nextFloat(float min, float max){
		if(min == max)
			return min;
		else{
			float value = nextFloat(), tempMin = min, tempMax = max;
			min = Math.min(tempMin, tempMax);
			max = Math.max(tempMin, tempMax);

			while(value < min || value > max)
				value = min + nextFloat();
			return value;
		}
	}

	public static double nextDouble(double min, double max){
		if(min == max)
			return min;
		else{
			double value = nextDouble(), tempMin = min, tempMax = max;
			min = Math.min(tempMin, tempMax);
			max = Math.max(tempMin, tempMax);
			int iMin = (int) min;
			int iMax = (int) max;
			int whole = iMax > iMin ? nextInt(iMin, iMax) : iMin;
			value += whole;

			while(value < min || value > max)
				value = whole + nextDouble();
			return value;
		}
	}

	public static long nextLong(long min, long max){
		if(min == max)
			return min;
		else{
			long value = nextLong(), tempMin = min, tempMax = max;
			min = Math.min(tempMin, tempMax);
			max = Math.max(tempMin, tempMax);

			while(value < min || value > max)
				value = min + nextLong();
			return value;
		}
	}

}

