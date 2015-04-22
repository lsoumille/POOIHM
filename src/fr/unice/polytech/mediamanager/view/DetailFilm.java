package fr.unice.polytech.mediamanager.view;

import fr.unice.polytech.mediamanager.control.MediaControl;
import fr.unice.polytech.mediamanager.model.Film;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * @author : Lucas SOUMILLE
 * @date : 27/03/15
 */
public class DetailFilm extends JFrame{

    private Film film;

    private JLabel filmTitle;
    private JTextField filmField;

    private JLabel mainActor;
    private JTextField actorField;

    private JLabel synopsis;
    private JTextArea synopArea;

    private JButton edit;

    private JPanel topPanel;
    private JPanel buttonPanel;

    private MediaControl controller;

    public DetailFilm(MediaControl controller, Film film) {
        init(controller, film);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /*
    create the view with the information of the movie
     */
    private void init(MediaControl controller, Film film){
        this.controller = controller;

        this.film = film;

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3,2));

        filmTitle = new JLabel("Titre :");
        topPanel.add(filmTitle);
        filmField = new JTextField(20);
        filmField.setText(film.getTitle());
        topPanel.add(filmField);

        mainActor = new JLabel("Acteur principal :");
        topPanel.add(mainActor);
        actorField = new JTextField(20);
        actorField.setText(film.getActors().get(0).getLastname());
        topPanel.add(actorField);

        synopsis = new JLabel("Synopsis :");
        topPanel.add(synopsis);
        synopArea = new JTextArea();
        synopArea.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(synopArea);
        topPanel.add(jsp);
        synopArea.setText(film.getSynopsis());
        add(topPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        edit = new JButton("Editer");
        edit.addActionListener(new EditListener());
        buttonPanel.add(edit);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
    Listener linked at the edit button
     */
    private class EditListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if (event.getSource() == edit) {
                HashMap<String, Object> info = new HashMap<String, Object>();
                info.put("TITLE", filmField.getText());
                info.put("ACTOR", actorField.getText());
                info.put("SYNOPSIS", synopArea.getText());
                controller.editMovie(film, info);
                dispose();
            }
        }
    }
}
