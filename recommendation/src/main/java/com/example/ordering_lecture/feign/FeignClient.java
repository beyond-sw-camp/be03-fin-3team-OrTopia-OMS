package com.example.ordering_lecture.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.cloud.openfeign.FeignClient(name = "item-service", url = "localhost:8080", configuration = MyFeignClientConfig.class)
public interface FeignClient {

    @GetMapping(value="/item-service/item/{id}/imagePath")
    String getImagePath(@PathVariable("id") Long itemId);
}
