package lab3.multimapreducer;

import java.util.ArrayList;
import java.util.List;

public class Reducer {
	/**
	 * Finds the sum of each key used in a input
	 * 
	 * @param shuffledList
	 * @return
	 */
	public List<GroupByPair<String, Integer>> reducer(List<GroupByPair<String, Integer>> shuffledList) {

		List<GroupByPair<String, Integer>> reducedList = new ArrayList<GroupByPair<String, Integer>>();

		shuffledList.stream().forEach(e -> {
			reducedList.add(
					new GroupByPair<String, Integer>(e.getKey(), e.getValues().stream().reduce(0, (x, y) -> (x + y))));
		});

		return reducedList;
	}
}
