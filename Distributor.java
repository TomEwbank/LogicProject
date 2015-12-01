import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Distributor {
	public String distribution(String formula) {
		formula = formula.replaceAll(" ", "");
		String prevFormula = new String();
		while (!formula.equals(prevFormula)) {
			prevFormula = formula;
			// find an OR that can be distributed
			for (int i=0; i<formula.length(); i++) {
				if (formula.charAt(i) == '|') {
					
					//select left member(s)
					int leftStart = 0;
					int leftEnd = i;
					int level = 0;
					LinkedList<Integer> leftSubMembersStarts = new LinkedList<Integer>();
					LinkedList<Integer> leftSubMembersEnds = new LinkedList<Integer>();
					leftSubMembersEnds.add(leftEnd);  
					for (int j = i-1; j >= 0; j--) {
						if (formula.charAt(j) == ')') {
							++level;
						}
						else if (formula.charAt(j) == '(') {
							--level;
						}
						if (level == 0 && formula.charAt(j) == '&') {
							leftSubMembersStarts.add(j+1);
							leftSubMembersEnds.add(j);
						}
						if (level < 0 || (level == 0 && formula.charAt(j) == '|')) {
							leftStart = j+1;
							leftSubMembersStarts.add(j+1);
							break;
						}
					}
					if (leftSubMembersEnds.size() != leftSubMembersStarts.size()) {
						leftSubMembersStarts.add(leftStart);
					}
					
					//select right member(s)
					int rightStart = i+1;
					int rightEnd = formula.length();
					LinkedList<Integer> rightSubMembersStarts = new LinkedList<Integer>();
					LinkedList<Integer> rightSubMembersEnds = new LinkedList<Integer>();
					rightSubMembersStarts.add(rightStart);
					level = 0;
					for (int j = i+1; j < formula.length(); j++) {
						if (formula.charAt(j) == '(') {
							++level;
						}
						if (formula.charAt(j) == ')') {
							--level;
						}
						if (level == 0 && formula.charAt(j) == '&') {
							rightSubMembersEnds.add(j);
							rightSubMembersStarts.add(j+1);
						}
						if (level < 0 || (level == 0 && formula.charAt(j) == '|')) {
							rightEnd = j;
							rightSubMembersEnds.add(j);
							break;
						}
					}
					if (rightSubMembersEnds.size() != rightSubMembersStarts.size()) {
						rightSubMembersEnds.add(rightEnd);
					}
					
					// Check for useless parenthesis and remove them
					String leftFormula = formula.substring(0, leftEnd);
					String rightFormula = formula.substring(rightStart);
					boolean uselessParenthesis = false;
					if (leftSubMembersStarts.size() == 1 && formula.charAt(leftStart) == '(') {
						leftFormula = formula.substring(0, leftStart) + formula.substring(leftStart+1, leftEnd-1);
						uselessParenthesis = true;
					}
					if (rightSubMembersEnds.size() == 1 && formula.charAt(rightStart) == '(') {
						rightFormula = formula.substring(rightStart+1, rightEnd-1); 
						if (rightEnd != formula.length()) {
							rightFormula += formula.substring(rightEnd);
						}
						uselessParenthesis = true;
					}
					if (uselessParenthesis) {
						// Remove useless parenthesis from formula and restart the search for OR's
						formula = leftFormula + "|" + rightFormula;
						
						break;
					}
					
					String newFormula;
					if (leftSubMembersStarts.size() > 1) {
						ListIterator<Integer> startsIterator = leftSubMembersStarts.listIterator(0);
						ListIterator<Integer> endsIterator = leftSubMembersEnds.listIterator(0);
						newFormula = "(" 
									+ formula.substring(startsIterator.next(), endsIterator.next())
									+ "|" 
									+ formula.substring(rightStart, rightEnd)
									+ ")";
						while(startsIterator.hasNext()) {
							newFormula += 	"&(" 
											+ formula.substring(startsIterator.next(), endsIterator.next()) 
											+ "|" 
											+ formula.substring(rightStart, rightEnd) 
											+ ")";
						}
						formula = 	formula.substring(0, leftStart) 
									+ newFormula
									+ formula.substring(rightEnd);
						break;
					}
					else if (rightSubMembersEnds.size() > 1) {
						ListIterator<Integer> startsIterator = rightSubMembersStarts.listIterator(0);
						ListIterator<Integer> endsIterator = rightSubMembersEnds.listIterator(0);
						newFormula = "(" 
									+ formula.substring(startsIterator.next(), endsIterator.next())
									+ "|" 
									+ formula.substring(leftStart, leftEnd)
									+ ")";
						while(startsIterator.hasNext()) {
							newFormula += 	"&(" 
											+ formula.substring(startsIterator.next(), endsIterator.next()) 
											+ "|" 
											+ formula.substring(leftStart, leftEnd) 
											+ ")";
						}
						formula = 	formula.substring(0, leftStart) 
									+ newFormula
									+ formula.substring(rightEnd);
						break;
					}
					// If the execution reach this point, that means the OR can't be distributed, 
					// so we continue the iteration to go to the next one. 
				}
				
			}
			
		}
			
		return cleanParenthesis(formula);
	}

	private String cleanParenthesis(String formula) {
		String prevFormula = new String();
		while (!formula.equals(prevFormula)) {
			prevFormula = formula;
			//formula = formula.replaceAll("\\)\\)&\\(\\(", ")&(");
			formula = formula.replaceAll("\\)\\)", ")");
			formula = formula.replaceAll("\\(\\(", "(");
		}
		
		Pattern p = Pattern.compile("&\\([a-zA-Z0-9_~]+&");
		Matcher m = p.matcher(formula);

		while (m.find()) {
			formula = 	  formula.substring(0, m.start()+1)
						+ formula.substring(m.start()+2);
			m = p.matcher(formula);
		}
		
		p = Pattern.compile("&[a-zA-Z0-9_~]\\)+&");
		m = p.matcher(formula);
		
		while (m.find()) {
			formula = 	  formula.substring(0, m.end()-2)
						+ formula.substring(m.end()-1);
			m = p.matcher(formula);
		}
		
		p = Pattern.compile(">\\([a-zA-Z0-9_~\\$]+&");
		m = p.matcher(">"+formula);
		if (m.find()) {
			formula = formula.substring(1);
		}
		
		p = Pattern.compile("&[a-zA-Z0-9_~\\$]+\\)<");
		m = p.matcher(formula+"<");
		if (m.find()) {
			formula = formula.substring(0,formula.length()-1);
		}
		
		return formula;
	}

}
