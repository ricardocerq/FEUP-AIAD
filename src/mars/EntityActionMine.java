package mars;

import onto.Task;
import onto.Task.Type;

public class EntityActionMine implements EntityAction{
	
	Task.Type type;
	Mineral min; 
	int amount;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	public EntityActionMine(Type type, Mineral min, int amount) {
		super();
		this.type = type;
		this.min = min;
		this.amount = amount;
	}
}
