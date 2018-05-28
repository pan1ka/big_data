package lab1.mapper;

import java.util.Comparator;

public class Mpair<K, V>{
	K key;
	V value;
	
	public Mpair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "(" + key + ", " + value + ")";
	}

	/*
	@Override
	public int compareTo(Object o) {
		return ((String)key).compareTo((String)o);
	}*/

}
