package domain;

public class Port {

    PortTradeRatio ratio;

    ResourceType resource;

    public Port(PortTradeRatio ratio, ResourceType resource){
        this.ratio = ratio;
        this.resource = resource;
    }

    public PortTradeRatio getPortTradeRatio(){
        return ratio;
    }

    public ResourceType getResourceType(){
        return resource;
    }
}
