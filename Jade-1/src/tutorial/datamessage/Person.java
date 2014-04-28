package tutorial.datamessage; 

public class Person { 
	private String name; 
	private Integer age; 

	public Person() { 
	} 

	public void setName(String name) { 
		this.name = name; 
	} 

	public void setAge(Integer age) { 
		this.age = age; 
	} 

	public String getName() { 
		return name; 
	} 


	public Integer getAge() { 
		return age; 
	} 

	public String toString() { 
		return "(person\n\t:name " + getName() + "\n\t:age " + getAge() + ")"; 
	} 
} 
