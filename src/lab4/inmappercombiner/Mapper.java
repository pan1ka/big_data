package lab4.inmappercombiner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
	HashMap<String, Integer> hm;
	
	public void initialize() {
		hm = new HashMap<String, Integer>();
	}
	/**
	 * maps input string according to the logic and sorts the list, combines to hashmap
	 * 
	 * @param inputString
	 * @return list of objects
	 */
	public List<Mpair<String, Integer>> mapper(String inputString) {
		initialize(); // Initialize
		
		String[] splited = inputString.split("[\\s+]|[-]|[.]|[']"); // split by space, hyphen and dot
		List<String> splitList =  Arrays.asList(splited);

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
		
		//assume this is combiner
		for (Mpair<String, Integer> mpair : lastSplitList) {
			if(hm.containsKey(mpair.getKey())) {
				hm.put(mpair.getKey(), hm.get(mpair.getKey()).intValue() + mpair.getValue());
			}
			else{
				hm.put(mpair.getKey(), mpair.getValue());
			}
		}
		
		return  close();
	}
	
	public List<Mpair<String, Integer>> close() {
		return this.hm.entrySet().stream().map(e->{return new Mpair<String, Integer>(e.getKey(), e.getValue());}).sorted(new MpairComparator()).collect(Collectors.toList());
	}

	
}
