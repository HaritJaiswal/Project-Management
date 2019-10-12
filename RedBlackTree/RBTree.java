package RedBlack;


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {
	public RedBlackNode<T,E> root;
	public RBTree(){
		this.root = new RedBlackNode(null);
		this.root.color = 1;
	}
    int no_of_nodes = 0;

    @Override
    public void insert(T key, E value) {
    	if(root!=null && root.key==null){
    		root.key = key;
    		root.list.add(value);
    		return;
    	}

    	RedBlackNode<T, E> current = root;
    	RedBlackNode<T, E> parent = null;

    	while(current!=null){
    		parent=current;
    		if(key.compareTo(current.key)<0){
    			current = current.left;
    		}
    		else if(key.compareTo(current.key)>0){
    			current = current.right;
    		}
    		else{
    			break;
    		}
    	}

    	// System.out.println("in insert, before fixing-  "+parent.key);

    	if(key.compareTo(parent.key)<0){
    		parent.left = new RedBlackNode(key);
            no_of_nodes++;
    		parent.left.parent = parent;
    		parent.left.list.add(value);
    		// System.out.println("In insert, to the left- ");
    		this.fixInsert(parent.left);
    	}
    	else if(key.compareTo(parent.key)>0){
    		parent.right = new RedBlackNode(key);
            no_of_nodes++;
    		parent.right.parent = parent;
    		parent.right.list.add(value);
    		// System.out.println("In insert, to the right- ");
    		this.fixInsert(parent.right);
    	}

    	else{
    		if(parent!=null){
	    		parent.list.add(value);
	    		// System.out.println("In insert, value added to parent list- ");
	    	}
    	}


    }

    private RedBlackNode<T, E> grandparent(RedBlackNode<T, E> node){
        if(node.parent!=null){
            return node.parent.parent;
        }
        else{
            return null;
        }
    }

    private void left_rotate(RedBlackNode<T, E> node){
    	RedBlackNode<T, E> left_above = node.parent;
    	node.parent = node.parent.parent;
    	RedBlackNode<T, E> l1 = node.left;

    	if(node.parent!=null && left_above==node.parent.left){
    		node.parent.left = node;
    	}

    	else if(node.parent!=null && left_above==node.parent.right){
    		node.parent.right = node;
    	}

    	else if(node.parent==null){
    		this.root = node;
    	}

    	left_above.parent = node;
    	left_above.right = l1;
    	node.left = left_above;
    	if(l1!=null){l1.parent = left_above;}
    }

    private void right_rotate(RedBlackNode<T, E> node){
    	RedBlackNode<T, E> right_above = node.parent;
    	node.parent = node.parent.parent;
    	RedBlackNode<T, E> r1 = node.right;
    	
    	if(node.parent!=null && right_above==node.parent.left){
    		node.parent.left = node;
    	}

    	else if(node.parent!=null && right_above==node.parent.right){
    		node.parent.right = node;
    	}

    	else if(node.parent==null){
    		this.root = node;
    	}

    	right_above.parent = node;
    	right_above.left = r1;
    	node.right = right_above;
    	if(r1!=null){r1.parent = right_above;}
    }

    private void fixInsert(RedBlackNode<T, E> node){
    	if(node.parent==null){
    		node.color = 1;
    	}

    	if(node.parent!=null && node.parent.color==1){return;}

        RedBlackNode<T, E> uncle;
        while(node.parent!=null && node.parent.color==0){
        	// System.out.println("In while loop of fixInsert- "+ node.key);
            if(this.grandparent(node)!=null && node.parent==this.grandparent(node).left){
                uncle = this.grandparent(node).right;
                if(uncle!=null && uncle.color==0){
                	// System.out.println("parent at left of grandparent & uncle is RED ");
                    uncle.color = 1;
                    node.parent.color = 1;
                    if(this.grandparent(node)!=null){this.grandparent(node).color = 0;}
                    node=this.grandparent(node);
                }
                else{
                	if(node==node.parent.right){
                		// System.out.println("parent at left of grandparent & LEFT-RIGHT case ");
                		this.left_rotate(node);
                		node.color = 1;
                		node.parent.color = 0;
                		this.right_rotate(node);
                	}
                	else{
                		node.parent.color = 1;
                		if(this.grandparent(node)!=null){this.grandparent(node).color = 0;}
                		this.right_rotate(node.parent);
                	}
                }
            }
        

        else if(this.grandparent(node)!=null && node.parent==this.grandparent(node).right){
            uncle = this.grandparent(node).left;
            if(uncle!=null && uncle.color==0){
            	// System.out.println("parent at right of grandparent & uncle is RED ");
            	uncle.color = 1;
                node.parent.color = 1;
                if(this.grandparent(node)!=null){this.grandparent(node).color = 0;}
				node=this.grandparent(node);    
	        }
            else{
            	if(node==node.parent.left){
            		// System.out.println("parent at right of grandparent & RIGHT-LEFT case ");
                		this.right_rotate(node);
                		node.color = 1;
                		node.parent.color = 0;
                		this.left_rotate(node);
                	}
                	else{
                		node.parent.color = 1;
                		if(this.grandparent(node)!=null){this.grandparent(node).color = 0;}
                		this.left_rotate(node.parent);
                	}
            }
        }

        if(node==root){
            break;
        }
    }

    root.color = 1;
    return;
}

    @Override
    public RedBlackNode<T, E> search(T key) {
    	if(root==null || (root!=null && root.key==null)){
    		return null;
    	}

        RedBlackNode<T, E> node = root;

        while(node!=null){
        	if(key.compareTo(node.key)<0){
	    		node = node.left;
    		}
    		else if(key.compareTo(node.key)>0){
    			node = node.right;

    		}
    		else{
				break;
    		}
        }
        if(node==null){
            return new RedBlackNode<T,E>(null);
        }
        return node;
    }
}
