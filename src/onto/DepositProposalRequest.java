package onto;

import jade.content.Predicate;
import mars.Mineral;

public class DepositProposalRequest implements Predicate {
	public DepositFact fact;
	public DepositFact getFact() {
		return fact;
	}
	public void setFact(DepositFact fact) {
		this.fact = fact;
	}
	public DepositProposalRequest(Mineral m, int t){
		this.fact = new DepositFact(m, t);
	}
	public DepositProposalRequest(DepositFact f){
		this.fact = f;
	}
	public DepositProposalRequest(){
	}
	
}
