package lab10;

import java.util.HashMap;
import java.util.Map;

public class Operation {

	public int a;
	public int b;
	public int opCode;
	int result;
	public String expression;
	
	static Map<Integer, String> opCodes = new HashMap<>();
	static{
		opCodes.put(0, "+");
		opCodes.put(1, "-");
		opCodes.put(2, "*");
		opCodes.put(3, "/");
	}


	public Operation(String expression){
		this.expression = expression;
		result = decode();

	}

	public Operation(int a, int b, int opCode, int result){
		this.a = a;
		this.b = b;
		this.opCode = opCode;
		this.result = result;
		expression = encode();
	}

	public int decode(){
		if(expression != null){
			if(expression.lastIndexOf('+') != -1){
				a = Integer.parseInt(expression.substring(0, expression.lastIndexOf('+')));
				b =  Integer.parseInt(expression.
						substring(expression.lastIndexOf('+') + 1));
				opCode = 0;
				return a + b;
				
			}
			if(expression.lastIndexOf('-') != -1){
				a = Integer.parseInt(expression.substring(0, expression.lastIndexOf('-')));
				b =  Integer.parseInt(expression.
						substring(expression.lastIndexOf('-') + 1));
				opCode = 1;
				return a - b;
			}
			if(expression.lastIndexOf('*') != -1){
				a = Integer.parseInt(expression.substring(0, expression.lastIndexOf('*')));
				b =  Integer.parseInt(expression.
						substring(expression.lastIndexOf('*') + 1));
				opCode = 2;
				return a * b;
			}
			if(expression.lastIndexOf('/') != -1){
				a = Integer.parseInt(expression.substring(0, expression.lastIndexOf('/')));
				b =  Integer.parseInt(expression.
						substring(expression.lastIndexOf('/') + 1));
				opCode = 3;
				return a / b;
			}
			
		}
		return 0;

	}

	public String encode(){
		String op = opCodes.get(opCode);
		return a + op + b;
	}
}
