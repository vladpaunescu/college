package tutorial; 
import jade.core.Agent; 
public class HelloWorldAgentWithParameters extends Agent { 

	private String service; 

	protected void setup() { 
		Object[] args = getArguments(); 
		service = String.valueOf(args[0]); 

		System.out.println("Hello World. My name is "+this.getLocalName()+ 
				" and I provide "+service+" service."); 
	} 
}