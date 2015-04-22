package fr.unice.polytech.mediamanager.model;

/**
 * Classe representant les nationalites des personnes.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public enum Nationality {
    french("French", "Français"), american("American", "Americain"), australian("Australian", "Australien"),
    italian("Italian", "Italien"), british("British", "Anglais"), belgian("Belgian", "Belge"),
    irish("Irish", "Irlandais"), mexican("Mexican", "Mexicain"), spanish("Spanish", "Espagnol");
    
    private String labelFr;
    private String labelEn;

    /**
     * Constructeur.
     *
     * @param labelEn label de la nationalite en Anglais.
     * @param labelFr label de la nationalite en Francais.
     */
    private Nationality(String labelEn, String labelFr) {
        this.labelEn = labelEn;
        this.labelFr = labelFr;
    }

    /**
     * Permet de recuperer le label en Anglais de la nationalite.
     *
     * @return le label en anglais de la nationalite.
     */
    public String getLabelEn() {
        return this.labelEn;
    }

    /**
     * Permet de recuperer le label en Francais de la nationalite.
     *
     * @return le label en francais de la nationalite.
     */
    public String getLabelFr() {
        return this.labelFr;
    }
}
