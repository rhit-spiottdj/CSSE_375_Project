package domain;


import java.util.Collection;

public class ProgressCardManager {

    Bank bank;

    ProgressCardManager(Bank bank){
        this.bank = bank;
    }


    public Collection<ResourceType> playYearOfPlenty(
        Player player, ResourceType resourceOne, ResourceType resourceTwo) {

        boolean insufficientResources = false;
        if(resourceOne.equals(resourceTwo)){
            insufficientResources = bank.noMoreResource(resourceOne,2);
        }else{
            insufficientResources = bank.noMoreResource(resourceOne, 1) ||
                bank.noMoreResource(resourceTwo, 1);
        }
        if(!insufficientResources){
            try {
                player.addResource(resourceOne);
                player.addResource(resourceTwo);
            }catch(IllegalStateException e){
            }
        }
        return player.getResources();
    }

    public Collection<ResourceType> playMonopoly(
        Collection<Player> players, Player player, ResourceType resource) {
        for(Player checkPlayer: players){
            if(checkPlayer.equals(player)){
                continue;
            }
            while (checkPlayer.removeResource(resource)){
                player.addResource(resource);
            }
        }
        return player.getResources();
    }
}
