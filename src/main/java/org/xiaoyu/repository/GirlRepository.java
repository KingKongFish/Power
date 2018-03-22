package org.xiaoyu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xiaoyu.domain.Girl;

import java.util.List;

/**
 * Created by K on 2018/2/5.
 */
public interface GirlRepository extends JpaRepository<Girl, Integer> {

  //通过年龄来查询
  public List<Girl> findByAge(Integer age);
}
