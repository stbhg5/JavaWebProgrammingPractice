package lesson02.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SimpleHttpClient {

	public static void main(String[] args) throws Exception {
		
		//1. 소켓 및 입출력 스트림 준비
		//Socket socket = new Socket("www.hani.co.kr", 80);
		Socket socket = new Socket("www.daum.net", 80); //웹 서버 기본 포트번호 : 80
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream out = new PrintStream(socket.getOutputStream());

		//2. 요청(request)라인 출력
		out.println("GET / HTTP/1.1"); //웹 서버 루트폴더에 있는 기본문서(/)

		//3. 헤더정보 출력
		//out.println("Host: www.hani.co.kr");
		out.println("Host: www.daum.net"); //접속하려는 웹 서버 주소
		out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0)" //요청자 정보
				+ " AppleWebKit/537.36 (KHTML, like Gecko)"
				+ " Chrome/30.0.1599.101 Safari/537.36");
		
		//4. 공백라인 출력
		out.println(); //요청의 끝 표시
		
		//5. 응답(response) 내용 출력
		String line = null;
		while((line = in.readLine()) != null) {//서버로부터 받은 데이터 line 단위로 표시
			System.out.println(line);
		}

		in.close();
		out.close();
		socket.close();
		
	}

}
