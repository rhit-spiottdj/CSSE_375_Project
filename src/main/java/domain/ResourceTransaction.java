package domain;

import java.util.Objects;

public class ResourceTransaction {

    public ResourceType resourceType;
    public int amount;

    public ResourceTransaction(ResourceType resourceType, int amount) {
        this.resourceType = resourceType;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceTransaction)) return false;
        ResourceTransaction that = (ResourceTransaction) o;
        return amount == that.amount &&
                resourceType == that.resourceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceType, amount);
    }

    public void setResourceType(ResourceType newResourceType) { this.resourceType = newResourceType; }

    public void setAmount(int newAmount) {  this.amount = newAmount; }

    public ResourceType getResourceType() { return this.resourceType; }

    public int getAmount() { return this.amount; }
}
