/* Nome: Vinicius Pessoa Duarte          */
/* Numero USP: 8941043                   */
/* Disciplina: MAC-0323                  */
/* Exercicio: Faster Word Ladder/4.1.12  */

import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class FasterWordLadders {
    private static int n = 0;
    private static int[] accum;

    // Le as palavras de um texto agrupando as palavras de diferente
    // comprimento em indices distintos de um vetor de IndexSETs
    public static IndexSET readText (IndexSET<String> words, In in) {
        
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length () > n)
                n = word.length ();
            words.add(word);
        }

        accum = new int[n + 1];
        accum[0] = 0;
        for (String word : words.keys ())
            accum[word.length ()]++;

        for (int i = 1; i <= n; ++i)
            accum[i] = accum[i] + accum[i - 1]; 

        return words;
    } 

    // Cria o grafo das palavras
    public static Graph createGraph (IndexSET<String> words) {

        Graph G = new Graph(words.size ());

        for (int i = 0; i < n; ++i)
            G = NeibEq (words, i, G);

        for (String word1 : words.keys())
            for (String word2 : words.keys())
                if (word1.compareTo(word2) < 0 && isNeighborDif (word1, word2))
                    G.addEdge(words.indexOf(word1), words.indexOf(word2));
        
        return G;
    }

    // Verifica se ha caminho de 'from' ate 'to' e o imprime, caso exista
    public static void connect (Graph G, IndexSET<String> words, String from, String to) {
        
        if (!words.contains(from)) throw new RuntimeException(from + " is not in word list");
        if (!words.contains(to))   throw new RuntimeException(to   + " is not in word list");

        BreadthFirstPaths bfs = new BreadthFirstPaths(G, words.indexOf(from));
        if (bfs.hasPathTo(words.indexOf(to))) {
            StdOut.println(bfs.distTo(words.indexOf(to)));
            for (int v : bfs.pathTo(words.indexOf(to))) {
               StdOut.println(words.keyOf(v));
            }
        }
        else StdOut.println("NOT CONNECTED");
        StdOut.println();
    }
    
    // Verifica se palavras de tamanhos diferentes sao vizinhas
    private static boolean isNeighborDif (String a, String b) {
        if (a.length() != b.length()) {
            String tempA, tempB;
            tempA = a; tempB = b;
            if (a.length () > b.length ()) {
                tempA = tempB; tempB = a;
            }
            if (tempB.length () != 1 + tempA.length () || !tempB.startsWith (tempA))
                return false;
        }
        else
            return false;
        
        return true;
    }

    // Adiciona as arestas para o conjunto de palavras de tamanho len
    private static Graph NeibEq (IndexSET<String> words, int len, Graph G) {
        int size = 0, max = 2;
        String[] strings = new String[max];
        int[] pos = new int[max];

        for (String word1 : words.keys ()) {
            if (word1.length () == len) {
                if (size == max) {
                    strings = resizeStr (strings, max);
                    pos = resizeInt (pos, max);
                    max *= 2;
                }
                strings[size] = word1;
                pos[size] = words.indexOf (word1);
                size++;
            }
        }

        for (int sft = size; sft >= 0; --sft) {
            for (int j = 0; j < size - 1; ++j) {
                for (int k = j + 1; k < size; ++k) {
                    if (strings[j].substring (0, len - 1).compareTo (strings[k].substring(0, len - 1)) == 0)
                        G.addEdge(pos[j], pos[k]);
                    else 
                        break;
                }
            }
            shift (strings, pos, size);
        }        
        return G;
    }

    // Orneda uma lista de strings e um vetor que representa as posicoes
    // originais das strings na lista
    private static void sorting (String[] strings, int[] pos, int size) {
        for (int i = 0; i < size - 1; ++i) {
            for (int j = i + 1; j < size; ++j) {
                if (strings[i].compareTo (strings[j]) > 0) {
                    String temp = strings[i];
                    int tempNum = pos[i];
                    strings[i] = strings[j]; strings[j] = temp;
                    pos[i] = pos[j]; pos[j] = tempNum;
                }
            }
        }
    }

    // Realiza shiit circular nas strings, mantendo ordenacao lexicografica
    private static void shift (String[] strings, int[] pos, int size) {
        for (int j = 0; j < size; ++j) {
            String temp = strings[j].substring(strings[j].length () - 1, strings[j].length ());
            strings[j] = temp.concat (strings[j].substring (0, strings[j].length () - 1));
        }
        sorting (strings, pos, size);
    }

    // Redimensiona um vetor de strings
    private static String[] resizeStr (String[] words, int N) {
        String[] newWords = new String[2 * N];

        for (int i = 0; i < N; ++i)
            newWords[i] = words[i];

        return newWords;
    }    

    // Redimensiona um vetor de inteiros
    private static int[] resizeInt (int[] nums, int N) {
        int[] newNums = new int[2 * N];

        for (int i = 0; i < N; ++i)
            newNums[i] = nums[i];

        return newNums;
    }    

    // Modulo principal
    public static void main(String[] args) {
        In in = new In(args[0]);
        IndexSET<String> words = new IndexSET<String> ();
        
        words = readText (words, in);

        Graph G = createGraph (words);

        while (!StdIn.isEmpty()) {
            String from = StdIn.readString();
            String to   = StdIn.readString();
            connect (G, words, from, to);
        }
    }
}
