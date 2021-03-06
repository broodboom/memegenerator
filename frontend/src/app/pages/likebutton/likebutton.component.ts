import { Component, OnInit } from "@angular/core";
import { Meme } from "app/models/Meme";
import { tap } from "rxjs/operators";
import { MemeService } from "../../services/meme/meme.service";

@Component({
  selector: "ngx-likebutton",
  styleUrls: ["./likebutton.component.scss"],
  templateUrl: "./likebutton.component.html",
})
export class LikeButtonComponent implements OnInit {
  meme: Meme;
  constructor(private memeService: MemeService) {}

  ngOnInit() {
    this.memeService
      .getMeme(1)
      .pipe(tap((result) => console.log(result)))
      .subscribe((meme: Meme) => (this.meme = meme));
    this.activateButtons();
  }
  activateButtons() {
    var likes = document.querySelector(".likeValue");
    var dislikes = document.querySelector(".dislikeValue");
    //likes.innerHTML = "69";
    //dislikes.innerHTML = "420";

    likes.innerHTML = this.meme.likes.toString();
    dislikes.innerHTML = this.meme.dislikes.toString();

    var likebutton = document.querySelector(".like-button");
    likebutton.addEventListener(
      "click",
      function (event) {
        likes.innerHTML = (parseInt(likes.innerHTML) + 1).toString();

        this.meme.upvotes = parseInt(likes.innerHTML);
        this.memeService.updateMeme(this.meme);
      },
      false
    );
    var dislikebutton = document.querySelector(".dislike-button");
    dislikebutton.addEventListener(
      "click",
      function (event) {
        dislikes.innerHTML = (parseInt(dislikes.innerHTML) + 1).toString();

        this.meme.downvotes = parseInt(dislikes.innerHTML);
        this.memeService.updateMeme(this.meme);
      },
      false
    );
  }
}
