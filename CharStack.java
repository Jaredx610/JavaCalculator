
public class CharStack {
	final int MAX_STACK = 20;
	private char items[];
	private int top;
	
	public CharStack(){
		items = new char[MAX_STACK];
		top = -1;
	}
	public boolean isEmpty(){
		return top < 0;
	}
	public boolean isFull(){
		return top == MAX_STACK-1;
	}
	public void push(char newItem){
		if(!isFull()){
			items[++top] = newItem;
		}
		else{
			System.err.println("Cannot push onto a full stack.");
		}
	}
	public void printCharStack(){
		CharStack temp = new CharStack();
		while(!isEmpty()){
			temp.push(pop());
		}
		while(!temp.isEmpty()){
			char obj = temp.pop();
			System.out.print(obj+" ");
			push(obj);
		}
	}
	public char pop(){
		if(!isEmpty()){
			return items[top--];
			
		}
		else{
			System.err.println("Cannot pop from an empty char stack.");
			return ' ';
		}
	}
	public char peek(){
		if(!isEmpty()){
			return items[top];
		}
		else{
			System.err.println("Cannot peek from an empty char stack.");
			return ' ';
		}
	}
}
