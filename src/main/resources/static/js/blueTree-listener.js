$(document).ready(function () {
	var errorSymbol = document.querySelector("#add-form > span").innerHTML;
	var userSymbol = " &#x1F464; ";
    
    $("#find-control-button").click(function(e){
        e.preventDefault();
        $("#find-sheet").css("display","block");
        $("#add-sheet").css("display","none");
        $("#update-sheet").css("display","none");
        $("#remove-sheet").css("display","none");
        $("#find-control-button").removeClass("button2"); $("#find-control-button").addClass("button1");
        $("#add-control-button").removeClass("button1"); $("#add-control-button").addClass("button2");
        $("#remove-control-button").removeClass("button1"); $("#remove-control-button").addClass("button2");
        $("#update-control-button").removeClass("button1"); $("#update-control-button").addClass("button2");
        
    });
    $("#add-control-button").click(function(e){
        e.preventDefault();
        $("#add-sheet").css("display","block");
        $("#find-sheet").css("display","none");
        $("#update-sheet").css("display","none");
        $("#remove-sheet").css("display","none");
        $("#add-control-button").removeClass("button2"); $("#add-control-button").addClass("button1");
        $("#find-control-button").removeClass("button1"); $("#find-control-button").addClass("button2");
        $("#remove-control-button").removeClass("button1"); $("#remove-control-button").addClass("button2");
        $("#update-control-button").removeClass("button1"); $("#update-control-button").addClass("button2");
        
    });
    $("#update-control-button").click(function(e){
        e.preventDefault();
        $("#update-sheet").css("display","block");
        $("#add-sheet").css("display","none");
        $("#find-sheet").css("display","none");
        $("#remove-sheet").css("display","none");
        $("#update-control-button").removeClass("button2"); $("#update-control-button").addClass("button1");
        $("#add-control-button").removeClass("button1"); $("#add-control-button").addClass("button2");
        $("#remove-control-button").removeClass("button1"); $("#remove-control-button").addClass("button2");
        $("#find-control-button").removeClass("button1"); $("#find-control-button").addClass("button2");
        
    });
    $("#remove-control-button").click(function(e){
        e.preventDefault();
        $("#remove-sheet").css("display","block");
        $("#add-sheet").css("display","none");
        $("#update-sheet").css("display","none");
        $("#find-sheet").css("display","none");
        $("#remove-control-button").removeClass("button2"); $("#remove-control-button").addClass("button1");
        $("#add-control-button").removeClass("button1"); $("#add-control-button").addClass("button2");
        $("#find-control-button").removeClass("button1"); $("#find-control-button").addClass("button2");
        $("#update-control-button").removeClass("button1"); $("#update-control-button").addClass("button2");
        
    });


    
    $("#find-form").on("submit", function(e){
        e.preventDefault();
        $("#transparent").css("display","block");
        let params = $(this).serialize();
        url="http://localhost:8072/employee/find?field="+params.split("&")[0].split("=")[1]+"&value="+params.split("&")[1].split("=")[1];
        $.ajax({
            type:"GET",
            url,
            success:function(data){
                $("#transparent").css("display", "none");
                document.querySelector("#find-form > span").style.display ="none";
                var table = document.querySelector("#query-result-table");
                var oldTbody = document.querySelector("#query-result-table > tbody");
                table.removeChild(oldTbody);
                var newTbody = document.createElement("tbody");
                table.appendChild(newTbody);
                if (params.split("&")[0].split("=")[1] == "id") {
                    data["updatedAt"] = data["updatedAt"].split("T")[0];
                    var parent = document.querySelector("#query-result-table > tbody");
                    var row = document.createElement("tr");
                    var dat = document.createElement("td");
                    dat.innerHTML = 1;
                    row.appendChild(dat);

                    for (let [key, value] of Object.entries(data)) {
                        dat = document.createElement("td");
                        dat.innerHTML = value;
                        row.appendChild(dat);
                    }
                    parent.appendChild(row);
                }else{
                    var parent = document.querySelector("#query-result-table > tbody")
                    for (let i = 0; i < data.length; i++) {
                        data[i]["updatedAt"] = data[i]["updatedAt"].split("T")[0]
                        var row = document.createElement("tr");
                        var dat = document.createElement("td");
                        dat.innerHTML = i+1;
                        row.appendChild(dat);

                        for (let [key, value] of Object.entries(data[i])) {
                            dat = document.createElement("td");
                            dat.innerHTML = value;
                            row.appendChild(dat);
                        }
                        parent.appendChild(row);
                    }
                }    
            },
            error:function(data){
                $("#transparent").css("display", "none");
				var error =  document.querySelector("#find-form > span");
				error.innerHTML = errorSymbol+" "+data.responseText;
				error.style.display ="block";           
			}
        })
    });

    $("#add-form").on("submit", function(e){
        e.preventDefault();
        $("#transparent").css("display","block");
        
        let params = decodeURIComponent($(this).serialize());
        var body = {};
        for(val of params.split("&")){
            var entry = val.split("=");
            body[entry[0]] = entry[1].replaceAll("+"," ");
        }
        $.ajax({
            type:"POST",
            url:"http://localhost:8072/employee/add",
            contentType: 'application/json;  charset=utf-8' ,
            data:JSON.stringify(body),
            success:function(res){
                $("#transparent").css("display", "none");
                document.querySelector("#add-form > span").style.display = "none";
                var table = document.querySelector("#query-result-table");
                var oldTbody = document.querySelector("#query-result-table > tbody");
                table.removeChild(oldTbody);
                var newTbody = document.createElement("tbody");
                table.appendChild(newTbody);
                fillSingleRow(res);
            },
              error:function(data){
               $("#transparent").css("display", "none");
				var error =  document.querySelector("#add-form > span");
				error.innerHTML = errorSymbol + " " + data.responseText;
				error.style.display = "block";
				
            }
        });
    });

    
    $("#update-form").on("submit", function(e){
        e.preventDefault();
        var body = {};
        let params = decodeURIComponent($(this).serialize());
        for(val of params.split("&")){
            var entry = val.split("=");
            body[entry[0]] = entry[1].replaceAll("+"," ");
        }
        if(body["field"]=="updatedAt"){
            return;
        }     
        
        
        $.ajax({
            type:"PUT",
            url:"http://localhost:8072/employee/update",
            contentType: 'application/json;  charset=utf-8' ,
            data:JSON.stringify(body),
            success:function(res){
                $("#transparent").css("display", "none");
                document.querySelector("#update-form > span").style.display ="none";
                var table = document.querySelector("#query-result-table");
                var oldTbody = document.querySelector("#query-result-table > tbody");
                table.removeChild(oldTbody);
                var newTbody = document.createElement("tbody");
                table.appendChild(newTbody);
                fillSingleRow(res);
            },
              error:function(data){
               $("#transparent").css("display", "none");
				var error =  document.querySelector("#update-form > span");
				error.innerHTML = errorSymbol+" "+data.responseText;
				error.style.display ="block";
            }
        });
    });

    $("#remove-form").on("submit", function(e){
    	 e.preventDefault();
        let params = decodeURIComponent($(this).serialize());
        $.ajax({
            type:"DELETE",
            url:"http://localhost:8072/employee/remove?"+params,
            success:function(res){
                $("#transparent").css("display", "none");
                 document.querySelector("#remove-form > span").style.display ="none";
                var table = document.querySelector("#query-result-table");
                var oldTbody = document.querySelector("#query-result-table > tbody");
                table.removeChild(oldTbody);
                var newTbody = document.createElement("tbody");
                table.appendChild(newTbody);
                fillSingleRow(res);
            },
              error:function(data){
               $("#transparent").css("display", "none");
				var error =  document.querySelector("#remove-form > span");
				error.innerHTML = errorSymbol+" "+data.responseText;
				error.style.display ="none";
            }
        });
    });

    $.ajax({
        type:"GET",
        url:"http://localhost:8072/employee/getAll",
        success:function(data){
            var parent = document.querySelector("#query-result-table > tbody")
            for (let i = 0; i < data.length; i++) {
                data[i]["updatedAt"] = data[i]["updatedAt"].split("T")[0]
                var row = document.createElement("tr");
                var dat = document.createElement("td");
                dat.innerHTML = i+1;
                row.appendChild(dat);

                for (let [key, value] of Object.entries(data[i])) {
                    dat = document.createElement("td");
                    dat.innerHTML = value;
                    row.appendChild(dat);
                }
                parent.appendChild(row);
            }
        },
       
    });


    
    $("#upload-excel").on("submit", function(e){
    	e.preventDefault();
    	alert("Uploading form, please reload the page to see updates.");
        var fileInput = $(this);
        // var form = new FormData();
        // form.append("file", fileInput.files[0], fileInput.files[0].value);
        
        // var settings = {
        //   "url": "http://localhost:8072/excelUpload/save",
        //   "method": "POST",
        //   "timeout": 0,
        //   "processData": false,
        //   "mimeType": "multipart/form-data",
        //   "contentType": false,
        //   "data": form
        // };
        
        // $.ajax(settings).done(function (response) {
        //   alert("done!");
        // });

        var formdata = new FormData();
formdata.append("file", fileInput.files[0], "/C:/Users/Kabir Sethi/eclipse-workspace/BlueTree-Intership-Kabir/employees.xlsx");

var requestOptions = {
  method: 'POST',
  body: formdata,
  redirect: 'follow'
};

fetch("http://localhost:8072/excelUpload/save", requestOptions)
  .then(response => response.text())
  .then(result => console.log("Done"))
  .catch(error => console.log('error', error));

    });

    $(".logout").click(function(e){
        // alert($.cookie('token'));
        $.removeCookie('name', { path: '/' });
         $.removeCookie('token', { path: '/' }); 
         $(location).attr('href', '/login');
        });
      
      //User-log  
      var userLog = document.querySelector(".user-log");
     if(userLog != undefined){
        userLog.innerHTML = userSymbol+$.cookie("name")+" ";
     }

});

var fillSingleRow = function(data){
    data["updatedAt"] = data["updatedAt"].split("T")[0]
    var parent = document.querySelector("#query-result-table > tbody")
    var row = document.createElement("tr");
    var dat = document.createElement("td");
    dat.innerHTML = 1;
    row.appendChild(dat);

    for (let [key, value] of Object.entries(data)) {
        dat = document.createElement("td");
        dat.innerHTML = value;
        row.appendChild(dat);
    }
    parent.appendChild(row);
}
