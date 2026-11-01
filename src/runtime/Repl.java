package runtime;
import matrix.LinearAlgebra;
import matrix.Matrix;
import matrix.Vector;
import transformations.Transformations;

import java.util.Locale;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

/**
 * javac -d out src/matrix/*.java src/transformations/*.java src/runtime/*.java
 * java -cp out runtime.Repl
 */
public class Repl {

    private static final LinearAlgebra la = new LinearAlgebra();
    private static final Transformations tf = new Transformations();
    private static final Scanner in = new Scanner(System.in).useLocale(Locale.US);
    private static ObjectStore os = new ObjectStore();

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
                    case 6 -> fazerVetor();
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
        System.out.println("6  Criar vetor na memória");
        System.out.println("0  Sair");
        System.out.println("=================================================");
    }

    private static String selecionarVetor() {
        while (true) {
            Map<String, Vector> vetores = os.getVectors();
            System.out.println("--------- VETOR DA OPERAÇÃO ---------");
            System.out.println("1  Criar novo vetor");
            if (!vetores.isEmpty()) {
                System.out.println("2  Usar vetor existente");
            }
            System.out.println("0  Voltar");
            int op = lerInt("Opção: ");
            System.out.println();

            switch (op) {
                case 1 -> {
                    return fazerVetor();
                }
                case 2 -> {
                    if (!vetores.isEmpty()) {
                        return escolherVetorExistente();
                    }
                    System.out.println("Opção inválida.");
                }
                case 0 -> {
                    return null;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static String escolherVetorExistente() {
        Map<String, Vector> vetores = os.getVectors();
        while (true) {
            System.out.println("--------- VETORES EM MEMÓRIA ---------");
            for (String nome : new TreeMap<>(vetores).keySet()) {
                mostrar(nome, vetores.get(nome));
                System.out.println();
            }

            String nome = lerString("Nome do vetor (enter para voltar): ").trim();
            if (nome.isEmpty()) {
                return null;
            }
            if (vetores.containsKey(nome)) {
                return nome;
            }
            System.out.println("Este vetor não existe. Tente novamente.");
        }
    }

    private static String fazerVetor() {
        int dim = lerDimensao2D3D();
        String nome = lerNomeNovoVetor();
        Vector v = lerVetorDim("v (" + dim + "D)", dim);
        os.add(nome, v);
        mostrar("Vetor '" + nome + "' criado", os.getVector(nome));
        return nome;
    }

    private static void finalizarOperacao(String descricao, Vector resultado, String nomeOrigem) {
        mostrar(descricao, resultado);
        salvarResultado(resultado, nomeOrigem);
    }

    private static void salvarResultado(Vector resultado, String nomeOrigem) {
        while (true) {
            System.out.println("--------- SALVAR RESULTADO ---------");
            System.out.println("1  Salvar como novo vetor");
            System.out.println("2  Sobrescrever '" + nomeOrigem + "'");
            System.out.println("3  Não salvar");
            int op = lerInt("Opção: ");
            System.out.println();

            switch (op) {
                case 1 -> {
                    String nome = lerNomeNovoVetor();
                    os.add(nome, resultado);
                    System.out.println("Vetor '" + nome + "' salvo.");
                    return;
                }
                case 2 -> {
                    os.replaceVector(nomeOrigem, resultado);
                    System.out.println("Vetor '" + nomeOrigem + "' atualizado.");
                    return;
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ------------------- AV2: Transformações Lineares -------------------

    private static void fazerTranslacao() {
        String nome = selecionarVetor();
        if (nome == null) {
            return;
        }

        Vector v = os.getVector(nome);
        Vector resultado;
        String descricao;

        if (v.dimension() == 2) {
            double dx = lerDouble("dx: ");
            double dy = lerDouble("dy: ");
            resultado = tf.translate2D(v, dx, dy);
            descricao = "translate2D(" + nome + ", " + dx + ", " + dy + ")";
        } else if (v.dimension() == 3) {
            double dx = lerDouble("dx: ");
            double dy = lerDouble("dy: ");
            double dz = lerDouble("dz: ");
            resultado = tf.translate3D(v, dx, dy, dz);
            descricao = "translate3D(" + nome + ", " + dx + ", " + dy + ", " + dz + ")";
        } else {
            throw new IllegalArgumentException("A translação só é definida para vetores 2D ou 3D.");
        }

        finalizarOperacao(descricao, resultado, nome);
    }

    private static void fazerRotacao() {
        String nome = selecionarVetor();
        if (nome == null) {
            return;
        }

        Vector v = os.getVector(nome);
        Vector resultado;
        String descricao;

        if (v.dimension() == 2) {
            double angulo = lerDouble("Ângulo (em graus): ");
            resultado = tf.rotation2D(v, angulo);
            descricao = "rotation2D(" + nome + ", " + angulo + ")";
        } else if (v.dimension() == 3) {
            String eixo = lerEixo("Eixo (X, Y ou Z): ", true);
            double angulo = lerDouble("Ângulo (em graus): ");
            switch (eixo) {
                case "X" -> {
                    resultado = tf.rotation3DX(v, angulo);
                    descricao = "rotation3DX(" + nome + ", " + angulo + ")";
                }
                case "Y" -> {
                    resultado = tf.rotation3DY(v, angulo);
                    descricao = "rotation3DY(" + nome + ", " + angulo + ")";
                }
                case "Z" -> {
                    resultado = tf.rotation3DZ(v, angulo);
                    descricao = "rotation3DZ(" + nome + ", " + angulo + ")";
                }
                default -> throw new IllegalStateException("Eixo inválido.");
            }
        } else {
            throw new IllegalArgumentException("A rotação só é definida para vetores 2D ou 3D.");
        }

        finalizarOperacao(descricao, resultado, nome);
    }

    private static void fazerReflexao() {
        String nome = selecionarVetor();
        if (nome == null) {
            return;
        }

        Vector v = os.getVector(nome);
        Vector resultado;
        String descricao;

        if (v.dimension() == 2) {
            String eixo = lerEixo("Eixo (X ou Y): ", false);
            switch (eixo) {
                case "X" -> {
                    resultado = tf.reflection2DX(v);
                    descricao = "reflection2DX(" + nome + ")";
                }
                case "Y" -> {
                    resultado = tf.reflection2DY(v);
                    descricao = "reflection2DY(" + nome + ")";
                }
                default -> throw new IllegalStateException("Eixo inválido.");
            }
        } else if (v.dimension() == 3) {
            String eixo = lerEixo("Eixo (X, Y ou Z): ", true);
            switch (eixo) {
                case "X" -> {
                    resultado = tf.reflection3DX(v);
                    descricao = "reflection3DX(" + nome + ")";
                }
                case "Y" -> {
                    resultado = tf.reflection3DY(v);
                    descricao = "reflection3DY(" + nome + ")";
                }
                case "Z" -> {
                    resultado = tf.reflection3DZ(v);
                    descricao = "reflection3DZ(" + nome + ")";
                }
                default -> throw new IllegalStateException("Eixo inválido.");
            }
        } else {
            throw new IllegalArgumentException("A reflexão só é definida para vetores 2D ou 3D.");
        }

        finalizarOperacao(descricao, resultado, nome);
    }

    private static void fazerProjecao() {
        String nome = selecionarVetor();
        if (nome == null) {
            return;
        }

        Vector v = os.getVector(nome);
        Vector resultado;
        String descricao;

        if (v.dimension() == 2) {
            String eixo = lerEixo("Eixo (X ou Y): ", false);
            switch (eixo) {
                case "X" -> {
                    resultado = tf.projection2DX(v);
                    descricao = "projection2DX(" + nome + ")";
                }
                case "Y" -> {
                    resultado = tf.projection2DY(v);
                    descricao = "projection2DY(" + nome + ")";
                }
                default -> throw new IllegalStateException("Eixo inválido.");
            }
        } else if (v.dimension() == 3) {
            String eixo = lerEixo("Eixo (X, Y ou Z): ", true);
            switch (eixo) {
                case "X" -> {
                    resultado = tf.projection3DX(v);
                    descricao = "projection3DX(" + nome + ")";
                }
                case "Y" -> {
                    resultado = tf.projection3DY(v);
                    descricao = "projection3DY(" + nome + ")";
                }
                case "Z" -> {
                    resultado = tf.projection3DZ(v);
                    descricao = "projection3DZ(" + nome + ")";
                }
                default -> throw new IllegalStateException("Eixo inválido.");
            }
        } else {
            throw new IllegalArgumentException("A projeção só é definida para vetores 2D ou 3D.");
        }

        finalizarOperacao(descricao, resultado, nome);
    }

    private static void fazerCisalhamento() {
        String nome = selecionarVetor();
        if (nome == null) {
            return;
        }

        Vector v = os.getVector(nome);
        if (v.dimension() != 2) {
            throw new IllegalArgumentException("O cisalhamento só é definido para vetores 2D.");
        }

        double kx = lerDouble("kx: ");
        double ky = lerDouble("ky: ");
        Vector resultado = tf.shearing(v, kx, ky);
        finalizarOperacao("shearing(" + nome + ", " + kx + ", " + ky + ")", resultado, nome);
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

    private static String lerNomeNovoVetor() {
        while (true) {
            String nome = lerString("Nome: ").trim();
            if (nome.isEmpty()) {
                System.out.println("O nome não pode ser vazio.");
                continue;
            }
            if (nome.matches(".*\\s.*")) {
                System.out.println("O nome não pode conter espaços.");
                continue;
            }
            if (os.getVectors().containsKey(nome)) {
                System.out.println("Já existe um vetor com esse nome.");
                continue;
            }
            return nome;
        }
    }

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

    private static String lerString(String prompt) {
        System.out.print(prompt);
        String str = in.nextLine();

        return str;
    }

    private static void mostrar(String nome, Vector v) {
        System.out.println(nome + ":");
        System.out.print(v);
    }
}
