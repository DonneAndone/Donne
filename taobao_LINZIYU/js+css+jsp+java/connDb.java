package dbtaobao;
import java.sql.*;
import java.util.ArrayList;

public class connDb {
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    //连接数据库方法
    public static void startConn(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //连接数据库中间件
            try{
                con = DriverManager.getConnection("jdbc:MySQL://192.168.1.103:3306/dbtaobao","root","Dong123...");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //关闭连接数据库方法
    public static void endConn() throws SQLException{
        if(con != null){
            con.close();
            con = null;
        }
        if(rs != null){
            rs.close();
            rs = null;
        }
        if(stmt != null){
            stmt.close();
            stmt = null;
        }
    }
    //数据库双11 所有买家消费行为比例
    public static ArrayList index() throws SQLException{
        ArrayList<String[]> list = new ArrayList();
        startConn();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select action,count(*) num from user_log group by action desc");
        while(rs.next()){
            String[] temp={rs.getString("action"),rs.getString("num")};
            list.add(temp);
        }
        endConn();
        return list;
    }
    //男女买家交易对比
    public static ArrayList index_1() throws SQLException{
        ArrayList<String[]> list = new ArrayList();
        startConn();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select gender,count(*) num from user_log group by gender desc");
        while(rs.next()){
            String[] temp={rs.getString("gender"),rs.getString("num")};
            list.add(temp);
        }
        endConn();
        return list;
    }
    //男女买家各个年龄段交易对比
    public static ArrayList index_2() throws SQLException{
        ArrayList<String[]> list = new ArrayList();
        startConn();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select gender,age_range,count(*) num from user_log group by gender,age_range desc");
        while(rs.next()){
            String[] temp={rs.getString("gender"),rs.getString("age_range"),rs.getString("num")};
            list.add(temp);
        }
        endConn();
        return list;
    }
    //获取销量前五的商品类别
    public static ArrayList index_3() throws SQLException{
        ArrayList<String[]> list = new ArrayList();
        startConn();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select cat_id,count(*) num from user_log group by cat_id order by count(*) desc limit 5");
        while(rs.next()){
            String[] temp={rs.getString("cat_id"),rs.getString("num")};
            list.add(temp);
        }
        endConn();
        return list;
    }
    //各个省份的总成交量对比
    public static ArrayList index_4() throws SQLException{
        ArrayList<String[]> list = new ArrayList();
        startConn();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select province,count(*) num from user_log group by province order by count(*) desc");
        while(rs.next()){
            String[] temp={rs.getString("province"),rs.getString("num")};
            list.add(temp);
        }
        endConn();
        return list;
    }

    //预测回头客的概率
    public static ArrayList index_5() throws SQLException{
        ArrayList<String[]> list = new ArrayList();
        startConn();
        stmt = con.createStatement();
        rs = stmt.executeQuery("select score,count(*) num from rebuy group by score order by score desc");
        Double[] shuzu={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        while(rs.next()){
            if(Double.valueOf(rs.getString("score"))<-90000){
                shuzu[0]=shuzu[0]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-80000){
                shuzu[1]=shuzu[1]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-70000){
                shuzu[2]=shuzu[2]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-60000){
                shuzu[3]=shuzu[3]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-50000){
                shuzu[4]=shuzu[4]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-40000){
                shuzu[5]=shuzu[5]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-30000){
                shuzu[6]=shuzu[6]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-20000){
                shuzu[7]=shuzu[7]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<-10000){
                shuzu[8]=shuzu[8]+Double.valueOf(rs.getString("num"));
            }else if(Double.valueOf(rs.getString("score"))<0){
                shuzu[9]=shuzu[9]+Double.valueOf(rs.getString("num"));
            }
        }
        String[] temp0={"%0~10%",String.valueOf(shuzu[9])};
        String[] temp1={"%10~20%",String.valueOf(shuzu[8])};
        String[] temp2={"%20~30%",String.valueOf(shuzu[7])};
        String[] temp3={"%30~40%",String.valueOf(shuzu[6])};
        String[] temp4={"%40~50%",String.valueOf(shuzu[5])};
        String[] temp5={"%50~60%",String.valueOf(shuzu[4])};
        String[] temp6={"%60~70%",String.valueOf(shuzu[3])};
        String[] temp7={"%70~80%",String.valueOf(shuzu[2])};
        String[] temp8={"%80~90%",String.valueOf(shuzu[1])};
        String[] temp9={"%90~100%",String.valueOf(shuzu[9])};

        list.add(temp0);
        list.add(temp1);
        list.add(temp2);
        list.add(temp3);
        list.add(temp4);
        list.add(temp5);
        list.add(temp6);
        list.add(temp7);
        list.add(temp8);
        list.add(temp9);
        endConn();
        return list;
    }


    public static void main(String[] args) throws SQLException {
        startConn();
        ArrayList<String[]> list = connDb.index_5();
        for(String[] a:list) {
            System.out.println(a[0]);
            System.out.println(a[1]);
        }
    }
}