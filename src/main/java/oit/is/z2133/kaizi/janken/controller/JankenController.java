package oit.is.z2133.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2133.kaizi.janken.model.Janken;
import oit.is.z2133.kaizi.janken.model.Entry;
import oit.is.z2133.kaizi.janken.model.User;
import oit.is.z2133.kaizi.janken.model.UserMapper;
import oit.is.z2133.kaizi.janken.model.Match;
import oit.is.z2133.kaizi.janken.model.MatchMapper;

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
      ArrayList<User> AllUserName = userMapper.selectAllByName();

      model.addAttribute("AllResults", AllResults);
      model.addAttribute("AlluserName", AllUserName);
      model.addAttribute("name", name);

      return "janken.html";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam String name, ModelMap model) {
    name = "Hi " + name;
    model.addAttribute("name", name);

    return "janken.html";
  }

  @GetMapping("/janken/{myhand}")
  public String janken1(@PathVariable String myhand, ModelMap model) {

    Janken janken = new Janken(myhand);
    String cpuhand = "相手の手 Gu";
    String result = "結果 " + janken.getresult();

    model.addAttribute("myhand", "あなたの手 " + myhand);
    model.addAttribute("cpuhand", cpuhand);
    model.addAttribute("result", result);
    return "janken.html";
  }

}
