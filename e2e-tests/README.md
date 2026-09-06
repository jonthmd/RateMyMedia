# e2e-tests

Tests Cucumber + Page Object, exécutables via **deux configurations
différentes** sans changer une ligne de Gherkin, de step definition ni de
Page Object — seule la fabrication du WebDriver change (voir
`DriverFactory`).

## Pré-requis communs

Avant de lancer les tests, quelle que soit la configuration :

```bash
cd rate-my-media
docker compose up
```

Et dans un autre terminal :
```bash
cd frontend
ng serve
```

## Configuration "web" (par défaut) : Selenium + Chrome desktop

Aucune infrastructure supplémentaire nécessaire.

```bash
cd e2e-tests
mvn test
```

Chrome s'ouvre avec l'émulation mobile activée (résolution/UA d'un Pixel 7),
et chromedriver est téléchargé automatiquement par WebDriverManager (pas de
configuration manuelle).

## Configuration "android" : Appium + UiAutomator2 sur émulateur

Trois choses doivent tourner AVANT de lancer les tests :

1. **L'émulateur Android** démarré (via Android Studio ou `emulator -avd <nom>`)
   — vérifier avec `adb devices`.
2. **Le serveur Appium** lancé dans un terminal dédié :
   ```bash
   appium
   ```
3. **Le driver UiAutomator2** installé (une seule fois) :
   ```bash
   appium driver install uiautomator2
   ```

Puis :
```bash
cd e2e-tests
mvn test -Pandroid
```

Appium ouvre alors Chrome directement dans l'émulateur et pointe
automatiquement vers `http://10.0.2.2:4200` (voir `TestConfig`), l'alias
réseau qui permet à l'émulateur d'atteindre ta machine hôte.

## Pourquoi le même code fonctionne dans les deux cas

`AndroidDriver` (Appium) **hérite** de `WebDriver` (Selenium). Les Page
Objects (`MediaListPage`, `MediaFormPage`, `MediaDetailPage`) ne
connaissent que l'interface `WebDriver` — ils n'ont aucune idée de si,
en coulisses, les clics partent vers un vrai Chrome desktop ou vers Chrome
dans un émulateur Android. Seul `DriverFactory` sait comment construire
l'un ou l'autre, selon la propriété système `platform` (positionnée par
les profils Maven `web`/`android` du `pom.xml`).

## Stratégie de données de test

- Les scénarios de **`ajouter_media.feature`** testent le vrai parcours
  utilisateur via l'UI (formulaire).
- Les scénarios de **`noter_media.feature`** et **`filtrer_catalogue.feature`**
  préparent leurs données via l'API REST de catalog-service directement
  (`CatalogApiClient`), plus rapide et plus fiable que de repasser par le
  formulaire à chaque fois — l'ajout via l'UI est déjà testé ailleurs.

Limite actuelle assumée : les médias créés pour les tests ne sont pas
supprimés après exécution (pas de `@After` qui nettoie via DELETE). Pour
un exercice, ce n'est pas gênant — le catalogue grossit simplement à
chaque run. Une amélioration possible plus tard : ajouter un hook qui
supprime les médias créés pendant le scénario.
