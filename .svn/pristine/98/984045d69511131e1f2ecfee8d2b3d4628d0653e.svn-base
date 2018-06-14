/**
 * 加载行业
 */
function ajaxIndustry(obj){
	
	$.getJSON(getRootPath()+"/assets/file/industry.json",function(result){
	     if(result){
	    	 var json=formatTo(result);
	    	 var html='';
	    	html+='<option value="">请选择行业</option>';
	          
	            
	    	 for(var i=0,len=json.length;i<len;i++){
	    		 var one=json[i];
	    		 if(one.subs&&one.subs.length>0){
	    			 html+='<optgroup label="'+one.name+'">';
	    			 var subs=one.subs;
	    			 for(var j=0,jlen=subs.length;j<jlen;j++){
	    				 var subone=subs[j];
	    					html+='<option value="'+subone.id+'">'+subone.name+'</option>';
	    				 
	    			 }
	    			 
	    			 
	    			 html+='</optgroup>';
	    		 }else{
	    			 
	    			 html+='<option value="'+one.id+'">'+one.name+'</option>';
	    		 }
	    		 
	    		 
	    		 
	    	 }
	    	 
	    	 obj.html(html);
	    	 form.render();
	    	 
	     }
		
		
		
	});
	
}
function formatTo(json){
	var arr0=[];
	for(var i=0,len=json.length;i<len;i++){
		 var one=json[i];
		 if(one.pId==0){
			 
			 var array1=chooseSub(json,one.id);
			 if(array1.length>0){
				 one.subs=array1;
			 }
			
			 arr0.push(one);
		 }
		 
	}
	
	return arr0;
	
}
function chooseSub(json,id){
	var arr0=[];
	for(var i=0,len=json.length;i<len;i++){
		 var one=json[i];
		 if(one.pId==id){
			 
			
			 
			
			 arr0.push(one);
		 }
		 
	}
	
	return arr0;
}
/**
 * 加载消息分类
 */
function ajaxMessType(obj){
	
	$.getJSON(getRootPath()+"/assets/file/messType.json",function(result){
	     
		if(result){
			 var html='';
		    html+='<option value="">请选择分类</option>';
		          
			for(var i=0,len=result.length;i<len;i++){
				 var one=result[i];
				 html+='<option value="'+one.id+'">'+one.name+'</option>';
				 
			}
			
			
			 
	    	 obj.html(html);
	    	 form.render();
		}
		
		
		
		
		
	});
	
}