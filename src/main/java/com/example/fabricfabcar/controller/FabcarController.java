package com.example.fabricfabcar.controller;

import com.example.fabricfabcar.ECCUtils.EncryptAndDecrypt;
import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.mapper.CarMapper;
import com.example.fabricfabcar.service.FabcarService;
import com.example.fabricfabcar.service.OffchainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.bouncycastle.oer.its.HashAlgorithm.sha256;


@Controller
@RequestMapping("/car")
@Slf4j
@AllArgsConstructor
public class FabcarController {
    final Gateway gateway;
    final Contract contract;
    final FabcarService fabcarService;
    //final OffchainService offchainService;
    private CarMapper carMapper;


    /*根据key查询对应的car*/
    @GetMapping("/queryBykey")
    public String queryCarByKey(@RequestParam("key") String key, Model model) throws NoSuchAlgorithmException, JsonProcessingException {

        Map<String, Object> result = new HashMap<>();
        List<Car> car = fabcarService.selectCarinfoByKey(key);
        System.out.println("carinfo="+car);
        result.put("carinfo", car);
        String hash1 = fabcarService.queryCarByKey(key);
        System.out.println("hash1="+hash1);
        result.put("hash1", hash1);
        String hash2 = fabcarService.selectCarHashByKey(key);
        result.put("hash2", hash2);
        System.out.println("result="+result);
        /*ObjectMapper mapper = new ObjectMapper();
        JsonNode tree1 = mapper.readTree(hash1);
        JsonNode tree2 = mapper.readTree(hash2);*/
        if(hash1.equals(hash2)){
            result.put("result1","TRUE");
        }else {
            result.put("result1","FALSE");
        }
        model.addAllAttributes(result);
        return "aloneCar";
    }

    /*查询所有的car*/
    @GetMapping("/all")
    public String queryAllCar(Model model)  {

        Map<String, Object> result ;
        result = fabcarService.queryAllCars();
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "allCar";
    }

    /*新增一个car的资产*/
    @PostMapping("/add")
    public String createCar(@RequestParam("carnumber")String carnumber,@RequestParam("make")String make,
                                        @RequestParam("model")String model,@RequestParam("colour")String colour,
                                        @RequestParam("owner")String owner,Model model1) throws NoSuchAlgorithmException {

        String owner1 = EncryptAndDecrypt.encode(owner);
        System.out.println(owner1);
        fabcarService.insertCar(carnumber,make,model,colour,owner1);
        String carhash = fabcarService.hashCar(carnumber,make,model,colour,owner1);
        Map<String, Object> result;
        result = fabcarService.createCar(carnumber, carhash);
        System.out.println("result="+result);
        model1.addAllAttributes(result);

        return "create";
    }

    /*将一个car资产转移到另一个owner上*/
    @PostMapping("/update")
    public String updateCarOwner(@RequestParam("carnumber")String carnumber,@RequestParam("newowner")String newowner,Model model)  {
        Map<String, Object> result;
        result = fabcarService.updateCarOwner(carnumber,newowner);
        System.out.println("result="+result);
        model.addAllAttributes(result);

        return "transfer";
    }


}
