package onto;

import java.util.List;

import jade.content.Predicate;

public class DepositFactRequestAnswer implements Predicate {
	private static final long serialVersionUID = 1L;
	
	public List<DepositFact> facts;

	public List<DepositFact> getFacts() {
		return facts;
	}

	public void setFacts(List<DepositFact> facts) {
		this.facts = facts;
	}
}
