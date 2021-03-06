public class DoubleChain{

		DoubleChain prev; 
		double val; 
		DoubleChain next; 

	public DoubleChain(double num){
		DoubleChain prev = null;
		double val = num;
		DoubleChain next = null;
	}

	public DoubleChain(DoubleChain front, double num, DoubleChain back){
		DoubleChain prev = front;
		double val = num;
		DoubleChain next = back;
	}

/*Returns the back of a DoubleChain*/

	public static DoubleChain getBack(DoubleChain d){
		return d.prev;
	}

/*Returns the front of a DoubleChain*/

	public static DoubleChain getFront(DoubleChain d){
		return d.next;
	}

/*Inserts a value at the back of a DoubleChain*/

	public static void insertBack(DoubleChain d, double val){
		DoubleChain temp = d;
		if (temp.next == null){
		temp.next = DoubleChain(val);
		}
		else{
		temp = temp.next;
		insertBack(temp, val);
		}
		d = temp;
	}

/*Inserts a value at the front of a DoubleChain*/

	public static void insertFront(DoubleChain d, double val){
		DoubleChain temp = d;
		if (temp.prev == null){
		temp.prev = DoubleChain(val);
		}
		else{
		temp = temp.prev;
		insertFront(temp, val);
		}
		d = temp;
	}

/*Returns a string representation of a DoubleChain*/

	public static String toString(DoubleChain d){
		return null;
	}





    public static void main(String[] args){

    }


}
