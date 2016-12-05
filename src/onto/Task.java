package onto;

import jade.content.Concept;

public class Task implements Concept{
	public enum Type {SCAN, EXTRACT, TRANSPORT};
	public Type type;
}
