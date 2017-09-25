$(function(){
	var dateTime;
	getDate(dateTime);
	$(".mini").css("background","#bbffaa");
	
});

// 添加预定会议室
function addBookMeet(){
	$("#addDialog").dialog({title: "预定会议室"});
	$("#addDialog").dialog('open');
}

// 取消按钮
function cancleBookMeet(){
	$("#addDialog").dialog('close');
}

// 确认提交按钮
function onSubmit(){
	//emptyDiv();
	var ctxPath = $("#ctxPath").val();
	// 选择会议室
	var meet = $("#meet").combo('getValue');
	if(!meet ||meet<=0){
		sayInfo("请选择会议室。");
		return;
	}
//	$('#dd').datebox('setValue', '6/1/2012');	// set datebox value
//	var v = $('#dd').datebox('getValue');	// get datebox value
	// 预定日期
	var bookDate = $('#bookDate').datebox('getValue');
	if(!bookDate){
		sayInfo("请选择会议预定日期。");
		return;
	}
	// 开始时间
	var startTime = $("#startTime").combo('getValue');
	if(!startTime ||startTime<=0){
		sayInfo("请选择会议预定开始时间。");
		return;
	}	
	
	// 结束时间
	var endTime = $("#endTime").combo('getValue');
	if(!endTime ||endTime<=0){
		sayInfo("请选择会议预定结束时间。");
		return;
	}
	
	if(parseInt(startTime)>parseInt(endTime)){
		sayInfo("开始时间不能在结束时间之后。");
		return;
	}
	
	$.ajax({
		url : ctxPath + '/meet/checkMeet',
		data :{"meet":meet,
			"bookDate":bookDate,
			"startTime":startTime,
			"endTime":endTime,
		},
		type : 'post',
		dataType : 'json',
		//contentType : 'application/json;charset=utf-8',
		success : function(data) {// data:{meet:[{}],info:[{}]}
		if(data.mess>0){
			sayInfo("该会议室在"+startTime +"时 ~~ "+endTime+"时的时间段内已经有人预订了！");
			return;
			}else{
				$("#bookMeet").attr("action",ctxPath + "/meet/bookMeet");
				$("#bookMeet").submit();
			}
		},
		error : function() {
			sayInfo("出错了。");
		}
	});
	
	
	
	
}


// 获取页面大数据
function getDate(dateTime){
	var ctxPath = $("#ctxPath").val();
	$.ajax({
		url : ctxPath + '/meet/getMeetInfo',
		data :{"dateTimex":dateTime,},
		type : 'post',
		dataType : 'json',
		//contentType : 'application/json;charset=utf-8',
		success : function(data) {// data:{meet:[{}],info:[{}]}
			var meet =null;
			var meetInfo =null;
			if(data){
				meet = data.meet;
				meetInfo = data.meetinfo;
			}
			
			var tbody="";
			for(var i=9;i<18;i++){
				tbody = tbody + "<tr class='"+(i%2==1?"comment_odd":"comment_even")+"'>";// <tr class="xx">
				tbody = tbody + "<td height='40'> <center> <font color='#2e8764' > " +(i<10? "0"+i:i)+":00 </font> <br/>|<br/> <font color='#0F1719' >"+(i+1)+ ":00 </font> </center> </td>";							//<td> xx </td>
					for(var m=0;m<meet.length;m++){//**********************************
						if(meetInfo&&meetInfo.length>0){
							var flage =0;
							var count =0;
							var countx =0;
							for(var j=0;j<meetInfo.length;j++){
								//已经有预定记录了
								if(meetInfo[j].meetId==meet[m].pk_id){
										// 如果匹配到了判断时间节点 -- 只包含开始节点不包含结束节点-
									if(meetInfo[j].startTime-0.5<=i && meetInfo[j].endTime>i){
										// 结束半小时的节点之后的节点的判定
										if(meetInfo[j].endTime-i==0.5){
											var endflag = 0;
											// 判断该会议室的吓一条记录是不是也是半小时的
											for(var n=j+1;n<meetInfo.length;n++){
												if(meetInfo[n].meetId==meet[m].pk_id){
													if(i+1-meetInfo[n].startTime==0.5){
														tbody = tbody + "<td > " ;
														tbody = tbody + "<div class='updown'><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> "+"</div><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j+1].booker+"</font> "+"</div></div>";
														tbody = tbody +	"</td>";
														endflag=1;
														//m++;
														break;
													}
												}
											}
											if(endflag==0){
												tbody = tbody + "<td > " ;
												tbody = tbody + "<div class='updown'><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> "+"</div><div class='down'></div></div>";
												tbody = tbody +	"</td>";
												break;	
											}else{
												break;
											}
										}
										// 开始半小时的节点
										if(meetInfo[j].startTime-i==0.5){
											tbody = tbody + "<td  > " ;
											tbody = tbody + "<div class='updown'><div class='down'></div><div class='up'>"+	"预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> "+"</div></div>";
											tbody = tbody +	"</td>";
											break;
										}
										// 完整1个时间的节点
										tbody = tbody + "<td class='mini' > 预定人：<font color='#FFFFFF'>"+meetInfo[j].booker+"</font> <br/> 设&nbsp;&nbsp;&nbsp;备：";
										if(meetInfo[j].assist==0){
											tbody = tbody +"无"+"</td>";
										}else{
											tbody = tbody +meetInfo[j].assist+"</td>";
										}
									}else{
										count++;
										if(count==meetInfo.length)
											tbody = tbody + "<td></td>";
									}
								}else{
									countx++;
								}
							}
							if(countx==meetInfo.length-count&&countx!=0)
								tbody = tbody + "<td></td>";
							
						}else{// 没有预定记录的情况下
							tbody = tbody + "<td></td>";
						}
					}//***************************************************************
				tbody = tbody + "</tr>";
			}
			$("#dateTime").html(data.dateTime);
			$("#weekday").html(data.weekday);
			// 清空之前的数据
			$("#tbody").empty();
			// 插入到前台
			$("#tbody").append(tbody);
		}
	});
}


// 后一天
function nextDay(){
	$('#searchData').datebox('setValue', '');
	var dateTime = $("#dateTime").text();
	//alert(dateTime+"****");
	dateTime = addDate(dateTime,1);
	//$("#dateTime").html(dateTime);
	//alert(dateTime+"++++****");
	// 获取数据
	getDate(dateTime);
	
}

// 前一天
function previousDay(){
	$('#searchData').datebox('setValue', '');
	var dateTime = $("#dateTime").text();
	dateTime = addDate(dateTime,-1);
	//$("#dateTime").html(dateTime);
	// 获取数据
	getDate(dateTime);
}

/**
 * 获取后一天的日期
 * @param dd  时间
 * @param dadd 天数
 * @returns {Date}
 */
 function addDate(date,days){
	// var d=new Date(date);
	 var d = new Date(Date.parse(date.replace(/-/g,"/")));
	 d.setDate(d.getDate()+days);
	 var month=d.getMonth()+1;
	 var day = d.getDate();
	 if(month<10){
		 month = "0"+month;
		 }
	 if(day<10){
		 day = "0"+day;
		 }
	 var val = d.getFullYear()+"-"+month+"-"+day;
	 return val;
 } 

// 搜索功能
 function searchData(){
	 var dateTime = $('#searchData').datebox('getValue');
	 getDate(dateTime);
 }




function emptyDiv(){
	
	$("#meet").combo('setValue', '');
//	$('#dd').datebox('setValue', '6/1/2012');	// set datebox value
//	var v = $('#dd').datebox('getValue');	// get datebox value
	// 预定日期
	 $('#bookDate').datebox('setValue', '');
	// 开始时间
	var startTime = $("#startTime").combo('setValue', '');
	
	// 结束时间
	var endTime = $("#endTime").combo('setValue', '');
}














































