package cc.dyjh.www.DiaoYuJiangHu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/23.
 */
public class YuChang {
    private int fhid;
    private List<Fishery> fishery;
    private List<Yu> yu;
    private List<Yu> fisherytype;
    private List<Yu> fisheryfeature;
    private FisheryArea fisheryarea;
    private List<Yu> er;
    private List<Yu> xiangan;
    private YuXun yuxun;

    public static class Fishery {
        private int fid;//private String 2private String ,
        private int uid;//private String 2private String ,
        private String lan;//null,
        private String lat;//null,
        private String area;//null,
        private String position;//null,
        private String fisheryname;//null,
        private String album;//null,
        private String fisherytype;//null,
        private String fishtype;//null,
        private String fisheryage;//null,
        private String principal;//null,
        private String phone;//private String 18837145615private String ,
        private String seatcount;//null,
        private String waterdepth;//null,
        private String acreage;//null,
        private String fisheryfeature;//null,
        private String fdescribe;//null

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getLan() {
            return lan;
        }

        public void setLan(String lan) {
            this.lan = lan;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFisheryname() {
            return fisheryname;
        }

        public void setFisheryname(String fisheryname) {
            this.fisheryname = fisheryname;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getFisherytype() {
            return fisherytype;
        }

        public void setFisherytype(String fisherytype) {
            this.fisherytype = fisherytype;
        }

        public String getFishtype() {
            return fishtype;
        }

        public void setFishtype(String fishtype) {
            this.fishtype = fishtype;
        }

        public String getFisheryage() {
            return fisheryage;
        }

        public void setFisheryage(String fisheryage) {
            this.fisheryage = fisheryage;
        }

        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSeatcount() {
            return seatcount;
        }

        public void setSeatcount(String seatcount) {
            this.seatcount = seatcount;
        }

        public String getWaterdepth() {
            return waterdepth;
        }

        public void setWaterdepth(String waterdepth) {
            this.waterdepth = waterdepth;
        }

        public String getAcreage() {
            return acreage;
        }

        public void setAcreage(String acreage) {
            this.acreage = acreage;
        }

        public String getFisheryfeature() {
            return fisheryfeature;
        }

        public void setFisheryfeature(String fisheryfeature) {
            this.fisheryfeature = fisheryfeature;
        }

        public String getFdescribe() {
            return fdescribe;
        }

        public void setFdescribe(String fdescribe) {
            this.fdescribe = fdescribe;
        }
    }

    public static class Yu implements Parcelable, AddrBase{
        public Yu(){

        }
        private int id;//private String 1private String ,
        private String type;//private String 1private String ,
        private String name;//private String 鲤鱼private String ,
        private String ordernum;//private String 1private String ,
        private String describes;//private String 鱼种类private String

        protected Yu(Parcel in) {
            id = in.readInt();
            type = in.readString();
            name = in.readString();
            ordernum = in.readString();
            describes = in.readString();
        }

        public static final Creator<Yu> CREATOR = new Creator<Yu>() {
            @Override
            public Yu createFromParcel(Parcel in) {
                return new Yu(in);
            }

            @Override
            public Yu[] newArray(int size) {
                return new Yu[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        @Override
        public String getAddrName() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(type);
            dest.writeString(name);
            dest.writeString(ordernum);
            dest.writeString(describes);
        }
    }

    public static class FisheryArea{
        private String province;//":"",
        private String city;//":"",
        private String district;//":""

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

    public int getFhid() {
        return fhid;
    }

    public void setFhid(int fhid) {
        this.fhid = fhid;
    }

    public List<Fishery> getFishery() {
        return fishery;
    }

    public void setFishery(List<Fishery> fishery) {
        this.fishery = fishery;
    }

    public List<Yu> getYu() {
        return yu;
    }

    public void setYu(List<Yu> yu) {
        this.yu = yu;
    }

    public List<Yu> getFisherytype() {
        return fisherytype;
    }

    public void setFisherytype(List<Yu> fisherytype) {
        this.fisherytype = fisherytype;
    }

    public List<Yu> getFisheryfeature() {
        return fisheryfeature;
    }

    public void setFisheryfeature(List<Yu> fisheryfeature) {
        this.fisheryfeature = fisheryfeature;
    }

    public FisheryArea getFisheryarea() {
        return fisheryarea;
    }

    public void setFisheryarea(FisheryArea fisheryarea) {
        this.fisheryarea = fisheryarea;
    }

    public List<Yu> getEr() {
        return er;
    }

    public void setEr(List<Yu> er) {
        this.er = er;
    }

    public List<Yu> getXiangan() {
        return xiangan;
    }

    public void setXiangan(List<Yu> xiangan) {
        this.xiangan = xiangan;
    }

    public YuXun getYuxun() {
        return yuxun;
    }

    public void setYuxun(YuXun yuxun) {
        this.yuxun = yuxun;
    }
}
