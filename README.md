
# SimuVirus 🦠

Le but de ce TP est de programmer un simulateur de propagation de virus.


## Fonction
## Simuvirus

| Fonction | Description |
| ----------------- | ------------------------------------------------------------------ |
| `start` |  Défini les propriétés de la fenêtre |
| `construirePlateauJeu` |  Construit un plateau de jeu avec les propriétés de la troupe des acteurs et des decors |
| `construireFXML` |  Construit une fenêtre avec les propriétés du FXML  |
| `lancer` |  Démmare un cycle  |
| `quitter` |  Quitter le cycle |
| `go` |  Permet de mettre en route le cycle (Lancer la simulation 4xAvancerTemps 1xSactiver Avec le FXML)  |
| `dessinEnvironnement` |  Dessine les lignes sur le plateau |
| `ajouterPlaces` |  Ajout des places/lieu sur le plateau |
| `ajoutPions` |  Ajout des pions/personnes sur le plateau |
| `updatePos` |  Mis à jour de la position de la personne en fonction des paramètres du plateau |
| `handle` |  Gestion de la souris : selection de jeton, de place |
| `animPionVers` |  lancement d'une animation de deplacement du jeton selectionne vers la place |
| `ajoutPions` |  Ajout des pions/personnes sur le plateau |




## Environnement

| Fonction | Description |
| ----------------- | ------------------------------------------------------------------ |
| `bouger` |  demande à chaque personne dans l'environnement de bouger |
| `afficherActivite` | Affiche l'activité de chaque personnes |
| `sactiver` | Bouge les personnes en fonction de l'activité  |
| `avancerTemps` |  Demande à chaque personne de calculer son temps de contact avec malade  |
| `updateFXML` |  Met à jour les informations du FXML |
| `addPersonne` |  Ajoute une personne dans la liste, fonction lancée par l'application   |
| `switchMalade` |  Change la propriété de la personne voulu en malade et change la couleur du pion avec les coordonées en paramètre|
 `switchMalade` |  Change la propriété de la personne voulu en malade et change la couleur du pion avec la personne en paramètre|
  ` switchMaladeRandom` |  Change la propriété d'une personne aléatoire en malade et change la couleur du pion |
| `addLieu` |  ajoute un lieu dans le plan |
| `putTypeDeLieuAnnuaire` |  ajoute un type de lieu dans l'annuaire |
| `putLieuAnnuaire` |  Ajoute une liste de de Lieu dans l'annuaire || `animPionVers` |  lancement d'une animation de deplacement du jeton selectionne vers la place |

## Personne

| Fonction | Description |
| ----------------- | ------------------------------------------------------------------ |
| `Activite` |  Donne une activité aléatoire à une personne  |
| `setMalade` | Met une personne malade et|
| `sactiver` |  Change la propriété de la personne voulu en malade et change la couleur du pion |
| `updatepos` |  Change les coordonées de la personne |
| `sactiver` |  Change la propriété de la personne voulu en malade et change la couleur du pion |

## Lieu

| Fonction | Description |
| ----------------- | ------------------------------------------------------------------ |
| `addPersonnes` |  Ajoute une personne à la liste de personnes  |
| `removePersonnes` | Supprime une personne à la liste de personnes|


## Touche du clavier


| Touche | Fonction |
| ----------------- | ------------------------------------------------------------------ |
| `T` | AvancerTemps |
| `B` | Bouger |
| `M` | switchMaladeRandom |
| `A` | Sactiver|
| `G` | Go |


## Couleur 
 

| Lieu              | Couleur                                                            |
| ----------------- | ------------------------------------------------------------------ |
| Domicile | ![#0a192f](https://placehold.co/15x15/0a192f/0a192f.png) `#0a192f`|
| Entreprise | ![191970](https://placehold.co/15x15/191970/191970.png) `#191970` |
| Magasin | ![#F5FFFA](https://placehold.co/15x15/F5FFFA/F5FFFA.png) `#F5FFFA` |


| Personne          | Couleur                                                             |
| ----------------- | ------------------------------------------------------------------ |
| Durée du contact = 1 | ![#FAFAD2](https://placehold.co/15x15/FAFAD2/FAFAD2.png) `#FAFAD2` |
| Durée du contact = 2 | ![#FFB6C1](https://placehold.co/15x15/FFB6C1/FFB6C1.png) `#FFB6C1` |
| Durée du contact > 3 | ![#FF0000](https://placehold.co/15x15/FF0000/FF0000.png) `#FF0000` |




