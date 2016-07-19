import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static raykernel.apps.readability.eval.Main.getReadability;

public class Main {
	public static void main(String args[]) {
		String code = readFile("./src/Program.java");
		if(code == null) return;
		
		double score = getReadability(code);
		System.out.println(score);
		
		Program program = new Program(code);
		for(String s : program.getMethodList()) {
			double scoreOfMethod = getReadability(s);
			System.out.println(scoreOfMethod);
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
