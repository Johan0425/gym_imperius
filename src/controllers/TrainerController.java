/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.TrainerDao;
import java.util.ArrayList;
import model.Trainer;

/**
 *
 * @author joanp
 */
public class TrainerController {
    
     private final TrainerDao trainerdao;

    public TrainerController() {
        trainerdao = new TrainerDao();
    }

    public ArrayList<Object> listTrainers() {
        return trainerdao.listEntity();
    }

    public Trainer selectTrainer(String cedula) {
        return (Trainer) trainerdao.selectEntity(cedula);
    }

    public void insertTrainer(Trainer trainer) {
        trainerdao.insertEntity(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerdao.updateEntity(trainer);
    }

    public void deleteTrainer(String cedula) {
        trainerdao.deleteEntity(cedula);
    }
    
}
