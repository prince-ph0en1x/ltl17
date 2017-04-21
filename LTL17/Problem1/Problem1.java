import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Problem1 {
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	private String[] inputs = {"B","E","C","A","D"};

	public int a175 = 6;
	public boolean cf = true;
	public int a52 = 9;
	public int a176 = 7;
	public String a166 = "e";
	public String a167 = "e";
	public String a62 = "f";

	private void errorCheck() {

	}private  void calculateOutputm1(String input) {
    if(((((a166.equals("e")) && cf) && (input.equals("C"))) && (a176 == 6))) {
    	cf = false;
    	a166 = "f";
    	a52 = 11; 
    	System.out.println("Y");
    } 
    if(((input.equals("E")) && (((a166.equals("e")) && cf) && (a176 == 6)))) {
    	cf = false;
    	a176 = 11; 
    	System.out.println("X");
    } 
    if((((a166.equals("e")) && (cf && (a176 == 6))) && (input.equals("D")))) {
    	cf = false;
    	a166 = "g";
    	a167 = "g"; 
    	System.out.println("W");
    } 
}
private  void calculateOutputm2(String input) {
    if(((input.equals("D")) && (((a166.equals("e")) && cf) && (a176 == 7)))) {
    	cf = false;
    	a176 = 11; 
    	System.out.println("Y");
    } 
}
private  void calculateOutputm3(String input) {
    if((((input.equals("C")) && ((a176 == 8) && cf)) && (a166.equals("e")))) {
    	cf = false;
    	a166 = "h";
    	a175 = 6; 
    	System.out.println("S");
    } 
}
private  void calculateOutputm4(String input) {
    if(((input.equals("B")) && ((a176 == 10) && ((a166.equals("e")) && cf)))) {
    	cf = false;
    	a166 = "i";
    	a62 = "h"; 
    	System.out.println("Y");
    } 
}
private  void calculateOutputm5(String input) {
    if((((cf && (a166.equals("e"))) && (a176 == 11)) && (input.equals("C")))) {
    	cf = false;
    	a166 = "i";
    	a62 = "e"; 
    	System.out.println("U");
    } 
}
private  void calculateOutputm6(String input) {
    if((((cf && (a176 == 12)) && (a166.equals("e"))) && (input.equals("C")))) {
    	cf = false;
    	a166 = "f";
    	a52 = 8; 
    	System.out.println("S");
    } 
    if((((cf && (a176 == 12)) && (input.equals("E"))) && (a166.equals("e")))) {
    	cf = false;
    	a176 = 8; 
    	System.out.println("Z");
    } 
}
private  void calculateOutputm7(String input) {
    if((((a166.equals("f")) && ((a52 == 5) && cf)) && (input.equals("E")))) {
    	cf = false;
    	a166 = "g";
    	a167 = "e"; 
    	System.out.println("Z");
    } 
    if((((a166.equals("f")) && ((a52 == 5) && cf)) && (input.equals("B")))) {
    	cf = false;
    	a52 = 7; 
    	System.out.println("Z");
    } 
}
private  void calculateOutputm8(String input) {
    if((((input.equals("E")) && (cf && (a166.equals("f")))) && (a52 == 7))) {
    	cf = false;
    	a166 = "e";
    	a176 = 11; 
    	System.out.println("Y");
    } 
    if((((cf && (a52 == 7)) && (input.equals("D"))) && (a166.equals("f")))) {
    	cf = false;
    	a166 = "g";
    	a167 = "f"; 
    	System.out.println("U");
    } 
}
private  void calculateOutputm9(String input) {
    if(((a166.equals("f")) && ((a52 == 8) && (cf && (input.equals("D")))))) {
    	cf = false;
    	 
    	System.out.println("S");
    } 
    if((((a52 == 8) && ((a166.equals("f")) && cf)) && (input.equals("E")))) {
    	cf = false;
    	a166 = "e";
    	a176 = 6; 
    	System.out.println("Z");
    } 
    if(((input.equals("B")) && ((cf && (a166.equals("f"))) && (a52 == 8)))) {
    	cf = false;
    	a166 = "g";
    	a167 = "g"; 
    	System.out.println("W");
    } 
}
private  void calculateOutputm10(String input) {
    if((((a166.equals("f")) && ((a52 == 9) && cf)) && (input.equals("D")))) {
    	cf = false;
    	a166 = "e";
    	a176 = 6; 
    	System.out.println("U");
    } 
}
private  void calculateOutputm11(String input) {
    if((((a166.equals("f")) && (cf && (a52 == 11))) && (input.equals("C")))) {
    	cf = false;
    	 
    	System.out.println("Y");
    } 
}
private  void calculateOutputm12(String input) {
    if((((cf && (a166.equals("g"))) && (input.equals("B"))) && (a167.equals("e")))) {
    	cf = false;
    	a166 = "i";
    	a62 = "e"; 
    	System.out.println("U");
    } 
    if(((a166.equals("g")) && ((input.equals("D")) && (cf && (a167.equals("e")))))) {
    	cf = false;
    	a166 = "e";
    	a176 = 10; 
    	System.out.println("W");
    } 
    if(((input.equals("C")) && ((cf && (a167.equals("e"))) && (a166.equals("g"))))) {
    	cf = false;
    	a166 = "f";
    	a52 = 5; 
    	System.out.println("U");
    } 
}
private  void calculateOutputm13(String input) {
    if((((cf && (input.equals("D"))) && (a166.equals("g"))) && (a167.equals("f")))) {
    	cf = false;
    	a166 = "f";
    	a52 = 7; 
    	System.out.println("U");
    } 
    if((((cf && (a166.equals("g"))) && (input.equals("E"))) && (a167.equals("f")))) {
    	cf = false;
    	a166 = "f";
    	a52 = 8; 
    	System.out.println("X");
    } 
    if(((a166.equals("g")) && ((input.equals("C")) && ((a167.equals("f")) && cf)))) {
    	cf = false;
    	a166 = "h";
    	a175 = 4; 
    	System.out.println("W");
    } 
}
private  void calculateOutputm14(String input) {
    if(((a167.equals("g")) && (((a166.equals("g")) && cf) && (input.equals("E"))))) {
    	cf = false;
    	a166 = "i";
    	a62 = "e"; 
    	System.out.println("U");
    } 
    if((((cf && (a166.equals("g"))) && (input.equals("C"))) && (a167.equals("g")))) {
    	cf = false;
    	a166 = "f";
    	a52 = 9; 
    	System.out.println("X");
    } 
}
private  void calculateOutputm15(String input) {
    if(((((input.equals("D")) && cf) && (a167.equals("h"))) && (a166.equals("g")))) {
    	cf = false;
    	a166 = "i";
    	a62 = "f"; 
    	System.out.println("X");
    } 
}
private  void calculateOutputm16(String input) {
    if((((a175 == 4) && ((a166.equals("h")) && cf)) && (input.equals("D")))) {
    	cf = false;
    	 
    	System.out.println("S");
    } 
}
private  void calculateOutputm17(String input) {
    if(((a166.equals("h")) && ((a175 == 6) && (cf && (input.equals("E")))))) {
    	cf = false;
    	a166 = "e";
    	a176 = 11; 
    	System.out.println("X");
    } 
    if(((a166.equals("h")) && ((cf && (input.equals("B"))) && (a175 == 6)))) {
    	cf = false;
    	a166 = "e";
    	a176 = 12; 
    	System.out.println("W");
    } 
}
private  void calculateOutputm18(String input) {
    if(((a175 == 8) && ((cf && (a166.equals("h"))) && (input.equals("D"))))) {
    	cf = false;
    	a175 = 6; 
    	System.out.println("Z");
    } 
}
private  void calculateOutputm19(String input) {
    if(((input.equals("E")) && (((a62.equals("e")) && cf) && (a166.equals("i"))))) {
    	cf = false;
    	a166 = "e";
    	a176 = 10; 
    	System.out.println("W");
    } 
    if(((a166.equals("i")) && ((a62.equals("e")) && ((input.equals("B")) && cf)))) {
    	cf = false;
    	a166 = "g";
    	a167 = "f"; 
    	System.out.println("S");
    } 
    if((((a62.equals("e")) && ((a166.equals("i")) && cf)) && (input.equals("D")))) {
    	cf = false;
    	a166 = "h";
    	a175 = 8; 
    	System.out.println("W");
    } 
}
private  void calculateOutputm20(String input) {
    if(((a166.equals("i")) && ((a62.equals("f")) && (cf && (input.equals("C")))))) {
    	cf = false;
    	a166 = "f";
    	a52 = 8; 
    	System.out.println("S");
    } 
    if(((a166.equals("i")) && ((cf && (a62.equals("f"))) && (input.equals("E"))))) {
    	cf = false;
    	a166 = "g";
    	a167 = "e"; 
    	System.out.println("X");
    } 
}
private  void calculateOutputm21(String input) {
    if(((a62.equals("h")) && ((input.equals("C")) && (cf && (a166.equals("i")))))) {
    	cf = false;
    	a166 = "f";
    	a52 = 5; 
    	System.out.println("U");
    } 
    if((((a62.equals("h")) && ((a166.equals("i")) && cf)) && (input.equals("D")))) {
    	cf = false;
    	a166 = "g";
    	a167 = "h"; 
    	System.out.println("U");
    } 
}



public  void calculateOutput(String input) {
 	cf = true;
    if(((a166.equals("e")) && cf)) {
    	if(((a176 == 6) && cf)) {
    		calculateOutputm1(input);
    	} 
    	if((cf && (a176 == 7))) {
    		calculateOutputm2(input);
    	} 
    	if(((a176 == 8) && cf)) {
    		calculateOutputm3(input);
    	} 
    	if((cf && (a176 == 10))) {
    		calculateOutputm4(input);
    	} 
    	if(((a176 == 11) && cf)) {
    		calculateOutputm5(input);
    	} 
    	if(((a176 == 12) && cf)) {
    		calculateOutputm6(input);
    	} 
    } 
    if(((a166.equals("f")) && cf)) {
    	if(((a52 == 5) && cf)) {
    		calculateOutputm7(input);
    	} 
    	if((cf && (a52 == 7))) {
    		calculateOutputm8(input);
    	} 
    	if((cf && (a52 == 8))) {
    		calculateOutputm9(input);
    	} 
    	if((cf && (a52 == 9))) {
    		calculateOutputm10(input);
    	} 
    	if(((a52 == 11) && cf)) {
    		calculateOutputm11(input);
    	} 
    } 
    if((cf && (a166.equals("g")))) {
    	if(((a167.equals("e")) && cf)) {
    		calculateOutputm12(input);
    	} 
    	if(((a167.equals("f")) && cf)) {
    		calculateOutputm13(input);
    	} 
    	if((cf && (a167.equals("g")))) {
    		calculateOutputm14(input);
    	} 
    	if((cf && (a167.equals("h")))) {
    		calculateOutputm15(input);
    	} 
    } 
    if((cf && (a166.equals("h")))) {
    	if((cf && (a175 == 4))) {
    		calculateOutputm16(input);
    	} 
    	if((cf && (a175 == 6))) {
    		calculateOutputm17(input);
    	} 
    	if(((a175 == 8) && cf)) {
    		calculateOutputm18(input);
    	} 
    } 
    if((cf && (a166.equals("i")))) {
    	if(((a62.equals("e")) && cf)) {
    		calculateOutputm19(input);
    	} 
    	if((cf && (a62.equals("f")))) {
    		calculateOutputm20(input);
    	} 
    	if(((a62.equals("h")) && cf)) {
    		calculateOutputm21(input);
    	} 
    } 

    errorCheck();
    if(cf)
    	throw new IllegalArgumentException("Current state has no transition for this input!");
}


public static void main(String[] args) throws Exception {
	// init system and input reader
	Problem1 eca = new Problem1();

	// main i/o-loop
	while(true) {
		//read input
		String input = stdin.readLine();

		 if((input.equals("B")) && (input.equals("E")) && (input.equals("C")) && (input.equals("A")) && (input.equals("D")))
			throw new IllegalArgumentException("Current state has no transition for this input!");
		try {
			//operate eca engine output = 
			eca.calculateOutput(input);
		} catch(IllegalArgumentException e) {
			System.err.println("Invalid input: " + e.getMessage());
		}
	}
}
}