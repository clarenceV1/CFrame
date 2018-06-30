package com.cai.work.bean;

public class StockBuy {
   private float[] bond;
   private float[] zy;
   private float zs;
   private String zhf;
   private String yybzj;
   private float[] buyMoney;
   private String begin;
   private String end;
   private String isTrade;
   private String click_times;

   private StockBuyStock stock;
   private String holdTime;
   private String balance;
//   private StockBuyRedBag redBags;

   public float[] getBond() {
      return bond;
   }

   public void setBond(float[] bond) {
      this.bond = bond;
   }

   public float[] getZy() {
      return zy;
   }

   public void setZy(float[] zy) {
      this.zy = zy;
   }

   public float getZs() {
      return zs;
   }

   public void setZs(float zs) {
      this.zs = zs;
   }

   public String getZhf() {
      return zhf;
   }

   public void setZhf(String zhf) {
      this.zhf = zhf;
   }

   public String getYybzj() {
      return yybzj;
   }

   public void setYybzj(String yybzj) {
      this.yybzj = yybzj;
   }

   public float[] getBuyMoney() {
      return buyMoney;
   }

   public void setBuyMoney(float[] buyMoney) {
      this.buyMoney = buyMoney;
   }

   public String getBegin() {
      return begin;
   }

   public void setBegin(String begin) {
      this.begin = begin;
   }

   public String getEnd() {
      return end;
   }

   public void setEnd(String end) {
      this.end = end;
   }

   public String getIsTrade() {
      return isTrade;
   }

   public void setIsTrade(String isTrade) {
      this.isTrade = isTrade;
   }

   public String getClick_times() {
      return click_times;
   }

   public void setClick_times(String click_times) {
      this.click_times = click_times;
   }

   public StockBuyStock getStock() {
      return stock;
   }

   public void setStock(StockBuyStock stock) {
      this.stock = stock;
   }

   public String getHoldTime() {
      return holdTime;
   }

   public void setHoldTime(String holdTime) {
      this.holdTime = holdTime;
   }

   public String getBalance() {
      return balance;
   }

   public void setBalance(String balance) {
      this.balance = balance;
   }
//
//   public StockBuyRedBag getRedBags() {
//      return redBags;
//   }
//
//   public void setRedBags(StockBuyRedBag redBags) {
//      this.redBags = redBags;
//   }
}
