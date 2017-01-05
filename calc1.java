import java.util.Scanner;

public class calc1 {

	Scanner kin = new Scanner(System.in);
	String symbols[] = new String[255];
	double values[] = new double[255];
	String line = "";
	String nline = "";
	int ind = 0;

    public static void main(String[] args) {
    	new calc1().nonStatic();
    }
    public void nonStatic(){
    	boolean stop = false;


		System.out.println("READY.");
		while(!stop){
			System.out.print("> ");
			line = kin.nextLine().trim();
			if(line.equals("stop")){
				stop = true;
			}
			else{
				String cmd = line.trim();
				if(cmd.contains("load ")){
					loadInst(line);
				}
				else if(cmd.contains("mem ")){
					memInst(line);
				}
				else if(line.contains("=")){
					assignInst(line);
				}
				else if(line.contains("print ")){
					printInst(line);
				}
				else if(line.substring(0,2).contains("//")){
					//The line is a comment
				}
			}
		}
		kin.close();
    }

    public void loadInst(String cmd){
    	String s = (cmd.substring(cmd.indexOf(" ")));
    	System.out.print("VALUE FOR "+s.toUpperCase().trim()+": ");
    	nline = kin.nextLine();
    	double val = Double.parseDouble(nline);
    	values[ind] = val;
    	symbols[ind] = s.trim();
    	System.out.println(symbols[ind]+" = "+values[ind]);
    	ind++;
		//System.out.println("Loaded "+val);
    }

    public void memInst(String cmd){
		String s = cmd.substring(cmd.indexOf(" "));
		values[ind] = 0.0;
		symbols[ind] = s;
		ind++;
    }
    public void assignInst(String cmd){
    	cmd = cmd.replaceAll(" ","");
    	cmd = cmd.replaceAll("sqrt","|");
    	String parts[] = cmd.split("=");
		String var = parts[0];
		DoubleStack ds = new DoubleStack(); //double stack
		CharStack os = new CharStack(); //String stack
		String temp = parts[1].toString();
		String[] nocom = temp.split("//"); //Take out comments
		String assign = nocom[0].trim();
		String str = "";
		char ch = ' ';
		double result = 0.0;
		assign = assign.trim();
		System.out.println("Cmd("+cmd+")");
		for(int i = 0;i < assign.length();i++){
			ch = assign.charAt(i);
			if(Character.isLetter(ch) || Character.isDigit(ch)||ch == '.'){
				str += ch;
				System.out.println("Added "+ch+" to string");
			}
			else if(ch == '+' || ch == '-'||ch == '/' || ch == '*'||ch == '^'||ch == '|'){
				if(str != ""){
					double tempd = 0.0;
					try{
						tempd = Double.parseDouble(str);
					}
					catch (NumberFormatException e){
						tempd = getValue(str.trim());
					}
					System.out.println("\tfirst Tempd = "+tempd+ "(before clear)");
					str = "";
					ds.push(tempd);
				}
				if(os.isEmpty()){
					os.push(ch);
				}
				else{
					if(!os.isEmpty()){
						char cis = os.pop();
						double op1 = ds.pop();
						if(cis != '|'){
							double op2 = ds.pop();
							result = Operate(op1,op2,cis);
						}
						else{
							result = Operate(op1,0.5,cis);
						}
						ds.push(result);
					}
					os.push(ch);
				}
			}
		}
		if(!str.equals("")){
			double tempd = 0.0;
			try{
				tempd = Double.parseDouble(str);
			}
			catch (NumberFormatException e){
				tempd = getValue(str.trim());
			}
			System.out.println("\tTempd = "+tempd+ "(before clear)");
			str = "";
			ds.push(tempd);
		}
		while(!os.isEmpty()){
			char cis = os.pop();
			double op1 = ds.pop();
			if(cis != '|'){
				double op2 = ds.pop();
				result = Operate(op1,op2,cis);
			}
			else{
				result = Operate(op1,0.5,cis);
			}
			ds.push(result);
		}
		result = ds.pop();
		//
		int index = getIndex(var.trim());
		values[index] = result;
		System.out.println(var +" = "+values[index]);
    }
    private double Operate(double op1, double op2, char ch) {
		double r = 0.0;
    	if(ch == '+'){
			r = op2+op1;
			System.out.println(op2+" + "+op1+" = "+r);
		}
		else if(ch == '-'){
			r = (op2-op1);
			System.out.println(op2+" - "+op1+" = "+r);
		}
		else if(ch == '/'){
			r = (op2/op1);
			System.out.println(op2+" / "+op1+" = "+r);
		}
		else if(ch == '*'){
			r = (op2*op1);
			System.out.println(op2+" * "+op1+" = "+r);
		}
		else if(ch == '^'){
			r = Math.pow(op2,op1);
			System.out.println(op2+" ^ "+op1+" = "+r);
		}
		else if(ch == '|'){
			r = Math.pow(op1,op2);
			System.out.println(op1+" ^ "+op2+" = "+r);
		}
		return r;
	}
	public void printInst(String cmd){
		String[] c = cmd.split("print ");
		double v = values[getIndex(c[1].trim())];
		System.out.println("Value of "+c[1]+": "+v);
    }
	public int getIndex(String var){
		int index = -1;
		for(int i = 0; i < ind;i++){
            if(symbols[i].equals(var)){
            	System.out.println("Found "+var+" at i = "+i);
                index = i;
            }
        }
		System.out.println("Index of "+var+": "+index);
        return index;
	}

    public double getValue(String var){
    	double v = 0.0;
    	System.out.println("Finding value of "+var+"...");
    	try{
    		v = Double.parseDouble(var);
    		
    	}
        catch (NumberFormatException e){
        	 for(int i = 0; i < ind;i++){
        		 int ri = getIndex(var); 
                 if( ri != -1){
                     v = values[ri];
                 }
             }
        }
        return v;
    }

}
