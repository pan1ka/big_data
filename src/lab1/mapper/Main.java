package lab1.mapper;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
 

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String inputString = Utils.readFile("input/testDataForW1D1.txt");
			//System.out.println(inputString);
			
			
			String[] splited = inputString.split("[\\s+]|[-]"); // split by space and hyphen
			List<String> splitList = Arrays.asList(splited);
			
			List<Mpair<String, Integer>> lastSplitList  = splitList.stream()
					.map(e->Utils.convertToAlpha(e)) // filter ",.
					.filter(e->{
								if(e.trim().length()==0) { // delete empty strings
									return false;
								}
								
								if(!Utils.isAlpha(e)) { // delete abc123 non-words
									return false;
								}
								
								return true;
							})
					.map(e->new Mpair(e.toLowerCase(), 1)) // add new Pair element
					.collect(Collectors.toList());
			
			lastSplitList.sort(new MpairComparator());
			
			lastSplitList.forEach(e->{
				System.out.println(e);
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
