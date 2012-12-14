package com.readytalk.crdt;

import javax.annotation.concurrent.NotThreadSafe;

import org.codehaus.jackson.annotate.JsonValue;

@NotThreadSafe
public interface CRDT<S, R extends CRDT<S, R>> {
	R merge(R other);
	
	S value();
	
	@JsonValue
	byte [] payload();
}
