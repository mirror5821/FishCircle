package cc.dyjh.www.DiaoYuJiangHu.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class Address implements AddrBase,Parcelable{
	public Address() {
	}

	private String addr;
	private String name;
	private String phone;
	private double lat; 
	private double lng; 
	private String city_name;//private String 郑州市private String ,
	private String province_name;//private String 河南省private String ,
	private String street;//private String 建业如意家园20号楼private String ,
	private String area_name;//private String 金水区private String ,
	private String is_default;//1,
	private String full_address;//private String 河南省郑州市金水区建业如意家园20号楼private String ,
	private String addressId;//3




	public Address(Parcel parcel){
		addr = parcel.readString();
		name = parcel.readString();
		phone = parcel.readString();
		lat = parcel.readDouble();
		lng = parcel.readDouble();
		city_name = parcel.readString();
		province_name = parcel.readString();
		street = parcel.readString();
		area_name = parcel.readString();
		is_default = parcel.readString();
		full_address = parcel.readString();
		addressId = parcel.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(addr);
		dest.writeString(name);
		dest.writeString(phone);
		dest.writeDouble(lat);
		dest.writeDouble(lng);
		dest.writeString(city_name);
		dest.writeString(province_name);
		dest.writeString(street);
		dest.writeString(area_name);
		dest.writeString(is_default);
		dest.writeString(full_address);
		dest.writeString(addressId);
	}

	public static final Creator<Address> CREATOR = new Creator<Address>() {

		@Override
		public Address[] newArray(int size) {
			return new Address[size];
		}

		@Override
		public Address createFromParcel(Parcel source) {
			return new Address(source);
		}
	};

	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getCity_name() {
		return city_name;
	}



	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}



	public String getProvince_name() {
		return province_name;
	}



	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}



	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public String getArea_name() {
		return area_name;
	}



	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}



	public String getIs_default() {
		return is_default;
	}



	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}



	public String getFull_address() {
		return full_address;
	}



	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}



	public String getAddressId() {
		return addressId;
	}



	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}



	public String getAddr() {
		return addr;
	}



	public void setAddr(String addr) {
		this.addr = addr;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}






	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String getAddrName() {
		return addr+name;
	}



	@Override
	public int describeContents() {
		return 0;
	}





}
