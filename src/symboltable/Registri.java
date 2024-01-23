package symboltable;

import java.util.ArrayList;
import java.util.Arrays;

public class Registri {

	static ArrayList<Character> caratteri = new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f'));
	
	public static char newRegister() {
		return caratteri.remove(0);
	}
	
}
