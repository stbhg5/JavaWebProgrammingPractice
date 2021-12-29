package lesson01.exam02.multiserver;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorWorker extends Thread {//스레드를 상속받아 스레드 기능을 수행
	
	//선언
	static int count;
	
	Socket socket;
	Scanner in;
	PrintStream out;
	int workerId;
	
	public CalculatorWorker(Socket socket) throws Exception {
		workerId = ++count;
		this.socket = socket;
		//입출력 위한 스트림 객체 준비
		in = new Scanner(socket.getInputStream());
		out = new PrintStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {//스레드를 시작시키면 부모 스레드의 흐름으로부터 분리되어 별개의 흐름으로 run() 실행 → 부모스레드와 자식스레드 병행으로 작업 수행
		System.out.println("[thread-" + workerId + "] processing the client request.");
		
		String operator = null;
		double a, b, r;
		
		while(true) {
			try {
				operator = in.nextLine();
				
				if (operator.equals("goodbye")) {
					out.println("goodbye");
					break;
					
				} else {
					a = Double.parseDouble(in.nextLine());
					b = Double.parseDouble(in.nextLine());
					r = 0;
				
					switch (operator) {
					case "+": r = a + b; break;
					case "-": r = a - b; break;
					case "*": r = a * b; break;
					case "/": 
						if (b == 0) throw new Exception("0 으로 나눌 수 없습니다!");
						r = a / b; 
						break;
					default:
						throw new Exception("해당 연산을 지원하지 않습니다!");
					}
					out.println("success");
					out.println(r);
				}
				
			} catch (Exception err) {
				out.println("failure");
				out.println(err.getMessage());
			}
		}
		
		try { out.close(); } catch (Exception e) {}
		try { in.close(); } catch (Exception e) {}
		try { socket.close(); } catch (Exception e) {}
		
		System.out.println("[thread-" + workerId + "] closed client.");
	}
}
