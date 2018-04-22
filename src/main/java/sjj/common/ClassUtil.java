package sjj.common;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ClassUtil {

	public static String getIdKey(Class<?> class1) {
		Field[] fields = class1.getDeclaredFields();
		for (Field field : fields) {
			String filedName = field.getName();
			// 获取属性上指定类型的注解
			Annotation annotation = field.getAnnotation(Id.class);
			// 有该类型的注解存在
			if (annotation != null) {
				return ClassUtil.humpName(filedName);
			}
		}
		return "";
	}

	/**
	 * 转换为下划线
	 */
	public static String underscoreName(String camelCaseName) {
		StringBuilder result = new StringBuilder();
		if (camelCaseName != null && camelCaseName.length() > 0) {
			result.append(camelCaseName.substring(0, 1).toLowerCase());
			for (int i = 1; i < camelCaseName.length(); i++) {
				char ch = camelCaseName.charAt(i);
				if (Character.isUpperCase(ch)) {
					result.append("_");
					result.append(Character.toLowerCase(ch));
				} else {
					result.append(ch);
				}
			}
		}
		return result.toString();
	}

	/**
	 * 转换为驼峰
	 */
	public static String humpName(String underscoreName) {
		StringBuilder result = new StringBuilder();
		if (underscoreName != null && underscoreName.length() > 0) {
			boolean flag = false;
			for (int i = 0; i < underscoreName.length(); i++) {
				char ch = underscoreName.charAt(i);
				if ("_".charAt(0) == ch) {
					flag = true;
				} else {
					if (flag) {
						result.append(Character.toUpperCase(ch));
						flag = false;
					} else {
						result.append(ch);
					}
				}
			}
		}
		return result.toString();
	}

}