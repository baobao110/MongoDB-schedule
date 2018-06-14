var MERCHANT_LOGININFO=null;
/**
 * 商家pc版校验
 */ 
$(function(){
	
	
	var str = getCookie(Merchants_Cookie);
	 
	if(str!= null && str!=""){
		MERCHANT_LOGININFO = JSON.parse(str);
		 
		$.ajax({
			type:"post",
	  		url: getRootPath() +"/sjinfo/check_merchants.do",
	  		dataType:"json",
	  		data:MERCHANT_LOGININFO,
	  		success: function(data){
	  			var status=parseInt(data.back_code);
	  			if( status== 200){
	  				  
	  			}else if(status== 250){
	  				obj.token =  data.token; 
	  				setCookie(Merchants_Cookie,JSON.stringify(obj));
	  			}else{
	  				parent.location.href = getRootPath()+"/pages/sjpc/mlogin.html"; 
	  			}
	  			
	  		}
		});
	}else{
		parent.location.href = getRootPath()+"/pages/sjpc/mlogin.html"; 
	}
	 
});