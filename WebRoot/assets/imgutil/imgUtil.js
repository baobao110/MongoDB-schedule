/**
 * 调用方法:imgUtil.init(fun,merid);
 * imgUtil.init(
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
 + '" onclick="imgUtil.showLgImg(this)">';
 html += '<div class="delimg" onclick="imgUtil.delthisimg(this)"></div></div>';

 }

 $(obj).before(html);

 }

 }, "1000");
 */

var imgUtil = {};
imgUtil.outputFun = {};
imgUtil.data = null;
imgUtil.chooseArr = [];
imgUtil.bpath = "/assets/img/";
imgUtil.nowpath = "";
imgUtil.merid = null;
imgUtil.closeShow = function() {
	layer.close(imgUtil.modal);

};
imgUtil.modal = null;

imgUtil.init = function(obj, merid) {

	this.outputFun = obj;

	var html = '';
	html += '<div id="imgUtilmodal" >';

	html += '<div class="imgmain" >';

	html += '<div class="imgarea">';

	html += '</div>';

	html += '<div class="obtn choosebtn"  onclick="imgUtil.returnImg()">确定</div>';
	html += '<div class="obtn cancelbtn" onclick="imgUtil.closeShow()">取消</div>';
	html += '<div class="obtn delbtning" onclick="imgUtil.delimg()">删除</div>';
	html += '</div>';

	html += '<div id="filediv">';
	html += '<form id="imgfrom" >';
	html += '<input type="hidden" id="pathhiden" name="imgpath">';
	html += '<input type="file" name="file" id="uploadimg"  accept="image/*" multiple="multiple"  onchange="imgUtil.ajaxUploadImg(this)">';

	html += '</form>';
	html += '</div>';

	html += '</div>';

	imgUtil.modal = layer.open({
		type : 1,
		title : "图片管理",
		skin : 'layui-layer-demo', //样式类名

		anim : 2,
		shadeClose : true, //开启遮罩关闭
		area : [ '700px', '450px' ],
		content : html
	});

	imgUtil.merid = merid;
	this.nowpath = this.bpath + merid + "/";

	this.showimg({
		imgpath : this.nowpath

	});

}
imgUtil.showimg = function(dirobj) {
	dirobj.merid = imgUtil.merid;

	$
			.ajax({

				type : "post",
				url : getRootPath() + "/img/queryImgs.do",
				data : dirobj,
				dataType : "json",
				success : function(alldata) {
					if (alldata == "400") {
						return;
					}
					imgUtil.data = alldata;
					var html = '';
					var data = alldata.pb;

					if (data != null && data.list != null
							&& data.list.length > 0) {

						var list = data.list;
						for ( var i = 0, len = list.length; i < len; i++) {
							var one = list[i];
							if (one.type == "file") {
								html += '<div class="oneimg" onclick="imgUtil.chooseImg(this)" data="'
										+ one.name + '">';
								html += '<img src="' + getRootPath() + one.name
										+ '">';
								html += '<span>' + one.sname + '</span>';
								html += '<div class="arrow"><div class="arrowfont"></div></div>';
								html += '</div>';

							} else {

								html += '<div class="onedir" data="'
										+ one.name
										+ '"  onclick="imgUtil.chooseImg(this)"   ondblclick="imgUtil.showSubImg(\''
										+ one.name + "/" + '\')">';
								html += '<img src="' + getRootPath()
										+ '/assets/imgutil/dir.jpg">';
								html += '<span>' + one.sname + "/" + '</span>';
								html += '<div class="arrow"><div class="arrowfont"></div></div>';
								html += '</div>';
							}

						}

						if (dirobj && dirobj.imgpath) {

							imgUtil.nowpath = dirobj.imgpath;

							var arr = dirobj.imgpath.split("/");
							var len = arr.length;
							if (len > 5) {
								var pstr = "";
								for ( var j = 0; j < len - 2; j++) {
									pstr += arr[j] + "/";
								}

								html += '<div class="onetuichu"  onclick="imgUtil.showSubImg(\''
										+ pstr + '\')">';
								html += '</div>';
							}
						}

//						html += '</div>';

						html += '<div class="weiimg">';

						html += '<div class="obtn  addbtning" onclick="imgUtil.openfile()">上传图片</div>';
						html += '<div class="obtn  addbtning" onclick="imgUtil.createFileDir()" style="margin-left:10px;background:#679998">新建文件夹</div>';
						html += '<div class="pagebtns">';
						html += '<span class="fpage">共' + data.rowCount
								+ '个</span>';
						html += '<span class="fpage">' + data.pageNum + '/'
								+ data.pageCount + '页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage(1)">首页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage('
								+ (parseInt(data.pageNum) - 1)
								+ ')">上一页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage('
								+ (parseInt(data.pageNum) + 1)
								+ ')">下一页</span>';
						html += '<span class="fpage fbtn" onclick="imgUtil.gopage('
								+ parseInt(data.pageCount) + ')">尾页</span>';
						html += '<input type="text" class="fpage fbtn" value="'
								+ data.pageNum + '" >';
						html += '<span class="fpage fbtn" id="gopagenum"  onclick="imgUtil.gopage2(this)">跳转</span>';

						html += '</div>';

					} else {

						if (dirobj && dirobj.imgpath) {

							imgUtil.nowpath = dirobj.imgpath;

							var arr = dirobj.imgpath.split("/");
							var len = arr.length;
							if (len > 5) {
								var pstr = "";
								for ( var j = 0; j < len - 2; j++) {
									pstr += arr[j] + "/";
								}

								html += '<div class="onetuichu"  onclick="imgUtil.showSubImg(\''
										+ pstr + '\')">';

								html += '</div>';
							}

						}
//						html += '</div>';

						html += '<div class="weiimg">';

						html += '<div class="obtn  addbtning" onclick="imgUtil.openfile()">上传图片</div>';
						html += '<div class="obtn  addbtning" onclick="imgUtil.createFileDir()" style="margin-left:10px;background:#679998">新建文件夹</div>';

					}
					var dom = $(document.getElementById("imgUtilmodal")).find(
							".imgarea");

					dom.html(html);

				}

			});

};

imgUtil.gopage = function(num) {
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

	this.showimg({
		page : num,
		imgpath : this.nowpath
	});

};

imgUtil.gopage2 = function(obj) {
	var num = $(obj).closest("div").find("input").val();
	this.gopage(num);
};
imgUtil.showSubImg = function(dir) {

	this.showimg({
		imgpath : dir
	});
};
imgUtil.chooseImg = function(obj) {

	$(obj).toggleClass("checkon");

};

imgUtil.openfile = function() {

	var obj = document.getElementById("uploadimg");
	$(document.getElementById("pathhiden")).val(this.nowpath);

	$(obj).trigger("click");

};
imgUtil.ajaxUploadImg = function(obj) {
	var form = $(obj).closest("#imgfrom");

	var file = $(obj).val();
	if (file == "") {
		return;
	}

	var formData = new FormData(form[0]);

	$.ajax({
		type : "post",
		url : getRootPath() + "/img/uploadImg.do",
		data : formData,
		dataType : 'text',
		cache : false,
		processData : false,
		contentType : false,
		success : function(data) {
			if (data != null) {
				layer.msg("上传成功");
			}
			if (document.getElementById("gopagenum") != null) {
				$(document.getElementById("gopagenum")).trigger("click");
			} else {
				imgUtil.gopage(1);
			}

		},

	});

};

imgUtil.createFileDir = function() {
	var pp = this.nowpath;

	$.ajax({
		type : "post",
		url : getRootPath() + "/img/createDir.do",
		data : {
			npath : pp
		},
		dataType : "text",
		success : function(data) {
			if (data != null) {
				layer.msg("创建文件夹成功");
			}

			if (document.getElementById("gopagenum") != null) {
				$(document.getElementById("gopagenum")).trigger("click");
			} else {
				imgUtil.gopage(1);
			}
		}

	});
}

imgUtil.delimg = function() {

	var arr = [];
	$(".oneimg.checkon,.onedir.checkon").each(function() {

		arr.push($(this).attr("data"));

	});
	$.ajax({
		type : "post",
		url : getRootPath() + "/img/delimg.do",
		data : {
			arr : JSON.stringify(arr)
		},
		dataType : "text",
		success : function(data) {
			if (data == "200") {
				layer.msg("删除成功");
			} else {
				layer.msg("只能删除空文件夹");
			}

			if (document.getElementById("gopagenum") != null) {
				$(document.getElementById("gopagenum")).trigger("click");
			} else {
				imgUtil.gopage(1);
			}
		}

	});

};

imgUtil.returnImg = function() {

	var arr = [];
	$(".oneimg.checkon").each(function() {

		arr.push($(this).attr("data"));

	});
	this.chooseArr = arr;
	layer.close(imgUtil.modal);

	this.outputFun();

	//alert(JSON.stringify(arr));
};

imgUtil.delthisimg = function(obj) {
	if (obj) {
		$(obj).closest(".onegimg").remove()
	}
	;
};

imgUtil.showLgImg = function(obj) {
	layer.open({
		type : 1,
		title : false,
		closeBtn : 0,
		area : '80%',
		skin : 'layui-layer-nobg', //没有背景色
		shadeClose : true,
		content : $(obj)
	});

}
