// 模糊搜索
	var likename = "";
	$("#likename").val("");
var bpath=getRootPath();
var table=null;
var state=99;//全部课程
var json = {
		elem : '#tablearea99',
		
		
		cols : [ [ {
			type : 'numbers',
				width : '5%'
		}, {
			field : 'username',
			width : '10%',
			title : '姓名'
		},
		{	field : 'fromwhere',
			title : '学校',
			width : '20%',
		},
		
		{	field : 'user_suc_num',
			title : '答对数目',
			width : '10%',
		},
		{
			field : 'exp',
			width : '10%',
			title : '获得经验',
			style:'display:block;height:auto',
			
			
		}, {
			field : 'jf',
			title : '获得积分',
			width : '10%',
		},
		
		 {
			field : 'star',
			title : '获得加星',
			width : '10%',
		},
		
		
		 {	field : 'time',
			
				title : '时间',
				width : '40%',
			}

		 ] ],
		page : true,
		url : bpath + "/school/query.do",
		where : {
			likename : likename,
			state:state
		},
		limit : 30
	};
layui.use([ 'form', 'layer', 'jquery', 'table','element' ], function() {
	var form = layui.form, layer = parent.layer === undefined ? layui.layer
			: parent.layer,element = layui.element;
	
	table= layui.table;

	table.render(json);

	$(".search_btn").on("click", function() {
		var name =$("#likename").val();
		 
		if ($("#likename").val() != null && $("#likename").val().trim() != "") {
			likename = $("#likename").val().trim();
		}
		json.where.likename = likename;
		 
		json.where.state=state;
		json.elem="#tablearea"+state;
		table.render(json);
	});
	
	
	
	 

});

