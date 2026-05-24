import java.io.*;
import java.util.*;

public class Main {

    /*
     * ============================================================
     * Classe Edge (Aresta)
     * ============================================================
     * Representa uma aresta do grafo com:
     * - u: vértice de origem
     * - v: vértice de destino
     * - w: peso da aresta
     *
     * Implementa Comparable para permitir ordenação por peso,
     * necessária para o algoritmo de Kruskal.
     */
    static class Edge implements Comparable<Edge> {
        int u, v;
        int w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.w, other.w);
        }
    }

    /*
     * ============================================================
     * Classe Union-Find (Disjoint Set Union - DSU)
     * ============================================================
     * Estrutura usada para controlar componentes conectados.
     *
     * Operações principais:
     * - find(x): encontra o representante do conjunto de x
     * - union(a, b): une os conjuntos de a e b
     *
     * Otimizações:
     * - Path Compression
     * - Union by Rank
     *
     * Complexidade praticamente constante: O(α(n))
     */
    static class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];

            // Inicialmente, cada vértice é seu próprio pai
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        // Encontra o representante do conjunto (com compressão de caminho)
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        // Une dois conjuntos; retorna false se já estavam conectados
        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            // Já estão no mesmo conjunto → formaría ciclo
            if (rootA == rootB) return false;

            // União por rank (mantém a árvore balanceada)
            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }

            return true;
        }
    }

    /*
     * ============================================================
     * Função Principal
     * ============================================================
     * Estratégia:
     * - Ler múltiplos casos de teste
     * - Aplicar Kruskal
     * - Armazenar arestas que formam ciclo (heavy edges)
     *
     * Insight:
     * Arestas rejeitadas pelo Kruskal são exatamente as
     * arestas mais pesadas em algum ciclo.
     */
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while (true) {

            // Lê n (vértices) e m (arestas)
            line = br.readLine();
            if (line == null) break;

            String[] parts = line.trim().split(" ");
            int n = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);

            // Condição de parada
            if (n == 0 && m == 0) break;

            List<Edge> edges = new ArrayList<>();

            // Leitura das arestas
            for (int i = 0; i < m; i++) {
                String[] e = br.readLine().split(" ");
                int u = Integer.parseInt(e[0]);
                int v = Integer.parseInt(e[1]);
                int w = Integer.parseInt(e[2]);

                edges.add(new Edge(u, v, w));
            }

            // Passo 1: ordenar arestas por peso (Kruskal)
            Collections.sort(edges);

            // Inicializa Union-Find
            UnionFind uf = new UnionFind(n);

            // Lista para armazenar as "heavy cycle edges"
            List<Integer> heavyEdges = new ArrayList<>();

            /*
             * Passo 2: processar arestas
             *
             * - Se unir → faz parte da MST
             * - Se NÃO unir → forma ciclo → é heavy edge
             */
            for (Edge edge : edges) {
                if (!uf.union(edge.u, edge.v)) {
                    heavyEdges.add(edge.w);
                }
            }

            // Saída
            if (heavyEdges.isEmpty()) {
                // Não há ciclos
                System.out.println("forest");
            } else {
                // Ordena os pesos para saída
                Collections.sort(heavyEdges);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < heavyEdges.size(); i++) {
                    if (i > 0) sb.append(" ");
                    sb.append(heavyEdges.get(i));
                }

                System.out.println(sb.toString());
            }
        }
    }
}