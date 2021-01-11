import { Component, OnInit } from "@angular/core";
import { Meme } from "app/models/Meme";
import { tap } from "rxjs/operators";
import { MemeService } from "../../services/meme.service";

@Component({
  selector: "ngx-likebutton",
  styleUrls: ["./likebutton.component.scss"],
  templateUrl: "./likebutton.component.html",
})
export class LikeButtonComponent implements OnInit {
  meme: Meme;
  constructor(private memeService: MemeService) {}

  ngOnInit() {
    let memeId = 5;
    this.memeService
      .getMeme(memeId)
      .pipe(tap((result) => console.log(result)))
      .subscribe(
        meme => {
          this.meme = meme;
          this.meme.id = memeId;
          this.activateButtons(this.memeService, this.meme);
        });

  }
  activateButtons(memeService: MemeService, meme: Meme) {
    var likes = document.querySelector(".likeValue");
    var dislikes = document.querySelector(".dislikeValue");

    likes.innerHTML = this.meme.likes.toString();
    dislikes.innerHTML = this.meme.dislikes.toString();

    var likebutton = document.querySelector(".like-button");
    likebutton.addEventListener(
      "click",
      function (event) {
        likes.innerHTML = (parseInt(likes.innerHTML) + 1).toString();

        meme.likes = parseInt(likes.innerHTML);
        memeService.updateMeme(meme).subscribe((data: any) => console.log(data));
      },
      false
    );
    var dislikebutton = document.querySelector(".dislike-button");
    dislikebutton.addEventListener(
      "click",
      function (event) {
        dislikes.innerHTML = (parseInt(dislikes.innerHTML) + 1).toString();

        meme.dislikes = parseInt(dislikes.innerHTML);
        memeService.updateMeme(meme).subscribe((data: any) => console.log(data));
      },
      false
    );
  }
}
