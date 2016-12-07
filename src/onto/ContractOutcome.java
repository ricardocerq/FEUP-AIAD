package onto;

import jade.content.Predicate;

public class ContractOutcome implements Predicate{
	private boolean success;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
