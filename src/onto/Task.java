package onto;

import jade.content.Concept;

public class Task implements Concept {
	private static final long serialVersionUID = 1L;

	public enum Type {
		SCAN, EXTRACT, TRANSPORT
	};

	public Type type;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
