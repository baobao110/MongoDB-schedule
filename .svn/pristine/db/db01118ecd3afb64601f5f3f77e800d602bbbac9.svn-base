//判断手机号
function isPhone (string) {    
	var pattern = /^1[3456789]\d{9}$/;    
	if (pattern.test(string)) {        
	return true;    
	}else{
	return false;
	}  
}
//判断数字
function IsNumber(str){  
    var reg = new RegExp("^[0-9]*$"); 
 	return reg.test(str);  
      
}
//是否为正整数  
function isPositiveNum(s){
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s); 
}

//js 校验正数
function validate(num)
{ 
  var reg = /^\d+(?=\.{0,1}\d+$|$)/;
  if(reg.test(num)){
	  return true;
  }else{
	  return false ;  
  } 
}
//判断字符串是否为数字和字母组合   
function checkpass(nubmer)  
{  
     var re =  /^[0-9a-zA-Z]*$/g; 
     if (!re.test(nubmer))  
    {  
        return false;  
     }else{  
    return true;  
     }  
}  

//制保留2位小数，如：2，会在2后面补上00.即2.00    
function toDecimal2(x) {    
    var f = parseFloat(x);    
    if (isNaN(f)) {    
        return false;    
    }    
    var f = Math.round(x*100)/100;    
    var s = f.toString();    
    var rs = s.indexOf('.');    
    if (rs < 0) {    
        rs = s.length;    
        s += '.';    
    }    
    while (s.length <= rs + 2) {    
        s += '0';    
    }    
    return s;    
}

//tip 的错误提示
function error_show(Errorid,Errormsg){
	
	$(Errorid).tips({
		side : 2,
		msg : Errormsg,
		bg : '#3498E9',
		time :4
	});
}

//luyer 的错误提示
function layui_error(Errormsg) {
	 
	layer.msg(Errormsg, {
        time: 8000, //8s后自动关闭 
        offset:['100',$(window).width()/2 -150],
        btn:  '知道了'
      });
}
