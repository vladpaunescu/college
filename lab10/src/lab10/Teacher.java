package lab10;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Teacher extends Agent{

	protected void setup() {
		System.out.println("Hello. My name is "+this.getLocalName());
		Behaviour behaviour = new TeacherBehaviour(this);
		this.addBehaviour(behaviour);
	}
}


class TeacherBehaviour extends Behaviour {
	private Random r = new Random();
	
	private static final MessageTemplate mt =
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private int step = 0;
	public TeacherBehaviour(Agent agent){
		super(agent);
	}
	public void action() {
		AID r = new AID ("pupil@" + this.myAgent.getHap(), AID.ISGUID);
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
		aclMessage.addReceiver(r);
		aclMessage.setContent("Hello! How are you?");
		myAgent.send(aclMessage);
		
		ACLMessage responseMessage = myAgent.receive(mt);
		if (responseMessage!=null) {
			System.out.println(myAgent.getLocalName()+": I receive message.\n"+aclMessage);
		} else {
			this.block();
		}
		
		step++;
	}
	public boolean done() {
		return step == 20;
	}
}
