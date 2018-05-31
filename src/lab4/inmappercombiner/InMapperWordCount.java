package lab4.inmappercombiner;

import java.util.ArrayList;

public class InMapperWordCount {
	int reducerCount = 4; // temporary
	int mapperCount = 3;

	public static void main(String[] args) {
		InMapperWordCount m = new InMapperWordCount();
		String inputString = Utils.readFile("input/lab3test.txt");
		// TODO specify number of reducers/mappers
		
		System.out.println(String.format("Number of Input-Splits: %d", m.mapperCount));
		System.out.println(String.format("Number of Reducers: %d", m.reducerCount));
		
		@SuppressWarnings("unchecked")
		ArrayList<Mpair<String, Integer>>[] mappers = new ArrayList[m.mapperCount];
		@SuppressWarnings("unchecked")
		ArrayList<GroupByPair<String, Integer>>[] reducers = new ArrayList[m.reducerCount];
				
		// Mappers initialization
		for (int i = 0; i < m.mapperCount; i++) {
			mappers[i] = new ArrayList<Mpair<String, Integer>>();
		}
		
		// Reducers initialization
		for (int i = 0; i < m.reducerCount; i++) {
			reducers[i] = new ArrayList<GroupByPair<String, Integer>>();
		}
		
		// Mapper feeding
		for (int i = 0; i < m.mapperCount; i++) {
			System.out.println(String.format("\nMapper %d input:", i));
			ArrayList<Mpair<String, Integer>> tempMapper = (ArrayList<Mpair<String, Integer>>) (new Mapper()).mapper(m.splitby(inputString, i));
			mappers[i].addAll(tempMapper);
		}
		
		// Print mapper output
		for (int i = 0; i < m.mapperCount; i++) {
			System.out.println(String.format("\nMapper %d output:", i));
			mappers[i].stream().forEach(System.out::println);
			
		}

		// Shuffle and sort
		m.shuffleSortAdvanced(mappers, reducers);
		
		// Print output after shuffle
		int r = 0;
		for (ArrayList<GroupByPair<String, Integer>> reducer : reducers) {
			System.out.println(String.format("\nReducer %d input:", r++));
			reducer.stream().forEach(System.out::println);
		}
		
		r = 0;
		for (ArrayList<GroupByPair<String, Integer>> reducer : reducers) {
			System.out.println(String.format("\nReducer %d output:", r++));
			(new Reducer()).reducer(reducer).forEach(e -> { // here we taking 0 index because we already summed up counts.
				System.out.println(String.format("< %s, %d >", e.getKey(), e.getValues().get(0))); }); 
		} 
		 
	}

	public void shuffleSortAdvanced(ArrayList<Mpair<String, Integer>>[] mappers, ArrayList<GroupByPair<String, Integer>>[] tred) {
		@SuppressWarnings("unchecked")
		ArrayList<Mpair<String, Integer>>[] reducerLog = new ArrayList[tred.length]; // to show results in proper way
		for (int i = 0; i < mappers.length; i++) {
			for (int q = 0; q < reducerLog.length; q++) {
				reducerLog[q] = new ArrayList<Mpair<String, Integer>>(); // clean log array
			}
			
			for (Mpair<String, Integer> pair : mappers[i]) {
				int reducerIndex = this.getPartition(pair.getKey());
				reducerLog[reducerIndex].add(pair); // for console logs
			
				boolean containsKey = false;
				
				// converting from Pair to GroupedPair
				for (GroupByPair<String, Integer> groupByPair : tred[reducerIndex]) {
					if (groupByPair.getKey().equals(pair.getKey())) {
						groupByPair.add(pair.getValue());
						containsKey = true;
					}
				}
				
				if (!containsKey)
					tred[reducerIndex].add(new GroupByPair<String, Integer>(pair.getKey(), pair.getValue()));
			}
			
			// output logger 
			for (int q = 0; q < reducerLog.length; q++) {
				System.out.println(String.format("\nPairs send from Mapper  %d to Reducer %d: ", i, q));
				reducerLog[q].stream().forEach(System.out::println);
			}
		}
		
		
		 
		for (ArrayList<GroupByPair<String, Integer>> reducer : tred) {
			reducer.sort(new GpairComparator());
		}
		 
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
		int intForOneMapper = c / mapperCount; // assume that no errors will happen
		String outString = "";
		for (int j = mapperNumber * intForOneMapper; j < mapperNumber * intForOneMapper + intForOneMapper; j++) {
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
		return (int) key.hashCode() % reducerCount;
	}

}
