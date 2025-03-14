package domain;

public abstract class Structure {

    public Player owner = null;

    public abstract void distributeResources(ResourceType resourceType);

    public abstract int getVictoryPoints();

    protected void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

}