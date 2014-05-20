package auction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Bidder extends Agent {

	private int maximalBudget;
	private int inc = 10;

	private AID[] auctioners;
	private AID auctioner;

	// Put agent initializations here
	protected void setup() {
		// Printout a welcome message
		System.out.println("Hello! Bidder-agent "+getAID().getName()+" is ready.");

		// Get the title of the book to buy as a start-up argument
		Object[] args = getArguments();
		maximalBudget = Integer.parseInt((String) args[0]);
		System.out.println("Maximal budget is " + maximalBudget);

		// Register the bidder service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bidder");
		sd.setName("bidder");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Update the list of bidder agents
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setType("auctioner");
		template.addServices(sd1);
		try {
			DFAgentDescription[] result = DFService.search(this, template); 
			System.out.println("Found the following auctioner agents:");
			auctioners = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				auctioners[i] = result[i].getName();
				System.out.println(auctioners[i].getName());
			}
			auctioner = auctioners[0];
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}


		// Perform the request
		this.addBehaviour(new RequestPerformer());
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Bidder-agent "+getAID().getName()+" terminating.");
	}

	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Book-buyer agents to request seller 
	   agents the target book.
	 */
	private class RequestPerformer extends Behaviour {
		boolean done = false;
		private boolean updateBid = false;
		public RequestPerformer(){
			super();

		}

		public void action() {
			// Send the cfp to all sellers
			System.out.println("Requesting price from auctioner");
			ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
			req.addReceiver(auctioner);
			myAgent.send(req);

			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.blockingReceive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String bid = msg.getContent();
				System.out.println("Starting bid price is " +  bid);
				int currentBid = Integer.parseInt(bid);
				if (currentBid < maximalBudget){
					System.out.println("Current bid " + currentBid + " is < max budget" +
							maximalBudget);

					int price = currentBid + inc;
					System.out.println("Bidding with " + price);
					ACLMessage reply = msg.createReply();
					reply.setContent(String.valueOf(price));
					reply.setPerformative(ACLMessage.PROPOSE);
					myAgent.send(reply);
				}
				else {
					done = true;
				}
			}
			else {
				block();
			}
		}

		public boolean done() {
			return done == true;
		}
	}  // End of inner class RequestPerformer
}
