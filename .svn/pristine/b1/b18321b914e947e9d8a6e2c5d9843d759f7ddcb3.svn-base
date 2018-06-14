var bpath=getRootPath();
var $,form;
layui.use([ 'form', 'layer', 'jquery', 'table' ], function() {
	 form = layui.form, layer = parent.layer === undefined ? layui.layer
			: parent.layer;
	$ = layui.jquery;
	var table = layui.table;

	// 模糊搜索
	var likename = "";
	$("#likename").val("");
	var json = {
		elem : '#tablearea',
		
		
		cols : [ [ {
			type : 'numbers'

		}, {
			width : 50,

			checkbox : true

		}, {
			field : 'name',
			width : '35%',
			title : '商品'
		}, {
			field : 'state',
			width : '10%',
			title : '状态',
			templet : '#state'
		}, {
			field : '创建时间',
			width : '15%',
			title : '类型',
			templet : '#ctimestr'
		}
  		, {
			field : 'do',
			title : '操作',
			align : 'center',
			toolbar : '#barDemo'
		} ] ],
		page : true,
		url : bpath + "/mgood/ajaxMerGoods.do",
		where : {
			likename : likename,
			merid : merid
		},
		limit : 30
	};

	table.render(json);

	$(".search_btn").on("click", function() {
		
		if ($("#likename").val() != null && $("#likename").val().trim() != "") {
			likename = $("#likename").val().trim();
		}
		json.where.likename = likename;
		table.render(json);
	});
	
	
	
	
	  table.on('tool(tablearea)', function(obj){
		    var data = obj.data;
		    var id=data._id;
		    if(obj.event === 'detail'){
		        //发放记录，实际领取
		    	orderinfo(id);
		      
		    } else if(obj.event === 'del'){
		    	
		      layer.confirm('删除该商品吗?', function(index){
		    	  $.ajax({
		    		  type:"post",
		    		  url:bpath+"/mcoupon/deleteCModal.do",
		    		  data:{id:id},
		    		  dataType:"text",
		    		  success:function(data){
		    			  if(data=="200"){
		    				  obj.del();
		    				  layer.close(index);
		    			  }
		    		  }
		    	  })
		    	  
		       
		        
		        
		        
		      });
		    } else if(obj.event === 'edit'){
		    	
		    	
		    	var url=bpath+"/mcoupon/queryCouponTemplateById.do?modelid="+id;
		    	addModal(url);
		    }
		  });

});

function addModal(){
	
goodsUtil.addGoodsModal(function(){
		
	table.render(json);
	});
}

/**
 * 
 */
function editGoods(id){
	
	$.ajax({
		  type:"post",
		  url:bpath+"/mcoupon/queryOneMgoods.do",
		  data:{id:id},
		  dataType:"text",
		  success:function(data){
			  if(data=="200"){
				  obj.del();
				  layer.close(index);
			  }
		  }
	  })
}
