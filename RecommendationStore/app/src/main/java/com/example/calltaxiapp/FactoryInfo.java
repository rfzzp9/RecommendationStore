package com.example.calltaxiapp;

//공장(매매) 데이터 구조
public class FactoryInfo {

    private String storeName; //점포명
    private String address;  //도로명
    private String transactionAmount;  //거래금액
    private String constructYear; //건축년도
    private String landArea;  //대지면적
    private String contractMethod; //거래방식(전세or매매)


    public FactoryInfo(String storeName, String address, String _transactionAmount, String landArea, String constructYear, String contractMethod) {
        this.storeName = storeName;
        this.address = address;
        this.transactionAmount = _transactionAmount;
        this.landArea = landArea;
        this.constructYear = constructYear;
        this.contractMethod = contractMethod;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getConstructYear() {
        return constructYear;
    }

    public void setConstructYear(String constructYear) {
        this.constructYear = constructYear;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getContractMethod() {
        return contractMethod;
    }

    public void setContractMethod(String contractMethod) {
        this.contractMethod = contractMethod;
    }
}
