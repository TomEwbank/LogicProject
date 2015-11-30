

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eliminator {

	public String elimination(String formula) {

		formula = formula.replaceAll("<=>", "<>");
		formula = formula.replaceAll(" ", "");
		Pattern p = Pattern.compile("=>|<=|<>|<~>");
		Matcher m = p.matcher(formula);

		while (m.find()) {
			String[] parts;
			System.out.println(formula.substring(m.start(), m.end()));
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public LinkedList<Token> elimination(String formula) {
//		LogicTokenizer tokenizer = new LogicTokenizer(formula);
//		LinkedList<Token> tokens = tokenizer.getTokens();
//		
//		ListIterator<Token> it = tokens.listIterator(0);
//		ListIterator<Token> right_it;
//		ListIterator<Token> left_it;
//		while (it.hasNext()) {
//			Token t = it.next();
//			Token t_left;
//			System.out.println(t.token);
//			if (t.token == LogicElementID.IMPLIES_R) {
//				it.set(new Token(LogicElementID.OR, "|"));
//				left_it = tokens.listIterator(it.nextIndex()-2);
////				left_it.previous();
//				left_it.add(new Token(LogicElementID.R_PAREN, ")"));
//				left_it.add(new Token(LogicElementID.R_PAREN, "("));
//				System.out.println("coucou  "+it.next().sequence);
//				t_left = left_it.previous();
//				int level = 0;
//				while (level >= 0) {
//					if (!it.hasPrevious()) {
//						break;
//					}
//					else if (it.previous().token == LogicElementID.R_PAREN) {
//						++level;
//					}
//					else if (it.previous().token == LogicElementID.L_PAREN) {
//						--level;
//					}
//				}
//				it.add(new Token(LogicElementID.R_PAREN, ")"));
//				
//		
//			}
//			else if (t.token == LogicElementID.IMPLIES_L) {
//				if (it.next().token == LogicElementID.L_PAREN) {
//					it.previous();
//					it.add(new Token(LogicElementID.NOT, "~"));
//				} else {
//					it.previous();
//					it.add(new Token(LogicElementID.NOT, "~"));
//					it.add(new Token(LogicElementID.L_PAREN, "("));
//					// find the right place to insert ")"
//					int level = 0;
//					while (level >= 0) {
//						if (!it.hasNext()) {
//							break;
//						}
//						else if (it.next().token == LogicElementID.L_PAREN) {
//							++level;
//						}
//						else if (it.next().token == LogicElementID.R_PAREN) {
//							--level;
//						}
//					}
//					it.add(new Token(LogicElementID.R_PAREN, ")"));
//				}
//				
//			}
//			else if (t.token == LogicElementID.EQUIV) {
//				
//			}
//			else if (t.token == LogicElementID.XOR) {
//				
//			}
//			
//			
//		}
//		
//		return tokens;
//	}
	
}
