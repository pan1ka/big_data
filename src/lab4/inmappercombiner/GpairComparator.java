package lab4.inmappercombiner;

import java.util.Comparator;

public class GpairComparator implements Comparator<GroupByPair<String, Integer>> {

	@Override
	public int compare(GroupByPair<String, Integer> o1, GroupByPair<String, Integer> o2) {
		// TODO Auto-generated method stub
		 return ((String)o1.getKey()).compareTo((String)o2.getKey());
	}
	
}
