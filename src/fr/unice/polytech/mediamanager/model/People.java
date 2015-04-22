package fr.unice.polytech.mediamanager.model;

import java.util.Date;

/**
 * Classe representant une personne.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public class People {

    private String id;
    private String firstname;
    private String lastname;
    private Nationality nationality;
    private Date birth;
    private Date death;
    private String photo;

    /**
     * Constructeur. 
     *
     * @param id identifiant de la personne
     * @param firstname prenom de la personne
     * @param lastname nom de la personne
     * @param nationality nationalite de la personne
     * @param birth date de naissance de la personne
     * @param death date de mort de la personne
     * @param photo URI vers la photo de la personne (URI relative, les URI pointant vers des pages Web ne sont pas gerees)
     */
    public People(String id, String firstname, String lastname, Nationality nationality, Date birth, Date death, String photo) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationality = nationality;
        this.birth = birth;
        this.death = death;
        this.photo = photo;
    }

    /**
     * Permet de recuperer l'identifiant de la personne.
     *
     * @return l'identifiant de la personne.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Permet de definir l'identifiant de la personne.
     *
     * @param id identifiant de la personne.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Permet de recuperer le prenom de la personne.
     *
     * @return le prenom de la personne.
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * Permet de definir le prenom de la personne.
     *
     * @param firstname prenom de la personne.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Permet de recuperer le nom de la personne.
     *
     * @return le nom de la personne.
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * Permet de definir le nom de la personne.
     *
     * @param lastname nom de la personne
     */
    public void setLastanme(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Permet de recuperer la nationalite de la personne.
     *
     * @return la nationalite de la personne.
     */
    public Nationality getNationality() {
        return this.nationality;
    }

    /**
     * Permet de definir la nationalite de la personne.
     *
     * @param nationality nationalite de la personne.
     */
    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    /**
     * Permet de recuperer la date de naissance de la personne.
     *
     * @return la date de naissance de la personne.
     */
    public Date getBirth() {
        return this.birth;
    }

    /**
     * Permet de definir la date de naissance de la personne.
     *
     * @param birth date de naissance de la personne.
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * Permet de recuperer la date de mort de la personne.
     *
     * @return la date de mort de la personne.
     */
    public Date getDeath() {
        return this.death;
    }

    /**
     * Permet de definir la date de mort de la personne.
     *
     * @param death date de mort de la personne.
     */
    public void setDeath(Date death) {
        this.death = death;
    }

    /**
     * Permet de recuperer l'URI de la photo de la personne.
     *
     * @return l'URI de la photo de la personne.
     */
    public String getPhoto() {
        return this.photo;
    }

    /**
     * Permet de definir l'URI de la photo de la personne.
     *
     * @param photo URI vers la photo de la personne (URI relative, les URI pointant vers des pages Web ne sont pas gerees)
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Permet de recuperer une description de la personne.
     *
     * @return la description de la personne.
     */
    public String toString() {
        String returnString = "";

        returnString += "Id: ";
        returnString += this.id;
        returnString += "\n";

        returnString += "Firstname: ";
        returnString += this.firstname;
        returnString += "\n";

        returnString += "Lastname: ";
        returnString += this.lastname;
        returnString += "\n";

        returnString += "Nationality: ";
        returnString += this.nationality.getLabelEn();
        returnString += "\n";

        returnString += "Birth: ";
        returnString += this.birth.getYear();
        returnString += "\n";

        returnString += "Death: ";
        if(this.death.equals(new Date(0, 0, 0))) {
            returnString += "////";
        } else {
            returnString += this.death.getYear();
        }
        returnString += "\n";

        returnString += "Photo: ";
        returnString += this.photo;
        returnString += "\n";

        return returnString;
    }
}
