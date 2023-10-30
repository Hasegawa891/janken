package oit.is.z2133.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

  @Select("SELECT * from matches")
  ArrayList<Match> selectAllByMatch();

  @Select("SELECT id, user1, user2, user1Hand, user2Hand from matches WHERE isActive = true")
  Match selectActiveMatch();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},'TRUE');")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertActiveMatchInfo(MatchInfo matchInfo);

  @Update("UPDATE matches SET isActive = 'false' WHERE isActive = true")
  void updateByActiveMatch();

}
