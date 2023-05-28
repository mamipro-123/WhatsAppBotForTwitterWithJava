package org.muh4mmrd;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to Whatsapp_İnstagram Bot");
        Thread.sleep(500);
        System.out.println("Starting Program ....1");
        Thread.sleep(1000);
        System.out.println("Starting Program ....2");
        Thread.sleep(1000);
        System.out.println("Starting Program ....3");
        Thread.sleep(1000);

        System.setProperty("webdriver.edge.driver", "lib/msedgedriver.exe");

        System.out.println("Drivers are initializing");
        EdgeOptions options = new EdgeOptions();
        // options.setHeadless(true);
        WebDriver whatsapp = new EdgeDriver();
        WebDriver instagram = new EdgeDriver(options);
        whatsapp.navigate().to("https://web.whatsapp.com");

        System.out.println("Waiting for WhatsApp login...");
        for (; ; ) {
            try {
                String control = whatsapp.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[4]/header/div[1]/div/img")).getAttribute("src");
                try {
                    String control2 = whatsapp.findElement(By.xpath("//*[@id=\"main\"]/header/div[1]/div/img")).getAttribute("src");
                    System.out.println("Successfully logged in!");
                    break;
                } catch (Exception e) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter Person or Group");
                    String user = scanner.nextLine();
                    WebElement chatInput = whatsapp.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/div/div[2]/div/div[1]/p"));
                    chatInput.click();
                    chatInput.sendKeys(user);
                    chatInput.sendKeys(Keys.ENTER);
                }
            } catch (Exception e) {
                Thread.sleep(2000);
                System.out.println("Waiting for WhatsApp login...");
            }
        }

        sendMessage(whatsapp, "Program Başarıyla Başlatıldı");
        for (; ; ) {
            WebElement messageContainer = whatsapp.findElements(By.xpath("(//div[@data-testid='msg-container'])[last()]//div[@class='copyable-text']//span//span")).get(0);
            String message = messageContainer.getText();
            System.out.println(message);
            if (message.equals("!info")) {
                sendMessage(whatsapp, "Welcome to İnstagram Bot for WhatsApp\n" +
                        "----------------------------------------------------------------\n" +
                        "Commands:\n" +
                        "\n" +
                        "!username follows\n" +
                        "!username followers\n" +
                        "!username biography\n" +
                        "\n" +
                        "!username count\n");
            } else {
                try {
                    String[] data = message.replace("!", "").split(" ");
                    int datacount = 0;
                    if (data.length == 3) {
                        datacount = Integer.parseInt(data[2]);
                    }
                    Thread.sleep(1500);
                    switch (data[1]) {
                        case "follows":
                            if (!instagram.getCurrentUrl().equals("https://twitter.com/" + data[0]))
                                instagram.navigate().to("https://twitter.com/" + data[0]);
                            WebElement followsElement = instagram.findElements(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/div[5]/div[1]/div/span[1]/span")).get(datacount);
                            String follows = followsElement.getText();
                            sendMessage(whatsapp, follows);
                            break;
                        case "followers":
                            if (!instagram.getCurrentUrl().equals("https://twitter.com/" + data[0]))
                                instagram.navigate().to("https://twitter.com/" + data[0]);
                            WebElement followersElement = instagram.findElements(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/div[5]/div[2]/div/span[1]/span")).get(datacount);
                            String followers = followersElement.getText();
                            sendMessage(whatsapp, followers);
                            break;
                        case "biography":
                            if (!instagram.getCurrentUrl().equals("https://twitter.com/" + data[0]))
                                instagram.navigate().to("https://twitter.com/" + data[0]);
                            WebElement bioElement = instagram.findElements(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[3]/div/div/div[1]/div[2]/div[3]/div/div/span[1]")).get(datacount);
                            String bio = bioElement.getText();
                            sendMessage(whatsapp, bio);
                            break;
                        case "count":
                            if (!instagram.getCurrentUrl().equals("https://twitter.com/" + data[0]))
                                instagram.navigate().to("https://twitter.com/" + data[0]);
                            WebElement countElement = instagram.findElements(By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[1]/div[1]/div/div/div/div/div/div[2]/div/div")).get(datacount);
                            String count = countElement.getText();
                            sendMessage(whatsapp, count);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessage(WebDriver whatsapp, String message) {
        try {
            WebElement messageInput = whatsapp.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p"));
            messageInput.click();
            messageInput.sendKeys(message);
            Thread.sleep(200);
            messageInput.sendKeys(Keys.ENTER);
            System.out.println("----------------------------------------------------------------");
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
