# UVA 11747 - Heavy Cycle Edges

## 🔗 Link do problema
https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2847

## 👥 Integrantes do grupo
- Arthur Alves
- Lucas Magalhães

## 💻 Linguagem utilizada
Java

---

## ▶️ Como executar a solução

### Compilar:
javac Main.java

### Executar:
java Main < input.txt

---

## 🧠 Explicação da modelagem

O problema é modelado como um **grafo não direcionado e ponderado**, onde:

- Os vértices representam os nós do grafo
- As arestas representam conexões entre pares de vértices, com um peso associado

O objetivo é identificar todas as arestas que são a **mais pesada dentro de algum ciclo** do grafo.

---

## ⚙️ Algoritmo utilizado

Foi utilizado o algoritmo de **Kruskal** para construção da Árvore Geradora Mínima (MST), juntamente com a estrutura **Union-Find (DSU)**.

A estratégia é:

- Ordenar as arestas em ordem crescente de peso
- Para cada aresta:
  - Se conecta componentes diferentes → adiciona à MST
  - Se conecta vértices já conectados → forma ciclo

As arestas que formam ciclo durante o Kruskal são exatamente aquelas que são **as mais pesadas em algum ciclo**, e por isso são armazenadas como resposta.

---

## 📈 Análise de complexidade

- Ordenação das arestas: O(m log m)
- Operações de Union-Find: O(m α(n)) (quase constante)

Complexidade total:
O(m log m)

---

## 📷 Evidência de submissão

A evidência de submissão com resultado **Accepted** está disponível em:

evidencias/accepted.pdf