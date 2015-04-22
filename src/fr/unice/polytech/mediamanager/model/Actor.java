package fr.unice.polytech.mediamanager.model;

import fr.inria.acacia.corese.api.*;
import fr.inria.acacia.corese.exceptions.EngineException;
import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/**
 * Classe representant un acteur.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public class Actor extends People {

    /**
     * Constructeur. Fait appel au constructeur de la classe People.
     *
     * @param id identifiant de l'acteur
     * @param firstname prenom de l'acteur
     * @param lastname nom de l'acteur
     * @param nationality nationalite de l'acteur
     * @param birth date de naissance de l'acteur
     * @param death date de mort de l'acteur
     * @param photo URI vers la photo de l'acteur (URI relative, les URI pointant vers des pages Web ne sont pas gerees)
     */
    public Actor(String id, String firstname, String lastname, Nationality nationality, Date birth, Date death, String photo) {
        super(id, firstname, lastname, nationality, birth, death, photo);
    }

    /**
     * Permet de recuperer tous les acteurs de la base de donnees.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @return l'ensemble des acteurs de la base de donnees sous forme d'une ArrayList<Actor>
     */
    public static ArrayList<Actor> getAll(IEngine engine) {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        try {
			IResults resActors = engine.SPARQLQuery("select ?id where {	" +
                    "?id rdf:type <" + Constants.FILM_NS + "#Actor> }");

            for (Enumeration<IResult> enActors = resActors.getResults(); enActors.hasMoreElements();) {
	  			IResult rActor = enActors.nextElement();
                IResultValue[] id = rActor.getResultValues("?id");

                actors.add(Actor.getById(engine, id[0].getStringValue()));
            }
		} catch(EngineException e) {
			e.printStackTrace();
		}
        return actors;
    }

    /**
     * Permet de recuperer l'acteur designe par l'id passe en parametre.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param id identifiant de l'acteur
     * @return si trouve, retourne l'acteur, sinon retourne null.
     */
    public static Actor getById(IEngine engine, String id) {
        Actor returnActor = null;
        try {
            IResults resActor = engine.SPARQLQuery("select ?firstname ?lastname ?nationality ?birth ?death ?photo where {	" +
                        "<" + id + "> <" + Constants.FILM_NS + "#firstname> ?firstname . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#lastname> ?lastname . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#nationality> ?nationality . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#birth> ?birth . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#death> ?death . " +
                        "<" + id + "> <" + Constants.FILM_NS + "#photo> ?photo }");

                Enumeration<IResult> enActor = resActor.getResults();
                IResult rActor = enActor.nextElement();
                IResultValue[] firstnameActor = rActor.getResultValues("?firstname");
                IResultValue[] lastnameActor = rActor.getResultValues("?lastname");
                IResultValue[] nationalityActor = rActor.getResultValues("?nationality");
                IResultValue[] birthActor = rActor.getResultValues("?birth");
                IResultValue[] deathActor = rActor.getResultValues("?death");
                IResultValue[] photoActor = rActor.getResultValues("?photo");

                returnActor = new Actor(id, firstnameActor[0].getStringValue(),
                        lastnameActor[0].getStringValue(),
                        Nationality.valueOf(nationalityActor[0].getStringValue().split(Constants.FILM_INSTANCES_NS + "#")[1]),
                        new Date(Integer.parseInt(birthActor[0].getStringValue()), 0, 0),
                        new Date(Integer.parseInt(deathActor[0].getStringValue()), 0, 0),
                        photoActor[0].getStringValue());
        } catch(EngineException e) {
			e.printStackTrace();
		}
        return returnActor;
    }

    /**
     * Permet d'ajouter une acteur a la base de donnee.
     *
     * @param engine Instance du moteur Corese qui permet de faire les requetes a la base de donnee.
     * @param actor l'acteur a ajouter a la base de donnee
     * @return si l'acteur est bien ajoute, retourne true, sinon retourne false (l'acteur passe en parametre a deja un id ou l'acteur existe deja dans la base de donnee.
     */
    public static boolean add(IEngine engine, Actor actor) {
        if(actor.getId().equals("")) {
            try {
                IResults resActors = engine.SPARQLQuery("select ?firstname ?lastname where {	" +
                        "?id rdf:type <" + Constants.FILM_NS + "#Actor> ." +
                        "?id <" + Constants.FILM_NS + "#firstname> ?firstname ." +
                        "?id <" + Constants.FILM_NS + "#lastname> ?lastname ." +
                        "FILTER(regex(?firstname, '" + actor.getFirstname() + "') && regex(?lastname, '" + actor.getLastname() + "'))}");

                Enumeration<IResult> enActors = resActors.getResults();
                if(enActors.hasMoreElements()) {
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

                    Element actorElement = new Element("Actor");
                    root.addContent(actorElement);

                    actorElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                    actorElement.setAttribute("ID", "people" + peopleNextId, root.getNamespace("rdf"));

                    actor.setId(Constants.FILM_INSTANCES_NS + "#people" + peopleNextId);

                    Element firstnameElement = new Element("firstname");
                        firstnameElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        firstnameElement.setText(actor.getFirstname());
                        actorElement.addContent(firstnameElement);

                    Element lastnameElement = new Element("lastname");
                        lastnameElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        lastnameElement.setText(actor.getLastname());
                        actorElement.addContent(lastnameElement);

                    Element nationalityElement = new Element("nationality");
                        nationalityElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        nationalityElement.setAttribute("resource", Constants.FILM_INSTANCES_NS + "#" + actor.getNationality().name(), root.getNamespace("rdf"));
                        actorElement.addContent(nationalityElement);

                    Element birthElement = new Element("birth");
                        birthElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        birthElement.setText(Integer.toString(actor.getBirth().getYear()));
                        actorElement.addContent(birthElement);

                    Element deathElement = new Element("death");
                        deathElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        deathElement.setText(Integer.toString(actor.getDeath().getYear()));
                        actorElement.addContent(deathElement);

                    Element photoElement = new Element("photo");
                        photoElement.setNamespace(Namespace.getNamespace("http://www.polytech.unice.fr/mediamanager/film.rdfs#"));
                        photoElement.addContent(new CDATA(actor.getPhoto()));
                        actorElement.addContent(photoElement);

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
