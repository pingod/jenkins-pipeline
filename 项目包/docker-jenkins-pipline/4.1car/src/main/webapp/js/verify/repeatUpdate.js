/**
 * 显示判定结果的操作界面
 * @param obj
 */
function openDialog(obj) {
	var exe = $(obj).parents("td").next().html(); // 正常异常
	var name = $(obj).parents("td").next().next().html(); // 正常异常
	$("#titleThis").html(name);
	var valu = $(obj).parents("td").siblings("td").last();	// 备注信息
	$("#remark").val(valu.find("span").html());
	if(exe == '正常'){	
		$("#state2").removeAttr("checked");
		$("#state1").prop("checked",true);
	}else if(exe == '异常'){	
		$("#state1").removeAttr("checked");
	    $("#state2").prop("checked",true);		   
	}else{
		$("#state1").removeAttr("checked");
	    $("#state2").removeAttr("checked");	
	} 
	$("#input").modal('show');
	$(obj).attr("id","innerRepeat");
}
$(function(){
	var checkTel =  $(window.parent.document).find("input[id='checkTel']").val();
	// 失去焦点事件判定
	$("#state1").focus(function(){
		removeMyValidateCss($("#state1").parents("span"));
	});
	$("#state2").focus(function(){
		removeMyValidateCss($("#state2").parents("span"));
	});
	$("#remark").focus(function(){
		removeMyValidateCss($("#remark"));
	});
	
	/**
	 * 保存判定结果
	 */
	$("#save").click(function(){
		var obj = $("#innerRepeat"); //判定按钮
		var valu = $(obj).parents("td").siblings("td").last();	//备注
		var id = $(obj).parents("td").find("input").first().val();//id
		var exe = $(obj).parents("td").next();
			// 获取是否异常 和备注 并赋值给对应的input
	    var remark = $("#remark").val();
	    var exception = $("input[name='exception']:checked ").val();
			var inputs = $(obj).parents("tr").find("input");
			var str = "";
			if(exception == '' || exception == undefined ){
				addMyValidateCss($("#state1").parents("span"));
				return false;
			}
			if($("#remark").val() == ''){
				addMyValidateCss( $("#remark"));
				return false;
			}
			var str = "id="+id+"&remark="+remark+"&exceptionFlag="+exception+"&";
			var param1 = $(window.parent.document).find("#param").serialize();
			//异步提交方法
			$.ajax({
				type:"POST",
				url:ctx+"/verify/InsideNet/updateInnerCheck",
				data:encodeURI(str)+param1,
				success:function(data){
					if(data=="true"){
						$("#input").modal('hide');
						art.dialog.tips("保存成功");
						 $(valu).find("span").html(remark);
						 if(exception == 1){
								$(exe).html("异常");
							}else if(exception == 0){
								$(exe).html("正常");
						}
						$(obj).removeAttr("id"); // 移除id属性
						 $("#remark").val("");
					}else{
						art.dialog.tips("保存失败");
					}
				}
			});   
	});
	
	/**
	 * 放弃判定
	 */
	$("#canle").click(function(){
		$("#input").modal('hide');
		$(obj).removeAttr("id"); // 移除id属性
	});
	// 展开收缩
	$("img[mark='change']").click(function(){
		var title = $(this).attr("title");
	   	if(title == "收起"){
	   		$(this).attr("src",ctxStatic+"/images/add.jpg").attr("title","展开");
	       	$(this).parents("h3").nextAll().hide();
	   	}else{
	   		$(this).attr("src",ctxStatic+"/images/minus.jpg").attr("title","收起");
	       	$(this).parents("h3").nextAll().show();
	   	}
	})
});

/**
 * 显示详细的逾期情况
 * @param event
 */
function showLateTime(event){
	var loanCode = $(event).attr("code");
	art.dialog.open(ctx+'/verify/InsideNet/getLateTime?loanCode='+loanCode,{title:"近12期逾期状况",width:"500px",height:"150px",left:850,top:85,window:"top",lock: false  }); 
}

/**
 * 显示查重中贷后情况的异常
 * @param event
 */
function showLateTimeRecord(event){
	var loanCode = $(event).attr("code");
	art.dialog.open(ctx+'/verify/InsideNet/getLateTimeRecord?loanCode='+loanCode,{title:"违约记录",width:"500px",height:"500px",left:850,top:85,window:"top",lock: false  }); 
}
