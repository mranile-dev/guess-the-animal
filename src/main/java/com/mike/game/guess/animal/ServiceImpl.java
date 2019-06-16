/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.game.guess.animal;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mrani
 */
@Path("/game/guess-animal")
public class ServiceImpl implements Service{

    private JSONObject animalsDS;
    private JSONObject questionsDS;
    
    public ServiceImpl(){
        try { 
            this.animalsDS = (JSONObject)(new JSONParser().parse(new FileReader(animalsDSFile)));
            this.questionsDS = (JSONObject)(new JSONParser().parse(new FileReader(questionsDSFile)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private JSONObject getAnimalsDS(){
        return this.animalsDS;
    }

    private JSONObject getQuestionsDS(){
        return this.questionsDS;
    }
    
    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAnimal(@PathParam("name") String name){
    	return Response.status(200).entity(findAnimal(name)).build();
    }
    
    @Override
    public boolean findAnimal(String name) {
        Map animals = ((Map)getAnimalsDS().get("Animals")); 
        Iterator iter = animals.keySet().iterator();
        while (iter.hasNext()){
            String animal = (String)iter.next();
            if (animal.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewAnimal(Animal animal){
    	saveNewAnimal(animal);
    	return Response.status(200).entity("success").build();
    }
    
    @SuppressWarnings("unchecked")
    public void saveNewAnimal(Animal animal) {
        
        JSONObject newAnimal = new JSONObject();
        newAnimal.put("canFly", animal.isCanFly());
        newAnimal.put("canSwim", animal.isCanSwim());
        newAnimal.put("liveInWater", animal.isLiveInWater());
        newAnimal.put("aggressive", animal.isAggressive());
        newAnimal.put("venomous", animal.isVenomous());
        newAnimal.put("pet", animal.isDomesticated());
        
        JSONArray uniqueChars = new JSONArray();
        animal.getUniqueChars().forEach(chars -> uniqueChars.add(chars));
        
        newAnimal.put("uniqueChar", uniqueChars);
        
        JSONObject animals = ((JSONObject)getAnimalsDS().get("Animals")); 
        animals.put(animal.getName(), newAnimal);
        
        getAnimalsDS().put("Animals", animals);
        
        try (FileWriter file = new FileWriter(animalsDSFile)) {
            file.write(getAnimalsDS().toJSONString());
        } catch (IOException e) {
            Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void getNextQuestion(QuestionAnswer qa) {
        Map q = ((Map)getQuestionsDS().get("Questions"));
        if (qa.getPossibleAnswers() == null){
            qa.setPossibleAnswers(loadAnimals());
            qa.setQuestionEnum(QEnum.canFly);
            qa.setCurrQuestion((String)q.get("canFly"));
        } else {
            final boolean userAns = qa.getUserAns();
            switch(qa.getQuestionEnum()){
                case canFly: {
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> a.isCanFly() == userAns)
                            .collect(Collectors.toList());
                    qa.setPossibleAnswers(anims);
                    qa.setQuestionEnum(QEnum.canSwim);
                    qa.setCurrQuestion((String)q.get("canSwim"));
                    break;
                }
                case canSwim: {
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> a.isCanSwim() == userAns)
                            .collect(Collectors.toList());
                    qa.setPossibleAnswers(anims);
                    qa.setQuestionEnum(QEnum.liveInWater);
                    qa.setCurrQuestion((String)q.get("liveInWater"));
                    break;
                }
                case liveInWater: {
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> a.isLiveInWater() == userAns)
                            .collect(Collectors.toList());
                    qa.setPossibleAnswers(anims);
                    qa.setQuestionEnum(QEnum.aggressive);
                    qa.setCurrQuestion((String)q.get("aggressive"));
                    break;
                }
                case aggressive: {
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> a.isAggressive() == userAns)
                            .collect(Collectors.toList());
                    qa.setPossibleAnswers(anims);
                    qa.setQuestionEnum(QEnum.venomous);
                    qa.setCurrQuestion((String)q.get("venomous"));
                    break;
                }
                case venomous: {
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> a.isVenomous() == userAns)
                            .collect(Collectors.toList());
                    
                    if (anims.size() == 0){
                        generateAnswer(anims, qa);
                        break;
                    }

                    qa.setPossibleAnswers(anims);
                    qa.setQuestionEnum(QEnum.pet);
                    qa.setCurrQuestion((String)q.get("pet"));
                    break;
                }
                case pet: {
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> a.isDomesticated() == userAns)
                            .collect(Collectors.toList());                                                         

                    if (anims.size() == 0){
                        generateAnswer(anims, qa);
                        break;
                    }
                    
                    qa.setPossibleAnswers(anims);
                    qa.setUniqueCharQuestion(anims.get(0).getUniqueChars().get(0));
                    qa.setUniqueCharIdx(0);
                    qa.setQuestionEnum(QEnum.uniqueChar);
                    qa.setCurrQuestion(MessageFormat.format(((String)q.get("uniqueChar1")), qa.getUniqueCharQuestion()));
                    break;
                }
                default: {
                    if (userAns){
                        List<Animal> anims = qa.getPossibleAnswers().stream()
                                .filter(a -> a.getUniqueChars().contains(qa.getUniqueCharQuestion()))
                                .collect(Collectors.toList());

                        if (anims.size() == 1){
                            generateAnswer(anims, qa);
                            break;
                        } 
                        
                        int idx = qa.getUniqueCharIdx()+1;
                        
                        qa.setUniqueCharQuestion(anims.get(0).getUniqueChars().get(idx));
                        qa.setPossibleAnswers(anims);
                        qa.setQuestionEnum(QEnum.uniqueChar);
                        qa.setCurrQuestion(MessageFormat.format((String)q.get("uniqueChar"+idx), qa.getUniqueCharQuestion()));
                        qa.setUniqueCharIdx(idx);
                        
                        break;
                    }
                    
                    List<Animal> anims = qa.getPossibleAnswers().stream()
                            .filter(a -> !a.getUniqueChars().contains(qa.getUniqueCharQuestion()))
                            .collect(Collectors.toList());

                    if (anims.size() == 1){
                        generateAnswer(anims, qa);
                        break;
                    } 

                    int idx = qa.getUniqueCharIdx()+1;
                    
                    qa.setPossibleAnswers(anims);
                    qa.setUniqueCharQuestion(anims.get(0).getUniqueChars().get(idx));
                    qa.setUniqueCharIdx(0);
                    qa.setQuestionEnum(QEnum.uniqueChar);
                    qa.setCurrQuestion(MessageFormat.format(((String)q.get("uniqueChar"+idx)), qa.getUniqueCharQuestion()));
                    break;
                }
            }
        }
    }

    private void generateAnswer(List<Animal> anims, QuestionAnswer qa) {
        //we have an answer
        if (anims.size() == 0){
            qa.setAnswerText("I can't guess the animal !!");
            qa.setAnswerDesc("Please enter this animal into the system.");
            qa.setFoundAnswer(true);
        } 
        if (anims.size() == 1){
            Animal animal = anims.get(0);

            String answerText = MessageFormat.format("Your animal is a {0} !!", animal.getName());

            StringBuffer answerDesc = new StringBuffer();
            answerDesc.append(MessageFormat.format("Is a {0}, ", animal.getUniqueChars().get(0)));

            if (animal.isCanFly()) answerDesc.append("can fly, ");
            if (animal.isCanSwim()) answerDesc.append("can Swim, ");
            if (animal.isAggressive()) answerDesc.append("is aggressive, ");
            if (animal.isVenomous()) answerDesc.append("is venomous, ");
            if (animal.isDomesticated()) answerDesc.append("can be a pet, ");

            answerDesc.append(animal.getUniqueChars().get(1)+" and ");
            answerDesc.append(animal.getUniqueChars().get(2)+".");

            qa.setPossibleAnswers(anims);
            qa.setAnswerText(answerText);
            qa.setAnswerDesc(answerDesc.toString());
            qa.setFoundAnswer(true);
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<Animal> loadAnimals(){
        List<Animal> results = new ArrayList<Animal>();
        JSONObject animals = ((JSONObject)getAnimalsDS().get("Animals")); 
        animals.forEach((k1, v1) -> {
            Animal animal = new Animal();
            animal.setName(k1.toString());
            JSONObject ani = (JSONObject)v1;
            ani.forEach((k2, v2) -> {
                QEnum q = QEnum.valueOf(k2.toString());
                switch(q){
                    case canFly:
                        animal.setCanFly((Boolean)v2);
                        break;
                    case canSwim:
                        animal.setCanSwim((Boolean)v2);
                        break;
                    case liveInWater:
                        animal.setLiveInWater((Boolean)v2);
                        break;
                    case aggressive:
                        animal.setAggressive((Boolean)v2);
                        break;
                    case venomous:
                        animal.setVenomous((Boolean)v2);
                        break;
                    case pet:
                        animal.setDomesticated((Boolean)v2);
                        break;
                    default:
                        JSONArray chars = (JSONArray)v2;
                        chars.forEach(uc -> animal.getUniqueChars().add((String)uc));
                        break;
                }
            });
            
            results.add(animal);
        });
        
        return results;
    }
    
}
