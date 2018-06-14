/**调用方法
 * bmap.init(function(){
			
			
			
//			console.log(JSON.stringify(bmap.address))


		},address,point);
 */
var bmap={};
bmap.address={province:"江苏省",city:"南京市",county:"秦淮区",address:"苜蓿园大街88号锦湖大厦南楼"};

bmap.point = new BMap.Point(118.838035,32.035181);//初始位置，lat经度，lng纬度
bmap.outFun=null;
bmap.modal=null;
bmap.mapobj=null;
bmap.myGeo=new BMap.Geocoder();//
bmap.init=function(fun,add,point){
	if(add){
		this.address=add;
	}
	if(point){
		bmap.point=new BMap.Point(point.lat,point.lng);
	}
	if(fun){
		bmap.outFun=fun;
	}
	
	//初始化地图和搜索
	var html='';
	
	html+='<div id="bmaparea" class="layui-form">';
	html+='<div class="layui-form-item" id="head">';
	
				html+='<i class="fa fa-map-marker" ></i><input id="bmapaddress" value="'+this.address.address+'"   placeholder="请输入地址" class="layui-input" type="text">';
				
				html+='</div>';
							html+='<div id="bmap"></div>';
								html+=' <div id="bmapres"> </div>';
	
									html+='</div>';
	
	
									bmap.modal= layer.open({
										type : 0,
										title : false,
										skin : 'layui-layer-demo', //样式类名

										anim : 2,
										shadeClose : true, //开启遮罩关闭
										area : [ '520px', '520px' ],
										content : html,
										yes:function(){
											//选择
											
											bmap.getAddress(bmap.mapobj.getCenter());
											
											layer.close(bmap.modal);
										},
										success:function(){
											
										
											
											bmap.mapobj = new BMap.Map("bmap");            // 创建Map实例
											
//											bmap.mapobj.addEventListener("moveend",showCurrentPoi);
											 
											document.getElementById("bmapaddress").value=bmap.address.address;
											   if(document.getElementById("bmapaddress").value!=""){
									        	   showPoiFromKeyword(document.getElementById("bmapaddress").value); 
									           }else{
									        	   bmap.mapobj.centerAndZoom(bmap.point,17);
									        	   bmap.setlocationDian(bmap.mapobj,bmap.point);
											         
											         
									           }
									            
											    //自动完成
											    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
											            {"input" : "bmapaddress",
											            	
											              "location" : bmap.mapobj
											            });
												
											    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
//											        console.log(JSON.stringify(e.item))
											        
											    	var _value = e.item.value;
											        var myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
											        bmap.address.province=_value.province;
											        bmap.address.city=_value.city;
											        bmap.address.county=_value.district;
											        bmap.address.address=_value.district +  _value.street +  _value.business;
											        // alert("1:"+_value.province+"2:"+_value.city+"3:"+_value.district+"4:"+_value.street+"5:"+_value.business);
											        showPoiFromKeyword(myValue);
											        
											        
											    });
			
											
										}
									});
									
									
								
	
};


//加点
bmap.setlocationDian=function(map, point) {
	bmap.point=point;
	var mkr = new BMap.Marker(point);
	map.clearOverlays();
	map.addOverlay(mkr);
	//自动定位
	map.centerAndZoom(point, 17);
    map.panTo(point);
//	map.centerAndZoom(point, 17);
	//map.enableScrollWheelZoom(); //开启滚动缩放
//	map.enableContinuousZoom(); //开启缩放平滑

//	map.addControl(new BMap.NavigationControl());
//	map.addControl(new BMap.ScaleControl());
//	map.addControl(new BMap.OverviewMapControl());
	/*map.addControl(new BMap.MapTypeControl()); */

	map.addEventListener("click", function (e) {
		
	    mkr.setPosition(e.point);
	    map.addOverlay(mkr);
	    //将点击的点的坐标显示在页面上
	    bmap.point = e.point;
	   
	    showCurrentPoi(bmap.mapobj);
	    
	    
	});
	bmap.point=bmap.mapobj.getCenter();
	showCurrentPoi(bmap.mapobj);
}


function showCurrentPoi(map)
{
	
   var local= new BMap.LocalSearch(map, {
        renderOptions: {selectFirstResult:true},
        onSearchComplete:function(results){
        	
                if (local.getStatus() == BMAP_STATUS_SUCCESS){
                    var s = [];
                    var content = "";
                    content = " <ul class='layui-collapse' id='allarea'> ";
                    for (var i = 0; i < results.getCurrentNumPois(); i++)
                    {
                        var tname = results.getPoi(i).title;
                        var tadd = results.getPoi(i).address;
                        content = content +
                        " <li class='layui-colla-item oneadd' onclick='chooseOneitem(this)' data='"+JSON.stringify(results.getPoi(i))+"'   ><i class='layui-icon'>&#xe715;</i> <label>" +
                        tname + "</label><span>(" +
                        tadd + ")</span></li>";
                    }
                    content = content + " </ul>";
                    document.getElementById("bmapres").innerHTML = content;
                    
                    
                   // showHeadADD( results.getPoi(0));
                    
                }
        }
    });
    
   bmap.myGeo.getLocation(bmap.point, function (result) {  
       if (result) {  
           local.search(result.address);  
       }  
   });  
   
}
function chooseOneitem(obj){
	var poi=eval("("+$(obj).attr("data")+")");
	bmap.point = new BMap.Point(poi.point.lat,poi.point.lng);
	showPoiFromKeyword(poi.address);
	
	showHeadADD(poi);
	
	
	
	
}
function showHeadADD(poi){
	

	
	document.getElementById("bmapaddress").value=poi.title;
}

function showPoiFromKeyword(myValue){
    var local = new BMap.LocalSearch(bmap.mapobj, { //智能搜索
        onSearchComplete: function(results){
        	
            if (local.getStatus() == BMAP_STATUS_SUCCESS) {
            	
            	bmap.point = local.getResults().getPoi(0).point;
                
                bmap.setlocationDian(bmap.mapobj,bmap.point);
                
                //var distance = (map.getDistance(point,basePoint)).toFixed(2);
               
            }
        }
    });
    local.search(myValue);
}

/*function confirmAddress(i)
{
    //用户单击某一项之后，跳转会页面，同时将地址传递过去
    var distance = (bmap.mapobj.getDistance(points[i],basePoint)).toFixed(2);
    if(distance<=d)
    {
        isdispatching = true;
    }
    if (!isdispatching) {
        alert("你的选择是：" + title[i] + " 与最近的仓库的距离是：" + distance + "米，目前该地址尚不在配送范围内！")
    } else {
        alert("你的选择是：" + title[i] + " 与最近的仓库的距离是：" + distance + "米，恭喜你，该地址在配送范围内！")
    }
}*/



//根据经纬度获取地址描述
bmap.getAddress=function(point){
	
	// 根据坐标得到地址描述
	 this.myGeo.getLocation(point, function(result){
             if (result){
            	var add=result.addressComponents;
            	 bmap.address.province= add.province;
            	 bmap.address.city= add.city;
            	 bmap.address.county= add.district;
            	 bmap.address.address= document.getElementById("bmapaddress").value;
            	 
            	 bmap.outFun();
             }
         });
}