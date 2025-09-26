package ssafy.ps.enjoytrip_be.domain;

public class Attraction {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private int sidoCode;
    private int gugunCode;
    private double latitude;
    private double longitude;

    // 기본 생성자
    public Attraction() {}

    // Getters and Setters ... (모든 필드에 대해 생성)
    public int getContentId() { return contentId; }
    public void setContentId(int contentId) { this.contentId = contentId; }
    public int getContentTypeId() { return contentTypeId; }
    public void setContentTypeId(int contentTypeId) { this.contentTypeId = contentTypeId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAddr1() { return addr1; }
    public void setAddr1(String addr1) { this.addr1 = addr1; }
    public String getAddr2() { return addr2; }
    public void setAddr2(String addr2) { this.addr2 = addr2; }
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getFirstImage() { return firstImage; }
    public void setFirstImage(String firstImage) { this.firstImage = firstImage; }
    public String getFirstImage2() { return firstImage2; }
    public void setFirstImage2(String firstImage2) { this.firstImage2 = firstImage2; }
    public int getSidoCode() { return sidoCode; }
    public void setSidoCode(int sidoCode) { this.sidoCode = sidoCode; }
    public int getGugunCode() { return gugunCode; }
    public void setGugunCode(int gugunCode) { this.gugunCode = gugunCode; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}