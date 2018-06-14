/**
 * 初始化加载金额
 * @param fun
 * @param obj
 * @param time
 * @returns {initLoadM}
 */
function initLoadM(fun,obj,time){
	//最长时间，
	this.limittime=time;
	this.nowtime=0;//开始时间

	this.loadobj=obj;
	
	this.fun=fun;
	this.showHours=function(obj,num2,n2){

	  	var n=30;
	  	var num=2*num2/(n2/n);
	  	
	    var aEle = obj.children,
	        Rdeg = num > n ? n : num,
	        Ldeg = num > n ? num - n : 0;
	    aEle[2].innerHTML = "<span>"+parseInt(num2/n2*100)+"%</span>";

	    aEle[1].children[0].style.transform = "rotateZ("+ (360/(2*n)*Rdeg-180) +"deg)";
	    aEle[0].children[0].style.transform = "rotateZ("+ (360/(2*n)*Ldeg-180) +"deg)";
	  
		
	};
}

initLoadM.prototype.init=function(){
	var loaddom=this;
	if(loaddom.limittime>=0){
		
		loaddom.timeobj=setInterval(function(){

			loaddom.nowtime+=0.5;

			loaddom.showHours(loaddom.loadobj,loaddom.nowtime,loaddom.limittime);
		  
		  if(loaddom.nowtime==loaddom.limittime){
		  	
		  	clearInterval(loaddom.timeobj);
		  
		  	loaddom.loadobj.children[2].style.color="#ffffff";
		  	loaddom.loadobj.style.opacity=0.8;
		  	loaddom.loadobj.style.backgroundColor="#1da1f4";
		  	
		  	loaddom.loadobj.children[2].style.background="transparent";
		  	loaddom.loadobj.children[2].style.fontSize="15px";
		  	
		  	loaddom.fun();
		  	
		  }

			
		},500);
	}
};
