package exam.test18;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
	
	CustomDateEditor customDateEditor;
	
	/**
	 * 셋터
	 * @param customDateEditor
	 */
	public void setCustomDateEditor(CustomDateEditor customDateEditor) {
		this.customDateEditor = customDateEditor;
	}

	/**
	 * 프로퍼티 에디터 등록
	 * @param registry
	 */
	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(java.util.Date.class, customDateEditor);
	}
	
}