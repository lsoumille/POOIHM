package fr.unice.polytech.mediamanager.control;

import fr.unice.polytech.mediamanager.model.*;
import java.util.ArrayList;
import java.util.Date;

public class TestManagerBase {

    public static void main(String[] args) {
        Manager manager = new Manager();
        ArrayList<Film> films = manager.getAllFilms();

        for(Film film: films) {
            System.out.println(film);
            System.out.println();
        }

        /*ArrayList<Genre> genres = manager.getAllGenres();

        for(Genre genre: genres) {
            System.out.println(genre.getLabelEn());
            System.out.println();
        }*/

        /*ArrayList<Actor> actors = manager.getAllActors();

        for(Actor actor: actors) {
            System.out.println(actor);
            System.out.println();
        }*/

        /*ArrayList<Director> directors = manager.getAllDirectors();

        for(Director director: directors) {
            System.out.println(director);
            System.out.println();
        }*/

        /*ArrayList<Director> directors = manager.getAllDirectors();

        for(Director director: directors) {
            System.out.println(director);
            System.out.println();
        }*/

        /*ArrayList<Nationality> nationalities = manager.getAllNationalities();

        for(Nationality nationality: nationalities) {
            System.out.println(nationality);
            System.out.println();
        }*/

        /*ArrayList<Film> films = manager.searchByTitle("arche Wars");//TODO les majuscules

        for(Film film: films) {
            System.out.println(film);
            System.out.println("######################################");
            System.out.println();
        }*/

        /*ArrayList<Film> films = manager.searchByGenre(Genre.scifi);

        for(Film film: films) {
            System.out.println(film);
            System.out.println("######################################");
            System.out.println();
        }*/

        /*ArrayList<Film> films = manager.searchByActor(new Actor("http://www.polytech.unice.fr/mediamanager/film.rdfs-instances#people4"
, "Harrison", "Ford", Nationality.american, new Date(1942, 0, 0), new Date(0, 0, 0), "resources/photos/ford.jpg"));

        for(Film film: films) {
            System.out.println(film);
            System.out.println("######################################");
            System.out.println();
        }*/

        /*ArrayList<Film> films = manager.searchByDirector(new Director("http://www.polytech.unice.fr/mediamanager/film.rdfs-instances#people2"
, "George", "Lucas", Nationality.american, new Date(1943, 0, 0), new Date(0, 0, 0), "resources/photos/lucas.jpg"));

        for(Film film: films) {
            System.out.println(film);
            System.out.println("######################################");
            System.out.println();
        }*/

        /*ArrayList<Film> films = manager.searchByTitle("espoir");
        Film SWFilm = films.get(0);
        Film newFilm = new Film("", "Star_Wars", SWFilm.getDirector(), SWFilm.getActors(), SWFilm.getGenres(), SWFilm.getRuntime(), SWFilm.getPoster(), SWFilm.getSynopsis());

        if(manager.addFilm(newFilm)) {

            ArrayList<Film> films2 = manager.getAllFilms();

            System.out.println("NB FILMS ==> " + films2.size());

            for(Film film: films2) {
                System.out.println(film.getId() + "\n" + film.getTitle());
                System.out.println();
            }
        } else {
            System.out.println("NOP");
        }*/

        /*ArrayList<Film> films = manager.searchByTitle("espoir");
        Film SWFilm = films.get(0);
        Film newFilm = new Film("http://www.polytech.unice.fr/mediamanager/film.rdfs-instances#film4", "Star_Wars", SWFilm.getDirector(), SWFilm.getActors(), SWFilm.getGenres(), SWFilm.getRuntime(), SWFilm.getPoster(), SWFilm.getSynopsis());

        if(manager.deleteFilm(newFilm)) {

            ArrayList<Film> films2 = manager.getAllFilms();

            System.out.println("NB FILMS ==> " + films2.size());

            for(Film film: films2) {
                System.out.println(film.getId() + "\n" + film.getTitle());
                System.out.println();
            }
        } else {
            System.out.println("NOP");
        }*/

        /*ArrayList<Film> films = manager.searchByTitle("espoir");
        Film SWFilm = films.get(0);
        SWFilm.setTitle("Star Wars espoir Test");

        if(manager.updateFilm(SWFilm)) {

            ArrayList<Film> films2 = manager.getAllFilms();

            System.out.println("NB FILMS ==> " + films2.size());

            for(Film film: films2) {
                System.out.println(film.getId() + "\n" + film.getTitle());
                System.out.println();
            }
        } else {
            System.out.println("NOP");
        }*/
    }

}
