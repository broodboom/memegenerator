import { Component, OnInit } from '@angular/core';
import { MemeService } from '../../_helpers/MemeService';
import { Meme } from '../../shared/models/Meme';

let self: any;

@Component({
  selector: 'ngx-createpage',
  templateUrl: './createpage.component.html',
  styleUrls: ['./createpage.component.scss']
})

export class CreatepageComponent implements OnInit{

  constructor(public memeService : MemeService ) { }

  ngOnInit() {
    self = this;

    this.fillCanvas();

  }



  handleSaveImage(e){
    var canvas = document.querySelector('canvas')
    console.log(canvas);
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
          console.log("img src:  ")
          var temp = event.target.result as string;
          var image = document.querySelector('canvas');
          canvas.setAttribute("src", img.src)

      }


      reader.readAsDataURL(e.target.files[0]);
  }

  fillCanvas(){
    var inputField = document.querySelector('input');
    var canvas = document.querySelector('canvas')
    var ctx = canvas.getContext('2d');
    inputField.addEventListener("keyup", function(){
       var image = document.querySelector('canvas');
       var temp = canvas.getAttribute("src")
      canvas.width = image.width;
      canvas.setAttribute("crossOrigin" , "Anonymous");
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0);
      ctx.font = "64px Verdana";
       //redraw image
       ctx.clearRect(0,0,image.width,image.height);


      var tempImg = new Image();
      tempImg.src = temp;
        ctx.drawImage(tempImg, 0, 0);
      // canvas.setAttribute("background-image", temp);
       //refill text
       ctx.fillStyle = "black";
       ctx.fillText(inputField.value,40,80);
    })

    var imageLoader = document.getElementById('imageLoader');
      imageLoader.addEventListener('change', this.handleSaveImage, false);

      var savebutton = document.querySelector('.save-button');
    savebutton.addEventListener("click", this.saveMeme)


  }

  saveMeme(){
    var canvas = document.querySelector('canvas');

    canvas.toBlob(function(blob) {
      var newImg = document.createElement('img'),
          url = URL.createObjectURL(blob);

      var meme= new Meme("test", blob, 1 );
      
      self.memeService.CreateMeme(meme).subscribe(res => {
        console.log(res);
      });

      newImg.onload = function() {
        // no longer need to read the blob so it's revoked
        URL.revokeObjectURL(url);

        
        newImg.src = url;
        document.body.appendChild(newImg);
      };
    });
  }


}
