/**
 * 初始化
 * @param fun
 * @param obj
 * @param time
 * @returns {initLoadM}
 */
function initLoadM(fun,obj,limittime,nowtime){
	//最长时间，
	this.limittime=limittime;
	this.nowtime=nowtime;//开始时间

	this.loadobj=obj;
	
	this.fun=fun;
	this.showHours=function(obj,num2,n2){

	  	var n=30;
	  	var num=2*num2/(n2/n);
	  	
	    var aEle = obj.children,
	        Rdeg = num > n ? n : num,
	        Ldeg = num > n ? num - n : 0;
	   // aEle[2].innerHTML = "<span>"+parseInt(num2/n2*100)+"</span>";
     aEle[2].innerHTML = "<span>"+(n2-num2)+"</span>";
	    aEle[1].children[0].style.transform = "rotateZ("+ (360/(2*n)*Rdeg-180) +"deg)";
	    aEle[0].children[0].style.transform = "rotateZ("+ (360/(2*n)*Ldeg-180) +"deg)";
	  
		
	};
}

initLoadM.prototype.init=function(){
	var loaddom=this;
	if(loaddom.timeobj!=null){
		clearInterval(loaddom.timeobj);
	}
	//还原
	loaddom.nowtime=0;
	if(loaddom.limittime>=0){
		
		loaddom.timeobj=setInterval(function(){

			loaddom.nowtime+=1;

			loaddom.showHours(loaddom.loadobj,loaddom.nowtime,loaddom.limittime);
		  
		  if(loaddom.nowtime==loaddom.limittime){
		  	
		  	clearInterval(loaddom.timeobj);
		  
		  	loaddom.loadobj.children[2].style.color="#ffffff";
		  	loaddom.loadobj.style.opacity=0.8;
		  	loaddom.loadobj.style.backgroundColor="#FFFFFF";
		  	
		  	loaddom.loadobj.children[2].style.background="transparent";
		  	loaddom.loadobj.children[2].style.fontSize="15px";
		  	
		  	loaddom.fun();
		  	
		  }

			
		},1000);
	}
};
/*停止*/
initLoadM.prototype.stop=function(){
	var loaddom=this;
	clearInterval(loaddom.timeobj)
}
