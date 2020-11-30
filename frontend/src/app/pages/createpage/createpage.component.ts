import { Component, OnInit } from '@angular/core';

function fillCanvas(){
  
  var canvas = document.querySelector('canvas'),
  ctx = canvas.getContext('2d');
  var image = document.querySelector('img');
  console.log(image);
  canvas.width = image.width;
  canvas.setAttribute("crossOrigin" , "Anonymous");
  canvas.height = image.height;
  ctx.drawImage(image, 0, 0);
  ctx.font = "36pt Verdana";
  console.log(canvas);
  var inputField = document.querySelector('input');
  console.log(inputField);
  inputField.addEventListener("keyup", function(){
     //redraw image
     ctx.clearRect(0,0,canvas.width,canvas.height);
     ctx.drawImage(image, 0, 0);
     //refill text
     ctx.fillStyle = "black";
     ctx.fillText(inputField.value,40,80);
  })
  // $(document).on('input','#inp',function(){
  //     //redraw image
  //     ctx.clearRect(0,0,canvas.width,canvas.height);
  //     ctx.drawImage(image, 0, 0);
  //     //refill text
  //     ctx.fillStyle = "black";
  //     ctx.fillText($(this).val(),40,80);
  // });
  // $('button').click(function(){
  //     console.log(ctx.getImageData(50, 50, 100, 100));
  // });


  // $('.save_button').click(function(){
      
  //     console.log(canvas);
  //     var image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");  // here is the most important part because if you dont replace you will get a DOM 18 exception.
  //     console.log(image)

  //     window.location.href=image; // it will save locally
  // })
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
