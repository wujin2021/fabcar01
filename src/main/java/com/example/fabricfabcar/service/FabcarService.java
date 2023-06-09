package com.example.fabricfabcar.service;


import com.example.fabricfabcar.ECCUtils.EncryptAndDecrypt;
import com.example.fabricfabcar.domain.Car;
import com.example.fabricfabcar.mapper.CarMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
@Service
@AllArgsConstructor
public class FabcarService {

    final Gateway gateway;
    final Contract contract;
    final Network network;


    /*public Map<String, Object> queryCarByKey(String key) {
        Map<String, Object> result = new HashMap<>();
        try {
            byte[] queryResult = contract.evaluateTransaction("queryCar", key);
            result.put("payload", StringUtils.newStringUtf8(queryResult));
            result.put("status", "ok");
        } catch (ContractException e) {
            e.printStackTrace();
            result.put("status", "error");
        }
        return result;
    }*/
    public String queryCarByKey(String key) {
        String result = null;
        try {
            byte[] queryResult = contract.evaluateTransaction("queryCar", key);
            String jsonString = StringUtils.newStringUtf8(queryResult);
            // 创建一个 Gson 对象
            Gson gson = new Gson();
            // 将 JSON 字符串转换为 JsonObject 对象
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            // 获取其中的 carhash 属性的值
            result = jsonObject.get("carhash").getAsString();
        } catch (ContractException e) {
            e.printStackTrace();
            return "error";
        }
        return result;
    }
    public String selectCarHashByKey(String key) throws NoSuchAlgorithmException {
        String result = null;
        List<Car> list = carMapper.selectBykey(key);
        System.out.println(list);
        // 创建一个Gson实例
        Gson gson = new Gson();
        // 将List对象转换为JSON字符串
        String json = gson.toJson(list);
        // 打印JSON字符串
        System.out.println(json);
        result = sha256(json);
        //result = "{\"carhash\":\""+hash +"\"}";
        System.out.println("hash2="+result);
        return result;
    }
    public List<Car> selectCarinfoByKey(String key){
        List<Car> cars = carMapper.selectBykey(key);
        System.out.println(cars);
        for (Car car : cars) {
            if (car.getKey().equals(key)) {
                System.out.println(car.getOwner());
                String owner = EncryptAndDecrypt.decode(car.getOwner());
                car.setOwner(owner);
                System.out.println(cars);
                break;
            }
        }
        System.out.println(cars);
        return cars;
    }

    public Map<String, Object> queryAllCars() {
        Map<String, Object> result = new HashMap<>();
        try {
            byte[] queryResult = contract.evaluateTransaction("queryAllCars");
            result.put("payload", StringUtils.newStringUtf8(queryResult));
            result.put("status", "ok");
        } catch (ContractException e) {
            result.put("status", "error");
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> createCar(String carnumber, String carhash) {
        Map<String, Object> result = new HashMap<>();
//        String carnumber1 = EncryptAndDecrypt.encode(carnumber);
//        String make1 = EncryptAndDecrypt.encode(make);
//        String model1 = EncryptAndDecrypt.encode(model);
//        String colour1 = EncryptAndDecrypt.encode(colour);
//        String owner1 = EncryptAndDecrypt.encode(owner);
        try {
            byte[] transaction = contract.createTransaction("createCar")
                    .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                    .submit(carnumber, carhash);
            result.put("payload", "添加成功");
            result.put("status", "ok");
        } catch (ContractException e) {
            e.printStackTrace();
            result.put("status", "error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.put("status", "error");
        } catch (TimeoutException e) {
            e.printStackTrace();
            result.put("status", "error");
        }
        return result;
    }

   /* public Map<String, Object> updateCarOwner(String carnumber, String newowner) {
        Map<String, Object> result = new HashMap<>();
        try {
            byte[] transaction = contract.submitTransaction("changeCarOwner", carnumber, newowner);
            result.put("payload", StringUtils.newStringUtf8(contract.evaluateTransaction("queryCar", carnumber)));
            result.put("status", "ok");
        } catch (ContractException e) {
            e.printStackTrace();
            result.put("status", "error");
        } catch (TimeoutException e) {
            e.printStackTrace();
            result.put("status", "error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.put("status", "error");
        }
        return result;
    }*/
   public Map<String, Object> updateCarOwner(String carnumber, String newowner) {
       Map<String, Object> result = new HashMap<>();
       try {
           byte[] transaction = contract.submitTransaction("changeCarOwner", carnumber, newowner);
           result.put("payload", StringUtils.newStringUtf8(contract.evaluateTransaction("queryCar", carnumber)));
           result.put("status", "ok");
       } catch (ContractException e) {
           e.printStackTrace();
           result.put("status", "error");
       } catch (TimeoutException e) {
           e.printStackTrace();
           result.put("status", "error");
       } catch (InterruptedException e) {
           e.printStackTrace();
           result.put("status", "error");
       }
       return result;
   }
    public String getChain() {
        BlockchainInfo blockchainInfo;
        try {
            blockchainInfo = network.getChannel().queryBlockchainInfo();

        } catch (ProposalException e) {
            throw new RuntimeException(e);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        }
        return blockchainInfo.getBlockchainInfo().toString();
    }

    private CarMapper carMapper;

    public void insertCar(String carnumber, String make, String model, String colour, String owner) {
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
        //String json = "[{\"key\":\""+carnumber +"\",\"colour\":\""+colour+"\",\"make\":\""+make+"\",\"owner\":\""+owner+"\",\"model\":\""+model+"\"}]";
        String json = "[{\"key\":\""+carnumber +"\",\"make\":\""+make+"\",\"model\":\""+model+"\",\"colour\":\""+colour+"\",\"owner\":\""+owner+"\"}]";
        String hash = sha256(json);
        System.out.println(json);
        System.out.println(hash);
        return hash;
    }

    public String sha256(String input) throws NoSuchAlgorithmException {
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
