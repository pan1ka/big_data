package lab3.multimapreducer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCount {
	int reducerCount = 4; //temporary
	int mapperCount = 3;
	/*
	 * Mapper class, output key - value pairs from the file word count
	 * implementation for map reduce computation model
	 */
	public static void main(String[] args) {
		HashMap<Integer, List<Mpair<String, Integer>>> mapperOutput = new HashMap<Integer, List<Mpair<String, Integer>>>();
		HashMap<Integer, List<GroupByPair<String, Integer>>> reducerOutput = new HashMap<Integer, List<GroupByPair<String, Integer>>>();
		HashMap<Integer, List<GroupByPair<String, Integer>>> shuffleSortOutput = new HashMap<Integer, List<GroupByPair<String, Integer>>>();
		
		//String inputString = Utils.readFile("input/testDataForW1D1.txt");
		String inputString = "\"cat bat\" mat-pat mum.edu sat.\r\n" + 
							 "fat 'rat eat cat' mum_cs mat\r\n"	+ 
							 "bat-hat mat pat \"oat \r\n" + 
							 "hat rat mum_cs eat oat-pat\r\n" +
							 "zat lat-cat pat jat.\r\n" + 
							 "hat rat. kat sat wat";
		
		WordCount m = new WordCount();
		//TODO specify number of reducers/mappers
		
		// Mapper
		for (int i = 1; i <= m.mapperCount; i++) {
			mapperOutput.put(i, (new Mapper()).mapper(m.splitby(inputString, i)));
		}
		
		System.out.println("\n******* Mapper output:");
		for (int key = 1; key <= m.mapperCount; key++) {
			System.out.println("\nMapper " + key + " output:");
			mapperOutput.get(key).forEach(System.out::println);
		}
		
		System.out.println("\n******* Pairs sorting:");
		for (int key = 1; key <= m.mapperCount; key++) {
			mapperOutput.get(key).forEach(e->{
				System.out.println(String.format("Pairs send from Mapper %d Reducer %d", key, m.getPartition(e.getKey())));
			});
			
			
		}
		
		
		// pseudo Shuffle & sort
		/*List<GroupByPair<String, Integer>> shuffledList = m0.shuffleSort(mappedList);
		System.out.println("\n After Shuffle & sort, Reducer input:");
		shuffledList.forEach(System.out::println);

		// Reducer
		List<GroupByPair<String, Integer>> reducedList = r0.reducer(shuffledList);
		System.out.println("\n Reducer output:");
		reducedList.forEach(e -> {
			System.out.println(String.format("< %s, %d >", e.getKey(), e.getValues().get(0)));
		});*/
	}

	/**
	 * Shuffles and sorts input for reducer
	 * 
	 * @param mappedList
	 * @return list of Grouped by key objects
	 */
	public List<GroupByPair<String, Integer>> shuffleSort(List<Mpair<String, Integer>> mappedList) {

		List<GroupByPair<String, Integer>> hm = new ArrayList<GroupByPair<String, Integer>>();

		mappedList.forEach(e -> {
			boolean containsKey = false;

			for (GroupByPair<String, Integer> groupByPair : hm) {
				if (groupByPair.getKey().equals(e.getKey())) {
					groupByPair.add(e.getValue());
					containsKey = true;
				}
			}

			if (!containsKey)
				hm.add(new GroupByPair<String, Integer>(e.getKey(), e.getValue()));
		});

		return hm;
	}
	
	
	/**
	 * splits input String for according to mapper numbers
	 * 
	 * @param inputString
	 * @param mapperCount
	 * @param mapperNumber
	 * @return
	 */
	private String splitby(String inputString, int mapperNumber) {

		String lines[] = inputString.split("\\r?\\n");
		int c = lines.length;
		int intForOneMapper = c/mapperCount; // assume that no errors will happen
		String outString = ""; 
		for (int j = (mapperNumber-1)*intForOneMapper; j < (mapperNumber-1)*intForOneMapper + intForOneMapper; j++) {
			outString += lines[j];
			System.out.println(j + " " + lines[j]);
		}
		
		return outString;
	}





	/**
	 * Calculates partition from the key, takes the first letter of the word
	 * 
	 * @param pair
	 * @return
	 */
	public int getPartition(String key) {
		return (int) key.hashCode()%reducerCount;
	}

}
