package fr.unice.polytech.mediamanager.view;

import fr.unice.polytech.mediamanager.control.MediaControl;
import fr.unice.polytech.mediamanager.model.Film;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * @author : Lucas SOUMILLE
 * @date : 11/03/15
 */
public class ResultatForm extends JPanel {

    private JButton ajout;
    private JButton delete;
    private JButton details;
    private JList<String> resultats;
    private DefaultListModel model;
    private JLabel labelRes;
    private MediaControl controller;

    private JPanel paneAdd;

    public ResultatForm(MediaControl controller){
        init(controller);
    }

    /*
    create the view
     */
    private void init(MediaControl controller){
        this.controller = controller;

        setLayout(new BorderLayout(0,5));
        this.controller = controller;
        ajout = new JButton("+");
        ajout.addActionListener(new AddListener());
        delete = new JButton("Supprimer");
        delete.addActionListener(new DeleteListener());
        details = new JButton("Détails");
        details.addActionListener(new DetailListener());
        labelRes = new JLabel("Résultats :");

        initModel();
        resultats = new JList<String>(model);
        resultats.setBorder(new EmptyBorder(1,1,5,1));
        add(labelRes, BorderLayout.NORTH);
        add(resultats, BorderLayout.CENTER);

        paneAdd = new JPanel();
        paneAdd.setLayout(new GridLayout(3,1));
        paneAdd.add(details);
        paneAdd.add(delete);
        paneAdd.add(ajout);

        add(paneAdd, BorderLayout.SOUTH);

    }

    public DefaultListModel getModel() {
        return model;
    }

    /*
    add a new movie at the list
     */
    public void addMovieElement(String elt){
        model.addElement(elt);
    }

    /*
   delete all the movies in the list
     */
    public void removeAllResults() {
        model.removeAllElements();
    }

    public JLabel getLabelRes() {
        return labelRes;
    }

    /*
    initialize the list with all the movies in the database
     */
    public void initModel(){
        model = new DefaultListModel();
        List<Film> allFilm = controller.getAllMovies();
        for(Film f : allFilm){
            model.addElement(f.getTitle());
        }
    }

    /*
    listener linked to the addButton to create an addframe
     */
    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            controller.createAddFrame();
        }
    }

    /*
    listener linked to the deletebutton to remove an element
     */
    private class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (!resultats.getSelectedValuesList().isEmpty()) {
                String selectedFilm = resultats.getSelectedValuesList().get(0);
                controller.deleteMovie(selectedFilm);
            }
        }
    }

    /*
    listener linked to the detailbutton to create a editfilm
     */
    private class DetailListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (!resultats.getSelectedValuesList().isEmpty()) {
                String selectedFilm = resultats.getSelectedValuesList().get(0);
                controller.createDetailFilm(selectedFilm);
            }
        }
    }


}
