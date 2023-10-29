package oit.is.z2133.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},NULL,#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchinfo);

  @Select("SELECT id, user1, user2, isActive, from matchinfo where isActive = true;")
  ArrayList<MatchInfo> selectActiveMatchInfo();

}
