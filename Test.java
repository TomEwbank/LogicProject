
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
	  Distributor d = new Distributor();
	  
//	  System.out.println(e.elimination(formula));
//	  System.out.println(e.elimination("a => b"));
//	  System.out.println(e.elimination("a <= b"));
//	  System.out.println(e.elimination("a <=> b"));
//	  System.out.println(e.elimination("a <~> b"));
//	  String f = e.elimination("(q=>r)=>((p=>q)=>(p=>r))");
//	  System.out.println(f);
//	  f = p.propagation(f);
//	  System.out.println(f);
//	  f = d.distribution(f);
//	  System.out.println(f);
	  System.out.println(d.distribution("a|(e&q)"));
	  System.out.println(d.isCNF("a|e&q"));
//	  String s ="ab";
//	  s = s.substring(0, 0);
	  
  }
}
