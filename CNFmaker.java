
public class CNFmaker {
	public String getCNF(String formula) {
		Eliminator e = new Eliminator();
		Propagator p = new Propagator();
		Distributor d = new Distributor();
		return d.distribution(p.propagation(e.elimination(formula)));
	}

}
