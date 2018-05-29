package lab2.mapreducer;

import java.util.ArrayList;
import java.util.List;

public class GroupByPair<K, V> {
	private K key;
	private List<V> values;

	public K getKey() {
		return key;
	}

	public GroupByPair(K key, V value) {
		super();
		values = new ArrayList<V>();
		this.key = key;
		add(value);
	}

	@Override
	public String toString() {
		return "< " + key + ", " + values + " >";
	}

	public void add(V value) {
		this.values.add(value);
	}

	public List<V> getValues() {
		return values;
	}

}
