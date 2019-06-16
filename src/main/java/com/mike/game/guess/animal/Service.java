/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.game.guess.animal;

import java.util.List;
import java.util.Map;

/**
 *
 * @author mrani
 */
interface Service {
    
    public static final String animalsDSFile = "/animals-datasource.json";
    public static final String questionsDSFile = "/questions-datasource.json";
    
    public boolean findAnimal(String name);
    public void saveNewAnimal(Animal animal);
    public void getNextQuestion(QuestionAnswer qa);
    public List<Animal> loadAnimals();
}
