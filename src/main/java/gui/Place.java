package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import modele.Lieu;

import java.util.ArrayList;

/**
 * classe representant une zone de jeu cliquable
 */
public class Place extends Circle {

    /**
     * @param x     centre x
     * @param y     centre y
     * @param rayon rayon!
     */

    private static ArrayList<Place> listeDePlace = new ArrayList<Place>();
    private Lieu lieu;

    public Place(double x, double y, double rayon, Lieu lieu ) {
        super(x, y, rayon, Color.WHITE);
        setOpacity(0.7);
        this.lieu = lieu;
        listeDePlace.add(this);


    }

    public static ArrayList<Place> getListeDePlace() {
        return listeDePlace;
    }

    public static void setListeDePlace(ArrayList<Place> listeDePlace) {
        Place.listeDePlace = listeDePlace;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public String toString() {
        return "place en " + getCenterX() + ", " + getCenterY();
    }
}
