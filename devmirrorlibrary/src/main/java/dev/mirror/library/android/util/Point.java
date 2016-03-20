package dev.mirror.library.android.util;

/**
 * Created by 王沛栋 on 2015/12/17.
 */
public class Point {
    public static final double EPSILON = 1e-9;
    private double lat;
    private double lon;
    public Point(double lat, double lon) {
        super();
        this.lat = lat;
        this.lon = lon;
    }
    /**
     * @return the lat
     */
    public double getLat() {
        return this.lat;
    }
    /**
     * @param lat
     *            the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
    /**
     * @return the lon
     */
    public double getLon() {
        return this.lon;
    }
    /**
     * @param lon
     *            the lon to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Point [lat=" + this.lat + ", lon=" + this.lon + "]";
    }
    public static  String toString(Point[] ps) {
        StringBuffer sBuffer = new StringBuffer();
        for(Point p:ps) {
            sBuffer.append("\n");
            sBuffer.append(p.toString());
            sBuffer.append("==>");
        }
        return sBuffer.toString();
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(this.lat);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.lon);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (Double.doubleToLongBits(this.lat) != Double
                .doubleToLongBits(other.lat))
            return false;
        if (Double.doubleToLongBits(this.lon) != Double
                .doubleToLongBits(other.lon))
            return false;
        return true;
    }

    public  double distance(Point b) {
        Point a = this;
        return Math.sqrt((a.getLon() - b.getLon()) * (a.getLon() - b.getLon())
                + (a.getLat() - b.getLat()) * (a.getLat() - b.getLat()));
    }

    /**
     * 多边形顶点序号以0开始顺次为1,2直到n-1，
     * 定义增序向溯进为getNext，
     * 降序向溯进为getPre。
     * 求多边形顶点的前一点
     * @param me
     * @param count
     * @return
     */
    private static int getPre(int me, int count) {
        return (me == 0) ? count - 1 : me - 1;
    }
    private static int getNext(int me, int count) {
        return (me + 1) % count;
    }

    private static double distance(Point a, Point b) {
        return a.distance(b);
    }


    /**
     * 判断指定指定点是否在线段a-->b上。
     * @param toTest
     * @param a
     * @param b
     * @return
     */
    /**
     * @param toTest
     * @param a
     * @param b
     * @return
     */
    public static boolean onLine(Point toTest, Point a, Point b) {
        if(toTest.equals(a) || toTest.equals(b)) {
            return true;
        }
        if(a.equals(b)) {
            return false;
        }
        // 距离和判断。
        //  return Math.abs(distance(a,b)- distance(a,toTest)-distance(toTest,b)) <= ESP;
        // 斜率判断。
        double dlon1 = toTest.getLon() - a.getLon();
        double dlon2 = b.getLon() - toTest.getLon();
        double dlat1 = toTest.getLat() - a.getLat();
        double dlat2 = b.getLat() - toTest.getLat();
        if(dlon1 <= 0 && dlon2 <= 0 || dlon1 >= 0 && dlon2 >= 0) {
            if(dlat1 < 0 && dlat2 < 0 || dlat1 > 0 && dlat2 > 0) {
                return Math.abs(dlon1/dlat1 - dlon2/dlat2) <= EPSILON;
            }
            return (dlat1 == 0 && dlat2 == 0);
        }
        return false;
    }
    /**
     * 判断指定点是否在指定多边形内。
     * 使用北向射线刺破点(割点)数判断，奇数点在内，偶数点在外。
     * 交叉点类型定义： 刺破点(割点)，交叉点线的前后顶点在射线异侧；
     *               弹回点(切点)，交叉点线的前后顶点在射线同侧；
     * 和边的非顶点交叉点都是刺破点。
     * @param toTest
     * @param polygon
     * @return
     */
    public static boolean inPolygon(Point toTest, Point[] polygon) {
        boolean ret = false;
        int vertexs = polygon.length;
        int purgeTimes = 0;
        int end = vertexs;
        for (int iIndex = 0; iIndex < end; iIndex++) {
            int preIndex = iIndex;
            int nextIndex = getNext(iIndex, vertexs);
            if(onLine(toTest,polygon[preIndex],polygon[nextIndex])) {
                return true;
            }
            double difpre = toTest.getLon() - polygon[preIndex].getLon();
            // 支持连续的顶点指定在同一直线上。
            while (difpre == 0) {
                // 交点发生在顶点，需要区分切点和割点
                if(onLine(toTest,polygon[preIndex],polygon[getPre(preIndex, vertexs)])) {
                    return true;
                }
                preIndex = getPre(preIndex, vertexs);
                difpre = toTest.getLon() - polygon[preIndex].getLon();
                if(preIndex == iIndex) {
                    break;
                }
            }
            if(preIndex > iIndex) {
                end = preIndex+1;
            }
            double difNext = toTest.getLon() - polygon[nextIndex].getLon();
            while (difNext == 0) {
                if(onLine(toTest,polygon[nextIndex],polygon[getNext(nextIndex, vertexs)])) {
                    return true;
                }
                iIndex++;
                nextIndex = getNext(nextIndex, vertexs);
                difNext = toTest.getLon()
                        - polygon[nextIndex].getLon();
                if(nextIndex == preIndex) {
                    break;
                }
            }
            // 刺破点或线
            if (difpre < 0 && difNext > 0 || difpre > 0 && difNext < 0) {
                // 射线检查
                nextIndex = getNext(preIndex, vertexs);

                // (y1-y2)/(x1-x2)=(y1-y3)/(x1-x3)==>y3=y1-(y1-y2)*(x1-x3)/(x1-x2),y轴平行射线y<或>y3
                double dlat = polygon[preIndex].getLat() - (polygon[preIndex].getLat() -polygon[nextIndex].getLat()) * (polygon[preIndex].getLon() - toTest.getLon()) / (polygon[preIndex].getLon() - polygon[nextIndex].getLon());
                //          double dlat = ((polygon[preIndex].getLat() -polygon[nextIndex].getLat())* toTest.getLon() + polygon[preIndex].getLon()*polygon[nextIndex].getLat() -polygon[nextIndex].getLon()*polygon[preIndex].getLat())/ (polygon[preIndex].getLon() - polygon[nextIndex].getLon());
                if (dlat < toTest.getLat()) {
                    purgeTimes++;
                }
            }
        }
        ret = (purgeTimes % 2 != 0);
        return ret;
    }

    private static void testIsPointInPolygon() {
        Point[][] toTests = new Point[][] { new Point[] {
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 2),
                new Point(1.5,2.5),
                new Point(2, 6),
                new Point(4, 2),
                new Point(3.5, 5),
                new Point(2, 5),
                new Point(3, 3),
                new Point(5, 3),
                new Point(4, 3.5),
                new Point(2, 4),
                new Point(1,1)
        },new Point[] {
                new Point(0, 0),
                new Point(10, 9.9999),
                new Point(9.99999, 10),
                new Point(10.1, 10.1),
                new Point(19, 18),
                new Point(19, 20),
                new Point(20, 20),
                new Point(20, 40),
                new Point(15, 30),
                new Point(15, 47),
                new Point(15, 48),
                new Point(15, 49),
                new Point(15, 50),
                new Point(17, 51),
                new Point(17, 55),
                new Point(17, 60),
                new Point(20, 50),
                new Point(20, 60),
                new Point(20, 80),
                new Point(21, 80),
                new Point(22, 80),
                new Point(27, 80),
                new Point(111, 80),
                new Point(111, 81),
                new Point(111, 82),
                new Point(30, 125),
                new Point(30,139),
                new Point(40, 125.0001),
                new Point(45, 39.9999),
                new Point(45, 40.0001),
                new Point(120, 40),
                new Point(129.999, 39.999)
        }};
        Point[][] polygons = new Point[][] { new Point[] {
                new Point(1, 1),
                new Point(2, 3),
                new Point(1.5, 5),
                new Point(3, 4.5),
                new Point(4, 4),
                new Point(5, 5),
                new Point(7,5),
                new Point(6, 3),
                new Point(7, 1),
                new Point(5, 1),
                new Point(4, 3),
                new Point(3, 1)
        },new Point[] {
                new Point(10, 10),
                new Point(20, 20),
                new Point(20, 45),
                new Point(10, 50),
                new Point(20, 60),
                new Point(20, 80),
                new Point(5,80),
                new Point(20, 105),
                new Point(5, 110),
                new Point(20, 115),
                new Point(15, 125),
                new Point(16, 125),
                new Point(17, 125),
                new Point(18, 125),
                new Point(19, 125),
                new Point(20, 125),
                new Point(30, 130),
                new Point(40, 125),
                new Point(65, 125),
                new Point(70, 140),
                new Point(2000, 125),
                new Point(22, 80),
                new Point(30, 80),
                new Point(33, 80),
                new Point(35, 80),
                new Point(37, 80),
                new Point(40, 80),
                new Point(50, 80),
                new Point(60, 80),
                new Point(80, 80),
                new Point(110, 80),
                new Point(120, 85),
                new Point(45, 40),
                new Point(130, 39.999),
                new Point(95, 20),
                new Point(100, 5),
                new Point(90, 20),
                new Point(65, 20),
                new Point(65, 5),
                new Point(40, 20)
        }};
        for(int i = 0; i < polygons.length; i++) {
            Point[] polygon = polygons[i];
            Point[] tt = toTests[i];
            System.out.println(" to test polygon:"+ toString(polygon));
            for(Point me:tt) {
                System.out.println(me.toString() + " isPointInPolygon="+ inPolygon(me,polygon));
            }
        }
        //  for(Point[] polygon:polygons) {
        //   System.out.println(" to test polygon:"+ toString(polygon));
        //   for(Point[] tt:toTests) {
        //	    for(Point me:tt) {
        //	     System.out.println(me.toString() + " isPointInPolygon="+ isPointInPolygon(me,polygon));
        //	    }
        //   }
        //  }
    }


    public static void main(String[] a) {
        Point.testIsPointInPolygon();
    }

}