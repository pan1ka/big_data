package lab3.multimapreducer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
	/**
	 * maps input string according to the logic and sorts the list
	 * 
	 * @param inputString
	 * @return list of objects
	 */
	public List<Mpair<String, Integer>> mapper(String inputString) {
		String[] splited = inputString.split("[\\s+]|[-]|[.]"); // split by space, hyphen and dot
		List<String> splitList = Arrays.asList(splited);

		List<Mpair<String, Integer>> lastSplitList = splitList.stream().map(e -> Utils.convertToAlpha(e)) // filter ",.
				.filter(e -> {
					if (e.trim().length() == 0) { // delete empty strings
						return false;
					}

					if (!Utils.isAlpha(e)) { // delete abc123 like non-words
						return false;
					}

					return true;
				}).map(e -> (new Mpair<String, Integer>(e.toLowerCase(), 1))) // add new Pair element
				.sorted(new MpairComparator()).collect(Collectors.toList());

		return lastSplitList;
	}

	
}
