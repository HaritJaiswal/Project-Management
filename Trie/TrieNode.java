package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	char c;
	public Object obj;
	TrieNode<T>[] children;
	boolean end;
	int level;
	int count;

	TrieNode(char c, Object obj){
		this.end = false;
		this.children = new TrieNode[95];
		this.c = c;
		this.obj = obj;
		this.level = 0;
		this.count = 0;
	} 

    @Override
    public T getValue() {
        return (T)obj;
    }


}