package modele;

import gui.PersonneImg;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Personne {
    // Liste de personnes
    private static ArrayList<Personne> listePersonnes = new ArrayList<>();

    // sa position x,y dans l'environnement (x, y n'étant pas des pixels, mais ses no de ligne et de colonne)
    private Point point;
    // son etat (malade ou non)
    private boolean etatMalade; // Malade si 1
    //  la duree depuis laquelle elle est dans la même place qu'un malade
    private int duree;
    // son identifiant, unique
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    //un lien vers sa representation graphique PersonneImg
    private PersonneImg persImg;
    // un lien vers l'environnement
    private Environnement envt;
    // domicile qui est un lieu et une position du domicile (un Point)
    private Hashtable<Lieu, Point> domicile;
    // son lieu actuel et sa position actuelle.
    private Hashtable<Lieu, Point> lieuActuel;
    // un champs activite de type Activite qui est un énumération possédant les champs Repos, Travail, Course.
    private Activite activite;


    private Personne(int x, int y, boolean etatMalade, int duree, PersonneImg persImg, Environnement envt) {
        this.point = new Point(x, y);
        this.etatMalade = etatMalade;
        this.duree = duree;
        this.id = count.incrementAndGet();
        this.persImg = persImg;
        this.envt = envt;
        listePersonnes.add(this);
        // ajouter la personne dans la liste de son environnement
        envt.getListePersonnesDansEnvt().add((this));

    }

    public Personne(int x, int y, PersonneImg persImg, Environnement envt) {
        this.point = new Point(x, y);
        this.etatMalade = false;
        this.duree = 0;
        this.id = count.incrementAndGet();
        this.persImg = persImg;
        this.envt = envt;
        listePersonnes.add(this);
        // ajouter la personne dans la liste de son environnement
        envt.getListePersonnesDansEnvt().add((this));
        //this.activite = activiteHasard();
        this.activite = Activite.Repos;

    }

    private Activite activiteHasard() {
        Random hasard = new Random();
        return Activite.values()[new Random().nextInt(Activite.values().length)];
    }


    // Mettre en malade
    private void setMalade() {
        this.persImg.changeColorMalade();
        this.etatMalade = true;
    }


    void updatepos(int x, int y) {
        this.getPoint().setLocation(x, y);
    }


    public static ArrayList<Personne> getListePersonnes() {
        return listePersonnes;
    }

    public static void setListePersonnes(ArrayList<Personne> listePersonnes) {
        Personne.listePersonnes = listePersonnes;
    }


    public boolean isEtatMalade() {
        return etatMalade;
    }

    public void setEtatMalade(boolean etatMalade) {
        this.etatMalade = etatMalade;
    }


    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public PersonneImg getPersImg() {
        return persImg;
    }

    public void setPersImg(PersonneImg persImg) {
        this.persImg = persImg;
    }

    public Environnement getEnvt() {
        return envt;
    }

    public void setEnvt(Environnement envt) {
        this.envt = envt;
    }

    public Hashtable<Lieu, Point> getDomicile() {
        return domicile;
    }

    public void setDomicile(Hashtable<Lieu, Point> domicile) {
        this.domicile = domicile;
    }

    public Hashtable<Lieu, Point> getLieuActuel() {
        return lieuActuel;
    }

    public void setLieuActuel(Hashtable<Lieu, Point> lieuActuel) {
        this.lieuActuel = lieuActuel;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }


    public int getDuree() {
        return duree;
    }
}

