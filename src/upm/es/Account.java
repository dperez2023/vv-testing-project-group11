package upm.es;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private List<Website> websites;

    public Account() {
        this.websites = new ArrayList<>();
    }

    public Website getWebsite(Website website) {
        if(this.websites.contains(website)) {
            String message = String.format("Get: Website '%s' has been found.", website.getUrl());
            System.out.println(message);
            return website;
        } else {
            String message = String.format("Get: Website '%s' haven't been found.", website.getUrl());
            System.out.println(message);
            return null;
        }
    }

    public Boolean addWebsite(Website website) {
        this.websites.add(website);
        String message = String.format("Add: Website '%s' has been added.", website.getUrl());
        System.out.println(message);
        return true;
    }

    public Boolean removeWebsite(Website website) {
        if(getWebsite(website) != null) {
            this.websites.remove(website);
            String message = String.format("Remove: Website '%s' has been removed", website.getUrl());
            System.out.println(message);
            return true;
        } else {
            String message = String.format("Remove: Website '%s' doesn't exist.", website.getUrl());
            System.out.println(message);
            return false;
        }
    }

    public void countWebsites() {
        Integer totalWebsites = this.websites.size();
        Integer totalUsernames = 0;
        for (Website website : websites) {
            totalUsernames += website.countUsernames();
        }

        String message = String.format("Count: Total Websites: '%s'. Total Usernames: %d", totalWebsites, totalUsernames);
        System.out.println(message);
    }

    public void displayWebsites() {
        for (Website website : websites) {
            website.displayUsernames();
        }
    }
}
