

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{	
		Eliminator e = new Eliminator();
		Propagator p = new Propagator();
		Distributor d = new Distributor();
		CNFmaker c = new CNFmaker();

		// test1
		String f = e.elimination("~((q=>r)=>((p=>q)=>(p=>r)))");
		System.out.println(f);
		f = p.propagation(f);
		System.out.println(f);
		f = d.distribution(f);
		System.out.println(f);
		System.out.println(c.getCNF("~((q=>r)=>((p=>q)=>(p=>r)))"));
		System.out.println(" ");
		
		// test2
		f = e.elimination("(((p&q)=>r)|((q=>p)&~q))&(~p=>(q=>r))");
		System.out.println(f);
		f = p.propagation(f);
		System.out.println(f);
		f = d.distribution(f);
		System.out.println(f);
		System.out.println(c.getCNF("(((p&q)=>r)|((q=>p)&~q))&(~p=>(q=>r))"));
		System.out.println(" ");
		
		// test2
		f = e.elimination("~(((p&q)=>(~q&r))=>(((q=>r)=>(p&r))=>((p&q)=>(p&r))))");
		System.out.println(f);
		f = p.propagation(f);
		System.out.println(f);
		f = d.distribution(f);
		System.out.println(c.getCNF("~(((p&q)=>(~q&r))=>(((q=>r)=>(p&r))=>((p&q)=>(p&r))))"));
		System.out.println(f);
	
	}
}
