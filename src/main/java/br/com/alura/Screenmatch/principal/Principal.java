package br.com.alura.Screenmatch.principal;

import br.com.alura.Screenmatch.model.DadosSerie;
import br.com.alura.Screenmatch.model.DadosTemporada;
import br.com.alura.Screenmatch.service.ConsumoAPI;
import br.com.alura.Screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Principal {
    private Scanner leitura = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    ConverteDados conversor = new ConverteDados();

    private String ENDERECO = "https://www.omdbapi.com/?t=";
    private String APIK_KEY = "&apikey=406855ed";


    public void exibeMenu() {


        System.out.println("Informe o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIK_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i =1; i <= dados.totalTemporadas();i++){
            json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season="+i+APIK_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
            temporadas.add(dadosTemporada);


        }
        temporadas.forEach(System.out::println);


    }
}
