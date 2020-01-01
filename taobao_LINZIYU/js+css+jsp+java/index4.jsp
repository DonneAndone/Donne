<%@ page language="java" import="dbtaobao.connDb,java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
ArrayList<String[]> list = connDb.index_4();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ECharts 可视化分析淘宝双11</title>
<link href="./css/style.css" type='text/css' rel="stylesheet"/>

<script src="./js/echarts.js"></script>
<script src="./js/china.js"></script>
</head>
<body>
	<div class='header'>
        <p>ECharts 可视化分析淘宝双11</p>
    </div>
    <div class="content">
        <div class="nav">
            <ul>
                <li><a href="index.jsp">所有买家各消费行为对比</a></li>
                <li><a href="./index1.jsp">男女买家交易对比</a></li>
                <li><a href="./index2.jsp">男女买家各个年龄段交易对比</a></li>
                <li><a href="./index3.jsp">商品类别交易额对比</a></li>
                <li class="current"><a href="#">各省份的总成交量对比</a></li>
                <li><a href="./index5.jsp">Rebuy预测</a></li>
            </ul>
        </div>
        <div class="container">
            <div class="title">各省份的总成交量对比</div>
            <div class="show">
                <div class='chart-type'>地图</div>
                <div id="main"></div>
            </div>
        </div>
    </div>
<script type="text/javascript">
    var data = [];
    <%
	for(String[] a:list){
			%>
    data.push({name:"<%=a[0]%>",value:"<%=a[1]%>"});
    <%}%>
    var myChart = echarts.init(document.getElementById('main'));
    option = {
        tooltip: {
            formatter:function(params,ticket, callback){
                return params.seriesName+'<br />'+params.name+'：'+params.value
            }//数据格式化
        },
        visualMap: {
            min: 280,
            max: 340,
            left: 'left',
            top: 'bottom',
            text: ['高','低'],//取值范围的文字
            inRange: {
                color: ['#e0ffff', '#0011bb']//取值范围的颜色
            },
            show:true//图注
        },
        geo: {
            map: 'china',
            roam: false,//不开启缩放和平移
            zoom:1.23,//视角缩放比例
            label: {
                normal: {
                    show: true,
                    fontSize:'10',
                    color: 'rgba(5,2,10,0.7)'
                }
            },
            itemStyle: {
                normal:{
                    borderColor: 'rgba(250, 1, 23, 0.2)'
                },
                emphasis:{
                    areaColor: 'red',//鼠标选择区域颜色
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    shadowBlur: 20,
                    borderWidth: 0,
                    shadowColor: 'rgba(100, 50, 0, 1)'
                }
            }
        },
        series : [
            {
                name: '11.11销售额',
                type: 'map',
                geoIndex: 0,
                data:data
            }
        ]
    };
    myChart.setOption(option);
    myChart.on('click', function (params) {
        alert(params.name);
    });
</script>
</body>
</html>