function nst(t)
{
   var stmp = "";
   if(t.value==stmp) return;
   var ms = t.value.replace(/[^\d\.]/g,"").replace(/(\.\d{2}).+$/,"$1").replace(/^0+([1-9])/,"$1").replace(/^0+$/,"0");
   var txt = ms.split(".");
   while(/\d{4}(,|$)/.test(txt[0]))
     txt[0] = txt[0].replace(/(\d)(\d{3}(,|$))/,"$1,$2");
   t.value = stmp = txt[0]+(txt.length>1?"."+txt[1]:"");
   $(t).attr("data",ms-0);
}

function nstchange(ojb){
	
//var a_value=document.getElementById(obj).value;
var a_value=ojb.value;
//alert(a_value);
var a_leng=a_value.length;

if(a_leng>0){
//alert(a_value.substr(0,1));
	if (a_value.substr(0,1)=="￥"){
	//document.all.aaa.value="￥"+document.all.aaa.value;
	//alert(a_value.substr(1,a_leng)) ;
	//document.getElementById(obj).value=a_value.substr(1,a_leng);
	ojb.value=a_value.substr(1,a_value.length); 
	}
	}
}
function nstblurchange(ojb2){
//document.getElementById(obj).value="￥"+document.getElementById(obj).value;
ojb2.value="￥"+ojb2.value;
}