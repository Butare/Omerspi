package jim.omerspi.model;
// Generated Sep 1, 2013 10:25:34 AM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Items generated by hbm2java
 */
public class Items implements java.io.Serializable {

    private Integer itemId;
    private Categorytype categorytype;
    private String itemName;
    private Integer minimumQty;
    private boolean isAllowed;
    private int totalQuantity;
    private Set<RequestedItems> requestedItemses = new HashSet<RequestedItems>(0);
    private Set<Stationaryregistration> stationaryregistrations = new HashSet<Stationaryregistration>(0);

    public Items() {
    }

    public Items(Categorytype categorytype, String itemName, boolean isAllowed) {
        this.categorytype = categorytype;
        this.itemName = itemName;
        this.isAllowed = isAllowed;
    }

    public Items(Categorytype categorytype, String itemName, Integer minimumQty, int totalQuantity, boolean isAllowed, Set<RequestedItems> requestedItemses, Set<Stationaryregistration> stationaryregistrations) {
        this.categorytype = categorytype;
        this.itemName = itemName;
        this.minimumQty = minimumQty;
        this.isAllowed = isAllowed;
        this.totalQuantity = totalQuantity;
        this.requestedItemses = requestedItemses;
        this.stationaryregistrations = stationaryregistrations;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Categorytype getCategorytype() {
        return this.categorytype;
    }

    public void setCategorytype(Categorytype categorytype) {
        this.categorytype = categorytype;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getMinimumQty() {
        return this.minimumQty;
    }

    public void setMinimumQty(Integer minimumQty) {
        this.minimumQty = minimumQty;
    }

    public boolean isIsAllowed() {
        return this.isAllowed;
    }

    public void setIsAllowed(boolean isAllowed) {
        this.isAllowed = isAllowed;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Set<RequestedItems> getRequestedItemses() {
        return this.requestedItemses;
    }

    public void setRequestedItemses(Set<RequestedItems> requestedItemses) {
        this.requestedItemses = requestedItemses;
    }

    public Set<Stationaryregistration> getStationaryregistrations() {
        return this.stationaryregistrations;
    }

    public void setStationaryregistrations(Set<Stationaryregistration> stationaryregistrations) {
        this.stationaryregistrations = stationaryregistrations;
    }
}