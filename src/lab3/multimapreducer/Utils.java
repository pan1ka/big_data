package lab3.multimapreducer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public static String readFile(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
		
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			
			String everything = sb.toString();
			br.close();
			return everything;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String convertToAlpha(String name) {
	    char[] chars = name.toCharArray();
	    String returnString = "";
	    for (char c : chars) {
	        if((Character.OTHER_PUNCTUATION!=c)&&('"'!=c)&&(','!=c)) {
	        	returnString = returnString.concat(Character.toString(c));
	        }
	    }

	    return returnString;
	}
}
