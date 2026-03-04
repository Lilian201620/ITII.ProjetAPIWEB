# ITII.ProjetAPIWEB

## Sommaire

1. De quoi s'agit-il ?
2. Comment fonctionne-t'il ?
3. Comment l'exécuter ?

## De quoi s'agit-il ?

Ce projet est à titre pédagogique. Il s'agit du rendu pour le devoir de programmation avancé en Java proposé par M. Loge.
Il n'est pas déstiné à un usage public. Aucun travail d'optimisation profonde n'a été menée, le développement ayant été mené sur la courte période de 2 mois.

------

Il s'agit d'un "moteur de recherche" de villes par le biais d'une API web du gouvernement. A partir de cette recherche, vous pourrez en tirer des informations comme la population, les codes postaux, ...
Cette fonctionnalité est liée avec une autre fonctionnalité permettant de connaître la météo en direct de cette ville, à l'aide d'une autre API.
Enfin, il est possible de rechercher les établissements d'une ville à l'aide de la troisième et dernière API incluse dans ce projet.

Il est possible de sauvegarder les résultats (Etablissement & Commune) dans une base de données qui est fournie avec lors de l'installation.

## Comment fonctionne-t'il ? ( Pré-requis )

Ce projet fonctionne sous Docker.
Une installation de docker récente est nécessaire, et la possibilité d'exécuter des fichiers "docker-compose.yml" également.
Pour plus d'informations, merci de vous renseigner sur la documentation officielle Docker : 
[Site Docker](https://www.docker.com/)

## Comment l'exécuter ?

Pour faciliter la mise en place et la liaison de la BDD, il est préférable d'utiliser le fichier docker-compose mis à disposition dans /build/docker plutôt que de recompiler l'image soit même.
Une fois le fichier docker-compose installé, il suffit d'exécuter la commande suivante dans un powershell :
```docker compose up```
