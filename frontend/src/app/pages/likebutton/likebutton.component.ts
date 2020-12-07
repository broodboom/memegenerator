import {Component, OnInit} from '@angular/core';

function activateButtons(){
    var likes = document.querySelector('.likeValue');
    var dislikes = document.querySelector('.dislikeValue');
    likes.innerHTML = "23";
    /*
    var Meme = FindMemeById(1);
    likes.innerHTML = (Meme.likes).toString();
    dislikes.innerHTML = (Meme.dislikes).toString();
    */

    var likebutton = document.querySelector('.like-button');
    likebutton.addEventListener("click", function(event){
        likes.innerHTML = (parseInt(likes.innerHTML) + 1).toString();
        /*
        Meme.likes = parseInt(likes.innerHTML);
        UpdateMeme(Meme);
        */
    }, false)
    var dislikebutton = document.querySelector('.dislike-button');
    dislikebutton.addEventListener("click", function(event){
        dislikes.innerHTML = (parseInt(dislikes.innerHTML) + 1).toString();
        /*
        Meme.dislikes = parseInt(dislikes.innerHTML);
        UpdateMeme(Meme);
        */
    }, false)
}


@Component({
  selector: 'ngx-likebutton',
  styleUrls: ['./likebutton.component.scss'],
  templateUrl: './likebutton.component.html',
})
export class LikeButtonComponent implements OnInit{
    constructor(){

    }

    ngOnInit(){
        activateButtons();
    }
}