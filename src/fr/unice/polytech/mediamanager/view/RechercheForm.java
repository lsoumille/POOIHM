package fr.unice.polytech.mediamanager.view;

import fr.unice.polytech.mediamanager.control.MediaControl;
import fr.unice.polytech.mediamanager.model.Film;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author : Lucas SOUMILLE
 * @date : 11/03/15
 */
public class RechercheForm extends JPanel implements ActionListener{

    private JTextField rechField;
    private ButtonGroup allRadio;
    private JButton search;
    private JRadioButton acteur;
    private JRadioButton director;
    private JRadioButton film;
    private JRadioButton genre;

    private MediaControl controller;

    JPanel pWest;
    JPanel pEast;

    public RechercheForm(MediaControl controller){
        this.controller = controller;
        init();
    }

    /*
    Create the view
     */
    private void init(){
        pWest = new JPanel();
        pWest.setLayout(new BoxLayout(pWest, BoxLayout.Y_AXIS));
        rechField = new JTextField("Tapez votre recherche");
        allRadio = new ButtonGroup();
        acteur = new JRadioButton("Acteur (Lastname)");
        acteur.setActionCommand("actor");
        director = new JRadioButton("Réalisateur (Lastname)");
        director.setActionCommand("director");
        film = new JRadioButton("Nom");
        film.setActionCommand("nom");
        genre = new JRadioButton("Genre");
        genre.setActionCommand("genre");
        allRadio.add(film);
        allRadio.add(acteur);
        allRadio.add(director);
        allRadio.add(genre);
        pWest.add(rechField);
        pWest.add(film);
        pWest.add(acteur);
        pWest.add(director);
        pWest.add(genre);
        add(pWest, BorderLayout.WEST);

        pEast = new JPanel();
        search = new JButton("Rechercher");
        search.addActionListener(this);
        pEast.add(search);
        add(pEast, BorderLayout.EAST);
    }

    public JTextField getRechField() {
        return rechField;
    }

    public JButton getSearch() {
        return search;
    }

    public ButtonGroup getAllRadio() {
        return allRadio;
    }

    /*
    put a inset
     */
    public Insets getInsets() {
        return new Insets(5,5,5,5);
    }


    public JRadioButton getActeur() {
        return acteur;
    }

    public JRadioButton getDirector() {
        return director;
    }

    public JRadioButton getFilm() {
        return film;
    }

    public JRadioButton getGenre() {
        return genre;
    }

    /*
    listener on the search button
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == search && allRadio.getSelection() != null){
            String categ = allRadio.getSelection().getActionCommand();
            controller.research(categ, rechField.getText());
        }
    }
}
