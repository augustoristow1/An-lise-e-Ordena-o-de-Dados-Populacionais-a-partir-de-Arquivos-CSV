import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class file {

    public static void main(String[] args) {
        String arquivoCSV = "C:\\Users\\gugur\\Documents\\Trabalho Java\\lista de paises.csv";
        String linha = "";
        String divisor = ",";

        List<String> termosIgnorados = Arrays.asList(
                "Africa Eastern and Southern", "Africa Western and Central", "Arab World",
                "Caribbean small states", "Central Europe and the Baltics", "East Asia & Pacific (excluding high income)",
                "Early-demographic dividend", "East Asia & Pacific", "Europe & Central Asia (excluding high income)",
                "Europe & Central Asia", "Euro area", "European Union", "Fragile and conflict affected situations",
                "High income", "Heavily indebted poor countries (HIPC)", "IBRD only", "IDA & IBRD total",
                "IDA total", "IDA blend", "IDA only", "Not classified", "Latin America & Caribbean (excluding high income)",
                "Least developed countries: UN classification", "Low income", "Lower middle income", "Low & middle income",
                "Late-demographic dividend", "Middle East & North Africa", "Middle income", "North America",
                "OECD members", "Other small states", "Pre-demographic dividend", "Post-demographic dividend",
                "Pacific island small states", "Small states", "Sub-Saharan Africa (excluding high income)",
                "Sub-Saharan Africa", "Upper middle income", "World", "East Asia & Pacific (IDA & IBRD countries)",
                "Europe & Central Asia (IDA & IBRD countries)", "Latin America & the Caribbean (IDA & IBRD countries)",
                "Middle East & North Africa (IDA & IBRD countries)", "South Asia (IDA & IBRD)",
                "Sub-Saharan Africa (IDA & IBRD countries)"
        );

        List<Pais> paises = new ArrayList<>();

        System.out.println("Iniciando a leitura do arquivo CSV...");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {
            String cabecalho = br.readLine();
            System.out.println("Cabeçalho: " + cabecalho);

            while ((linha = br.readLine()) != null) {
                System.out.println("Lendo linha: " + linha);

                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (dados.length < 5) {
                    System.out.println("Linha ignorada por não ter colunas suficientes: " + linha);
                    continue;
                }

                String nomePais = dados[0].replace("\"", "");

                if (termosIgnorados.contains(nomePais)) {
                    System.out.println("Linha ignorada por conter um termo irrelevante: " + nomePais);
                    continue;
                }

                int populacao1960;

                try {
                    System.out.println("Processando país: " + nomePais + " com população de 1960: " + dados[3]);
                    populacao1960 = Integer.parseInt(dados[3].replace("\"", "").trim());
                } catch (NumberFormatException e) {
                    System.out.println("População inválida para o país: " + nomePais + " - dado: " + dados[3]);
                    continue;
                }

                System.out.println("Adicionando " + nomePais + " à lista com população de " + populacao1960);
                paises.add(new Pais(nomePais, populacao1960));
            }

        } catch (IOException e) {
            System.out.println("Erro ao tentar ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Leitura do arquivo concluída.");
        System.out.println("Número total de países lidos: " + paises.size());

        if (paises.isEmpty()) {
            System.out.println("Nenhum país encontrado com dados válidos.");
        } else {
            System.out.println("Iniciando a ordenação dos países pela população...");
            insertionSort(paises);

            Pais maisPopuloso = paises.get(paises.size() - 1);
            System.out.println("País mais populoso em 1960: " + maisPopuloso.getNome());
            System.out.println("População: " + maisPopuloso.getPopulacao());
        }
    }

    public static void insertionSort(List<Pais> paises) {
        for (int i = 1; i < paises.size(); i++) {
            Pais chave = paises.get(i);
            int j = i - 1;

            while (j >= 0 && paises.get(j).getPopulacao() > chave.getPopulacao()) {
                paises.set(j + 1, paises.get(j));
                j = j - 1;
            }
            paises.set(j + 1, chave);
        }
        System.out.println("Ordenação concluída.");
    }

}

class Pais {
    private String nome;
    private int populacao;

    public Pais(String nome, int populacao) {
        this.nome = nome;
        this.populacao = populacao;
    }

    public String getNome() {
        return nome;
    }

    public int getPopulacao() {
        return populacao;
    }
}
