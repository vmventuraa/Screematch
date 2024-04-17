package br.com.alura.Screenmatch;

import br.com.alura.Screenmatch.model.DadosEpisodios;
import br.com.alura.Screenmatch.model.DadosSerie;
import br.com.alura.Screenmatch.model.DadosTemporada;
import br.com.alura.Screenmatch.service.ConsumoAPI;
import br.com.alura.Screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumoAPI = new ConsumoAPI();

        var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=breaking+bad&apikey=406855ed");


//        System.out.println(json);
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

//
        System.out.println();
//        System.out.println(dados);

        json = consumoAPI.obterDados("https://www.omdbapi.com/?t=breaking+bad&season=5&episode=14&apikey=406855ed");


        DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);


//        System.out.println(dadosEpisodios);

        List<DadosTemporada> temporadas = new ArrayList<>();
       for (int i =1; i <= dados.totalTemporadas();i++){
           json = consumoAPI.obterDados("https://www.omdbapi.com/?t=peaky+blinders&season="+i+"&apikey=406855ed");
           DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
            temporadas.add(dadosTemporada);


       }
       temporadas.forEach(System.out::println);



    }
}
