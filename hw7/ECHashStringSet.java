

public class ECHashStringSet {
	private int numObjects = 0;
	private int numBuckets = 10;
	private Node[] array;

    private class Node {
        private String value;
        private Node next;
        private int hashVal;

        public Node(String s) {
            value = s;
            hashVal = s.hashCode();
        }
    }

	public ECHashStringSet(){
		array = new Node[0];
	}

	public ECHashStringSet(String s) {  // creates the hash table
		array = new Node[5];
		Node hashValue = new Node(s);
		array[hashValue.hashVal % 5]= hashValue;
	}

	public ECHashStringSet(int size) {
		array = new Node[size];
	}

	public void put(String s) { // uses modulus to store it and then follows the linked list until it sees a null (0) then puts itself there
		int val =  s.hashCode() % array.length;
		if (this.contains(s)) {
			return;
		} else {
			for (Node i = this.array[val]; i.next != null; i = i.next) {
				if (i.next == null) {
					Node tempNode = new Node(s);
					i.next = tempNode;
				}
			}
		}
		this.resize();
	}

	public boolean contains(String s) {
		int val = s.hashCode() % this.array.length;
		for (Node i = this.array[val]; i.next != null; i = i.next) {
			if (i.hashVal == s.hashCode()) {
				return true;
			}
		}
		return false;
	}

	public void resize(){
		for (int j = 0; j < array.length; j++) {
			for (Node i = this.array[j]; i.next != null; i = i.next) {
				numObjects++;
				if (i.next.next == null) {
					numObjects++;
				}
			}
		}
		numBuckets = this.array.length;
		if (numObjects/numBuckets >= 5) {
			ECHashStringSet newSet = new ECHashStringSet(this.array.length + 1);
			for (int i = 0; i < this.array.length; i++) {
				for (Node j = this.array[i]; j.next != null; j = j.next) {
					newSet.put(j.value);
				}
			}
		} else if (numObjects/numBuckets < 0.2) {
			numBuckets++;
		} else {
			return;
		}
	}
}
