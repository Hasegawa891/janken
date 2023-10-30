package oit.is.z2133.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2133.kaizi.janken.model.Match;
import oit.is.z2133.kaizi.janken.model.MatchMapper;
import oit.is.z2133.kaizi.janken.model.MatchInfo;
import oit.is.z2133.kaizi.janken.model.MatchInfoMapper;

@Service
public class AsyncKekka {

  boolean dbUpdated = false;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;
  @Autowired
  MatchInfoMapper matchInfoMapper;

@Transactional
public int syncActiveMatch() {
  // 探索対象のフルーツを取得
  ArrayList<Match> matchList = matchMapper.selectAllByMatch();
  int flag = 0;

  for (Match temp : matchList) {
    if (temp.isActive() == true) {
      flag = 1;
    }
  }

  // 非同期でDBのisActiveにtrue存在していることを共有する際に利用する
  if (flag == 1) {
    this.dbUpdated = true;
    return 1;
  }

  return 0;
}

  public Match syncShowActiveMatch() {
    return matchMapper.selectActiveMatch();
  }

  @Async
  public void asyncShowFruitsList(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);

          continue;
        }
        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする


        Match result = this.syncShowActiveMatch();
        emitter.send(result);
        TimeUnit.MILLISECONDS.sleep(1000);
        matchInfoMapper.updateByActiveMatchInfo();
         dbUpdated = false;
         matchMapper.updateByActiveMatch();
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncShowFruitsList complete");
  }


}
