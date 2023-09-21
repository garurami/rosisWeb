$(document).ready(function(){
	// 안내바 마우스 오버시 색상 변경
	$("#ftco-navbar .container").hover(function(){
		
//		$(this).attr("style","color : #323edd");
		$(this).css("color","#323edd");
		
		if($("#ftco-navbar").hasClass("awake") == true) { // nav바의 디자인이 변경 되었을때
			$(this).next().css("background-color","white"); // ".dropdown-menu"
			$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
			
//			$(".dropdown-menu").css("background-color","white");
//			$(".dropdown-menu .dropdown-item").css("color","black");
		}else{ // 첫 진입시의 nav바
			
			if($(".ftco-navbar-light .container").width() >= 930){ // 모바일(넓이가 930이상인 경우만), PC 공통
				$(this).next().css("background-color","white"); // ".dropdown-menu"
				$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
				
//				$(".dropdown-menu").css("background-color","white");
//				$(".dropdown-menu .dropdown-item").css("color","black");
			}else{
				// 모바일 화면일 경우
				$(this).next().css("background-color","black"); // ".dropdown-menu"
				$(this).next().children().css("color","white"); // ".dropdown-menu .dropdown-item"
				
//				$(".dropdown-menu").css("background-color","black");
//				$(".dropdown-menu .dropdown-item").css("color","white");
			}
			
		}

	}, function(){
		
		if($("#ftco-navbar").hasClass("awake") == true) {
			
			// nav바의 배경이 하얀색일때
			$(this).next().css("background-color","white"); // ".dropdown-menu"
			$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
			
//			$(".dropdown-menu").css("background-color","white");
//			$(".dropdown-menu .dropdown-item").css("color","black");
			
			// hover 아웃시
//			$(this).attr("style","color : black");
			$(this).css("color","black");
			
			// 모바일 웹(넓이가 930이상인 경우만), PC 웹 둘다 네비게이션 바가 하얀색일 경우
			if($(".ftco-navbar-light .container").width() >= 930){
				
				// hover 아웃시
//				$(this).attr("style","color : black");
				$(this).css("color","black");
				
				// nav바의 배경이 흰색일때
				$(this).next().css("background-color","white"); // ".dropdown-menu"
				$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
				
//				$(".dropdown-menu").css("background-color","white");
//				$(".dropdown-menu .dropdown-item").css("color","black");
			}else{
				
				// nav바의 배경이 검은색일때
				$(this).next().css("background-color","black"); // ".dropdown-menu"
				$(this).next().children().css("color","white"); // ".dropdown-menu .dropdown-item"
				
//				$(".dropdown-menu").css("background-color","black");
//				$(".dropdown-menu .dropdown-item").css("color","white");
			}
			
		}else{
			
			// hover 아웃시
			
			// 모바일 웹(넓이가 930이상인 경우만), PC 웹 둘다 네비게이션 바가 하얀색일 경우
			if($(".ftco-navbar-light .container").width() >= 930){
				
//				$(this).attr("style","color : black");
				$(this).css("color","black");
				
				$(this).next().css("background-color","white"); // ".dropdown-menu"
				$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
				
//				$(".dropdown-menu").css("background-color","white");
//				$(".dropdown-menu .dropdown-item").css("color","black");
					
			}else{
				
//				$(this).attr("style","color : white");
				$(this).css("color","white");
				
				$(this).next().css("background-color","black"); // ".dropdown-menu"
				$(this).next().children().css("color","white"); // ".dropdown-menu .dropdown-item"
				
//				$(".dropdown-menu").css("background-color","black");
//				$(".dropdown-menu .dropdown-item").css("color","white");
				
			}
			
		}
		
	});
	
	// 이메일 플랫폼 호출 및 전송 
	$("#sendBtn").on("click", function(){
		fnEmailTo();
	});
	
	// 카카오 지도 정보 
	$("#mapBtn").on("click", function(){
		getInfo();
	});
	
	// 드롭 박스 메뉴
	$(".dropdown-toggle").on("click", function(){
		
		if($(this).hasClass("show")){
			$(this).removeClass("show");
			
			// 해당 메뉴 숨김처리
//			$(".dropdown-menu").removeClass("show");
			$(this).next().removeClass("show");	// ".dropdown-menu"
			
			if($("#ftco-navbar").hasClass("awake") == true){
				$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
				$(this).next().css("background-color","white"); // ".dropdown-menu"
//				$(".dropdown-item").css("color", "black");
//				$(".dropdown-menu").css("background-color","white");
			}else{
				$(this).next().children().css("color","white"); // ".dropdown-menu .dropdown-item"
				$(this).next().css("background-color","black"); // ".dropdown-menu"
//				$(".dropdown-item").css("color", "white");
//				$(".dropdown-menu").css("background-color","black");
				
			}
			
			// 모바일 웹(넓이가 930이상인 경우만), PC 웹 둘다 네비게이션 바가 하얀색일 경우 
			if($(".ftco-navbar-light .container").width() >= 930){
				$(this).attr("style","color : black");
			}else{
				$(this).attr("style","color : white");
			}
			
		}else {
			
			// 드롭박스 보이게 설정
			$(this).addClass("show");
			
			// 해당 메뉴 보이게 설정
//			$(".dropdown-menu").addClass("show");
			$(this).next().addClass("show");	// ".dropdown-menu"
			
			// 아래로 스크롤시 생성 되는 class 
			if($("#ftco-navbar").hasClass("awake") == true){
				$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
				$(this).next().css("background-color","white"); // ".dropdown-menu"
//				$(".dropdown-item").css("color", "black");
//				$(".dropdown-menu").css("background-color","white");
				
			}else{
				// 모바일 웹(넓이가 930이상인 경우만), PC 웹 둘다 네비게이션 바가 하얀색일 경우
				if($(".ftco-navbar-light .container").width() >= 930){
					$(this).next().children().css("color","black"); // ".dropdown-menu .dropdown-item"
					$(this).next().css("background-color","white"); // ".dropdown-menu"
//					$(".dropdown-item").css("color", "black");
//					$(".dropdown-menu").css("background-color","white");
				}else{
					$(this).next().children().css("color","white"); // ".dropdown-menu .dropdown-item"
					$(this).next().css("background-color","black"); // ".dropdown-menu"
//					$(".dropdown-item").css("color", "white");
//					$(".dropdown-menu").css("background-color","black");
				}
					
			}
		}
	});
	
	// 개발 사업 범위 버튼 이벤트
	$(".btnGroup button").on("click", function(){
		let btnType = $(this).attr("id")
		
		$("#"+btnType).addClass("active");
		
		if(btnType == "bankDev"){// 금융 버튼을 제외한 나머지 버튼 active 해제
			
			$("#insuranceDev").removeClass("active");
			$("#pulicDev").removeClass("active");
			
		}else if(btnType == "insuranceDev"){// 보험 버튼을 제외한 나머지 버튼 active 해제
			
			$("#bankDev").removeClass("active");
			$("#pulicDev").removeClass("active");
			
		}else if(btnType == "pulicDev"){// 공공 버튼을 제외한 나머지 버튼 active 해제
			
			$("#bankDev").removeClass("active");
			$("#insuranceDev").removeClass("active");
			
		}
	});
})

// 이메일 플랫폼 호출 및 전송
function fnEmailTo(){
	let emailAddr = $("#email").val();									// 이메일 주소
	let sendNm = $("#name").val();										// 이름
	let sendContext = $("#message").val().replaceAll("\n","%0D%0A");	// 보낼 내용
	location.href = "mailto:" + emailAddr + "?body=" + sendNm + "%0D%0A" + sendContext; 
}
