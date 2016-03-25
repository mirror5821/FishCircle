package cc.dyjh.www.DiaoYuJiangHu.bean;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/25.
 */
public class ServiceArea {
    private int province_id;//":2,
    private String province_name;//":"北京",
    private List<City> city;

    public static class City{
        private int city_id;//":36,
        private String city_name;//":"安庆"
        private List<District> district;

        public static class District{
            private int district_id;//":398,
            private String district_name;//":"迎江区"

            public int getDistrict_id() {
                return district_id;
            }

            public void setDistrict_id(int district_id) {
                this.district_id = district_id;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public List<District> getDistrict() {
            return district;
        }

        public void setDistrict(List<District> district) {
            this.district = district;
        }
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}
