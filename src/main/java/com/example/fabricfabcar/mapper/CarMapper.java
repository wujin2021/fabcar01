package com.example.fabricfabcar.mapper;
import com.example.fabricfabcar.domain.Car;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface CarMapper {
    @Select("select * from cars")
    List<Car> getAll();

    @Select("select `key`,`make`,`model`,`colour`,`owner` from cars where `key` = #{key}")
    public List<Car> selectBykey(@Param("key")String key);

    @Insert({"insert into cars(`key`, `make`, `model`, `colour`, `owner`) values (#{key}, #{make}, #{model}, #{colour}, #{owner})"})
    //@Options(useGeneratedKeys = true, keyProperty = "id")
    // String key, String make, String model, String colour, String owner
    void insertCar(Car car);
    /*public List<Car> insertCar(@Param("key")String key, @Param("make")String make,
                           @Param("model")String model, @Param("colour")String colour,
                           @Param("owner")String owner);*/
}
