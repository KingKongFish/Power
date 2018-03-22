package org.xiaoyu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaoyu.domain.Girl;
import org.xiaoyu.repository.GirlRepository;

/**
 * Created by K on 2018/2/5.
 */
@Service
public class GirlService {

  @Autowired
  private GirlRepository girlRepository;

  //启用事务
  @Transactional
  public void insertTwo(){
    Girl girlA = new Girl();
    girlA.setCupSize("A");
    girlA.setAge(18);
    girlRepository.save(girlA);

    Girl girlB = new Girl();
    girlB.setCupSize("B");
    girlB.setAge(19);
    girlRepository.save(girlB);
  }
}
