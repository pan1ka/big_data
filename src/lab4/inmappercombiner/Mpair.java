package lab4.inmappercombiner;


public class Mpair<K, V>{
	private K key;
	private V value;
	
	public Mpair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "< " + key + ", " + value + " >";
	}


}
