/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var progress = document.getElementById("progress");
var progress_wrapper = document.getElementById("progress_wrapper");
var progress_status = document.getElementById("progress_status");

var upload_btn = document.getElementById("upload_btn");
var loading_btn = document.getElementById("loading_btn");
var cancel_btn = document.getElementById("cancel_btn");

var staffTextField=document.getElementById("staff");

var catField=document.getElementById("ParentCategory").value;

var alert_wrapper = document.getElementById("alert_wrapper");
var file_input = document.getElementById("file");
var file_input_lablel=document.getElementById("file_input_lablel"); 
function show_alert(message, alert) {

    alert_wrapper.innerHTML = `<div class="alert alert-${alert} alert-dismissible fade show" role="alert">
  <span>${message}</span>
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>`;
 

};

function input_filename(){
    file_input_lablel.innerText=file_input.files[0].name;
    
    
};

function upload(){
    
    if(!file_input.value){
        
        show_alert("No file selected","warning");
        return; 
        
    }
    
    var url=document.getElementById("route").value;
   
    var data=new FormData();
    var request=new XMLHttpRequest();
    //request.reponseType="json";
    alert_wrapper.innerHTML="";
    file_input.disabled=true;
    upload_btn.classList.add("d-none");
    loading_btn.classList.remove("d-none");
    cancel_btn.classList.remove("d-none");
    progress_wrapper.classList.remove("d-none");
    var file=file_input.files[0];
    alert(file.name);
  //  var filename=file.name;
    var filesize=file.size;
    document.cookie=`Filesize=${filesize}`;
    data.append("file",file);
    
    //end of upload;
    
    request.onreadystatechange=function(){
        if(request.readyState==XMLHttpRequest.DONE){
            
            if(request.status==200){
            
            
            request.upload.addEventListener("progress",function(e){
        var loaded=e.loaded;
        var total=e.total;
       
        var percentage_complete=(loaded/total)*100;
        
        progress.setAttribute("style",`width:${Math.floor(percentage_complete)}%`);
         progress_status.innerHTML=`${Math.floor(percentage_complete)}% uploaded into database <br /><hr /> Click on student List to view Students`;
             
    });
            
            
                loading_btn.classList.add("d-none");
                cancel_btn.classList.add("d-none");
                
            }
            else{
                alert("error"+request.status);
            }
        }
        
        
    }
    
    request.open("POST",url,true);
    request.send(data);
    
  
};
    
    cancel_btn.addEventListener("click",function(){
        request.abort();
        
    });
   
function spinOnload(){
    cancel_btn .classList.remove("d-none");
  loading_btn.classList.remove ("d-none");   
  upload_btn.classList.add("d-none"); 
   
   cancel_btn.addEventListener("click",function(){
        loading_btn.classList.add("d-none");
        upload_btn.classList.remove("d-none");
         cancel_btn .classList.add("d-none");
    });
   
}

    
