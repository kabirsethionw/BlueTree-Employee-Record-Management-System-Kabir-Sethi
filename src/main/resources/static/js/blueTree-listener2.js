$(document).ready(function () {
    //Not working
    var userSymbol = " &#x1F464; ";
    var errorSymbol = "&#10008; Error:"; 

    $(window.location.href).on('change', function(){
    	alert("Changed");
        if(window.location.href = 'http://localhost:8072/login'){
            alert("found");
            $(location).attr('href', '/dashboard');
        }
     });
     
     var userLog = document.querySelector(".user-log");
     if(userLog != undefined){
        userLog.innerHTML = userSymbol+$.cookie("name")+" ";
     }
     


     window.addEventListener('popstate', function (event) {
	 // Log the state data to the console
	 		alert("found");
            $(location).attr('href', '/dashboard');
            
     });

    $(".create-form").on("submit", function(e){
        e.preventDefault();
        
        let params = decodeURIComponent($(this).serialize());
        var body = {};
        for(val of params.split("&")){
            var entry = val.split("=");
            body[entry[0]] = entry[1].replaceAll("+"," ");
        }

        $.ajax({
            type:"POST",
            url:"http://localhost:8072/admin/create",
            contentType: 'application/json;  charset=utf-8' ,
            data:JSON.stringify(body),
            success:function(res){
               alert(res);
            },
              error:function(data){
                alert(data);
            }
        });
    });
    
    
    $(".logout").click(function(e){
        $.removeCookie('token', { path: '/' }); 
        $.removeCookie('name', { path: '/' });
        $(location).attr('href', '/login');
       });

	    $("#login-form").on("submit", function(e){
        e.preventDefault();
        var body = {};
        let params = decodeURIComponent($(this).serialize());
        for(val of params.split("&")){
            var entry = val.split("=");
            body[entry[0]] = entry[1].replaceAll("+"," ");
        } 
        $.ajax({
            type:"POST",
            url:"http://localhost:8072/authenticate",
            contentType: 'application/json;  charset=utf-8' ,
            data:JSON.stringify(body),
            success:function(res){
               document.cookie = "token="+res;
               $(location).attr('href', '/dashboard');
               
            },
              error:function(data){
                var error =  document.querySelector("#login-form > span");
				error.innerHTML = error.innerHTML+" "+data.responseText;
				error.style.display ="block";
            }
        });
    });

});