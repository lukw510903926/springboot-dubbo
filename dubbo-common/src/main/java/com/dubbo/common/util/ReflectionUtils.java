package com.dubbo.common.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.Transient;
import java.lang.reflect.*;
import java.text.DateFormat;
import java.util.*;

/**
 * 反射工具类
 * 
 * @author 26223
 * @time 2016年10月13日 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class,
 *       被AOP过的真实类等工具函数.
 */
public class ReflectionUtils {

	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * 调用Getter方法. 支持多级，如：对象名.对象名.方法
	 */
	public static Object invokeGetter(Object obj, String propertyName) {

		Object object = obj;
		for (String name : StringUtils.split(propertyName, ".")) {
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return object;
	}

	/**
	 * 调用Setter方法, 仅匹配方法名。 支持多级，如：对象名.对象名.方法
	 */
	public static void invokeSetter(Object obj, String propertyName, Object value) {

		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for (int i = 0; i < names.length; i++) {
			if (i < names.length - 1) {
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			} else {
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {

		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {

		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用. 同时匹配方法名+参数类型，
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {

		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符，
	 * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
	 * 只匹配函数名，如果有多个同名函数调用第一个。
	 */
	public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {

		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {

		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null. 匹配函数名+参数类型。
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {

		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(methodName, "methodName can't be blank");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getDeclaredField(Class<?> cls, String fileName) {
		while (!cls.equals(Object.class)) {
			try {
				Field field = cls.getDeclaredField(fileName);
				if (field != null) {
					return field;
				}
			} catch (Exception e) {
			}
			cls = cls.getSuperclass();
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null. 只匹配函数名。
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName) {

		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(methodName, "methodName can't be blank");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处 如无法找到, 返回Object.class. eg. public
	 * UserDao extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static Class<?> getClassGenricType(final Class<?> clazz) {
		return (Class<?>) getClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static Class<?> getClassGenricType(final Class<?> clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class<?>) params[index];
	}

	public static Class<?> getUserClass(Object instance) {

		Assert.notNull(instance, "Instance must not be null");
		Class<?> clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	private static DateFormat dateformat = DateFormat.getDateInstance();

	/**
	 * 获取实例
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className) {

		Object o = null;
		try {
			Class<?> cl = Class.forName(className);
			return newInstance(cl);
		} catch (Exception cl) {
		}
		return o;
	}

	/**
	 * 根据class 获取实例
	 * 
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {

		try {
			Constructor<?> cons = cls.getDeclaredConstructor();
			cons.setAccessible(true);
			return cons.newInstance();
		} catch (Exception cons) {
		}
		return null;
	}

	/**
	 * 根据参数 通过构造方法获取实例
	 * 
	 * @param className
	 * @param args
	 * @return
	 */
	public static Object newInstance(String className, Object[] args) {
		return newInstance(className, objectToClass(args), args);
	}

	/**
	 * 根据参数 通过构造方法获取实例
	 * 
	 * @param className
	 * @param argsClass
	 * @param args
	 * @return
	 */
	public static Object newInstance(String className, Class<?>[] argsClass, Object[] args) {

		if ((args == null) || (args.length <= 0)) {
			return newInstance(className);
		}

		Class<?> cls = null;
		try {
			cls = Class.forName(className);
		} catch (Exception e) {
			return null;
		}

		Constructor<?> cons = null;
		try {
			cons = cls.getDeclaredConstructor(argsClass);
			cons.setAccessible(true);
		} catch (Exception localException1) {
		}
		Object obj = null;
		try {
			obj = cons.newInstance(args);
		} catch (Exception localException2) {
		}
		return obj;
	}

	public static Object getInstance(String className) {
		return getInstance(className, "getInstance", null, null);
	}

	/**
	 * 调用getInstance 方法获取实例
	 * 
	 * @param className
	 * @param args
	 * @return
	 */
	public static Object getInstance(String className, Object[] args) {
		return getInstance(className, "getInstance", objectToClass(args), args);
	}

	public static Object getInstance(String className, Class<?>[] argsClass, Object[] args) {
		return getInstance(className, "getInstance", argsClass, args);
	}

	public static Object getInstance(String className, String methodName) {
		return getInstance(className, methodName, null, null);
	}

	public static Object getInstance(String className, String methodName, Object[] args) {
		return getInstance(className, methodName, objectToClass(args), args);
	}

	public static Object getInstance(String className, String methodName, Class<?>[] argsClass, Object[] args) {

		Object obj = null;
		try {
			Class<?> cls = Class.forName(className);
			obj = invoke(cls, methodName, argsClass, args);
		} catch (Exception cls) {
		}
		return obj;
	}

	public static Object invoke(Object obj, String methodName, Object[] args) {
		return invoke(obj, methodName, objectToClass(args), args);
	}

	public static Object invoke(Object obj, String methodName, Class<?>[] argsClass, Object[] args) {

		Object returnObj = null;
		if (obj instanceof Class) {
			returnObj = invoke((Class<?>) obj, obj, methodName, argsClass, args);
		} else {
			returnObj = invoke(obj.getClass(), obj, methodName, argsClass, args);
		}
		return returnObj;
	}

	public static Object invoke(Class<?> cls, Object obj, String methodName, Class<?>[] argsClass, Object[] args) {

		Object returnObj = null;
		Method method = null;
		try {
			method = cls.getDeclaredMethod(methodName, argsClass);
			method.setAccessible(true);
			returnObj = method.invoke(obj, args);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			if (!(cls.equals(Object.class))) {
				returnObj = invoke(cls.getSuperclass(), obj, methodName, argsClass, args);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
		return returnObj;
	}

	/**
	 * 判断是否存在某一属性
	 * 
	 * @param cl
	 * @param fieldName
	 * @return
	 */
	public static boolean isExistField(Class<?> cl, String fieldName) {

		while (!(Object.class.equals(cl))) {
			Field[] fields = cl.getDeclaredFields();
			for (int i = 0; i < fields.length; ++i)
				if (fieldName.equals(fields[i].getName())) {
					return true;
				}
			cl = cl.getSuperclass();
		}
		return false;
	}

	/**
	 * 判断是否存在某一方法
	 * 
	 * @param cl
	 * @param methodName
	 * @return
	 */
	public static boolean isExistMethod(Class<?> cl, String methodName) {
		while (!(Object.class.equals(cl))) {
			Method[] methods = cl.getDeclaredMethods();
			for (int i = 0; i < methods.length; ++i)
				if (methodName.equals(methods[i].getName())) {
					return true;
				}
			cl = cl.getSuperclass();
		}
		return false;
	}

	public static String getFieldValueString(Object obj) {

		Field[] fields = obj.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fields.length; ++i) {
			String fieldName = fields[i].getName();
			sb.append(fields[i].getName() + "=");
			try {
				sb.append(getter(obj, fieldName));
			} catch (Exception e) {
				sb.append("无法获得参数值");
			}
		}
		return sb.toString();
	}

	/**
	 * 设定 bean 属性值
	 * 
	 * @param obj
	 *            实体
	 * @param fieldName
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public static void setter(Object obj, String fieldName, Object value) {

		if ((obj == null) || (value == null))
			return;
		if (obj instanceof Class) {
			setter((Class<?>) obj, obj, fieldName, value, value.getClass());
		} else {
			setter(obj.getClass(), obj, fieldName, value, value.getClass());
		}
	}

	public static boolean judTypeClass(Class<?> value, Class<?> cls) {

		if (value.equals(cls)) {
			return true;
		}
		if (value.equals(Object.class)) {
			return false;
		}
		return judTypeClass(value.getSuperclass(), cls);
	}

	public static void setter(Object obj, String fieldName, Object args, Class<?> argsType) {
		setter(obj.getClass(), obj, fieldName, args, argsType);
	}

	/**
	 * 
	 * @param cls
	 *            bean 类型
	 * @param bean
	 *            实体
	 * @param fieldName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @param valueClass
	 *            属性类型
	 */
	public static void setter(Class<?> cls, Object bean, String fieldName, Object value, Class<?> valueClass) {

		Field field = null;
		try {
			field = cls.getDeclaredField(fieldName);
			if (!(judTypeClass(valueClass, field.getType()))) {
				value = convert2(value, field.getType());
			}
			field = cls.getDeclaredField(fieldName);
			String mName = field.getName();
			String firstChar = String.valueOf(mName.charAt(0));
			mName = "get" + mName.replaceFirst(firstChar, firstChar.toUpperCase());
			boolean isInv = false;
			try {
				Method method = cls.getMethod(mName, new Class[] { value.getClass() });
				method.invoke(bean, new Object[] { value });
				isInv = true;
			} catch (Exception method) {
			}
			if (isInv)
				return;
			field.setAccessible(true);
			field.set(bean, value);
		} catch (SecurityException e) {
			return;
		} catch (NoSuchFieldException e) {
			if (cls.equals(Object.class)) {
				return;
			}
			setter(cls.getSuperclass(), bean, fieldName, value, valueClass);
		} catch (Exception e) {
		}
	}

	/**
	 * 获取属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getter(Object obj, String fieldName) {

		if (obj == null)
			return null;
		if (obj instanceof Class) {
			return getter((Class<?>) obj, obj, fieldName);
		}
		return getter(obj.getClass(), obj, fieldName);
	}

	public static Object getter(Class<?> cls, Object obj, String fieldName) {

		if (fieldName.indexOf(".") != -1) {
			String fname1 = fieldName.substring(0, fieldName.indexOf("."));
			String fname2 = fieldName.substring(fieldName.indexOf(".") + 1);
			try {
				Field field = cls.getDeclaredField(fname1);
				return getter(field.getType(), getter(obj, field), fname2);
			} catch (Exception e) {
				return null;
			}

		}

		Object retObj = null;
		Field field = null;
		try {
			field = cls.getDeclaredField(fieldName);
			String mName = field.getName();
			mName = "get" + mName.replaceFirst(new StringBuilder(String.valueOf(mName.charAt(0))).toString(),
					new StringBuilder(String.valueOf(mName.charAt(0))).toString().toUpperCase());
			boolean isInv = false;
			try {
				Method method = cls.getMethod(mName);
				retObj = method.invoke(obj);
				isInv = true;
			} catch (Exception method) {
			}
			if (!isInv)
				return null;
			field.setAccessible(true);
			retObj = field.get(obj);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchFieldException e) {
			if (cls.equals(Object.class))
				return null;
			retObj = getter(cls.getSuperclass(), obj, fieldName);
		} catch (Exception e) {
		}
		return retObj;
	}

	public static Object getter(Object obj, Field field) {

		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception localException) {
		}
		return null;
	}

	public static Object convert(Object value, Class<?> toClass) {

		if (value == null)
			return null;
		if (toClass.isInstance(value))
			return value;
		if ((Date.class.equals(toClass)) && (value instanceof String)) {
			try {
				dateformat.parse((String) value);
			} catch (Exception e) {
				return null;
			}
		}
		Object obj = null;
		try {
			obj = convert2(value, toClass);
		} catch (Exception e) {
			return value;
		}
		return obj;
	}

	private static Object convert2(Object value, Class<?> toClass) {

		Converter converter = ConvertUtils.lookup(toClass);
		if (converter == null)
			converter = ConvertUtils.lookup(String.class);
		return converter.convert(toClass, value);
	}

	public static String toString(Object obj) {
		return toString(obj, "@");
	}

	public static String toString(Object obj, String prot) {

		if (obj == null) {
			return "null";
		}
		StringBuffer sb = new StringBuffer("");
		Class<?> cls = obj.getClass();
		do {
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; ++i) {
				Object vv = getter(obj, fields[i].getName());
				if ((vv != null) && (((vv.getClass().equals(Date.class))
						|| (vv.getClass().getSuperclass().equals(Date.class))))) {
					vv = dateformat.format((Date) vv);
				}
				sb.append(fields[i].getName() + "=" + vv);
				if (i != fields.length - 1) {
					sb.append(prot);
				}
			}
			cls = cls.getSuperclass();
		} while (!(cls.equals(Object.class)));
		return sb.toString();
	}

	private static Class<?>[] objectToClass(Object[] args) {

		if ((args == null) || (args.length <= 0)) {
			return null;
		}
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0; i < args.length; ++i)
			if (args[i] == null) {
				argsClass[i] = null;
			} else {
				argsClass[i] = args[i].getClass();
			}
		return argsClass;
	}

	public static String objectToString(Object obj) {

		if (obj == null) {
			return "";
		}
		String value = null;
		if (obj instanceof Date) {
			value = dateformat.format((Date) obj);
		} else {
			value = obj.toString();
		}
		return value;
	}

	public static Method getMethod(Class<?> cls, String methodName) {

		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName))
				return method;

		}
		return null;
	}

	/**
	 * 获取 object 属性
	 * 
	 * @param object
	 * @param isSuper
	 *            是否包含父类属性 true包含,false不包含 默认包含
	 * @return
	 */
	public static List<Field> getFields(Object object, boolean isSuper) {

		if (isSuper) {
			return getFields(object);
		}
		Class<?> clazz = getClass(object);
		List<Field> list = new ArrayList<Field>();
		getFields(clazz, list);
		return list;
	}

	private static void getFields(Class<?> clazz, List<Field> list) {

		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				if (field.isAnnotationPresent(Transient.class)) {
					continue;
				}
				if (Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				list.add(field);
			}
		}
	}

	/**
	 * 获取object 属性 不包含父类属性
	 * 
	 * @return
	 */
	public static List<Field> getFields(Object object) {

		Class<?> clazz = getClass(object);
		List<Field> list = new ArrayList<Field>();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			getFields(clazz, list);
		}
		return list;
	}

	/**
	 * 获取class
	 * 
	 * @param object
	 * @return
	 */
	private static Class<?> getClass(Object object) {

		Class<?> clazz = object.getClass();
		if (object instanceof Class<?>) {
			clazz = (Class<?>) object;
		}
		return clazz;
	}

	/**
	 * List Set 属性转换未实现 String与数字类型转换未实现
	 * 
	 * 实现简单属性的mapToBean
	 * 
	 * @param map
	 * @param object
	 */
	public static void mapToBean(Map<String, Object> map, Object object) {

		String property = null;
		List<Field> list = ReflectionUtils.getFields(object.getClass(), true);
		for (Field field : list) {
			if (field.getType() != List.class && field.getType() != Set.class) {
				property = field.getName();
				setter(object, property, map.get(property));
			}
		}
	}

	/**
	 * entity 转为 map
	 * 
	 * @param object
	 */
	public static Map<String, Object> beanToMap(Object object) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Field> list = ReflectionUtils.getFields(object.getClass(), true);
		for (Field field : list) {
			if (field.getType() != List.class && field.getType() != Set.class) {
				map.put(field.getName(), getter(object, field));
			}
		}
		return map;
	}

	/**
	 * bean 复制
	 * 
	 * @param object
	 *            源类
	 * @param target
	 *            目标类
	 */
	public static void copyBean(Object object, Object target) {

		if (object == null || target == null) {
			return;
		}
		List<Field> tFs = getFields(target);
		List<Field> oFs = getFields(object);
		for (Field of : oFs) {
			for (Field tf : tFs) {
				String ofSimpleName = of.getClass().getSimpleName();
				String tfSimpleName = tf.getClass().getSimpleName();
				if (of.getName().equals(tf.getName()) && ofSimpleName.equals(tfSimpleName)) {
					Object value = getter(object, of.getName());
					setter(target, tf.getName(), value);
				}
			}
		}
	}
}
