package com.mike.game.guess.animal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Animal implements Serializable {

    private String name;

    private boolean canFly;
    private boolean canSwim;
    private boolean liveInWater;
    private boolean aggressive;
    private boolean venomous;
    private boolean domesticated;
    
    private List<String> uniqueChars = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean isCanSwim() {
        return canSwim;
    }

    public void setCanSwim(boolean canSwim) {
        this.canSwim = canSwim;
    }

    public boolean isLiveInWater() {
        return liveInWater;
    }

    public void setLiveInWater(boolean liveInWater) {
        this.liveInWater = liveInWater;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    public boolean isVenomous() {
        return venomous;
    }

    public void setVenomous(boolean venomous) {
        this.venomous = venomous;
    }

    public boolean isDomesticated() {
        return domesticated;
    }

    public void setDomesticated(boolean domesticated) {
        this.domesticated = domesticated;
    }

    public List<String> getUniqueChars() {
        return uniqueChars;
    }

    public void setUniqueChars(List<String> uniqueChars) {
        this.uniqueChars = uniqueChars;
    }
    
    
}
