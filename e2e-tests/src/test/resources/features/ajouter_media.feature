# language: fr
Fonctionnalité: Ajouter un média au catalogue
  En tant qu'utilisateur, je veux ajouter différents types de médias au
  catalogue, pour vérifier que les champs spécifiques à chaque type sont
  bien pris en compte par le formulaire et par le backend.

  Contexte:
    Étant donné que je suis sur le formulaire d'ajout d'un média

  Scénario: Ajouter un film
    Quand je choisis le type "FILM" et je renseigne le titre "Inception"
    Et je renseigne le réalisateur "Christopher Nolan" et une durée de "148" minutes
    Et je valide le formulaire
    Alors le média "Inception" apparaît dans le catalogue

  Scénario: Ajouter un livre
    Quand je choisis le type "LIVRE" et je renseigne le titre "Dune"
    Et je renseigne l'auteur "Frank Herbert", "688" pages et l'éditeur "Robert Laffont"
    Et je valide le formulaire
    Alors le média "Dune" apparaît dans le catalogue

  Scénario: Ajouter un album
    Quand je choisis le type "ALBUM" et je renseigne le titre "Random Access Memories"
    Et je renseigne l'artiste "Daft Punk", "13" pistes et le genre "Electro"
    Et je valide le formulaire
    Alors le média "Random Access Memories" apparaît dans le catalogue
