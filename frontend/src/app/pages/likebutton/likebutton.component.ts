import {Component, OnInit} from '@angular/core';
import { Meme } from 'app/shared/models/Meme';
import { tap } from 'rxjs/operators';
import { LikebuttonService } from './likebutton.service';

@Component({
  selector: 'ngx-likebutton',
  styleUrls: ['./likebutton.component.scss'],
  templateUrl: './likebutton.component.html',
})
export class LikeButtonComponent implements OnInit{
    meme: Meme;
    constructor(private likebuttonService: LikebuttonService){

    }
    ngOnInit(){
        this.likebuttonService.getMemeById(5).pipe(
            tap((result)=>console.log(result))
        ).subscribe(
            meme=>{
                this.meme = meme;
                this.activateButtons(this.likebuttonService, this.meme);
            }
        );
    }
    activateButtons(likebuttonService: LikebuttonService, meme: Meme){
        var likes = document.querySelector('.likeValue');
        var dislikes = document.querySelector('.dislikeValue');
    
        likes.innerHTML = (this.meme.likes).toString();
        dislikes.innerHTML = (this.meme.dislikes).toString();
    
        var likebutton = document.querySelector('.like-button');

        likebutton.addEventListener("click", function(event){
            likes.innerHTML = (parseInt(likes.innerHTML) + 1).toString();
            meme.likes = parseInt(likes.innerHTML);
            likebuttonService.updateMeme(meme);
        }, false)

        var dislikebutton = document.querySelector('.dislike-button');

        dislikebutton.addEventListener("click", function(event){
            dislikes.innerHTML = (parseInt(dislikes.innerHTML) + 1).toString();
            meme.dislikes = parseInt(dislikes.innerHTML);
            likebuttonService.updateMeme(meme);
        }, false)
    }
}