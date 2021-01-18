import { Component, OnInit, ViewChild } from "@angular/core";
import { MemeService } from "../../services/meme/meme.service";
import { Meme } from "../../models/Meme";
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from "../../services/auth/auth.service";
import { TagService } from "app/services/tag/tag.service";
import { Tag } from "app/models/Tag";

let self: any;

@Component({
  selector: "ngx-createpage",
  templateUrl: "./createpage.component.html",
  styleUrls: ["./createpage.component.scss"],
})
export class CreatepageComponent implements OnInit {

  options: string[];
  filteredOptions$: Observable<string[]>;
  tag: Tag;

  @ViewChild('autoInput') input;

  constructor(
    private memeService: MemeService,
    private authService: AuthService,
    private tagService: TagService
  ) {}

  ngOnInit() {
    self = this;

    this.fillCanvas();
    this.tagService.getTags().pipe(
      tap((result)=>console.log(result))
    ).subscribe((tags: Tag[])=>{
        this.tag = {title: "test", id: tags.length};
        this.options = [];
        tags.forEach(tag => this.options.push(tag.title))
        this.filteredOptions$ = of(this.options);
        this.activateButton(self.tag, this.tagService);
      }

    );
  }

  activateButton(tag: Tag, tagService: TagService){
    const tagbutton = document.querySelector('.add-tag-button');
        tagbutton.addEventListener("click", function(event){
          let tagInput = <HTMLInputElement>document.getElementById('tag');
          tag.title = tagInput.value;
          // Still needing a don't add if already exists
          tagService.createTag(tag);
        }, false)
  }

  private filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(optionValue => optionValue.toLowerCase().includes(filterValue));
  }

  getFilteredOptions(value: string): Observable<string[]> {
    return of(value).pipe(
      map(filterString => this.filter(filterString)),
    );
  }

  onChange() {
    this.filteredOptions$ = this.getFilteredOptions(this.input.nativeElement.value);
  }

  onSelectionChange($event){
    this.filteredOptions$ = this.getFilteredOptions($event);
  }

  handleSaveImage(e) {
    var canvas = document.querySelector("canvas");
    console.log(canvas);
    var ctx = canvas.getContext("2d");
    var reader = new FileReader();
    reader.onload = function (event) {
      var img = new Image();
      img.onload = function () {
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0);
      };
      img.src = event.target.result as string;
      console.log("img src:  ");
      var temp = event.target.result as string;
      var image = document.querySelector("canvas");
      canvas.setAttribute("src", img.src);
    };

    reader.readAsDataURL(e.target.files[0]);
  }

  fillCanvas() {
    var inputField = document.querySelector("input");
    var canvas = document.querySelector("canvas");
    var ctx = canvas.getContext("2d");
    inputField.addEventListener("keyup", function () {
      var image = document.querySelector("canvas");
      var temp = canvas.getAttribute("src");
      canvas.width = image.width;
      canvas.setAttribute("crossOrigin", "Anonymous");
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0);
      ctx.font = "64px Verdana";
      //redraw image
      ctx.clearRect(0, 0, image.width, image.height);

      var tempImg = new Image();
      tempImg.src = temp;
      ctx.drawImage(tempImg, 0, 0);
      // canvas.setAttribute("background-image", temp);
      //refill text
      ctx.fillStyle = "black";
      ctx.fillText(inputField.value, 40, 80);
    });

    var imageLoader = document.getElementById("imageLoader");
    imageLoader.addEventListener("change", this.handleSaveImage, false);

    var savebutton = document.querySelector(".save-button");
    savebutton.addEventListener("click", this.saveMeme);
  }

  saveMeme() {
    var canvas = document.querySelector("canvas");

    const userId = this.authService.getCurrentUser().id;

    canvas.toBlob(function (blob) {
      var newImg = document.createElement("img"),
        url = URL.createObjectURL(blob);

      var meme: Meme = {
        title: "test",
        categoryId: 1,
        userId: userId,
        imageblob: blob,
      };

      self.memeService.CreateMeme(meme).subscribe((res) => {
        console.log(res);
      });

      newImg.onload = function () {
        // no longer need to read the blob so it's revoked
        URL.revokeObjectURL(url);

        newImg.src = url;
        document.body.appendChild(newImg);
      };
    });
  }
}
