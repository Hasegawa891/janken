package oit.is.z2133.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2133.kaizi.janken.model.Janken;
import oit.is.z2133.kaizi.janken.model.Entry;
import oit.is.z2133.kaizi.janken.model.User;
import oit.is.z2133.kaizi.janken.model.UserMapper;
import oit.is.z2133.kaizi.janken.service.AsyncKekka;
import oit.is.z2133.kaizi.janken.model.Match;
import oit.is.z2133.kaizi.janken.model.MatchMapper;
import oit.is.z2133.kaizi.janken.model.MatchInfo;
import oit.is.z2133.kaizi.janken.model.MatchInfoMapper;

/**
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

    /*@Autowired
    private Entry entry;*/
    @Autowired
    UserMapper userMapper;
    @Autowired
    MatchMapper matchMapper;
    @Autowired
    MatchInfoMapper matchinfoMapper;

    @Autowired
    AsyncKekka kekka;

  /*@GetMapping("/janken")
  public String janken1(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    String name = prin.getName();
    name = "Hi " + name;

    ArrayList<User> AllUserName = userMapper.selectAllByName();
    model.addAttribute("AlluserName", AllUserName);
    this.entry.addUser(loginUser);
    model.addAttribute("name", name);
    model.addAttribute("entry", this.entry);

    return "janken.html";
  }*/

    @GetMapping("/janken")
    public String janken1(Principal prin, ModelMap model) {
      String name = prin.getName();
      name = "Hi " + name;

      ArrayList<Match> AllResults = matchMapper.selectAllByMatch();
      ArrayList<User> AllUsers = userMapper.selectAllByUsers();

      ArrayList<MatchInfo> active = matchinfoMapper.selectActiveMatchInfo();

      model.addAttribute("AllResults", AllResults);
      model.addAttribute("AllUsers", AllUsers);
      model.addAttribute("Active", active);
      model.addAttribute("name", name);

      return "janken.html";
  }

  /*@GetMapping("/match/{myhand}")
  public String janken1(@PathVariable String myhand, ModelMap model) {

    Janken janken = new Janken(myhand);
    String cpuhand = "相手の手 Gu";
    String result = "結果 " + janken.getresult();

    model.addAttribute("myhand", "あなたの手 " + myhand);
    model.addAttribute("cpuhand", cpuhand);
    model.addAttribute("result", result);
    return "match.html";
  }*/

  @GetMapping("/match")
  public String match(@RequestParam int id, Principal prin, ModelMap model) {

    User User = userMapper.selectName(id);
    String name = prin.getName();

    model.addAttribute("name", name);
    model.addAttribute("User", User);
    return "match.html";
  }

  @GetMapping("/match/{myHand}")
  public String Hand(@PathVariable String myHand, ModelMap model) {

    Janken janken = new Janken(myHand);
    String result = janken.getresult();
    model.addAttribute("myHand", myHand);
    model.addAttribute("cpuHand", janken.getcpuhand());
    model.addAttribute("result", result);
    return "match.html";
  }

  @GetMapping("/fight")
  @Transactional
  public String Game(@RequestParam String myHand, @RequestParam int id, Principal prin, ModelMap model) {

    String name = prin.getName();
    User User1 = userMapper.selectName(id);
    User User2 = userMapper.selectId(name);

    Janken janken = new Janken(myHand);

    Match match = new Match();
    match.setUser1(id);
    match.setUser2(User2.getId());
    match.setUser1Hand(janken.getcpuhand());
    match.setUser2Hand(janken.getmyhand());

    matchMapper.insertMatch(match);

    model.addAttribute("name", name);
    model.addAttribute("User", User1);

    model.addAttribute("myHand", "あなたの手 " + myHand);
    model.addAttribute("cpuHand", "相手の手 " + janken.getcpuhand());
    model.addAttribute("result", "結果 " + janken.getresult());

    return "match.html";
  }

  @GetMapping("/wait")
  @Transactional
  public String wait(@RequestParam String myHand, @RequestParam int id, Principal prin, ModelMap model) {

    String name = prin.getName();
    User User1 = userMapper.selectId(name);

    Janken janken = new Janken(myHand);

    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(User1.getId());
    matchinfo.setUser2(id);
    matchinfo.setUser1Hand(janken.getmyhand());
    matchinfo.setActive(true);

    ArrayList<MatchInfo> matchInfoList = matchinfoMapper.selectMyIdMatchInfo();

    int flag = 0;

    for (MatchInfo temp : matchInfoList) {
      if (temp.getUser2() == matchinfo.getUser1()) {
        flag = 1;
      }

    }

    if (flag == 1) {
      matchinfoMapper.updateUser2HandMatchInfo(matchinfo);
      matchMapper.insertActiveMatchInfo(matchinfoMapper.selectMyMatch(User1.getId()));

    } else {
      matchinfoMapper.insertMatchInfo(matchinfo);
    }

    model.addAttribute("name", "Hi " + name);
    /*
    model.addAttribute("myHand", "あなたの手 " + myHand);
    model.addAttribute("cpuHand", "相手の手 " + janken.getcpuhand());
    model.addAttribute("result", "結果 " + janken.getresult());*/

    int a = this.kekka.syncActiveMatch();

    if(a == 1){
      Match result = matchMapper.selectActiveMatch();
      model.addAttribute("result", result);
    }

    return "wait.html";
  }

    @GetMapping("/kekka")
  public SseEmitter wait1() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.kekka.asyncShowFruitsList(sseEmitter);
    return sseEmitter;
  }
}
