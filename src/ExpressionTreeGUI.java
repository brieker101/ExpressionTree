import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ExpressionTreeGUI {
	
	private JFrame graphic;
	private JButton parse;
	private JTextField input;
	private JTextField inorderTitle;
	private JTextField preorderTitle;
	private JTextField postorderTitle;
	private JTextField equals;
	private JTextField answer;
	private JTextPane output;
	private JTextPane inorder;
	private JTextPane preorder;
	private JTextPane postorder;
	private JLabel inputTitle;
	
	
	public ExpressionTreeGUI() {
		generateFields();
		generateListeners();
		setBounds();
		buildLayout();			
	}
	
	public void generateFields() {
		graphic = new JFrame("Expression Tree Calculator");
		
		parse = new JButton("Create Expression Tree");
		
		input = new JTextField();
		inorderTitle = new JTextField("In-order Sequence:");
		preorderTitle = new JTextField("Pre-order Sequence:");
		postorderTitle = new JTextField("Post-order Sequence:");
		equals = new JTextField("=");
		answer = new JTextField();
		
		output = new JTextPane();
		inorder = new JTextPane();
		preorder = new JTextPane();
		postorder = new JTextPane();
		
		inputTitle = new JLabel("Equation:");
	}
	
	public void generateListeners() {
		parse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExpressionTree tree = new ExpressionTree();
				StyledDocument doc = output.getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
				int inputCorrector = tree.textChecker(input.getText());
				if(inputCorrector==0){
					tree.parse(input.getText());
					output.setText(tree.bfs());
					inorder.setText(tree.inorder());
					preorder.setText(tree.preorder());
					postorder.setText(tree.postorder());
					answer.setText(tree.solve() + "");
				}
				else if(inputCorrector==-1) {output.setText("Too Many Parenthesis!");}
				else if(inputCorrector==-2) {output.setText("Too Many Operators!");}
				else if(inputCorrector==-3) {output.setText("Incorrect Entries Found!");}
				
			}
		});
	    graphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setBounds() {
		inputTitle.setLabelFor(input);
		inputTitle.setBounds(120, 30, 300, 30);
		inputTitle.setVerticalAlignment(JLabel.TOP);
		inputTitle.setHorizontalAlignment(JLabel.CENTER);
		
		input.setBounds(25, 50, 500, 50);
		input.setHorizontalAlignment(JTextField.CENTER);
		
		output.setBounds(25, 160, 500, 390);
		
		equals.setBounds(530, 50, 20, 50);
		
		answer.setBounds(550, 20, 220, 100);
		
		inorderTitle.setBounds(550, 220, 220, 20);
		inorderTitle.setBorder(null);
		inorder.setBounds(550, 250, 220, 20);
		postorderTitle.setBounds(550, 320, 220, 20);
		postorderTitle.setBorder(null);
		postorder.setBounds(550, 350, 220, 20);
		preorderTitle.setBounds(550, 420, 220, 20);
		preorderTitle.setBorder(null);
		preorder.setBounds(550, 450, 220, 20);
		
		parse.setBounds(120, 105, 300, 30); 
		parse.setVerticalAlignment(JButton.TOP);
		parse.setHorizontalAlignment(JButton.CENTER);
		
	}
	
	public void buildLayout() {
		output.setEditable(false);
		inorderTitle.setEditable(false);
		inorder.setEditable(false);
		preorderTitle.setEditable(false);
		preorder.setEditable(false);
		postorderTitle.setEditable(false);
		postorder.setEditable(false);
		equals.setBorder(null);
		equals.setEditable(false);
		answer.setEditable(false);
		answer.setBackground(Color.WHITE);
		graphic.add(answer);
		graphic.add(equals);
		graphic.add(inorderTitle);
		graphic.add(postorderTitle);
		graphic.add(preorderTitle);
		graphic.add(parse);
		graphic.add(input);
		graphic.add(inputTitle);
		graphic.add(output);
		graphic.add(inorder);
		graphic.add(preorder);
		graphic.add(postorder);
		graphic.setSize(800, 600); 
		graphic.setLayout(new BorderLayout()); 
		graphic.setVisible(true);		
	}
	
	public static void main(String[] args) {
		ExpressionTreeGUI test = new ExpressionTreeGUI();
	}
	
	
	
	
	
	
	
	
	
	
	

}
