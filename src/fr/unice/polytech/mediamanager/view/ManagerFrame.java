package fr.unice.polytech.mediamanager.view;

import fr.unice.polytech.mediamanager.control.MediaControl;
import fr.unice.polytech.mediamanager.model.Film;

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
public class ManagerFrame extends JFrame{

    private RechercheForm research;
    private ResultatForm resultat;
    private MediaControl controller;

    public ManagerFrame(MediaControl controller){
        init(controller);
        //dimensionWindow();
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*
    create the view
     */
    private void init(MediaControl controller){
        this.controller = controller;
        research = new RechercheForm(this.controller);
        resultat = new ResultatForm(this.controller);

        add(research, BorderLayout.NORTH);
        add(resultat, BorderLayout.SOUTH);
    }

    public ResultatForm getResultat() {
        return resultat;
    }

    /*
    set a minimum size at the view
     */
    public void dimensionWindow(){
        setResizable(false);
    }


}
