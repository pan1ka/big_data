package lab2.mapreducer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	/*
	 * Mapper class, output key - value pairs from the file word count
	 * implementation for map reduce computation model
	 */
	public static void main(String[] args) {

		String inputString = Utils.readFile("input/testDataForW1D1.txt");
		Main m = new Main();

		// Mapper
		List<Mpair<String, Integer>> mappedList = m.mapper(inputString);
		System.out.println("\nMapper output:");
		mappedList.forEach(System.out::println);
		
		// pseudo Shuffle & sort
		List<GroupByPair<String, Integer>> shuffledList = m.shuffleSort(mappedList);
		System.out.println("\n After Shuffle & sort, Reducer input:");
		shuffledList.forEach(System.out::println);
		
		// Reducer
		List<GroupByPair<String, Integer>> reducedList = m.reducer(shuffledList);
		System.out.println("\n Reducer output:");
		reducedList.forEach(e->{System.out.println(String.format("< %s, %d >", e.getKey(), e.getValues().get(0)));});
	}

	private List<GroupByPair<String, Integer>> reducer(List<GroupByPair<String, Integer>> shuffledList) {
		
		List<GroupByPair<String, Integer>> reducedList =  new ArrayList<GroupByPair<String, Integer>>();
		
		shuffledList.stream().forEach(e->{
			reducedList.add(new GroupByPair<String, Integer>(e.getKey(), e.getValues().stream().reduce(0,(x,y)->(x+y))) );
		});
		
		return reducedList;
	}

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

	public List<GroupByPair<String, Integer>> shuffleSort(List<Mpair<String, Integer>> mappedList) {
		
		// hashMap here is just for optimization
		//HashMap<String, GroupByPair<String, Integer>> hm = new HashMap<>();

		List<GroupByPair<String, Integer>> hm = new ArrayList<GroupByPair<String, Integer>>();
		
		mappedList.forEach(e -> {
			boolean containsKey = false;
			
			for (GroupByPair<String, Integer> groupByPair : hm) {
				if(groupByPair.getKey().equals(e.getKey())){
					groupByPair.add(e.getValue());
					containsKey = true;
				}
			}
			
			if(!containsKey)
				hm.add(new GroupByPair<String, Integer>(e.getKey(), e.getValue()));
		});

		return hm;
	}
	

	}
