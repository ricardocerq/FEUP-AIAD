package mars;

import jade.core.AID;
import jade.domain.FIPAAgentManagement.*;
import repast.simphony.engine.environment.RunEnvironment;
import jade.domain.FIPAException;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.df;

public class SubDF extends df {
	private static final long serialVersionUID = 1L;

	public void setup() {

		// Input df name
		// int len = 0;
		// byte[] buffer = new byte[1024];

		try {
			AID parentName = getDefaultDF();

			super.setup();

			setDescriptionOfThisDF(getDescription());

			DFService.register(this, parentName, getDescription());
			addParent(parentName, getDescription());
			
			if(!RunEnvironment.getInstance().isBatch())
				System.out.println("Agent: " + getName() + " federated with default df.");
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	private DFAgentDescription getDescription() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getLocalName() + "-sub-df");
		sd.setType("fipa-df");
		sd.addProtocols(FIPANames.InteractionProtocol.FIPA_REQUEST);
		sd.addOntologies("fipa-agent-management");
		sd.setOwnership("JADE");
		dfd.addServices(sd);
		return dfd;
	}

}
