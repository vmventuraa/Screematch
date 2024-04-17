package br.com.alura.Screenmatch;

import br.com.alura.Screenmatch.model.DadosEpisodios;
import br.com.alura.Screenmatch.model.DadosSerie;
import br.com.alura.Screenmatch.service.ConsumoAPI;
import br.com.alura.Screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumoAPI = new ConsumoAPI();

        var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=breaking+bad&apikey=406855ed");


        System.out.println(json);
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);


        System.out.println();
        System.out.println(dados);

        json = consumoAPI.obterDados("https://www.omdbapi.com/?t=breaking+bad&season=5&episode=14&apikey=406855ed");


        DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);


        System.out.println(dadosEpisodios);




    }
}
