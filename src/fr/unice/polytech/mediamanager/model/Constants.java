package fr.unice.polytech.mediamanager.model;

/**
 * Constantes utiles pour le mediamanager.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public class Constants {

    /**
     * Namespace pour le schema des films.
     */
    public static final String FILM_NS = "http://www.polytech.unice.fr/mediamanager/film.rdfs";
    /**
     * Namespace pour le schema des musiques.
     */
    public static final String MUSIC_NS = "http://www.polytech.unice.fr/mediamanager/film.rdfs";

    /**
     * Namespace pour les instances du schema des films.
     */
    public static final String FILM_INSTANCES_NS = "http://www.polytech.unice.fr/mediamanager/film.rdfs-instances";
    /**
     * Namespace pour les instances du schema des musiques.
     */
    public static final String MUSIC_INSTANCES_NS = "http://www.polytech.unice.fr/mediamanager/film.rdfs-instances";
    
    public static String toXmlString(String atraiter) {
        String returnString = atraiter;
        returnString = returnString.replaceAll("&", "&amp;");
        returnString = returnString.replaceAll("'", "&#39;");
        returnString = returnString.replaceAll("\"", "&quot;");
        returnString = returnString.replaceAll("<", "&lt;");
        returnString = returnString.replaceAll(">", "&gt;");

        return returnString;
    }

    public static String fromXmlString(String atraiter) {
        String returnString = atraiter;
        returnString = returnString.replaceAll("&#39;", "'");
        returnString = returnString.replaceAll("&quot;", "\"");
        returnString = returnString.replaceAll("&lt;", "<");
        returnString = returnString.replaceAll("&gt;", ">");
        returnString = returnString.replaceAll("&amp;","&");

        return returnString;
    }
}
