package biz;

import java.util.regex.Pattern;

import entity.FormationTree;
import entity.FormationTree.Connective;

public class Parser {
	public static FormationTree parsePropositon(String p) {
		if (p.startsWith("(")) {

			// unary
			if (p.startsWith("(\\not")) {
				String child = p.substring("(\\not".length() + 1, p.length() - 1);
				// System.out.println(child);
				return new FormationTree(Connective.NOT, parsePropositon(child));
			}

			// binary
			else {
				// parse the left proposition
				int leftParentheses = 0, leftPropEndIndex = 0;

				// if it's still a compound proposition
				if (p.charAt(1) == '(') {
					for (leftPropEndIndex = 1; leftPropEndIndex < p.length() - 1; leftPropEndIndex++) {
						if (p.charAt(leftPropEndIndex) == '(')
							leftParentheses++;
						else if (p.charAt(leftPropEndIndex) == ')')
							leftParentheses--;
						if (leftParentheses == 0) {
							break;
						}
					}
					// return the invalid tree if there's no valid proposition
					if (leftPropEndIndex == p.length() - 1)
						return FormationTree.invalidTree();
				} else
				// if it's a proposition letter
				{
					for (int i = 1; i < p.length(); i++) {
						if (p.charAt(i) != ' ')
							leftPropEndIndex++;
						else
							break;
					}
				}
				String left = p.substring(1, leftPropEndIndex + 1);

				// parse the connective
				int connEndIndex = leftPropEndIndex + 2;
				for (int i = leftPropEndIndex + 2; i < p.length(); i++) {
					if (p.charAt(i) != ' ') {
						connEndIndex++;
					} else {
						break;
					}
				}
				String connective = p.substring(leftPropEndIndex + 2, connEndIndex);
				Connective c = null;
				switch (connective) {
				case "\\and":
					c = Connective.AND;
					break;
				case "\\or":
					c = Connective.OR;
					break;
				case "\\not":
					c = Connective.NOT;
					break;
				case "\\imply":
					c = Connective.IMPLY;
					break;
				case "\\eq":
					c = Connective.EQ;
					break;
				// return the invalid tree if there's no valid connective
				default:
					return FormationTree.invalidTree();
				}

				// parse the right proposition
				String right = p.substring(connEndIndex + 1, p.length() - 1);

				return new FormationTree(c, parsePropositon(left), parsePropositon(right));
			}
		}

		// proposition letter
		else {
			if (checkLetter(p))
				return new FormationTree(p);
			else
				return FormationTree.invalidTree();
		}
	}

	// return a boolean whether p is a proposition letter
	private static boolean checkLetter(String p) {
		return Pattern.compile("^[A-Z]+(_\\{[0-9]+\\})?$").matcher(p).matches();
	}
}
