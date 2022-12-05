package modele;

import gui.Place;

import java.awt.*;
import java.util.ArrayList;

public class Lieu {

    private Point point;
    private TypeLieu typeDeLieu;
    private ArrayList<Personne> listePersonnesDansLieu;


    public Lieu(Point point, TypeLieu typeDeLieu) {
        this.point = point;
        this.typeDeLieu = typeDeLieu;
        this.listePersonnesDansLieu = new ArrayList<Personne>();
    }


    public void addPersonnes(Personne p) {
        this.listePersonnesDansLieu.add(p);
    }

    public void removePersonnes(Personne p) {
        this.listePersonnesDansLieu.remove(p);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public TypeLieu getTypeDeLieu() {
        return typeDeLieu;
    }

    public void setTypeDeLieu(TypeLieu typeDeLieu) {
        this.typeDeLieu = typeDeLieu;
    }

    public ArrayList<Personne> getListePersonnesDansLieu() {
        return listePersonnesDansLieu;
    }

    public void setListePersonnesDansLieu(ArrayList<Personne> listePersonnesDansLieu) {
        this.listePersonnesDansLieu = listePersonnesDansLieu;
    }
}
