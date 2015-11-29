
import java.util.LinkedList;

public class Test
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {	
	  String formula = new String("p & ((~p => ~q) => p => q)");
	  Eliminator e = new Eliminator();
	  Propagator p = new Propagator();
	  
//	  System.out.println(e.elimination(formula));
//	  System.out.println(e.elimination("a => b"));
//	  System.out.println(e.elimination("a <= b"));
//	  System.out.println(e.elimination("a <=> b"));
//	  System.out.println(e.elimination("a <~> b"));
	  System.out.println(p.propagation("~~~(((a&d))|b)"));
	  
  }
}
