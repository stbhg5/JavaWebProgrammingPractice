package lesson01.exam01;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame implements ActionListener {
	//윈도우 기능 갖기 위해 JFrame클래스 상속
	//=과 clear 버튼 클릭 이벤트를 처리하는(리스너) ActionListener인터페이스 구현
	
	//선언
	JTextField operand1 = new JTextField(4);
	JTextField operand2 = new JTextField(4);
	String[] operatorData = {"+", "-", "*", "/"};
	JComboBox<String> operator = new JComboBox<String>(operatorData);
	JButton equal = new JButton("=");
	JTextField result = new JTextField(6);
	JButton clear = new JButton("Clear");
	
	//생성자
	public CalculatorFrame() {
		this.setTitle("Lesson01-Exam01");
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		contentPane.add(Box.createVerticalGlue());
		
		//메소드 실행
		contentPane.add(this.createInputForm());
		contentPane.add(this.createToolBar());
		
		contentPane.add(Box.createVerticalGlue());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == equal) {
			compute();
		} else {
			clearForm();
		}
	}
	
	private void compute() {
		double a = Double.parseDouble(operand1.getText()); 
		double b = Double.parseDouble(operand2.getText());
		double r = 0;
		
		try {
			switch (operator.getSelectedItem().toString()) {
			case "+": r = a + b; break;
			case "-": r = a - b; break;
			case "*": r = a * b; break;
			case "/": 
				if (b == 0) throw new Exception("0 으로 나눌 수 없습니다!");
				r = a / b; break;
			}
			
			result.setText(Double.toString(r));
			
		} catch (Exception err) {
			JOptionPane.showMessageDialog(
				null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void clearForm() {
		this.operand1.setText("");
		this.operand2.setText("");
		this.result.setText("");
	}
	
	//=에 이벤트 건다.
	private Box createInputForm() {
		Box box = Box.createHorizontalBox();
		box.setMaximumSize(new Dimension(300, 30));
		box.setAlignmentY(Box.CENTER_ALIGNMENT);
		box.add(operand1);
		box.add(operator);
		box.add(operand2);
		box.add(equal);
		box.add(result);
		equal.addActionListener(this);
		return box;
	}
	
	//clear에 이벤트 건다.
	private Box createToolBar() {
		Box box = Box.createHorizontalBox();
		box.add(clear);
		clear.addActionListener(this);
		return box;
	}
	
	public static void main(String[] args) {
		CalculatorFrame app = new CalculatorFrame();
		//생성자 실행
		app.setVisible(true);
		System.out.println("Test");
	}
}
