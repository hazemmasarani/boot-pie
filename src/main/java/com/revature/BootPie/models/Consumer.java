package com.revature.BootPie.models;

public class Consumer {
    private String username;
    private String password;
    private Pie lastPie;

    public Consumer() {
        // Default constructor
    }

    public Consumer(String username, String password, Pie lastPie) {
        this.username = username;
        this.password = password;
        this.lastPie = lastPie;
    }   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pie getLastPie() {
        return lastPie;
    }

    public void setLastPie(Pie lastPie) {
        this.lastPie = lastPie;
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "username='" + username + '\'' +
                ", lastPie=" + (lastPie != null ? lastPie.getPieName() : "none") +
                '}';
    }


}
