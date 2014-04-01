package lab04.predicates;

import lab04.Robot;

public class ArmEmpty extends Predicate{

	private final static ArmEmpty _instance = new ArmEmpty();
	public Robot robot;
	
	private  ArmEmpty(){}
	
	public static Predicate getInstance(){
		return _instance;
	}
	
	public void setRobot(Robot r){
		 robot = r;
	}
	
	@Override
	public boolean isValid(){
		if(robot != null){
			return robot.armEmpty();
		}
		return false;
	}
}
