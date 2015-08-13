package testplus2.game.jmx;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author solq
 * */
public class ReflectionUtils {

	/**
	 * ѭ������ת��, ��ȡ����� DeclaredMethod
	 * 
	 * @param clz
	 *            : ����
	 * @param methodName
	 *            : �����еķ�����
	 * @param parameterTypes
	 *            : �����еķ�����������
	 * @return �����еķ�������
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
	 * ֱ�ӵ��ö��󷽷�, ���������η�(private, protected, default)
	 * 
	 * @param object
	 *            : �������
	 * @param methodName
	 *            : �����еķ�����
	 * @param parameterTypes
	 *            : �����еķ�����������
	 * @param parameters
	 *            : �����еķ�������
	 * @return �����з�����ִ�н��
	 */

	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameters) {
		Method method = getDeclaredMethod(object.getClass(), methodName, parameterTypes);
		if (method == null) {
			throw new RuntimeException("δ�ҵ���ӳ���� :" + methodName + " class : "
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
	 * ѭ������ת��, ��ȡ����� DeclaredField
	 * 
	 * @param clz
	 *            : ����
	 * @param fieldName
	 *            : �����е�������
	 * @return �����е����Զ���
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
	 * ֱ�����ö�������ֵ, ���� private/protected ���η�, Ҳ������ setter
	 * 
	 * @param object
	 *            : �������
	 * @param fieldName
	 *            : �����е�������
	 * @param value
	 *            : ��Ҫ���õ�ֵ
	 */

	public static void setFieldValue(Object object, String fieldName,
			Object value) {

		Field field = getDeclaredField(object.getClass(), fieldName);
		if (field == null) {
			throw new RuntimeException("δ�ҵ���ӳ�ֶ� :" + fieldName + " class : "
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
	 * ֱ�Ӷ�ȡ���������ֵ, ���� private/protected ���η�, Ҳ������ getter
	 * 
	 * @param object
	 *            : �������
	 * @param fieldName
	 *            : �����е�������
	 * @return : �����е�����ֵ
	 */

	public static Object getFieldValue(Object object, String fieldName) {

		Field field = getDeclaredField(object.getClass(), fieldName);
		if (field == null) {
			throw new RuntimeException("δ�ҵ���ӳ�ֶ� :" + fieldName + " class : "
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