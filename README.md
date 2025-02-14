# Set dominant minimal de sommets

Le projet vise à développer un logiciel pour la planification de bornes de recharge pour véhicules électriques dans une communauté d'agglomération. Les contraintes incluent l'accessibilité, chaque ville devant avoir ses bornes ou être connectée à une ville équipée. L'objectif est de minimiser le coût global du projet tout en respectant ces contraintes. 
Le logiciel doit représenter les villes et les routes, garantir l'accessibilité et calculer le coût optimal en termes de zones de recharge à construire.

### Equipe de développement
- CROUZET Oriane
- PUGENGER Riad
- GAMEIRO COSTA Shawn

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
### Présentation

Le premier algorithme cherche à trouver un ensemble dominant de sommets. Le second tente de trouver un ensemble minimal dominant de sommets. Les deux algorithmes fonctionnent globalement de la même façon, le premier étant plus naïf que le second.

En effet, pour le premier algorithme, la solution n'est pas forcément optimale puisqu'il commence toujours par le premier sommet de l'agglomération. Le second commence par la "meilleure" ville pour exécuter l'algorithme.

### Fonctionnement du premier algorithme

L'algorithme parcourt tous les sommets de l'agglomération. Pour chaque sommet non visité, on ajoute le sommet à la solution et marque le sommet comme visité.
L'algoritme explore alors les voisins de ce sommet et les marque comme visités (puisque reliés à la ville qui possède une borne).
A la fin, on obtient un ensemble de sommets qui constituent notre solution.

### Fonctionnement du deuxième algorithme

Ici, on se base sur le même principe de sommets domines/non domines, cependant certaines modifications permettent d'améliorer l'algorithme précédent.
On possède un ensemble de sommets non dominés (initialement toutes les villes) et un ensemble pour la solution minimale. Tant qu'il reste des villes non dominées, on continue l'algorithme. A chaque tour de boucle, on cherche la "meilleure" ville et on l'ajoute dans la solution : la "meilleure" ville est celle qui possède le plus grand nombre de voisins non dominés.
Cet ajout permet de commencer l'algorithme au sommet le plus intéréssant (mais également le plus contraignant), et ainsi réduire le nombre de villes avec une borne.
