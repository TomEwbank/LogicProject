

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eliminator {
	
	// Take a valid formula as argument,
	// Return the equivalent formula where all the other operators than OR, AND and NOT has been eliminated
	public String elimination(String formula) {

		formula = formula.replaceAll("<=>", "<>"); // to prevent the regex pattern of matching <= or => in case of <=>
		formula = formula.replaceAll(" ", "");
		Pattern p = Pattern.compile("=>|<=|<>|<~>");
		Matcher m = p.matcher(formula);

		while (m.find()) {
			String[] parts;
			if (formula.substring(m.start(), m.end()).equals("=>")) {
				parts = getMembers(formula, "=>");
				formula = parts[0] + "~(" + parts[1] + ")|(" + parts[2] + ")" + parts[3];
			} else if (formula.substring(m.start(), m.end()).equals("<=")) {
				parts = getMembers(formula, "<=");
				formula = parts[0] + "(" + parts[1] + ")|~(" + parts[2] + ")" + parts[3];
			} else if (formula.substring(m.start(), m.end()).equals("<>")) {
				parts = getMembers(formula, "<>");
				formula = parts[0] + "(" + parts[1] + ")" + parts[3];
			} else {
				parts = getMembers(formula, "<~>");
				formula = parts[0] + "((" + parts[1] + ")|(" + parts[2] +"))&~((" + parts[1] + ")&(" + parts[2] + "))" + parts[3];
			}
			m = p.matcher(formula);
		}
		return formula;
	}
	
	// Takes a formula and a particular connector as argument (=>|<=|<>|<~>),
	// Returns an array of size 4, 
	// where the two middle elements are the left and right members of the first specified connector encountered,
	// and the first and last elements are respectively the beginning and the ending of the formula, surrounding the left and right members 
	private String[] getMembers(String formula, String connector) {
		String[] formulaParts = formula.split(connector, 2);
		  if (formulaParts.length == 1) {
			  return null;
		  }
		  
		  int level = 0;
		  String left = formulaParts[0];
		  int leftLength = left.length();
		  int leftStart = 0;
		  // find left member starting point
		  for (int i = 1; i <= leftLength; i++) {
				
				if (left.charAt(leftLength-i) == ')') {
					++level;
				}
				else if (left.charAt(leftLength-i) == '(') {
					--level;
				}
				
				if (level < 0) {
					leftStart = leftLength-i+1;
					break;
				}
			}
		  
		  level = 0;
		  String right = formulaParts[1];
		  int rightLength = right.length();
		  int rightEnd = rightLength-1;
		  // find right member ending point
		  for (int i = 0; i < rightLength; i++) {
				
				if (right.charAt(i) == '(') {
					++level;
				}
				else if (right.charAt(i) == ')') {
					--level;
				}
				
				if (level < 0) {
					rightEnd = i-1;
					break;
				}
			}
		  
		  String[] parts = new String[4];
		  if (leftStart == 0) {
			  parts[0] = "";
			  parts[1] = left;
		  }
		  else {
			  parts[0] = left.substring(0, leftStart);
			  parts[1] = left.substring(leftStart);
		  }
		  if (rightEnd == rightLength-1) {
			  parts[2] = right;
			  parts[3] = "";
		  }
		  else {
			  parts[2] = right.substring(0, rightEnd+1);
			  parts[3] = right.substring(rightEnd+1);
		  }
		return parts;	  
	}
}
