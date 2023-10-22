package oit.is.z2133.kaizi.janken.model;

import java.util.ArrayList;

//import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from users")
  ArrayList<User> selectAllByUsers();

  @Select("SELECT * from users WHERE id=#{id}")
  User selectName(int id);

  @Select("SELECT id from users WHERE name=#{name}")
  User selectId(String name);
}
