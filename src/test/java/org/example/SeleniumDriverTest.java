package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class SeleniumDriverTest {

    @Test
    @DisplayName("Should open and close chrome browser")
    void shouldOpenAndCloseChromeBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver"); // sets the driver path
        WebDriver driver = new ChromeDriver(); // creates a driver to interact with the browser
        driver.get("https://www.google.com"); // request the page
        Thread.sleep(1000); // waits for 1s.
        driver.quit(); // quits all WebDriver instances and closes the browser
    }

    @Test
    @DisplayName("Should open and close chrome browser using Manager")
    void shouldOpenAndCloseChromeBrowserUsingManager() throws InterruptedException {
        WebDriverManager.chromedriver().setup(); //sets up a valid driver for Chrome.
        // WebDriverManager.operadriver().setup(); // do the same for Opera browser
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(1000);
        driver.quit();
    }

    //BASE
    // Teste de Sistema
    // O que é Selenium
    // Elementos do Selenium (WebBrowser, Grid, Selenium)
    // Funcionamento do Web Browser (imagem explicando os drivers)
    // Configuração do Selenium (com Arquivo Externo)
    // Exemplo simples (abrir browser, esperar, fechar)
    // Problemas de não usar webdrivermanager (NEcessidade de gestão de versões de drivers e diferentes browsers)
    // Configuração com o webdrivermanager
    // Exemplo com driver manager
    // Encontrando elementos no site (ByClass)
    // Enviando ações para elementos no site (sendkeys())
    // Enviando ações de click (click())


    //ELEMENTS
    // Encontrando elementos no site (ById, ByClass, ByName, ByXPath)
    // Exemplo de como obter informações usando inspect element
    // Exemplo de obtençao por Id, Class, Name
    // Como criar xpath com inspect no browser
    // Exemplo de obtençao com xpath
    // Exemplo de ações iniciais (click, sendkeys())
    // Encontrando Links
    // Propriedades dos elementos (isEnabled, isBla)
    // Checkbox
    // RadioButton
    // Combobox

    //INTERACTIONS AND WAITS
    // Gestão do Driver (cookies, Window, etc.)
    // Navigate
    // Types of wait
    // Exercices
    // Exemplo de propriedades do driver (getHtml, getTitle, getSource)

}