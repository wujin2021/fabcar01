package com.example.fabricfabcar.controller;

import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.mapper.CarMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/car")
@Slf4j
@AllArgsConstructor
public class offchainController {

    /*新增一个car的资产到数据库*/

    private CarMapper carMapper;
    @PostMapping("/input")
    public String inputCar(@RequestParam("carnumber")String carnumber, @RequestParam("make")String make,
                            @RequestParam("model")String model, @RequestParam("colour")String colour,
                            @RequestParam("owner")String owner){//        , Model model1
        //Map<String, Object> result;
        Car car = new Car();
        car.setKey(carnumber);
        car.setMake(make);
        car.setModel(model);
        car.setColour(colour);
        car.setOwner(owner);
        carMapper.insertCar(car);
//        model1.addAllAttributes(result);

        return "create";
    }
}
