package bh.app.chronomicon.model.enums;

public enum Role {
    USER(10),
    ADMIN(11),
    MASTER(12);
    
    private final int hierarchyLevel;
    
    Role(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }
    
    public int getHierarchyLevel() {
        return hierarchyLevel;
    }
    
    public boolean isHigherThan(Role otherRole){
        return this.getHierarchyLevel() > otherRole.getHierarchyLevel();
    }
    
    public boolean isHigherOrEqualThan(Role otherRole){
        return this.getHierarchyLevel() >= otherRole.getHierarchyLevel();
    }
}
