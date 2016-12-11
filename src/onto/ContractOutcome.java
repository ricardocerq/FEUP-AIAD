package onto;

import jade.content.Predicate;

public class ContractOutcome implements Predicate {
	private static final long serialVersionUID = 1L;
	
	private boolean success;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
