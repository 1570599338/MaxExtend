$(function(){ 
	$("#fname").val(""); 
	$("#fpwd").val(""); 
	var nameV1 = $("#fname").val(); 
	if (nameV1==""){ 
		$("#fname").attr("class","fm_user");	   
	}else{  
		$("#fname").attr("class","fm_user3");
	}
	$("#fname").focus(function(){ 
		$("#fname").attr("class","fm_user2");
	}).blur(function(){ 
        var nameV = $("#fname").val(); 
		if (nameV=="")
		{ 
		    $(this).attr("class","fm_user");	   
		}else{
			
			$(this).attr("class","fm_user3");
		}
	});
	$("#fpwd").focus(function(){  
		$("#fpwd").attr("class","fm_pwd2");
	}).blur(function(){ 
	    var pwdV = $("#fpwd").val(); 
		if (pwdV==""){ 
		    $(this).attr("class","fm_pwd");	   
		}else{  
			$(this).attr("class","fm_pwd3");
		}
	});
	$("#sub").click(function(){ 
	    $(".fm_error").remove();
		var name=$.trim( $("#fname").val() );
		if(  name ==""){ 
			$("#logErr").html(' <span   class="fm_error"> 请输入用户名 </span>');		
			$("#fname").focus();	
			return false;
		} 
	});
	$("body").bind("keyup",function(event) {  
		if(event.keyCode == 13){
			$("#sub").trigger("click"); 
		}
	});
});

function login(ctxPath){
	$("#flogin").attr("action",ctxPath + "/user/toMain");
	$("#flogin").submit();
}

