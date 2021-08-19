package trabalho_grau_b;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    static BufferedReader in;

    static AVLTree<Long> CPFs = new AVLTree<>();
    static AVLTree<String> nomes = new AVLTree<>();
    static AVLTree<Date> datas = new AVLTree<>();

    public static void main(String[] args) throws IOException, ParseException {

        in = new BufferedReader(new FileReader("./res/info.txt"));

        // Read info.txt and populate the tree
        try {
            String line;
            while ((line = in.readLine()) != null) {
                String[] props = line.split(";");
                Pessoa pessoa = new Pessoa(Long.parseLong(props[0]), props[1], props[2], props[3], props[4]);
                CPFs.root = CPFs.insert(pessoa.getCPF(), pessoa);
                nomes.root = nomes.insert(pessoa.getNomeCompleto(), pessoa);
                Date date = new SimpleDateFormat("dd/MM/y").parse(pessoa.getDataNascimento());
                datas.root = datas.insert(date, pessoa);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        in = new BufferedReader(new InputStreamReader(System.in));

        // Menu
        do {
            System.out.println("1 => Consultar CPFs");
            System.out.println("2 => Consultar nomes");
            System.out.println("3 => Consultar por intervalo de data de nascimento");
            System.out.println("4 => Encerrar");
            System.out.print("Opção: ");
            int opcao = Integer.parseInt(in.readLine());

            switch (opcao) {
                case 1 -> {
                    System.out.print("\nCPF: ");
                    long cpf = Long.parseLong(in.readLine());
                    Pessoa pessoa = CPFs.search(cpf);
                    if (pessoa != null) {
                        System.out.println("\nPessoa encontrada...");
                        System.out.println(pessoa);
                    } else {
                        System.out.println("\nPessoa não encontrada...\n");
                    }
                }
                case 2 -> {
                    System.out.print("\nNome: ");
                    String segmento = in.readLine();
                    nomes.searchByName(segmento);
                }
                case 3 -> {
                    System.out.print("Data de início: ");
                    Date inicio = new SimpleDateFormat("dd/MM/y").parse(in.readLine());
                    System.out.print("Data de término: ");
                    Date fim = new SimpleDateFormat("dd/MM/y").parse(in.readLine());
                    System.out.println();
                    datas.searchByDate(inicio, fim);
                }
                case 4 -> {
                    System.out.println("Encerrando o sistema...");
                    System.exit(0); // Termina a execução do programa
                }
            }
        } while (true);
    }
}
