<%@page import="com.hysm.db.MessageDB"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
MessageDB mdb=new MessageDB();

JSONArray all=mdb.ajaxKcTitleList();
request.setAttribute("kc", all);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>题库录入</title>
    
	<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript" src="/ay/assets/url.js"></script>
<!-- OLYCBjuz0sbCGabBGbk6EdXjnENCATEo -->
<script type="text/javascript">

	var paths = [  {
		type : "link",
		path : "/assets/layui/css/layui.css"
	},{
		type : "link",
		path : "/assets/css/pc_mess/mess.css"
	},{
		type : "link",
		path : "/assets/ui/libs/bower/font-awesome/css/font-awesome.min.css"
	} ];

	addFilesSRC(paths);

	 var merid="1000";
</script>

</head>

<body style="background: #F1F1F2" id="bodyarea">
	<div class="layui-container">

		<div class="layui-row layui-form"
			style="width: 100%;display: inline-block;">

<input type="hidden" value="${tm._id}" id="tmid">
			<ul class="layui-timeline" id="allmodal" >
				<li class="layui-timeline-item">
					<div class="layui-timeline-content layui-text"></div></li>




				<li class="layui-timeline-item"><i
					class="layui-icon layui-timeline-axis" data="&#xe63c;">&#xe63c;</i>
					<div class="layui-timeline-content layui-text">
						<h3 class="layui-timeline-title">题目基本内容</h3>
						<div class="layui-tab-item layui-show">

				<div class="layui-form-item">
								<label class="layui-form-label"><b class="red" id="industryred">*</b>所属课程</label>
								<div class="layui-input-inline">
									<select lay-verify="required" id="kcid" class="width200">
									
									<c:forEach items="${kc}" var="one">
									    <option value="${one._id }"  <c:if test="${not empty tm&&tm.kcid eq one._id}">selected</c:if>>${one.title }</option>
									</c:forEach>
									
										
									</select>
								</div>
							</div>

							<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>问题</label>
								<div class="layui-input-block">
									<textarea placeholder="请输入内容" lay-verify="required"
										maxlength="512" class="layui-textarea width500" id="question">${tm.question }</textarea>
										
										
								</div>
							</div>
							

							



				
							

						</div>
					</div>
				</li>

<li class="layui-timeline-item"><i
					class="layui-icon layui-timeline-axis" data="&#xe63c;">&#xe63c;</i>
					<div class="layui-timeline-content layui-text">
						<h3 class="layui-timeline-title">题目选项</h3>
						<div class="layui-tab-item layui-show">

				
							<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>A</label>
								<div class="layui-input-block">
									<textarea placeholder="请输入选项内容" lay-verify="required"
										maxlength="512" class="layui-textarea width500" id="chooseA">${tm.chooseA }</textarea>
										
										
								</div>
							</div>
							
					<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>B</label>
								<div class="layui-input-block">
									<textarea placeholder="请输入选项内容" lay-verify="required"
										maxlength="512" class="layui-textarea width500" id="chooseB">${tm.chooseB }</textarea>
										
										
								</div>
							</div>
							
<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>C</label>
								<div class="layui-input-block">
									<textarea placeholder="请输入选项内容" lay-verify="required"
										maxlength="512" class="layui-textarea width500" id="chooseC">${tm.chooseC }</textarea>
										
										
								</div>
							</div>

<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>D</label>
								<div class="layui-input-block">
									<textarea placeholder="请输入选项内容" lay-verify="required"
										maxlength="512" class="layui-textarea width500" id="chooseD">${tm.chooseD }</textarea>
										
										
								</div>
							</div>
				
							

						</div>
					</div>
				</li>
				

				<li class="layui-timeline-item"><i
					class="layui-icon layui-timeline-axis" data="&#xe63c;">&#xe63c;</i>
					<div class="layui-timeline-content layui-text">
						<h3 class="layui-timeline-title">题目解析</h3>
						<div class="layui-tab-item layui-show">

				
							
							
					
				
							<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>正确答案</label>
								<div class="layui-input-block" style="width:100px">
								
									<select lay-verify="required" id="answer" >
									
									<option value="A"  <c:if test='${not empty tm&&tm.answer eq "A"}'>selected</c:if>>A</option>
									<option value="B"  <c:if test='${not empty tm&&tm.answer eq "B"}'>selected</c:if>>B</option>
									<option value="C"  <c:if test='${not empty tm&&tm.answer eq "C"}'>selected</c:if>>C</option>
									<option value="D"  <c:if test='${not empty tm&&tm.answer eq "D"}'>selected</c:if>>D</option>
									
										
									</select>
									
								</div>
							</div>
							
							<div class="layui-form-item">
								<label class="layui-form-label"><b class="red">*</b>答案解析</label>
								<div class="layui-input-block">
									<textarea placeholder="请输入选项内容" 
										maxlength="512" class="layui-textarea width500" id="info">${tm.info }</textarea>
										
										
								</div>
							</div>

						</div>
					</div>
				</li>



				
			</ul>


			<!-- <div style="margin-left:30px" onclick="addOneModal(this)" class="layui-btn  layui-btn-big layui-btn-normal"><i class="layui-icon">&#xe654;</i>添加选项</div>

 -->


		</div>
		<div class="layui-row" align="center"
			style="margin-bottom: 50px;margin-top: 20px" id="workdo">
			
			
			


		</div>
	</div>


<div style="display:none" id="uploadppt"></div>
</body>
<script type="text/javascript">

	var paths = [  {
		type : "src",
		path : "/assets/js/jquery-3.1.1.min.js"
	},{
		type : "src",
		path : "/assets/layui/layui.js"
	}
	
	
	
	
	//
	, {
		type : "src",
		path : "/assets/utils/mess/tmutil.js",
		version:2
	}
	,{
		type : "src",
		path : "/assets/login.js"

	} 
	
	];

	addFilesSRC(paths);
	
	
</script>
<script type="text/javascript">
	var messobj={};
	//商家id
	messobj.merid=merid;
	//编辑

	

</script>

<script type="text/javascript">

    var pptx="";
	var  form, layer, element;
	layui.use([ 'form', 'layer', 'element','laydate'], function() {
		form = layui.form;
		layer = parent.layer === undefined ? layui.layer : parent.layer;
		element = layui.element;
		
		form.render();
		
		
	
		showMess();
		stertup_choose(form);
		
		
		
	});

	
</script>
<!-- <script type="text/javascript" src="/bi/assets/merchants_check.js"></script> -->
</html>
