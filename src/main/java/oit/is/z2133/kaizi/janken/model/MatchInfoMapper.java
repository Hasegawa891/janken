package oit.is.z2133.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},NULL, 'true');")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchinfo);

  @Insert("INSERT INTO matchinfo (user2Hand) VALUES (#{user1Hand});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertUser2HandMatchInfo(MatchInfo matchInfo);

  @Select("SELECT * from matchInfo WHERE user2 = #{user2} AND isActive = true")
  MatchInfo selectMyMatch(int user2);

  @Select("SELECT id, user1, user2, isActive from matchinfo where isActive = true;")
  ArrayList<MatchInfo> selectActiveMatchInfo();

  @Select("SELECT * from matchinfo")
  ArrayList<MatchInfo> selectAllMatchInfo();

  @Select("SELECT user2, from matchinfo where isActive = true;")
  ArrayList<MatchInfo> selectMyIdMatchInfo();

  @Update("UPDATE matchInfo SET user2Hand = #{user1Hand}  where isActive = true")
  void updateUser2HandMatchInfo(MatchInfo matchInfo);

   @Update("UPDATE matchInfo SET isActive = 'false' WHERE isActive = true")
  void updateByActiveMatchInfo();
}
