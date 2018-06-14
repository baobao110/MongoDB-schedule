// 模糊搜索
	var likename = "";
	$("#likename").val("");
var bpath=getRootPath();
var table=null;
var state=99;//全部题目
var json = {
		elem : '#tablearea99',
		
		
		cols : [ [ {
			type : 'numbers',
				width : '10%'
		},{
			field : 'title',
			width : '70%',
			title : '竞答内容',
			style:'display:block;height:auto',
			templet : '#tmdetail'
			
		}, {
			field : 'ct',
			title : '操作',
			width : '20%',
			templet : '#dowork'
			
		}

		 ] ],
		page : false,
		url : bpath + "/jd/ajaxJdList.do",
		where : {
			likename : likename,
			merid : merid,
			state:state
		},
		limit : 30
		/*request: {
			  pageName: 'curr' //页码的参数名称，默认：page
			  ,limitName: 'nums' //每页数据量的参数名，默认：limit
			}    */
		/*response: {
			  statusName: 'status' //数据状态的字段名称，默认：code
			  ,statusCode: 200 //成功的状态码，默认：0
			  ,msgName: 'hint' //状态信息的字段名称，默认：msg
			  ,countName: 'total' //数据总数的字段名称，默认：count
			  ,dataName: 'rows' //数据列表的字段名称，默认：data
			}  */
	
	};
layui.use([ 'form', 'layer', 'jquery', 'table','element' ], function() {
	var form = layui.form, layer = parent.layer === undefined ? layui.layer
			: parent.layer,element = layui.element;
	
	table= layui.table;

	table.render(json);

	$(".search_btn").on("click", function() {
		
		if ($("#likename").val() != null && $("#likename").val().trim() != "") {
			likename = $("#likename").val().trim();
		}
		json.where.likename = likename;
		json.where.state=state;
		json.elem="#tablearea"+state;
		table.render(json);
	});
	
	 element.on('tab(docDemoTabBrief)', function(elem){
		   state=Number($(this).attr('data'));
		   if ($("#likename").val() != null && $("#likename").val().trim() != "") {
				likename = $("#likename").val().trim();
			}
			json.where.likename = likename;
			json.where.state=state;
			json.elem="#tablearea"+state;
			
			
			table.render(json);
	});
	
	
	
	  table.on('tool(tablearea)', function(obj){
		    var data = obj.data;
		    var id=data._id;
		    if(obj.event === 'detail'){
		        //发放记录，实际领取
		    	orderinfo(id);
		      
		    } else if(obj.event === 'del'){
		    	
		      layer.confirm('关闭该题目吗?', function(index){
		    	  $.ajax({
		    		  type:"post",
		    		  url:bpath+"/message/closeMessage.do",
		    		  data:{id:id},
		    		  dataType:"text",
		    		  success:function(data){
		    			  if(data=="200"){
		    				  obj.del();
		    				  layer.close(index);
		    			  }
		    		  }
		    	  });
		    	  
		       
		        
		        
		        
		      });
		    } else if(obj.event === 'edit'){
		    	
		    	
		    	var url=bpath+"/pages/sjpc/info/sendInfo.html?messid="+id;
		    	addModal(url);
		    }
		  });

});


function updateTMState(id,state){
	 $.ajax({
  		  type:"post",
  		  url:bpath+"/jd/updateJdState.do",
  		  data:{id:id,state:state},
  		  dataType:"text",
  		  success:function(data){
  			table.render(json);
			  layer.closeAll();
  		  }
  	  });
   	 
   	  
}





function editTM(id){
	
	

window.location.href=bpath+'/pages/pt/jd/sendJD.html?messid='+id;
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
