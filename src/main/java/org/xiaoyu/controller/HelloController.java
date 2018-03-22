package org.xiaoyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoyu.properties.GirlProperties;

/**
 * Created by K on 2018/2/2.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

  @Autowired
  private GirlProperties girlProperties;

//  @RequestMapping(value = "/say", method = RequestMethod.GET)
  @GetMapping(value = "/say")
  public String say(){
    return girlProperties.getCupSize();
  }
}
