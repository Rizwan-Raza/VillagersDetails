package com.mars.villagersdetails;

/**
 * Created by HP on 12/13/2017.
 */

public final class UserBean {
    private String name;
    private String fname;
    private String mname;
    private String address;
    private String gender;
    private String status;
    private String qual;
    private String category;
    private String religon;

    private String aadhar;
    private String voterId;
    private String bankAC;
    private String ifsc;
    private String branch;
    private String bankAdd;
    private String jobId;

    private String supply;

    private String detail;
    private String toilet;
    private String gents;
    private String ladies;

    public UserBean(String name, String fname, String mname, String address, String gender, String status, String qual, String category, String religon, String aadhar, String voterId, String bankAC, String ifsc, String branch, String bankAdd, String jobId, String supply, String detail, String toilet, String gents, String ladies) {
        this.name = name;
        this.fname = fname;
        this.mname = mname;
        this.address = address;
        this.gender = gender;
        this.status = status;
        this.qual = qual;
        this.category = category;
        this.religon = religon;
        this.aadhar = aadhar;
        this.voterId = voterId;
        this.bankAC = bankAC;
        this.ifsc = ifsc;
        this.branch = branch;
        this.bankAdd = bankAdd;
        this.jobId = jobId;
        this.supply = supply;
        this.detail = detail;
        this.toilet = toilet;
        this.gents = gents;
        this.ladies = ladies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReligon() {
        return religon;
    }

    public void setReligon(String religon) {
        this.religon = religon;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getVoterId() {return voterId; }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getBankAC() {
        return bankAC;
    }

    public void setBankAC(String bankAC) {
        this.bankAC = bankAC;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getGents() {
        return gents;
    }

    public void setGents(String gents) {
        this.gents = gents;
    }

    public String getLadies() {
        return ladies;
    }

    public void setLadies(String ladies) {
        this.ladies = ladies;
    }
}
