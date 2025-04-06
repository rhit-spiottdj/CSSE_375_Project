package domain;

import java.util.*;

public class Bank {

    public static final int MAX_INDIVIDUAL_RESOURCE = 19;
    public static final int MAX_KNIGHT_CARDS = 14;
    public static final int MAX_VICTORY_CARDS = 5;
    public static final int MAX_ROAD_CARDS = 2;
    public static final int MAX_PLENTY_CARDS = 2;
    public static final int MAX_MONOPOLY_CARDS = 2;
    public static final int REQUIRE_FOUR_TIMES_RESOURCES = 4;
    public static final int REQUIRE_THREE_TIMES_RESOURCES = 3;
    HashMap<ResourceType, Integer> resources = new HashMap<>();
    ArrayList<DevelopmentCards> developmentCards = new ArrayList<>();
    Random random;

    public Bank() {
        this.random = new Random();
        setupResources();
        setUpDevelopmentCards();
    }

    public boolean obtainResource(ResourceTransaction transaction) {
        if (noMoreResource(transaction)) {
            return false;
        }
        resources.put(transaction.resourceType, resources.get(transaction.resourceType) - transaction.amount);
        return true;
    }

    public boolean giveBackResource(ResourceTransaction transaction) {
        if (maxResources(transaction)) {
            return false;
        }
        resources.put(transaction.resourceType, resources.get(transaction.resourceType) + transaction.amount);
        return true;
    }

    private boolean maxResources(ResourceTransaction transaction) {
        return resources.get(transaction.resourceType) + transaction.amount > MAX_INDIVIDUAL_RESOURCE || transaction.amount < 1;
    }

    boolean noMoreResource(ResourceTransaction transaction) {
        return transaction.amount < 1 || resources.get(transaction.resourceType) < transaction.amount;
    }

    public boolean tradeResourcePort(Port port, ResourceType giving, ResourceType taking,
        int amount) {
        ResourceTransaction transaction = new ResourceTransaction(taking, amount);
        if (noMoreResource(transaction) || isTwoToOnePortOfDifferentResource(port, giving)){
            return false;
        }
        tradeResourcePortExchangeResources(port, giving, taking, amount);
        return true;
    }

    private boolean isTwoToOnePortOfDifferentResource(Port port, ResourceType giving){
        return port.getPortTradeRatio() == 2 &&
               port.getResourceType() != giving;
    }

    private void tradeResourcePortExchangeResources(Port port,
        ResourceType giving, ResourceType taking, int amount) {
        if (port.getPortTradeRatio() == 3) {
            exchangeResourcesInBank(giving, taking, amount, REQUIRE_THREE_TIMES_RESOURCES);
        } else {
            exchangeResourcesInBank(giving, taking, amount, 2);
        }
    }

    private void exchangeResourcesInBank(ResourceType giving, ResourceType taking, int amount,
        int ratio) {
        resources.put(taking, resources.get(taking) - amount);
        resources.put(giving, resources.get(giving) + (amount * ratio));
    }

    public boolean tradeResourceBank(ResourceType giving, ResourceType taking, int amount) {
        ResourceTransaction transaction = new ResourceTransaction(taking, amount);
        if (noMoreResource(transaction) || giving == taking) {
            return false;
        }
        exchangeResourcesInBank(giving, taking, amount, REQUIRE_FOUR_TIMES_RESOURCES);
        return true;
    }

    private void setUpDevelopmentCards() {
        setupDevCardsLoop(DevelopmentCards.KNIGHT, MAX_KNIGHT_CARDS);
        setupDevCardsLoop(DevelopmentCards.VICTORY, MAX_VICTORY_CARDS);
        setupDevCardsLoop(DevelopmentCards.ROAD, MAX_ROAD_CARDS);
        setupDevCardsLoop(DevelopmentCards.PLENTY, MAX_PLENTY_CARDS);
        setupDevCardsLoop(DevelopmentCards.MONOPOLY, MAX_MONOPOLY_CARDS);
    }

    private void setupDevCardsLoop(DevelopmentCards card, int max) {
        for (int i = 0; i < max; i++) {
            developmentCards.add(this.random.nextInt(developmentCards.size() + 1), card);
        }
    }

    private void setupResources() {
        resources.put(ResourceType.LUMBER, MAX_INDIVIDUAL_RESOURCE);
        resources.put(ResourceType.BRICK, MAX_INDIVIDUAL_RESOURCE);
        resources.put(ResourceType.WOOL, MAX_INDIVIDUAL_RESOURCE);
        resources.put(ResourceType.GRAIN, MAX_INDIVIDUAL_RESOURCE);
        resources.put(ResourceType.ORE, MAX_INDIVIDUAL_RESOURCE);
    }

    public boolean obtainDevelopmentCard(Player player) {
        if (developmentCards.isEmpty() || playerDoesntHaveResourcesForDevelopmentCard(player)) {
            return false;
        }
        handlePlayerBuysCard(player);
        return true;
    }

    private void handlePlayerBuysCard(Player player) {
        player.addDevelopmentCard(developmentCards.remove(0));
        Collection<ResourceType> resources =
            List.of(new ResourceType[]{ResourceType.ORE, ResourceType.GRAIN, ResourceType.WOOL});
        for(ResourceType resource: resources)   player.removeResource(resource);
    }

    private boolean playerDoesntHaveResourcesForDevelopmentCard(Player player) {
        Collection<ResourceType> resources =
            List.of(new ResourceType[]{ResourceType.ORE, ResourceType.GRAIN, ResourceType.WOOL});
        return !player.hasResources(resources);
    }

    public boolean playerTrade(Player player1, Player player2, Collection<ResourceType> resources1,
        Collection<ResourceType> resources2) {
        if (!player1.hasResources(resources1) || !player2.hasResources(resources2)) {
            return false;
        }
        playerTradeLogic(player1, player2, resources1, resources2);
        return true;
    }

    private void playerTradeLogic(Player player1, Player player2,
        Collection<ResourceType> resources1, Collection<ResourceType> resources2) {
        for (ResourceType resource: resources1) player1.removeResource(resource);
        for (ResourceType resource: resources2) player2.removeResource(resource);
        for (ResourceType resource: resources2) player1.addResource(resource);
        for (ResourceType resource: resources1) player2.addResource(resource);
    }

    List<DevelopmentCards> getDevelopmentCards() {
        return new ArrayList<>(developmentCards);
    }

    void setDevelopmentCards(ArrayList<DevelopmentCards> devCards) {
        developmentCards = new ArrayList<>(devCards);
    }

    public boolean playerDiscardResources(Player player, Collection<ResourceType> toDiscard) {
        if (!player.hasResources(toDiscard)) {
            return false;
        }
        return discardAll(player, toDiscard);
    }

    private boolean discardAll(Player player, Collection<ResourceType> toDiscard) {
        for (ResourceType resource : toDiscard) {
            if (unsuccessfulRemoveOrGibeBack(player, resource)) return false;
        }
        return true;
    }

    private boolean unsuccessfulRemoveOrGibeBack(Player player, ResourceType resource) {
        ResourceTransaction transaction = new ResourceTransaction(resource, 1);
        return !player.removeResource(resource) || !giveBackResource(transaction);
    }

    protected void setBankResource(ResourceTransaction transaction) {
        resources.put(transaction.resourceType, transaction.amount);
    }

    protected int getBankResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }
}