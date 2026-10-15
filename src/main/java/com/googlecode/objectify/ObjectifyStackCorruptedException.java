package com.googlecode.objectify;

/**
 * Thrown when an Objectify instance is closed but is not on the current thread's stack. Extends
 * {@link IllegalStateException} so existing handlers of that type still catch it.
 */
public class ObjectifyStackCorruptedException extends IllegalStateException
{
	private static final long serialVersionUID = 1L;

	public ObjectifyStackCorruptedException(final String message) {
		super(message);
	}
}
