/* Nome: Vinicius Pessoa Duarte          */
/* Numero USP: 8941043                   */
/* Disciplina: MAC-0323                  */
/* Exercicio: Faster Word Ladder/4.1.12  */


/******************************************************************************
 *  Compilation:  javac IndexSET.java
 *  Execution:    java IndexSET
 *  Dependencies: ST.java
 *  
 *  Indexed set. Assigns an integer index to each new element.
 *
 *  Remarks
 *  -------
 *   - could use generic array for ts
 *
 *  % java IndexSET
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.*;

public class IndexSET<Key extends Comparable<Key>> {
    private int N;
    private ST<Key, Integer> st = new ST<Key, Integer>();
    private ST<Integer, Key> ts = new ST<Integer, Key>();

    public void add(Key key) {
        if (!st.contains(key)) {
            st.put(key, N);
            ts.put(N, key);
            N++;
        }
    }

    public int indexOf(Key key) {
        return st.get(key);
    }

    public Key keyOf(int index) {
        return ts.get(index);
    }

    public boolean contains(Key key) { return st.contains(key);  }
    public int size()                { return st.size();         }
    public Iterable<Key> keys()      { return st.keys();         }


   /***************************************************************************
    * Test routine.
    ***************************************************************************/
    public static void main(String[] args) {
        IndexSET<String> set = new IndexSET<String>();

        // insert some keys
        set.add("www.cs.princeton.edu");
        set.add("www.princeton.edu");
        set.add("www.yale.edu");
        set.add("www.amazon.com");
        set.add("www.simpsons.com");

        // does given key exist?
        System.out.println(set.contains("www.cs.princeton.edu"));
        System.out.println(set.contains("www.amazon.com"));
        System.out.println(!set.contains("www.amazon.edu"));
        System.out.println();

        // print out all keys in the set
        for (String s : set.keys()) {
            System.out.println(s + " : " + set.indexOf(s));
        }

    }

}
