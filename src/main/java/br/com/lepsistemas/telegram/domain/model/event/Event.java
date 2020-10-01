package br.com.lepsistemas.telegram.domain.model.event;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class Event<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T data;
	
	protected Event() {}
	
	public Event(T data) {
		this.data = data;
	}
	
	public abstract String key();
	
	public T data() {
		return this.data;
	}

}
