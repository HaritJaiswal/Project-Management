package Trie;
import java.util.LinkedList;

public class Trie<T> implements TrieInterface {
    TrieNode<T> root = new TrieNode((char)0, null);
    int maxLevel=0;

    
    @Override
    public boolean delete(String word) {
        if(root==null){
            return false;
        }
        if(this.search(word)==null){
            return false;}

        else{
            root = this.deletex(word, root, 0);
            return true;
        }    

    }

    private TrieNode<T> deletex(String word, TrieNode<T> current, int l){
        if(word.length()==l){
            //System.out.println(word.length()+" Word length matched "+l);
            if(current.end && current.count>0){
                current.end = false;
                current.obj = null;
            }
            else if(current.end && current.count==0){
                //System.out.println("entered else if "+current.end+" "+current.c);
                current = null;
            }
            return current;
        }
        else{
            char data = word.charAt(l);
            int index = data-32;
            //System.out.println("Before Recur deletex "+current.end+" "+current.c+" "+current.children[index].c);
            current.children[index] = this.deletex(word, current.children[index], ++l);
            
            //if(current!=null){System.out.println("After Recur deletex "+current.end+" "+current.c+" "+current.count+" "+current.children[index]);}

            if(current!=null && current.count>=1 && current.children[index]==null){//System.out.println("In 1st of else");
                current.count--;
            }

            if(current!=null && current.count==0 && !current.end){//System.out.println("In 2nd of else");
                current = null;
            }
        }
        return current;
    }
    
    @Override
    public TrieNode search(String word) {
        //System.out.println("entered search");
        int index;
        TrieNode<T> current = root;

        for(int k=0;k<word.length();k++){
            char data = word.charAt(k);
            index = data-32;
            if(current!=null && current.children[index]==null){
                //System.out.println("entered if of search");
                return null;
            }
            else if(current!=null){
                current = current.children[index];
            }
        }
        if(current!= null && current.end){return current;}
        return null;
    }

    @Override
    public TrieNode startsWith(String prefix) {
        int index;
        TrieNode<T> current = root;

        for(int k=0;k<prefix.length();k++){
            char data = prefix.charAt(k);
            index = data-32;
            if(current!=null && current.children[index]==null){
                return null;
            }
            if(current!=null){current = current.children[index];}
        }
        if(current!= null){return current;}
        return null;
    }

    @Override
    public void printTrie(TrieNode trieNode) {
        TrieNode<T> current = trieNode;

        for(int k=0;k<current.children.length;k++){
            if(current.children[k]!=null){
                if(!current.children[k].end){
                    this.printTrie(current.children[k]);
                }
                else{
                    System.out.println(current.children[k].getValue().toString());
                }
            }
        }

        return;
    }

    @Override
    public boolean insert(String word, Object value) {
        if(this.search(word)!=null){//System.out.println("search in insert failed()");
            return false;}
            //System.out.println("in insert");
        int index;
        TrieNode<T> current = root;

        for(int k=0;k<word.length();k++){
            char data = word.charAt(k);
            index = data-32;
            if(current.children[index]==null){
                current.children[index] = new TrieNode(data,null);
                current.count++;
            }
            current.children[index].level = current.level+1;
            maxLevel = Math.max(maxLevel, current.children[index].level);
            current = current.children[index];
        }

        current.obj = value;
        current.end = true;
                    
        return true;
    }

    @Override
    public void printLevel(int level) {
        LinkedList<Character> list = new LinkedList<Character>();
        if(level==0){
            System.out.println("Level "+level+": ");
            return;
        }
        
        if(level<=maxLevel){
            //System.out.println(root);
            this.printLevelx(level, root, list);
            list  = this.lex_sort(list);
        
            System.out.print("Level "+level+": ");
            if(list.size()==0){System.out.println(); 
                return;}
                for (int i=0;i<list.size();i++){
                    char x = list.get(i);
                    if(x!=' '){
                        System.out.print(x);
                        if(i!=list.size()-1){
                        System.out.print(",");
                    } 
                    }      
                }
            System.out.println(); 
        }   
        else{
            System.out.println("Level "+level+": ");
        }
    }

    private void printLevelx(int level, TrieNode node, LinkedList<Character> list){
        TrieNode<T> current = node;
       //System.out.println(current);

        for(int k=0;k<current.children.length;k++){
            if(current!=null && current.children[k]!=null){
                if(current.children[k].level<level){
                    this.printLevelx(level, current.children[k], list);
                }
                else if(current.children[k].level==level){
                    list.add(current.children[k].c);
                }
            }
        }

        return;
    }

    private LinkedList<Character> lex_sort(LinkedList<Character> l){
        if(l.size()==0){return l;}

        int current = 0;
        while(current<l.size()){
           int it = current+1;
            while(it<l.size()){
                if(l.get(current).compareTo(l.get(it))>0){
                    char temp = l.get(current);
                    l.set(current,l.get(it));
                    l.set(it,temp);
                }
                it++;
            }
            current++;
        }

        return l;
    }

    @Override
    public void print() {
        System.out.println("-------------");
        System.out.println("Printing Trie");

        for(int k=1;k<=maxLevel+1;k++){
            this.printLevel(k);
        }
        System.out.println("-------------");
    }
}
