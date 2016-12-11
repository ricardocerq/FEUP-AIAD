package onto;

import jade.content.Predicate;

public class DepositProposalResponse implements Predicate {
	private static final long serialVersionUID = 1L;
	
	public Task.Type type;

	public Task.Type getType() {
		return type;
	}

	public void setType(Task.Type type) {
		this.type = type;
	}

	public DepositFact getFact() {
		return fact;
	}

	public void setFact(DepositFact fact) {
		this.fact = fact;
	}

	public DepositFact fact;

	public DepositProposalResponse(DepositProposal proposal) {
		this.type = proposal.type;
		this.fact = proposal.fact;
	}

	public DepositProposalResponse() {

	}
}
