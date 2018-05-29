package lab1.mapper;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
 

public class Main {
/*
 * Mapper class, output key - value pairs from the file 
 * word count implementation for map reduce computation model
 * */
	public static void main(String[] args) {
		try {
			String inputString = Utils.readFile("input/testDataForW1D1.txt");
			
			String[] splited = inputString.split("[\\s+]|[-]"); // split by space and hyphen
			List<String> splitList = Arrays.asList(splited);
			
			List<Mpair<String, Integer>> lastSplitList  = splitList.stream()
					.map(e->Utils.convertToAlpha(e)) // filter ",.
					.filter(e->{
								if(e.trim().length()==0) { // delete empty strings
									return false;
								}
								
								if(!Utils.isAlpha(e)) { // delete abc123 like non-words
									return false;
								}
								
								return true;
							})
					.map(e->(new Mpair<String, Integer>(e.toLowerCase(), 1))) // add new Pair element
					.sorted(new MpairComparator())
					.collect(Collectors.toList());
			
			lastSplitList.forEach(System.out::println);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
