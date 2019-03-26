package packetHandler;
public class SLLNode<N>{
	private N data;
	private SLLNode<N> next;
	
	
	public SLLNode(N data, SLLNode<N> next) {
		super();
		this.data = data;
		this.next = next;
	}
	
	public SLLNode(N data) {
		super();
		this.data = data;
		this.next = null;
	}
	
	public SLLNode() {
		super();
	}
	
	public N getData() {
		return data;
	}
	
	public void setData(N data) {
		this.data = data;
	}
	
	public SLLNode<N> getNext() {
		return next;
	}
	
	public void setNext(SLLNode<N> next) {
		this.next = next;
	}
	
	
	@Override
	public String toString() {
		return "[" + data + "]";
	}
}
