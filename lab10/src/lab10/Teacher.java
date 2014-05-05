package lab10;

import java.util.HashMap;
import java.util.Map;
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
	private int correctAnswers = 0;
	private Operation op;
	Map <Integer, String> ops;

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
		if (step < 20){
			op = generateOperation();
			System.out.println("Generated operation " + op.expression);
			aclMessage.setContent(op.expression);
			myAgent.send(aclMessage);

			ACLMessage responseMessage = myAgent.blockingReceive(mt);
			if (responseMessage!=null) {
				System.out.println(myAgent.getLocalName()+": I receive message.\n"+responseMessage);
				int response = Integer.parseInt(responseMessage.getContent());
				System.out.println(myAgent.getLocalName()+": Received " + response);
				if (response == op.result){
					System.out.println("Correct answer");
					correctAnswers++;
				}
			} else {
				this.block();
			}

		} else {
			System.out.println(myAgent.getLocalName()+":" + "Sending results " + correctAnswers);
			aclMessage.setContent("A:" + correctAnswers);
			myAgent.send(aclMessage);

			ACLMessage responseMessage = myAgent.blockingReceive(mt);
			if (responseMessage!=null) {
				System.out.println(myAgent.getLocalName()+": I receive message.\n"+responseMessage);
			;
			} else {
				this.block();
			}

		}

		step++;
	}
	public boolean done() {
		return step == 21;
	}

	private Operation generateOperation(){
		int a = r.nextInt(10) + 1;
		int b = r.nextInt(10) + 1;
		int op = r.nextInt(4);
		if (op == 0){
			return new Operation(a, b, op, a+b);
		}
		if(op == 1){
			return new Operation(a, b, op, a-b);
		}
		if (op == 2){
			return new Operation(a, b, op, a*b);
		}
		return new Operation(a, b, op, a/b);

	}
}
