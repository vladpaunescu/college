
package auction;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Auctioner extends Agent {

	private AID[] bidders;
	private int startingPrice;
	private AID bestBidder;
	boolean finish[] = new boolean[2];
	Semaphore sempahore = new Semaphore(1);
	Semaphore done = new Semaphore(0);

	// Put agent initializations here
	protected void setup() {
		Object[] args = getArguments();
		startingPrice = Integer.parseInt((String) args[0]);
		System.out.println("Starting price is " + startingPrice);

		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("auctioner");
		sd.setName("auctioner");
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
		sd1.setType("bidder");
		template.addServices(sd1);
		try {
			DFAgentDescription[] result = DFService.search(this, template); 
			System.out.println("Found the following bidder agents:");
			bidders = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				bidders[i] = result[i].getName();
				System.out.println(bidders[i].getName());
			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		//		// Send the cfp to all sellers
		//		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		//		for (int i = 0; i < bidders.length; ++i) {
		//			cfp.addReceiver(bidders[i]);
		//		}
		//		cfp.setContent(String.valueOf(startingPrice));
		//		send(cfp);

		// Add the behaviour serving queries from buyer agents
		addBehaviour(new OfferRequestsServer());
		addBehaviour(new BidRequestServer());


		System.out.println("Bidding done");


	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		System.out.println("Seller-agent "+getAID().getName()+" terminating.");
	}

	private class OfferRequestsServer extends Behaviour {
		//	boolean done = false;
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String content = msg.getContent();
				System.out.println("Auction from " + msg.getSender().getName() +
						" at " + content);
				Integer price = Integer.parseInt(content);
				if (price > startingPrice){
					synchronized (myAgent) {
						System.out.println("Bid " + price + " > " + startingPrice);
						startingPrice = price;
						bestBidder = msg.getSender();
					}
				}
				//	myAgent.send(reply);
			}
			else {
					if(!sempahore.tryAcquire()){
						System.out.println("Finish 1");
						done.release();

					}else {
						System.out.println("wait 1");
						block();
						System.out.println("Release 1");
						sempahore.release();
					}
			}
		}

		public boolean done() {
			return false;
		}


	}  // End of inner class OfferRequestsServer


	private class BidRequestServer extends Behaviour {
		//boolean done = false;
		public void action() {
			System.out.println("Processing price request from bidder");
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				ACLMessage reply = msg.createReply();
				System.out.println("Receiveed price req from " + msg.getSender().getName() +
						" at " +  msg.getSender().getName());
				synchronized (myAgent) {
					reply.setContent(String.valueOf(startingPrice));
					reply.setPerformative(ACLMessage.CFP);
				}
				myAgent.send(reply);
			}
			else { 
					if(!sempahore.tryAcquire()){
						System.out.println("Finish 2");
						done.release();
					} else {
						System.out.println("wait 2");
						block();
						System.out.println("Release 2");
						sempahore.release();
					}
			}
		}


		// End of inner class OfferRequestsServer

		public boolean done() {
			return false;
		}
	}



}
