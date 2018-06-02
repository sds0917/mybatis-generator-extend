package org.mybatis.generator.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public abstract class ObjectUtils {

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Optional) {
			return !((Optional<?>) obj).isPresent();
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		}
		return false;
	}

	public static String toLowerFristChar(String string) {
		char[] arr = string.toCharArray();
		arr[0] += 32;
		return String.valueOf(arr);
	}

	public static String toUpperFristChar(String string) {
		char[] arr = string.toCharArray();
		arr[0] -= 32;
		return String.valueOf(arr);
	}

}