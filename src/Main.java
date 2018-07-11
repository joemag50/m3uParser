import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PepeHttpRequest momo = new PepeHttpRequest();
		
		String myJson = momo.toJson().toJSONString();
		//System.out.println(myJson);
	}
}
