package onto;

import jade.content.Predicate;
import mars.Bot;
import mars.Producer;
import mars.Spotter;
import mars.Transporter;

public class DepositProposal implements Predicate {
	public Task.Type type;
	public Task.Type getType() {
		return type;
	}
	public void setType(Task.Type type) {
		this.type = type;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public DepositFact getFact() {
		return fact;
	}
	public void setFact(DepositFact fact) {
		this.fact = fact;
	}
	public double cost;
	public DepositFact fact;
	public DepositProposal(DepositFact m, Bot b, double cost){
		this.cost = cost;
		this.fact = m;
		if(b instanceof Spotter){
			type = Task.Type.SCAN;
		} else if(b instanceof Producer){
			type = Task.Type.EXTRACT;
		} else if(b instanceof Transporter){
			type = Task.Type.TRANSPORT;
		}
	}
	public DepositProposal(Task.Type type, double cost, DepositFact f){
		this.type = type;
		this.cost = cost;
		this.fact = f;
	}
	public DepositProposal(){
		
	}
}
