package oit.is.z2133.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2133.kaizi.janken.model.Janken;

/**
 * Sample21Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class JankenController {

  /**
   * jankenというGETリクエストがあったら sample21()を呼び出し，sample21.htmlを返す
   */

  @GetMapping("/janken.html")
  public String janken1() {
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
