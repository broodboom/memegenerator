import { Component, OnInit } from '@angular/core';

function fillCanvas(){
  
  
  var inputField = document.querySelector('input');
  var canvas = document.querySelector('canvas')
  var ctx = canvas.getContext('2d');
  inputField.addEventListener("keyup", function(){
  
   
    var image = document.querySelector('img');
    canvas.width = image.width;
    canvas.setAttribute("crossOrigin" , "Anonymous");
    canvas.height = image.height;
    ctx.drawImage(image, 0, 0);
    ctx.font = "64px Verdana";
     //redraw image
     ctx.clearRect(0,0,image.width,image.height);
     console.log(canvas.width);
     ctx.drawImage(image, 0, 0);
     //refill text
     ctx.fillStyle = "black";
     ctx.fillText(inputField.value,40,80);
  })
  
  var imageLoader = document.getElementById('imageLoader');
    imageLoader.addEventListener('change', handleImage, false);

  var savebutton = document.querySelector('.save-button');
  savebutton.addEventListener("click", function(){

    var savedImage = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
    window.location.href= savedImage;
  })
}


// var ctx = canvas.getContext('2d');


function handleImage(e){
  var canvas = document.querySelector('canvas')
  var ctx = canvas.getContext('2d');
    var reader = new FileReader();
    reader.onload = function(event){
        var img = new Image();
        img.onload = function(){
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img,0,0);
        }
        img.src= event.target.result as string;
        var temp = event.target.result as string;
        var image = document.querySelector('img');
        image.setAttribute("src", temp)
    }

   
    reader.readAsDataURL(e.target.files[0]);     
}
  


@Component({
  selector: 'ngx-createpage',
  templateUrl: './createpage.component.html',
  styleUrls: ['./createpage.component.scss']
})



export class CreatepageComponent implements OnInit{
 
  constructor() { 

  }
  
  ngOnInit() {
    fillCanvas();
  }


}
