package com.googlecode.objectify.util;

import java.util.List;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

/**
 * Simple wrapper/decorator for a Query.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class QueryWrapper<T> implements Query<T>
{
	/** */
	Query<T> base;
	
	/** */
	public QueryWrapper(Query<T> base) 
	{
		this.base = base;
	}
	
	@Override
	public Query<T> filter(String condition, Object value)
	{
		return this.base.filter(condition, value);
	}
	
	@Override
	public Query<T> order(String condition)
	{
		return this.base.order(condition);
	}
	
	@Override
	public Query<T> ancestor(Object keyOrEntity)
	{
		return this.base.ancestor(keyOrEntity);
	}
	
	@Override
	public Query<T> limit(int value)
	{
		return this.base.limit(value);
	}
	
	@Override
	public Query<T> offset(int value)
	{
		return this.base.offset(value);
	}

	@Override
	public Query<T> startAt(Cursor value)
	{
		return this.base.startAt(value);
	}

	@Override
	public Query<T> endAt(Cursor value)
	{
		return this.base.endAt(value);
	}

	@Override
	public String toString()
	{
		return this.base.toString();
	}

	@Override
	public Ref<T> first()
	{
		return this.base.first();
	}

	@Override
	public int count()
	{
		return this.base.count();
	}

	@Override
	public QueryResultIterable<T> entities()
	{
		return this.base.entities();
	}

	@Override
	public QueryResultIterable<Key<T>> keys()
	{
		return this.base.keys();
	}

	@Override
	public List<T> list()
	{
		return this.base.list();
	}

	@Override
	public List<Key<T>> listKeys()
	{
		return this.base.listKeys();
	}
	
	@Override
	public Query<T> chunkSize(int value)
	{
		return this.base.chunkSize(value);
	}

	@Override
	public Query<T> prefetchSize(int value)
	{
		return this.base.prefetchSize(value);
	}

	@Override
	public Query<T> keysOnly()
	{
		return this.base.keysOnly();
	}
}
