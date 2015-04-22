package fr.unice.polytech.mediamanager.model;

import java.util.ArrayList;

/**
 * Interface pour la gestion des medias.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public interface IManager {
    /**
     * Permet de recuperer tous les films de la base de donnees.
     *
     * @return les films contenus dans la base de donnees.
     */
    public ArrayList<Film> getAllFilms();
   
    /**
     * Permet de recuperer tous les genres de film de la base de donnees.
     *
     * @return les genres de films contenus dans la base de donnees.
     */
    public ArrayList<Genre> getAllGenres();

    /**
     * Permet de recuperer la liste des acteurs de la base de donnees.
     *
     * @return les acteurs contenus dans la base de donnees.
     */
    public ArrayList<Actor> getAllActors();

    /**
     * Permet de recuperer la liste des realisateurs de la base de donnees.
     *
     * @return les realisateurs contenus dans la base de donnees.
     */
    public ArrayList<Director> getAllDirectors();

    /**
     * Permet de recuperer la liste des nationalites de la base de donnees.
     * (La liste n'est pas exhaustive)
     *
     * @return les nationalites contenus dans la base de donnees.
     */
    public ArrayList<Nationality> getAllNationalities();

    /**
     * Permet de rechercher des films par leur titre.
     * (L'espace est le separateur pris par defaut. Cette methode renvoie
     * tous les films dont le titre contient au moins un mot du titre passe en parametre)
     *
     * @param title le titre du film recherche
     * @return une liste de films correspondant a la recherche.
     */
    public ArrayList<Film> searchByTitle(String title);

    /**
     * Permet de rechercher les films par genre.
     *
     * @param genre genre des films recherches
     * @return une liste de films correspondant a la recherche.
     */
    public ArrayList<Film> searchByGenre(Genre genre);

    /**
     * Permet de rechercher les films par acteur.
     *
     * @param actor acteur des films recherches
     * @return une liste de films correspondant a la recherche
     */
    public ArrayList<Film> searchByActor(Actor actor);

    /**
     * Permet de rechercher les films par realisateur.
     *
     * @param director realisateur des films recherches
     * @return une liste de films correspondant a la recherche.
     */
    public ArrayList<Film> searchByDirector(Director director);

    /**
     * Permet de rajouter un film a la base de donnees.
     *
     * @param film film a ajouter
     * @return true si le film a bien ete rajoute, false sinon
     */
    public boolean addFilm(Film film);

    /**
     * Permet de supprimer un film de la base de donnees.
     *
     * @param film film a supprimer
     * @return true si le film a bien ete supprime, false sinon.
     */
    public boolean deleteFilm(Film film);

    /**
     * Permet de mettre a jour un film.
     *
     * @param film le film a mettre a jour
     * @return true si le film a bien ete mis a jour, false sinon.
     */
    public boolean updateFilm(Film film);

}
