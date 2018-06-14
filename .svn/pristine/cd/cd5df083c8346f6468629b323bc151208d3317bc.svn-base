var version="1.0.0"; 
var Merchants_Cookie ="Merchants_Cookie";

// js获取项目根路径，如： http://localhost:8088/labms_s
function getRootPath() {
	// 获取当前网址，如：
	// http://localhost:8080/labms_s/navigation/files/sysmanagement/main.jsp?url=sys_equi_number.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： /ems/navigation/files/sysmanagement/main.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，"http://localhost:8080"
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/jquery
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	// 返回"/ems/navigation/files/sysmanagement/"
	var forwordPath = pathName.substring(0, pathName.length - 8);
	// return (localhostPaht + forwordPath);
	return (localhostPaht + projectName);
}

function GetQueryString(name)
{
    
	 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	         var r = window.location.search.substr(1).match(reg);
	         if (r != null)return decodeURI(r[2]);   //对参数进行decodeURI解码
	          return null;
}

//添加到收藏
function AddFavorite(title,url){
	try{
	   window.external.addFavorite(url,title);
	 }
	catch(e){
	 try{
	    window.sidebar.addPanel(title,url,"");
	  }
	 catch(e){
	   alert("抱歉，您所使用的浏览器无法完成此操作。\n\n请使用快捷键Ctrl+D进行添加！");
	   }
	 }
	}


function jspost(URL, PARAMS) {
    console.info(PARAMS);
    var temp = document.createElement("form");
    temp.action = URL;
    temp.method = "post";
    temp.style.display = "none";
    if (PARAMS["new_window"] != null) {
        openWindow(PARAMS["new_window"]);
        temp.target = PARAMS["new_window"];

    }
    if (PARAMS["target"] != null) {
        temp.target = PARAMS["target"];
    }
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;

}

function getCookie(name) {  
    //取出cookie   
   /* var strCookie = document.cookie;  
    //cookie的保存格式是 分号加空格 "; "  
    var arrCookie = strCookie.split("; ");  
    for ( var i = 0; i < arrCookie.length; i++) {  
        var arr = arrCookie[i].split("=");  
        if (arr[0] == name&&arr[1]!="") {  
            return decodeURIComponent(arr[1]);  
        }  
    }  
    return null; */ 
	var info=localStorage.getItem(name);
	if(info!=null&&info!=""){
		return info;
	}
	return "";
}

function setCookie(name,value)
{
	
	localStorage.setItem(name,value);
//	var Days = 30;
//	var exp = new Date();
//	exp.setTime(exp.getTime() + Days*24*60*60*1000); 
//	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+"";
}



function addFilesSRC(arr){

	var basepath=getRootPath();
	if(arr){
		for(var i=0,len=arr.length;i<len;i++){
			
			var one=arr[i];
			if(one.version){
				version=one.version;
			}
		
			
			if(one.type=="link"){
				document.write("<link rel='stylesheet' href='"+basepath+one.path+"?v="+version+"' />");
			}else{
				document.write("<script type='text/javascript'  src='"+basepath+one.path+"?v="+version+"'>" + "<" + "/script>");
			}
			
		} 
	}
	 
}