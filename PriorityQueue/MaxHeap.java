package PriorityQueue;
import java.util.ArrayList;


public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	public ArrayList<HeapNode<T>> list = new ArrayList();
	int global_timer = 0;

	private int left_child(int i){
		return 2*i+1;
	}

	private int right_child(int i){
		return 2*i+2;
	}

	private int parent(int i){
		if(i==0){
			return 0;
		}
		return (i-1)/2;
	}

	private void swap(int i, int j){
		HeapNode<T> temp = list.get(i);
		list.set(i,list.get(j));
		list.set(j,temp);
	}

	private void bubble_up(int i){
		if(i==0){return;}

		else{
			if(list.get(i).compareTo(list.get(this.parent(i)))>0){
				// System.out.println("Inside Bubble up, the swapped node, matlab new node: "+this.list.get(i).obj);
				// System.out.println("Inside Bubble up, the old node: "+this.list.get(this.parent(i)).obj);
				this.swap(i,this.parent(i));
				this.bubble_up(this.parent(i));
				
			}
			return;
		}
	}

	private void bubble_down(int i){
		int size = list.size();
		int l = this.left_child(i);
		int r = this.right_child(i);
		// System.out.println("in bubble_down, l & r: "+l+" "+r);

		if(l<size && r<size){
			if(list.get(l).compareTo(list.get(r))>=0 && list.get(l).compareTo(list.get(i))>0){
				// System.out.println("In bubble_down,  list.get(l) "+list.get(l)+"list.get(r) "+list.get(r));
				swap(l,i);
				this.bubble_down(l);
			}

			else if(list.get(l).compareTo(list.get(r))<0 && list.get(r).compareTo(list.get(i))>0){
				swap(r,i);
				this.bubble_down(r);
			}
		}
		if(r==size && l<size){
			if(list.get(l).compareTo(list.get(i))>0){
				//System.out.println("In bubble_down,  list.get(l) "+list.get(l)+"list.get(r) "+list.get(r));
				swap(l,i);
			}
		}

		if(size==2){
			if(list.get(l)!=null && list.get(l).compareTo(list.get(i))>0){
				swap(l,i);
			}
		}

	}


    @Override
    public void insert(T element) {
    	HeapNode<T> node = new HeapNode(element, global_timer);
    	list.add(node);
    	// System.out.println("In MaxHeap, node to be inserted: "+node.obj);
    	global_timer++;
    	int size = list.size();

    	this.bubble_up(size-1);
    }

    @Override
    public T extractMax() {
    	if(list.size()==0){
    		return null;
    	}

    	HeapNode<T> val = list.get(0);
		list.set(0,list.get(list.size()-1));
		list.remove(list.size()-1);

		this.bubble_down(0);
		
		return val.obj;  
    }

}
