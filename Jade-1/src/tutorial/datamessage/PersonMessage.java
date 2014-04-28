package tutorial.datamessage; 

public class PersonMessage { 

	private Person person = new Person(); 

	public PersonMessage() { 
	} 

	public void set_0(Person a) { 
		person = a; 
	} 

	public void setPerson(Person person) { 
		set_0(person); 
	} 

	public Person get_0() { 
		return person; 
	} 

	public Person getPerson() { 
		return get_0(); 
	} 

} 
