# Matrix Library

A simple Java library for working with matrices, vectors, and common linear algebra operations.

[Português](#português)

---

## Contents

* [Matrix](#matrix)

  * [Creating a Matrix](#creating-a-matrix)
  * [Getting and Setting Elements](#getting-and-setting-elements)
  * [Getting Matrix Dimensions](#getting-matrix-dimensions)
  * [Copying a Matrix](#copying-a-matrix)
  * [Displaying a Matrix](#displaying-a-matrix)
* [Vector](#vector)

  * [Creating a Vector](#creating-a-vector)
  * [Getting and Setting Elements](#getting-and-setting-vector-elements)
  * [Getting the Dimension](#getting-the-dimension)
  * [Displaying a Vector](#displaying-a-vector)
* [LinearAlgebra](#linearalgebra)

  * [Transpose](#transpose)
  * [Sum](#sum)
  * [Scalar Multiplication](#scalar-multiplication)
  * [Element-wise Multiplication](#element-wise-multiplication)
  * [Matrix Multiplication](#matrix-multiplication)
  * [Gaussian Elimination](#gaussian-elimination)
  * [Solving Linear Systems](#solving-linear-systems)
* [Indexing](#indexing)
* [Error Handling](#error-handling)

---

# Matrix

The `Matrix` class represents a matrix of `double` values.

Matrices can be created from an existing two-dimensional array or by specifying their dimensions. The class also provides methods for accessing and modifying individual elements.

## Creating a Matrix

A matrix can be created from a two-dimensional `double` array:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
});
```

The array must represent a rectangular matrix. Every row must contain the same number of elements.

A matrix can also be created by specifying its number of rows and columns:

```java
Matrix A = new Matrix(3, 3);
```

Elements are initialized to `0.0`.

### Copying a Matrix

A matrix can be copied using the copy constructor:

```java
Matrix B = new Matrix(A);
```

`B` is an independent copy of `A`, so modifying one does not modify the other.

---

## Getting and Setting Elements

Matrix elements are accessed using `get()`:

```java
double value = A.get(2, 3);
```

This retrieves the element at **row 2, column 3**.

Elements can be modified using `set()`:

```java
A.set(2, 3, 10);
```

This changes the element at row 2, column 3 to `10`.

### Important: Matrix indexing starts at 1

Unlike Java arrays, matrix indices in this library start at **1**.

```java
A.get(1, 1); // first element
A.get(2, 3); // second row, third column
```

---

## Getting Matrix Dimensions

Use `getRows()` and `getColumns()` to retrieve the dimensions of a matrix:

```java
int rows = A.getRows();
int columns = A.getColumns();
```

For a `3 × 4` matrix:

```java
A.getRows();    // 3
A.getColumns(); // 4
```

---

## Displaying a Matrix

`Matrix` overrides Java's `toString()` method, so matrices can be printed directly:

```java
System.out.println(A);
```

The output is formatted with two decimal places and aligned columns.

For example:

```text
 1.00   2.00   3.00
 4.00   5.00   6.00
 7.00   8.00   9.00
```

---

# Vector

The `Vector` class represents a vector of `double` values.

Vectors in this library are represented as **column vectors**.

## Creating a Vector

Create a vector by passing its elements as a one-dimensional `double` array:

```java
Vector v = new Vector(new double[] {
    1,
    2,
    3
});
```

This represents:

```text
1
2
3
```

---

## Getting and Setting Vector Elements

Use `get()` to retrieve an element:

```java
double value = v.get(2);
```

This returns the second element of the vector.

Use `set()` to modify an element:

```java
v.set(2, 10);
```

### Vector indexing also starts at 1

```java
v.get(1); // first element
v.get(2); // second element
v.get(3); // third element
```

---

## Getting the Dimension

Use `dimension()` to get the number of elements in a vector:

```java
int dimension = v.dimension();
```

For a vector containing three elements:

```java
v.dimension(); // 3
```

---

## Displaying a Vector

Vectors can be printed directly:

```java
System.out.println(v);
```

Since vectors are represented internally as column matrices, they are displayed vertically:

```text
1.00
2.00
3.00
```

---

# LinearAlgebra

The `LinearAlgebra` class contains the mathematical operations provided by the library.

Create an instance before using its methods:

```java
LinearAlgebra la = new LinearAlgebra();
```

---

## Transpose

`transpose()` returns the transpose of a matrix.

```java
Matrix A = new Matrix(new double[][] {
    {1, 2, 3},
    {4, 5, 6}
});

Matrix B = la.transpose(A);
```

The result is:

```text
1  4
2  5
3  6
```

The dimensions change from `2 × 3` to `3 × 2`.

---

## Sum

### Matrix + Matrix

Two matrices can be added using `sum()`:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Matrix B = new Matrix(new double[][] {
    {5, 6},
    {7, 8}
});

Matrix C = la.sum(A, B);
```

Result:

```text
 6.00   8.00
10.00  12.00
```

The matrices must have the same dimensions.

### Vector + Vector

Vectors can also be added:

```java
Vector a = new Vector(new double[] {1, 2, 3});
Vector b = new Vector(new double[] {4, 5, 6});

Vector c = la.sum(a, b);
```

Result:

```text
5
7
9
```

The vectors must have the same dimension.

---

## Scalar Multiplication

A matrix or vector can be multiplied by a scalar using `times()`.

### Matrix

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Matrix B = la.times(2, A);
```

Result:

```text
2   4
6   8
```

### Vector

```java
Vector v = new Vector(new double[] {
    1,
    2,
    3
});

Vector result = la.times(2, v);
```

Result:

```text
2
4
6
```

---

## Element-wise Multiplication

`times()` can also perform element-wise multiplication.

Unlike regular matrix multiplication, each element is multiplied by the element at the same position.

### Matrix

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Matrix B = new Matrix(new double[][] {
    {5, 6},
    {7, 8}
});

Matrix C = la.times(A, B);
```

Result:

```text
 5.00  12.00
21.00  32.00
```

The matrices must have the same dimensions.

### Vector

```java
Vector a = new Vector(new double[] {
    1,
    2,
    3
});

Vector b = new Vector(new double[] {
    4,
    5,
    6
});

Vector c = la.times(a, b);
```

Result:

```text
 4
10
18
```

The vectors must have the same dimension.

---

## Matrix Multiplication

`dot()` performs regular matrix multiplication.

For matrices `A` and `B`, the number of columns in `A` must equal the number of rows in `B`.

```java
Matrix A = new Matrix(new double[][] {
    {1, 2, 3},
    {4, 5, 6}
});

Matrix B = new Matrix(new double[][] {
    {5, 8},
    {3, 7},
    {2, 9}
});

Matrix C = la.dot(A, B);
```

The result is:

```text
 17.00  49.00
47.00  123.00
```

The dimensions follow the usual matrix multiplication rule:

```text
(m × n) · (n × p) = (m × p)
```

### Matrix × Vector

A matrix can also be multiplied by a vector:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Vector v = new Vector(new double[] {
    5,
    6
});

Matrix result = la.dot(A, v);
```

The vector is treated as a column matrix.

---

## Gaussian Elimination

`gauss()` transforms a matrix into **row-echelon form** using Gaussian elimination.

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 1},
    {2, 2, 3},
    {1, 2, 3}
});

Matrix result = la.gauss(A);
```

The returned matrix is in row-echelon form.

The original matrix is not modified.

Gaussian elimination can also be used on augmented matrices when working with systems of linear equations.

---

## Solving Linear Systems

`solve()` determines whether a linear system is:

* impossible,
* possible but indeterminate, or
* possible and determinate.

The system must be provided as an **augmented matrix**, with the final column containing the constants.

For example:

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 1, 6},
    {2, -1, 1, 3},
    {1, 2, -1, 2}
});

la.solve(A);
```

This represents:

```text
x + y + z = 6
2x - y + z = 3
x + 2y - z = 2
```

The solution is:

```text
1
2
3
```

### Possible but indeterminate

A system with infinitely many solutions is reported as indeterminate:

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 2},
    {2, 2, 4}
});

la.solve(A);
```

Output:

```text
Linear system is possible but indeterminate.
```

### Impossible

A system with no solution is reported as impossible:

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 2},
    {2, 2, 5}
});

la.solve(A);
```

Output:

```text
Linear system is impossible.
```

---

# Indexing

One of the most important things to remember when using this library is that **matrix and vector indices start at 1, not 0**.

This differs from normal Java array indexing.

For a matrix:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});
```

the elements are:

```text
        column
          1    2
       ┌────┬────┐
row 1  │ 1  │ 2  │
       ├────┼────┤
row 2  │ 3  │ 4  │
       └────┴────┘
```

Therefore:

```java
A.get(1, 1); // 1
A.get(1, 2); // 2
A.get(2, 1); // 3
A.get(2, 2); // 4
```

---

# Error Handling

The library checks several invalid operations and throws exceptions when they occur.

### Invalid matrix dimensions

Matrices must be rectangular when created from a two-dimensional array.

```java
new Matrix(new double[][] {
    {1, 2},
    {3, 4, 5}
});
```

This throws an `IllegalArgumentException`.

### Invalid matrix position

Trying to access a matrix outside its valid range throws an `IllegalArgumentException`:

```java
A.get(0, 1);
A.set(4, 1, 10);
```

### Invalid vector index

Vector indices must be greater than zero.

### Incompatible operations

Operations such as matrix addition and element-wise multiplication require compatible dimensions.

Matrix multiplication requires the number of columns in the first matrix to equal the number of rows in the second matrix.

---

# Português

[Voltar ao topo](#matrix-library)

## Conteúdo

* [Matrix](#matrix-1)

  * [Criando uma Matrix](#criando-uma-matrix)
  * [Obtendo e Alterando Elementos](#obtendo-e-alterando-elementos)
  * [Obtendo as Dimensões](#obtendo-as-dimensões)
  * [Copiando uma Matrix](#copiando-uma-matrix)
  * [Exibindo uma Matrix](#exibindo-uma-matrix)
* [Vector](#vector-1)

  * [Criando um Vector](#criando-um-vector)
  * [Obtendo e Alterando Elementos](#obtendo-e-alterando-elementos-de-um-vector)
  * [Obtendo a Dimensão](#obtendo-a-dimensão)
  * [Exibindo um Vector](#exibindo-um-vector)
* [LinearAlgebra](#linearalgebra-1)

  * [Transposta](#transposta)
  * [Soma](#soma)
  * [Multiplicação por Escalar](#multiplicação-por-escalar)
  * [Multiplicação Elemento a Elemento](#multiplicação-elemento-a-elemento)
  * [Multiplicação de Matrizes](#multiplicação-de-matrizes)
  * [Eliminação de Gauss](#eliminação-de-gauss)
  * [Resolução de Sistemas Lineares](#resolução-de-sistemas-lineares)
* [Indexação](#indexação)
* [Tratamento de Erros](#tratamento-de-erros)

---

# Matrix

A classe `Matrix` representa uma matriz de valores `double`.

As matrizes podem ser criadas a partir de um array bidimensional ou especificando o número de linhas e colunas. A classe também fornece métodos para acessar e modificar elementos individuais.

## Criando uma Matrix

Uma matriz pode ser criada a partir de um array bidimensional de `double`:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
});
```

O array deve representar uma matriz retangular. Todas as linhas devem possuir a mesma quantidade de elementos.

Também é possível criar uma matriz especificando o número de linhas e colunas:

```java
Matrix A = new Matrix(3, 3);
```

Os elementos são inicializados com `0.0`.

### Copiando uma Matrix

Uma matriz pode ser copiada utilizando o construtor de cópia:

```java
Matrix B = new Matrix(A);
```

`B` é uma cópia independente de `A`, portanto modificar uma não modifica a outra.

---

## Obtendo e Alterando Elementos

Os elementos de uma matriz podem ser acessados utilizando `get()`:

```java
double value = A.get(2, 3);
```

Isso retorna o elemento localizado na **linha 2, coluna 3**.

Para modificar um elemento, utilize `set()`:

```java
A.set(2, 3, 10);
```

Isso altera o elemento da linha 2, coluna 3 para `10`.

### Importante: a indexação começa em 1

Diferentemente dos arrays Java, os índices das matrizes nesta biblioteca começam em **1**.

```java
A.get(1, 1); // primeiro elemento
A.get(2, 3); // segunda linha, terceira coluna
```

---

## Obtendo as Dimensões

Utilize `getRows()` e `getColumns()` para obter as dimensões de uma matriz:

```java
int rows = A.getRows();
int columns = A.getColumns();
```

Para uma matriz `3 × 4`:

```java
A.getRows();    // 3
A.getColumns(); // 4
```

---

## Exibindo uma Matrix

`Matrix` sobrescreve o método `toString()` do Java, portanto matrizes podem ser impressas diretamente:

```java
System.out.println(A);
```

A saída utiliza duas casas decimais e mantém as colunas alinhadas.

Por exemplo:

```text
 1.00   2.00   3.00
 4.00   5.00   6.00
 7.00   8.00   9.00
```

---

# Vector

A classe `Vector` representa um vetor de valores `double`.

Os vetores nesta biblioteca são representados como **vetores coluna**.

## Criando um Vector

Crie um vetor passando seus elementos como um array unidimensional de `double`:

```java
Vector v = new Vector(new double[] {
    1,
    2,
    3
});
```

Isso representa:

```text
1
2
3
```

---

## Obtendo e Alterando Elementos de um Vector

Utilize `get()` para obter um elemento:

```java
double value = v.get(2);
```

Isso retorna o segundo elemento do vetor.

Utilize `set()` para modificar um elemento:

```java
v.set(2, 10);
```

### A indexação de Vector também começa em 1

```java
v.get(1); // primeiro elemento
v.get(2); // segundo elemento
v.get(3); // terceiro elemento
```

---

## Obtendo a Dimensão

Utilize `dimension()` para obter a quantidade de elementos de um vetor:

```java
int dimension = v.dimension();
```

Para um vetor contendo três elementos:

```java
v.dimension(); // 3
```

---

## Exibindo um Vector

Vetores podem ser impressos diretamente:

```java
System.out.println(v);
```

Como os vetores são representados internamente como matrizes coluna, eles são exibidos verticalmente:

```text
1.00
2.00
3.00
```

---

# LinearAlgebra

A classe `LinearAlgebra` contém as operações matemáticas fornecidas pela biblioteca.

Crie uma instância antes de utilizar seus métodos:

```java
LinearAlgebra la = new LinearAlgebra();
```

---

## Transposta

`transpose()` retorna a transposta de uma matriz.

```java
Matrix A = new Matrix(new double[][] {
    {1, 2, 3},
    {4, 5, 6}
});

Matrix B = la.transpose(A);
```

O resultado é:

```text
1  4
2  5
3  6
```

As dimensões mudam de `2 × 3` para `3 × 2`.

---

## Soma

### Matrix + Matrix

Duas matrizes podem ser somadas utilizando `sum()`:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Matrix B = new Matrix(new double[][] {
    {5, 6},
    {7, 8}
});

Matrix C = la.sum(A, B);
```

Resultado:

```text
 6.00   8.00
10.00  12.00
```

As matrizes devem possuir as mesmas dimensões.

### Vector + Vector

Vetores também podem ser somados:

```java
Vector a = new Vector(new double[] {1, 2, 3});
Vector b = new Vector(new double[] {4, 5, 6});

Vector c = la.sum(a, b);
```

Resultado:

```text
5
7
9
```

Os vetores devem possuir a mesma dimensão.

---

## Multiplicação por Escalar

Uma matriz ou vetor pode ser multiplicado por um escalar utilizando `times()`.

### Matrix

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Matrix B = la.times(2, A);
```

Resultado:

```text
2   4
6   8
```

### Vector

```java
Vector v = new Vector(new double[] {
    1,
    2,
    3
});

Vector result = la.times(2, v);
```

Resultado:

```text
2
4
6
```

---

## Multiplicação Elemento a Elemento

`times()` também pode realizar multiplicação elemento a elemento.

Diferentemente da multiplicação matricial normal, cada elemento é multiplicado pelo elemento que ocupa a mesma posição.

### Matrix

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Matrix B = new Matrix(new double[][] {
    {5, 6},
    {7, 8}
});

Matrix C = la.times(A, B);
```

Resultado:

```text
 5.00  12.00
21.00  32.00
```

As matrizes devem possuir as mesmas dimensões.

### Vector

```java
Vector a = new Vector(new double[] {
    1,
    2,
    3
});

Vector b = new Vector(new double[] {
    4,
    5,
    6
});

Vector c = la.times(a, b);
```

Resultado:

```text
 4
10
18
```

Os vetores devem possuir a mesma dimensão.

---

## Multiplicação de Matrizes

`dot()` realiza a multiplicação matricial convencional.

Para as matrizes `A` e `B`, a quantidade de colunas de `A` deve ser igual à quantidade de linhas de `B`.

```java
Matrix A = new Matrix(new double[][] {
    {1, 2, 3},
    {4, 5, 6}
});

Matrix B = new Matrix(new double[][] {
    {5, 8},
    {3, 7},
    {2, 9}
});

Matrix C = la.dot(A, B);
```

O resultado é:

```text
 17.00  49.00
47.00  123.00
```

As dimensões seguem a regra usual da multiplicação matricial:

```text
(m × n) · (n × p) = (m × p)
```

### Matrix × Vector

Uma matriz também pode ser multiplicada por um vetor:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});

Vector v = new Vector(new double[] {
    5,
    6
});

Matrix result = la.dot(A, v);
```

O vetor é tratado como uma matriz coluna.

---

## Eliminação de Gauss

`gauss()` transforma uma matriz em **forma escalonada** utilizando eliminação de Gauss.

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 1},
    {2, 2, 3},
    {1, 2, 3}
});

Matrix result = la.gauss(A);
```

A matriz retornada estará em forma escalonada.

A matriz original não é modificada.

A eliminação de Gauss também pode ser utilizada em matrizes aumentadas para trabalhar com sistemas de equações lineares.

---

## Resolução de Sistemas Lineares

`solve()` determina se um sistema linear é:

* impossível;
* possível, mas indeterminado; ou
* possível e determinado.

O sistema deve ser fornecido como uma **matriz aumentada**, com a última coluna contendo as constantes.

Por exemplo:

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 1, 6},
    {2, -1, 1, 3},
    {1, 2, -1, 2}
});

la.solve(A);
```

Isso representa:

```text
x + y + z = 6
2x - y + z = 3
x + 2y - z = 2
```

A solução é:

```text
1
2
3
```

### Possível, mas indeterminado

Um sistema com infinitas soluções é reportado como indeterminado:

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 2},
    {2, 2, 4}
});

la.solve(A);
```

Saída:

```text
Linear system is possible but indeterminate.
```

### Impossível

Um sistema sem solução é reportado como impossível:

```java
Matrix A = new Matrix(new double[][] {
    {1, 1, 2},
    {2, 2, 5}
});

la.solve(A);
```

Saída:

```text
Linear system is impossible.
```

---

# Indexação

Uma das coisas mais importantes a lembrar ao utilizar esta biblioteca é que os índices de **matrizes e vetores começam em 1, e não em 0**.

Isso é diferente da indexação normal de arrays em Java.

Para uma matriz:

```java
Matrix A = new Matrix(new double[][] {
    {1, 2},
    {3, 4}
});
```

os elementos são:

```text
        coluna
          1    2
       ┌────┬────┐
linha 1│ 1  │ 2  │
       ├────┼────┤
linha 2│ 3  │ 4  │
       └────┴────┘
```

Portanto:

```java
A.get(1, 1); // 1
A.get(1, 2); // 2
A.get(2, 1); // 3
A.get(2, 2); // 4
```

---

# Tratamento de Erros

A biblioteca verifica diversas operações inválidas e lança exceções quando elas ocorrem.

### Dimensões inválidas

As matrizes criadas a partir de arrays bidimensionais devem ser retangulares.

```java
new Matrix(new double[][] {
    {1, 2},
    {3, 4, 5}
});
```

Isso lança uma `IllegalArgumentException`.

### Posição inválida em uma Matrix

Tentar acessar uma posição fora dos limites da matriz lança uma `IllegalArgumentException`:

```java
A.get(0, 1);
A.set(4, 1, 10);
```

### Índice inválido em um Vector

Os índices de vetores devem ser maiores que zero.

### Operações incompatíveis

Operações como soma de matrizes e multiplicação elemento a elemento exigem dimensões compatíveis.

A multiplicação matricial exige que a quantidade de colunas da primeira matriz seja igual à quantidade de linhas da segunda.
