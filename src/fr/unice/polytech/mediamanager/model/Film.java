package fr.unice.polytech.mediamanager.model;

import fr.inria.acacia.corese.api.*;
import fr.inria.acacia.corese.exceptions.EngineException;
import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
 * Classe representant un film.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public class Film {

    private String id;
    private String title;
    private Director director;
    private ArrayList<Actor> actors;
    private ArrayList<Genre> genres;
    private int runtime;
    private String poster;
    private String synopsis;

    /**
     * Contructeur.
     *
     * @param id identifiant du film.
     * @param title titre du film.
     * @param director realisateur du film.
     * @param actors acteurs du film.
     * @param genres genres associes au film.
     * @param runtime duree du film.
     * @param poster URI de l'affiche du film (URI relative, les URI pointant vers des pages Web ne sont pas gerees).
     * @param synopsis synopsis du film.
     */
    public Film(String id, String title, Director director, ArrayList<Actor> actors, ArrayList<Genre> genres, int runtime, String poster, String synopsis) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.genres = genres;
        this.runtime = runtime;
        this.poster = poster;
        this.synopsis = synopsis;
    }

    /**
     * Permet de recuperer l'identifiant du film.
     *
     * @return l'identifiant du film.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Permet de definir l'identifiant du film.
     *
     * @param id identifiant du film.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Permet de recuperer le titre du film.
     *
     * @return le titre du film.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Permet de definir le titre du film.
     *
     * @param title titre du film.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Permet de recuperer le realisateur du film.
     *
     * @return le realisateur du film.
     */
    public Director getDirector() {
        return this.director;
    }

    /**
     * Permet de definir le realisateur du film.
     *
     * @param director realisateur du film.
     */
    public void setDirector(Director director) {
        this.director = director;
    }

    /**
     * Permet de recuperer les acteurs du film.
     *
     * @return les acteurs du film.
     */
    public ArrayList<Actor> getActors() {
        return this.actors;
    }

    /**
     * Permet de definir les acteurs du film.
     *
     * @param actors acteurs du film.
     */
    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Permet de recuperer les genres du film.
     *
     * @return les genres du film.
     */
    public ArrayList<Genre> getGenres() {
        return this.genres;
    }

    /**
     * Permet de definir les genres du film.
     *
     * @param genres genres du film.
     */
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Permet de recuperer la duree du film.
     *
     * @return la duree du film.
     */
    public int getRuntime() {
        return this.runtime;
    }

    /**
     * Permet de definir la duree du film.
     *
     * @param runtime duree du film.
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Permet de recuperer l'URI de l'affiche du film.
     *
     * @return l'URI de l'affiche du film.
     */
    public String getPoster() {
        return this.poster;
    }

    /**
     * Permet de definir l'URI de l'affiche du film.
     *
     * @param poster URI de l'affiche du film (URI relative, les URI pointant vers des pages Web ne sont pas gerees).
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * Permet de recuperer le synopsis du film.
     *
     * @return le synopsis du film.
     */
    public String getSynopsis() {
        return this.synopsis;
    }

    /**
     * Permet de definir le synopsis du film.
     *
     * @param synopsis synopsis du film.
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public static ArrayList<Film> getAll(IEngine engine) {
        ArrayList<Film> films = new ArrayList<Film>();
        try {
			IResults resFilms = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Film> }");
            for (Enumeration<IResult> enFilms = resFilms.getResults(); enFilms.hasMoreElements();) {
	  			IResult rFilm = enFilms.nextElement();
                IResultValue[] id = rFilm.getResultValues("?id");

                films.add(Film.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return films;
    }

    /**
     * Permet de recuperer un film par son identifiant.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param id identifiant du film.
     * @return si trouve, retourne le film, sinon retourne null.
     */
    public static Film getById(IEngine engine, String id) {
        Film film = null;
        try {
            IResults resFilm = engine.SPARQLQuery("select ?title ?director ?runtime ?poster ?synopsis where {	" +
                        "<" + id + "> <" + Constants.FILM_NS + "#hasTitle> ?title ." +
                        "<" + id + "> <" + Constants.FILM_NS + "#hasDirector> ?director ." +
                        "<" + id + "> <" + Constants.FILM_NS + "#hasRuntime> ?runtime ." +
                        "<" + id + "> <" + Constants.FILM_NS + "#hasPoster> ?poster ." +
                        "<" + id + "> <" + Constants.FILM_NS + "#hasSynopsis> ?synopsis }");

            Enumeration<IResult> enFilm = resFilm.getResults();
            IResult rFilm = enFilm.nextElement();

            IResultValue[] title = rFilm.getResultValues("?title");
            IResultValue[] director = rFilm.getResultValues("?director");
            IResultValue[] runtime = rFilm.getResultValues("?runtime");
            IResultValue[] poster = rFilm.getResultValues("?poster");
            IResultValue[] synopsis = rFilm.getResultValues("?synopsis");

            Director filmDirector = Director.getById(engine, director[0].getStringValue());
            ArrayList<Actor> filmActors = new ArrayList<Actor>();

            IResults resActors = engine.SPARQLQuery("select ?actor where {	" +
                    "<" + id + "> <" + Constants.FILM_NS + "#hasActor> ?actor }");

            for (Enumeration<IResult> enActors = resActors.getResults(); enActors.hasMoreElements();) {
	  			IResult rActor = enActors.nextElement();
                IResultValue[] actorId = rActor.getResultValues("?actor");

                Actor filmActor = Actor.getById(engine, actorId[0].getStringValue());

                filmActors.add(filmActor);
            }

            ArrayList<Genre> filmGenres = new ArrayList<Genre>();
            IResults resGenres = engine.SPARQLQuery("select ?genre where {	" +
                    "<" + id + "> <" + Constants.FILM_NS + "#hasGenre> ?genre}");
            for (Enumeration<IResult> enGenres = resGenres.getResults(); enGenres.hasMoreElements();) {
	  			IResult rGenre = enGenres.nextElement();
                IResultValue[] genreId = rGenre.getResultValues("?genre");

                filmGenres.add(Genre.valueOf(genreId[0].getStringValue().split(Constants.FILM_INSTANCES_NS + "#")[1]));
            }
            
            film = new Film(id, Constants.fromXmlString(title[0].getStringValue()), filmDirector, filmActors, filmGenres, Integer.parseInt(runtime[0].getStringValue()), poster[0].getStringValue(), Constants.fromXmlString(synopsis[0].getStringValue()));
        } catch(EngineException e) {
			e.printStackTrace();
		}

        return film;
    }

    /**
     * Permet de rechercher des film par leur titre.
     * Attention, la casse est prise en compte.
     * Cette methode prend l'espace comme separateur et renvoi tous les films dont
     * le titre contient au un mot du titre passe en parametre.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param title titre du film recherche.
     * @return une liste de film correspondant au titre.
     */
    public static ArrayList<Film> searchByTitle(IEngine engine, String title) {
        ArrayList<Film> films = new ArrayList<Film>();
        String newTitle = Constants.toXmlString(title);
        String[] titlePieces = newTitle.split(" ");

        String regExpQuery = "";

        for(String text: titlePieces) {
            regExpQuery += "regex(?title, '" + text + "') || ";
        }
        regExpQuery = regExpQuery.substring(0, regExpQuery.length() - 4);

        try {
			IResults resFilms = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Film> ." +
                    "?id <" + Constants.FILM_NS + "#hasTitle> ?title ." +
                    "FILTER(" + regExpQuery + ") }");

            for (Enumeration<IResult> enFilms = resFilms.getResults(); enFilms.hasMoreElements();) {
	  			IResult rFilm = enFilms.nextElement();
                IResultValue[] id = rFilm.getResultValues("?id");

                films.add(Film.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return films;
    }

    /**
     * Permet de rechercher des film par leur genre.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param genre genre des films recherches.
     * @return une liste de film correspondant au genre.
     */
    public static ArrayList<Film> searchByGenre(IEngine engine, Genre genre) {
        ArrayList<Film> films = new ArrayList<Film>();

        try {
			IResults resFilms = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Film> ." +
                    "?id <" + Constants.FILM_NS + "#hasGenre> <" + Constants.FILM_INSTANCES_NS + "#" + genre.name() + ">}");

            for (Enumeration<IResult> enFilms = resFilms.getResults(); enFilms.hasMoreElements();) {
	  			IResult rFilm = enFilms.nextElement();
                IResultValue[] id = rFilm.getResultValues("?id");

                films.add(Film.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return films;
    }

    /**
     * Permet de rechercher des film par leur acteur.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param actor acteur des films recherches.
     * @return une liste de film correspondant a l'acteur.
     */
    public static ArrayList<Film> searchByActor(IEngine engine, Actor actor) {
        ArrayList<Film> films = new ArrayList<Film>();

        try {
			IResults resFilms = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Film> ." +
                    "?id <" + Constants.FILM_NS + "#hasActor> <" + actor.getId() + ">}");

            for (Enumeration<IResult> enFilms = resFilms.getResults(); enFilms.hasMoreElements();) {
	  			IResult rFilm = enFilms.nextElement();
                IResultValue[] id = rFilm.getResultValues("?id");

                films.add(Film.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return films;
    }

    /**
     * Permet de rechercher des film par leur realisateur.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param director realisateur des films recherches.
     * @return une liste de film correspondant au realisateur.
     */
    public static ArrayList<Film> searchByDirector(IEngine engine, Director director) {
        ArrayList<Film> films = new ArrayList<Film>();

        try {
			IResults resFilms = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Film> ." +
                    "?id <" + Constants.FILM_NS + "#hasDirector> <" + director.getId() + ">}");

            for (Enumeration<IResult> enFilms = resFilms.getResults(); enFilms.hasMoreElements();) {
	  			IResult rFilm = enFilms.nextElement();
                IResultValue[] id = rFilm.getResultValues("?id");

                films.add(Film.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return films;
    }

    /**
     * Permet de rajouter un film dans la base de donnees.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param film film a ajouter a la base de donnees.
     * @param force booleen qui permet de forcer l'ajout a la base.
     * @return si le film a bien ete ajoute, retourne true sinon retourne false.
     */
    public static boolean add(IEngine engine, Film film, boolean force) {
        if(film.getId().equals("") || force) {
            try {
                /*IResults resFilms = engine.SPARQLQuery("select ?title where {	" +
                        "?id rdf:type <" + Constants.FILM_NS + "#Film> ." +
                        "?id <" + Constants.FILM_NS + "#hasTitle> ?title ." +
                        "FILTER(regex(?title, '" + Constants.toXmlString(film.title) + "'))}");

                Enumeration<IResult> enFilms = resFilms.getResults();
                if(enFilms.hasMoreElements()) {
                    return false;
                } else {*/
                    int filmNextId = -1;
                    if(!force) {
                        SAXBuilder sxb = new SAXBuilder();
                        Document config = sxb.build(new File("resources/config/config.xml"));
                        Element configRoot = config.getRootElement();
                        Element filmNextIdElement = configRoot.getChild("filmNextId");

                        filmNextId = Integer.parseInt(filmNextIdElement.getText());

                        filmNextIdElement.setText(Integer.toString(filmNextId + 1));

                        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
                        sortie.output(config, new FileOutputStream("resources/config/config.xml"));
                    } else {
                        filmNextId = Integer.parseInt(film.getId().split(Constants.FILM_INSTANCES_NS + "#film")[1]);
                    }


                    SAXBuilder sxb2 = new SAXBuilder();
                    Document rdfFilm = sxb2.build(new File("resources/annotations/film.rdf"));

                    Element root = rdfFilm.getRootElement();

                    Element filmElement = new Element("Film");
                    root.addContent(filmElement);

                    filmElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                    filmElement.setAttribute("ID", "film" + filmNextId, root.getNamespace("rdf"));

                    film.setId(Constants.FILM_INSTANCES_NS + "#film" + filmNextId);

                    Element titleElement = new Element("hasTitle");
                        titleElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        titleElement.addContent(new CDATA(Constants.toXmlString(film.title)));
                        filmElement.addContent(titleElement);

                    //Director Actors Genres
                    Director.add(engine, film.director);

                    Element directorElement = new Element("hasDirector");
                        directorElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        directorElement.setAttribute("resource", film.director.getId(), root.getNamespace("rdf"));
                        filmElement.addContent(directorElement);

                    for(Actor actor: film.actors) {
                        Actor.add(engine, actor);
                        Element actorElement = new Element("hasActor");
                            actorElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                            actorElement.setAttribute("resource", actor.getId(), root.getNamespace("rdf"));
                            filmElement.addContent(actorElement);
                    }

                    for(Genre genre: film.genres) {
                        Element genreElement = new Element("hasGenre");
                            genreElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                            genreElement.setAttribute("resource", Constants.FILM_INSTANCES_NS + "#" + genre.name(), root.getNamespace("rdf"));
                            filmElement.addContent(genreElement);
                    }

                    Element runtimeElement = new Element("hasRuntime");
                        runtimeElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        runtimeElement.setText(Integer.toString(film.runtime));
                        filmElement.addContent(runtimeElement);
                    
                    Element posterElement = new Element("hasPoster");
                        posterElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        posterElement.addContent(new CDATA(film.poster));
                        filmElement.addContent(posterElement);

                    Element synopsisElement = new Element("hasSynopsis");
                        synopsisElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        synopsisElement.addContent(new CDATA(Constants.toXmlString(film.synopsis)));
                        filmElement.addContent(synopsisElement);

                    XMLOutputter sortie2 = new XMLOutputter(Format.getPrettyFormat());
                    sortie2.output(rdfFilm, new FileOutputStream("resources/annotations/film.rdf"));

                    /*Element titleElement = new Element("hasTitle");
                    Element titleElement = new Element("hasTitle");
                    Element titleElement = new Element("hasTitle");*/


                    return true;
               // }
            } catch (JDOMException jde) {
                jde.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Permet de supprimer un film de la base de donnees.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param film film a supprimer de la base de donnees
     * @return si le film a ete supprime, retourne true, sinon retourne false.
     */
    public static boolean delete(IEngine engine, Film film) {
        try {
            String filmId = film.getId().split(Constants.FILM_INSTANCES_NS + "#")[1];

            SAXBuilder sxb2 = new SAXBuilder();
            Document rdfFilm = sxb2.build(new File("resources/annotations/film.rdf"));

            Element root = rdfFilm.getRootElement();


            Element filmElement = null;
            List rootElements = root.getContent();
            for(int i = 0; i < rootElements.size(); i++) {
                Content rootContent = root.getContent(i);
                if(rootContent.getClass().getName().equals(Element.class.getName())) {
                    Element rootElem = (Element) rootContent;

                    Attribute attId = rootElem.getAttribute("ID", root.getNamespace("rdf"));
                    //System.out.println(attId.getValue() + " -- " + film.getId());
                    if(attId.getValue().equals(filmId)) {
                        filmElement = rootElem;
                    }
                }
            }

            root.removeContent(filmElement);

            XMLOutputter sortie2 = new XMLOutputter(Format.getPrettyFormat());
            sortie2.output(rdfFilm, new FileOutputStream("resources/annotations/film.rdf"));

            return true;
        } catch (JDOMException jde) {
            jde.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    /**
     * Permet de mettre a jour les informations d'un film.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param film film a mettre a jour.
     * @return si le film a ete mis a jour, retourne true, sinon retourne false.
     */
    public static boolean update(IEngine engine, Film film) {
        return delete(engine, film) && add(engine, film, true);
    }

    /**
     * Permet de recuperer une description du film.
     *
     * @return la description du film.
     */
    public String toString() {
        String returnString = "";

        returnString += "Id: ";
        returnString += this.id;
        returnString += "\n";

        returnString += "Title: ";
        returnString += this.title;
        returnString += "\n";

        returnString += "Director: \n";
        returnString += "------------------";
        returnString += this.director.toString();
        returnString += "------------------";
        returnString += "\n";

        returnString += "Actors: \n";
        for(Actor actor: this.actors) {
            returnString += "------------------";
            returnString += actor.toString();
            returnString += "------------------";
            returnString += "\n";
        }

        returnString += "Genres: \n";
        for(Genre genre: this.genres) {
            returnString += "------------------";
            returnString += genre.getLabelEn();
            returnString += "------------------";
            returnString += "\n";
        }

        returnString += "Runtime: ";
        returnString += this.runtime + " minutes";
        returnString += "\n";

        returnString += "Poster: ";
        returnString += this.poster;
        returnString += "\n";

        returnString += "Sysnopsis: ";
        returnString += this.synopsis;
        returnString += "\n";

        return returnString;
    }
}
