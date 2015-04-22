package fr.unice.polytech.mediamanager.model;

/**
 * Classe representant un genre de film.
 *
 * @author Brel Christian <brel@polytech.unice.fr>
 * @version 05/06/2009
 */
public enum Genre {
    action("Action", "Action"), adventure("Adventure", "Aventure"), animation("Animation", "Animation"),
    biography("Biography", "Biographie"), cartoon("Cartoon", "Dessin Anime"), comedy("Comedy", "Comedie"), crime("Crime", "Policier"),
    documentary("Documentary", "Documentaire"), drama("Drama", "Drame"), family("Family", "Famille"),
    fantasy("Fantasy", "Fantastique"), history("History", "Historique"), horror("Horror", "Horreur-Epouvante"),
    musical("Musical", "Comedie Musicale"), romance("Romance", "Romance"), scifi("Sci-Fi", "Science fiction"),
    thriller("Thriller", "Thriller"), war("War", "Guerre"), western("Western", "Western"), 
    rock("Rock", "Rock"), pop("Pop", "Pop"), jazz("Jazz", "Jazz"), country("Country", "Country");

    private String labelFr;
    private String labelEn;

    /**
     * Constructeur.
     *
     * @param labelEn label du genre en Anglais.
     * @param labelFr label du genre en Francais.
     */
    private Genre(String labelEn, String labelFr) {
        this.labelEn = labelEn;
        this.labelFr = labelFr;
    }

    /**
     * Permet de recuperer le label en Anglais du genre.
     *
     * @return le label en anglais du genre.
     */
    public String getLabelEn() {
        return this.labelEn;
    }

    /**
     * Permet de recuperer le label en Francais du genre.
     *
     * @return le label en francais du genre.
     */
    public String getLabelFr() {
        return this.labelFr;
    }
}
