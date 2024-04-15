package br.com.alura.Screenmatch.service;

public interface IConverteDados {

    //metodo a ser implantado para a conversao dos dados vindos do Json, para linguagem Java
    //utiliza o tipo generico notado por maior e menor, pois nao sabemos qual é o tipo de retorno
    <T> T obterDados(String json, Class<T> classe);
}
