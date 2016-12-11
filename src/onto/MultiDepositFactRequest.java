package onto;

public class MultiDepositFactRequest extends DepositFactRequest {
	private static final long serialVersionUID = 1L;
	
	public int maximumNumber;

	public int getMaximumNumber() {
		return maximumNumber;
	}

	public void setMaximumNumber(int maximumNumber) {
		this.maximumNumber = maximumNumber;
	}
}
