package entity;

public class FormationTree {
	public enum Connective {
		AND("\\and"), OR("\\or"), NOT("\\not"), IMPLY("\\imply"), EQ("\\eq");
		String str;

		Connective(String str) {
			this.str = str;
		}

		public String toString() {
			switch (str) {
			case "\\and":
				return "∧";
			case "\\or":
				return "∨";
			case "\\not":
				return "¬";
			case "\\imply":
				return "→";
			case "\\eq":
				return "↔";
			}
			return null;
		}
	}

	public class Letter {
		private String s;

		public Letter(String s) {
			this.s = s;
		}

		public String toString() {
			return s;
		}
	}

	private FormationTree left, right;
	private Letter letter;
	private Connective connective;
	private boolean valid = false;
	private static FormationTree invalidTree;

	public FormationTree(Connective connective, FormationTree left, FormationTree right) {
		this.connective = connective;
		this.left = left;
		this.right = right;
		if (!(!this.left.isValid() || !this.right.isValid()))
			this.valid = true;
	}

	public FormationTree(Connective connective, FormationTree left) {
		this.connective = connective;
		if (connective == Connective.NOT)
			this.left = left;
		if (this.left.isValid())
			this.valid = true;
	}

	public FormationTree(String letter) {
		this.letter = new Letter(letter);
		this.valid = true;
	}

	public static FormationTree invalidTree() {
		if (invalidTree == null) {
			invalidTree = new FormationTree();
		}
		return invalidTree;
	}

	private FormationTree() {
		this.valid = false;
	}

	public FormationTree getChild() {
		return left;
	}

	public FormationTree getLeft() {
		return left;
	}

	public void setLeft(FormationTree left) {
		this.left = left;
	}

	public FormationTree getRight() {
		return right;
	}

	public void setRight(FormationTree right) {
		this.right = right;
	}

	public Connective getConnective() {
		return connective;
	}

	public Letter getLetter() {
		return letter;
	}

	public int childCount() {
		if (this.connective == null) {
			return 0;
		} else if (this.connective == Connective.NOT) {
			return 1;
		} else {
			return 2;
		}
	}

	public String toString() {
		if (!isValid()) {
			return "invalid";
		}
		if (this.connective == null) {
			return this.letter.toString();
		} else if (this.connective == Connective.NOT) {
			return "(" + connective.toString() + " " + left.toString() + ")";
		} else {
			return "(" + left.toString() + " " + connective.toString() + " " + right.toString() + ")";
		}
	}

	public String toStringYN() {
		if (isValid()) {
			return "yes";
		} else {
			return "no";
		}
	}

	public boolean isValid() {
		return valid;
	}

	public int getLevel() {
		if (this.childCount() == 0)
			return 1;
		else if (this.childCount() == 1)
			return this.getChild().getLevel() + 1;
		else {
			int ll = this.left.getLevel();
			int rl = this.right.getLevel();
			return (ll > rl) ? ll + 1 : rl + 1;
		}
	}

}
