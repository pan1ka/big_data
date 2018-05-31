package lab4.inmappercombiner;

import java.util.Comparator;

public class MpairComparator implements Comparator<Mpair<String, Integer>> {

	@Override
	public int compare(Mpair<String, Integer> o1, Mpair<String, Integer> o2) {
		// TODO Auto-generated method stub
		 return ((String)o1.getKey()).compareTo((String)o2.getKey());
	}
	
}
