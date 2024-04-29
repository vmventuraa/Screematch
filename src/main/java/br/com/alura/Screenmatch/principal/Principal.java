package br.com.alura.Screenmatch.principal;

import br.com.alura.Screenmatch.model.DadosEpisodios;
import br.com.alura.Screenmatch.model.DadosSerie;
import br.com.alura.Screenmatch.model.DadosTemporada;
import br.com.alura.Screenmatch.model.Episodio;
import br.com.alura.Screenmatch.service.ConsumoAPI;
import br.com.alura.Screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {
    //Constantes do link de consumo da API
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIK_KEY = "&apikey=406855ed";

    private Scanner leitura = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();


    public void exibeMenu() {


        System.out.println("Informe o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIK_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        List<DadosEpisodios> episodiosTemporada;
        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + APIK_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);


        }
        temporadas.forEach(System.out::println);

        for (int i = 0; i < dados.totalTemporadas(); i++) {
            episodiosTemporada = temporadas.get(i).episodios();
            for (DadosEpisodios dadosEpisodios : episodiosTemporada) {
                System.out.println(dadosEpisodios.titulo());
            }

        }

//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());


//        System.out.println("\nTop 10 - Episódios");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primeiro filtro(N/A) " + e) )
//                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
//                .peek(e-> System.out.println("Ordenação " + e) )
//                .limit(10)
//                .peek(e-> System.out.println("Limitador " + e) )
//                .map(e->e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento " + e) )
//                .forEach(System.out::println);



        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());


        episodios.forEach(System.out::println);

        System.out.println("Informe um episódio para busca");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
            System.out.println("Titulo: " + episodioBuscado.get().getTitulo());
        }else {
            System.out.println("Episodio não encontrado");
        }


//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getData() != null && e.getData().isAfter(dataBusca))
//                .forEach(e -> {
//                    System.out.println("Temporada: " + e.getTemporada() +
//                            " Episódio:" + e.getTitulo() +
//                            " Data de Lançamento: " + e.getData().format(formatter));
//                })
//        ;





    }
}
