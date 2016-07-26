package readability;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
	private String code;
	private Map<String, String> methodMap;
	private List<String> words;

	Program(String code) {
		this.code = code;
		methodMap = new HashMap<String, String>();
		words = new ArrayList<String>();
		splitIntoMethods();
		splitIntoWords();
	}

	private void splitIntoMethods() {
		StringBuilder sb = new StringBuilder();
		String methodName = "";

		for(int i = 0, openedCurlyBracket = 0; i < code.length(); i++) {
			switch(code.charAt(i)) {
			case '{':
				if(isSurroundedBySingleQuotation(code, i)) {
					sb.append('{');
					continue;
				}

				openedCurlyBracket++;
				if(openedCurlyBracket == 2) { // beginning of method
					if(code.charAt(i + 1) == '\n')
						i++; // skip newline after open bracket

					methodName = extractMethodName(sb);
					sb = new StringBuilder();
					continue;
				}
				break;

			case '}':
				if(isSurroundedBySingleQuotation(code, i)) {
					sb.append('}');
					continue;
				}

				openedCurlyBracket--;
				if(openedCurlyBracket == 1) { // end of method
					methodMap.put(methodName, sb.toString());
					continue;
				}
				break;
			}

			sb.append(code.charAt(i));
		}
	}

	private boolean isSurroundedBySingleQuotation(String s, int index) {
		return s.charAt(index - 1) == '\'' && s.charAt(index + 1) == '\'';
	}

	Map<String, String> getMethodMap() {
		return methodMap;
	}

	@Override
	public String toString() {
		return code;
	}

	private void splitIntoWords() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			if(CharUtil.isSeparator(c) || CharUtil.isBracket(c) || c == '.' || c == ';') {
				if(sb.length() > 0) {
					words.add(sb.toString());
					sb = new StringBuilder();
				}
				continue;
			} else {
				sb.append(c);
			}
		}
	}

	double averageWordLength() {
		double sum = 0;
		for(String word : words) {
			sum += word.length();
		}
		return sum / words.size();
	}

	double averageSentenceLength() {
		return (double) words.size() / numberOfSentences();
	}

	private int numberOfSentences() {
		int n = 0;
		for(int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			if(c == '{' || c == ';')
				n++;
		}
		return n;
	}

	double calcSRES() {
		return averageSentenceLength() - 0.1 * averageWordLength();
	}

	private static String extractMethodName(StringBuilder sb) {
		StringBuilder name = new StringBuilder();
		for(int i = sb.length() - 1; !(name.toString().contains("(") && CharUtil.isSeparator(sb.charAt(i))); i--) {
			name.append(sb.charAt(i));
		}

		if(CharUtil.isSeparator(name.charAt(0))) {
			name.deleteCharAt(0);
		}

		return name.reverse().toString();
	}

	public static void main(String args[]) {
		String code = Main.readFile("./src/Program.java");
		Program program = new Program(code);

		for(String key : program.methodMap.keySet()) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(program.getMethodMap().get(key));
		}

		for(String s : program.words) {
			// System.out.println(s);
		}

		System.out.println(program.calcSRES());
	}
}
