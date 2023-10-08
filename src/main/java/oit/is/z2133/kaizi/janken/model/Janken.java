package oit.is.z2133.kaizi.janken.model;

public class Janken {
  String myhand;
  String cpuhand;
  String result;

  public Janken(String myhand) {
    this.myhand = myhand;
    this.cpuhand = "Gu";

    if (myhand.equals("Gu")) {
      this.result = "Draw!";

    } else if (myhand.equals("Tyoki")) {
      this.result = "You Lose!";

    } else {
      this.result = "You Win!";
    }
  }

  public String getmyhand() {
    return this.myhand;
  }

  public void setmyhand(String myhand) {
    this.myhand = myhand;
  }

  public String getcpuhand(){
    return this.cpuhand;
  }

  public void setcpuhand(String cpuhand){
    this.cpuhand = cpuhand;
  }

  public String getresult(){
    return this.result;
  }
}
