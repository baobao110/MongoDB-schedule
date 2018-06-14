

function get(name){
	return localStorage.getItem(name);
}


$(function () {
	var UUID=get("UUID");
	if(UUID!=null&&UUID.trim()!=""){
		$.ajax({
		       type : "POST",
		       dataType : "text",
		       url : getRootPath()+"/login/toLogin.do",
		       data : {uuid:UUID},
		       success : function(data) {
		    	   
		           if(data=='200') {
		        	    
		           }else{
		        	   window.location.href = getRootPath()+"/pages/pt/common/login.html";
		           }
		         
		       }
		   });
	}else{
		
		window.location.href = getRootPath()+"/pages/pt/common/login.html";
	}
	
});

