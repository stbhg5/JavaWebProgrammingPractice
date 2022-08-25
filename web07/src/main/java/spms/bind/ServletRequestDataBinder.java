package spms.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

//클라이언트가 보낸 매개변수 값 자바 객체에 담아줌
public class ServletRequestDataBinder {
	//요청 매개변수의 값, 데이터 이름, 데이터 타입을 받아 데이터 객체를 만드는 일
	public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {
		if(isPrimitiveType(dataType)) {
			//기본 타입의 경우 셋터 메서드가 없기 때문에 객체를 생성하는 메서드
			return createValueObject(dataType, request.getParameter(dataName));
		}

		Set<String> paramNames = request.getParameterMap().keySet(); //매개변수 키목록
		Object dataObject = dataType.newInstance(); //해당 클래스의 인스턴스 얻는다
		Method m = null;

		for(String paramName : paramNames) {
			m = findSetter(dataType, paramName);
			if(m != null) {
				//dataObject에 대해 m메서드 호출 - createValueObject()로 객체생성한 매개변수 가지고 셋터 메서드 실행
				//createValueObject(셋터 메서드의 매개변수 타입(매개변수 목록 배열로 반환), 요청 매개변수 타입)
				m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], request.getParameter(paramName)));
			}
		}
		return dataObject;
	}

	//dataType이 기본 타입인지 아닌지 검사
	private static boolean isPrimitiveType(Class<?> type) {
		if(type.getName().equals("int") || type == Integer.class
		|| type.getName().equals("long") || type == Long.class
		|| type.getName().equals("float") || type == Float.class
		|| type.getName().equals("double") || type == Double.class
		|| type.getName().equals("boolean") || type == Boolean.class
		|| type == Date.class || type == String.class) {
			return true;
		}
		return false;
	}

	//요청 매개변수의 값을 가지고 기본 타입의 객체를 생성
	private static Object createValueObject(Class<?> type, String value) {
		if(type.getName().equals("int") || type == Integer.class) {
			return new Integer(value);
		}else if(type.getName().equals("float") || type == Float.class) {
			return new Float(value);
		}else if(type.getName().equals("double") || type == Double.class) {
			return new Double(value);
		}else if(type.getName().equals("long") || type == Long.class) {
			return new Long(value);
		}else if (type.getName().equals("boolean") || type == Boolean.class) {
			return new Boolean(value);
		}else if (type == Date.class) {
			return java.sql.Date.valueOf(value);
		}else {
			return value;
		}
	}

	//데이터 타입(Class)과 매개변수 이름(String)을 주면 셋터 메서드 찾아서 반환
	private static Method findSetter(Class<?> type, String name) {
		Method[] methods = type.getMethods();
		String propName = null;
		for(Method m : methods) {
			if(!m.getName().startsWith("set")) continue; //메서드 이름이 set으로 시작하지 않으면 넘김
			propName = m.getName().substring(3); //index값 0~2까지 제외한 값 가져옴
			if(propName.toLowerCase().equals(name.toLowerCase())) {//toLowerCase() : 대상 문자열을 모두 소문자로 변환
				return m;
			}
		}
		return null;
	}
}