package com.ratemymedia.e2e;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/**
 * La configuration (plugin d'affichage détaillé, désactivation de la
 * publication cloud) est déclarée ici directement plutôt que dans un
 * fichier cucumber.properties séparé : ce dernier s'est révélé peu fiable
 * selon l'environnement d'exécution, alors que les @ConfigurationParameter
 * du @Suite sont toujours garantis d'être lus par cucumber-junit-platform-engine.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.ratemymedia.e2e")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/cucumber-report.html, summary")
@ConfigurationParameter(key = "cucumber.publish.enabled", value = "false")
public class RunCucumberTest {
}