package org.xiaoyu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.xiaoyu.aspect.HttpAspect;
import org.xiaoyu.domain.Girl;
import org.xiaoyu.repository.GirlRepository;
import org.xiaoyu.service.GirlService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by K on 2018/2/5.
 */
@RestController
public class GirlController {

  private final static Logger logger = LoggerFactory.getLogger(GirlController.class);

  @Autowired
  private GirlRepository girlRepository;

  @Autowired
  private GirlService girlService;

  /**
   * 查询所有女生列表
   * @return
   */
  @GetMapping(value = "/girls")
  public List<Girl> girlList() {
    logger.info("girlList");
    return girlRepository.findAll();
  }

  /**
   * 添加一个女生
   * @return
   * 加 @Valid 注解是为了验证 Girl 这个对象，通过 BindingResult 获取验证之后的结果
   */
  @PostMapping(value = "/girls")
  public Girl girlAdd(@Valid Girl girl, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
      System.out.println(bindingResult.getFieldError().getDefaultMessage());
      return null;
    }
    girl.setCupSize(girl.getCupSize());
    girl.setAge(girl.getAge());

    return girlRepository.save(girl);
  }

  //查询一个女生
  @GetMapping(value = "/girls/{id}")
  public Girl girlFindOne(@PathVariable("id") Integer id) {
    return girlRepository.findOne(id);
  }

  //更新一个女生
  @PutMapping(value = "/girls/{id}")
  public Girl girlUpdate(@PathVariable("id") Integer id,
                         @RequestParam("cupSize") String cupSize,
                         @RequestParam("age") Integer age) {
    Girl girl = new Girl();
    girl.setId(id);
    girl.setCupSize(cupSize);
    girl.setAge(age);

    return girlRepository.save(girl);
  }

  //删除一个女生
  @DeleteMapping(value = "/girls/{id}")
  public void girlDelete(@PathVariable("id") Integer id) {
    girlRepository.delete(id);
  }

  //通过年龄查询女生列表
  @GetMapping(value = "/girls/age/{age}")
  public List<Girl> girlListByAge(@PathVariable("age") Integer age) {
    return girlRepository.findByAge(age);
  }

  @PostMapping(value = "/girls/two")
  public void girlTwo() {
    girlService.insertTwo();
  }
}
