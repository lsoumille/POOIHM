package fr.unice.polytech.mediamanager.view;


import fr.unice.polytech.mediamanager.control.MediaControl;
import fr.unice.polytech.mediamanager.model.Genre;
import fr.unice.polytech.mediamanager.model.Nationality;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * @author : Lucas SOUMILLE
 * @date : 11/03/15
 */
public class AddFrame extends JFrame {

    private MediaControl controller;

    private JPanel p;
    private JPanel pChemin;
    private JPanel paneBouton;

    private JButton valider;
    private JButton cancel;

    private JTextField fFilm;
    private JLabel lFilm;
    private JTextField fActors;
    private JLabel lActors;
    private JTextField fDirectors;
    private JLabel lDirectors;
    private JComboBox cbGenre;
    private JLabel lGenre;
    private JComboBox cbNationality;
    private JLabel lNationality;
    private JSpinner jsDuree;
    private JLabel lDuree;
    private JTextField fLien;
    private JButton parcourir;
    private JLabel lLien;
    private JTextArea fSynop;
    private JLabel lSynop;

    JFileChooser chooser;

    ButtonsListener list;

    public AddFrame(MediaControl controller){
        init(controller);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /*
    create the view
     */
    private void init(MediaControl controller){
        this.controller = controller;
        list = new ButtonsListener();

        p = new JPanel(new GridLayout(8,2));

        valider = new JButton("Ajouter");
        valider.addActionListener(list);
        cancel = new JButton("Annuler");
        cancel.addActionListener(list);

        lFilm = new JLabel("Titre :");
        fFilm = new JTextField(20);
        lFilm.setLabelFor(fFilm);
        p.add(lFilm);
        p.add(fFilm);

        lActors = new JLabel("Acteurs :");
        fActors = new JTextField(20);
        p.add(lActors);
        p.add(fActors);

        lDirectors = new JLabel("Réalisateur :");
        fDirectors = new JTextField(20);
        p.add(lDirectors);
        p.add(fDirectors);

        lGenre = new JLabel("Genre :");
        cbGenre = new JComboBox();
        initComboGenre();
        p.add(lGenre);
        p.add(cbGenre);

        lNationality = new JLabel("Nationalité :");
        cbNationality = new JComboBox();
        initComboNation();
        p.add(lNationality);
        p.add(cbNationality);


        lDuree = new JLabel("Durée (min):");
        jsDuree = new JSpinner();
        p.add(lDuree);
        p.add(jsDuree);


        lLien = new JLabel("Affiche :");
        pChemin = new JPanel();
        pChemin.setLayout(new GridLayout(1,2));
        fLien = new JTextField(10);
        parcourir = new JButton("Parcourir");
        parcourir.addActionListener(list);
        p.add(lLien);
        pChemin.add(fLien);
        pChemin.add(parcourir);
        p.add(pChemin);

        lSynop= new JLabel("Synopsis :");
        fSynop = new JTextArea();
        fSynop.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(fSynop);
        p.add(lSynop);
        p.add(jsp);

        paneBouton = new JPanel();
        paneBouton.add(valider, BorderLayout.WEST);
        paneBouton.add(cancel,BorderLayout.EAST);

        this.add(p, BorderLayout.CENTER);
        this.add(paneBouton, BorderLayout.SOUTH);

        chooser = new JFileChooser();
    }

    /*
    Listener link to the three buttons of the view
     */
    private class ButtonsListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == cancel) {
                dispose();
            } else if (event.getSource() == parcourir){
                chooser.showOpenDialog(p);
                fLien.setText(chooser.getSelectedFile().getPath());
            } else if (event.getSource() == valider){
                HashMap<String, Object> info = new HashMap<String, Object>();
                info.put("TITLE", fFilm.getText());
                info.put("DIRECTOR", fDirectors.getText());
                info.put("ACTOR", fActors.getText());
                info.put("GENRE", cbGenre.getSelectedItem().toString());
                info.put("RUNTIME", jsDuree.getValue());
                info.put("POSTER", fLien.getText());
                info.put("SYNOPSIS", fSynop.getText());
                controller.addFilm(info);
                dispose();
            }
        }
    }

    /*
    add all the genres at the combobox
     */
    private void initComboGenre(){
        List<Genre> allGenres = controller.getAllGenres();
        for(Genre g : allGenres){
            cbGenre.addItem(g);
        }
    }

    /*
    add all the nationalities at the combobox
     */
    private void initComboNation(){
        List<Nationality> allNation = controller.getAllNationalities();
        for(Nationality nat : allNation){
            cbNationality.addItem(nat);
        }
    }

}
