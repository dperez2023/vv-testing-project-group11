package upm.es;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private List<Website> websites;

    public Account() {
        this.websites = new ArrayList<>();
    }

    public Website getWebsite(Website website) {
        Website exists = this.websites.stream()
                .filter(currentWebsite -> currentWebsite.getUrl().equals(website.getUrl()))
                .findFirst()
                .orElse(null);

        if(exists != null) {
            String message = String.format("Get: Website %s has been found.", website.getUrl());
            //System.out.println(message);
            return exists;
        } else {
            String message = String.format("Get: Website %s haven't been found.", website.getUrl());
            //System.out.println(message);
            return null;
        }
    }

    public Boolean addWebsite(Website website) {
        this.websites.add(website);
        String message = String.format("Add: Website %s has been added.", website.getUrl());
        Logger.success(message);
        return true;
    }

    public Boolean removeWebsite(Website website) {
        this.websites.remove(getWebsite(website));
        String message = String.format("Remove: Website %s has been removed", website.getUrl());
        Logger.success(message);
        return true;
    }

    public void removeWebsites() {
        this.websites = new ArrayList<>();
        String message = String.format("Remove: All websites has been removed");
        Logger.error(message);
    }

    public void countWebsites() {
        Integer totalWebsites = this.websites.size();
        Integer totalUsernames = 0;
        for (Website website : websites) {
            totalUsernames += website.countUsernames();
        }

        String message = String.format("Count: Total Websites: %s. Total Usernames: %d", totalWebsites, totalUsernames);
        Logger.success(message);
    }

    public void displayWebsites() {
        if(!websites.isEmpty()) {
            for (Website website : websites) {
                website.displayUsernames();
            }
        } else {
            String message = String.format("Account: No websites to display");
            Logger.error(message);
        }
    }
}
