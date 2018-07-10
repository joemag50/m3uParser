

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lector momo = new Lector("1.m3u");
		Lector momo2 = new Lector("2.m3u");
		
		System.out.println(momo2.toJson().toJSONString());
	}

}
