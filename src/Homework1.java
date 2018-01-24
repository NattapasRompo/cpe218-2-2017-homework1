import  java.util.Stack;


public class Homework1 {

	public static Node tree;
	public static Stack<Character> KK = new Stack<Character>();




	public static void inorder(Node node) {
		if (node.k == '+') {
			if (node != tree) System.out.print("(");
			inorder(node.left);
			System.out.print("+");
			inorder(node.right);
			if (node != tree) System.out.print(")");

		} else if (node.k == '-') {
			if (node != tree) System.out.print("(");
			inorder(node.left);
			System.out.print("-");
			inorder(node.right);
			if (node != tree) System.out.print(")");

		} else if (node.k == '*') {
			if (node != tree) System.out.print("(");
			inorder(node.left);
			System.out.print("*");
			inorder(node.right);
			if (node != tree) System.out.print(")");

		} else if (node.k == '/') {
			if (node != tree) System.out.print("(");
			inorder(node.left);
			System.out.print("/");
			inorder(node.right);
			if (node != tree) System.out.print(")");

		} else System.out.print(node.k);

	}

	public static void infix(Node inf) {
		if (inf.k == '+' || inf.k == '-' || inf.k == '*' || inf.k == '/') {
			inf.right = new Node(KK.pop());
			infix(inf.right);
			inf.left = new Node(KK.pop());
			infix(inf.left);
		}
	}

	public static int calculate(Node cal) {
		if (cal.k == '+') {
			return calculate(cal.left) + calculate(cal.right);
		} else if (cal.k == '-') {
			return calculate(cal.left) - calculate(cal.right);
		} else if (cal.k == '*') {
			return calculate(cal.left) * calculate(cal.right);
		} else if (cal.k == '/') {
			return calculate(cal.left) / calculate(cal.right);
		} else return Integer.parseInt(cal.k.toString());
	}

	public static void main(String[] args) {
		// Begin of arguments input sample
		//String profix = "251-*32*+";
		String profix = args[0];
		for (int i = 0; i < profix.length(); i++) {
			KK.add(profix.charAt(i));
		}
		tree = new Node(KK.pop());
		infix(tree);
		inorder(tree);
		calculate(tree);
		System.out.print("=" + calculate(tree));
		tree_demo.main(args);
		// End of arguments input sample

		// TODO: Implement your project here
	}

	//public class Homework1 extends JPanel implements TreeSelectionListener {


}


