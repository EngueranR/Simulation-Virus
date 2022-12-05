package gui;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import modele.Personne;

import java.util.ArrayList;
import java.util.List;

/**
 * classe représentant un pion de jeu
 *
 * @author emmanueladam
 */
public class PersonneImg extends Circle {



    private boolean selected;
    private Paint maCouleur;

    private static List<PersonneImg> personneImgList = new ArrayList<>();

    /**
     * @param x       centre x
     * @param y       centre y
     * @param rayon   rayon!
     * @param couleur la couleur..
     */
    public PersonneImg(double x, double y, double rayon, Paint couleur)  {
        super(x, y, rayon, couleur);
        maCouleur = couleur;
        setStroke(Color.BLACK);
        setStrokeWidth(4);
        personneImgList.add(this);
    }

    /**
     * @param x          centre x
     * @param y          centre y
     * @param rayon      rayon!
     * @param couleur    la couleur..
     * @param dropShadow l'ombre portee
     */
    public PersonneImg(double x, double y, double rayon, Paint couleur, DropShadow dropShadow) {
        this(x, y, rayon, couleur);
        setEffect(dropShadow);
    }

    /**
     * selectionne ou deselectionne le pion et change sa couleur
     */
    public void select() {
        selected = !selected;
        if (selected)
            for (Personne p : Personne.getListePersonnes()) {
                if (p.getPersImg() == this) {
                    if (p.isEtatMalade()) {
                        this.setFill(Color.ORANGERED);
                        break;
                    } else this.setFill(Color.LIGHTBLUE);
                }
            }
        else
            for (Personne p : Personne.getListePersonnes()) {
                if (p.getPersImg() == this) {
                    if (p.isEtatMalade()) {
                        this.setFill(Color.RED);
                        break;
                    } else this.setFill(Color.WHITE);
                }
            }
    }

    private void updatepos(double x,double y){
        this.setCenterX(x);
        this.setCenterY(y);
       // System.out.println(x+" "+y);
}

    public void changeColorMalade() {
        this.setMaCouleur(Color.RED);
        this.setFill(Color.RED);
    }


    private Paint getMaCouleur() {
        return maCouleur;
    }

    private void setMaCouleur(Paint maCouleur) {
        this.maCouleur = maCouleur;
    }

    public boolean isSelected() {
        return selected;
    }

    public String toString() {
        return "pion en " + getCenterX() + ", " + getCenterY();
    }


}
