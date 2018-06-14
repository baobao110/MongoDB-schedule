
$(function(){
	
	//更新mess消息
	ajaxUpdateMessInfo();
	
	
	
	//初始化
	USERAPP.init(function(){
		//是登陆用户
		
		USERAPP.initMenu(0);
		//获取用户消息
		updateUmInfo();
		
		
	}, function(){
		//游客阅读提示注册用户阅读会有收益
		//非登陆用户
		$(".pjdiv").hide();
		$(".flagdivarea").hide();
		$("#jdloading").hide();
		//游客阅读提示注册用户阅读会有收益
		
		$("body").append("<a class='regeditTitle' href='#'>立即下载APP,读消息赚钱！</a>");
		
		
	});
	
	
	
	//点击事件,标记
	flagClickWork();
	
	
});
//会员
function showUmHY(hy){
	$("#changetohy").removeClass("checkon");
	if(Number(hy)==1){
		$("#changetohy").toggleClass("checkon");
	}
}


//评价
function showUmZan(zan){
	
	$(".pjarea .onearea").removeClass("checkon");
	var num=Number(zan);
	if(num==1){
		$("#zanuser_num").toggleClass("checkon");
	}else if(num==2){
		$("#xquser_num").toggleClass("checkon");
	}else if(num==3){
		$("#xq2user_num").toggleClass("checkon");
	}else if(num==4){
		$("#blackuser_num").toggleClass("checkon");
	}else if(num==5){
		$("#storeuser_num").toggleClass("checkon");
	}
	
	
}

//标记
function showUmFlag(flag){
	
	$(".oneflag").removeClass("checkon");
	if($(".oneflag.flag"+flag).length>0){
		$(".oneflag.flag"+flag).toggleClass();
	}
	
};
//标记点击事件
function flagClickWork(){
$("#main").on(".oneflag","click",function(){
		
		if($(this).hadClass("flag1")){
			if(messBean.user_mess.isFLAG==1){
				messBean.user_mess.isFLAG=0;
			}else{
				messBean.user_mess.isFLAG=1;
			}
			
			
		}else if($(this).hadClass("flag2")){
			if(messBean.user_mess.isFLAG==2){
				messBean.user_mess.isFLAG=0;
			}else{
				messBean.user_mess.isFLAG=2;
			}
			
		}else{
			if(messBean.user_mess.isFLAG==3){
				messBean.user_mess.isFLAG=0;
			}else{
				messBean.user_mess.isFLAG=3;
			}
		}
	
		showUmFlag(messBean.user_mess.isFLAG);
		
		//flag修改
		messBean.updateLocalUmData(messBean.user_mess,1);
		
	});
}
/**
 * 
 * 更新消息
 * 
 */
function ajaxUpdateMessInfo(){
	
	messBean.updateMessInfo(function(){
		
		//显示数据
		//点赞人数
		var zanuser_num=0;
		if(messBean.mess.zanuser_num){
			zanuser_num=messBean.mess.zanuser_num;
		}
		
	    $("#zanuser_num").html(zanuser_num);
		
		
		
		//消息状态显示(发布中)
		
	},messid);
}
//用户消息更新
function updateUmInfo(){
	//用户消息数据里面包含该数据就不需要执行再次请求获取
	messBean.GET_USER_MESS(function(){
		
		//获取该数据之后执行的函数
		
		//是否成为该商家会员
		showUmHY(messBean.user_mess.isHY);
		
		
		
		//是否领过券，游客(点击领券提示登陆、下载APP)
		
		//是否购买过，是否关注
		
		
		//评价
		showUmZan(messBean.user_mess.isZAN);
		
		//消息标记那一级
		showUmFlag(messBean.user_mess.isFLAG);
		
		
		//该信息商家账户有金额，用户未获得收益，执行方法
		var loadm=new initLoadM(function(){
			
			document.getElementById("jdloading").children[2].children[0].innerHTML="<sm>收益</sm>+0.93";
		},document.getElementById("jdloading"),2);
		
		loadm.init();
		
		
		
		
		
		
		
		
		
	},messid);
}