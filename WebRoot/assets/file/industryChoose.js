var industryChoose={};
industryChoose.outputFun = {};
industryChoose.modal=null;
industryChoose.setting = {
		view: {
				showIcon: industryChoose.showIconForTree
			},
			check: {
				enable: true
			},
			
			data: {
				simpleData: {
					enable: true
				}
			}
		};
//节点
industryChoose.zNodes=[];
//选中后的数据
industryChoose.choose_arr=[];


industryChoose.init = function(obj) {
	this.outputFun = obj;
	//<!-- 选择行业  -->


		var html='';
		html+='<div class="industry_body" style="width: 100%;">';
		html+='<ul id="treeDemo" class="ztree"></ul>';
		html+='</div>';
		industryChoose.modal = layer.open({
			type : 0,
			title : "选择行业",
			skin : 'layui-layer-demo', //样式类名
			btnAlign:"c",
			anim : 2,
			shadeClose : true, //开启遮罩关闭
			area : [ '300px', '400px' ],
			content : html,
			id:"12121",
			yes:function(){

				
				var id_arr =[];
				 
				$(".checkbox_true_full").each(function(){
					var id = $(this).attr("id");
					id = id.replace(/[^0-9]/ig,"");
					id_arr.push(Number(id));
				});
				
				$(".checkbox_true_part").each(function(){
					var id = $(this).attr("id");
					id = id.replace(/[^0-9]/ig,"");
					id_arr.push(Number(id));
				});
				
				if(id_arr.length< 1){ 
					layer.msg('请选择合适的行业！', {
				        time: 5000, //5s后自动关闭
				        btn: [ '知道了']
				      });
					return false;
				}else{
					var zNodes=industryChoose.zNodes;
					var choose_arr=[];
					for(var i=0;i<zNodes.length;i++){
						zNodes[i].checked=false;
						
						for(var j=0;j<id_arr.length;j++){
							
							if(id_arr[j] == zNodes[i].id){
								zNodes[i].checked=true;
								choose_arr.push({"id":zNodes[i].id,"name":zNodes[i].name});
								
								//将选择的行业显示出来
								break;
							}
						}
					}
					industryChoose.choose_arr=choose_arr;
					//执行外部方法
					industryChoose.outputFun();
					industryChoose.close_industry();
				}
				
				//console.log(JSON.stringify(choose_arr));

				
			}
		});	
		
	

		$.fn.zTree.init($(document.getElementById("treeDemo")), industryChoose.setting, industryChoose.zNodes); 
	
	
};


industryChoose.close_industry=function(){
	 layer.close(industryChoose.modal);
}





industryChoose.showIconForTree=function(treeId, treeNode) {
	return false;
};


$.ajax({  
    url : getRootPath() + "/assets/file/industry.json",  
    datatype: "json",        
    async : false,  
    data :{},  
    success : function(result) {  
    	industryChoose.zNodes=result; 
    	
		
    }  
 }); 

