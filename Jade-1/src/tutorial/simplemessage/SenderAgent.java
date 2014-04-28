package tutorial.simplemessage; 

import jade.core.AID; 
import jade.core.Agent; 
import jade.lang.acl.ACLMessage; 

public class SenderAgent extends Agent { 
	protected void setup() { 
		System.out.println("Hello. My name is "+this.getLocalName()); 
		sendMessage(); 
	} 

	private void sendMessage() { 
		AID r = new AID ("jack@"+getHap(), AID.ISGUID); 
		ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST); 
		aclMessage.addReceiver(r); 
		aclMessage.setContent("Hello! How are you?"); 
		this.send(aclMessage); 
	} 
}