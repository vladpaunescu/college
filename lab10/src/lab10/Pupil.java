package lab10;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Pupil extends Agent{

	protected void setup() {
		System.out.println("Hello. My name is "+this.getLocalName());
		addBehaviour(new ResponderBehaviour(this));
	}
}


class ResponderBehaviour extends SimpleBehaviour {
	private static final MessageTemplate mt =
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private ACLMessage aclMessage;
	public ResponderBehaviour(Agent agent) {
		super(agent);
	}
	public void action() {
		aclMessage = myAgent.receive(mt);
		if (aclMessage!=null) {
			System.out.println(myAgent.getLocalName()+": I receive message.\n"+aclMessage);
			AID r = new AID ("teacher@" + this.myAgent.getHap(), AID.ISGUID);
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(r);
			aclMessage.setContent("Fine");
			myAgent.send(aclMessage);
		} else {
			this.block();
		}
	}
	public boolean done() {
		return false;
	}
}
