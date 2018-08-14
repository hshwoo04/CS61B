/* BSTStringSet class */

public class BSTStringSet implements StringSet {
String cNode;
BSTStringSet cLeft;
BSTStringSet cRight;

	public BSTStringSet() {
		cNode = null;
	}

	public BSTStringSet(String s) {
		cNode = s;
		cLeft = null;
		cRight = null;
	}

	public void put(String s) {
		if (contains(s)) {
			return;
		}

		if (this.cNode == null) {
			this.cNode = s;
		} else if (this.cLeft == null) {
			this.cLeft = new BSTStringSet(s); 
		} else if (this.cRight == null) {
			this.cRight = new BSTStringSet(s);
		} else {
			if (this.cNode.compareTo(s) < 0) {
				this.cLeft.put(s);
			} else if (this.cNode.compareTo(s) > 0) {
				this.cRight.put(s);
			}
		}
	}

	public boolean contains(String s) {
		if (this.cNode == null) {
			return false;
		}
		if (s.equals(this.cNode)) {
			return true;
		} else if (s.compareTo(this.cNode) < 0) {
			if (this.cLeft == null) {
				return false;
			} 
			return this.cLeft.contains(s);
		} else if ((s.compareTo(this.cNode)) > 0) {
			if (this.cRight == null) {
				return false;
			}
			return this.cRight.contains(s);
		}
		return false;
	}
}
