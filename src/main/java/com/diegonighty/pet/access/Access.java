package com.diegonighty.pet.access;

import java.lang.reflect.Field;

public final class Access {

	private Access() {}

	public static class FieldReflect<T> {

		private final Field field;

		public FieldReflect(final Field field) {
			this.field = field;
		}

		@SuppressWarnings("unchecked")
		public T get(final Object object) {
			try {
				return (T) field.get(object);
			} catch (final IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}

		public void set(final Object object, final T value) {
			try {
				field.set(object, value);
			} catch (final IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}

	}

	public static <T> FieldReflect<T> findFieldByType(final Class<?> clazz, final Class<? super T> type) {
		Field value = null;
		for (final var field : clazz.getDeclaredFields()) {
			if (field.getType() == type) {
				value = field;
				field.setAccessible(true);
			}
		}
		if (value == null) {
			throw new IllegalStateException("Field with type " + type + " not found in " + clazz);
		}
		return new FieldReflect<>(value);
	}

}