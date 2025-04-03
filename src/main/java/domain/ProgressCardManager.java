package domain;
import java.util.Collection;
public class ProgressCardManager {
    Bank bank;

    ProgressCardManager(Bank bank){
        this.bank = bank;
    }

    public Collection<ResourceType> playYearOfPlenty(
            Player player, ResourceType resourceOne, ResourceType resourceTwo) {
        if (!checkSufficientResources(resourceOne, resourceTwo)) {
            return player.getResources();
        }
        addResourcesToPlayer(player, resourceOne, resourceTwo);
        return player.getResources();
    }

    private boolean checkSufficientResources(ResourceType resourceOne, ResourceType resourceTwo) {
        if (resourceOne.equals(resourceTwo)) {
            return !bank.noMoreResource(resourceOne, 2);
        } else {
            return !(bank.noMoreResource(resourceOne, 1) ||
                    bank.noMoreResource(resourceTwo, 1));
        }
    }

    private void addResourcesToPlayer(Player player, ResourceType resourceOne, ResourceType resourceTwo) {
        try {
            player.addResource(resourceOne);
            player.addResource(resourceTwo);
        } catch(IllegalStateException e) {
            // Original code swallows the exception
        }
    }

    public Collection<ResourceType> playMonopoly(
            Collection<Player> players, Player player, ResourceType resource) {
        for (Player checkPlayer : players) {
            if (checkPlayer.equals(player)) {
                continue;
            }
            transferResources(checkPlayer, player, resource);
        }
        return player.getResources();
    }

    private void transferResources(Player fromPlayer, Player toPlayer, ResourceType resource) {
        while (fromPlayer.removeResource(resource)) {
            toPlayer.addResource(resource);
        }
    }
}