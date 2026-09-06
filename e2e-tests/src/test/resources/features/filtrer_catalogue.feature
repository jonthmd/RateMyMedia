# language: fr
Fonctionnalité: Filtrer le catalogue par type
  En tant qu'utilisateur, je veux filtrer le catalogue pour ne voir que les
  médias d'un type donné.

  Scénario: Filtrer sur les livres uniquement
    Étant donné que le média "Dune (Test)" de type "LIVRE" existe dans le catalogue
    Et que le média "Inception (Test 2)" de type "FILM" existe dans le catalogue
    Quand je filtre le catalogue sur le type "LIVRE"
    Alors le média "Dune (Test)" apparaît dans la liste
    Et le média "Inception (Test 2)" n'apparaît pas dans la liste
