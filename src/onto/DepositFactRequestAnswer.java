package onto;

import java.util.List;

import jade.content.Predicate;

public class DepositFactRequestAnswer implements Predicate {
	public List<DepositFact> facts;

	public List<DepositFact> getFacts() {
		return facts;
	}

	public void setFacts(List<DepositFact> facts) {
		this.facts = facts;
	}
}
