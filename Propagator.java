import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Propagator {
	
	// When taking a valid formula as argument, only containing the OR, AND and NOT operators,
	// Return the equivalent formula where all the negations has been propagated, and the double negations removed
	public String propagation(String formula) {
		formula = formula.replaceAll(" ", "");
		Pattern p = Pattern.compile("~\\(");
		Matcher m = p.matcher(formula);

		while (m.find()) {
			String[] parts = formula.split("~\\(",2);
			String begining = parts[0];
			
			// find the members in the scope of the NOT
			LinkedList<Integer> membersStarts = new LinkedList<Integer>();
			LinkedList<Integer> membersEnds = new LinkedList<Integer>(); 
			int level = 0;
			membersStarts.add(m.end());
			int scopeEnd = formula.length(); 
			for (int i = m.end(); i < formula.length(); i++) {
				if (formula.charAt(i) == '(') {
					++level;
				}
				else if (formula.charAt(i) == ')') {
					--level;
				}
				if (level == 0 && formula.charAt(i) == '|') {
					membersEnds.add(i);
					membersStarts.add(i+1);
				}
				
				if (level < 0) {
					membersEnds.add(i);
					scopeEnd = i;
					break;
				}
			}
			
			String newFormula = begining + "(";
			if (membersStarts.size() > 1) {
				// Case where we propagate NOT in a OR
				ListIterator<Integer> startsIterator = membersStarts.listIterator(0);
				ListIterator<Integer> endsIterator = membersEnds.listIterator(0);
				newFormula += "~(" + formula.substring(startsIterator.next(), endsIterator.next()) + ")";
				while(startsIterator.hasNext()) {
					newFormula += "&~(" + formula.substring(startsIterator.next(), endsIterator.next()) + ")";
				}
				newFormula += ")" + formula.substring(scopeEnd+1);
				
			} else {
				// Case where we propagate NOT in a AND or in useless parenthesis
				membersStarts = new LinkedList<Integer>();
				membersEnds = new LinkedList<Integer>();
				membersStarts.add(m.end());
				level = 0;
				for (int i = m.end(); i < formula.length(); i++) {
					if (formula.charAt(i) == '(') {
						++level;
					}
					else if (formula.charAt(i) == ')') {
						--level;
					}
					if (level == 0 && formula.charAt(i) == '&') {
						membersEnds.add(i);
						membersStarts.add(i+1);
					}
					
					if (level < 0) {
						membersEnds.add(i);
						scopeEnd = i;
						break;
					}
				}
				
				if (membersStarts.size() > 1) {
					// Case where we propagate NOT in a AND
					ListIterator<Integer> startsIterator = membersStarts.listIterator(0);
					ListIterator<Integer> endsIterator = membersEnds.listIterator(0);
					newFormula += "~(" + formula.substring(startsIterator.next(), endsIterator.next()) + ")";
					while(startsIterator.hasNext()) {
						newFormula += "|~(" + formula.substring(startsIterator.next(), endsIterator.next()) + ")";
					}
					newFormula += ")" + formula.substring(scopeEnd+1);
				} else {
					// Case of useless parenthesis
					newFormula = begining + "~" + formula.substring(membersStarts.listIterator(0).next(), membersEnds.listIterator(0).next()) + formula.substring(scopeEnd+1);
				}	
			}
			formula = newFormula;
			m = p.matcher(formula);
		}
		return (formula.replaceAll("~~", ""));
	}
}
