/**
 * 商品，sku，spu
 * 商品编码，商品图片(多种)、名称、价格、规格(，价格)，说明，state(1上架，下架)
 * 调用方法:goodsUtil.init(fun,merid);
 * goodsUtil.init(
 function() {
 if (this.chooseArr != null
 && this.chooseArr.length > 0) {
 var html = "";
 for ( var i = 0, len = this.chooseArr.length; i < len; i++) {

 var one = this.chooseArr[i];
 

 }

 $(obj).before(html);

 }

 }, "1000");
 */

var goodsUtil = {};
goodsUtil.outputFun = {};
goodsUtil.data = null;
goodsUtil.chooseArr = [];
goodsUtil.merid = null;
goodsUtil.type=1;
goodsUtil.closeShow = function() {
	layer.close(goodsUtil.modal);

};
goodsUtil.modal = null;
//添加商品
goodsUtil.addGoods=function(){
	
	goodsUtil.addGoodsModal(function(){
		
		
	});
};
goodsUtil.addImgs=function(obj){
	imgUtil
	.init(
			function() {
				if (this.chooseArr != null
						&& this.chooseArr.length > 0) {
					
					var html=showImgHtml(this.chooseArr);
					

					$(obj).before(html);
					
					

				}

			}, merid);
	
	
};
//添加规格
goodsUtil.addGoodsSibs=function(obj){
	var len=$(obj).siblings(".goodggedit").length;
	if(len<4){
		var html=goodsUtil.showGoodsSibs();
		$(obj).before(html);
	}else{
		layer.msg("最多添加三种规格");
	}
	
	goodsUtil.showSiblist();
	
};
goodsUtil.deletesib=function(obj){
	$(obj).closest(".layui-elem-field").remove();
	//组合成姐妹商品
	goodsUtil.showSiblist();
};
goodsUtil.delthisvalue=function(obj){
	$(obj).closest(".onesib_value").remove();
	//组合成姐妹商品
	goodsUtil.showSiblist();
}
goodsUtil.showVlaueHtml=function(value){
	var v='';
	if(value){
		v=value;
	}
	var html='<div class="onesib_value"><input onblur="goodsUtil.showSiblist()" placeholder="请填写" value="'+v+'" class="layui-btn layui-btn-primary width150 sib_value" type="text"><i class="layui-icon" onclick="goodsUtil.delthisvalue(this)">&#x1006;</i></div>';

	return html;
}
goodsUtil.addSibValue=function(obj){
	
	
	$(obj).before(goodsUtil.showVlaueHtml());
	
	goodsUtil.showSiblist();
};
//规格明细
goodsUtil.findSibList=function(){
	var siblist=[];
	var modal=$(document.getElementById("addGoodsModal"));
	modal.find("tr.data").each(function(){
		var onesib={};
		onesib.price=0;
		if($(this).find(".price").val()!=""){
			onesib.price=parseInt(parseFloat($(this).find(".price").val())*100);
		}
		onesib.skuid="";
		if($(this).find(".skuid").val()!=""){
			onesib.skuid=$(this).find(".skuid").val().trim();
		}
		var len=modal.find("th.headth").length;
		for(var i=0;i<len;i++){
			
			onesib[modal.find("th.headth").eq(i).text().trim()]=$(this).find("td").eq(i).text().trim();
			
		}
		
		siblist.push(onesib);
		
	});
	
	return siblist;
	
};

//获取规格
goodsUtil.findSibs=function(){
	var sibs=[];
	$(document.getElementById("addGoodsModal")).find(".goodggedit").each(function(){
		var onesib_key="";
		if($(this).find(".onesib_key").val()!=""){
			onesib_key=$(this).find(".onesib_key").val();
		}
		
		
		var onesib_values=[];
		$(this).find(".sib_value").each(function(){
			if($(this).val()!=""){
				onesib_values.push($(this).val());
			}
			
			
		});
		if(onesib_key!=""&&onesib_values.length>0){
			sibs.push({onesib_key:onesib_key,onesib_values:onesib_values});
		}
	
		
	});
	
	return sibs;
	
};

//组合成姐妹商品
goodsUtil.showSiblist=function(good){
	var data=null;
	var nowprice=0;
	var nowskuid="";
	
	if(good){
		if(good.siblists){
			data=good.siblists;	
		}
		if(good.price){
			nowprice=good.price;	
		}
		if(good.skuid){
			nowskuid=good.skuid;	
		}
	}
	
	
	var sibs=goodsUtil.findSibs();
	
	//生成姐妹商品规格
	
	var html='';
	html+='<table class="layui-table">';
	html+=showThInfo(sibs);//表头
	if(data){
		html+=showTbodydata(sibs,data);
	}else{
		
		html+=showTbodydata(sibs,null,nowprice,nowskuid);
	}
	
	
	html+='</table>';
	
	
	
	$(document.getElementById("siblist")).html(html);
	
};
//表头数据
function showThInfo(sibs){
	var html='';
	html+='<tr class="head">';
	for(var i=0,len=sibs.length;i<len;i++){
		var onesib=sibs[i];
		if(onesib!=null&&onesib.onesib_key){
			html+='<th class="headth">'+onesib.onesib_key+'</th>';
		}
		
		
		
	}
	html+='<th><b class="red">*</b>价格设置</th>';
	
	html+='<th>商品编号</th>';
	html+='</tr>';
	
	return html;
	
}
/**
* 笛卡尔乘积
*/
function Cartesian(a,b){  
    var ret=[];  
    for(var i=0; i<a.length;i++){  
        for(var j=0;j<b.length;j++){  
            ret.push(array(a[i],b[j]));  
        }  
    }  
    return ret;  
}  
function array(a,b){  
    var ret=[];  
    if(!(a instanceof Array)){  
        ret=Array.call(null,a);  
    }  
    else{  
        ret=Array.apply(null,a);  
    }  
    ret.push(b);  
    return ret;  
}  
function multiCartesian(data){  
    var len=data.length;  
    if(len==0){  
        return [];  
    }  
    else if(len==1){  
        return data[0];  
    }  
    else{  
        var ret=data[0];  
        for(var i=1;i<len;i++){  
            ret=Cartesian(ret,data[i]);  
        }  
        return ret;  
    } 
}; 
/**
 * 
 * @param sibs
 * @param data,
 * @param nowprice,单品价格
 * @param nowskuid,单品sku
 * @returns {String}
 */
function showTbodydata(sibs,data,nowprice,nowskuid){
	var arr=[];
	var index=[];
	if(sibs){
		for(var i=0,len=sibs.length;i<len;i++){
			var onesib=sibs[i];
			if(onesib&&onesib.onesib_values&&onesib.onesib_values.length>0){
				arr.push(onesib.onesib_values);
			}
		}
	}
	if(arr.length>0){
		index=multiCartesian(arr);  
	}
	var html='';
	if(index&&index.length>0){
		
		for(var x=0,xlen=index.length;x<xlen;x++){
			var onevalues=index[x];
			html+='<tr class="data">';
			if(onevalues instanceof Array){
				for(var y=0,ylen=onevalues.length;y<ylen;y++){
					var vl=onevalues[y];
					
					html+='<td>'+vl+'</td>';
					
					
				}
			}else{
				html+='<td>'+onevalues+'</td>';
			}
			
			var price=0;
			var skuid="";
			if(data){
				var onedata=goodsUtil.chooseDataPrice(data,onevalues);
				price=onedata.price;
				skuid=onedata.skuid;
			}
			
			html+='<td><input class="layui-input width100 price" value="'+parseFloat(price)/100+'"  type="text"  placeholder="￥，单位:元"></td>';
			
			html+='<td><input class="layui-input width100 skuid" value="'+skuid+'" type="text" placeholder="请输入商品编号"></td>';
			html+='</tr>';
			
		}
	}else{
		html+='<tr>';
		html+='<td><input class="layui-input width100 price"  value="'+parseFloat(nowprice)/100+'" type="text" placeholder="￥，单位:元"></td>';
		 
		html+='<td><input class="layui-input width100 skuid"  value="'+nowskuid+'" type="text" placeholder="请输入商品编号"></td>';
		html+='</tr>';
		
	}
	
	return html;
}

goodsUtil.chooseDataPrice=function(arr,one){
	if(arr){
		for(var i=0,len=arr.length;i<len;i++){
			var oned=JSON.stringify(arr[i]);
			if(one instanceof Array){
				var is=true;
				for(var j=0,jlen=one.length;j<jlen;j++){
					var onestr=one[j];
					if(oned.indexOf(onestr)==-1){
						is=false;
						break;
					}
					
				}
				if(is){
					return arr[i];
				}
				
			}else{
				if(oned.indexOf(one)!=-1){
					return arr[i];
				}
				
			}
			
		}
		
	}
	
	return null;
	
}
//规格
goodsUtil.showGoodsSibs=function(onesib){
	var onesib_key='';
	var onesib_values=[];
	if(onesib){
		onesib_key=onesib.onesib_key;
		onesib_values=onesib.onesib_values;
	}
	var html='';
	html+='<fieldset class="layui-elem-field goodggedit">';
	html+='<i class="fa fa-trash-o fa-lg" onclick="goodsUtil.deletesib(this)"></i>';
		html+=' <legend><span>规格名：</span><input class="layui-btn layui-btn-primary width150 onesib_key" placeholder="请填写规格" type="text" value="'+onesib_key+'"></legend>';
		html+=' <div class="layui-field-box">';
	  for(var i=0,len=onesib_values.length;i<len;i++){
		  var onevalue=onesib_values[i];
		  html+=goodsUtil.showVlaueHtml(onevalue);
	  }
	  
	html+=' <a class="addsibvaluebtn" onclick="goodsUtil.addSibValue(this)">添加规格值</a>';
		html+=' </div>';
			html+='</fieldset>';
				html+='<br>';
				
				
				return html;
};

//添加/编辑商品
goodsUtil.addGoodsModal=function(fun,good){

	var html='<br>';
	    html+='<div class="layui-form" id="addGoodsModal">';
	    
	    
	   
    	
	    html+=' <div class="layui-form-item">';
	    	html+=' <label class="layui-form-label"><b class="red">*</b>商品名称</label>';
	    		html+=' <div class="layui-input-block" id="onecouponedit">';
	    		var name='';
	    		if(good&&good.name){
	    			name=good.name;
	    		}
	    		
	    		 html+='<input name="name" value="'+name+'" id="name" autocomplete="off" placeholder="请输入商品名称" class="layui-input" type="text">';
	    html+='</div>';
	    	html+='</div>';
	    	
	    	
	    	  html+=' <div class="layui-form-item">';
		    	html+=' <label class="layui-form-label"><b class="red"></b>商品描述</label>';
		    		html+=' <div class="layui-input-block" id="onecouponedit">';
		    		
		    		var note='';
		    		if(good&&good.note){
		    			note=good.note;
		    		}
		    		
		    		 html+='<input name="note" value="'+note+'" id="note" autocomplete="off" placeholder="请输入商品说明" class="layui-input" type="text">';
		    html+='</div>';
		    	html+='</div>';
	    
	    	
		    	  html+=' <div class="layui-form-item">';
			    	html+=' <label class="layui-form-label"><b class="red"></b>商品图片</label>';
			    		html+=' <div class="layui-input-block" id="onecouponedit">';
			    		
			    		if(good&&good.imgs){
			    			html+=showImgHtml(good.imgs);
			    		}
			    		
			    		html+='<div onclick="goodsUtil.addImgs(this)" class="layui-btn layui-btn-sm layui-btn-primary addimgbtn"><i class="fa fa-plus"></i>添加</div>';
		    			
			    html+='</div>';
			    	html+='</div>';
			    	
			    	
		    	
          html+='<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">';
        	  html+=' <legend>商品规格</legend>';
        		  html+=' </fieldset>';
        		  
        		  
		    	 html+=' <div class="layui-form-item">';
			    	html+=' <label class="layui-form-label">商品规格</label>';
			    		html+=' <div class="layui-input-block">';
			    		
			    		if(good&&good.sibs){
			    			for(var i=0,len=good.sibs.length;i<len;i++){
			    				var oneg=good.sibs[i];
			    				html+=goodsUtil.showGoodsSibs(oneg);
			    			}
			    		}
			    		
			    		
			    		
			    		
			    			html+='<div  onclick="goodsUtil.addGoodsSibs(this)" class="layui-btn layui-btn-sm layui-btn-primary"><i class="fa fa-gg"></i>添加规格</div>';
			    			
			    			html+='</div>';
			    	html+='</div>';
			    	
			    	
			    	  
			    	 html+=' <div class="layui-form-item">';
				    	html+=' <label class="layui-form-label">规格明细</label>';
				    		html+=' <div class="layui-input-block" id="siblist">';
				    		
				    		
				    		
				    		
				    		
				    		
				    			
				    			html+='</div>';
				    	html+='</div>';
			    	
			    	 
			    	
	    
	    html+='</div>';
	
	    var addGoodsModal= layer.open({
			type : 1,
			title : "编辑商品",
			skin : 'layui-layer-demo', //样式类名

			anim : 2,
			shadeClose : true, //开启遮罩关闭
			area : [ '650px', '500px' ],
			content : html,
			btn:["确认","取消"],
			yes:function(){
				if(good==null){
					good={};
				}
				
				
				var editmodal=$(document.getElementById("addGoodsModal"));
				var name=editmodal.find("#name").val();
				var note=editmodal.find("#note").val();
				if(name!=""){
					good.name=name;
				}else{
					layer.msg("请输入商品名称");
					return;
				}
				if(note!=null){
					good.note=note;
				}
				var imgs=[];
				editmodal.find(".onegimg").each(function(){
					imgs.push($(this).attr("data"));
				});
				good.imgs=imgs;
				
				//上下架
				good.state=1;
				
				if(editmodal.find(".goodggedit").length<1){
					
					//无规格
					var price=parseInt(parseFloat(editmodal.find(".price").val())*100);
					var skuid=editmodal.find(".skuid").val();
					if(editmodal.find(".price").val()==""){
						layer.msg("商品价格必须设置");
						return;
					}
					if(skuid==""){
						layer.msg("商品货号必须设置");
						return;
					}
					good.price=price;
					good.skuid=skuid;
					delete good.sibs;
					delete good.siblists;
				}else{
					//规格，说明
					var sibs=goodsUtil.findSibs();
					var siblists=goodsUtil.findSibList();
					good.sibs=sibs;
					good.siblists=siblists;
				}
				
				$.ajax({
					type:"post",
					url:getRootPath()+"/mgood/editGoods.do",
					data:{data:JSON.stringify(good),merid:merid},
					dataType:"text",
					success:function(data){
						
						fun();
						
					}
				});
				
				layer.close(addGoodsModal);
				
			},btn2:function(){
				
				layer.close(addGoodsModal);
			}
		});
	    goodsUtil.showSiblist(good);
	
	    form.render();
	
	
	
}
goodsUtil.init = function(obj, merid,type) {
	this.type=type;
	this.outputFun = obj;

	var html = '';
	html += '<div id="goodsUtilmodal" >';

	
	

	html += '<div class="goodsarea">';
	
	

	html += '</div>';


	
	html += '</div>';

	goodsUtil.modal = layer.open({
		type : 1,
		title : "商品列表",
		skin : 'layui-layer-demo', //样式类名

		anim : 2,
		shadeClose : true, //开启遮罩关闭
		area : [ '750px', '450px' ],
		content : html,
		btn:['确认','取消'],
		yes:function(){
			
			goodsUtil.returnImg();
			
			
			
		},btn2:function(){
			layer.close(goodsUtil.modal);
		}
	});

	goodsUtil.merid = merid;

	this.showCoupon({
		merid:merid

	});

};
goodsUtil.editGoods=function(obj){
	var  good=eval("("+$(obj).closest(".onegood").attr("data")+")");
	goodsUtil.addGoodsModal(function(){
		
		goodsUtil.gopage(1);
	},good);
}
goodsUtil.showCoupon = function(dirobj) {
	dirobj.merid = goodsUtil.merid;

	$
			.ajax({

				type : "post",
				url : getRootPath() + "/mgood/ajaxMgoods.do",
				data : dirobj,
				dataType : "json",
				success : function(data) {
					
					goodsUtil.data = data;
					var html = '';

					if (data != null && data.list != null
							&& data.list.length > 0) {

						var list = data.list;
						var pathurrl=getRootPath();
						for ( var i = 0, len = list.length; i < len; i++) {
							
							
							var one = list[i];
							
							html += '<div class="onegood" onclick="goodsUtil.chooseImg(this)" data=\''
								+JSON.stringify(one)+ '\'>';
							
							html+='<i class="edit fa fa-edit" onclick="goodsUtil.editGoods(this)" >编辑</i>';
							
							if(one.imgs&&one.imgs.length>0){
								html+='<div class="imgshow"><img src="'+pathurrl+one.imgs[0]+'"></div>';
								
								
							}
							html+='<div class="leftdiv">';
							
							html += '<label>' + one.name + '</label>';
							var price=0;
							if(one.price){
								price=one.price;
							}else if(one.siblists[0]&&one.siblists[0].price){
								
								price=one.siblists[0].price;
							}
							
							
							html += '<label class="price">' +(parseFloat(price)/100).toFixed(2) + '元/份</label>';
							 html += '</div>';
							
							
							
						    html += '<div class="arrow"><div class="arrowfont"></div></div>';
						    html += '</div>';

						}

						

					

						html += '<div class="weiimg">';
						html+='<a  class="layui-btn layui-btn-primary addgoods" onclick="goodsUtil.addGoods();"><i class="fa fa-plus"></i>添加商品</a>';

						html += '<div class="pagebtns">';
						html += '<span class="fpage">共' + data.rowCount
								+ '个</span>';
						html += '<span class="fpage">' + data.pageNum + '/'
								+ data.pageCount + '页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage(1)">首页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage('
								+ (parseInt(data.pageNum) - 1)
								+ ')">上一页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage('
								+ (parseInt(data.pageNum) + 1)
								+ ')">下一页</span>';
						html += '<span class="fpage fbtn" onclick="goodsUtil.gopage('
								+ parseInt(data.pageCount) + ')">尾页</span>';
						html += '<input type="text" class="fpage fbtn" value="'
								+ data.pageNum + '" >';
						html += '<span class="fpage fbtn" id="gopagenum"  onclick="goodsUtil.gopage2(this)">跳转</span>';

						html += '</div>';

					} else {

						

						html+="<p>还没有商品</p>";
						html+='<a  class="layui-btn layui-btn-primary addgoods" onclick="goodsUtil.addGoods();"><i class="fa fa-plus"></i>添加商品</a>';


					}
					var dom = $(document.getElementById("goodsUtilmodal")).find(
							".goodsarea");

					dom.html(html);

				}

			});

};

goodsUtil.gopage = function(num) {
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
		merid:goodsUtil.merid
	});

};

goodsUtil.gopage2 = function(obj) {
	var num = $(obj).closest("div").find("input").val();
	this.gopage(num);
};

goodsUtil.chooseImg = function(obj) {

	if(this.type==1){
		$(obj).siblings(".onegood").removeClass("checkon");
		$(obj).toggleClass("checkon");
	}else{
		
		$(obj).toggleClass("checkon");
	}
	
	

};







goodsUtil.returnImg = function() {

	var arr = [];
	$(".onegood.checkon").each(function() {

		arr.push(eval("("+$(this).attr("data")+")"));

	});
	if(arr.length<1){
		
		
		return;
		
	}else{

		this.chooseArr = arr;
		layer.close(goodsUtil.modal);

		this.outputFun();
	}
	
	

	
};


goodsUtil.deleteThisChooseGoods=function(obj){
	$(obj).closest(".oneChooseGood").remove();
	
}

