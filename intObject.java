
public class intObject {
	public intObject(int i, boolean b) {
		data=i;
		permenant=b;
	}
	int data;
	boolean permenant;
	public int getData() {
		return data;
	}
	public void setData(int k) {
		this.data=k;
		
	}
	public void setPermData(int i) {
		this.data=i;
		permenant=true;
		
	}
}
