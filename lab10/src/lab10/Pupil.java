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
	Operation op;
	int incorrectAttempts;
	int attempts = 0;
	Random r;
	public ResponderBehaviour(Agent agent) {
		super(agent);
		r = new Random();
		incorrectAttempts = r.nextInt(3);
		System.out.println(myAgent.getLocalName()+": Incorrect attemtps " + incorrectAttempts);
	}
	public void action() {
		aclMessage = myAgent.blockingReceive(mt);
		if (aclMessage!=null) {
			System.out.println(myAgent.getLocalName()+": I receive message.\n"+aclMessage);
			if (aclMessage.getContent().lastIndexOf("A:")==-1){
				op = new Operation(aclMessage.getContent());
				System.out.println(myAgent.getLocalName()+": Received " + op.expression);
				int ans = op.result;
				if(attempts < incorrectAttempts && r.nextInt(2) == 0){
						attempts++;
						ans = r.nextInt(100);
				}

				System.out.println(myAgent.getLocalName()+": Answering " + op.expression + " " + ans);

				AID r = new AID ("teacher@" + this.myAgent.getHap(), AID.ISGUID);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
				aclMessage.addReceiver(r);
				aclMessage.setContent(ans+"");
				myAgent.send(aclMessage);
			} else{
				String content = aclMessage.getContent();
				int correctAnswers = Integer.parseInt(content.substring(2));
				System.out.println(myAgent.getLocalName() +": I answered correctly to " + correctAnswers + " answers");

				AID r = new AID ("teacher@" + this.myAgent.getHap(), AID.ISGUID);
				ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
				aclMessage.addReceiver(r);
				aclMessage.setContent("Bye");
				myAgent.send(aclMessage);
			}
		} else {
			this.block();
		}
	}
	public boolean done() {
		return false;
	}
}
