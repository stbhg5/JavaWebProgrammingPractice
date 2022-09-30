package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

//mybatis 적용에 필요한 변경
public class ApplicationContext {
	
	//객체 저장할 보관소
	Hashtable<String,Object> objTable = new Hashtable<String,Object>();

	//객체 꺼낼 메서드(getter)
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	/**
	 * 외부에서 생성한 객체 등록
	 * @param name : 객체 이름
	 * @param obj : 객체 주소
	 */
	public void addBean(String name, Object obj) {
		objTable.put(name, obj);
	}
	
	/**
	 * 프로퍼티 파일의 내용 로딩 후 객체 준비 메서드
	 * @param propertiesPath : 프로퍼티 파일의 경로
	 * @throws Exception
	 */
	public void prepareObjectsByProperties(String propertiesPath) throws Exception {
		//'이름=값'형태로 된 파일 다루는 Properties 클래스
		Properties props = new Properties();
		//load() : FileReader를 통해 읽어드린 프로퍼티 내용 키-값 형태로 내부 맵에 보관
		props.load(new FileReader(propertiesPath));
		//JNDI 객체 찾을 때 사용할 InitialContext 준비
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		for(Object item : props.keySet()) {
			key = (String)item;
			value = props.getProperty(key);
			if(key.startsWith("jndi.")) {
				//lookup() : JNDI 인터페이스 통해 톰캣 서버에 등록된 객체 찾음
				objTable.put(key, ctx.lookup(value));
			}else {//사용안함
				//Class.forName() 호출하여 클래스 로딩하고, newInstance() 사용하여 인스턴스 생성
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	/**
	 * 어노테이션이 붙은 클래스를 찾아 객체 준비 메서드
	 * @param basePackage : 어노테이션을 검색할 패키지 이름
	 * @throws Exception
	 */
	public void prepareObjectsByAnnotation(String basePackage) throws Exception {
		Reflections reflector = new Reflections(basePackage);
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list) {
			key = clazz.getAnnotation(Component.class).value();
			objTable.put(key, clazz.newInstance());
		}
	}
	
	/**
	 * 각 객체가 필요로 하는 의존 객체 주입 메서드
	 * @throws Exception
	 */
	public void injectDependency() throws Exception {
		for(String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
		}
	}

/*
	//ApplicationContext 클래스의 생성자
	public ApplicationContext(String propertiesPath) throws Exception {
		//'이름=값'형태로 된 파일 다루는 Properties 클래스
		Properties props = new Properties();
		//load() : FileReader를 통해 읽어드린 프로퍼티 내용 키-값 형태로 내부 맵에 보관
		props.load(new FileReader(propertiesPath));
		prepareObjects(props);
		prepareAnnotationObjects();
		injectDependency();
	}

	//프로퍼티 파일의 내용 로딩 후 객체 준비 메서드
	private void prepareObjects(Properties props) throws Exception {
		//JNDI 객체 찾을 때 사용할 InitialContext 준비
		Context ctx = new InitialContext();
		String key = null;
		String value = null;

		for(Object item : props.keySet()) {
			key = (String)item;
			value = props.getProperty(key);
			if(key.startsWith("jndi.")) {//keySet() : key목록 가져옴
				//lookup() : JNDI 인터페이스 통해 톰캣 서버에 등록된 객체 찾음
				objTable.put(key, ctx.lookup(value));
			}else {//사용안함
				//Class.forName() 호출하여 클래스 로딩하고, newInstance() 사용하여 인스턴스 생성
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	//어노테이션이 붙은 클래스를 찾아 객체 준비 메서드
	private void prepareAnnotationObjects() throws Exception {
		//Reflections 클래스 : 원하는 클래스를 찾아주는 도구
		Reflections reflector = new Reflections("");
		//@Component 어노테이션 선언된 클래스 찾아 클래스 목록 반환
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list) {
			//어노테이션에 정의된 메서드 호출하여 속성값 꺼냄
			key = clazz.getAnnotation(Component.class).value();
			//어노테이션을 통해 알아낸 객체 이름(key)으로 인스턴스 저장
			objTable.put(key, clazz.newInstance());
		}
	}
	
	//각 객체가 필요로 하는 의존 객체 주입 메서드
	private void injectDependency() throws Exception {
		for(String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
		}
	}
*/

	//매개변수로 주어진 객체에 대해 셋터 메서드 찾아서 호출
	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		for(Method m : obj.getClass().getMethods()) {
			if(m.getName().startsWith("set")) {
				dependency = findObjectByType(m.getParameterTypes()[0]);
				//의존 객체 찾았다면 셋터 메서드 호출
				if(dependency != null) {
					m.invoke(obj, dependency);
				}
			}
		}
	}

	//셋터 메서드의 매개변수 타입이 일치하는 객체를 objTable에서 찾음 - 셋터 메서드 호출할 때 넘겨줄 의존 객체 찾음
	private Object findObjectByType(Class<?> type) {
		for(Object obj : objTable.values()) {
			//타입이 일치하면 객체의 주소 리턴
			if(type.isInstance(obj)) {//isInstance() : 주어진 객체가 해당 클래스 또는 인터페이스의 인스턴스인지 검사
				return obj;
			}
		}
		return null;
	}
	
}