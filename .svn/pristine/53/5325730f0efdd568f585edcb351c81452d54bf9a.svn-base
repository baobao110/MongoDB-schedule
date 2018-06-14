var urlUtil = {};
urlUtil.outputFun={};
urlUtil.returnUrl= "";
urlUtil.closeShow = function() {
	$("#urlmodal").remove();
};

urlUtil.init = function(fun,url,obj) {
	
	this.outputFun=fun;
	this.showUrlText(url,obj);
}
urlUtil.showUrlText = function(url,obj) {
	
	$("#urlmodal").remove();
	var html="<div id='urlmodal' style='background:#FFFFFF;width:400px;border-radius:5px;border:3px solid #1AA194;height:70px;display:inline-block;z-index:99999999;text-align:center;line-height:70px;box-shadow: 0 1px 2px #1AA194;'>";
	html+="<input type='text' style='border:1px solid #1AA194;box-shadow: 0 1px 2px #1AA194;height:30px;line-height:30px;width:250px;margin-right:10px;font-size:12px' id='urltext' value='"+url+"'  placeholder='请输入链接地址，如:http://www.baidu.com' >";
	html+="<div class='layui-btn layui-btn-small  layui-btn-info' onclick='urlUtil.returnFun();'>确定</div><div class='layui-btn layui-btn-small  layui-btn-primary' onclick='urlUtil.closeShow();'>取消</div>";
	html+="</div>";
	$("body").append(html);
	
	var X = $(obj).offset().top;
	var Y = $(obj).offset().left;

	$(document.getElementById("urlmodal")).offset({
		top : X+30,
		left : Y-150
	});

};



urlUtil.returnFun= function() {

	
	this.returnUrl = document.getElementById("urltext").value;
	
	this.closeShow();
	
	this.outputFun();
	


};