package com.example.fabricfabcar;

import com.example.fabricfabcar.ECCUtils.EncryptAndDecrypt;
import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.mapper.CarMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

@SpringBootTest
class FabricFabcarApplicationTests {
//    final HyperLedgerFabricGatewayJavaConfig config = new HyperLedgerFabricGatewayJavaConfig();
//
//     FabcarService fabcarService;
//
//    @Test
//    void contextLoads() {
//    }
//    @Test
//    void aa(){
//        {
//            try {
//                Gateway gateway = config.gateway();
//                Network network = config.getNwork(gateway);
//                Contract contract = config.fabcar(network);
//                fabcarService = new FabcarService(gateway,contract,network);
//                System.out.println(fabcarService.getChain());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    }

    /*@Test
    void test1(){
        EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt();
        String planttext = encryptAndDecrypt.encrypt("123456","1");
        String text= encryptAndDecrypt.decrypt(planttext,"2");*/
    @Autowired
    private CarMapper carMapper;


    @Test
    public void testGetAll(){
        List<Car> list = carMapper.getAll();
        System.out.println(list);

    }

    @Test
    public void testSelectBykey(){
        String key = "CAR101";
        List<Car> cars = carMapper.selectBykey(key);
        System.out.println(cars);
        for (Car car : cars) {
            if (car.getKey().equals("CAR101")) {
                String owner = car.getOwner();
                System.out.println(owner);
                break;
            }
        }
        for (Car car : cars) {
            if (car.getKey().equals("CAR101")) {
                car.setOwner("wujin");
                break;
            }
        }
    }
    @Test
    public void testSelectCarinfoByKey(){
        String key = "CAR022";

    }

/*    @Test
    public void testInsertCar(){
        String key = "CAR500";
        String make = "BMW";
        String model = "X7";
        String colour = "white";
        String owner= "Wujin";

        Car car = new Car();
        car.setKey(key);
        car.setMake(make);
        car.setModel(model);
        car.setColour(colour);
        car.setOwner(owner);
        //carMapper.insertCar(key, make, model, colour, owner);
        carMapper.insertCar(car);
        List<Car> list = carMapper.getAll();
        System.out.println(list);
    }*/
    /*@Test
    @Controller
    @RequestMapping("/car")
    @Slf4j
    @AllArgsConstructor
    public void test1(){
        @PostMapping("/add")
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

        }
    }*/



}
