package com.example.fabricfabcar.service;

import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.mapper.CarMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OffchainService {
    private CarMapper carMapper;
    public void insertCar(String carnumber, String make, String model, String colour, String owner){
        Car car = new Car();
        car.setKey(carnumber);
        car.setMake(make);
        car.setModel(model);
        car.setColour(colour);
        car.setOwner(owner);
        carMapper.insertCar(car);
    }
    public String hashCar(String carnumber, String make, String model, String colour, String owner) throws NoSuchAlgorithmException {
        // Hash the car information
        String json = "{ \"carnumber\": \"" + carnumber + "\", \"make\": \"" + make + "\", \"model\": \"" + model + "\", \"colour\": \"" + colour + "\", \"owner\": \"" + owner + "\" }";
        String hash = sha256(json);
        return hash;
    }
    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
