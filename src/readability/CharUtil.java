package readability;

public class CharUtil {
	static boolean isSeparator(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}

	static boolean isBracket(char c) {
		return c == '(' || c == ')' || c == '{' || c == '}';
	}
}
