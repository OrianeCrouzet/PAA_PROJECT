# paa_crouzet_gameiro_costa_pugenger

Le projet vise à développer un logiciel pour la planification de bornes de recharge pour véhicules électriques dans une communauté d'agglomération. Les contraintes incluent l'accessibilité, chaque ville devant avoir ses bornes ou être connectée à une ville équipée. L'objectif est de minimiser le coût global du projet tout en respectant ces contraintes. Le logiciel doit représenter les villes et les routes, garantir l'accessibilité et calculer le coût optimal en termes de zones de recharge à construire.

## Point d'entrée du projet

Le fichier principal du programme se trouve dans le dossier `src/structure`. Vous pouvez trouver le point d'entrée du programme dans le fichier `Menu.java`.

## Comment Exécuter

Assurez-vous d'avoir Java installé sur votre système.

1. Clonez ce dépôt.
2. Naviguez jusqu'au dossier source du projet (cd paa_crouzet_gameiro_costa)
2. Excecutez la commande : javac -d bin src/structure/*.java src/io/*.java src/resolution/*.java
3. Puis la commande : java -cp bin src/structure/Menu.java

## Fonctionnalités implémentées

Toutes les fonctionnalités demandées sont correctement implémentées dans le projet.

## Algorithmes
### Presentation

Le premier algorithme cherche a trouver un ensemble dominant de sommets. Le second tente de trouver un ensemble minimal dominant de sommets. Les deux algorithmes fonctionnent globalement de la même façon, le premier etant plus naif que le second.

En effet, pour le premier algorithme, la solution n'est pas forcement optimale puisqu'il commence toujours par le premier sommet de l'agglomeration. Le second commence par la "meilleure" ville pour executer l'algorithme.

### Fonctionnement du premier algorithme

L'algorithme parcourt tous les sommets de l'agglomération. Pour chaque sommet non visité, on ajoute le sommet à la solution et marque le sommet comme visité.
L'algoritme explore alors les voisins de ce sommet et les marque comme visités (puisque reliés a la ville qui possede une borne).
A la fin on obtient un ensemble de sommets qui constituent notre solution.

### Fonctionnement du deuxieme algorithme

Ici, on se base sur le meme principe de sommets domines/non domines, cependant certaines modifications permettent d'ameliorer l'algorithme precedent.
On possede un ensemble de sommets non domines (initialement toutes les villes) et un ensemble pour la solution minimale. Tant qu'il reste des villes non dominees, on continue l'algorithme. A chaque tour de boucle, on cherche la "meilleure" ville et on l'ajoute dans la solution : la "meilleure" ville est celle qui possede le plus grand nombre de voisins non domines.
Cet ajout permet de commencer l'algorithme au sommet le plus interessant (mais egalement le plus contraignant), et ainsi reduire le nombre de villes avec une borne.