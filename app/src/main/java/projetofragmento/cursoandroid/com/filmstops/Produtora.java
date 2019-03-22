package projetofragmento.cursoandroid.com.filmstops;

import java.util.ArrayList;

public class Produtora extends Object {
    private String name;
    private ArrayList<Filme> filmes;

    public Produtora() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(ArrayList<Filme> filmes) {
        this.filmes = filmes;
    }
}
