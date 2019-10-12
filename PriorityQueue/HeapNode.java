package PriorityQueue;

public class HeapNode<T extends Comparable> implements Comparable<HeapNode<T>>{
	int timer = -1;
	public T obj;

	public HeapNode(T obj, int timer){
		this.timer = timer;
		this.obj = obj;

	}

	@Override
	public int compareTo(HeapNode<T> n){
		if(this.obj.compareTo(n.obj)!=0){
			return this.obj.compareTo(n.obj);
		}
		else{
			return (n.timer - this.timer);
		} 
	}

}