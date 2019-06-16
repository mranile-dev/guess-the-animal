/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.game.guess.animal;

/**
 *
 * @author mrani
 */
public enum QEnum {
 
    canFly("canFly"), 
    canSwim("canSwim"), 
    liveInWater("liveInWater"), 
    aggressive("aggressive"), 
    venomous("venomous"), 
    pet("pet"),
    domesticated("pet"),
    uniqueChar("uniqueChar");
    
    private String question;
    
    private QEnum(String question){
        this.question = question;
    }
}
