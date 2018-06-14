var bpath=getRootPath();
var $;
layui.use([ 'form', 'layer', 'jquery', 'table' ], function() {
	var form = layui.form, layer = parent.layer === undefined ? layui.layer
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
			width : '25%',
			title : '模板名称'
		}, {
			field : 'expireType',
			width : '25%',
			title : '有效期',
			templet : '#fwdate'
		}, {
			field : 'ctype',
			
			title : '类型',
			templet : '#ctype'
		}, {
			field : 'state',
			title : '状态',
			width : 80,
			templet : '#state'
		}

		, {
			field : 'do',
			title : '操作',
			width : '25%',
			align : 'center',
			toolbar : '#barDemo'
		} ] ],
		page : true,
		url : bpath + "/mcoupon/ajaxCouponModals.do",
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
		    	
		      layer.confirm('删除该券模板吗?', function(index){
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




function orderinfo(id){


	var index=layer.open({
        type: 2 //此处以iframe举例
        ,title: '券模板使用详情'
        ,area: ['900px', '460px']
        ,shade: 0
        ,maxmin: true
        ,offset: [ //为了演示，随机坐标
        /*   Math.random()*($(window).height()-300)
          ,Math.random()*($(window).width()-390) */
          50,$(window).width()/2-390
        ] 
        ,content:bpath+'/mcoupon/queryCouponTemplateById.do?couponTemplateId='+id+'&buttonType=1'
//         ,btn: [/* '全部关闭' */] //只是为了演示
        ,yes: function(){
          $(that).click(); 
        }
        ,btn2: function(){
          layer.closeAll();
        }
        
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
          layer.setTop(layero); //重点2
        }
      });

	layer.full(index);
}

function addModal(url){
	var uu=bpath+'/mcoupon/addCouponModal.do';
	if(url){
		uu=url;
	}

	var index=layer.open({
	        type: 2 //此处以iframe举例
	        ,title: '券模板编辑'
	        ,area: ['900px', '460px']
	        ,shade: 0
	        ,maxmin: false
	        ,offset: [ //为了演示，随机坐标
	        /*   Math.random()*($(window).height()-300)
	          ,Math.random()*($(window).width()-390) */
	          50,$(window).width()/2-390
	        ] 
	        ,content:uu
//	         ,btn: [/* '全部关闭' */] //只是为了演示
	        ,yes: function(){
	          $(that).click(); 
	        }
	        ,btn2: function(){
	          layer.closeAll();
	        }
	        
	        ,zIndex: layer.zIndex //重点1
	        ,success: function(layero){
	          layer.setTop(layero); //重点2
	        },end:function(){
	        	
	        	 location.reload();
	        }
	      });
	
	layer.full(index);
}
