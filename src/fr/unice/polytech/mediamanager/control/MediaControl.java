package fr.unice.polytech.mediamanager.control;

import fr.unice.polytech.mediamanager.model.*;
import fr.unice.polytech.mediamanager.view.AddFrame;
import fr.unice.polytech.mediamanager.view.DetailFilm;
import fr.unice.polytech.mediamanager.view.ManagerFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : Lucas SOUMILLE
 * @date : 18/03/15
 */
public class MediaControl {

    private Manager manager;

    private ManagerFrame frame;

    public MediaControl(){
        manager = new Manager();
    }

    public void start(){
        frame = new ManagerFrame(this);
    }

    /*
    Create an add form
     */
    public void createAddFrame(){
        AddFrame add = new AddFrame(this);
    }

    /*
    Create an editing form
     */
    public void createDetailFilm(String name){
        DetailFilm detail = new DetailFilm(this, getByTitle(name).get(0));
    }

    /*
    return all the movies in the database
     */
    public List<Film> getAllMovies(){
        return manager.getAllFilms();
    }

    /*
    return all the existing genres
     */
    public List<Genre> getAllGenres(){
        return manager.getAllGenres();
    }

    /*
    return all the nationalities
     */
    public List<Nationality> getAllNationalities(){
        return manager.getAllNationalities();
    }

    /*
    return the film corresponding at the title
     */
    public List<Film> getByTitle(String title){
        List<Film> allMovies = getAllMovies();
        List<Film> tmp = new ArrayList<Film>();
        for(Film f : allMovies){
            if(f.getTitle().toUpperCase().equals(title.toUpperCase())){
                tmp.add(f);
            }
        }
        return tmp;
    }

    /*
    return films corresponding at the genre
     */
    public List<Film> getByGenre(String genre){
        Genre[] allGenres = Genre.values();
        Genre searchGenre = null;
        for(Genre gen : allGenres){
            if(genre.toUpperCase().equals(gen.getLabelEn().toUpperCase())
                    || genre.toUpperCase().equals(gen.getLabelFr().toUpperCase())){
                searchGenre = gen;
            }
        }
        if(searchGenre != null){
            return manager.searchByGenre(searchGenre);
        }
        return new ArrayList<Film>();
    }

    /*
    return actor's movies
     */
    public List<Film> getByActor(String actor){
        List<Film> allMovies = manager.getAllFilms();
        List<Actor> allActors = manager.getAllActors();
        Actor searchActor = null;
        for(Actor act : allActors){
            if(actor.toUpperCase().equals(act.getLastname().toUpperCase())
                    || actor.toUpperCase().equals(act.getFirstname().toUpperCase())){
                searchActor = act;
            }
        }
        if(searchActor != null){
            return manager.searchByActor(searchActor);
        }
        return new ArrayList<Film>();
    }

    /*
    return director's movies
     */
    public List<Film> getByDirector(String director){
        List<Film> allMovies = manager.getAllFilms();
        List<Director> allDirectors = manager.getAllDirectors();
        Director searchDir = null;
        for(Director dir : allDirectors){
            if(director.toUpperCase().equals(dir.getLastname().toUpperCase())
                    || director.toUpperCase().equals(dir.getFirstname().toUpperCase())){
                searchDir = dir;
            }
        }
        if(searchDir != null){
            return manager.searchByDirector(searchDir);
        }
        return new ArrayList<Film>();
    }

    /*
    return the list corresponding at the search
    if word is empty return all the movies
     */
    public void research(String categ, String word){
        List<Film> filmRes = new ArrayList<Film>();
        if(categ.equals("nom")){
            filmRes = getByTitle(word);
        } else if (categ.equals("actor")){
            filmRes = getByActor(word);
        } else if (categ.equals("director")){
            filmRes = getByDirector(word);
        } else if(categ.equals("genre")){
            filmRes = getByGenre(word);
        }
        if(word == null || word.equals("")){
            filmRes = getAllMovies();
        }
        reloadModel(filmRes);
    }

    /*
    add a new film in the database
     */
    public void addFilm(HashMap<String,?> info){
        String id = "";
        String title = info.get("TITLE").toString();
        Director dir = getDirectorByName(info.get("DIRECTOR").toString());
        Actor act = getActorByName(info.get("ACTOR").toString());
        Genre genre = getGenreByName(info.get("GENRE").toString());
        int runtime = (Integer)info.get("RUNTIME");
        String poster = info.get("POSTER").toString();
        String synopsis = info.get("SYNOPSIS").toString();
        ArrayList<Genre> genres = new ArrayList<Genre>();
        genres.add(genre);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(act);

        Film newFilm = new Film(id, title, dir, actors, genres, runtime, poster, synopsis);
        manager.addFilm(newFilm);

        reloadModel(getAllMovies());
    }


    /*
    return actor corresponding at the firstname or the lastname
     */
    private Actor getActorByName(String name){
        List<Actor> allActors = manager.getAllActors();
        for(Actor act : allActors){
            if(name.toUpperCase().equals(act.getLastname().toUpperCase())
                    || name.toUpperCase().equals(act.getFirstname().toUpperCase())){
                return act;
            }
        }
        return allActors.get(0);
    }

    /*
    return director corresponding at the first name or the lastname
     */
    private Director getDirectorByName(String name){
        List<Director> allDirector = manager.getAllDirectors();
        for(Director dir : allDirector){
            if(name.toUpperCase().equals(dir.getLastname().toUpperCase())
                    || name.toUpperCase().equals(dir.getFirstname().toUpperCase())){
                return dir;
            }
        }
        return allDirector.get(0);
    }

    /*
    return the genre corresponding at the name
     */
    private Genre getGenreByName(String name){
        List<Genre> allGenres = manager.getAllGenres();
        System.out.println(name);
        for (Genre g : allGenres){
            if(name.toUpperCase().equals(g.getLabelEn().toUpperCase())){
                return g;
            }
        }
        return null;
    }

    /*
    refresh the list which contains the movies
     */
    public void reloadModel(List<Film> res){
        frame.getResultat().removeAllResults();
        for(Film film : res){
            frame.getResultat().addMovieElement(film.getTitle());
        }
        frame.repaint();
        frame.pack();
    }

    /*
    delete the movie passed in parameter
     */
    public void deleteMovie(String name){
        Film deleteFilm = getByTitle(name).get(0);
        manager.deleteFilm(deleteFilm);
        reloadModel(getAllMovies());
    }

    /*
    edit the movie with the info
     */
    public void editMovie(Film film, HashMap<String,?> info){
        film.setTitle(info.get("TITLE").toString());
        film.setSynopsis(info.get("SYNOPSIS").toString());
        Actor act = getActorByName(info.get("ACTOR").toString());
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(act);
        film.setActors(actors);
        manager.updateFilm(film);
        reloadModel(getAllMovies());
    }
}
