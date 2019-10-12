package RedBlack;

import Util.RBNodeInterface;
import java.util.List;
import java.util.ArrayList;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	public T key;
	public ArrayList<E> list = new ArrayList();
	int color; // 0 for RED, 1 for BLACK
	public RedBlackNode<T,E> left, right, parent;

	RedBlackNode(T key){
		this.key = key;
		this.color = 0;
		this.left = null;
		this.right = null;
		this.parent = null;
	}


    @Override
    public E getValue() {
    	if(list.size()==1){
    		return list.get(0);
    	}
        return null;
    }

    @Override
    public List<E> getValues() {
    	if(list.size()==0){
    		return null;
    	}
        return list;
    }
}
