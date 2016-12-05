package onto;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;

public class MarsOntology extends BeanOntology {
	
	public static final String ONTOLOGY_NAME = "mars";
	
	private static Ontology theInstance = new MarsOntology();
	
	public static Ontology getInstance() {
		return theInstance;
	}
	
	// Private constructor
	private MarsOntology() {
		super(ONTOLOGY_NAME);
		
		try {
			// add all Concept, Predicate and AgentAction
			add(ContractOutcome.class);
			add(DepositFact.class);
			add(DepositFactRequestAnswer.class);
			add(DepositProposal.class);
			add(DepositProposalRequest.class);
			add(DepositProposalResponse.class);
			add(MultiDepositFactRequest.class);
			add(SingleDepositFactRequest.class);
			add(Task.class);
			
		} catch(BeanOntologyException boe) {
			boe.printStackTrace();
		}
	}
	
}