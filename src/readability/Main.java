package readability;
import static raykernel.apps.readability.eval.Main.*;

import java.io.*;
import java.util.Map;

public class Main {
	public static void main(String args[]) {
		String code = readFile("./src/readability/Program.java");
		if(code == null) return;
		
		System.out.println("Readability by Buse & Weimer");
		
		double score = getReadability(code);
		System.out.println(score + "\n");
		
		Program program = new Program(code);
		
		Map<String, String> methodMap = program.getMethodMap();
		for(String methodName : methodMap.keySet()) {
			double scoreOfMethod = getReadability(methodMap.get(methodName));
			System.out.println(methodName + " : " + scoreOfMethod);
		}
		
		System.out.println("\nCyclomatic Complexity");
		
		for(String methodName : methodMap.keySet()) {
			System.out.println(methodName + ": " + program.cyclomaticComplexityOf(methodName));
		}
	}

	static String readFile(String path) {
		try {
			File file = new File(path);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			StringBuilder sb = new StringBuilder();
			String line;
			
			while((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			
			br.close();
			return sb.toString();
		} catch(IOException e) {
			System.err.println(e.toString());
			return null;
		}
	}
}
