package ru.skillbench.tasks.basics.entity;

public class LocationImpl implements Location {

    private String locName;
    private Type locType;
    private Location locParent;

    public String getName() {
        return locName;
    }

    public void setName(String name) {
        locName = name;
    }

    public Type getType() {
        return locType;
    }

    public void setType(Type type) {
        locType = type;
    }

    public void setParent(Location parent) {
        locParent = parent;
    }

    public String getParentName() {
        if (locParent == null) {
            return "--";
        } else {
            return locParent.getName();
        }
    }

    public Location getTopLocation() {
        if (this.getParentName().equals("--")) {
            return this;
        } else {
            return locParent.getTopLocation();
        }
    }


    public boolean isCorrect() {
        if (this.getParentName().equals("--")) {
            return true;
        } else {
            if (this.locParent.getType().compareTo(this.getType()) < 0) {
                return locParent.isCorrect();
            } else {
                return false;
            }
        }
    }

    public String getAddress() {
        if (this.getParentName().equals("--")) {
            return this.getName();
        } else {
            int countMatches = this.getName().length() - this.getName().replace(" ", "").length();
            if (this.getName().indexOf('.') == -1 | countMatches > 1) {
                return this.getType().getNameForAddress() + this.getName() + ", " + locParent.getAddress();
            } else {
                return this.getName() + ", " + locParent.getAddress();
            }

        }
    }

    public String toString() {
        return this.getName() + " (" + this.getType() + ")";
    }

}
