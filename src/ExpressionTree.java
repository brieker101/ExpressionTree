import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import com.sun.xml.internal.ws.util.StringUtils;
public class ExpressionTree {
	
	private Node root;
	private boolean rootSet;
	private String[] operators;
	private boolean parenthesis;
	private boolean formatted;
	private String[] numbers;
	
	public ExpressionTree() {
		rootSet = false;
		operators = new String[] {"-", "+", "/", "*", "^"};
		parenthesis = false;
	}
	
	public Node parse(String s) {
		if(formatted==false) {
			s = s.trim();
			s.replaceAll("\\s", "");
			s.replaceAll("\\t", "");
			formatted = true;
		}
		
		int lastOperatorIndex;
		int operatorCount;
		
		operatorCount = operatorCounter(s);
		
		if(operatorCount==0) {
			return new Node(s);
		}
		if(operatorCount==1&&s.substring(0, 1).equals("(")&&s.substring(s.length()-1, s.length()).equals(")")) {
			s = s.substring(1, s.length()-1);
		}
		
		lastOperatorIndex = operatorLocator(s);
		
		if(lastOperatorIndex!=-1) {
			if(rootSet==false) {
				rootSet = true;
				root = new Node(s.substring(lastOperatorIndex, lastOperatorIndex+1));
				root.left = parse(s.substring(0, lastOperatorIndex));
				root.right = parse(s.substring(lastOperatorIndex+1, s.length()));
			}
			else if(operatorCount==1){
				Node operatorNode = new Node(s.substring(lastOperatorIndex, lastOperatorIndex+1));
				operatorNode.left = new Node(s.substring(0, lastOperatorIndex));
				operatorNode.right = new Node(s.substring(lastOperatorIndex+1, s.length()));
				return operatorNode;	
			}
			else {
				Node operatorNode = new Node(s.substring(lastOperatorIndex, lastOperatorIndex+1));
				operatorNode.left = parse(s.substring(0, lastOperatorIndex));
				operatorNode.right = parse(s.substring(lastOperatorIndex+1, s.length()));
				return operatorNode;	
			}
		}
		
		return root;
	}
	
	public int operatorLocator(String s) {
		int location = -1;
		for(int i = 0;i<operators.length;i++) {
			for(int j = s.length();j>0;j--) {
				if(s.substring(j-1, j).equals(")")) {
					parenthesis = true;
				}
				if(s.substring(j-1, j).equals("(")){
					if(parenthesis==true) {
						parenthesis = false;
					}
					else {
						return -1;
					}
				}
				if(s.substring(j-1, j).equals(operators[i])&&parenthesis==false) {
					if((j-1)==0||(j-1)==s.length()-1) {
						return -1;
					}
					else {
						location = j-1;
						return location;
					}
				}
			}
		}
		return location;
	}
	
	public int operatorCounter(String s) {
		int count = 0;
		for(int i = 0;i<operators.length;i++) {
			for(int j = 0;j<s.length();j++) {
				if(s.substring(j, j+1).equals(operators[i])) {
					count++;
				}
			}		
		}
		return count;
	}
	public double solve() {
		return _solve(root);
	}
	private double _solve(Node s) {
		if(isNumeric(s.data)) {
			return Double.parseDouble(s.data);
		}
		if(s.data.equals("-")) {
			return _solve(s.left) - _solve(s.right);
		}
		if(s.data.equals("+")) {
			return _solve(s.left) + _solve(s.right);
		}
		if(s.data.equals("/")) {
			return _solve(s.left) / _solve(s.right);
		}
		if(s.data.equals("*")) {
			return _solve(s.left) * _solve(s.right);
		}
		if(s.data.equals("^")) {
			return Math.pow(_solve(s.left), _solve(s.right));
		}
		return 0;
	}
	
	public boolean isOperator(String s) {
		if(s.length()>1) {
			return false;
		}
		for(int i = 0;i<operators.length;i++) {
				if(s.substring(0, 1).equals(operators[i])||s.substring(0, 1).equals("(")||s.substring(0, 1).equals(")")){
					return true;
				}
		}
		return false;
	}
	public boolean isNumeric(String s) {
		try{
			Double.parseDouble(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	public String bfs() {
		Queue<Node> list1 = new ArrayDeque<Node>();
		Queue<Node> list2 = new ArrayDeque<Node>();
		String output ="";
		
		int childCounter;
		list1.add(root);
		while(list1.size()>0||list2.size()>0) {
			childCounter = 0;
			output = output + "\n";
			while(list1.size()>0) {
				if(list1.peek().left!=null) {
					list2.add(list1.peek().left);
				}
				if(list1.peek().right!=null) {
					list2.add(list1.peek().right);
					}
				if(childCounter==1) {
					output = output + list1.poll().data + "\t" + "\t";
					childCounter = 0;
				}
				else {
					output = output + list1.poll().data + "\t";
					childCounter++;
				}
			}
			output = output + "\n";
			while(list2.size()>0) {
				if(list2.peek().left!=null) {
					list1.add(list2.peek().left);
				}
				if(list2.peek().right!=null) {
					list1.add(list2.peek().right);
					}
				if(childCounter==1) {
					output = output + list2.poll().data + "\t" + "\t";
					childCounter = 0;
				}
				else {
					output = output + list2.poll().data + "\t";
					childCounter++;
				}
			}
		}
		return output;
	}
	
	public String postorder() {
		return _postorder(root);
	}
	private String _postorder(Node s) {
		String output = "";
		if(s.left == null && s.right==null) {
			return s.data;
		}
		output = output  +  _postorder(s.left)  +  _postorder(s.right)  +  s.data ;
		return output;
	}
	
	public String preorder() {
		return _preorder(root);
	}
	private String _preorder(Node s) {
		String output = "";
		if(s.left == null && s.right==null) {
			return s.data;
		}
		output = output  + s.data + _preorder(s.left) + _preorder(s.right)  ;
		
		return output;
	}
	
	public String inorder() {
		return _inorder(root);
	}
	private String _inorder(Node s) {
		String output = "";
		if(s.left == null && s.right==null) {
			return s.data;
		}
		output = output  +  _inorder(s.left) +  s.data +  _inorder(s.right) ;
	
		return output;
	}

	public int textChecker(String s) {
		boolean pCheck = false;
		for(int i = 0;i<operators.length;i++) {
			if(s.substring(0, 1).equals(operators[i])||s.substring(s.length()-1, s.length()).equals(operators[i])) {
				return -2;
			}
		}
		for(int i = 0;i<s.length();i++) {
			if(i<s.length()-1) {
				for(int j =0;j<operators.length;j++) {
					if(s.substring(i, i+1).equals(operators[j])) {
						for(int k = 0;k<operators.length;k++) {
							if(s.substring(i+1, i+2).equals(operators[k])) {
								return -2;
							}
						}
					}
				}
			}
			
			if(s.substring(i, i+1).equals("(")) {
				pCheck = true;
			}
			if(s.substring(i, i+1).equals(")")) {
				if(pCheck==true) {pCheck=false;}
				else {return -1;}
			}
			if(!isNumeric(s.substring(i, i+1))&&!isOperator(s.substring(i, i+1))){
				return -3;
			}
		}
		if(pCheck==true) {
			return -1;
		}
		return 0;
	}
	
	private class Node  {
		Node left;
		Node right;
		String data;
		public Node(String s) {
			data = s;
		}
		public void setLeft(String s) {
			left = new Node(s);
		}
		public void setRight(String s) {
			right = new Node(s);
		}
	}
}
