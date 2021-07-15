$(document).ready(function () {
	var userSymbol = " &#x1F464; ";

    //Not working
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

     $.ajax({
        type:"GET",
        url:"http://localhost:8072/admin/getAll",
        success:function(data){
           //alert(JSON.stringify(data));
            var select = document.querySelector("#employee-list");
            data.forEach(name => {
                console.log(name);
                var option = document.createElement("option");
                option.appendChild(document.createTextNode(name));
                select.appendChild(option);
            });
        },
       
    });

    $(".delete-form").on("submit", function(e){
        e.preventDefault();
       let param = decodeURIComponent($(this).serialize()).split("=")[1];
       alert(param);
       $.ajax({
           type:"DELETE",
           url:"http://localhost:8072/admin/delete?"+"employee="+param,
           success:function(res){
               alert(res);
               location.reload();
           },
             error:function(data){
              alert(data);
           }
       });
   });

	var errorSymbol = "&#10008; Error:"; 
    
    
    $(".logout").click(function(e){
       // alert($.cookie('token'));
        $.removeCookie('token', { path: '/' }); 
        $.removeCookie('name', { path: '/' });
        $(location).attr('href', '/login');
       });
});