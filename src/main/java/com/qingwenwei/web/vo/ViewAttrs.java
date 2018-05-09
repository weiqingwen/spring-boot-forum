package com.qingwenwei.web.vo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ViewAttrs implements Map<String, Object> {

	private final Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public int size() {
		return this.attributes.size();
	}

	@Override
	public boolean isEmpty() {
		return this.attributes.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.attributes.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.attributes.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return this.attributes.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return this.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return this.attributes.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		this.attributes.putAll(m);
	}

	@Override
	public void clear() {
		this.attributes.clear();
	}

	@Override
	public Set<String> keySet() {
		return this.attributes.keySet();
	}

	@Override
	public Collection<Object> values() {
		return this.attributes.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return this.entrySet();
	}

}
