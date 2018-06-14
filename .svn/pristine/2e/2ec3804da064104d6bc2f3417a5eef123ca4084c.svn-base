/**
 * 调用方法:couponUtil.init(fun,merid);
 * couponUtil.init(
 function() {
 if (this.chooseArr != null
 && this.chooseArr.length > 0) {
 var html = "";
 for ( var i = 0, len = this.chooseArr.length; i < len; i++) {

 var one = this.chooseArr[i];
 html += '<div class="onegimg" data="'+one+'"  >';
 html += '<img src="'
 + getRootPath()
 + one
 + '" onclick="couponUtil.showLgImg(this)">';
 html += '<div class="delimg" onclick="couponUtil.delthisimg(this)"></div></div>';

 }

 $(obj).before(html);

 }

 }, "1000");
 */

var couponUtil = {};
couponUtil.outputFun = {};
couponUtil.data = null;
couponUtil.chooseArr = [];
couponUtil.merid = null;
couponUtil.closeShow = function() {
	layer.close(couponUtil.modal);

};
couponUtil.modal = null;
couponUtil.type=1;//1选一个，2选多个
couponUtil.init = function(obj, merid,type) {
	this.type=type;
	this.outputFun = obj;

	var html = '';
	html += '<div id="couponUtilmodal" >';

	
	
	html += '<div class="couponmain" >';

	html += '<div class="couponarea">';

	html += '</div>';

	html += '</div>';



	html += '</div>';

	couponUtil.modal = layer.open({
		type : 1,
		title : "券模板",
		skin : 'layui-layer-demo', //样式类名

		anim : 2,
		shadeClose : true, //开启遮罩关闭
		area : [ '750px', '450px' ],
		content : html,
		btn:['确认','取消'],
		yes:function(){
			
			couponUtil.returnImg();
			
			
			
		},btn2:function(){
			layer.close(couponUtil.modal);
		}
	});

	couponUtil.merid = merid;

	this.showCoupon({
		merid:merid

	});

}
couponUtil.showCoupon = function(dirobj) {
	dirobj.merid = couponUtil.merid;

	$
			.ajax({

				type : "post",
				url : getRootPath() + "/mcoupon/queryMcouponModals.do",
				data : dirobj,
				dataType : "json",
				success : function(data) {
					
					couponUtil.data = data;
					var html = '';

					if (data != null && data.list != null
							&& data.list.length > 0) {

						var list = data.list;
						for ( var i = 0, len = list.length; i < len; i++) {
							
							
							var one = list[i];
							
							html += '<div class="onecoupon" onclick="couponUtil.chooseImg(this)" data=\''
								+JSON.stringify(one)+ '\'>';
							
							html += '<label>' + one.name + '</label>';
							
							
							html += '<span>';
							
							
							if(one.minOrderMoney>0){
								
								
								html+='<b>满'+(parseFloat(one.minOrderMoney)/100).toFixed(2)+'元，</b>';
								
								
							}
							if(one.discountType==2){
								
								
								 html += '<b>折扣</b>' + (parseFloat(one.discountRate)/10).toFixed(1) + '折';
								 
								 
							}else{
								
								html += '<b>优惠</b>' + (parseFloat(one.discountMoney)/100).toFixed(2) + '元';
							}
							html+='</span>';
							
							html += '<span>';
							
							
							if(one.expireType==2){
								
								
								html+='有效期<b>'+one.validDays+'</b>天';
								
								
							}else{
								
								html+='有效期至<b>'+one.stopTime+'</b>';
						   }
					
							html+='</span>';
							
							
						    html += '<div class="arrow"><div class="arrowfont"></div></div>';
						    html += '</div>';

						}

						

					

						html += '<div class="weiimg">';

						html += '<div class="pagebtns">';
						html += '<span class="fpage">共' + data.rowCount
								+ '个</span>';
						html += '<span class="fpage">' + data.pageNum + '/'
								+ data.pageCount + '页</span>';
						html += '<span class="fpage fbtn" onclick="couponUtil.gopage(1)">首页</span>';
						html += '<span class="fpage fbtn" onclick="couponUtil.gopage('
								+ (parseInt(data.pageNum) - 1)
								+ ')">上一页</span>';
						html += '<span class="fpage fbtn" onclick="couponUtil.gopage('
								+ (parseInt(data.pageNum) + 1)
								+ ')">下一页</span>';
						html += '<span class="fpage fbtn" onclick="couponUtil.gopage('
								+ parseInt(data.pageCount) + ')">尾页</span>';
						html += '<input type="text" class="fpage fbtn" value="'
								+ data.pageNum + '" >';
						html += '<span class="fpage fbtn" id="gopagenum"  onclick="couponUtil.gopage2(this)">跳转</span>';

						html += '</div>';

					} else {

						

						


					}
					var dom = $(document.getElementById("couponUtilmodal")).find(
							".couponarea");

					dom.html(html);

				}

			});

};

couponUtil.gopage = function(num) {
	if (num == null) {
		num == 1;
	} else {
		if (parseInt(num) < 1) {
			num = 1;
		}
		if (parseInt(num) > parseInt(this.data.pageCount)) {
			num = parseInt(this.data.pageCount);
		}

	}

	this.showCoupon({
		page : num,
		merid:couponUtil.merid
	});

};

couponUtil.gopage2 = function(obj) {
	var num = $(obj).closest("div").find("input").val();
	this.gopage(num);
};

couponUtil.chooseImg = function(obj) {

	if(this.type==1){
		$(obj).siblings(".onecoupon").removeClass("checkon");
		$(obj).toggleClass("checkon");
	}else{
		
		$(obj).toggleClass("checkon");
	}
	
	

};







couponUtil.returnImg = function() {

	var arr = [];
	$(".onecoupon.checkon").each(function() {

		arr.push(JSON.parse($(this).attr("data")));

	});
	if(arr.length<1){
		
		
		return;
		
	}else{

		this.chooseArr = arr;
		layer.close(couponUtil.modal);

		this.outputFun();
	}
	
	

	
};




