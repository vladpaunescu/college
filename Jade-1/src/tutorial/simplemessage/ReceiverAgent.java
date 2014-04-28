package tutorial.simplemessage; 
 
import jade.core.Agent; 
 
public class ReceiverAgent extends Agent { 
 
 protected void setup() { 
 System.out.println("Hello. My name is "+this.getLocalName()); 
 addBehaviour(new ResponderBehaviour(this)); 
 } 
} 