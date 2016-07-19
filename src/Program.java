import java.util.ArrayList;
import java.util.List;

public class Program {
	private String code;
	private List<String> methods;
	
	Program(String code) {
		this.code = code;
		methods = new ArrayList<String>();
		splitIntoMethods();
	}
	
	private void splitIntoMethods() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0, openedBrancket = 0; i < code.length(); i++) {
			switch(code.charAt(i)) {
			case '{':
				openedBrancket++;
				if(openedBrancket == 2) { //beginning of method
					sb = new StringBuilder();
					if(code.charAt(i+1) == '\n') i++;
					continue;
				}
				break;
				
			case '}':
				openedBrancket--;
				if(openedBrancket == 1) { //end of method
					methods.add(sb.toString());
					continue;
				}
				break;
			}
			
			sb.append(code.charAt(i));
		}
	}
	
	List<String> getMethodList() {
		return methods;
	}
	
	@Override
	public String toString() {
		return code;
	}
	
	public static void main(String args[]) {
		String code = Main.readFile("./src/Program.java");
		Program program = new Program(code);
		for(String s : program.methods) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(s);
		}
	}
}
