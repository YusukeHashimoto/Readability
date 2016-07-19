import java.util.HashMap;
import java.util.Map;

public class Program {
	private String code;
	private Map<String, String> methodMap;
	
	Program(String code) {
		this.code = code;
		methodMap = new HashMap<String, String>();
		splitIntoMethods();
	}
	
	private void splitIntoMethods() {
		StringBuilder sb = new StringBuilder();
		String methodName = "";
		
		for(int i = 0, openedBrancket = 0; i < code.length(); i++) {
			switch(code.charAt(i)) {
			case '{':
				openedBrancket++;
				if(openedBrancket == 2) { //beginning of method
					sb = new StringBuilder();
					if(code.charAt(i+1) == '\n') i++;
					StringBuilder name = new StringBuilder();
					for(int j = i - 2; ; j--) {
						if(name.toString().contains("(") && (code.charAt(j) == ' '
								|| code.charAt(j) == '\t'))
							break;
						name.append(code.charAt(j));
					}
					methodName = name.reverse().toString();
					continue;
				}
				break;
				
			case '}':
				openedBrancket--;
				if(openedBrancket == 1) { //end of method
					methodMap.put(methodName, sb.toString());
					continue;
				}
				break;
			}
			
			sb.append(code.charAt(i));
		}
	}
	
	Map<String, String> getMethodMap() {
		return methodMap;
	}
	
	@Override
	public String toString() {
		return code;
	}
	
	public static void main(String args[]) {
		String code = Main.readFile("./src/Program.java");
		Program program = new Program(code);

		for(String key : program.methodMap.keySet()) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(program.getMethodMap().get(key));
		}
	}
}
