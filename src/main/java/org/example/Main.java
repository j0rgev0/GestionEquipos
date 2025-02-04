package org.example;

public class Main {

    public static void main(String[] args) {


        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setVisible(true);

        for(int i = 0; i <= 100;i++) {
            try {
                Thread.sleep(20);
                splashScreen.updateProgress(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        splashScreen.dispose();

        new HomeScreen().setVisible(true);

    }
}