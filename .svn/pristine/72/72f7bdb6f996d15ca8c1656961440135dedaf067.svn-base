var LOGIN={};
LOGIN.ajaxLogin=function(){
	$.ajax({
		type: "get",
		url: rootPath+"/jdgame/ajaxLogin.do?callback=ajaxLoginShow(data)",
		dataType: "jsonp",
		data: {
			uid: uid
		}
	});
}

function ajaxLoginShow() {

	setTimeout(function() {
				LOGIN.ajaxLogin();
	}, 1000);
	
	
}