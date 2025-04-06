package domain;

public class Port {

    int ratio;

    ResourceType resource;

    public Port(int ratio, ResourceType resource){
        this.ratio = ratio;
        this.resource = resource;
    }

    public int getPortTradeRatio(){
        return ratio;
    }

    public ResourceType getResourceType(){
        return resource;
    }
}
