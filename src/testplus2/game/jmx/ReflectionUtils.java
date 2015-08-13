package testplus2.game.jmx;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author solq
 * */
public class ReflectionUtils {

	/**
	 * 循环向上转型, 获取对象的 DeclaredMethod
	 * 
	 * @param clz
	 *            : 子类
	 * @param methodName
	 *            : 父类中的方法名
	 * @param parameterTypes
	 *            : 父类中的方法参数类型
	 * @return 父类中的方法对象
	 */

	public static Method getDeclaredMethod(Class<?> clz, String methodName,
			Class<?>... parameterTypes) {
		Method method = null;

		for (Class<?> clazz = clz; clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (Exception e) {
			}
		}

		return null;
	}

	/**
	 * 直接调用对象方法, 而忽略修饰符(private, protected, default)
	 * 
	 * @param object
	 *            : 子类对象
	 * @param methodName
	 *            : 父类中的方法名
	 * @param parameterTypes
	 *            : 父类中的方法参数类型
	 * @param parameters
	 *            : 父类中的方法参数
	 * @return 父类中方法的执行结果
	 */

	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameters) {
		Method method = getDeclaredMethod(object.getClass(), methodName, parameterTypes);
		if (method == null) {
			throw new RuntimeException("未找到反映方法 :" + methodName + " class : "
					+ object.getClass());
		}
		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * 
	 * @param clz
	 *            : 子类
	 * @param fieldName
	 *            : 父类中的属性名
	 * @return 父类中的属性对象
	 */

	public static Field getDeclaredField(Class<?> clz, String fieldName) {
		Field field = null;
		Class<?> clazz = clz;
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
	 * 
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @param value
	 *            : 将要设置的值
	 */

	public static void setFieldValue(Object object, String fieldName,
			Object value) {

		Field field = getDeclaredField(object.getClass(), fieldName);
		if (field == null) {
			throw new RuntimeException("未找到反映字段 :" + fieldName + " class : "
					+ object.getClass());
		}
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
	 * 
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @return : 父类中的属性值
	 */

	public static Object getFieldValue(Object object, String fieldName) {

		Field field = getDeclaredField(object.getClass(), fieldName);
		if (field == null) {
			throw new RuntimeException("未找到反映字段 :" + fieldName + " class : "
					+ object.getClass());
		}
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}