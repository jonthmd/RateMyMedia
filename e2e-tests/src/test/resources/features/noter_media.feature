# language: fr
Fonctionnalité: Noter un média
  En tant qu'utilisateur, je veux donner une note et un commentaire à un
  média déjà présent dans le catalogue.

  Scénario: Noter un film déjà présent au catalogue
    Étant donné que le média "Inception (Test)" de type "FILM" existe dans le catalogue
    Quand je consulte la fiche du média "Inception (Test)"
    Et je lui attribue la note de 5 avec le commentaire "Un classique du genre !"
    Alors l'avis avec la note 5 apparaît dans la liste des avis
