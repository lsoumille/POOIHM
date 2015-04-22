package fr.unice.polytech.mediamanager.model;

import fr.inria.acacia.corese.api.*;
import fr.inria.acacia.corese.exceptions.EngineException;
import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
 * Classe representant un realisateur.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public class Director extends People {

    /**
     * Constructeur. Fait appel au constructeur de la classe People.
     *
     * @param id identifiant du realisateur
     * @param firstname prenom du realisateur
     * @param lastname nom du realisateur
     * @param nationality nationalite du realisateur
     * @param birth date de naissance du realisateur
     * @param death date de mort du realisateur
     * @param photo URI vers la photo du realisateur (URI relative, les URI pointant vers des pages Web ne sont pas gerees)
     */
    public Director(String id, String firstname, String lastname, Nationality nationality, Date birth, Date death, String photo) {
        super(id, firstname, lastname, nationality, birth, death, photo);
    }

    /**
     * Permet de recuperer tous les realisateurs de la base de donnees.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @return l'ensemble des realisateurs de la base de donnees sous forme d'une ArrayList<Director>
     */
    public static ArrayList<Director> getAll(IEngine engine) {
        ArrayList<Director> directors = new ArrayList<Director>();
        try {
			IResults resDirectors = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Director> }");
        //<hasActor rdf:resource="#" />
        //<hasGenre rdf:resource="#" />

            for (Enumeration<IResult> enDirectors = resDirectors.getResults(); enDirectors.hasMoreElements();) {
	  			IResult rDirector = enDirectors.nextElement();
                IResultValue[] id = rDirector.getResultValues("?id");

                directors.add(Director.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return directors;
    }

    /**
     * Permet de recuperer le realisateur designe par l'id passe en parametre.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param id identifiant du realisateur
     * @return si trouve, retourne le realisateur, sinon retourne null.
     */
    public static Director getById(IEngine engine, String id) {
        Director returnDirector = null;
        try {
            IResults resDirector = engine.SPARQLQuery("select ?firstname ?lastname ?nationality ?birth ?death ?photo where {	" +
                        "<" + id + "> <" + Constants.FILM_NS + "#firstname> ?firstname . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#lastname> ?lastname . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#nationality> ?nationality . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#birth> ?birth . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#death> ?death . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#photo> ?photo }");

                Enumeration<IResult> enDirector = resDirector.getResults();
                IResult rDirector = enDirector.nextElement();
                IResultValue[] firstnameDirector = rDirector.getResultValues("?firstname");
                IResultValue[] lastnameDirector = rDirector.getResultValues("?lastname");
                IResultValue[] nationalityDirector = rDirector.getResultValues("?nationality");
                IResultValue[] birthDirector = rDirector.getResultValues("?birth");
                IResultValue[] deathDirector = rDirector.getResultValues("?death");
                IResultValue[] photoDirector = rDirector.getResultValues("?photo");

                returnDirector = new Director(id, firstnameDirector[0].getStringValue(),
                        lastnameDirector[0].getStringValue(),
                        Nationality.valueOf(nationalityDirector[0].getStringValue().split(Constants.FILM_INSTANCES_NS + "#")[1]),
                        new Date(Integer.parseInt(birthDirector[0].getStringValue()), 0, 0),
                        new Date(Integer.parseInt(deathDirector[0].getStringValue()), 0, 0),
                        photoDirector[0].getStringValue());
        } catch(EngineException e) {
			e.printStackTrace();
		}
        return returnDirector;

    }

    /**
     * Permet d'ajouter une realisateur a la base de donnee.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param actor le realisateur a ajouter a la base de donnee
     * @return si le realisateur est bien ajoute, retourne true, sinon retourne false (le realisateur passe en parametre a deja un id ou le realisateur existe deja dans la base de donnee.
     */
    public static boolean add(IEngine engine, Director director) {
        if(director.getId().equals("")) {
            try {
                IResults resDirectors = engine.SPARQLQuery("select ?firstname ?lastname where {	" +
                        "?id rdf:type <" + Constants.FILM_NS + "#Director> ." +
                        "?id <" + Constants.FILM_NS + "#firstname> ?firstname ." +
                        "?id <" + Constants.FILM_NS + "#lastname> ?lastname ." +
                        "FILTER(regex(?firstname, '" + director.getFirstname() + "') && regex(?lastname, '" + director.getLastname() + "'))}");

                Enumeration<IResult> enDirectors = resDirectors.getResults();
                if(enDirectors.hasMoreElements()) {
                    return false;
                } else {
                    SAXBuilder sxb = new SAXBuilder();
                    Document config = sxb.build(new File("resources/config/config.xml"));
                    Element configRoot = config.getRootElement();
                    Element peopleNextIdElement = configRoot.getChild("peopleNextId");

                    int peopleNextId = Integer.parseInt(peopleNextIdElement.getText());

                    peopleNextIdElement.setText(Integer.toString(peopleNextId + 1));

                    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
                    sortie.output(config, new FileOutputStream("resources/config/config.xml"));


                    SAXBuilder sxb2 = new SAXBuilder();
                    Document rdfPeople = sxb2.build(new File("resources/annotations/people.rdf"));

                    Element root = rdfPeople.getRootElement();

                    Element directorElement = new Element("Director");
                    root.addContent(directorElement);

                    directorElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                    directorElement.setAttribute("ID", "people" + peopleNextId, root.getNamespace("rdf"));

                    director.setId(Constants.FILM_INSTANCES_NS + "#people" + peopleNextId);

                    Element firstnameElement = new Element("firstname");
                        firstnameElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        firstnameElement.setText(director.getFirstname());
                        directorElement.addContent(firstnameElement);

                    Element lastnameElement = new Element("lastname");
                        lastnameElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        lastnameElement.setText(director.getLastname());
                        directorElement.addContent(lastnameElement);

                    Element nationalityElement = new Element("nationality");
                        nationalityElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        nationalityElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        nationalityElement.setAttribute("resource", Constants.FILM_INSTANCES_NS + "#" + director.getNationality().name(), root.getNamespace("rdf"));
                        directorElement.addContent(nationalityElement);

                    Element birthElement = new Element("birth");
                        birthElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        birthElement.setText(Integer.toString(director.getBirth().getYear()));
                        directorElement.addContent(birthElement);

                    Element deathElement = new Element("death");
                        deathElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        deathElement.setText(Integer.toString(director.getDeath().getYear()));
                        directorElement.addContent(deathElement);

                    Element photoElement = new Element("photo");
                        photoElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        photoElement.addContent(new CDATA(director.getPhoto()));
                        directorElement.addContent(photoElement);

                    XMLOutputter sortie2 = new XMLOutputter(Format.getPrettyFormat());
                    sortie2.output(rdfPeople, new FileOutputStream("resources/annotations/people.rdf"));

                    return true;
                }
            } catch(EngineException e) {
                e.printStackTrace();
            } catch (JDOMException jde) {
                jde.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return false;
    }
}
