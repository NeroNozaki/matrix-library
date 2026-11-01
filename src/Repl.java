import matrix.LinearAlgebra;
import matrix.Matrix;
import matrix.Vector;
import transformations.Transformations;

import java.util.Locale;
import java.util.Scanner;

/**
 * javac -d out src/matrix/*.java src/transformations/*.java src/Main.java
 * java -cp out Main
 */
public class Repl {

    private static final LinearAlgebra la = new LinearAlgebra();
    private static final Transformations tf = new Transformations();
    private static final Scanner in = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        System.out.println("Transformações Lineares (AV2) — Teste no Terminal");
        System.out.println("Índice começa em 1. Decimais com ponto (ex: 1.5). Ângulos em graus (ex: 90).");

        while (true) {
            menu();
            int op = lerInt("Opção: ");
            System.out.println();
            try {
                switch (op) {
                    case 1 -> fazerTranslacao();
                    case 2 -> fazerRotacao();
                    case 3 -> fazerReflexao();
                    case 4 -> fazerProjecao();
                    case 5 -> fazerCisalhamento();
                    // case 6 -> menuAv1();
                    case 0 -> {
                        System.out.println("Encerrado.");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void menu() {
        System.out.println("========= TRANSFORMAÇÕES LINEARES (AV2) =========");
        System.out.println("1  Translação   (translate2D / translate3D)");
        System.out.println("2  Rotação      (rotation2D / rotation3DX, Y, Z)");
        System.out.println("3  Reflexão     (reflection2DX, Y / reflection3DX, Y, Z)");
        System.out.println("4  Projeção     (projection2DX, Y / projection3DX, Y, Z)");
        System.out.println("5  Cisalhamento (shearing 2D)");
        System.out.println("0  Sair");
        System.out.println("=================================================");
    }

    // ------------------- AV2: Transformações Lineares -------------------

    private static void fazerTranslacao() {
        int dim = lerDimensao2D3D();
        if (dim == 2) {
            Vector v = lerVetorDim("v (2D)", 2);
            double dx = lerDouble("dx: ");
            double dy = lerDouble("dy: ");
            mostrar("translate2D(v, " + dx + ", " + dy + ")", tf.translate2D(v, dx, dy));
        } else {
            Vector v = lerVetorDim("v (3D)", 3);
            double dx = lerDouble("dx: ");
            double dy = lerDouble("dy: ");
            double dz = lerDouble("dz: ");
            mostrar("translate3D(v, " + dx + ", " + dy + ", " + dz + ")", tf.translate3D(v, dx, dy, dz));
        }
    }

    private static void fazerRotacao() {
        int dim = lerDimensao2D3D();
        if (dim == 2) {
            Vector v = lerVetorDim("v (2D)", 2);
            double angle = lerDouble("Ângulo (em graus): ");
            mostrar("rotation2D(v, " + angle + ")", tf.rotation2D(v, angle));
        } else {
            Vector v = lerVetorDim("v (3D)", 3);
            String eixo = lerEixo("Eixo (X, Y ou Z): ", true);
            double angle = lerDouble("Ângulo (em graus): ");
            switch (eixo) {
                case "X" -> mostrar("rotation3DX(v, " + angle + ")", tf.rotation3DX(v, angle));
                case "Y" -> mostrar("rotation3DY(v, " + angle + ")", tf.rotation3DY(v, angle));
                case "Z" -> mostrar("rotation3DZ(v, " + angle + ")", tf.rotation3DZ(v, angle));
            }
        }
    }

    private static void fazerReflexao() {
        int dim = lerDimensao2D3D();
        if (dim == 2) {
            Vector v = lerVetorDim("v (2D)", 2);
            String eixo = lerEixo("Eixo (X ou Y): ", false);
            switch (eixo) {
                case "X" -> mostrar("reflection2DX(v)", tf.reflection2DX(v));
                case "Y" -> mostrar("reflection2DY(v)", tf.reflection2DY(v));
            }
        } else {
            Vector v = lerVetorDim("v (3D)", 3);
            String eixo = lerEixo("Eixo (X, Y ou Z): ", true);
            switch (eixo) {
                case "X" -> mostrar("reflection3DX(v)", tf.reflection3DX(v));
                case "Y" -> mostrar("reflection3DY(v)", tf.reflection3DY(v));
                case "Z" -> mostrar("reflection3DZ(v)", tf.reflection3DZ(v));
            }
        }
    }

    private static void fazerProjecao() {
        int dim = lerDimensao2D3D();
        if (dim == 2) {
            Vector v = lerVetorDim("v (2D)", 2);
            String eixo = lerEixo("Eixo (X ou Y): ", false);
            switch (eixo) {
                case "X" -> mostrar("projection2DX(v)", tf.projection2DX(v));
                case "Y" -> mostrar("projection2DY(v)", tf.projection2DY(v));
            }
        } else {
            Vector v = lerVetorDim("v (3D)", 3);
            String eixo = lerEixo("Eixo (X, Y ou Z): ", true);
            switch (eixo) {
                case "X" -> mostrar("projection3DX(v)", tf.projection3DX(v));
                case "Y" -> mostrar("projection3DY(v)", tf.projection3DY(v));
                case "Z" -> mostrar("projection3DZ(v)", tf.projection3DZ(v));
            }
        }
    }

    private static void fazerCisalhamento() {
        Vector v = lerVetorDim("v (2D)", 2);
        double kx = lerDouble("kx: ");
        double ky = lerDouble("ky: ");
        mostrar("shearing(v, " + kx + ", " + ky + ")", tf.shearing(v, kx, ky));
    }

    /*
    // ------------------- AV1: Operações de Matrizes/Sistemas (Comentado) -------------------

    private static void menuAv1() {
        System.out.println("--------- OPERAÇÕES AV1 ---------");
        System.out.println("1  transpose");
        System.out.println("2  sum");
        System.out.println("3  times  (escalar)");
        System.out.println("4  times  (elemento a elemento)");
        System.out.println("5  dot    (produto de matrizes)");
        System.out.println("6  gauss");
        System.out.println("7  solve  (matriz aumentada)");
        System.out.println("8  get / set");
        System.out.println("0  voltar");
        System.out.println("---------------------------------");
        int op = lerInt("Opção AV1: ");
        System.out.println();
        switch (op) {
            case 1 -> fazerTranspose();
            case 2 -> fazerSum();
            case 3 -> fazerTimesEscalar();
            case 4 -> fazerTimesElemento();
            case 5 -> fazerDot();
            case 6 -> fazerGauss();
            case 7 -> fazerSolve();
            case 8 -> fazerGetSet();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void fazerTranspose() {
        if (perguntarSeVetor()) {
            mostrar("transpose(v)", la.transpose(lerVetor("Vetor")));
        } else {
            mostrar("transpose(A)", la.transpose(lerMatriz("A")));
        }
    }

    private static void fazerSum() {
        if (perguntarSeVetor()) {
            mostrar("sum(u, v)", la.sum(lerVetor("u"), lerVetor("v")));
        } else {
            mostrar("sum(A, B)", la.sum(lerMatriz("A"), lerMatriz("B")));
        }
    }

    private static void fazerTimesEscalar() {
        double c = lerDouble("Escalar: ");
        if (perguntarSeVetor()) {
            mostrar("times(c, v)", la.times(c, lerVetor("v")));
        } else {
            mostrar("times(c, A)", la.times(c, lerMatriz("A")));
        }
    }

    private static void fazerTimesElemento() {
        if (perguntarSeVetor()) {
            mostrar("times(u, v)", la.times(lerVetor("u"), lerVetor("v")));
        } else {
            mostrar("times(A, B)", la.times(lerMatriz("A"), lerMatriz("B")));
        }
    }

    private static void fazerDot() {
        Matrix A = lerMatriz("A");
        if (perguntar("Multiplicar por vetor? (s/n): ")) {
            mostrar("dot(A, v)", la.dot(A, lerVetor("v")));
        } else {
            mostrar("dot(A, B)", la.dot(A, lerMatriz("B")));
        }
    }

    private static void fazerGauss() {
        mostrar("gauss(A)", la.gauss(lerMatriz("A")));
    }

    private static void fazerSolve() {
        System.out.println("Digite a matriz aumentada [A | b]. Última coluna = b.");
        mostrar("solve", la.solve(lerMatriz("aumentada")));
    }

    private static void fazerGetSet() {
        if (perguntarSeVetor()) {
            Vector v = lerVetor("v");
            mostrar("v", v);
            int i = lerInt("Índice i: ");
            if (perguntar("Alterar com set? (s/n): ")) {
                v.set(i, lerDouble("Novo valor: "));
                mostrar("depois do set", v);
            } else {
                System.out.println("get(" + i + ") = " + v.get(i));
            }
        } else {
            Matrix A = lerMatriz("A");
            mostrar("A", A);
            int i = lerInt("Linha i: ");
            int j = lerInt("Coluna j: ");
            if (perguntar("Alterar com set? (s/n): ")) {
                A.set(i, j, lerDouble("Novo valor: "));
                mostrar("depois do set", A);
            } else {
                System.out.println("get(" + i + ", " + j + ") = " + A.get(i, j));
            }
        }
    }
    */

    // ------------------- Helpers de Leitura -------------------

    private static int lerDimensao2D3D() {
        while (true) {
            int dim = lerInt("Dimensão (2 ou 3): ");
            if (dim == 2 || dim == 3) {
                return dim;
            }
            System.out.println("Digite apenas 2 ou 3.");
        }
    }

    private static String lerEixo(String prompt, boolean permiteZ) {
        while (true) {
            System.out.print(prompt);
            String s = in.next().trim().toUpperCase();
            in.nextLine();
            if (s.equals("X") || s.equals("Y") || (permiteZ && s.equals("Z"))) {
                return s;
            }
            System.out.println("Eixo inválido. Tente novamente.");
        }
    }

    private static Vector lerVetorDim(String nome, int dim) {
        System.out.print("Vetor " + nome + " — digite " + dim + " número(s) separados por espaço: ");
        double[] elements = new double[dim];
        for (int i = 0; i < dim; i++) {
            elements[i] = lerProximoDouble();
        }
        in.nextLine();
        return new Vector(elements);
    }

    private static int lerInt(String prompt) {
        System.out.print(prompt);
        while (!in.hasNextInt()) {
            in.next();
            System.out.print(prompt);
        }
        int n = in.nextInt();
        in.nextLine();
        return n;
    }

    private static double lerDouble(String prompt) {
        System.out.print(prompt);
        double n = lerProximoDouble();
        in.nextLine();
        return n;
    }

    private static double lerProximoDouble() {
        while (true) {
            if (!in.hasNext()) {
                throw new IllegalStateException("Entrada encerrada.");
            }
            String token = in.next().replace(",", ".");
            try {
                return Double.parseDouble(token);
            } catch (NumberFormatException e) {
                System.out.print("  número inválido, tente de novo: ");
            }
        }
    }

    private static void mostrar(String nome, Vector v) {
        System.out.println(nome + ":");
        System.out.print(v);
    }
}
