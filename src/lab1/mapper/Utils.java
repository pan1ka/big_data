package lab1.mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public static String readFile(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			return everything;

		} finally {
			br.close();
		}

	}
	
	public static String convertToAlpha(String name) {
	    char[] chars = name.toCharArray();
	    String returnString = "";
	    for (char c : chars) {
	        if((Character.OTHER_PUNCTUATION!=c)&&('.'!=c)&&('"'!=c)&&(','!=c)) {
	        	returnString = returnString.concat(Character.toString(c));
	        }
	    }

	    return returnString;
	}
}
