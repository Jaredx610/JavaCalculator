
public class DoubleStack {
	final int MAX_STACK = 20;
	private double items[];
	private int top;
	
	public DoubleStack(){
		items = new double[MAX_STACK];
		top = -1;
	}
	public boolean isEmpty(){
		return top < 0;
	}
	public boolean isFull(){
		return top == MAX_STACK-1;
	}
	public void push(double newItem){
		if(!isFull()){
			items[++top] = newItem;
		}
		else{
			System.err.println("Cannot push onto a full double stack.");
		}
	}
	public void printDoubleStack(){
		DoubleStack temp = new DoubleStack();
		while(!isEmpty()){
			temp.push(pop());
		}
		while(!temp.isEmpty()){
			double obj = temp.pop();
			System.out.print(obj+" ");
			push(obj);
		}
	}
	public double pop(){
		if(!isEmpty()){
			return items[top--];
		}
		else{
			System.err.println("Cannot pop from an empty double stack.");
			return -1;
		}
	}
	public double peek(){
		if(!isEmpty()){
			return items[top];
		}
		else{
			System.err.println("Cannot peek from an empty double stack.");
			return ' ';
		}
	}
}
